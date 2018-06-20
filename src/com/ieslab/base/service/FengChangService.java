package com.ieslab.base.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
public class FengChangService {

	/**
	 * 根据公司ID获取这个公司下的风场信息
	 * @param comid
	 * @return
	 */
	public static List<JSONObject> findAllCZByCompany(String comid) {
		List<JSONObject> list = BaseModelCache.getInstance().getStaList();
		
		if(comid == null || comid.length() == 0){
			return list;
		}else{
			List<JSONObject> temp = new ArrayList<JSONObject>();
			
			for(JSONObject obj: list){
				if(comid.equals(obj.getString("powercorpid"))){
					temp.add(obj);
				}
			}
			return temp;
			
		}
		
	}
	
	
		/**
	 * 根据公司ID获取这个公司下的风场信息
	 * @param comid
	 * @return
	 */
	public static List<JSONObject> findAllCZByCompany_hebing(String comid) {
		List<JSONObject> list = BaseModelCache.getInstance().getStaList();
		List<JSONObject> listXiangmu = BaseModelCache.getInstance().getXiangmuList();
		List<JSONObject> fjList = BaseModelCache.getInstance().getFengjiList();
		
		List<JSONObject> dataList = new ArrayList<JSONObject>();
		if(comid == null || comid.length() == 0){
			for(JSONObject obj: list){
				//创建一个map 存场站 和场站下面的项目
				JSONObject czMap = new JSONObject();
				List<JSONObject> cz_xm = new ArrayList<JSONObject>();
				for(JSONObject xm : listXiangmu){
					if(obj.getString("id").equals(xm.getString("changzhanid"))){
						cz_xm.add(xm);
						List<JSONObject> xm_fj = new ArrayList<JSONObject>();
						for(JSONObject fj : fjList){
							if(xm.getString("id").equals(fj.getString("xiangmuid"))){
								xm_fj.add(fj);
							}
						}
						xm.put("fengjiList", xm_fj);
					}
				}
				czMap.put("changzhan", obj);
				czMap.put("xiangmu", cz_xm);
				dataList.add(czMap);
			}
		}else{
			for(JSONObject obj: list){
				//创建一个jsonobject 存场站 和场站下面的项目
				JSONObject czMap = new JSONObject();
				if(comid.equals(obj.getString("powercorpid"))){
					List<JSONObject> cz_xm = new ArrayList<JSONObject>();
					for(JSONObject xm : listXiangmu){
						if(obj.getString("id").equals(xm.getString("changzhanid"))){
							cz_xm.add(xm);
							List<JSONObject> xm_fj = new ArrayList<JSONObject>();
							for(JSONObject fj : fjList){
								if(xm.getString("id").equals(fj.getString("xiangmuid"))){
									xm_fj.add(fj);
								}
							}
							//给风机排序
//							Collections.sort(xm_fj, new Comparator<JSONObject>(){
//
//								@Override
//								public int compare(JSONObject o1, JSONObject o2) {
//									int val1 = o1.getInteger("bianhao");
//									int val2 = o2.getInteger("bianhao");
//									return (val2 < val1) ? -1 : ((val1 == val2) ? 0 : 1);
//								}
//								
//							});
							xm.put("fengjiList", xm_fj);
						}
					}
					czMap.put("changzhan", obj);
					czMap.put("xiangmu", cz_xm);
					dataList.add(czMap);
				}
				
				
			}
			
//			for(JSONObject obj: list){
//				if(comid.equals(obj.getString("powercorpid"))){
//					temp.add(obj);
//				}
//			}
		}
		return dataList;
	}
}
