package com.ieslab.model;

public class Changzhan {
	int id;
	String name;
	int netid;
	int type;
	String graName;
	String province;
	String subnetid;
	float longitude, latitude;
	public Changzhan(){
		
	}
	
	public Changzhan(int id,String name,int netid){
		this.id = id;
		this.name =name;
		this.netid = netid;
	}
	
	public Changzhan(int id,String name,int netid,int type){
		this.id = id;
		this.name =name;
		this.netid = netid;
		this.type = type;
	}
	
	public Changzhan(int id,String name,String graName){
		this.id = id;
		this.name =name;
		this.graName = graName;
	}
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getGraName() {
		return graName;
	}
	public void setGraName(String graName) {
		this.graName = graName;
	}
	public String getSubnetid() {
		return subnetid;
	}

	public void setSubnetid(String subnetid) {
		this.subnetid = subnetid;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNetid() {
		return netid;
	}
	public void setNetid(int netid) {
		this.netid = netid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
