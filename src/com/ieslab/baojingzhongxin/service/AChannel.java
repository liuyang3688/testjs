package com.ieslab.baojingzhongxin.service;

//采样描述列表	
public class AChannel {
	int 		id;						//通道号
	String		chname;					//通道名称
	String		ph;						//通道相
	String		ccbm;					//监视元件
	String		uu;						//通道单位
	float		a;						//通道乘数
	float		b;						//通道加数
	float		skew;					//通道时滞
	float		min;					//最小值
	float		max;					//最大值
	float		primary;				//变化比一次系数
	float		secondary;				//变化比二次系数
	String		ps;						//P/S
	public AChannel()
	{
		this.id = 1;
		this.chname = "";
		this.ph = "";
		this.ccbm = "";
		this.uu = "";
		this.a = 0.0f;
		this.b = 0.0f;
		this.skew = 0.0f;
		this.min = 0.0f;
		this.max = 0.0f;
		this.primary = 0.0f;
		this.secondary = 0.0f;
		this.ps = "";
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
	public String getUu() {
		return uu;
	}
	public void setUu(String uu) {
		this.uu = uu;
	}
	public float getA() {
		return a;
	}
	public void setA(float a) {
		this.a = a;
	}
	public float getB() {
		return b;
	}
	public void setB(float b) {
		this.b = b;
	}
	public float getSkew() {
		return skew;
	}
	public void setSkew(float skew) {
		this.skew = skew;
	}
	public float getMin() {
		return min;
	}
	public void setMin(float min) {
		this.min = min;
	}
	public float getMax() {
		return max;
	}
	public void setMax(float max) {
		this.max = max;
	}
	public float getPrimary() {
		return primary;
	}
	public void setPrimary(float primary) {
		this.primary = primary;
	}
	public float getSecondary() {
		return secondary;
	}
	public void setSecondary(float secondary) {
		this.secondary = secondary;
	}
	public String getPs() {
		return ps;
	}
	public void setPs(String ps) {
		this.ps = ps;
	}
}
