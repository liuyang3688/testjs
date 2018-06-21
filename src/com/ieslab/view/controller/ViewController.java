package com.ieslab.view.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller

public class ViewController {

	@RequestMapping("view.do")
	public String getView(HttpServletRequest request, HttpServletResponse response){
		String name = request.getParameter("name");
		if(name == null || name.length() == 0){
			name = "index";
		}
		return name;
		
	}
}
