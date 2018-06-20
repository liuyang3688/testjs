package com.ieslab.base.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.data.dao.HisDataDao;
import com.ieslab.base.data.service.HisDataService;
import com.ieslab.base.hbase.service.ChangZhanService;
import com.ieslab.base.hbase.service.DianWangService;
import com.ieslab.base.hbase.service.FengJiService;
import com.ieslab.base.service.FengjiService;
import com.ieslab.util.DateUtil;


@Controller
@RequestMapping("hisdata")
public class HistoryQueryController {
	
	@RequestMapping("getDianWangData")
	public void getDianWangData(HttpServletRequest request, HttpServletResponse response){
	
		String bjid = request.getParameter("bjid");
		String csids = request.getParameter("csids");//201,202,203
		String starttime = request.getParameter("starttime"); //yyyyMMddHHmmss
		String endtime = request.getParameter("endtime"); //yyyyMMddHHmmss
		
		if(csids == null || csids.length() == 0){
			return;
		}
		if(bjid == null || bjid.length()== 0){
			return;
		}
		bjid = String.format("%05d", Integer.parseInt(bjid));

		String csArr[] = csids.split(",");
		String csParams = "";
		List<String> csList = new ArrayList<String>();
		for(String param: csArr){
			csParams = csParams + "c" + param + ",";//"c201,c202,c203,"
			csList.add("c" + param);
		}
		//c201,c202,c203
		if(csParams.length() > 0){
			csParams = csParams.substring(0, csParams.length() - 1);
		}
		
		List<JSONObject> res = DianWangService.getDianWangData(csParams, bjid, starttime, endtime);
		
		JSONObject obj = new JSONObject();
		
		for(String cs: csList){
			JSONArray arr = new JSONArray();
			for(JSONObject t: res){
				if(t.containsKey(cs)){
					arr.add(t.getFloat(cs));
				}
			}
			obj.put(cs, arr);
		}
		JSONArray arr = new JSONArray();
		for(JSONObject t: res){
			if(t.containsKey("time")){
				arr.add(t.getString("time"));
			}
		}
		obj.put("time", arr);
		
		try {
			response.getWriter().print(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@RequestMapping("getChangZhanData")
	public void getChangZhanData(HttpServletRequest request, HttpServletResponse response){
	
		String bjid = request.getParameter("bjid");
		String csids = request.getParameter("csids");//201,202,203
		String starttime = request.getParameter("starttime"); //yyyyMMddHHmmss
		String endtime = request.getParameter("endtime"); //yyyyMMddHHmmss
		String queryYucePower = request.getParameter("yucepower");
		
		if(csids == null || csids.length() == 0){
			return;
		}
		if(bjid == null || bjid.length()== 0){
			return;
		}

		String csArr[] = csids.split(",");
		String csParams = "";
		List<String> csList = new ArrayList<String>();
		for(String param: csArr){
			csParams = csParams + "c" + param + ",";//"c201,c202,c203,"
			csList.add("c" + param);
		}
		//c201,c202,c203
		if(csParams.length() > 0){
			csParams = csParams.substring(0, csParams.length() - 1);
		}
		
		List<JSONObject> res = ChangZhanService.getChangZhanData(csParams, bjid, starttime, endtime);
		
		JSONObject obj = new JSONObject();
		
		for(String cs: csList){
			JSONArray arr = new JSONArray();
			for(JSONObject t: res){
				if(t.containsKey(cs)){
					arr.add(t.getFloat(cs));
				}
			}
			obj.put(cs, arr);
		}
		JSONArray arr = new JSONArray();
		for(JSONObject t: res){
			if(t.containsKey("time")){
				arr.add(t.getString("time"));
			}
		}
		obj.put("time", arr);
		
		
		//对于场站信息来讲，判断是否需要去查询预测功率
		if(queryYucePower != null && queryYucePower.equals("yucepower")){
			List<JSONObject> list = HisDataService.getYucePowerByCzid(bjid, "0", starttime, endtime);
			JSONArray ycDataArr = new JSONArray();
			JSONArray ycTimeArr = new JSONArray();
			if(list != null && list.size() > 0){
				for(JSONObject o: list){
					ycDataArr.add(o.getString("yucepower"));
					ycTimeArr.add(o.getString("time"));
				}
			}else{
				Date startDate = DateUtil.parseDateStr(starttime, "yyyyMMddHHmmss");
				Date nowDate = new Date();
				while(DateUtil.getlongDiff(startDate, nowDate) > 0){
					ycDataArr.add(0);
					ycTimeArr.add(DateUtil.formatdate(startDate, "yyyyMMddHHmmss"));
					startDate = DateUtil.addtimes(startDate, 15 * 1000 * 60);
				}
			}
			obj.put("ycdata", ycDataArr);
			obj.put("yctime", ycTimeArr);
		}
		
		try {
			response.getWriter().print(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@RequestMapping("getFengjiData")
	public void getFengjiData(HttpServletRequest request, HttpServletResponse response){
	
		String bjid = request.getParameter("bjid");
		String csids = request.getParameter("csids");//201,202,203
		String starttime = request.getParameter("starttime"); //yyyyMMddHHmmss
		String endtime = request.getParameter("endtime"); //yyyyMMddHHmmss
		
		if(csids == null || csids.length() == 0){
			return;
		}
		if(bjid == null || bjid.length()== 0){
			return;
		}

		String csArr[] = csids.split(",");
		String csParams = "";
		List<String> csList = new ArrayList<String>();
		for(String param: csArr){
			csParams = csParams + "c" + param + ",";//"c201,c202,c203,"
			csList.add("c" + param);
		}
		//c201,c202,c203
		if(csParams.length() > 0){
			csParams = csParams.substring(0, csParams.length() - 1);
		}
		
		List<JSONObject> res = FengJiService.getFengJiData(csParams, bjid, starttime, endtime);
		
		JSONObject obj = new JSONObject();
		
		for(String cs: csList){
			JSONArray arr = new JSONArray();
			for(JSONObject t: res){
				if(t.containsKey(cs)){
					arr.add(t.getFloat(cs));
				}
			}
			obj.put(cs, arr);
		}
		JSONArray arr = new JSONArray();
		for(JSONObject t: res){
			if(t.containsKey("time")){
				arr.add(t.getString("time"));
			}
		}
		obj.put("time", arr);
		
		try {
			response.getWriter().print(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("getShiShiJianShiTJData")
	public void getShiShiJianShiTJData(HttpServletRequest request, HttpServletResponse response){
		String type = request.getParameter("type"); //0:日 1：月 2：年
		String bjid = request.getParameter("bjid"); //场站或风机ID
		String cols = request.getParameter("cols"); //要查询的列
		String bjlxid = request.getParameter("bjlxid"); //部件类型ID
		//根据查询类型和部件类型确定表名
		String tableName = "";
		String tableAfter = "";
		
		//根据类型定位时间信息
		String timeStr = "";
		Date current = new Date();
		if("0".equals(type)){
			tableAfter = "day";
			//查询昨天的数据
			timeStr = DateUtil.formatdate(DateUtil.adddays(current, -1), "yyyy-MM-dd") + " 00:00:00";
		}else if("1".equals(type)){
			tableAfter = "month";
			timeStr = DateUtil.formatdate(current, "yyyy-MM") + "-01 00:00:00";
		}else{
			tableAfter = "year";
			timeStr = DateUtil.formatdate(current, "yyyy") + "-12-31 00:00:00";
		}
		
//		if("31".equals(bjlxid)){
//			tableName = "FJTJDATA" + tableAfter;
//		}else{
//			tableName = "CZTJDATA" + tableAfter;
//		}
		if("5".equals(bjlxid)){
			List<JSONObject> list = FengjiService.findAllFengjiByCzId(bjid);
			if(list != null){
				bjid = "";
				for(JSONObject o: list){
					bjid = bjid + o.getString("id") + ",";
				}
				if(bjid.length() > 0){
					bjid = bjid.substring(0, bjid.length() - 1);
				}else{
					bjid = "0";
				}
			}else{
				bjid = "0";
			}
			
			
		}
		tableName = "FJTJDATA" + tableAfter;
		List<JSONObject> list = HisDataDao.getInstance().queryShiShiJianShiTJData(cols, timeStr, bjid, tableName);
		
		JSONObject res = new JSONObject();
		
		if(list != null && list.size() > 0){
			res = list.get(0);
		}
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
