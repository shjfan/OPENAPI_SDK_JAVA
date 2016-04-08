package com.api;

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
	
	//开放平台网关地址
	private String gateway_url;
	
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
	
	/**
	 * post请求
	 * @param apiurl api服务的url
	 * @param postparam post参数（json格式）
	 * @return
	 * @throws Exception 
	 */
	public String sendPost(String apiurl,String postparam) throws Exception{
		Map<String, String> propertys =new HashMap<String,String>();
		propertys.put("content-type", "application/json");
		return sendPost(apiurl,postparam,propertys);
	}
	
	/**
	 * post请求
	 * @param apiurl api服务url
	 * @param postparam post参数（json格式）
	 * @param propertys header参数
	 * @return
	 * @throws Exception 
	 */
	public String sendPost(String apiurl,String postparam,Map<String, String> propertys) throws Exception{
		if(propertys==null){
			propertys =new HashMap<String,String>(); 
		}
		return send(apiurl,new HashMap<String,String>(),postparam,propertys,"POST",false);
	}
	
	/**
	 * get请求
	 * @param apiurl api服务url
	 * @param getparams body参数
	 * @return
	 * @throws Exception 
	 */
	public String sendGet(String apiurl,Map<String, String> getparams) throws Exception{
		return sendGet(apiurl,getparams,null);
	}
	public String sendGet(String apiurl,Map<String, String> getparams,Map<String, String> propertys) throws Exception{
		if(getparams==null){
			getparams =new HashMap<String,String>(); 
		}
		if(propertys==null){
			propertys =new HashMap<String,String>(); 
		}
		return send(apiurl,getparams,null,propertys,"GET",false);
	}
	/**
	 * post直接请求
	 * 即开发平台不经过任何权限、安全的验证
	 * 不需要传递开放平台所需的参数，只需传递实际api所需参数
	 * @param apiurl api服务url
	 * @param postparam post参数（json格式）
	 * @return
	 * @throws Exception 
	 */
	public String sendPostDirect(String apiurl,String postparam) throws Exception{
		Map<String, String> propertys =new HashMap<String,String>();
		propertys.put("content-type", "application/json");
		return sendPostDirect(apiurl,postparam,propertys);
	}
	/**
	 * post直接请求
	 * 即开发平台不经过任何权限、安全的验证
	 * 不需要传递开放平台所需的参数，只需传递实际api所需参数
	 * @param apiurl api服务url
	 * @param postparam post参数（json格式）
	 * @param propertys header参数
	 * @return
	 * @throws Exception 
	 */
	public String sendPostDirect(String apiurl,String postparam,Map<String, String> propertys) throws Exception{
		if(propertys==null){
			propertys =new HashMap<String,String>(); 
		}
		return send(apiurl,new HashMap<String,String>(),postparam,propertys,"POST",true);
	}
	/**
	 * get直接请求
	 * 即开发平台不经过任何权限、安全的验证
	 * 不需要传递开放平台所需的参数，只需传递实际api所需参数
	 * @param apiurl api服务url
	 * @param getparams body参数
	 * @return
	 * @throws Exception 
	 */
	public String sendGetDirect(String apiurl,Map<String, String> getparams) throws Exception{
		return sendGetDirect(apiurl,getparams,null);
	}
	
	/**
	 * get直接请求
	 * 即开发平台不经过任何权限、安全的验证
	 * 不需要传递开放平台所需的参数，只需传递实际api所需参数
	 * @param apiurl api服务url
	 * @param getparams body参数
	 * @param propertys header参数
	 * @return
	 * @throws Exception 
	 */
	public String sendGetDirect(String apiurl,Map<String, String> getparams,Map<String, String> propertys) throws Exception{
		if(getparams==null){
			getparams =new HashMap<String,String>(); 
		}
		if(propertys==null){
			propertys =new HashMap<String,String>(); 
		}
		return send(apiurl,getparams,null,propertys,"GET",true);
	}
	
	/**
	 * 发送请求
	 * @param apiurl api服务url
	 * @param getparams get的body参数
	 * @param postparam post的body参数（json格式）
	 * @param propertys header参数
	 * @param method 请求方法get或post
	 * @param isdirect 是否直接请求
	 * @return
	 * @throws Exception 
	 */
	private  String send(String apiurl,Map<String, String> getparams,String postparam,  
            Map<String, String> propertys,String method,boolean isdirect) throws Exception{
		addNCParams(getparams, propertys,isdirect);
		if(isHttps(getGateway_url())){
			return HttpsRequest.send(getGateway_url()+apiurl, method,getparams, postparam,propertys);
		}else{
			HttpRequester request = new HttpRequester();  
	        request.setDefaultContentEncoding("utf-8"); 
	        HttpRespons httpRespons= request.send(getGateway_url()+apiurl, method, getparams,postparam, propertys);
			return httpRespons.content;
		}        
	}
	
	/**
	 * 判断是否https请求
	 * @param urlstr
	 * @return
	 */
	private boolean isHttps(String urlstr){
		if(urlstr!=null){
			if(urlstr.startsWith("https")||urlstr.startsWith("HTTPS")){
				return true;
			}
		}
		
		
		return false;
	}
	
	/**
	 * 增加默认参数
	 * @param getparams get的body参数
	 * @param propertys header参数
	 * @param isdirect 是否直接请求
	 */
	private void addNCParams(Map<String, String> getparams,
            Map<String, String> propertys,boolean isdirect){
		if(!isdirect){
			/**
			 * 可以用来防止请求重放
			 * 考虑网络延迟,可通过截取长度来区分
			 * 如10位，表示1秒以内，9位表示10秒以内，8位表示100秒以内
			 * 功能在开发中
			 */
			String timestamp = String.valueOf(System.currentTimeMillis()).substring(0, 6);
			
			
			String oauth = MD5Util.MD5Encrypt(appKey+timestamp+accessToken);
			propertys.put(STR_APP_KEY, getAppKey());
			propertys.put(STR_OAUTH, oauth);
			getparams.put(STR_RESULTTYPE, getResultType());
			getparams.put(STR_TIMESTAMP, timestamp);
		}
		propertys.put(STR_UAP_DATASOURCE, getUap_dataSource());
		propertys.put(STR_UAP_TOKEN, getUap_token());
		propertys.put(STR_UAP_USERCODE, getUap_usercode());
		
				
	}
	
	public String getGateway_url() {
		return gateway_url;
	}

	public void setGateway_url(String gateway_url) {
		this.gateway_url = gateway_url;
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
