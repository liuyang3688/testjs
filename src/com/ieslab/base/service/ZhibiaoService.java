package com.ieslab.base.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.dao.XiangmuDao;
import com.ieslab.base.dao.ZhibiaoDao;
import com.ieslab.util.DateUtil;

public class ZhibiaoService {
	/**
	 * 获取指标数据
	 * @param weeknum 
	 * @param timeType 
	 * @param zclb 
	 * @param pagename 
	 * @param duibiaotype 
	 * @param duibiaotype 
	 * @params biaoming
	 * @params ziduanming,starttime.endtime,id(风机，项目，场站，公司)
	 * @return
	 */
	public static List<JSONObject> getZhibiaoData(String biaoming, String ziduanming, String id, String starttime, String endtime, String weeknum, String timeType, String jibie, String zclb, String pagename, String duibiaotype){
		List<JSONObject> list = null;
		if(pagename.equals("kekaoxingguanli") && !jibie.equals("4") && ziduanming!=null && !ziduanming.equals("")){
			String idStr;
			if(!jibie.equals("3")){
				idStr = XiangmuDao.instance().findXiangmuId(jibie, id);
			}else{
				idStr = id;
			}
			list = ZhibiaoDao.instance().findZhibiaoDataKKXGL(biaoming,ziduanming,idStr,starttime,endtime,weeknum,timeType,jibie,zclb,pagename,duibiaotype);
			Map<Integer, JSONObject> map = new ConcurrentHashMap<Integer, JSONObject>();
			if(!jibie.equals("3")){
				String[] zdm = ziduanming.split(",");
				for(JSONObject obj : list){
					if(jibie.equals("2")){
					//	System.out.println(obj.getInteger("czid"));
						if(!map.containsKey(obj.getInteger("czid"))){
							map.put(obj.getInteger("czid"), obj);
							obj.remove("xmid");
							obj.remove("xiangmuname");
						}else{
							for(int i = 0;i < zdm.length;i++){
								map.get(obj.getInteger("czid")).put(zdm[i], map.get(obj.getInteger("czid")).getFloat(zdm[i])+obj.getFloat(zdm[i]));
							}
						}
					}
					if(jibie.equals("1")){
						//System.out.println(obj.getInteger("gsid"));
						if(!map.containsKey(obj.getInteger("gsid"))){
							map.put(obj.getInteger("gsid"), obj);
							obj.remove("czid");
							obj.remove("changzhanname");
							obj.remove("xmid");
							obj.remove("xiangmuname");
						}else{
							for(int i = 0;i < zdm.length;i++){
								map.get(obj.getInteger("gsid")).put(zdm[i], map.get(obj.getInteger("gsid")).getFloat(zdm[i])+obj.getFloat(zdm[i]));
							}
						}
					}
				}
				List<JSONObject> Lst = new ArrayList<JSONObject>();;
				String[] idArr = id.split(",");
				for(int i = 0;i < idArr.length;i++){
					if(map.containsKey(Integer.parseInt(idArr[i]))){
						Lst.add(map.get(Integer.parseInt(idArr[i])));
					}
				}
				list = Lst;
			}
		}else{
			list = ZhibiaoDao.instance().findZhibiaoData(biaoming,ziduanming,id,starttime,endtime,weeknum,timeType,jibie,zclb,pagename,duibiaotype);
		}
		return list;
	}
	
	/**
	 * 获取历史指标数据
	 * @param duibiaotype 
	 * @params biaoming
	 * @params ziduanming,starttime.endtime,id(风机，项目，场站，公司)
	 * @return
	 */
	public static List<JSONObject> getHisZhibiaoData(String biaoming, String ziduanming, String id){
		List<JSONObject> list = ZhibiaoDao.instance().findHisZhibiaoData(biaoming,ziduanming,id,"0");
		return list;
	}

	/**
	 * 获取细化表指标数据
	 * @param zbsds
	 * @param startTime
	 * @param endTime
	 * @param gsid
	 * @param czid
	 * @param xmid
	 * @param fjid
	 * @param dw 
	 * @param weeknum 
	 * @param timeType 
	 * @param jxbuwei 
	 * @param duibiaotype 
	 * @param jibie 
	 * @return
	 */
	public static JSONObject getXihuaData(String Colname, String[] zbsds, String startTime, String endTime, String gsid, String czid, String xmid, String fjid, String dw, String weeknum, String timeType, String jxbuwei, String duibiaotype, String jibie){
		/*if(timeType.equals("2")){
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(endTime.split(" ")[0].split("-")[0])); // 设定年份
			cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(weeknum)); // 设置为第几周
			cal.set(Calendar.DAY_OF_WEEK, 2); // 1表示周日，2表示周一，7表示周六
			Date date = cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String a = sdf.format(date);
			
			
			cal.add(cal.DAY_OF_MONTH,6);
			Date date2 = cal.getTime();
			String b = sdf.format(date2);
			startTime = a + " 00:00:00";
			endTime = b + " 23:59:59";
		}*/
		//对标单独处理
		JSONObject obj = ZhibiaoDao.instance().findXihuaData(Colname,zbsds,startTime,endTime,gsid,czid,xmid,fjid,dw,jxbuwei,duibiaotype,weeknum,jibie);
		return obj;
	}
	
	/**
	 * 获取历史细化指标数据
	 * @param jibie 
	 * @param duibiaotype 
	 * @params biaoming
	 * @params ziduanming,starttime.endtime,id(风机，项目，场站，公司)
	 * @return
	 */
	public static List<JSONObject> getHisZhibiaoXihua(String[] cs,String Colname,String zbsds,String dw, String jibie){
		List<JSONObject> list = ZhibiaoDao.instance().findXihuaDataHis(cs,Colname,zbsds,dw,jibie);
		return list;
	}
	
	/**
	 * 获取指标数据
	 * @param danwei (统计级别)
	 * @param tablestyle (报表样式)
	 * @params tabletype (报表种类：日、月、年报表)
	 * @params ziduanming,starttime.endtime(风机，场站，分子公司， 公司)
	 * @return
	 * @throws ParseException 
	 */
	public static JSONArray getTJZhibiaoData(String ziduanming, String startTime, String endTime, String tabletype, String tablestyle, String danwei, String ids) throws ParseException{
		String tableName = "";
		String bjlx = "";
		if(danwei.equals("1")){
			tableName = "PCTJDATA";
			bjlx = "3";
		}else if(danwei.equals("2")){
			tableName = "CZTJDATA";
			bjlx = "5";
		}else if(danwei.equals("3")){
			tableName = "XMTJDATA";
			bjlx = "34";
		}else if(danwei.equals("4")){
			tableName = "FJTJDATA";
			bjlx = "31";
		}
		
		if(tabletype.equals("1")){
			tableName += "day";
			startTime += " 00:00:00";
			endTime += " 23:59:59";
		}else if(tabletype.equals("2")){
			tableName += "month";
			long days = DateUtil.getDaysofMonth(DateUtil.parseDateStr(endTime, "yyyy-MM"));
			startTime += "-1 00:00:00";
			endTime += "-"+days+" 23:59:59";
		}else if(tabletype.equals("3")){
			tableName += "year";
			startTime += "-01-01 00:00:00";
			endTime += "-12-31 23:59:59";
		}
		JSONArray arr = new JSONArray();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(sim.parse(startTime).getTime() > sim.parse(endTime).getTime()){
			arr.add("开始时间大于结束时间");
			return arr;
		}
//		String[] idArr = ids.split(",");
//		List<JSONObject> list = null;
//		for(int i = 0;i < idArr.length;i+=999){
//			String idStr = null;
//			if((i+999)>idArr.length){
//				idStr = StringUtils.join(",",Arrays.copyOfRange(idArr, i, idArr.length));
//			}else{
//				idStr = StringUtils.join(",",Arrays.copyOfRange(idArr, i, i+999));
//			}
//			list = ZhibiaoDao.instance().findTJZhibiaoData(tableName, startTime, endTime, tabletype, tablestyle, bjlx, ziduanming, idStr);
//			for(JSONObject obj : list){
//				arr.add(obj);
//			}
//		}
		return arr;
	}
}
