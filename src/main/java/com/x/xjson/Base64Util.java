/**
 * 
 * File Name: Base64Util.java 
 * Package Name: com.x.xijson
 * Date: 2016年5月20日 上午10:29:17
 */
package com.x.xjson;

import java.io.UnsupportedEncodingException;

/**
 * @author x
 *
 */
public class Base64Util {
	public static String decode(String s) {  
		if (s == null) return null;  
		try { 
			@SuppressWarnings("restriction")
			byte[] b = new sun.misc.BASE64Decoder().decodeBuffer(s); 
			return new String(b, "UTF-8");
		} catch (Exception e) { 
			return null;
		} 
    }
	
	// 编码
    @SuppressWarnings("restriction")
	public static String encode(String s){
        if (s == null) return null;
        try {
			//return new sun.misc.BASE64Encoder().encode(s.getBytes("UTF-8"));        	
        	s = new sun.misc.BASE64Encoder().encode(s.getBytes("UTF-8"));
        	return s.replaceAll("[\\s*\t\n\r]", "");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
    }
    
    public static void main(String[] args) {
		String s = "{\"FUNC\":\"GetOrRefreshVisionList\",\"SERV_CENTER_ID\":\"F2\",\"VISIONROOMINFOID\":\"a683320f-9905-4714-9767-ab6a5137e113\"}";
		s = encode(s); 
		System.out.println(s);
	}
}
