package com.ieslab.base.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.service.BaseModelCache;
import com.ieslab.base.service.XingHaoService;

@Controller

@RequestMapping("xinghao")
public class XingHaoController {
	
	@RequestMapping("getAllXinghao")
	public void getAllXinghao(HttpServletRequest request, HttpServletResponse response){
		
		String czid = request.getParameter("czid");
		if(czid.length() == 0){
			czid = "0";
		}
		List<JSONObject> obj = new ArrayList<JSONObject>();
		List<JSONObject> xhList = XingHaoService.getAllXingHaoBychangzhan();
		if(!"0".equals(czid)){
			String cz[] = czid.split(",");
			for(int j = 0; j < cz.length; j++){
				for (int i = 0; i < xhList.size(); i++) {
					String changzhanid = xhList.get(i).getString("changzhanid"); 
					if(cz[j].equals(changzhanid)){
						obj.add(xhList.get(i));
					}
				}
			}
			
		}else{
			List<JSONObject> xinghaoList = BaseModelCache.getInstance().getXinghaoList();
			obj = xinghaoList;
		}
		try {
			response.getWriter().print(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("getAllXinghaoByChangjia")
	public void getAllXinghaoByChangjia(HttpServletRequest request, HttpServletResponse response){
		String cjid = request.getParameter("cjid");
		String xmid = request.getParameter("xmid");
		JSONArray res = new JSONArray();
		JSONArray jixingids = new JSONArray();
		if(cjid!=null && cjid.length()!=0 && xmid!=null && xmid.length()!=0){
			String[] cjids = cjid.split(",");
			String[] xmids = xmid.split(",");
			List<JSONObject> fjlist = BaseModelCache.getInstance().getFengjiList();
			for(JSONObject obj : fjlist){
				boolean bl = false;
				for(int i = 0;i < xmids.length;i++){
					for(int j = 0;j < cjids.length;j++){
						if(obj.getString("xiangmuid").equals(xmids[i]) && obj.getString("fjchangjia").equals(cjids[j])){
							bl = true;
						}
					}
				}
				if(bl){
					if(jixingids.size() == 0){
						jixingids.add(obj.getString("fjtypeid"));
					}else{
						boolean b = true;
						for(int i = 0;i < jixingids.size();i++){
							if(jixingids.get(i).equals(obj.getString("fjtypeid"))){
								b = false;
							}	
						}
						if(b){
							jixingids.add(obj.getString("fjtypeid"));
						}
					}
				}
			}
			
			List<JSONObject> xhlist = BaseModelCache.getInstance().getXinghaoList();
			for(JSONObject obj : xhlist){
				for(int i = 0;i < jixingids.size();i++){
					if(obj.getString("id").equals(jixingids.get(i))){
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
