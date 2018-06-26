package com.common.unit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;

public class FileUtil {
	
	private static Logger log = Logger.getLogger(FileUtil.class);
	
	private static String bytesToHexString(byte[] src){   
        StringBuilder stringBuilder = new StringBuilder();   
        if (src == null || src.length <= 0) {   
            return null;   
        }   
        for (int i = 0; i < src.length; i++) {   
            int v = src[i] & 0xFF;   
            String hv = Integer.toHexString(v);   
            if (hv.length() < 2) {   
                stringBuilder.append(0);   
            }   
            stringBuilder.append(hv);   
        }   
        return stringBuilder.toString();   
    }
	
	@SuppressWarnings({ "resource" })
	public static Boolean isXmlFile(String strPath) throws IOException{
		Boolean blInfo;
		byte[] b;
		FileInputStream is;
		blInfo=false;
		is = new FileInputStream(strPath);
		b = new byte[3];
		is.read(b, 0, b.length);
		//3C3F786D6C Xml文件头
		if("3C3F78".equals(bytesToHexString(b).toUpperCase())==true)
		{
			blInfo=true;
		}
		return blInfo;
	}
	
	public static String readFileToString(String strPath) throws IOException
	{
		String strResult;
		File file;
		file=new File(strPath);
		strResult=FileUtils.readFileToString(file);
		return strResult;
	}
	
	public static String readFileToString(String strPath,String Encode) throws IOException
	{
		String strResult;
		File file;
		file=new File(strPath);
		strResult=FileUtils.readFileToString(file,Encode);
		return strResult;
	}
	
	public static void writeStringToFile(File file, String data, Charset encoding, boolean append)throws IOException{
		FileUtils.writeStringToFile(file, data, encoding, append);
	}
	
	public static void writeXmltoFile(Document document, String strPath,
			String strFormat) {
		File file;
		file=new File(strPath);
		try {
			FileUtils.write(file, document.asXML(), document.getXMLEncoding(), false);
		} catch (Exception exce) {
			log.error(exce.toString(),exce);
		}
	}
	
	/**
	 * 删除制定的xml
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean deleteXML(String filePath) {
		int tryCount;
		Boolean blInfo;
		File file;
		blInfo=false;
		tryCount = 0;
		file=new File(filePath);
		if(file.exists()==true)
		{
			while((blInfo==false) && tryCount<10)  
		    {  
		    	System.gc();
		    	blInfo = file.delete();
		    	tryCount++;
		    }
			if(file.exists()==true){
				log.error("file not delete:"+file.getName());
			}
		}
		return blInfo;
	}
}
