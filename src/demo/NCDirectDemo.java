package demo;  

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.api.NCApi;
import com.api.NCApiConst;
  
public class NCDirectDemo {  
  
	public static final String store_url = "http://gw.api.yonyou.com/direct/demo";
	
	public static final String uap_dataSource="nc65user";	
	public static final String uap_usercode="licc";	
	
	public static String uap_token="123456";	
	public static String uap_userpassword="nc1234";
	
	
    /**
     * @throws InterruptedException  
     * @Description: TODO 
     * @param @param args     
     * @return      
     * @throws  
     */  
    public static void main(String[] args) throws InterruptedException {  
    	
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
    		String res = NCApi.getInstance().sendGet(NCApiConst.URL_SALEORDER_BYBILLCODE, getparams);
    		System.out.println("返回结果"+res);
    		String data = handleResult(res);
    		System.out.println("订单数据："+data);
        
        } catch (Exception e) {       
            e.printStackTrace();       
        }   
    } 
    
    /**
     * 初始化信息
     */
    public void init(){
        
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
				NCApiConst.URL_USER_LOGIN, postparam);
		System.out.println("登录NC返回结果：" + res);		
		String data = handleResult(res);
		JSONObject jsonObject = new JSONObject(data);
		uap_token = jsonObject.getString("uap_token");
		if (uap_token == null || uap_token.trim().length() <= 0) {
			throw new Exception("登录失败,返回uap_token为空!");
		}
		System.out.println("uap_token="+uap_token);
		return uap_token;
	}
	/**
	 * 返回结果处理
	 * @param jsonres
	 * @return
	 * @throws Exception
	 */
	public static String handleResult(String jsonres)throws Exception{
		JSONObject jsonObject = new JSONObject(jsonres);
		String statuscode = jsonObject.getString("statuscode");
		if(!"0".equalsIgnoreCase(statuscode)){
			String errorcode = jsonObject.getString("errorcode");
			String errormsg = jsonObject.getString("errormsg");
			String errordetailmsg = jsonObject.getString("errordetailmsg");
			throw new Exception("错误编码："+errorcode+"；错误信息："+errormsg+";详细错误信息："+errordetailmsg);			
		}
		return jsonObject.getString("data");
	}
	
}  