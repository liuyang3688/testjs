package com.ieslab.base.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class JiDianXianService {

	/**
	 * 根据项目ID获取集电线路
	 * @param xmid
	 * @return
	 */
	public static List<JSONObject> findAllByXmId(String xmid) {
		if(xmid == null || "0".equals(xmid) || xmid.length() == 0){
			return BaseModelCache.getInstance().getJiDianXianList();
		}else{
			return BaseModelCache.getInstance().getXmJiDianXianMap().get(xmid);
		}
	}
	
	/**
	 * 根据厂站ID获取集电线路
	 * @param xmid
	 * @return
	 */
	public static List<JSONObject> findAllByCzId(String czid) {
		if(czid == null || "0".equals(czid) || czid.length() == 0){
			return BaseModelCache.getInstance().getJiDianXianList();
		}else{
			return BaseModelCache.getInstance().getXmJiDianXianMap().get(czid);
		}
	}
	
	public static List<JSONObject> findAllByFilter(String czid, String xmid,String gsid) {
		List<JSONObject> res = new ArrayList<JSONObject>();
		List<JSONObject> tempList = BaseModelCache.getInstance().getJiDianXianList();
		for(JSONObject obj: tempList){
			if("0".equals(gsid) || gsid.equals(obj.getString("powercorpid"))){
				if("0".equals(czid) || czid.equals(obj.getString("changzhanid"))){
					if("0".equals(xmid) || xmid.equals(obj.getString("xiangmuid"))){
						res.add(obj);
					}
				}
			}
		}
		return res;
	}
}
