package com.leotech.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leotech.service.ButtonService;
@Controller
@RequestMapping("button")
public class ButtonController {
	@RequestMapping("get_all_btn")
	public void getAllBtn(HttpServletRequest request, HttpServletResponse response)
	{
		JSONArray btns = ButtonService.getAllBtn();
		try {
			response.getWriter().print(btns);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}