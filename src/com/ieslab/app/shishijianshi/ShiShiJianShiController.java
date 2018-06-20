package com.ieslab.app.shishijianshi;
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
import com.ieslab.rdbdata.RdbwebservicePortType;
import com.ieslab.rdbdata.service_new.ParamInfo;
import com.ieslab.rdbdata.service_new.ParamManager;
import com.ieslab.rdbdata.service_new.RdbWebServiceUtil;
import com.ieslab.app.index.*;
@Controller
@RequestMapping("ShiShiJianShi")
public class ShiShiJianShiController {
	public class DATAID {
		private int BJLX;
		private int BJID;
		private int BJCS;
		public DATAID()
		{
			BJLX = 0;
			BJID = 0;
			BJCS = 0;
		}
		public String toString()
		{
			return BJLX + "," + BJCS + "," + BJID;
		}
	}

	private final static CSInfo DWCSInfo[] = new CSInfo[]{
		new CSInfo("DW_FJSecFaDianNum",MiaoShuInfo.CS_DW_CLFJFaDianNum),
		new CSInfo("DW_FJSecDaiFengNum",MiaoShuInfo.CS_DW_CLFJDaiFengNum),
		new CSInfo("DW_FJSecWeiHuNum",MiaoShuInfo.CS_DW_CLFJTingJiNum),
		new CSInfo("DW_FJSecShouLeiNum",MiaoShuInfo.CS_DW_CLFJWeiHuNum),
		new CSInfo("DW_FJSecGuZhuangNum",MiaoShuInfo.CS_DW_CLFJShouLeiNum),
		new CSInfo("DW_FJSecLiXianNum",MiaoShuInfo.CS_DW_CLFJQiTaNum),
		new CSInfo("DW_P",MiaoShuInfo.CS_DW_P),
		new CSInfo("DW_AvgWindSpeed",MiaoShuInfo.CS_DW_WindSpeed),
		new CSInfo("DW_DayFaDianLiang",MiaoShuInfo.CS_DW_FDLDay)
	};
	
	private final static CSInfo CZCSInfo[] = new CSInfo[]{
		new CSInfo("CZ_FJSecFaDianNum",MiaoShuInfo.CS_CZ_CLFJFaDianNum),
		new CSInfo("CZ_FJSecDaiFengNum",MiaoShuInfo.CS_CZ_CLFJDaiFengNum),
		new CSInfo("CZ_FJSecWeiHuNum",MiaoShuInfo.CS_CZ_CLFJTingJiNum),
		new CSInfo("CZ_FJSecShouLeiNum",MiaoShuInfo.CS_CZ_CLFJWeiHuNum),
		new CSInfo("CZ_FJSecGuZhuangNum",MiaoShuInfo.CS_CZ_CLFJShouLeiNum),
		new CSInfo("CZ_FJSecLiXianNum",MiaoShuInfo.CS_CZ_CLFJQiTaNum),
		new CSInfo("CZ_P",MiaoShuInfo.CS_CZ_P),
		new CSInfo("CZ_AvgWindSpeed",MiaoShuInfo.CS_CZ_WindSpeed),
		new CSInfo("CZ_DayFaDianLiang",MiaoShuInfo.CS_CZ_FDLDay)
	};
	
	private final static CSInfo FJCSInfo[] = new CSInfo[]{
		//new CSInfo("FJ_BianHao",MiaoShuInfo.CS_FJ_BianHao),
		new CSInfo("FJ_P",MiaoShuInfo.CS_FJ_P),
		new CSInfo("FJ_WindSpeed",MiaoShuInfo.CS_FJ_WindSpeed),
		new CSInfo("FJ_State",MiaoShuInfo.CS_FJ_State),
	};
	
	private RdbwebservicePortType rtdService = null;
	@RequestMapping("GetRealData")
	public void getRealData(HttpServletRequest request, HttpServletResponse response) throws JSONException, UnsupportedEncodingException{
		
		String strArrSDS = new String();
		for(CSInfo cs : DWCSInfo)
		{
			strArrSDS += MiaoShuInfo.LX_DW + "," +  cs.CS_ID + "," + 1 + ";";
		}
		
		JSONArray arrVal = getRealDataInternal(strArrSDS);
		
		// 设置返回数据格式
		JSONObject res = new com.alibaba.fastjson.JSONObject();
		for(int i=0; i < DWCSInfo.length; ++i)
		{
			res.put(DWCSInfo[i].CS_Name, arrVal.get(i));
		}
		
		// 获取描述库中的容量
		float DW_Capacity = BaseModelCache.getInstance().getDWCapacity();
		res.put("DW_Capacity", DW_Capacity);
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("GetRealData_CZ")
	public void getRealData_CZ(HttpServletRequest request, HttpServletResponse response) throws JSONException, UnsupportedEncodingException{
		String czid = request.getParameter("CZ_id");
		int nCZID = Integer.parseInt(czid);
		String strArrSDS = new String();
		// 获取每台风机的数据
		JSONArray arrVal = null;
		JSONArray arrFJObj = new JSONArray();
		List<JSONObject> fjList = BaseModelCache.getInstance().getFengjiList(nCZID);
		for(JSONObject obj : fjList)
		{// 遍历每个风机
			int nFJID = Integer.parseInt(obj.getString("id"));
			for(CSInfo cs : FJCSInfo)
			{
				strArrSDS += MiaoShuInfo.LX_FJ + "," +  cs.CS_ID + "," + nFJID + ";";
			}
			arrVal = getRealDataInternal(strArrSDS);
			// 设置返回数据格式
			JSONObject objFJ = new com.alibaba.fastjson.JSONObject();
			for(int i=0; i < FJCSInfo.length; ++i)
			{
				objFJ.put(FJCSInfo[i].CS_Name, arrVal.get(i));
			}
			objFJ.put("FJ_BianHao", obj.getString("bianhao"));
			arrFJObj.add(objFJ);
		}
		// 设置返回数据格式
		JSONObject res = new com.alibaba.fastjson.JSONObject();
		// 设置厂站名
		String CZ_Name = BaseModelCache.getInstance().getCZName(nCZID);
		res.put("CZName", CZ_Name);
		// 设置厂站容量
		float fCZCaps = BaseModelCache.getInstance().getCZCapacity(nCZID); 
		res.put("CZ_Capacity", fCZCaps);
		// 设置厂站内存数据
		strArrSDS = "";
		for(CSInfo cs : CZCSInfo)
		{
			strArrSDS += MiaoShuInfo.LX_CZ + "," +  cs.CS_ID + "," + nCZID + ";";
		}
		arrVal = getRealDataInternal(strArrSDS);
		
		for(int i=0; i < CZCSInfo.length; ++i)
		{
			res.put(CZCSInfo[i].CS_Name, arrVal.get(i));
		}
		// 设置厂站对应风机对象
		res.put("CZ_FJList", arrFJObj);
		
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}		

	public JSONArray getRealDataInternal(String strArrSDS)
	{
		JSONArray res = new JSONArray();
		if(strArrSDS == null || strArrSDS.length() == 0){
			return null;
		}
		boolean hasNotCached = false;
		String arrStrSDS[] = strArrSDS.split(";");
		for(String strSDS: arrStrSDS){
			ParamInfo info = ParamManager.getInstance().getParam(strSDS);
			if(info == null){
				hasNotCached = true;
				break;
			}
		}
		if(hasNotCached){
			//首先把要请求的内容给添加进入
			ParamManager.getInstance().addParams(strArrSDS);
			if(rtdService == null){
				rtdService = RdbWebServiceUtil.getRdbWebService();
			}
			try {
				String rtdData = rtdService.getRealData(strArrSDS);
				//把结果整理成json
				JSONArray arr = JSONArray.parseArray(rtdData);
				//遍历key，获取结果的value值并设置
				for(int i = 0; i < arr.size(); i++){
					JSONObject obj = (JSONObject) arr.get(i);
					String value = "0";
					ParamInfo info = ParamManager.getInstance().getParam(arrStrSDS[i]);
					try {
						if(info != null){
							info.setValue(obj.getString("val"));
						}
						value = new String(obj.getString("val").getBytes("ISO-8859-1"),"utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					res.add(value);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				rtdService = RdbWebServiceUtil.getRdbWebService();
			}
		}else{
			for(String strSDS: arrStrSDS){
				String value = "0";
				ParamInfo info = ParamManager.getInstance().getParam(strSDS);
				if(info != null){
					value = info.getValue();
					try {
						value = new String(value.getBytes("ISO-8859-1"),"utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				res.add(value);
			}
		}
		return res;
	}
}
