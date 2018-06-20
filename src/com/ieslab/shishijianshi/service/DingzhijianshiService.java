package com.ieslab.shishijianshi.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.rdbdata.DZQueryParam;
import com.ieslab.rdbdata.RdbwebservicePortType;
import com.ieslab.rdbdata.service_new.RdbWebServiceUtil;


public class DingzhijianshiService {
	
	public JSONArray fdnlData;

	
	
	/**
	 * 定制监视界面信息查询
	 * 调用接口
	 * @param queryParam
	 * @param type 风电机组：0，发电能力：1，电气设备：2，通信情况：3
	 * @return String
	 */
	public JSONArray dingzhijianshi(DZQueryParam queryParam , int type){
		String data = null;
		String dataStr = null;
		JSONArray res = null;
		//建立连接
		try {
			
			RdbwebservicePortType service = RdbWebServiceUtil.getRdbWebService();
			//风电机组：0，发电能力：1，电气设备：2，通信情况：3
			if(type == 0){
				dataStr = service.getFJDZResult(queryParam);
			}else if(type == 1){
				dataStr = service.getFDNLDZResult(queryParam);
			}else if(type == 2){
				dataStr = service.getDQSBDZResult(queryParam);
			}else if(type == 3){
				dataStr = service.getTXWLDZResult(queryParam);
			}
//			if(dataStr == null || dataStr.length() == 0 ){
//				return null;
//			}
			if(!"".equals(dataStr)){
				//请求返回的字符串转编码
				data = new String(dataStr.getBytes("iso8859-1"), "GBK"); 
				//把字符串返回值转成JSONArray
				res = JSONArray.parseArray(data);
				//如果是发电能力返回的字符串就放到全局变量fdnlData中
				if(type == 1){
					fdnlData = res;
					//调用日期处理方法返回JSONArray
					res = fdnlDate(res);
				}
			}else{
				res = null;
			}
			
			
		} catch (Exception e) {
			res = null;
			System.out.println("定制监视查询数据出错"+e.getMessage());
		}
		
		return res;
	}
	
	/**
	 * 如果本次返回的日期和上次不一样，就使用上一次的
	 * @param arr JSONArray
	 * @return
	 */
	public JSONArray fdnlDate(JSONArray arr){
		JSONArray res = new JSONArray();
		for (int i = 0; i < arr.size(); i++) {
			JSONObject objRes = (JSONObject) arr.get(i);
			JSONObject objFdnlData = (JSONObject) fdnlData.get(i);
			
			//如果本次和上次开始时间值不一样，就把上次开始时间的值赋给本次的JSONObject
			if(!objFdnlData.get("StartTime").equals(objRes.get("StartTime"))){
				objRes.put("StartTime", objFdnlData.get("StartTime"));
			}
			res.add(objRes);
		}
		
		return res;
	}
}
