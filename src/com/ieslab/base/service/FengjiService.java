package com.ieslab.base.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class FengjiService {
	
//	public static List<Fengji> findAllfengji(String czid){
//		return FengjiDao.instance().findAllfengji(czid);
//	}
	/**
	 * 通过厂站获取该厂站下的风机信息
	 * @param czid
	 * @return
	 */
	public static List<JSONObject> findAllFengjiByCzId(String czid) {
		
		List<JSONObject> tempList = BaseModelCache.getInstance().getFengjiList();
		
		if(czid == null || czid.length() == 0){
			return tempList;
		}else{
			List<JSONObject> res = new ArrayList<JSONObject>();
			
			for(JSONObject obj: tempList){
				if(czid.equals(obj.getString("changzhanid"))){
					res.add(obj);
				}
			}
			return res;
			
		}
	}

	public static List<JSONObject> findAllFengjiByCzId(String czid, String xmid, String jdxid, String fjtype , String gsid) {
		List<JSONObject> res = new ArrayList<JSONObject>();
		List<JSONObject> tempList = BaseModelCache.getInstance().getFengjiList();
		for(JSONObject obj: tempList){
			boolean flag = false;
			//判断厂站、项目、集电线
			if("0".equals(gsid) || gsid.equals(obj.getString("powercorpid"))){
				if("0".equals(czid) || czid.equals(obj.getString("changzhanid"))){
					if("0".equals(xmid) || xmid.equals(obj.getString("xiangmuid"))){
						if("0".equals(jdxid) || jdxid.equals(obj.getString("jidianxianid"))){
							flag = true;
						}
					}
				}
			}
			//然后去判断风机
			if(flag){
				if("0".equals(fjtype) || fjtype.equals(obj.getString("fjtypeid"))){
					res.add(obj);
				}
			}
		}
		return res;
	}
	/**
	 * 多选框选择风机
	 * @param czid
	 * @param xmid
	 * @param jdxid
	 * @param fjtype
	 * @param gsid
	 * @return
	 */
	
	public static List<JSONObject> findFengjiTree(String czids, String xmids, String fjtypes , String gsids) {
		List<JSONObject> res = new ArrayList<JSONObject>();
		List<JSONObject> tempList = BaseModelCache.getInstance().getFengjiList();
		for(JSONObject obj: tempList){
			//多个子公司id,如果不是全部，也不在多选的gsid里面,直接跳出循环
			if(!"0".equals(gsids) && (","+gsids+",").indexOf(","+obj.getString("powercorpid")+",") < 0){
				continue;
			}
			if(!"0".equals(czids) && (","+czids+",").indexOf(","+obj.getString("changzhanid")+",") < 0){
				continue;
			}
			if(!"0".equals(xmids) && (","+xmids+",").indexOf(","+obj.getString("xiangmuid")+",") < 0){
				continue;
			}
			if(!"0".equals(fjtypes) && (","+fjtypes+",").indexOf(","+obj.getString("fjtypeid")+",") < 0){
				continue;
			}
			res.add(obj);
		}
		return res;
	}
	
	
	public static List<JSONObject> guzhangfenxi_filter(String gsids, String czids, String xmids, String fjcjids, String fjxhs) {
		List<JSONObject> res = new ArrayList<JSONObject>();
		List<JSONObject> tempList = BaseModelCache.getInstance().getFengjiList();
		for(JSONObject obj: tempList){
			if(!"0".equals(gsids) && (","+gsids+",").indexOf(","+obj.getString("powercorpid")+",") < 0){
				continue;
			}
			if(!"0".equals(czids) && (","+czids+",").indexOf(","+obj.getString("changzhanid")+",") < 0){
				continue;
			}
			if(!"0".equals(xmids) && (","+xmids+",").indexOf(","+obj.getString("xiangmuid")+",") < 0){
				continue;
			}
			if(!"0".equals(fjcjids) && (","+fjcjids+",").indexOf(","+obj.getString("fjchangjia")+",") < 0){
				continue;
			}
			if(!"0".equals(fjxhs) && (","+fjxhs+",").indexOf(","+obj.getString("fjtypeid")+",") < 0){
				continue;
			}

			res.add(obj);
		}
		return res;
	}
	
	
	public static List<JSONObject> findAllFengjiByXmId(String xmid) {
		
		List<JSONObject> tempList = BaseModelCache.getInstance().getFengjiList();
		
		if(xmid == null || xmid.length() == 0){
			return tempList;
		}else{
			List<JSONObject> res = new ArrayList<JSONObject>();
			
			for(JSONObject obj: tempList){
				if(xmid.equals(obj.getString("xiangmuid"))){
					res.add(obj);
				}
			}
			return res;
			
		}
	}
}
