package com.ieslab.model;

public class BujianBase {
	int id;
	String bianhao;
	String mingzi;
	public BujianBase(){
		
	}
	
	public BujianBase(int id,String bianhao,String mingzi){
		this.id = id;
		this.bianhao =bianhao;
		this.mingzi = mingzi;
	}
	
	public String getBianhao() {
		return bianhao;
	}
	public String getMingzi() {
		return mingzi;
	}
	public int getID() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setMingzi(String mingzi) {
		this.mingzi = mingzi;
	}
	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
	}
	
}
