package com.ieslab.baojingzhongxin.service;

public class BinaryBlock{
	int			no;
	int			ts;
	float[]		aValList;
	byte[]		dValList;
	public BinaryBlock()
	{
		this.no = 0;
		this.ts = 0;
		aValList = null;
		dValList = null;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getTs() {
		return ts;
	}
	public void setTs(int ts) {
		this.ts = ts;
	}
	public float[] getaValList() {
		return aValList;
	}
	public void setaValList(float[] aValList) {
		this.aValList = aValList;
	}
	public byte[] getdValList() {
		return dValList;
	}
	public void setdValList(byte[] dValList) {
		this.dValList = dValList;
	}
}