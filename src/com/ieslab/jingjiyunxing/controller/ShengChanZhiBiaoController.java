package com.ieslab.jingjiyunxing.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ieslab.base.service.BaseModelCache;
import com.ieslab.jingjiyunxing.service.ShengChanZhiBiaoService;
import com.ieslab.util.DateUtil;

@Controller
@RequestMapping("shengchanzhibiao")
public class ShengChanZhiBiaoController {

	@RequestMapping("getAllData")
	public void getAllData(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException{
		String startTime = request.getParameter("starttime");
		String endTime = request.getParameter("endtime");
		String timeType = request.getParameter("timetype");
		String jibie = request.getParameter("jibieid");
		String gsid = request.getParameter("gsid");
		String czid = request.getParameter("czid");
		String xmid = request.getParameter("xmid");
		String cjid = request.getParameter("cjid");
		String jxid = request.getParameter("jxid");
		String zbid = request.getParameter("zbid");
		String type = request.getParameter("type");
		String dbCell = request.getParameter("dbCell");
		
		//趋势对标参数
		String dbGsid = request.getParameter("dbGsid");
		String dbCzid = request.getParameter("dbCzid");
		String dbXmid = request.getParameter("dbXmid");
		String dbFjid = request.getParameter("dbFjid");
		
		//日期未选择
		if( (startTime.equals("") || startTime==null ) && (endTime==null || endTime.equals("")) ){
			JSONObject error = new JSONObject();
			error.put("error", "未选择日期");
			response.getWriter().print(error);
			return;
		}
		
		//对标列未选择
		if(type!=null && ( dbCell == null || "".equals(dbCell) )){
			JSONObject error = new JSONObject();
			error.put("error", "未选择对标列");
			response.getWriter().print(error);
			return;
		}
		
		com.alibaba.fastjson.JSONObject timeObj = getTime(startTime, endTime, timeType, type);
		
		String table = "";
		String ids = "";
		if(jibie.equals("4")){
			table = "FJTJDATA"+timeObj.getString("querytype");
			ids = xmid+";"+cjid+";"+jxid;
			//趋势对标
			if("3".equals(type)){
				ids = dbFjid;
				table = "FJTJDATADAY";
			}
		}else if(jibie.equals("3")){
			table = "XMTJDATA"+timeObj.getString("querytype");
			ids = xmid;
			//趋势对标
			if("3".equals(type)){
				ids = dbXmid;
				table = "XMTJDATADAY";
			}
		}else if(jibie.equals("2")){
			table = "CZTJDATA"+timeObj.getString("querytype");	
			ids = czid;
			//趋势对标
			if("3".equals(type)){
				ids = dbCzid;
				table = "CZTJDATADAY";
			}
		}else if(jibie.equals("1")){
			table = "PCTJDATA"+timeObj.getString("querytype");
			ids = gsid;
			//趋势对标
			if("3".equals(type)){
				ids = dbGsid;
				table = "PCTJDATADAY";
			}
		}
		List<com.alibaba.fastjson.JSONObject> zbLst = null;
		if(type == null){
			zbLst = ShengChanZhiBiaoService.findData(table,ids,zbid,timeObj.getString("startTime"),timeObj.getString("endTime"),timeType,timeObj.getString("week"),jibie);
		}else{
			switch(type){
				case "1" : case "2" : 
					zbid = dbCell;
					zbLst = ShengChanZhiBiaoService.findDuibiaoData(table,ids,zbid,timeObj.getString("startTime"),timeObj.getString("endTime"),timeObj.getString("dbStartTime"),timeObj.getString("dbEndTime"),timeType,timeObj.getString("week"),timeObj.getString("dbweek"),jibie);
					if(zbLst.size() == 0){
						JSONObject error = new JSONObject();
						error.put("error", "缺少对标数据");
						response.getWriter().print(error);
						return;
					}
					break;
				case "3" :
					zbid = dbCell;
					zbLst = ShengChanZhiBiaoService.findHisData(table, ids, zbid, jibie);
					break;
			}
		}
		response.getWriter().print(zbLst);
	}

	public static com.alibaba.fastjson.JSONObject getTime(String startTime, String endTime, String timeType,String type){
		String week = "";
		String querytype = "";
		
		String dbStartTime = "";
		String dbEndTime = "";
		String dbweek = "";
		if("0".equals(timeType)){
			querytype = "day";  
			startTime = DateUtil.formatdate(DateUtil.adddays(new Date(), -1), "yyyy-MM-dd HH:mm:ss");
			endTime = DateUtil.formatdate(new Date(), "yyyy-MM-dd HH:mm:ss");
			
			if("2".equals(type)){
				dbStartTime = DateUtil.formatdate(DateUtil.adddays(new Date(), -2), "yyyy-MM-dd HH:mm:ss");
				dbEndTime = DateUtil.formatdate(DateUtil.adddays(new Date(), -1), "yyyy-MM-dd HH:mm:ss");
			}
			if("1".equals(type)){
				dbStartTime = DateUtil.formatdate(DateUtil.addYear(DateUtil.adddays(new Date(), -1), -1),"yyyy-MM-dd HH:mm:ss");
				dbEndTime = DateUtil.formatdate(DateUtil.addYear(new Date(), -1),"yyyy-MM-dd HH:mm:ss");
			}
		}else if("1".equals(timeType)){//按天查询
			querytype = "day";
			if("2".equals(type)){
				dbStartTime = DateUtil.formatdate(DateUtil.adddays(DateUtil.parseDateStr(startTime), -1), "yyyy-MM-dd");
				dbEndTime = dbStartTime + " 23:59:59";
				dbStartTime = dbStartTime + " 00:00:00";
			}
			if("1".equals(type)){
				dbStartTime = DateUtil.formatdate(DateUtil.addYear(DateUtil.parseDateStr(startTime),-1), "yyyy-MM-dd");
				dbEndTime = dbStartTime + " 23:59:59";
				dbStartTime = dbStartTime + " 00:00:00";
			}
			endTime = startTime + " 23:59:59";
			startTime = startTime + " 00:00:00";
		}else if("2".equals(timeType)){//按周查询
			querytype = "week";
			week = endTime;
			endTime = startTime + "-12-28 23:59:59";
			startTime = Integer.parseInt(startTime)-1 + "-12-29 00:00:00";
			
			if("2".equals(type)){
				if(week.equals("1")){
					dbweek = "52";
					endTime = Integer.parseInt(startTime.split("-")[0])-1 + "-12-28 23:59:59";
					startTime = Integer.parseInt(startTime.split("-")[0])-2 + "-12-29 00:00:00";
				}else{
					dbweek = Integer.parseInt(week)-1+"";
				}
				dbEndTime = endTime;
				dbStartTime = startTime;
			}
			if("1".equals(type)){
				endTime = Integer.parseInt(startTime.split("-")[0])-1 + "-12-28 23:59:59";
				startTime = Integer.parseInt(startTime.split("-")[0])-2 + "-12-29 00:00:00";
				
				dbEndTime = startTime;
				dbStartTime = endTime;
			}
		}else if("3".equals(timeType)){//按月查询
			querytype = "month";
			if("2".equals(type)){
				dbStartTime = DateUtil.formatdate(DateUtil.addmonth(DateUtil.parseDateStr(startTime,"yyyy-MM"), -1), "yyyy-MM");
			}
			if("1".equals(type)){
				dbStartTime = DateUtil.formatdate(DateUtil.addYear(DateUtil.parseDateStr(startTime,"yyyy-MM"), -1), "yyyy-MM");
			}
			//获取这个月的天数
			long days = DateUtil.getDaysofMonth(DateUtil.parseDateStr(startTime, "yyyy-MM"));
			endTime = startTime + "-" + days +" 23:59:59";
			startTime = startTime + "-01 00:00:00";
			
			if("2".equals(type) || "1".equals(type)){
				long days1 = DateUtil.getDaysofMonth(DateUtil.parseDateStr(dbStartTime, "yyyy-MM"));
				dbEndTime = dbStartTime + "-" + days1 +" 23:59:59";
				dbStartTime = dbStartTime + "-01 00:00:00";
			}
		}else if("4".equals(timeType)){
			querytype = "year";
			if("2".equals(type)){
				dbStartTime = DateUtil.formatdate(DateUtil.addYear(DateUtil.parseDateStr(startTime,"yyyy"), -1), "yyyy");
				dbEndTime = dbStartTime + "-12-31 23:59:59";
				dbStartTime = dbStartTime + "-01-01 00:00:00";
			}
			if("1".equals(type)){
				dbStartTime = DateUtil.formatdate(DateUtil.addYear(DateUtil.parseDateStr(startTime,"yyyy"), -1), "yyyy");
				dbEndTime = dbStartTime + "-12-31 23:59:59";
				dbStartTime = dbStartTime + "-01-01 00:00:00";
			}
			
			endTime = startTime + "-12-31 23:59:59";
			startTime = startTime + "-01-01 00:00:00";
		}else if("5".equals(timeType)){
			querytype = "day";
		}
		
		com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
		obj.put("startTime", startTime);
		obj.put("endTime", endTime);
		obj.put("dbStartTime", dbStartTime);
		obj.put("dbEndTime", dbEndTime);
		obj.put("week", week);
		obj.put("dbweek", dbweek);
		obj.put("querytype", querytype);
		return obj;
	}
}
