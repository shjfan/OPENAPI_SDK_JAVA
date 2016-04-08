package com.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsRequest {
	private static final String METHOD_POST = "POST";  
	private static final String METHOD_GET = "GET"; 
    private static final String DEFAULT_CHARSET = "utf-8";  
 
    public static String send(String url,String method,Map<String, String> getparameters,String postparameter,Map<String, String> propertys) throws Exception{
    	return send(url,method,getparameters,postparameter,propertys,10000,10000);
    }
    
    public static String send(String url,String method,Map<String, String> getparameters,String postparameter,Map<String, String> propertys, int connectTimeout, int readTimeout) throws Exception {  
        HttpsURLConnection conn = null;  
        OutputStream out = null;  
        String rsp = null;  
        try {  
            try{  
                SSLContext ctx = SSLContext.getInstance("TLS");  
                ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());  
                SSLContext.setDefault(ctx);  
                
	                if (getparameters != null&&getparameters.size()>0) {  
	                    StringBuffer param = new StringBuffer();  
	                    int i = 0;  
	                    for (String key : getparameters.keySet()) {  
	                        if (i == 0)  
	                            param.append("?");  
	                        else  
	                            param.append("&");  
	                        param.append(key).append("=").append(getparameters.get(key));  
	                        i++;  
	                    }  
	                    url += param;  
	                }
                
                
                conn = getConnection(new URL(url), method, propertys);   
                conn.setHostnameVerifier(new HostnameVerifier() {  
                   
                    public boolean verify(String hostname, SSLSession session) {  
                        return true;  
                    }  
                });  
                conn.setConnectTimeout(connectTimeout);  
                conn.setReadTimeout(readTimeout);  
            }catch(Exception e){  
                throw e;  
            }  
            try{  
            	if(postparameter!=null&&postparameter.trim().length()>0){
	                out = conn.getOutputStream();  
	                out.write(postparameter.getBytes());  
            	}
                rsp = getResponseAsString(conn);  
            }catch(IOException e){  
                throw e;  
            }  
              
        }finally {  
            if (out != null) {  
                out.close();  
            }  
            if (conn != null) {  
                conn.disconnect();  
            }  
        }  
          
        return rsp;  
    }  
  
    private static class DefaultTrustManager implements X509TrustManager {  
  
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}  
  
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}  
  
        public X509Certificate[] getAcceptedIssuers() {  
            return null;  
        }  
  
    }  
      
    private static HttpsURLConnection getConnection(URL url, String method, Map<String, String> propertys)  
            throws IOException {  
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();  
        conn.setRequestMethod(method);  
        conn.setDoInput(true);  
        conn.setDoOutput(true);  
        conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");  
        conn.setRequestProperty("User-Agent", "stargate");  
        if (propertys != null)  {
            for (String key : propertys.keySet()) {  
            	conn.addRequestProperty(key, propertys.get(key));  
            }  
        }
        
        return conn;  
    }  
  
    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {  
        String charset = getResponseCharset(conn.getContentType());  
        InputStream es = conn.getErrorStream();  
        if (es == null) {  
            return getStreamAsString(conn.getInputStream(), charset);  
        } else {  
            String msg = getStreamAsString(es, charset);  
            if (msg==null) {  
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());  
            } else {  
                throw new IOException(msg);  
            }  
        }  
    }  
  
    private static String getStreamAsString(InputStream stream, String charset) throws IOException {  
        try {  
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));  
            StringWriter writer = new StringWriter();  
  
            char[] chars = new char[256];  
            int count = 0;  
            while ((count = reader.read(chars)) > 0) {  
                writer.write(chars, 0, count);  
            }  
  
            return writer.toString();  
        } finally {  
            if (stream != null) {  
                stream.close();  
            }  
        }  
    }  
  
    private static String getResponseCharset(String ctype) {  
        String charset = DEFAULT_CHARSET;  
  
        if (ctype!=null) {  
            String[] params = ctype.split(";");  
            for (String param : params) {  
                param = param.trim();  
                if (param.startsWith("charset")) {  
                    String[] pair = param.split("=", 2);  
                    if (pair.length == 2) {  
                        if (pair[1]!=null) {  
                            charset = pair[1].trim();  
                        }  
                    }  
                    break;  
                }  
            }  
        }  
  
        return charset;  
    }  
}
