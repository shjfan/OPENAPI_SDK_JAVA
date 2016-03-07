package com.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class NCApi {
	
	private static final String STR_APP_KEY="appKey";
	private static final String STR_OAUTH="oauth";
	private static final String STR_TIMESTAMP="timestamp";
	private static final String STR_RESULTTYPE="resultType";
	
	private static final String STR_UAP_DATASOURCE="uap_dataSource";
	private static final String STR_UAP_TOKEN="uap_token";
	private static final String STR_UAP_USERCODE="uap_usercode";
	
	private String store_url;
	private String appKey;
	private String accessToken;
	private String resultType;

	private String uap_dataSource;	
	private String uap_token;
	private String uap_usercode;

	private static NCApi instance = new NCApi();

	private NCApi() {

	}

	public static NCApi getInstance() {
		return instance;
	}
	
	public String sendPost(String apiurl,String postparam) throws IOException{
		return sendPost(apiurl,postparam,null);
	}
	
	public String sendPost(String apiurl,String postparam,Map<String, String> propertys) throws IOException{
		if(propertys==null){
			propertys =new HashMap<String,String>(); 
		}
		return send(apiurl,new HashMap<String,String>(),postparam,propertys,"POST");
	}
	
	public String sendGet(String apiurl,Map<String, String> getparams) throws IOException{
		return sendGet(apiurl,getparams,null);
	}
	
	public String sendGet(String apiurl,Map<String, String> getparams,Map<String, String> propertys) throws IOException{
		if(getparams==null){
			getparams =new HashMap<String,String>(); 
		}
		if(propertys==null){
			propertys =new HashMap<String,String>(); 
		}
		return send(apiurl,getparams,null,propertys,"GET");
	}
	
	private  String send(String apiurl,Map<String, String> getparams,String postparam,  
            Map<String, String> propertys,String method) throws IOException{
		addNC(getparams, propertys);
		
		HttpRequester request = new HttpRequester();  
        request.setDefaultContentEncoding("utf-8"); 
        HttpRespons httpRespons= request.send(getStore_url()+apiurl, method, getparams,postparam, propertys);
		return httpRespons.content;
        
	}
	
	private void addNC(Map<String, String> getparams,
            Map<String, String> propertys){	
		String timestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
		String oauth = MD5Util.MD5Encrypt(appKey+timestamp+accessToken);
		propertys.put(STR_APP_KEY, getAppKey());
		propertys.put(STR_OAUTH, oauth);
		
		propertys.put(STR_UAP_DATASOURCE, getUap_dataSource());
		propertys.put(STR_UAP_TOKEN, getUap_token());
		propertys.put(STR_UAP_USERCODE, getUap_usercode());
		
		propertys.put("content-type", "application/json");
		
		getparams.put(STR_RESULTTYPE, getResultType());
		getparams.put(STR_TIMESTAMP, timestamp);		
	}
	
	public String getStore_url() {
		return store_url;
	}

	public void setStore_url(String store_url) {
		this.store_url = store_url;
	}
	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	public String getUap_dataSource() {
		return uap_dataSource;
	}

	public void setUap_dataSource(String uap_dataSource) {
		this.uap_dataSource = uap_dataSource;
	}

	public String getUap_token() {
		return uap_token;
	}

	public void setUap_token(String uap_token) {
		this.uap_token = uap_token;
	}

	public String getUap_usercode() {
		return uap_usercode;
	}

	public void setUap_usercode(String uap_usercode) {
		this.uap_usercode = uap_usercode;
	}
}
