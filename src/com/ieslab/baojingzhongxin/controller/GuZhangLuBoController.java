package com.ieslab.baojingzhongxin.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.baojingzhongxin.service.GuZhangLuBoCache;
import com.ieslab.baojingzhongxin.service.GuZhangLuBoService;
import com.ieslab.util.DateUtil;

/**
 * 
 * @author Li Hao
 *
 */
@Controller
@RequestMapping("guzhanglubo")
public class GuZhangLuBoController {
	
	/**
	 * 获取追忆保护信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryLuBoList")
	public void queryLuBoList(HttpServletRequest request , HttpServletResponse response){
		String czids = request.getParameter("czids");
		String timeType = request.getParameter("timetype");//时间类型
		String startTime = request.getParameter("starttime");
		String endTime = request.getParameter("endtime");
		
		//根据时间类型设置起始、终止时间
		if("0".equals(timeType)){//表示最近二十四小时
			startTime = DateUtil.formatdate(DateUtil.adddays(new Date(), -1), "yyyy-MM-dd HH:mm:ss");
			endTime = DateUtil.formatdate(new Date(), "yyyy-MM-dd HH:mm:ss");
		}else if("1".equals(timeType)){//按天查询
			if(startTime == null || startTime.length() == 0){
				return;
			}
			endTime = startTime + " 23:59:59";
			startTime = startTime + " 00:00:00";
			
		}else if("2".equals(timeType)){//按月查询
			if(startTime == null || startTime.length() == 0){
				return;
			}
			//获取这个月的天数
			long days = DateUtil.getDaysofMonth(DateUtil.parseDateStr(startTime, "yyyy-MM"));
			endTime = startTime + "-" + days +" 23:59:59";
			startTime = startTime + "-01 00:00:00";
		}else if("3".equals(timeType)){
			if(startTime == null || startTime.length() == 0){
				return;
			}
			endTime = startTime + "-12-31 23:59:59";
			startTime = startTime + "-01-01 00:00:00";
		}
		if(startTime == null || startTime.length() == 0){
			return;
		}
		
		GuZhangLuBoService service = new GuZhangLuBoService();
		
		List<JSONObject> res = service.parseStationPart(czids, startTime, endTime);
		
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 查询单个录波文件内容
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryLuBoChannelInfo")
	public void queryLuBoChannelInfo(HttpServletRequest request , HttpServletResponse response){
		String fileName = request.getParameter("filename");
		fileName = fileName.replace("jiahao", "+");
		
		if(!GuZhangLuBoCache.cacheMap.containsKey(fileName)){
			GuZhangLuBoService service = new GuZhangLuBoService();
			service.parseFileToCache(fileName);
		}	
		JSONObject dataRes = new JSONObject();
		if(GuZhangLuBoCache.cacheMap.containsKey(fileName)){
			dataRes.put("dchannel", GuZhangLuBoCache.cacheMap.get(fileName).get("dchannel"));
			dataRes.put("achannel", GuZhangLuBoCache.cacheMap.get(fileName).get("achannel"));
		}
		try {
			response.getWriter().print(dataRes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("queryLuBoChannelData")
	public void queryLuBoChannelData(HttpServletRequest request , HttpServletResponse response){
		String fileName = request.getParameter("filename");
		fileName = fileName.replace("jiahao", "+");
		String index = request.getParameter("index"); //通道的位置
		String type = request.getParameter("type");//表示是模拟通道还是数字通道
		
		if(!GuZhangLuBoCache.cacheMap.containsKey(fileName)){
			GuZhangLuBoService service = new GuZhangLuBoService();
			service.parseFileToCache(fileName);
		}	
		JSONArray dataRes = new JSONArray();
		if(GuZhangLuBoCache.cacheMap.containsKey(fileName)){
			dataRes = (JSONArray) GuZhangLuBoCache.cacheMap.get(fileName).get(type + index);
		}
		try {
			response.getWriter().print(dataRes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
