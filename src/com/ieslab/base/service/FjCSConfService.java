package com.ieslab.base.service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.model.FJBJCSInfo;
import com.ieslab.util.GlobalPathUtil;
import com.ieslab.base.dao.FengjiDao;

/**
 * 风机监视参数配置文件读取
 * @author Li Hao
 *
 */
public class FjCSConfService {

	
	public static Map<String, FJBJCSInfo> composeMap(List<FJBJCSInfo> list){
		Map<String, FJBJCSInfo> map = new HashMap<String, FJBJCSInfo>();
		
		for(FJBJCSInfo info: list){
			map.put(info.getBjcsid() + "", info);
		}
		return map;
	}
	
	public static List<FJBJCSInfo> dataScreening(List<FJBJCSInfo> list,String fengjiId){
		List<Integer> csList = FengjiDao.instance().findBjcsByFjid(fengjiId);
		List<FJBJCSInfo> lst = new ArrayList<>();
		for(int i = 0;i < list.size();i++){
			if(csList.contains(list.get(i).getBjcsid())){
				lst.add(list.get(i));
			}
		}
		return lst;
	}
	
	public static JSONArray sortInfo(List<FJBJCSInfo> list){
		JSONArray res = new JSONArray();
		Collections.sort(list);
		Map<String, JSONArray> map = new HashMap<>();
		for(FJBJCSInfo info: list){
			JSONObject obj = new JSONObject();
			obj.put("csid", info.getBjcsid());
			obj.put("csname", info.getBjcsName());
			obj.put("index", info.getIndex());
			if(map.containsKey(info.getSuoShuBj())){
				map.get(info.getSuoShuBj()).add(obj);
			}else{
				JSONArray arr = new JSONArray();
				arr.add(obj);
				map.put(info.getSuoShuBj(), arr);
			}
		}
		
		Set<String> keys = map.keySet();
		for(String key: keys){
			JSONObject keyObj = new JSONObject();
			keyObj.put("name", key);
			keyObj.put("children", map.get(key));
			res.add(keyObj);
		}
		return res;
	}
}
