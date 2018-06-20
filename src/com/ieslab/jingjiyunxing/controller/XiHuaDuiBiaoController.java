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

import com.ieslab.base.dao.FengjiDao;
import com.ieslab.jingjiyunxing.service.DianLiangGuanLiService;
import com.ieslab.jingjiyunxing.service.ShengChanZhiBiaoService;

@Controller
@RequestMapping("xihuaduibiao")
public class XiHuaDuiBiaoController {

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
		
		//趋势对标参数
		String dbGsid = request.getParameter("dbGsid");
		String dbCzid = request.getParameter("dbCzid");
		String dbXmid = request.getParameter("dbXmid");
		String dbFjid = request.getParameter("dbFjid");
		
		String jxbuwei = request.getParameter("jxbuwei");
		String dbCell = request.getParameter("dbCell");
		String dbdw = request.getParameter("dbdw");
		if(dbCell != null){
			dbCell = dbCell.replaceAll("and", ",");
			String[] dws = dbdw.split(",");
			for(String str : dws){
				dbCell = dbCell.replace(str+"_", "");
			}
			if(jxbuwei != null && !"".equals(jxbuwei)){
				jxbuwei = dbCell;
			}else{
				zbsds = dbCell;
			}
		}
		
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
			zbLst =  DianLiangGuanLiService.findData(gsid,czid,xmid,fjid,zbsds,table,ids,zbid,timeObj.getString("startTime"),timeObj.getString("endTime"),timeType,timeObj.getString("week"),jibie,dbdw,jxbuwei);
		}else{
			switch(type){
				case "1" : case "2" : 
					zbLst = DianLiangGuanLiService.findDuibiaoData(gsid,czid,xmid,fjid,zbsds,table,ids,zbid,
							timeObj.getString("startTime"),timeObj.getString("endTime"),timeType,timeObj.getString("week"),jibie,
							timeObj.getString("dbStartTime"),timeObj.getString("dbEndTime"),timeObj.getString("dbWeek"),dbCell,dbdw,jxbuwei);
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
						zbLst = DianLiangGuanLiService.findHisData(dbGsid,dbCzid,dbXmid,dbFjid,zbsds,jibie,dbdw,jxbuwei);
					}
					break;
			}
		}
		response.getWriter().print(zbLst);
	}
	
	/*@RequestMapping("getAllDataJTDB")
	public void getAllDataJTDB(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException{
		String startTime = request.getParameter("starttime");
		String endTime = request.getParameter("endtime");
		String timeType = request.getParameter("timetype");
		String zbid = request.getParameter("zbid");
		String type = request.getParameter("type");
		String dbCell = request.getParameter("dbCell");
		String province = request.getParameter("province");
		
		//日期未选择S
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
		
		com.alibaba.fastjson.JSONArray zbLst = null;
		if(type == null){
			zbLst = JiTuanDuiBiaoService.findXiHuaData(timeObj.getString("startTime"),timeObj.getString("endTime"),timeType,timeObj.getString("week"),zbid,timeObj.getString("querytype"),province);
		}else{
			switch(type){
				case "1" : case "2" : 
					zbid = dbCell;
					//zbLst = JiTuanDuiBiaoService.findDuibiaoData(jtid,timeObj.getString("startTime"),timeObj.getString("endTime"),timeType,timeObj.getString("week"),zbid,timeObj.getString("querytype"),timeObj.getString("dbStartTime"),timeObj.getString("dbEndTime"),timeObj.getString("dbWeek"));
					if(zbLst.size() == 0){
						JSONObject error = new JSONObject();
						error.put("error", "缺少对标数据");
						response.getWriter().print(error);
						return;
					}
					break;
				case "3" :
					zbid = dbCell;
					//zbLst = JiTuanDuiBiaoService.findHisData(jtid,timeObj.getString("startTime"),timeObj.getString("endTime"),timeType,timeObj.getString("week"),zbid,timeObj.getString("querytype"),province);
					
					break;
			}
		}
		response.getWriter().print(zbLst);
	}*/
	
	@RequestMapping("getJxbuwei")
	public void getJxbuwei(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException{
		String dbdw = request.getParameter("dbdw");
		String jibie = request.getParameter("jibieid");
		
		String danwei = "";
		if(dbdw.equals("CNT")){
			danwei = "(次)";
		}
		if(dbdw.equals("LOSSDL")){
			if(jibie.equals("4")){
				danwei = "(kwh)";
			}else{
				danwei = "(万kwh)";
			}
		}
		if(dbdw.equals("LASTTIME")){
			danwei = "(小时)";
		}
		String jxbuwei = "";
		String buweiname = "";
		List<com.alibaba.fastjson.JSONObject> bwLst = FengjiDao.instance().findAllGZZL();
		for(com.alibaba.fastjson.JSONObject obj : bwLst){
			if(!obj.getString("id").equals("1")){
				jxbuwei += obj.getString("id")+",";
				buweiname += obj.getString("explain")+danwei+",";
			}
		}
		jxbuwei = jxbuwei.substring(0, jxbuwei.length()-1);
		buweiname = buweiname.substring(0, buweiname.length()-1);
		com.alibaba.fastjson.JSONObject buweiObj = new com.alibaba.fastjson.JSONObject();
		buweiObj.put("jxbuwei", jxbuwei);
		buweiObj.put("buweiname", buweiname);
		response.getWriter().print(buweiObj);
	}

	public boolean judgeContainsStr(String str) {  
        String regex=".*[a-zA-Z]+.*";  
        Matcher m=Pattern.compile(regex).matcher(str);  
        return m.matches();  
    } 
}
