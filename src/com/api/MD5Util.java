package com.api;

import java.security.MessageDigest;

public class MD5Util {
	public static String get16String(String str){
		if(str==null || str.trim().equals(""))
			return null;
		if(str.length()<24){
			return str;
		}
		return str.substring(8, 24);
	}
	
	public static String MD5Encrypt(String inStr) {
		MessageDigest md5 = null;  
		try{  
        	md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
        	e.printStackTrace();  
            return "";  
        }  

        byte[] md5Bytes = md5.digest(inStr.getBytes());  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
        	int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
            	hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();
	} 

	public static String bytetoString(byte[] digest) {  

	    String str = "";  
	    String tempStr = "";  
	    for (int i = 1; i < digest.length; i++) {   
	     tempStr = (Integer.toHexString(digest[i] & 0xff));   
	     if (tempStr.length() == 1) {    
	      str = str + "0" + tempStr;   
	     } 
	     else {    
	      str = str + tempStr;   
	     }  
	    }  
	    return str.toLowerCase();

	}

}
