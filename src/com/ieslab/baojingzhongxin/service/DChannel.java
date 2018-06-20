package com.ieslab.baojingzhongxin.service;

public class DChannel {
	int			id;						//通道号
	String		chname;					//通道名称
	String		ph;						//通道相
	String		ccbm;					//监视元件
	int			y;						//正常1或不正常0
	public DChannel()
	{
		this.id = 0;
		this.chname = "";
		this.ph = "";
		this.ccbm = "";
		this.y = 0;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getChname() {
		return chname;
	}
	public void setChname(String chname) {
		this.chname = chname;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getCcbm() {
		return ccbm;
	}
	public void setCcbm(String ccbm) {
		this.ccbm = ccbm;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}