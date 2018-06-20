package com.ieslab.baojingzhongxin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.baojingzhongxin.dao.DaibangongzuoDao;

@Controller
@RequestMapping("daibangongzuo")
public class DaibangongzuoController {
	
	/**
	 * 检修部位查库
	 * @param request
	 * @param response
	 */
	@RequestMapping("jxbuwei")
	public void jxbuwei(HttpServletRequest request, HttpServletResponse response){
		DaibangongzuoDao daibangongzuo = new DaibangongzuoDao();
		List<JSONObject> list = daibangongzuo.getJXbuwei();
		JSONArray res = new JSONArray();
		for(JSONObject obj : list){
			res.add(obj);
		}
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
