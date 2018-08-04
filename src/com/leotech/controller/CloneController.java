package com.leotech.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.leotech.service.CloneService;
@Controller
@RequestMapping("clone")
public class CloneController {
	@RequestMapping("get_all_clone")
	public void getAllClone(HttpServletRequest request, HttpServletResponse response)
	{
		JSONArray clones = new JSONArray();
		List<JSONObject> list = CloneService.getAllClone();
		for(JSONObject mesh: list){
			clones.add(mesh);
		}
		try {
			response.getWriter().print(clones);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("get_all_device")
	public void getAllDevice(HttpServletRequest request, HttpServletResponse response)
	{
		JSONArray devices = new JSONArray();
		devices = CloneService.getAllDevice();
		try {
			response.getWriter().print(devices);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("get_all_cab")
	public void getAllCab(HttpServletRequest request, HttpServletResponse response) {
		JSONArray cabs = new JSONArray();
        cabs = CloneService.getAllCab();
		try {
			response.getWriter().print(cabs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("get_all_area")
	public void getAllArea(HttpServletRequest request, HttpServletResponse response) {
		JSONArray areas = new JSONArray();
		areas = CloneService.getAllArea();
		try {
			response.getWriter().print(areas);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("get_all_sys")
	public void getAllSys(HttpServletRequest request, HttpServletResponse response) {
		JSONArray syss = new JSONArray();
		syss = CloneService.getAllSys();
		try {
			response.getWriter().print(syss);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("get_all_power")
	public void getAllPower(HttpServletRequest request, HttpServletResponse response) {
		JSONArray powers = new JSONArray();
		powers = CloneService.getAllPower();
		try {
			response.getWriter().print(powers);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("update_isdirty")
	public void updateIsDirty(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject ret = new JSONObject();
		String strUuid = request.getParameter("uuid");
		if(strUuid==null || strUuid.isEmpty()) {
			if(CloneService.updateIsDirty_All()){
				ret.put("retcode", 1);
			} else {
				ret.put("retcode", 0);
			}
		} else {
			int uuid = Integer.parseInt(strUuid);
			if(CloneService.updateIsDirty(uuid)){
				ret.put("retcode", 1);
			} else {
				ret.put("retcode", 0);
			}
		}
		
		try {
			response.getWriter().print(ret);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
