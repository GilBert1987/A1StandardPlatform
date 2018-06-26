package com.common.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.ftp.util.FTPUtil;
import com.common.service.CommonService;

@RequiresAuthentication
@RequestMapping(method = { RequestMethod.POST })
public class FileController{

	private static Logger log = Logger.getLogger(FileController.class);
	
	@Resource
	private CommonService commonService;
	
	@Resource
	private FTPUtil ftpUtil;
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(method = { RequestMethod.POST }, value = { "/filedown" })
	@ResponseBody
	public void fileDown(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map mapInfo;
		String fileId;
		String strSql;
		Object[] param;
		List<Map> listMap;
		String strOrgName;
		String strFullpath;
		String strDescName;
		fileId=request.getParameter("_fileId");
		if(null!=fileId)
		{
			param=new Object[1];
			param[0]=fileId;
			strSql="SELECT sc_filepath.path as path,sc_file.org_name,sc_file.desc_name FROM sc_file join sc_filepath on sc_file.pathId=sc_filepath.id WHERE sc_file.id=?";
			listMap=commonService.queryListSql("com", strSql, param);
			if(null!=listMap && 1==listMap.size())
			{
				mapInfo=listMap.get(0);
				strOrgName=mapInfo.get("org_name").toString();
				strDescName=mapInfo.get("desc_name").toString();
				strFullpath=mapInfo.get("path").toString();
				response.addHeader("Content-Disposition","attachment;filename="+new String(strOrgName.getBytes("gb2312"),"ISO8859-1"));
				response.setContentType("octets/stream");
				ftpUtil.downloadFile(response.getOutputStream(), strDescName, "/updatefile"+strFullpath);
			}
		}
	}
	
	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(method = {RequestMethod.GET}, value = { "/imgdown" })
	@ResponseBody
	public void imgdown(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map mapInfo;
		String fileId;
		String strSql;
		Object[] param;
		List<Map> listMap;
		String strOrgName;
		String strFullpath;
		String strDescName;
		fileId=request.getParameter("_fileId");
		if(null!=fileId)
		{
			param=new Object[1];
			param[0]=fileId;
			strSql="SELECT sc_filepath.path as path,sc_file.org_name,sc_file.desc_name FROM sc_file join sc_filepath on sc_file.pathId=sc_filepath.id WHERE sc_file.id=?";
			listMap=commonService.queryListSql("com", strSql, param);
			if(null!=listMap && 1==listMap.size())
			{
				mapInfo=listMap.get(0);
				strOrgName=mapInfo.get("org_name").toString();
				strDescName=mapInfo.get("desc_name").toString();
				strFullpath=mapInfo.get("path").toString();
				response.setCharacterEncoding("UTF-8");
				response.setContentType("img/jpeg");
				ftpUtil.downloadFile(response.getOutputStream(), strDescName, "/updatefile"+strFullpath);
			}
		}
	}

	@RequiresAuthentication
	@RequiresUser
	@RequestMapping(method = { RequestMethod.POST }, value = { "/webUploader" })
	@ResponseBody
	public void webUploader(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Date dt;
		String id;
		int chunk;
		int chunks;
		File[] files;
		String pathId;
		File partFile;
		String strExte;
		String strType;
		String fileKey;
		String groupId;
		String filePath;
		String realname;
		String fileName;
		Map mapShowInfo;
		Object[] delObj;
		Object[] showObj;
		String strDelSql;
		String strShowSql;
		String strKeyInfo;
		File destTempFile;
		File tempPartFile;
		String strMultiple;
		String strPathInfo;
		List<Map> listShow;
		String tempFileDir;
		String fileSysName;
		File parentFileDir;
		boolean uploadDone;
		Object[] insertObj;
		JSONObject jsonFile;
		JSONArray jsonFiles;
		boolean isMultipart;
		FileItem tempFileItem;
		FileItemFactory factory;
		ServletFileUpload upload;
		List<FileItem> fileItems;
		FileOutputStream destTempfos;
		try {
			strMultiple="0";
			dt=new Date();
			isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				factory = new DiskFileItemFactory();
				upload = new ServletFileUpload(factory);
				// 得到所有的表单域，它们目前都被当作FileItem
				fileItems = upload.parseRequest(request);
				id = "";
				strType="";
				fileName = "";
				// 如果大于1说明是分片处理
				chunks = 1;
				chunk = 0;
				tempFileItem = null;
				for (FileItem fileItem : fileItems) {
					if (fileItem.getFieldName().equals("name")) {
						fileName = new String(fileItem.getString().getBytes("ISO-8859-1"), "UTF-8");
					} else if (fileItem.getFieldName().equals("chunks")) {
						chunks = NumberUtils.toInt(fileItem.getString());
					} else if (fileItem.getFieldName().equals("chunk")) {
						chunk = NumberUtils.toInt(fileItem.getString());
					} else if (fileItem.getFieldName().equals("id")) {
						id = fileItem.getString();
					} else if (fileItem.getFieldName().equals("type")) {
						strType = fileItem.getString();
					}
				}
				pathId="";
				fileKey="";
				groupId="";
				for (FileItem fileItem : fileItems) {
					if (fileItem.getFieldName().equals("files")) {
						jsonFiles=JSONArray.fromObject(fileItem.getString());
						for(int i=0;i<jsonFiles.size();i++)
						{
							jsonFile=jsonFiles.getJSONObject(i);
							if(id.equals(jsonFile.getString("fileId"))==true)
							{
								fileKey=jsonFile.getString("fileKey");
								groupId=jsonFile.getString("groupId");
								pathId=jsonFile.getString("pathId");
								strMultiple=jsonFile.getString("multiple");
							}
						}
						break;
					}
				}
				for (FileItem fileItem : fileItems) {
					if (fileItem.getFieldName().equals(groupId+"|multiFile")) {
						tempFileItem = fileItem;
						break;
					}
				}
				// session中的参数设置获取是我自己的原因,文件名你们可以直接使用fileName,这个是原来的文件名
				fileSysName = fileKey;//request.getSession().getAttribute("fileSysName").toString();
				strExte=fileName.substring(fileName.lastIndexOf("."));// 转化后的文件名
				realname = fileSysName + strExte;// 转化后的文件名
				filePath = request.getSession().getServletContext().getRealPath("/upload")+"/";// Const.VIDEOPATHFILE + "sound/";//文件上传路径
				// 临时目录用来存放所有分片文件
				tempFileDir = filePath + fileSysName;
				parentFileDir = new File(tempFileDir);
				if (!parentFileDir.exists()) {
					parentFileDir.mkdirs();
				}
				// 分片处理时，前台会多次调用上传接口，每次都会上传文件的一部分到后台
				tempPartFile = new File(parentFileDir, realname + "_" + chunk + ".part");
				FileUtils.copyInputStreamToFile(tempFileItem.getInputStream(),tempPartFile);
				// 是否全部上传完成
				// 所有分片都存在才说明整个文件上传完成
				uploadDone = true;
				for (int i = 0; i < chunks; i++) {
					partFile = new File(parentFileDir, realname + "_" + i + ".part");
					if (!partFile.exists()) {
						uploadDone = false;
					}
				}
				// 所有分片文件都上传完成
				// 将所有分片文件合并到一个文件中
				if (true==uploadDone) {
					// 得到 destTempFile 就是最终的文件
					destTempFile = new File(filePath, realname);
					for (int i = 0; i < chunks; i++) {
						destTempfos=null;
						partFile = new File(parentFileDir, realname + "_" + i + ".part");
						try
						{
							destTempfos = new FileOutputStream(destTempFile, true);
							// 遍历"所有分片文件"到"最终文件"中
							FileUtils.copyFile(partFile, destTempfos);
						}
						catch(Exception ee)
						{
							log.error(ee.toString(),ee);
						}
						finally{
							IOUtils.closeQuietly(destTempfos);
						}
					}
					files=new File[1];
					files[0]=destTempFile;
					showObj=new Object[1];
					showObj[0]=pathId;
					strShowSql="SELECT path FROM sc_filepath WHERE id=?";
					listShow=commonService.queryListSql("com", strShowSql, showObj);
					if(null!=listShow && 1==listShow.size())
					{
						mapShowInfo=listShow.get(0);
						strPathInfo=mapShowInfo.get("path").toString();
						ftpUtil.uploadFile(files, "/", "/updatefile"+strPathInfo);
						if("0".equals(strMultiple)==true)
						{
							delObj=new Object[1];
							delObj[0]=groupId;
							strDelSql="DELETE sc_file FROM sc_file WHERE groupID=?";
							commonService.execNoListSql("com", strDelSql, delObj);
						}
						// 删除临时目录中的分片文件
						strKeyInfo=commonService.getKeybyTabCol("com", "sc_form", "id");
						insertObj=new Object[9];
						insertObj[0]=strKeyInfo;
						insertObj[1]=pathId;
						insertObj[2]=fileName;
						insertObj[3]=realname;
						insertObj[4]="/updatefile"+strPathInfo+realname;
						insertObj[5]=filePath+realname;
						insertObj[6]=strType;
						insertObj[7]=groupId;
						insertObj[8]=dt;
						commonService.insertObj("com", "sc_file", "id,pathId,org_name,desc_name,desc_fullpath,read_filepath,file_type,groupID,createtime", insertObj);
					}
					FileUtils.deleteDirectory(parentFileDir);
					FileUtils.deleteQuietly(destTempFile);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
