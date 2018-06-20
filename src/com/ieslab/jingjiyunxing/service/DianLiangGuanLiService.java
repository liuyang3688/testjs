package com.ieslab.jingjiyunxing.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.jingjiyunxing.dao.DianLiangGuanLiDao;
import com.ieslab.jingjiyunxing.dao.ShengChanZhiBiaoDao;
import com.ieslab.util.DateUtil;

public class DianLiangGuanLiService {
	/**
	 * 获取指标数据
	 * @param gsid 
	 * @param czid 
	 * @param xmid 
	 * @param fjid 
	 * @param zbsds 
	 * @param table 
	 * @param ids 
	 * @param zbid 
	 * @param startTime 
	 * @param endTime 
	 * @param timeType 
	 * @param week 
	 * @param jibie 
	 * @param dw 
	 * @return
	 */
	public static List<JSONObject> findData(String gsid, String czid, String xmid, String fjid, String zbsds, 
			String table, String ids, String zbid, String startTime, String endTime, String timeType, 
			String week, String jibie, String dw, String buwei){
		List<JSONObject> tjList = ShengChanZhiBiaoDao.instance().findData(table,ids,zbid,startTime,endTime,timeType,week,jibie);
		JSONObject xhObj = DianLiangGuanLiDao.instance().findData(gsid,czid,xmid,fjid,zbsds,startTime,endTime,week,jibie,dw,buwei,null);
		if(xhObj != null){
			String[] dws = dw.split(",");
			String[] zbs = zbsds.split(";");
			for(com.alibaba.fastjson.JSONObject obj : tjList){
				String key = "";
				if(obj.getString("gsid") != null){
					key += obj.getString("gsid") + "_";
				}
				if(obj.getString("czid") != null){
					key += obj.getString("czid") + "_";
				}
				if(obj.getString("xmid") != null){
					key += obj.getString("xmid") + "_";
				}
				if(obj.getString("fjid") != null){
					key += obj.getString("fjid") + "_";
				}
				if(buwei != null && !"".equals(buwei)){
					String[] buweis = buwei.split(",");
					for(String str : buweis){
						String key1 = key+str;
						for(String s : dws){
							float value = 0;
							if(xhObj.getFloat(s+"_"+key1) != null){
								value += xhObj.getFloat(s+"_"+key1);
							}
							obj.put(s+"_"+str, value);
						}
					}
				}else{
					for(String str : zbs){
						String[] sArr = str.split("_");
						String key1 = key;
						key1 += sArr[0] + "_";
						for(String s : dws){
							float value = 0;
							for(String jxyy : sArr[1].split(",")){
								String key2 = key1;
								key2 += jxyy;
								if(xhObj.getFloat(s+"_"+key2) != null){
									value += xhObj.getFloat(s+"_"+key2);
								}
							}
							obj.put(s+"_"+str.replaceAll(",", "and"), value);
						}
					}
				}
			}
		}
		return tjList;
	}
	
	/**
	 * 获取对标数据
	 * @param gsid 
	 * @param czid 
	 * @param xmid 
	 * @param fjid 
	 * @param zbsds 
	 * @param table 
	 * @param ids 
	 * @param zbid 
	 * @param startTime 
	 * @param endTime 
	 * @param timeType 
	 * @param week 
	 * @param jibie 
	 * @param dbStartTime 
	 * @param dbEndTime 
	 * @param dbWeek 
	 * @return
	 */
	public static List<JSONObject> findDuibiaoData(String gsid, String czid, String xmid, String fjid, String zbsds, 
			String table, String ids, String zbid, String startTime, String endTime, String timeType, 
			String week, String jibie,String dbStartTime, String dbEndTime, String dbWeek, String dbCell, String dw, String buwei){
		
		List<JSONObject> lst = new ArrayList<JSONObject>();
		List<JSONObject> list = findData(gsid,czid,xmid,fjid,zbsds,table,ids,zbid,startTime,endTime,timeType,week,jibie,dw,buwei);
		List<JSONObject> dbList = findData(gsid,czid,xmid,fjid,zbsds,table,ids,zbid,dbStartTime,dbEndTime,timeType,dbWeek,jibie,dw,buwei);
		Map<String, JSONObject> dbMap = new ConcurrentHashMap<String, JSONObject>();
		for(JSONObject obj : dbList){
			dbMap.put(obj.getString("id"), obj);
		}
		for(int i = 0;i < list.size();i++){
			if(dbMap.containsKey(list.get(i).getString("id"))){
				String keyStr = dbCell.replaceAll(",", "and");
				if(dw.equals("LOSSDL") || dw.equals("CNT") || dw.equals("LASTTIME")){
					keyStr = dw + "_" + keyStr;
				}
				float value = list.get(i).getFloat(keyStr)-dbMap.get(list.get(i).getString("id")).getFloat(keyStr);
				JSONObject o = new JSONObject();
				o.put("name", list.get(i).getString("name"));
				o.put("value", value);
				lst.add(o);
			}
		}
		return lst;
	}
	
	/**
	 * 获取历史数据
	 * @param gsid
	 * @param czid
	 * @param xmid
	 * @param fjid
	 * @param zbsds 
	 * @return
	*/
	public static List<JSONObject> findHisData(String gsid, String czid, String xmid, String fjid, String zbsds, String jibie, String dbdw, String buwei){
		
		List<JSONObject> lst = DianLiangGuanLiDao.instance().findXihuaHisData(gsid,czid,xmid,fjid,zbsds,jibie,dbdw,buwei,null);
		
		return lst;
	}
}
