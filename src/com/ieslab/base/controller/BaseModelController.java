package com.ieslab.base.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.dao.BaseTableDao;
import com.ieslab.base.service.BaseModelCache;
import com.ieslab.util.QueryConfig;


@Controller
@RequestMapping("base")
public class BaseModelController {
	
	@RequestMapping("getBJObjByBjTypeAndBjId")
	public void getBJObjByBjTypeAndBjId(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String bjtype = request.getParameter("bjtype"); 
		String bjid = request.getParameter("bjid");
		if(bjtype == null){
			return;
		}
		if(bjid == null){
			return;
		}
		
		JSONObject res = new JSONObject();
		res = BaseModelCache.getInstance().getBjObjByBjTypeAndBjId(bjtype, bjid);
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@RequestMapping("getBjlx")
	public void getBjlx(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		
		
		List<JSONObject> res = BaseModelCache.getInstance().getBjTypeList();
		if(res != null){
			try {
				response.getWriter().print(res);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	@RequestMapping("getTableInfoByCzid")
	public void getTableInfoByCzid(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		
		String czid = request.getParameter("czid");
		String table = request.getParameter("table");
		if(czid == null || czid.length() == 0){
			return;
		}
		if(table == null || table.length() == 0){
			return;
		}
		List<JSONObject> res = BaseTableDao.instance().getTableInfoByCzid(table, czid);
		if(res != null){
			try {
				response.getWriter().print(res);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping("getAreaTypeByCz")
	public void getAreaTypeByCz(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String czid = request.getParameter("czid");
		
		List<JSONObject> list = BaseModelCache.getInstance().getAreaTypeList();
		List<JSONObject> res = new ArrayList<JSONObject>();
		Map<String, String> isHas = new HashMap<String, String>();
		for(JSONObject obj: list){
			if(czid != null && czid.equals(obj.getString("changzhanid"))){
				if(!isHas.containsKey(obj.getString("fieldid"))){
					res.add(obj);
					isHas.put(obj.getString("fieldid"), obj.getString("fieldid"));
				}
			}
		}
		
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 通过场站ID和域ID获取该域关联的部件信息
	 * @param request
	 * @param response
	 * @throws JSONException
	 */
	@RequestMapping("getAreaBuJianByCzAndAreaId")
	public void getAreaBuJianByCzAndAreaId(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		
		String czid = request.getParameter("czid");
		String jxqy = request.getParameter("jxqy");
		
		List<JSONObject> list = BaseModelCache.getInstance().getAreaTypeList();
		List<JSONObject> res = new ArrayList<JSONObject>();
		Map<String, String> map = new HashMap<String, String>();
		for(JSONObject obj: list){
			if(czid != null && czid.equals(obj.getString("changzhanid"))
					&& jxqy != null && ("," + jxqy + ",").indexOf("," + obj.getString("fieldid") + ",") >= 0){
				
				String bjlx = obj.getString("componenttype");
				String bjid = obj.getString("componentid");
				
				JSONObject o = BaseModelCache.getInstance().getBjObjByBjTypeAndBjId(bjlx, bjid);
				
				if(o != null){
					obj.put("bjname", o.getString("mingzi"));
					if(!map.containsKey(obj.getString("fieldid") + "_" + bjlx + "_" + bjid)){
						res.add(obj);
						map.put(obj.getString("fieldid") + "_" + bjlx + "_" + bjid, "");
					}
					
				}
				
			}
		}
		
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取公司类型
	 * @param request
	 * @param response
	 * @throws JSONException
	 */
	@RequestMapping("getCompanyType")
	public void getCompanyType(HttpServletRequest request, HttpServletResponse response){
		String type = QueryConfig.getInstance().getCompanyType();
		try {
			response.getWriter().print(type);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
