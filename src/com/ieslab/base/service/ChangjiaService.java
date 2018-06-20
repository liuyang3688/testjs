package com.ieslab.base.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class ChangjiaService {
	
	public static List<JSONObject> getChangJiaByStation(String id) {
		List<JSONObject> list = BaseModelCache.getInstance().getChangjiaList();
		
		if(id == null || id.length() == 0){
			return list;
		}else{
			List<JSONObject> temp = new ArrayList<JSONObject>();
			
			for(JSONObject obj: list){
				if(id.equals(obj.getString("id"))){
					temp.add(obj);
				}
			}
			return temp;
		}
	}

}
