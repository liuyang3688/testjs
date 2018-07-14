package com.leotech.controller;

import java.io.IOException;
//import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.leotech.service.EventService;
@Controller
@RequestMapping("event")
public class EventController {
	@RequestMapping("get_all_event")
	public void getAllEvent(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject events = EventService.getAllEvent();
		try {
			response.getWriter().print(events);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
