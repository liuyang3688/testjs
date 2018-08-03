package com.leotech.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leotech.model.Triple;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RtService {
	private static Map<Triple, Double> s_mapRtData;
	static{
		s_mapRtData = new ConcurrentHashMap<Triple, Double>();
	}
	public static JSONObject getRtData() {
		JSONObject datas = new JSONObject();
		for (Map.Entry<Triple, Double> entry : s_mapRtData.entrySet()) {
			Triple tri = entry.getKey();
			String bjlx = String.valueOf(tri.bjlx);
			String bjid = String.valueOf(tri.bjid);
			if (!datas.containsKey(bjlx)) {
				datas.put(bjlx, new JSONObject());
				((JSONObject)datas.get(bjlx)).put(bjid, new JSONArray());
			} else if (!((JSONObject)datas.get(bjlx)).containsKey(bjid)) {
				((JSONObject) datas.get(bjlx)).put(bjid, new JSONArray());
			}
			JSONObject one = new JSONObject();
			one.put("bjlx", tri.bjlx);
			one.put("bjid", tri.bjid);
			one.put("bjcs", tri.bjcs);
			one.put("val", entry.getValue());
			((JSONArray)((JSONObject)datas.get(bjlx)).get(bjid)).add(one);
		}
		return datas;
	}

	public static void setRtData(Triple tri, double val) {
		s_mapRtData.put(tri, val);
	}
}
