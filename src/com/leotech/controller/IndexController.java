package com.leotech.controller;

import java.io.IOException;

//import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.leotech.service.LabelService;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
import com.leotech.service.MeshService;
import com.leotech.service.CloneService;
@Controller
public class IndexController {
	@RequestMapping("test")
	public void test(HttpServletRequest request, HttpServletResponse response)
	{
		try {
			response.getWriter().print("Hello");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("index")
	public String index(HttpServletRequest request, HttpServletResponse response)
	{
//		MeshService.updateIsDirty_All(true);
//		CloneService.updateIsDirty_All(true);
//		LabelService.updateIsDirty_All(true);
		return "index";
	}
}
