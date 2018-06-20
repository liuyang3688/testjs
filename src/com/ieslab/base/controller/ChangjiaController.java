package com.ieslab.base.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.service.BaseModelCache;

@Controller
@RequestMapping("changjia")
public class ChangjiaController {
	/**
	 * 通过厂家信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("getChangJiaInfoByStation")
	public void getChangJiaInfoByStation(HttpServletRequest request , HttpServletResponse response){
		String xmid = request.getParameter("xmid");
		JSONArray res = new JSONArray();
		JSONArray changjiaids = new JSONArray();
		if(xmid!=null && xmid.length()!=0){
			String[] xmids = xmid.split(",");
			List<JSONObject> fjlist = BaseModelCache.getInstance().getFengjiList();
			for(JSONObject obj : fjlist){
				boolean bl = false;
				for(int j = 0;j < xmids.length;j++){
					if(xmids[j].equals(obj.getString("xiangmuid"))){
						bl = true;
					}
				}
				if(bl){
					if(changjiaids.size() == 0){
						changjiaids.add(obj.getString("fjchangjia"));
					}else{
						boolean b = true;
						for(int i = 0;i < changjiaids.size();i++){
							if(changjiaids.get(i).equals(obj.getString("fjchangjia"))){
								b = false;
							}	
						}
						if(b){
							changjiaids.add(obj.getString("fjchangjia"));
						}
					}
				}
			}
			
			List<JSONObject> cjlist = BaseModelCache.getInstance().getChangjiaList();
			for(JSONObject obj : cjlist){
				for(int i = 0;i < changjiaids.size();i++){
					if(obj.getString("id").equals(changjiaids.get(i))){
						res.add(obj);
					}
				}
			}
		}
		//List<JSONObject> list = ChangjiaService.getChangJiaByStation(cjid)
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
