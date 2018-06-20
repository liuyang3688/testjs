package com.ieslab.app.index;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.service.BaseModelCache;

@Controller
@RequestMapping("index")
public class IndexController {
	
	@RequestMapping("get_sta_list")
	public void getStaList(HttpServletRequest request, HttpServletResponse response) throws JSONException, UnsupportedEncodingException{
		JSONArray res = new JSONArray();
		List<JSONObject> list = BaseModelCache.getInstance().getCZAll();
		for(  JSONObject changzhan: list){
			JSONObject obj = new com.alibaba.fastjson.JSONObject();
			obj.put("id", changzhan.get("id"));
			obj.put("name", ((String) changzhan.get("mingzi")));
			res.add(obj);
		}
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("Update")
	public void Update(HttpServletRequest request, HttpServletResponse response) throws JSONException, UnsupportedEncodingException{
//		JSONArray res = new JSONArray();
//		List<JSONObject> list = BaseModelCache.getInstance().getCZAll();
//		for(  JSONObject changzhan: list){
//			
//			obj.put("id", changzhan.get("id"));
//			obj.put("name", ((String) changzhan.get("mingzi")));
//			res.add(obj);
//		}
		JSONObject res = new com.alibaba.fastjson.JSONObject();
		res.put("NewVer",  "0.17");
		res.put("UpdateContent",  "UpdateContent");
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
//		String comid = request.getParameter("comid");
//		String type = request.getParameter("type"); //0 表示全部 1表示风电   2 表示光伏
//		if(type == null || type.length() == 0){
//			type = "0";
//		}
//		String shenghanzi = request.getParameter("shenghanzi");
//		JSONArray res = new JSONArray();
//		List<com.alibaba.fastjson.JSONObject> list = BaseModelCache.getInstance().getFengchang();
//		
//		for(com.alibaba.fastjson.JSONObject changzhan: list){
//			
//			com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
//			
//			if((comid == null || comid.length() == 0)&& shenghanzi != null ){
//				if(changzhan.get("province").equals(shenghanzi)){
//					obj.put("id", changzhan.get("id"));
//					obj.put("netId", changzhan.get("netid"));
//					obj.put("name", ((String) changzhan.get("mingzi")).replaceAll("风场", ""));
//					obj.put("lgt", changzhan.get("latitude"));
//					obj.put("lat", changzhan.get("longtitude"));
//					obj.put("province", changzhan.get("province"));
//					obj.put("powercorpid", changzhan.get("powercorpid"));
//					obj.put("stationtype", changzhan.get("stationtype"));
//					obj.put("zlfjxmcaps", changzhan.get("zlfjxmcaps"));
//					obj.put("clfjxmcaps", changzhan.get("clfjxmcaps"));
//					obj.put("zlgfxmcaps", changzhan.get("zlgfxmcaps"));
//					obj.put("clgfxmcaps", changzhan.get("clgfxmcaps"));
//					res.add(obj);
//				}
//			}else{
//				if(changzhan.get("powercorpid").equals(comid) || "0".equals(comid)){
//					//如果查询全部的风场 就放进去
//					if("0".equals(type)){
//						obj.put("id", changzhan.get("id"));
//						obj.put("netId", changzhan.get("netid"));
//						obj.put("name", ((String) changzhan.get("mingzi")).replaceAll("风场", ""));
//						obj.put("lgt", changzhan.get("latitude"));
//						obj.put("lat", changzhan.get("longtitude"));
//						obj.put("province", changzhan.get("province"));
//						obj.put("powercorpid", changzhan.get("powercorpid"));
//						obj.put("stationtype", changzhan.get("stationtype"));
//						obj.put("zlfjxmcaps", changzhan.get("zlfjxmcaps"));
//						obj.put("clfjxmcaps", changzhan.get("clfjxmcaps"));
//						obj.put("zlgfxmcaps", changzhan.get("zlgfxmcaps"));
//						obj.put("clgfxmcaps", changzhan.get("clgfxmcaps"));
//						res.add(obj);
//					}else if(changzhan.get("stationtype").equals(type) || changzhan.get("stationtype").equals("3")){ //如果不是，那就根据光伏和风电过滤
//						obj.put("id", changzhan.get("id"));
//						obj.put("netId", changzhan.get("netid"));
//						obj.put("name", ((String) changzhan.get("mingzi")).replaceAll("风场", ""));
//						obj.put("lgt", changzhan.get("latitude"));
//						obj.put("lat", changzhan.get("longtitude"));
//						obj.put("province", changzhan.get("province"));
//						obj.put("powercorpid", changzhan.get("powercorpid"));
//						obj.put("stationtype", changzhan.get("stationtype"));
//						obj.put("zlfjxmcaps", changzhan.get("zlfjxmcaps"));
//						obj.put("clfjxmcaps", changzhan.get("clfjxmcaps"));
//						obj.put("zlgfxmcaps", changzhan.get("zlgfxmcaps"));
//						obj.put("clgfxmcaps", changzhan.get("clgfxmcaps"));
//						res.add(obj);
//					}
//				}
//			}
//		}
//		try {
//			response.getWriter().print(res);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	
//	@RequestMapping("getChangzhannameByGongsi")
//	public void getChangzhannameByGongsi(HttpServletRequest request, HttpServletResponse response) throws JSONException{
//		String gsid = request.getParameter("gsid");
//		String zclb = request.getParameter("zclb");
//		if(gsid == null || gsid.length() == 0){
//			gsid = "0";
//		}
//		if(zclb == null || zclb.length() == 0){
//			zclb = "0";
//		}
//		JSONArray res = new JSONArray();
//		if(gsid != null && gsid !=""){
//			List<com.alibaba.fastjson.JSONObject> list = BaseModelCache.getInstance().getFengchang();
//			//多个子公司id
//			if(gsid.indexOf(",") > -1){
//				String[] ids = gsid.split(",");
//				for(int i = 0;i < ids.length;i++){
//					for(com.alibaba.fastjson.JSONObject changzhan: list){
//						com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
//						if(list != null && list.size() > 0){
//							if(changzhan.get("powercorpid").equals(ids[i]) || "0".equals(ids[i])){
//								obj.put("id", changzhan.get("id"));
//								obj.put("netId", changzhan.get("netid"));
//								obj.put("name", ((String) changzhan.get("mingzi")).replaceAll("风场", ""));
//								obj.put("lat", changzhan.get("latitude"));
//								obj.put("lgt", changzhan.get("longtitude"));
//								obj.put("scType", changzhan.get("scType"));
//								obj.put("province", changzhan.get("province"));
//								obj.put("powercorpid", changzhan.get("powercorpid"));
//								res.add(obj);
//							}
//						}
//					}
//				}
//			}else{
//				for(com.alibaba.fastjson.JSONObject changzhan: list){
//					com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
//					if(list != null && list.size() > 0){
//						if(changzhan.get("powercorpid").equals(gsid) || "0".equals(gsid)){
//							obj.put("id", changzhan.get("id"));
//							obj.put("netId", changzhan.get("netid"));
//							obj.put("name", ((String) changzhan.get("mingzi")).replaceAll("风场", ""));
//							obj.put("lat", changzhan.get("latitude"));
//							obj.put("lgt", changzhan.get("longtitude"));
//							obj.put("scType", changzhan.get("scType"));
//							obj.put("province", changzhan.get("province"));
//							obj.put("powercorpid", changzhan.get("powercorpid"));
//							res.add(obj);
//						}
//					}
//				}
//			}
//		}
//
//		
//		/*
//		 * 资产类别过滤，因为检修工单场站下拉框初始化的时候不需要按资产类别过滤
//		 * 资产类别和场站没关系， 所以将这段代码封掉。
//		 */
////		if(zclb!=null && zclb.length()!=0){
////			JSONArray res2 = JSONArray.parseArray(res.toJSONString());
////			res.clear();
////			String[] zclbs = zclb.split(",");
////			for(int j = 0; j < zclbs.length; j++){
////				for(int i = 0; i < res2.size(); i++){
////					if(res2.getJSONObject(i).getString("scType").equals(zclbs[j])){
////						res.add(res2.getJSONObject(i));
////					}
////				}
////			}
////		}
//		try {
//			response.getWriter().print(res);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	@RequestMapping("getChangzhanById")
//	public void getCompanyById(HttpServletRequest request, HttpServletResponse response) throws JSONException{
//		String czid = request.getParameter("czid");
//		JSONObject res = new JSONObject();
//		if(czid != null && czid.length() > 0){
//			List<JSONObject> list = BaseModelCache.getInstance().getFengchang();
//			
//			if(list != null){
//				for(JSONObject obj: list){
//					if(czid.equals(obj.getString("id"))){
//						res = obj;
//					}
//				}
//			}
//		}
//		
//		try {
//			response.getWriter().print(res);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	@RequestMapping("getChangzhanname")
//	public void getChangzhanname(HttpServletRequest request, HttpServletResponse response) throws JSONException{
//		String gsid = request.getParameter("gsid");
//		JSONArray res = new JSONArray();
//		if(!gsid.equals("") && gsid!=null){
//			List<JSONObject> czLst = BaseModelCache.getInstance().getFengchang();
//			String[] gsids = gsid.split(",");
//			for(JSONObject o : czLst){
//				for(int i = 0;i < gsids.length;i++){
//					if(o.get("powercorpid").equals(gsids[i])){
//						JSONObject obj = new JSONObject();
//						obj.put("id", o.getString("id"));
//						obj.put("name", o.getString("mingzi").replaceAll("风场", ""));
//						res.add(obj);
//					}
//				}
//			}
//		}
//		try {
//			response.getWriter().print(res);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	@RequestMapping("getAllFengchang")
//	public void getAllFengchang(HttpServletRequest request, HttpServletResponse response){
//		List<JSONObject> list = BaseModelCache.getInstance().getFengchang();
//		
//		JSONArray res = new JSONArray();
//		for(JSONObject obj : list){
//			JSONObject o = new JSONObject();
//			if("1".equals(obj.getString("stationtype"))){
//				o.put("id", obj.get("id"));
//				o.put("netId", obj.get("netid"));
//				o.put("mingzi", ((String) obj.get("mingzi")).replaceAll("风场", ""));
//				o.put("lat", obj.get("latitude"));
//				o.put("lon", obj.get("longtitude"));
//				o.put("scType", obj.get("scType"));
//				o.put("province", obj.get("province"));
//				o.put("powercorpid", obj.get("powercorpid"));
//				res.add(o);
//			}
//		}
//		try {
//			response.getWriter().print(res);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//		

