package com.common.unit;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import org.apache.el.ExpressionFactoryImpl;
import org.apache.jasper.el.ELContextImpl;
import org.apache.jasper.runtime.ProtectedFunctionMapper;
import org.apache.log4j.Logger;
import org.apache.taglibs.standard.functions.Functions;

public class HtmlUnit {
	
	/** 
     * prefix of ascii string of native character 
     */  
    private static String PREFIX = "\\u";
	private static Logger log = Logger.getLogger(HtmlUnit.class);
	
	public static String getValueByRequest(String strExpree,HttpServletRequest request,HttpServletResponse response){
		int iStart;
		int iEnd;
		String strReturn;
		String strTemp;
		ELContextImpl elContext;
		ExpressionFactory factory;
		ValueExpression valueExpress;
		JspFactory jspfactory;
		PageContext context;
		EmuServlet emuServlet;
		ProtectedFunctionMapper fnMapper;
		EmuServletConfig emuServletConfig;
		strReturn=String.copyValueOf(strExpree.toCharArray());
		emuServlet=new EmuServlet();
		emuServletConfig=new EmuServletConfig();
		emuServletConfig.setServletContext(request.getSession().getServletContext());
		emuServlet.setServletConfig(emuServletConfig);
		factory = new ExpressionFactoryImpl();
		jspfactory = JspFactory.getDefaultFactory();
		fnMapper = ProtectedFunctionMapper.getInstance();
		fnMapper.mapFunction("fn:contains", Functions.class, "contains", new Class[] {java.lang.String.class, java.lang.String.class});
		fnMapper.mapFunction("fn:containsIgnoreCase", Functions.class, "containsIgnoreCase", new Class[] {java.lang.String.class, java.lang.String.class});
		fnMapper.mapFunction("fn:endsWith", Functions.class, "endsWith", new Class[] {java.lang.String.class, java.lang.String.class});
		fnMapper.mapFunction("fn:escapeXml", Functions.class, "escapeXml", new Class[] {java.lang.String.class});
		fnMapper.mapFunction("fn:indexOf", Functions.class, "indexOf", new Class[] {java.lang.String.class, java.lang.String.class});
		fnMapper.mapFunction("fn:join", Functions.class, "join", new Class[] {java.lang.reflect.Array.newInstance(java.lang.String.class,0).getClass(), java.lang.String.class});
		fnMapper.mapFunction("fn:length", Functions.class, "length", new Class[] {java.lang.Object.class});
		fnMapper.mapFunction("fn:replace", Functions.class, "replace", new Class[] {java.lang.String.class, java.lang.String.class, java.lang.String.class});
		fnMapper.mapFunction("fn:split", Functions.class, "split", new Class[] {java.lang.String.class, java.lang.String.class});
		fnMapper.mapFunction("fn:startsWith", Functions.class, "startsWith", new Class[] {java.lang.String.class, java.lang.String.class});
		fnMapper.mapFunction("fn:substring", Functions.class, "substring", new Class[] {java.lang.String.class, int.class, int.class});
		fnMapper.mapFunction("fn:substringAfter", Functions.class, "substringAfter", new Class[] {java.lang.String.class, java.lang.String.class});
		fnMapper.mapFunction("fn:substringBefore", Functions.class, "substringBefore", new Class[] {java.lang.String.class, java.lang.String.class});
		fnMapper.mapFunction("fn:toLowerCase", Functions.class, "toLowerCase", new Class[] {java.lang.String.class});
		fnMapper.mapFunction("fn:toUpperCase", Functions.class, "toUpperCase", new Class[] {java.lang.String.class});
		fnMapper.mapFunction("fn:trim", Functions.class, "trim", new Class[] {java.lang.String.class});
		context= jspfactory.getPageContext(emuServlet, request, response, "", true, 8*1024, true);
		elContext=(ELContextImpl)context.getELContext();
		elContext.setFunctionMapper(fnMapper);
		try
		{
			iStart=strExpree.indexOf("${");
			while(-1!=iStart){
				iEnd=strExpree.indexOf("}", iStart);
				if(iStart>0)
				{
					if("\\".equals(strExpree.charAt(iStart-1))==true){
						iStart=strExpree.indexOf("${");
						continue;
					}
				}
				if(iEnd>iStart)
				{
					strTemp=strExpree.substring(iStart, (iEnd+1)).trim();
					if("${}".equals(strTemp)==false)
					{
						valueExpress=factory.createValueExpression(elContext,strTemp,String.class);
						strReturn=strReturn.replace(strTemp, valueExpress.getValue(elContext).toString());
					}
					else
					{
						strReturn=strReturn.replace(strTemp, "");
					}
					iStart=strExpree.indexOf("${",(iEnd+1));
				}
				else
				{
					iStart=-1;
				}
			}
		}catch(Exception e)
		{
			log.error(e.toString(),e);
		}
		return strReturn;
	}
	
	public static Object[] getObejctArrByMap(String strExpree,Map<String,Object[]> mapInfo){
		Object[] objArr;
		objArr=getExpreeObjectArrByMapParam(strExpree,"${map.",mapInfo);
		if(null==objArr)
		{
			objArr=getExpreeObjectArrByMapParam(strExpree,"${head.",mapInfo);
		}
		if(null==objArr)
		{
			objArr=getExpreeObjectArrByMapParam(strExpree,"${attr.",mapInfo);
		}
		return objArr;
	}
	
	public static String getValueByMap(String strExpree,Map<String,Object[]> mapInfo){
		String strReturn;
		strReturn=getValueByMapParam(strExpree,"map.",mapInfo);
		strExpree=String.copyValueOf(strReturn.toCharArray());
		strReturn=getValueByMapParam(strExpree,"head.",mapInfo);
		strExpree=String.copyValueOf(strReturn.toCharArray());
		strReturn=getValueByMapParam(strExpree,"attr.",mapInfo);
		return strReturn;
	}
	
	private static Object[] getExpreeObjectArrByMapParam(String strExpree,String strParam,Map<String,Object[]> mapInfo){
		int iStart;
		int iEnd;
		Object[] objList;
		String strTemp;
		objList=null;
		try
		{
			iStart=strExpree.toLowerCase().indexOf(strParam);
			while(-1!=iStart){
				iEnd=strExpree.indexOf("}", iStart);
				if(iStart>0)
				{
					if("\\".equals(strExpree.charAt(iStart-1))==true){
						iStart=strExpree.toLowerCase().indexOf(strParam);
						continue;
					}
				}
				if(iEnd>iStart)
				{
					strTemp=strExpree.substring(iStart, (iEnd+1)).trim();
					objList=mapInfo.get(strTemp.substring(2, strTemp.length()-1));
					if(null!=objList)
					{
						break;
					}
					iStart=strExpree.toLowerCase().indexOf(strParam,(iEnd+1));
				}
				else
				{
					iStart=-1;
				}
			}
		}catch(Exception e)
		{
			log.error(e.toString(),e);
		}
		return objList;
	}
	
	private static String getValueByMapParam(String strExpree,String strParam,Map<String,Object[]> mapInfo){
		int iStart;
		int iEnd;
		String strReturn;
		String strTemp;
		String strValue;
		strReturn=String.copyValueOf(strExpree.toCharArray());
		try
		{
			iStart=strExpree.indexOf("${");
			while(-1!=iStart){
				iEnd=strExpree.indexOf("}", iStart);
				if(iStart>0)
				{
					if("\\".equals(strExpree.charAt(iStart-1))==true){
						iStart=strExpree.indexOf("${");
						continue;
					}
				}
				if(iEnd>iStart)
				{
					strTemp=strExpree.substring(iStart, (iEnd+1)).trim();
					strValue=getReturnValue(strTemp,strParam,mapInfo);
					strReturn=strReturn.replace(strTemp, strValue);
					iStart=strExpree.indexOf("${",(iEnd+1));
				}
				else
				{
					iStart=-1;
				}
			}
		}catch(Exception e)
		{
			log.error(e.toString(),e);
		}
		return strReturn;
	}
	
	private static String getReturnValue(String strExpree,String strParam,Map<String,Object[]> mapInfo){
		int iStart;
		int iEnd;
		String regex;
		Pattern pattern;
		Matcher match;
		Object[] objList;
		String strTempInfo;
		String strTemp;
		objList=null;
		regex=strParam+"[\\w]+";
		pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		match=pattern.matcher(strExpree);
		while(true==match.find()){
			strTemp="";
			iStart=match.start();
			iEnd=match.end();
			strTempInfo=strExpree.substring(iStart, iEnd);
			objList=mapInfo.get(strTempInfo);
			if(null!=objList)
			{
				strTemp="'"+getStringByObjArr(objList)+"'";
			}
			strExpree=strExpree.substring(0, iStart)+strTemp+strExpree.substring(iEnd,strExpree.length());
			match=pattern.matcher(strExpree);
		}
		return strExpree;
	}
	
	private static String getStringByObjArr(Object[] objArr)
	{
		String strBack;
		strBack="";
		for(int i=0;i<objArr.length;i++)
		{
			strBack+=objArr[i].toString()+",";
		}
		if(objArr.length>0)
		{
			strBack=strBack.substring(0, strBack.length()-1);
		}
		return strBack;
	}
  
    /** 
     * Native to ascii string. It's same as execut native2ascii.exe. 
     *  
     * @param str 
     *            native string 
     * @return ascii string 
     */  
    public static String native2Ascii(String str) {  
        char[] chars = str.toCharArray();  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < chars.length; i++) {  
            sb.append(char2Ascii(chars[i]));  
        }  
        return sb.toString();  
    }  
  
    /** 
     * Native character to ascii string. 
     *  
     * @param c 
     *            native character 
     * @return ascii string 
     */  
    private static String char2Ascii(char c) {  
        if (c > 255) {  
            StringBuilder sb = new StringBuilder();  
            sb.append(PREFIX);  
            int code = (c >> 8);  
            String tmp = Integer.toHexString(code);  
            if (tmp.length() == 1) {  
                sb.append("0");  
            }  
            sb.append(tmp);  
            code = (c & 0xFF);  
            tmp = Integer.toHexString(code);  
            if (tmp.length() == 1) {  
                sb.append("0");  
            }  
            sb.append(tmp);  
            return sb.toString();  
        } else {  
            return Character.toString(c);  
        }  
    }  
  
    /** 
     * Ascii to native string. It's same as execut native2ascii.exe -reverse. 
     *  
     * @param str 
     *            ascii string 
     * @return native string 
     */  
    public static String ascii2Native(String str) {  
        StringBuilder sb = new StringBuilder();  
        int begin = 0;  
        int index = str.indexOf(PREFIX);  
        while (index != -1) {  
            sb.append(str.substring(begin, index));  
            sb.append(ascii2Char(str.substring(index, index + 6)));  
            begin = index + 6;  
            index = str.indexOf(PREFIX, begin);  
        }  
        sb.append(str.substring(begin));  
        return sb.toString();  
    }  
  
    /** 
     * Ascii to native character. 
     *  
     * @param str 
     *            ascii string 
     * @return native character 
     */  
    private static char ascii2Char(String str) {  
        if (str.length() != 6) {  
            throw new IllegalArgumentException(  
                    "Ascii string of a native character must be 6 character.");  
        }  
        if (!PREFIX.equals(str.substring(0, 2))) {  
            throw new IllegalArgumentException(  
                    "Ascii string of a native character must start with \"\\u\".");  
        }  
        String tmp = str.substring(2, 4);  
        int code = Integer.parseInt(tmp, 16) << 8;  
        tmp = str.substring(4, 6);  
        code += Integer.parseInt(tmp, 16);  
        return (char) code;  
    }
}
