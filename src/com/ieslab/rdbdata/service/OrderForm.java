package com.ieslab.rdbdata.service;

import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * 实时数据订单
 * @author Li Hao
 * 
 */
public class OrderForm {

	private String code; //订单编号
	private JSONArray sdsArr; //订单三段式JSON格式：[{"cs":2,"id":3,"lx":1,"val":0.0},{"cs":6,"id":7,"lx":5,"val":0.0}]
	private String sdsStr; //三段式字符串数据
	private Date updateTime; //该订单的上次访问时间
	
	public String getSdsStr() {
		return sdsStr;
	}
	public void setSdsStr(String sdsStr) {
		this.sdsStr = sdsStr;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public JSONArray getSdsArr() {
		return sdsArr;
	}
	public void setSdsArr(JSONArray sdsArr) {
		this.sdsArr = sdsArr;
	}
	
	public JSONArray getValues(){
		JSONArray values = new JSONArray();
		if(sdsArr != null && sdsArr.size() > 0){
			for(int i = 0; i < sdsArr.size(); i++){
				JSONObject obj = (JSONObject) sdsArr.get(i);
				
				values.add(obj.getString("val"));
			}
		}
		return values;
	}
}
