package com.ieslab.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("t")
public class TestController {
	
	@RequestMapping("test")
	public void test(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {
		
		JSONObject obj = new JSONObject();
		
		obj.put("test", "测试！！！");
		response.setCharacterEncoding("UTF-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.print(obj);
		printWriter.close();
	}
}
