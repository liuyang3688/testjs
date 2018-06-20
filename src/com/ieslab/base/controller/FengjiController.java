package com.ieslab.base.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.service.BaseModelCache;
import com.ieslab.base.service.FengChangService;
import com.ieslab.base.service.FengjiService;
import com.ieslab.base.service.XiangmuService;
import com.ieslab.rdbdata.RdbwebservicePortType;
import com.ieslab.rdbdata.Rdbwebservice_ServiceLocator;
import com.ieslab.rdbdata.service_new.RdbWebServiceUtil;


@Controller

@RequestMapping("Fengji")
public class FengjiController {
	
	@RequestMapping("getFengji")
	public void getFengji(HttpServletRequest request, HttpServletResponse response){
		String czid = request.getParameter("czid");
		List<JSONObject> list = FengjiService.findAllFengjiByCzId(czid);	
		JSONArray arr = new JSONArray();
		for(int i = 1;i < list.size();i++){
			JSONObject t = list.get(i);
			JSONObject obj = new JSONObject();
			obj.put("id", t.getString("id"));
			obj.put("name", t.getString("mingzi") );
			obj.put("number",t.getString("bianhao")); //编号
			obj.put("pic", "fengji.png");
			
			obj.put("lat", t.getString("latitude"));
			obj.put("lon", t.getString("longtitude"));
			obj.put("powercorpid", t.getString("powercorpid"));
			obj.put("xiangmuid", t.getString("xiangmuid"));
			obj.put("changzhanid", t.getString("changzhanid"));
			obj.put("jidianxianid", t.getString("jidianxianid"));
			obj.put("fjtypeid", t.getString("fjtypeid"));
			arr.add(obj);
		}
		try {
			response.getWriter().print(arr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 通过公司信息获取风机
	 * @param request
	 * @param response
	 */
	@RequestMapping("getFengjiByComid")
	public void getFengjiByComid(HttpServletRequest request, HttpServletResponse response){
		String comid = request.getParameter("comid");
		if(comid == null){
			return;
		}
		
		JSONArray res = new JSONArray();
		
		List<JSONObject> dataList = FengChangService.findAllCZByCompany_hebing(comid);

//		for(Object dataObj: dataList){
		
			
			
//			JSONObject obj = new JSONObject();
//			obj.put("id", czObj.getString("id"));
//			obj.put("mingzi", czObj.getString("mingzi"));
//			obj.put("stationtype", czObj.getString("stationtype"));
//			
//			List<JSONObject> fjList = FengjiService.findAllFengjiByCzId(czObj.getString("id"));
//			obj.put("data", fjList);
//			
//			res.add(obj);
//		}
		
		
		try {
			response.getWriter().print(dataList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@RequestMapping("getFengjiByCzid")
	public void getFengjiByCzid(HttpServletRequest request, HttpServletResponse response){
		String czid = request.getParameter("czid");
		if(czid == null){
			return;
		}
		
		List<JSONObject> fjList = FengjiService.findAllFengjiByCzId(czid);
		
		
		try {
			response.getWriter().print(fjList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	 * 通过各种过滤信息获取风机
	 * @param request
	 * @param response
	 */
	@RequestMapping("getFengJiByFilters")
	public void getFengJiByFilters(HttpServletRequest request, HttpServletResponse response){
		
		String gsid = request.getParameter("gsid");
		String czid = request.getParameter("czid");
		String xmid = request.getParameter("xmid");
		String jdxid = request.getParameter("jdxid");
		String fjtype = request.getParameter("fjtype");
		
		if(gsid == null || gsid.length() == 0){
			gsid = "0";
		}
		if(czid == null || czid.length() == 0){
			czid = "0";
		}
		if(xmid == null || xmid.length() == 0){
			xmid = "0";
		}
		if(jdxid == null || jdxid.length() == 0){
			jdxid = "0";
		}
		if(fjtype == null || fjtype.length() == 0){
			fjtype = "0";
		}
		
		
		
		List<JSONObject> fjList = FengjiService.findAllFengjiByCzId(czid, xmid, jdxid, fjtype , gsid);
		
		try {
			response.getWriter().print(fjList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 通过各种过滤信息获取风机
	 * @param request
	 * @param response
	 */
	@RequestMapping("getFengJiByXmid")
	public void getFengJiByXmid(HttpServletRequest request, HttpServletResponse response){
		String xmid = request.getParameter("xmid");
		JSONArray res = new JSONArray();
		
		if(xmid == null || xmid.length() == 0){
			try {
				response.getWriter().print(res);
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String[] xmids = xmid.split(",");
		
		for(int i = 0;i < xmids.length;i++){
			for(JSONObject obj : FengjiService.findAllFengjiByCzId("0", xmids[i], "0", "0" , "0")){
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
	
	/**
	 * 通过域的开关找到这个开关关联的受累风机
	 * 调用webService接口
	 * kgids格式 8_1,8_2,,,,
	 * @param request
	 * @param response
	 */
	@RequestMapping("getSouLeiFjByKaiGuan")
	public void getSouLeiFjByKaiGuan(HttpServletRequest request , HttpServletResponse response){
		String kgIds = request.getParameter("kgids");
		JSONArray res = new JSONArray();
		//该map用来去重使用
		Map<String, String> map = new HashMap<String, String>();
		try{
			RdbwebservicePortType service = RdbWebServiceUtil.getRdbWebService();
			String data = service.getShouLeiFJList(kgIds);
			if(data != null){
				String dataArr[] = data.split(",");
				if(dataArr != null){
					for(String fjInfo: dataArr){
						if(fjInfo != null && fjInfo.length() > 0){
							String fjArr[] = fjInfo.split("_");
							if(fjArr != null){
								for(String fjid: fjArr){
									if(!map.containsKey(fjid)){
										if(BaseModelCache.getInstance().getFjMap().containsKey(fjid)){
											JSONObject obj = BaseModelCache.getInstance().getFjMap().get(fjid);
											res.add(obj);
											map.put(fjid, "");
										}
									}
								}
								
							}
						}
					}
				}
			}
		}catch(Exception e){
			System.out.println("通过域的开关找到这个开关关联的受累风机报错：" + e.getMessage());
		}
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * select 多选框风机查询
	 * @param request
	 * @param response
	 */
	@RequestMapping("getFengJiTree")
	public void getFengJiTree(HttpServletRequest request, HttpServletResponse response){
		String gsid = request.getParameter("comId");
		String czid = request.getParameter("czid");
		String xmid = request.getParameter("xmid");
		String fjtype = request.getParameter("fjtype");
		
		if(gsid == null || gsid.length() == 0){
			gsid = "0";
		}
		if(czid == null || czid.length() == 0){
			czid = "0";
		}
		if(xmid == null || xmid.length() == 0){
			xmid = "0";
		}
		if(fjtype == null || fjtype.length() == 0){
			fjtype = "0";
		}
		
		List<JSONObject> fjList = FengjiService.findFengjiTree(czid, xmid, fjtype , gsid);
		
		try {
			response.getWriter().print(fjList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("getFengjiToolTip")
	public void getFengjiToolTip(HttpServletRequest request, HttpServletResponse response){
		String fjid = request.getParameter("fjid");
		
		RdbwebservicePortType service = RdbWebServiceUtil.getRdbWebService();
		String res = "ERROR";
		if(service != null){
			try {
				res = service.getFJTipShowInfo(Integer.parseInt(fjid));
				if(res != null && res.length() > 0){
					try {
						res = new String(res.getBytes("iso8859-1"), "GBK");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						res = "ERROR";
					}
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				res = "ERROR";
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				res = "ERROR";
			}
		}
		
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取风机状态码和原始码
	 * @param request
	 * @param response
	 */
	@RequestMapping("getFengjiStateCode")
	public void getFengjiStateCode(HttpServletRequest request, HttpServletResponse response){
		String xingHao = request.getParameter("xinghao");
		
		String yuanshiCode = request.getParameter("yscode");
		String stateCode = request.getParameter("statecode");
		
		Map<String, String> stateMap = BaseModelCache.getInstance().getFjStateCodeMap();
		Map<String, String> ysMap = BaseModelCache.getInstance().getYuanshiCodeMap();
		
		String stateName = "", ysName = "";
		if(stateMap.containsKey(stateCode)){
			stateName = stateMap.get(stateCode);
		}
		if(ysMap.containsKey(xingHao + "_" + yuanshiCode)){
			ysName = ysMap.get(xingHao + "_" + yuanshiCode);
		}
		JSONObject res = new JSONObject();
		
		res.put("state", stateName);
		res.put("ys", ysName);
		
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@RequestMapping("getFengjiXmInfoByCzid")
	public void getFengjiXmInfoByCzid(HttpServletRequest request, HttpServletResponse response){
		String czid = request.getParameter("czid");
		if(czid == null){
			return;
		}
		
		JSONArray res = new JSONArray();
		
		List<JSONObject> xmList = XiangmuService.findXiangmuByCzid(czid);
		
		for(JSONObject xmObj: xmList){
			
			JSONObject obj = new JSONObject();
			obj.put("id", xmObj.getString("id"));
			obj.put("mingzi", xmObj.getString("mingzi"));
			
			List<JSONObject> fjList = FengjiService.findAllFengjiByXmId(xmObj.getString("id"));
			obj.put("data", fjList);
			
			res.add(obj);
		}
		
		
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
