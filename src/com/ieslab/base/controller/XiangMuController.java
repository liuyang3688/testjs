package com.ieslab.base.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.service.XiangmuService;


@Controller
@RequestMapping("xiangmu")
public class XiangMuController {
	/**
	 * 根据厂站ID获取项目信息
	 * @param request
	 * @param response
	 * @throws JSONException
	 */
	@RequestMapping("getXiangMuByCzid")
	public void getAllCompany(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String czid = request.getParameter("czid");
		String gsid = request.getParameter("gsid");
		List<JSONObject> list = XiangmuService.findXiangmuByCzid(gsid, czid);
		try {
			response.getWriter().print(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("selectXiangmu")
	public void selectXiangmu(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String czid = request.getParameter("czid");
		//String comId = request.getParameter("comId");
		String zclb = request.getParameter("zclb");
		List<JSONObject> list = XiangmuService.selectXiangmu(czid);
		if(zclb!=null&&zclb.length()!=0){
			String [] zclbs = zclb.split(",");
			for(int i = 0; i < list.size(); i++){
				boolean b = true;
				for(int j = 0; j < zclbs.length; j++){
					if(list.get(i).getString("sctype").equals(zclbs[j])){
						b = false;
					}
				}
				if(b){
					list.remove(i);
				}
			}
		}
		try {
			response.getWriter().print(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("getXiangMuSelect")
	public void getXiangMuSelect(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException{
		String czid = request.getParameter("czid");
		String zclb = request.getParameter("zclb");
		JSONArray res = new JSONArray();
		if(zclb==null || zclb.equals("") || czid==null || czid.equals("")){
			response.getWriter().print(res);
			return;
		}
		List<JSONObject> list = XiangmuService.selectXiangmu(czid);
		
		String[] zclbs = zclb.split(",");
		for(JSONObject obj : list){
			for(int i = 0;i < zclbs.length;i++){
				if(obj.getString("sctype").equals(zclbs[i])){
					res.add(obj);
				}
			}
		}
		
		/*if(czid.indexOf(",") > -1){
			String[] ids = czid.split(",");
			for(int i = 0;i < ids.length;i++){
				List<JSONObject> lists = XiangmuService.findXiangmuByCzid(ids[i]);
				if(lists!=null){
					res.add(lists.get(0));
				}
			}
		}
		if(czid.indexOf(",") == -1 && czid!=null && czid!=""){
			List<JSONObject> list = XiangmuService.findXiangmuByCzid(czid);
			res.add(list.get(0));
		}
		if(zclb!=null&&zclb.length()!=0){
			JSONArray res2 = JSONArray.parseArray(res.toJSONString());
			res.clear();
			String[] zclbs = zclb.split(",");
			for(int j = 0; j < zclbs.length; j++){
				for(int i = 0; i < res2.size(); i++){
					if(res2.getJSONObject(i).getString("scType").equals(zclbs[j])){
						res.add(res2.getJSONObject(i));
					}
				}
			}
		}*/
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
