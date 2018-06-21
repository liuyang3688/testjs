package com.ieslab.model;

public class BuJianParam {
	String bjlx;
	String bjcs;
	String bjid;
	String czid;
	String sanduanshi;
	boolean rs; //用于转换ct递归调用
	String value;
	
	public BuJianParam() {
	}

	public BuJianParam(String bjlx, String bjcs, String bjid, String czid) {
		this.bjlx =bjlx;
		this.bjcs =bjcs;
		this.bjid =bjid;
		this.czid =czid;
		this.sanduanshi = bjlx + "_" + bjcs + "_" + bjid;
	}

	public String getBjlx() {
		return bjlx;
	}

	public void setBjlx(String bjlx) {
		this.bjlx = bjlx;
	}

	public String getBjcs() {
		return bjcs;
	}

	public void setBjcs(String bjcs) {
		this.bjcs = bjcs;
	}

	public String getBjid() {
		return bjid;
	}

	public void setBjid(String bjid) {
		this.bjid = bjid;
	}

	public String getCzid() {
		return czid;
	}

	public void setCzid(String czid) {
		this.czid = czid;
	}

	public String getSanduanshi() {
		return sanduanshi;
	}

	public void setSanduanshi(String sanduanshi) {
		this.sanduanshi = sanduanshi;
	}

	public boolean isRs() {
		return rs;
	}

	public void setRs(boolean rs) {
		this.rs = rs;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
