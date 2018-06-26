package com.common.unit;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.codec.binary.Base64;


/**
 * 编码工具类
 * 1.将byte[]转为各种进制的字符串
 * 2.base 64 encode
 * 3.base 64 decode
 * 4.获取byte[]的md5值
 * 5.获取字符串md5值
 * 6.结合base64实现md5加密
 * 7.AES加密
 * 8.AES加密为base 64 code
 * 9.AES解密
 * 10.将base 64 code AES解密
 * @author uikoo9
 * @version 0.0.7.20140601
 */
public class EncodeUtil {
	
	private static final String passwd="eb4e77b0c58611e4bd99a01d48d28732";
	
	/**
	 * 将byte[]转为各种进制的字符串
	 * @param bytes byte[]
	 * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
	 * @return 转换后的字符串
	 */
	public static String binary(byte[] bytes, int radix){
		return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
	}
	
	/**
	 * base 64 encode
	 * @param bytes 待编码的byte[]
	 * @return 编码后的base 64 code
	 */
	public static String base64Encode(byte[] bytes){
		return Base64.encodeBase64String(bytes);
	}
	
	/**
	 * base 64 decode
	 * @param base64Code 待解码的base 64 code
	 * @return 解码后的byte[]
	 * @throws Exception
	 */
	public static byte[] base64Decode(String base64Code) throws Exception{
		return StringUtils.isEmpty(base64Code) ? null : Base64.decodeBase64(base64Code);
	}
	
	/**
	 * 获取byte[]的md5值
	 * @param bytes byte[]
	 * @return md5
	 * @throws Exception
	 */
	public static byte[] md5(byte[] bytes) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(bytes);
		return md.digest();
	}
	
	/**
	 * 获取字符串md5值
	 * @param msg 
	 * @return md5
	 * @throws Exception
	 */
	public static byte[] md5(String msg) throws Exception {
		return StringUtils.isEmpty(msg) ? null : md5(msg.getBytes());
	}
	
	/**
	 * 结合base64实现md5加密
	 * @param msg 待加密字符串
	 * @return 获取md5后转为base64
	 * @throws Exception
	 */
	public static String md5Encrypt(String msg) throws Exception{
		return StringUtils.isEmpty(msg) ? null : base64Encode(md5(msg));
	}
	
	/**
	 * AES加密
	 * @param content 待加密的内容
	 * @param encryptKey 加密密钥
	 * @return 加密后的byte[]
	 * @throws Exception
	 */
	public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(encryptKey.getBytes()));
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
		return cipher.doFinal(content.getBytes("utf-8"));
	}
	
	/**
	 * AES加密为base 64 code
	 * @param content 待加密的内容
	 * @param encryptKey 加密密钥
	 * @return 加密后的base 64 code
	 * @throws Exception
	 */
	public static String aesEncrypt(String content, String encryptKey) throws Exception {
		StringBuffer sb;
		String xmString;  
	    String xmlUTF8;
		sb = new StringBuffer(); 
	    sb.append(content);
	    xmString = new String(sb.toString());  
	    xmlUTF8 = Encode(xmString);
		return base64Encode(aesEncryptToBytes(xmlUTF8, encryptKey));
	}
	
	/**
	 * AES解密
	 * @param encryptBytes 待解密的byte[]
	 * @param decryptKey 解密密钥
	 * @return 解密后的String
	 * @throws Exception
	 */
	public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
		String strResult;
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(decryptKey.getBytes()));
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
		byte[] decryptBytes = cipher.doFinal(encryptBytes);
		strResult=new String(decryptBytes);
		return Decode(strResult);
	}
	
	/**
	 * 将base 64 code AES解密
	 * @param encryptStr 待解密的base 64 code
	 * @param decryptKey 解密密钥
	 * @return 解密后的string
	 * @throws Exception
	 */
	public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
		return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
	}
	
	public static String Encrypt(String content) throws Exception{
		return aesEncrypt(content, passwd);
	}
	
	public static String Decrypt(String encrypt) throws Exception{
		return aesDecrypt(encrypt, passwd);
	}
	
	public static String Encode(String content){
		String result = "";  
        for(int i = 0; i < content.length(); i++) {  
            String temp = "";  
            int strInt = content.charAt(i);  
            if(strInt > 127) {  
                temp += "\\u" + Integer.toHexString(strInt);  
            } else {  
                temp = String.valueOf(content.charAt(i));  
            }
            result += temp;  
        }  
        return result;
	}
	
	public static String Decode(String content){
		StringBuilder sb = new StringBuilder();   
        int begin = 0;   
        int index = content.indexOf("\\u");   
        while (index != -1) {   
            sb.append(content.substring(begin, index));   
            sb.append(ascii2Char(content.substring(index, index + 6)));   
            begin = index + 6;   
            index = content.indexOf("\\u", begin);   
        }   
        sb.append(content.substring(begin));   
        return sb.toString();   
    }
	
	private static char ascii2Char(String str) {   
        if (str.length() != 6) {   
            throw new IllegalArgumentException(   
                    "Ascii string of a native character must be 6 character.");   
        }   
        if (!"\\u".equals(str.substring(0, 2))) {   
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
