package com.ieslab.baojingzhongxin.service;


public class ComTradeCfg {
	public ComTradeCfg()
	{
		this.StationName = "";
		this.RecDevName = "";
		this.RevYear = "";
		this.TotalChannelNum = 0;
		this.AChannelNum = 0;
		this.DChannelNum = 0;
		this.AChannelList = null;
		this.DChannelList = null;
		this.PL = 0.0f;
		this.NRATES = 0;
		this.SampDescList = null;
		this.StartTime = "";
		this.EndTime = "";
		this.FileType = "";
		this.Timemult = 0;
	}
	String				StationName;				//厂站名称
	String				RecDevName;					//录波设备
	String				RevYear;					//标准年份
	int					TotalChannelNum;			//总通道数			
	int					AChannelNum;				//模拟通道数
	int					DChannelNum;				//数字通道数
	AChannel[]			AChannelList;				//模拟通道列表
	DChannel[]			DChannelList;				//数字通道列表
	float				PL;							//线路频率
	int					NRATES;						//采样频率个数
	SampDesc[]			SampDescList;				//采样描述列表
	String				StartTime;					//采样开始时间
	String				EndTime;					//采样结束时间
	public String getStationName() {
		return StationName;
	}
	public void setStationName(String stationName) {
		StationName = stationName;
	}
	public String getRecDevName() {
		return RecDevName;
	}
	public void setRecDevName(String recDevName) {
		RecDevName = recDevName;
	}
	public String getRevYear() {
		return RevYear;
	}
	public void setRevYear(String revYear) {
		RevYear = revYear;
	}
	public int getTotalChannelNum() {
		return TotalChannelNum;
	}
	public void setTotalChannelNum(int totalChannelNum) {
		TotalChannelNum = totalChannelNum;
	}
	public int getAChannelNum() {
		return AChannelNum;
	}
	public void setAChannelNum(int aChannelNum) {
		AChannelNum = aChannelNum;
	}
	public int getDChannelNum() {
		return DChannelNum;
	}
	public void setDChannelNum(int dChannelNum) {
		DChannelNum = dChannelNum;
	}
	public AChannel[] getAChannelList() {
		return AChannelList;
	}
	public void setAChannelList(AChannel[] aChannelList) {
		AChannelList = aChannelList;
	}
	public DChannel[] getDChannelList() {
		return DChannelList;
	}
	public void setDChannelList(DChannel[] dChannelList) {
		DChannelList = dChannelList;
	}
	public float getPL() {
		return PL;
	}
	public void setPL(float pL) {
		PL = pL;
	}
	public int getNRATES() {
		return NRATES;
	}
	public void setNRATES(int nRATES) {
		NRATES = nRATES;
	}
	public SampDesc[] getSampDescList() {
		return SampDescList;
	}
	public void setSampDescList(SampDesc[] sampDescList) {
		SampDescList = sampDescList;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	public String getFileType() {
		return FileType;
	}
	public void setFileType(String fileType) {
		FileType = fileType;
	}
	public int getTimemult() {
		return Timemult;
	}
	public void setTimemult(int timemult) {
		Timemult = timemult;
	}
	String				FileType;					//文件类型
	int					Timemult;					//时间乘数
}
