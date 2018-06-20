package com.ieslab.rdbdata.service_new;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.rdbdata.RdbwebservicePortType;

/**
 * 刷新一个map的线程类
 * @author Li Hao
 *
 */
public class ParamThread implements Runnable{

	private Map<String, ParamInfo> dataMap = null;
	
	public ParamThread(Map<String, ParamInfo> map){
		dataMap = map;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			if(dataMap != null){
				String paramStr = "";
				List<String> keyList = new ArrayList<String>();
				Set<String> keys = dataMap.keySet();
				for(String key: keys){
					paramStr = paramStr + key + ";";
					keyList.add(key);
				}
				if(paramStr.length() > 0){
					paramStr = paramStr.substring(0, paramStr.length() - 1);
					
					try {
						RdbwebservicePortType service = RdbWebServiceUtil.getRdbWebService();
						String res = service.getRealData(paramStr);
						//把结果整理成json
						JSONArray arr = JSONArray.parseArray(res);
						//遍历key，获取结果的value值并设置
						for(int i = 0; i < keyList.size(); i++){
							JSONObject obj = (JSONObject) arr.get(i);
							ParamInfo paramInfo = dataMap.get(keyList.get(i));
							if(paramInfo != null){
								paramInfo.setValue(obj.getString("val"));
							}
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("获取实时数据出错：" + e.getMessage());
					}
					
				}
			}else{
				//如果map为空了。那么就结束线程，跳出while循环即可
//				break;
			}
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}
