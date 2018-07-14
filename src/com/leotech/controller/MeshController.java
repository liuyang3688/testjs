package com.leotech.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leotech.service.MeshService;
@Controller
@RequestMapping("mesh")
public class MeshController {
	@RequestMapping("get_all_tpl")
	public void getAllTpl(HttpServletRequest request, HttpServletResponse response)
	{
		JSONArray tpls = MeshService.getAllTpl();
		try {
			response.getWriter().print(tpls);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("get_all_mesh")
	public void getAllMesh(HttpServletRequest request, HttpServletResponse response)
	{
		JSONArray meshes = new JSONArray();
		List<JSONObject> list = MeshService.getAllMesh();
		for(JSONObject mesh: list){
			meshes.add(mesh);
		}
		try {
			response.getWriter().print(meshes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("update_isdirty")
	public void updateIsDirty(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject ret = new JSONObject();
		String strUuid = request.getParameter("uuid");
		if( strUuid==null || strUuid.isEmpty()) {
			if(MeshService.updateIsDirty_All()){
				ret.put("retcode", 1);
			} else {
				ret.put("retcode", 0);
			}
		} else {
			int uuid = Integer.parseInt(strUuid);
			if(MeshService.updateIsDirty(uuid)){
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
