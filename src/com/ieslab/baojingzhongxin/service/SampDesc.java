package com.ieslab.baojingzhongxin.service;

public class SampDesc {
	int			samppl;					//采样频率
	int			endsamp;				//采样点数
	public SampDesc()
	{
		this.samppl = 0;
		this.endsamp = 0;
	}
	public int getSamppl() {
		return samppl;
	}
	public void setSamppl(int samppl) {
		this.samppl = samppl;
	}
	public int getEndsamp() {
		return endsamp;
	}
	public void setEndsamp(int endsamp) {
		this.endsamp = endsamp;
	}
}