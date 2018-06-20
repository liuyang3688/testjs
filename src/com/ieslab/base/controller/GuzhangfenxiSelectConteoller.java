package com.ieslab.base.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.service.BaseModelCache;

@Controller
@RequestMapping("guzhangfenxiSelect")
public class GuzhangfenxiSelectConteoller {
		
	
	/**
	 * 根据公司id过滤场站id
	 * @param request
	 * @param response
	 */
	@RequestMapping("changzhanSelect")
	public void changzhanSelect(HttpServletRequest request, HttpServletResponse response){
		List<JSONObject> changzhanList = BaseModelCache.getInstance().getStaList();
		JSONArray res = new JSONArray();
		String gsids = request.getParameter("comids");
		for(JSONObject obj: changzhanList){
			JSONObject jsonObj = new JSONObject();
			if(!"0".equals(gsids) && (","+gsids+",").indexOf(","+obj.getString("powercorpid")+",") < 0){
				continue;
			}
			jsonObj.put("id", obj.getString("id"));
			jsonObj.put("mingzi", obj.getString("mingzi"));
			res.add(jsonObj);
		}
		
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 根据公司id，场站id 过滤项目id
	 * @param request
	 * @param response
	 */
	@RequestMapping("xiangmuSelect")
	public void xiangmuSelect(HttpServletRequest request, HttpServletResponse response){
		List<JSONObject> xiangmuList = BaseModelCache.getInstance().getXiangmuList();
		JSONArray res = new JSONArray();
		String gsids = request.getParameter("comids");
		String czids = request.getParameter("czids");
		if(gsids == null || gsids.length() == 0){
			gsids ="0";
		}
		if(czids == null || czids.length() == 0){
			czids ="0";
		}
		
		
		for(JSONObject obj: xiangmuList){
			JSONObject jsonObj = new JSONObject();
			if(!"0".equals(gsids) && (","+gsids+",").indexOf(","+obj.getString("powercorpid")+",") < 0){
				continue;
			}
			if(!"0".equals(czids) && (","+czids+",").indexOf(","+obj.getString("changzhanid")+",") < 0){
				continue;
			}
			jsonObj.put("id", obj.getString("id"));
			jsonObj.put("mingzi", obj.getString("mingzi"));
			res.add(jsonObj);
		}
		
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据公司id、场站id、项目id、过滤厂家
	 * @param request
	 * @param response
	 */
	@RequestMapping("changjiaSelect")
	public void changjiaSelect(HttpServletRequest request, HttpServletResponse response){
		List<JSONObject> fengjiList = BaseModelCache.getInstance().getFengjiList();
		Map<String, JSONObject> cjMap = BaseModelCache.getInstance().getCjMap();
		JSONArray res = new JSONArray();
		String gsids = request.getParameter("comids");
		String czids = request.getParameter("czids");
		String xmids = request.getParameter("xmids");
		Map<String,String> fjid_cjMap = new HashMap<String, String>();
		for(JSONObject obj: fengjiList){
			if(!"0".equals(gsids) && (","+gsids+",").indexOf(","+obj.getString("powercorpid")+",") < 0){
				continue;
			}
			if(!"0".equals(czids) && (","+czids+",").indexOf(","+obj.getString("changzhanid")+",") < 0){
				continue;
			}
			if(!"0".equals(xmids) && (","+xmids+",").indexOf(","+obj.getString("xiangmuid")+",") < 0){
				continue;
			}
			//得到所有符合条件的风机
			if(fjid_cjMap.containsKey(obj.getString("fjchangjia"))){
				continue;
			}
			String fjcjid_key = obj.getString("fjchangjia");
			if(cjMap.containsKey(obj.getString("fjchangjia"))){
				String fjcjName_val = cjMap.get(obj.getString("fjchangjia")).getString("mingzi");
				fjid_cjMap.put(fjcjid_key, fjcjName_val);
			}
		}
		
		for(Entry<String, String> vo : fjid_cjMap.entrySet()){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("id", vo.getKey());
			jsonObj.put("mingzi", vo.getValue());
			res.add(jsonObj);
        }
		
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据公司id、 场站id、项目id、厂家id 过滤型号
	 * @param request
	 * @param response
	 */
	@RequestMapping("xinghaoSelect")
	public void xinghaoSelect(HttpServletRequest request, HttpServletResponse response){
		Map<String, JSONObject> xhMap = BaseModelCache.getInstance().getXhMap();
		List<JSONObject> fengjiList = BaseModelCache.getInstance().getFengjiList();
		JSONArray res = new JSONArray();
		String gsids = request.getParameter("comids");
		String czids = request.getParameter("czids");
		String xmids = request.getParameter("xmids");
		String cjids = request.getParameter("cjids");
		if(cjids == null || cjids.length() == 0){
			cjids = "0";
		}
		
		
		
		Map<String,String> xhid_xhMap = new HashMap<String, String>();
		for(JSONObject obj: fengjiList){
			if(!"0".equals(gsids) && (","+gsids+",").indexOf(","+obj.getString("powercorpid")+",") < 0){
				continue;
			}
			if(!"0".equals(czids) && (","+czids+",").indexOf(","+obj.getString("changzhanid")+",") < 0){
				continue;
			}
			if(!"0".equals(xmids) && (","+xmids+",").indexOf(","+obj.getString("xiangmuid")+",") < 0){
				continue;
			}
			if(!"0".equals(cjids) && (","+cjids+",").indexOf(","+obj.getString("fjchangjia")+",") < 0){
				continue;
			}
			//得到所有符合条件的风机
			if(xhid_xhMap.containsKey(obj.getString("fjtypeid"))){
				continue;
			}
			xhid_xhMap.put(obj.getString("fjtypeid"), xhMap.get(obj.getString("fjtypeid")).getString("mingzi"));
		}
		
		
		for(Entry<String, String> vo : xhid_xhMap.entrySet()){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("id", vo.getKey());
			jsonObj.put("mingzi", vo.getValue());
			res.add(jsonObj);
        }
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("fengjiSelect")
	public void fengjiSelect(HttpServletRequest request, HttpServletResponse response){
		List<JSONObject> fengjiList = BaseModelCache.getInstance().getFengjiList();
		JSONArray res = new JSONArray();
		String gsids = request.getParameter("comids");
		String czids = request.getParameter("czids");
		String xmids = request.getParameter("xmids");
		String cjids = request.getParameter("cjids");
		String xhids = request.getParameter("xhids");
		if(cjids == null || cjids.length() == 0){
			cjids = "0";
		}
		if(xhids == null || xhids.length() == 0){
			xhids = "0";
		}
		for(JSONObject obj: fengjiList){
			JSONObject jsonObj = new JSONObject();
			if(!"0".equals(gsids) && (","+gsids+",").indexOf(","+obj.getString("powercorpid")+",") < 0){
				continue;
			}
			if(!"0".equals(czids) && (","+czids+",").indexOf(","+obj.getString("changzhanid")+",") < 0){
				continue;
			}
			if(!"0".equals(xmids) && (","+xmids+",").indexOf(","+obj.getString("xiangmuid")+",") < 0){
				continue;
			}
			if(!"0".equals(cjids) && (","+cjids+",").indexOf(","+obj.getString("fjchangjia")+",") < 0){
				continue;
			}
			if(!"0".equals(xhids) && (","+xhids+",").indexOf(","+obj.getString("fjtypeid")+",") < 0){
				continue;
			}
			
			jsonObj.put("id", obj.getString("id"));
			jsonObj.put("mingzi", obj.getString("mingzi"));
			res.add(jsonObj);
		}
		
		
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
