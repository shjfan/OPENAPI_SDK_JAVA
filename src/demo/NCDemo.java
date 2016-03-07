package demo;  

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.api.NCApi;
import com.api.NCApiConst;
  
public class NCDemo {  
  
	//public static final String store_url = "http://gw.api.yonyou.com/gateway";
	public static final String store_url = "http://127.0.0.1:9080/gateway";
	public static final String resultType = "json";
	
	public static final String appKey = "Xb9tE";	
	public static final String accessToken = "1444c11d-2cd2-4bd1-9dc7-69a792c92c18";
	
	public static final String uap_dataSource="nc65user";	
	public static final String uap_usercode="licc";	
	
	public static String uap_token="123456";	
	public static String uap_userpassword="nc1234";
	
	
    /** 
     * @Description: TODO 
     * @param @param args     
     * @return      
     * @throws  
     */  
    public static void main(String[] args) {  
    	
        NCDemo demo = new NCDemo();
        demo.init();   
        try {     
        	uap_token = demo.loginNC();
        	NCApi.getInstance().setUap_token(uap_token);       	
        	
        	System.out.println("查询销售订单");  		
    		System.out.println("查询销售组织T300502下的销售订单SO302015040700000028");
    		Map<String, String> getparams = new HashMap<String, String>();
    		getparams.put("orgcode", "T300502");
    		getparams.put("vbillcode", "SO302015040700000028");
    		String res = NCApi.getInstance().sendGet(NCApiConst.URL_SO_1_QUERYORDER, getparams);
    		String msg= new JSONObject(res).getString("message");
    		System.out.println("返回结果"+msg);
        
        } catch (Exception e) {       
            e.printStackTrace();       
        }   
    } 
    
    /**
     * 初始化信息
     */
    public void init(){
    	NCApi.getInstance().setStore_url(store_url);
        NCApi.getInstance().setResultType(resultType);
        NCApi.getInstance().setAppKey(appKey);
        NCApi.getInstance().setAccessToken(accessToken);
        
        NCApi.getInstance().setUap_dataSource(uap_dataSource);
        NCApi.getInstance().setUap_token(uap_token);
        NCApi.getInstance().setUap_usercode(uap_usercode);
    }
    
    /**
     * 登录NC，获取uap_token
     * @return
     */
	public String loginNC() throws Exception {
		String uap_token = null;
		System.out.println("开始登录NC");
		
		JSONObject json = new JSONObject();
		json.put("usercode", uap_usercode);
		json.put("pwd", uap_userpassword);		
		String postparam = json.toString();
		
		String res = NCApi.getInstance().sendPost(
				NCApiConst.URL_SCMBASE_1_USERLOGIN, postparam);
		/**
		 * 返回结果的格式还有待调整
		 * 现版本是在NCapi返回结果外封装了一层，用来记录执行状态
		 * 返货结果中的message为NCapi的实际返回结果
		 */
		System.out.println("登录NC返回结果：" + res);
		JSONObject jsonObject = new JSONObject(res);
		uap_token = new JSONObject(jsonObject.getString("message"))
				.getString("uap_token");
		if (uap_token == null || uap_token.trim().length() <= 0) {
			throw new Exception("登录失败,返回uap_token为空!");
		}
		System.out.println("uap_token="+uap_token);
		return uap_token;
	}
}  