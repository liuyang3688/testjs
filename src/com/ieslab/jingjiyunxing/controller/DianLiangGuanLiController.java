package com.ieslab.jingjiyunxing.controller;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ieslab.jingjiyunxing.service.DianLiangGuanLiService;
import com.ieslab.jingjiyunxing.service.ShengChanZhiBiaoService;

@Controller
@RequestMapping("dianliangguanli")
public class DianLiangGuanLiController {

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
		String zbsds = request.getParameter("zbsds");
		String type = request.getParameter("type");
		String dw = request.getParameter("dw");
		
		String dbCell = request.getParameter("dbCell");
		String dbdw = request.getParameter("dbdw");
		if(dbCell != null){
			dbCell = dbCell.replaceAll("and", ",");
			String[] dws = dw.split(",");
			for(String str : dws){
				dbCell = dbCell.replace(str+"_", "");
			}
		}
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
		
		com.alibaba.fastjson.JSONObject timeObj = ShengChanZhiBiaoController.getTime(startTime, endTime, timeType, type);
		
		String table = "";
		String ids = "";
		String fjid = null;
		if(jibie.equals("4")){
			table = "FJTJDATA"+timeObj.getString("querytype");
			ids = xmid+";"+cjid+";"+jxid;
			fjid = cjid+";"+jxid;
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
			zbLst =  DianLiangGuanLiService.findData(gsid,czid,xmid,fjid,zbsds,table,ids,zbid,timeObj.getString("startTime"),timeObj.getString("endTime"),timeType,timeObj.getString("week"),jibie,dw,null);
		}else{
			if(judgeContainsStr(dbCell)){
				zbid = dbCell;
				zbsds = "";
			}else{
				zbid = "";
				zbsds = dbCell;
			}
			switch(type){
				case "1" : case "2" : 
					zbLst = DianLiangGuanLiService.findDuibiaoData(gsid,czid,xmid,fjid,zbsds,table,ids,zbid,
							timeObj.getString("startTime"),timeObj.getString("endTime"),timeType,timeObj.getString("week"),jibie,
							timeObj.getString("dbStartTime"),timeObj.getString("dbEndTime"),timeObj.getString("dbWeek"),dbCell,dbdw,null);
					if(zbLst.size() == 0){
						JSONObject error = new JSONObject();
						error.put("error", "缺少对标数据");
						response.getWriter().print(error);
						return;
					}
					break;
				case "3" :
					if("".equals(zbsds)){
						zbLst = ShengChanZhiBiaoService.findHisData(table, ids, zbid, jibie);
					}else{
						zbLst = DianLiangGuanLiService.findHisData(dbGsid,dbCzid,dbXmid,dbFjid,zbsds,jibie,dbdw,null);
					}
					break;
			}
		}
		response.getWriter().print(zbLst);
	}
	
	
	
	@RequestMapping("getzhengti")
	public void getzhengti(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String type = request.getParameter("type");
		
		JSONArray arr = new JSONArray();
		for(int i=1; i<=100; i++){
			int a = new java.util.Random().nextInt(8);
			int b = new java.util.Random().nextInt(7);
			JSONObject obj = new JSONObject();
			if(type.equals("1")){
				obj.put("mc", "name"+i);
				obj.put("lldl", new java.util.Random().nextInt(100));
				obj.put("yfdl", new java.util.Random().nextInt(100));
				obj.put("sfdl", new java.util.Random().nextInt(100));
				obj.put("xdss", new java.util.Random().nextInt(100));
				obj.put("gzss", new java.util.Random().nextInt(100));
				obj.put("whss", new java.util.Random().nextInt(100));
				obj.put("slss", new java.util.Random().nextInt(100));
				obj.put("xnss", new java.util.Random().nextInt(100));
			}
			if(type.equals("3")){
				obj.put("mc", "name"+i);
				obj.put("dj", new java.util.Random().nextInt(100));
				obj.put("xj", new java.util.Random().nextInt(100));
				obj.put("gz", new java.util.Random().nextInt(100));
				obj.put("lj", new java.util.Random().nextInt(100));
				obj.put("lldl", new java.util.Random().nextInt(100));
				obj.put("yfdl", new java.util.Random().nextInt(100));
				obj.put("sfdl", new java.util.Random().nextInt(100));
				obj.put("xdss", new java.util.Random().nextInt(100));
				obj.put("gzss", new java.util.Random().nextInt(100));
				obj.put("whss", new java.util.Random().nextInt(100));
				obj.put("slss", new java.util.Random().nextInt(100));
				obj.put("xnss", new java.util.Random().nextInt(100));
			}
			if(type.equals("4")){
				obj.put("mc", "name"+i);
				obj.put("dqys", new java.util.Random().nextInt(100));
				obj.put("gz", new java.util.Random().nextInt(100));
				obj.put("lj", new java.util.Random().nextInt(100));
				obj.put("lldl", new java.util.Random().nextInt(100));
				obj.put("yfdl", new java.util.Random().nextInt(100));
				obj.put("sfdl", new java.util.Random().nextInt(100));
				obj.put("xdss", new java.util.Random().nextInt(100));
				obj.put("gzss", new java.util.Random().nextInt(100));
				obj.put("whss", new java.util.Random().nextInt(100));
				obj.put("slss", new java.util.Random().nextInt(100));
				obj.put("xnss", new java.util.Random().nextInt(100));
			}
			if(type.equals("5")){
				obj.put("mc", "name"+i);
				obj.put("dw", new java.util.Random().nextInt(100));
				obj.put("diwen", new java.util.Random().nextInt(100));
				obj.put("gw", new java.util.Random().nextInt(100));
				obj.put("df", new java.util.Random().nextInt(100));
				obj.put("ld", new java.util.Random().nextInt(100));
				obj.put("bdssdl", new java.util.Random().nextInt(100));
			}
			arr.put(obj);
		}	
		try {
			response.getWriter().print(arr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean judgeContainsStr(String str) {  
        String regex=".*[a-zA-Z]+.*";  
        Matcher m=Pattern.compile(regex).matcher(str);  
        return m.matches();  
    } 
}
