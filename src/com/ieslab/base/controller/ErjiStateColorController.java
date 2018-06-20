package com.ieslab.base.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.util.ErJiStateConfig;
/**
 * 风机二级状态颜色获取
 * @author Li Hao
 *
 */
@Controller
@RequestMapping("erjicolor")
public class ErjiStateColorController {
	@RequestMapping("getErjiColor")
	public void getFengchang(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		
		/*String fadian = ErJiStateConfig.getInstance().getFadian();
		String xiandian = ErJiStateConfig.getInstance().getXiandian();
		String guzhang = ErJiStateConfig.getInstance().getGuzhang();
		String weihu = ErJiStateConfig.getInstance().getWeihu();
		String shoulei = ErJiStateConfig.getInstance().getShoulei();
		String daiji = ErJiStateConfig.getInstance().getDaiji();
		String weizhi = ErJiStateConfig.getInstance().getWeizhi();
		String lixian = ErJiStateConfig.getInstance().getLixian();
		
		JSONObject obj = new JSONObject();
		
		obj.put("fadian", fadian);
		obj.put("xiandian", xiandian);
		obj.put("guzhang", guzhang);
		obj.put("weihu", weihu);
		obj.put("shoulei", shoulei);
		obj.put("daiji", daiji);
		obj.put("weizhi", weizhi);
		obj.put("lixian", lixian);*/
		
		String daifeng = ErJiStateConfig.getInstance().getDaifeng();
		String fadian = ErJiStateConfig.getInstance().getFadian();
		String tingji = ErJiStateConfig.getInstance().getTingji();
		String weihu = ErJiStateConfig.getInstance().getWeihu();
		String shoulei = ErJiStateConfig.getInstance().getShoulei();
		String qita = ErJiStateConfig.getInstance().getQita();
		
		JSONObject obj = new JSONObject();
		
		obj.put("daifeng", daifeng);
		obj.put("fadian", fadian);
		obj.put("tingji", tingji);
		obj.put("weihu", weihu);
		obj.put("shoulei", shoulei);
		obj.put("qita", qita);
		
		try {
			response.getWriter().print(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
