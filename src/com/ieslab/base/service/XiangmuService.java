package com.ieslab.base.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class XiangmuService {
	/**
	 * 根据厂站获取这个厂站下的项目信息
	 * @param czid
	 * @return
	 */
	public static List<JSONObject> findXiangmuByCzid(String gsid, String czid) {
		if(czid == null || "0".equals(czid) || czid.length() == 0){
			List<JSONObject> xmList = BaseModelCache.getInstance().getXiangmuList();
			if(gsid == null || "0".equals(gsid) || gsid.length() == 0){
				return xmList;
			}
			if(xmList != null){
				List<JSONObject> res = new ArrayList<JSONObject>();
				for(JSONObject obj: xmList){
					if(gsid.equals(obj.getString("powercorpid"))){
						res.add(obj);
					}
				}
				return res;
			}
		}else{
			Map<String, List<JSONObject>> czXiangmuMap = BaseModelCache.getInstance().getCzXiangmuMap();
			List<JSONObject> czbyXm = czXiangmuMap.get(czid);
			return czbyXm;
		}
		return null;
	}
	
	public static List<JSONObject> findXiangmuByCzid(String czid) {
		if(czid == null || "0".equals(czid) || czid.length() == 0){
			return BaseModelCache.getInstance().getXiangmuList();
		}else{
			return BaseModelCache.getInstance().getCzXiangmuMap().get(czid);
		}
	}
	
	
	/**
	 * 根据厂站获取这个厂站下的项目信息
	 * @return
	 */
	public static List<JSONObject> selectXiangmu(String czid) {
		List<JSONObject> xmlist = new ArrayList<JSONObject>();
		List<JSONObject> xmLst = BaseModelCache.getInstance().getXiangmuList();
		if(czid!=null && !czid.equals("") && czid.length()!=0){
			for(JSONObject obj : xmLst){
				String[] czids = czid.split(",");
				for(int i = 0;i < czids.length;i++){
					if(obj.getString("changzhanid").equals(czids[i])){
						xmlist.add(obj);
					}
				}
			}
		}
		return xmlist;
	}

}
