package com.leotech.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leotech.service.DeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("dev")
public class DeviceController {
	@RequestMapping("get_device_info")
	public void getDeviceInfo(HttpServletRequest request, HttpServletResponse response)
	{
		String code = request.getParameter("code");

		JSONObject dev_info = new JSONObject();
		if (code != null) {
			dev_info = DeviceService.getDeviceInfo(code);
		}

		try {
			response.getWriter().print(dev_info);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
