package com.ieslab.model;

public class FJBJCSInfo implements Comparable<FJBJCSInfo>{

	private int bjlxid;
	private int bjcsid;
	private String bjcsName;
	private String suoShuBj;
	private int index;
	public int getBjlxid() {
		return bjlxid;
	}
	public void setBjlxid(int bjlxid) {
		this.bjlxid = bjlxid;
	}
	public int getBjcsid() {
		return bjcsid;
	}
	public void setBjcsid(int bjcsid) {
		this.bjcsid = bjcsid;
	}
	public String getBjcsName() {
		return bjcsName;
	}
	public void setBjcsName(String bjcsName) {
		this.bjcsName = bjcsName;
	}
	public String getSuoShuBj() {
		return suoShuBj;
	}
	public void setSuoShuBj(String suoShuBj) {
		this.suoShuBj = suoShuBj;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	@Override
	public int compareTo(FJBJCSInfo o) {
		// TODO Auto-generated method stub
		if(this.getIndex() > o.getIndex()){
			return 1;
		}
		return -1;
	}
	
	
}
