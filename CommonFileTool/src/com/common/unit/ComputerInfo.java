package com.common.unit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ComputerInfo {
	
	//通过截取cmd流方式得到计算机的配置信息(不好)  
	public static List<String> getIpAddress(){  
		Process p;
		String s;
		StringBuffer sb;
		BufferedReader br;
		List<String> address;
		InputStream inputStream;
		p=null;
		address = new ArrayList<String>();  
		try{  
			p = new ProcessBuilder("ipconfig","/all").start();  
		}catch(Exception e){  
			log.error(e.getMessage(),e); 
		}  
		sb = new StringBuffer();  
		//读取进程输出值  
		inputStream = p.getInputStream();
		br = new BufferedReader(new InputStreamReader(inputStream));  
	    s = "";  
	    try{
	    	while((s=br.readLine())!=null){
	    		sb.append(s+"\n");  
	        }  
	    }catch(Exception e){
	    	log.error(e.getMessage(),e); 
	    }finally{  
	    	try{  
	    		inputStream.close();  
	    	}catch(Exception e){  
	    		log.error(e.getMessage(),e); 
	    	}  
	    }
	    log.info(s);
	    return address;  
	}
	
	public static Map<String,String> getIpconfig(){  
		Map<String,String> map;
		map = System.getenv();  
		log.info(map.get("USERNAME"));//获取用户名  
		log.info(map.get("COMPUTERNAME"));//获取计算机名  
		log.info(map.get("USERDOMAIN"));//获取计算机域名  
		return map;
	}
	
	//得到计算机的ip地址和mac地址  
	public static void getConfig(){
		byte[] mac;
		String sIP;
		String sMAC;
		Formatter formatter;
		InetAddress address;
		NetworkInterface ni;
		formatter = new Formatter(); 
		try{
			address = InetAddress.getLocalHost();
			ni = NetworkInterface.getByInetAddress(address);
	        mac = ni.getHardwareAddress();
	        sIP = address.getHostAddress();
	        sMAC = "";
	        for (int i = 0; i < mac.length; i++) {  
	        	sMAC = formatter.format(Locale.getDefault(), "%02X%s", mac[i],  
	        			(i < mac.length - 1) ? "-" : "").toString();
	        }
	        log.info("IP：" + sIP);
	        log.info("MAC：" + sMAC);  
		}catch(Exception e){  
			log.error(e.getMessage(),e); 
		}  
	}
	
	//得到计算机的ip,名称,操作系统名称,操作系统版本  
	public static void Config(){
		String ip;
		String hostName;
		InetAddress addr;
		Properties props;
		try{
			addr = InetAddress.getLocalHost();
			ip=addr.getHostAddress().toString(); //获取本机ip
			hostName=addr.getHostName().toString(); //获取本机计算机名称  
	        log.info("本机IP："+ip+"\n本机名称:"+hostName);
	        props=System.getProperties();  
	        log.info("操作系统的名称："+props.getProperty("os.name"));  
	        log.info("操作系统的版本："+props.getProperty("os.version"));   
		}catch(Exception e){  
			log.error(e.getMessage(),e);
		}  
	}  

	//java环境  
	public static void all(){
		Properties props;
		props=System.getProperties();  
		log.info("Java的运行环境版本："+props.getProperty("java.version"));  
		log.info("Java的运行环境供应商："+props.getProperty("java.vendor"));  
		log.info("Java供应商的URL："+props.getProperty("java.vendor.url"));  
		log.info("Java的安装路径："+props.getProperty("java.home"));  
		log.info("Java的虚拟机规范版本："+props.getProperty("java.vm.specification.version"));  
		log.info("Java的虚拟机规范供应商："+props.getProperty("java.vm.specification.vendor"));  
		log.info("Java的虚拟机规范名称："+props.getProperty("java.vm.specification.name"));  
		log.info("Java的虚拟机实现版本："+props.getProperty("java.vm.version"));  
		log.info("Java的虚拟机实现供应商："+props.getProperty("java.vm.vendor"));  
		log.info("Java的虚拟机实现名称："+props.getProperty("java.vm.name"));  
	    log.info("Java运行时环境规范版本："+props.getProperty("java.specification.version"));  
	    log.info("Java运行时环境规范供应商："+props.getProperty("java.specification.vender"));  
	    log.info("Java运行时环境规范名称："+props.getProperty("java.specification.name"));  
	    log.info("Java的类格式版本号："+props.getProperty("java.class.version"));  
	    log.info("Java的类路径："+props.getProperty("java.class.path"));  
	    log.info("加载库时搜索的路径列表："+props.getProperty("java.library.path"));  
	    log.info("默认的临时文件路径："+props.getProperty("java.io.tmpdir"));  
	    log.info("一个或多个扩展目录的路径："+props.getProperty("java.ext.dirs"));  
	    log.info("操作系统的名称："+props.getProperty("os.name"));  
	    log.info("操作系统的构架："+props.getProperty("os.arch"));  
	    log.info("操作系统的版本："+props.getProperty("os.version"));  
	    log.info("文件分隔符："+props.getProperty("file.separator"));//在 unix 系统中是＂／＂ log.info("路径分隔符："+props.getProperty("path.separator"));//在 unix 系统中是＂:＂ log.info("行分隔符："+props.getProperty("line.separator"));//在 unix 系统中是＂/n＂ log.info("用户的账户名称："+props.getProperty("user.name"));  
	    log.info("用户的主目录："+props.getProperty("user.home"));  
	    log.info("用户的当前工作目录："+props.getProperty("user.dir"));  
	}
	
	public static void main(String[] args) {  
		getIpconfig();
		getIpAddress();
	}
}
