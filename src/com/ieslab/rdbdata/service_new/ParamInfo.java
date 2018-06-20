package com.ieslab.rdbdata.service_new;

import java.util.Date;

/**
 * 部件参数类
 * @author Li Hao
 *
 */
public class ParamInfo {

	private String bjlxid, bjid, csid;
	private String value;
	
	private Date updateTime;//记录该三段式上次访问时间
	
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getBjlxid() {
		return bjlxid;
	}
	public void setBjlxid(String bjlxid) {
		this.bjlxid = bjlxid;
	}
	public String getBjid() {
		return bjid;
	}
	public void setBjid(String bjid) {
		this.bjid = bjid;
	}
	public String getCsid() {
		return csid;
	}
	public void setCsid(String csid) {
		this.csid = csid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
