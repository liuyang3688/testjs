package com.leotech.controller;

import com.alibaba.fastjson.JSONObject;
import com.leotech.service.RtService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("rt")
public class RtController {
	@RequestMapping("get_rt_data")
	public void getRtData(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject datas = new JSONObject();
		datas = RtService.getRtData();
		try {
			response.getWriter().print(datas);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
