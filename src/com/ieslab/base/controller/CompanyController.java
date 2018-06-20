package com.ieslab.base.controller;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.model.Gongsi;
import com.ieslab.base.service.BaseModelCache;


@Controller
@RequestMapping("company")
public class CompanyController {
	
	@RequestMapping("getAllCompany")
	public void getAllCompany(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String JGtype = request.getParameter("type"); 
		String JiGouID = request.getParameter("JiGouID");
		String dwlb = request.getParameter("dwlb"); 
		String zclb = request.getParameter("zclb");
		if(JGtype == null){
			JGtype = "0";
		}
		if(JiGouID == null){
			JiGouID = "1";
		}
		
		JSONArray res = new JSONArray();
		
		//根据公司ID和场站类型获取这个公司下分子公司的名称、公司类型、所在省份和经纬度
		List<com.alibaba.fastjson.JSONObject> list = BaseModelCache.getInstance().getGongsiList();
		for(com.alibaba.fastjson.JSONObject fzgs: list){
			com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
			if(JGtype.equals("0") ){ // jgtype=0 表示全部
				if(fzgs.getString("jgType").equals("1") || fzgs.getString("jgType").equals("2") || fzgs.getString("jgType").equals("3")){
					obj.put("id",fzgs.get("id"));
					obj.put("name",fzgs.get("mingzi"));
					obj.put("JGType",fzgs.get("jgType"));
					obj.put("lat",fzgs.get("latitude"));
					obj.put("lnt",fzgs.get("longtitude"));
					obj.put("Address",fzgs.get("address"));
					obj.put("province", fzgs.get("address"));
					obj.put("CLFJCaps" , fzgs.get("CLFJCaps"));
					obj.put("ZLFJCaps" ,fzgs.get("ZLFJCaps"));
					obj.put("CLGFCaps" ,fzgs.get("CLGFCaps"));
					obj.put("ZLGFCaps" ,fzgs.get("ZLGFCaps"));
					obj.put("zongZjrl" ,fzgs.get("zongZjrl"));
					res.add(obj);
				}
			}else{ // jgtype=1 表示风电
				if(fzgs.getString("jgType").equals(JGtype) || fzgs.getString("jgType").equals("3")){
					obj.put("id",fzgs.get("id"));
					obj.put("name",fzgs.get("mingzi"));
					obj.put("JGType",fzgs.get("jgType"));
					obj.put("lat",fzgs.get("latitude"));
					obj.put("lnt",fzgs.get("longtitude"));
					obj.put("Address",fzgs.get("address"));
					obj.put("province", fzgs.get("address"));
					obj.put("CLFJCaps" , fzgs.get("CLFJCaps"));
					obj.put("ZLFJCaps" ,fzgs.get("ZLFJCaps"));
					obj.put("CLGFCaps" ,fzgs.get("CLGFCaps"));
					obj.put("ZLGFCaps" ,fzgs.get("ZLGFCaps"));
					res.add(obj);
				}
			}
		}
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@RequestMapping("getCompanyById")
	public void getCompanyById(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String gsid = request.getParameter("gsid");
		JSONObject res = new JSONObject();
		if(gsid != null && gsid.length() > 0){
			List<JSONObject> list = BaseModelCache.getInstance().getGongsiList();
			
			if(list != null){
				for(JSONObject obj: list){
					if(gsid.equals(obj.getString("id"))){
						res = obj;
					}
				}
			}
		}
		
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("getAllCompanySelect")
	public void getAllCompanySelect(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String JiGouID = "1";
		String dwlb = request.getParameter("dwlb"); 
		String zclb = request.getParameter("zclb");
		JSONArray res = new JSONArray();
		List<com.alibaba.fastjson.JSONObject> list = BaseModelCache.getInstance().getGongsiList();
		if(dwlb!=null&&dwlb.length()!=0 && zclb!=null&&zclb.length()!=0){
			String[] dwlbs = dwlb.split(",");
			String[] zclbs = zclb.split(",");
			for(int i = 0;i < dwlbs.length;i++){
				for(int j = 0;j < zclbs.length;j++){
					for(com.alibaba.fastjson.JSONObject fzgs: list){
						if(fzgs.getString("jgType").equals(dwlbs[i]) && fzgs.getString("scType").equals(zclbs[j])){
							com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
							obj.put("id",fzgs.get("id"));
							obj.put("name",fzgs.get("mingzi"));
							res.add(obj);
						}
					}
				}
			}
		}else{
			for(com.alibaba.fastjson.JSONObject fzgs: list){
				com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
				obj.put("id",fzgs.get("id"));
				obj.put("name",fzgs.get("mingzi"));
				res.add(obj);
			}
		}
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("getAllJituanSelect")
	public void getAllJituanSelect(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		List<Gongsi> jtLst = BaseModelCache.getInstance().getJituanList();
		JSONArray res = new JSONArray();
		for(Gongsi g : jtLst){
			JSONObject obj = new JSONObject();
			obj.put("mingzi", g.getMingzi());
			obj.put("id", g.getID());
			res.add(obj);
		}
		Map<String, JSONObject> ExtgsMap = BaseModelCache.getInstance().getExtGsMap();
		List<JSONObject> ExtgsLst = BaseModelCache.getInstance().getExtgsList();
		List<JSONObject> ExtczLst = BaseModelCache.getInstance().getExtczList();
		if(ExtczLst!=null && ExtczLst.size()!=0){
			for(JSONObject o : ExtgsLst){
				JSONObject obj = new JSONObject();
				obj.put("mingzi", o.getString("mingzi"));
				obj.put("id", o.get("id"));
				res.add(obj);
			}
		}
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
