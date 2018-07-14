package com.leotech.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.leotech.service.LabelService;
@Controller
@RequestMapping("label")
public class LabelController {
	@RequestMapping("get_all_label")
	public void getAllLabel(HttpServletRequest request, HttpServletResponse response)
	{
		JSONArray labels = new JSONArray();
		List<JSONObject> list = LabelService.getAllLabel();
		for(JSONObject mesh: list){
			labels.add(mesh);
		}
		try {
			String json = JSON.toJSONStringWithDateFormat(labels, "yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect);
			response.getWriter().print(json);
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
			if(LabelService.updateIsDirty_All()){
				ret.put("retcode", 1);
			} else {
				ret.put("retcode", 0);
			}
		} else {
			int uuid = Integer.parseInt(strUuid);
			if(LabelService.updateIsDirty(uuid)){
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
