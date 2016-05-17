package demo;  

import org.json.JSONObject;

import com.api.NCApi;
  
public class OpenapiDemo {  
  
	public static final String gateway_url = "http://gw.api.yonyou.com/gateway/default";
	
	//尝试https协议的话，可以修改为下面的地址
	//public static final String gateway_url = "https://gw.api.yonyou.com/gateway/default";
	
	
	public static final String resultType = "json";
	
	public static final String appKey = "UtwLQ";	
	public static final String accessToken = "2221f1ba-c563-4d8e-8e69-205a71a57ec8";
	
	
    /**
     * @throws InterruptedException  
     * @Description: TODO 
     * @param @param args     
     * @return      
     * @throws  
     */  
    public static void main(String[] args) throws InterruptedException {  
    	
        OpenapiDemo demo = new OpenapiDemo();
        demo.init();   
        try {  	        	
        	System.out.println("查询服务列表");  		
        	JSONObject json = new JSONObject();
    		json.put("projectname", "demo");
    		json.put("isnc", 1);
    		String res = NCApi.getInstance().sendPost("/openapi/api/query", json.toString());
    		System.out.println("返回结果"+res);
    		JSONObject data = handleResult(res);
    		System.out.println("服务列表数据："+data);
    		
    		System.out.println("查询资源明细");  		
        	json = new JSONObject();
    		json.put("projectname", "demo");
    		json.put("resourcename", "user/login");
    		res = NCApi.getInstance().sendPost("/openapi/resource/query", json.toString());
    		System.out.println("返回结果"+res);
    		data = handleResult(res);
    		System.out.println("服务列表数据："+data);
        
        } catch (Exception e) {       
            e.printStackTrace();       
        }   
    } 
    
    /**
     * 初始化信息
     */
    public void init(){
    	NCApi.getInstance().setGateway_url(gateway_url);
        NCApi.getInstance().setResultType(resultType);
        NCApi.getInstance().setAppKey(appKey);
        NCApi.getInstance().setAccessToken(accessToken);
    }
    
   
	/**
	 * 返回结果处理
	 * @param jsonres
	 * @return
	 * @throws Exception
	 */
	public static JSONObject handleResult(String jsonres)throws Exception{
		JSONObject jsonObject = new JSONObject(jsonres);
		String statuscode = jsonObject.getString("statuscode");
		if(!"0".equalsIgnoreCase(statuscode)){
			String errormsg = jsonObject.getString("errormsg");
			String errordetailmsg = jsonObject.getString("errordetailmsg");
			throw new Exception("错误编码："+statuscode+"；错误信息："+errormsg+";详细错误信息："+errordetailmsg);			
		}
		return jsonObject.getJSONObject("data");
	}
}  