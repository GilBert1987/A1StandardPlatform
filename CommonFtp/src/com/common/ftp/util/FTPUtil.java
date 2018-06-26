package com.common.ftp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.common.ftp.config.FTPClientConfigure;

public class FTPUtil {
	
	private static Logger log = Logger.getLogger(FTPUtil.class);
	
	//@Resource
	//private FTPClientPool fTPClientPool;
	@Resource
	private FTPClientConfigure config;
	
	public boolean uploadFile(File[] uploadFile, String basePath, String filePath){
		File file;
		String[] dirs;
		Boolean blInfo;
		String tempPath;
		FTPClient client;
		Boolean blRetInfo;
		blInfo=true;
		blRetInfo=false;
		client=null;
		try {
			client = getContentClient();
			//切换到上传目录  ,basepath需已存在
            if (!client.changeWorkingDirectory(basePath+filePath)) {  
                //如果目录不存在创建目录  
            	dirs = filePath.split("/");  
                tempPath = basePath;  
                for (String dir : dirs) {  
                    if (null == dir || "".equals(dir)){
                    	continue;
                    }
                    tempPath += "/" + dir;  
                    if (!client.changeWorkingDirectory(tempPath)) {  
                        if (!client.makeDirectory(tempPath)) {
                        	blInfo=false;
                            break; 
                        } else {  
                        	client.changeWorkingDirectory(tempPath);  
                        }  
                    }  
                }  
            }
            if(true==blInfo)
            {
				if(uploadFile != null && uploadFile.length > 0){
					for(int i=0;i<uploadFile.length;i++){
						file = uploadFile[i];
						saveLocalFile(file,client);
					}
				}
				blRetInfo=true;
            }
		} catch (Exception e) {
			log.error(e);
		} 
		finally {
			closeFtpClient(client);
		}
		return blRetInfo;
	}
	
	private void closeFtpClient(FTPClient client) {
		if (null!=client && client.isConnected()) {
            try {
            	client.disconnect();
            }
            catch (IOException ioe) {
            	log.error(ioe);
            }
        }
	}

	private FTPClient getContentClient(){
		FTPClient client;
		client = new FTPClient();
		try {
			client.connect(config.getHost(), config.getPort());
			client.login(config.getUsername(), config.getPassword());
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			client.setControlEncoding("UTF-8");
			client.setCharset(Charset.forName("UTF-8"));
		} catch (Exception e) {
			log.error(e);
		} 
		return client;
	}
	
	public boolean uploadFile(MultipartFile[] uploadFile, String basePath, String filePath){
		String[] dirs;
		Boolean blInfo;
		String tempPath;
		FTPClient client;
		Boolean blRetInfo;
		MultipartFile file;
		blInfo=true;
		blRetInfo=false;
		client=null;
		try {
			client = getContentClient();
			//切换到上传目录  ,basepath需已存在
            if (!client.changeWorkingDirectory(basePath+filePath)) {  
                //如果目录不存在创建目录  
                dirs = filePath.split("/");  
                tempPath = basePath;  
                for (String dir : dirs) {  
                    if (null == dir || "".equals(dir)) continue;  
                    tempPath += "/" + dir;  
                    if (!client.changeWorkingDirectory(tempPath)) {  
                        if (!client.makeDirectory(tempPath)) {  
                        	blInfo=false;
                            break;  
                        } else {  
                        	client.changeWorkingDirectory(tempPath);  
                        }  
                    }  
                }  
            }   
            if(true==blInfo)
            {
				if(uploadFile != null && uploadFile.length > 0){
					for(int i=0;i<uploadFile.length;i++){
						file = uploadFile[i];
						saveFile(file,client);
					}
				}
				blRetInfo=true;
            }
		} catch (Exception e) {
			log.error(e);
		}
		finally{
			closeFtpClient(client);
		}
		return blRetInfo;
	}
	
	private boolean saveLocalFile(File file, FTPClient client) {
		String fileName;
		boolean success = false;
		InputStream inStream = null;
		try {
			fileName = file.getName();
			inStream = new FileInputStream(file);
			success = client.storeFile(fileName, inStream);
			if (success == true) {
				success=true;
            }
		}  
		catch (Exception e) {
			log.error(e);
		}finally {
			IOUtils.closeQuietly(inStream);
        }
		return success;
	}

	private boolean saveFile(MultipartFile file,FTPClient client){
		String fileName;
		boolean success = false;
		InputStream inStream = null;
		try {
			fileName = new String(file.getOriginalFilename());
			inStream = file.getInputStream();
			success = client.storeFile(fileName, inStream);
			if (success == true) {
                return success;
            }
		}catch (Exception e) {
			log.error(e);
		}finally {
            IOUtils.closeQuietly(inStream);
        }
		return success;
	}
	
	public boolean downloadFile(OutputStream os,String fileName, String path){
		FTPFile[] fs;
		FTPClient client;
		client=null;
		try {
			client = getContentClient();
			client.changeWorkingDirectory(path);
			fs = client.listFiles();
			for(FTPFile ff: fs){
				if(ff.getName().equals(fileName)){
					client.retrieveFile(ff.getName(), os);
					os.flush();
					break;
				}
			}
		} catch (Exception e) {
			log.error(e);
		}finally {
			closeFtpClient(client);
        }
		return true;
	}
	
	public boolean removeDirectory(String path, String fileName){
		boolean flag;
		FTPClient client;
		flag=false;
		client=null;
		try {
			client = getContentClient();
			flag = client.changeWorkingDirectory(path);// 转移到FTP服务器目录
			if(true==flag)
			{
				flag=client.deleteFile(new String(fileName));
			}
		}
		catch(Exception e)
		{
			log.error(e);
		}
		finally{
			closeFtpClient(client);
		}
		return flag;
	}
}
 
