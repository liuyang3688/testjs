package com.ieslab.model;

import java.util.List;

public class Bjlx {
	private int id;
	private String name;
	private List<IdAndName> bujiancanshuList;
	
	
	public List<IdAndName> getBujiancanshuList() {
		return bujiancanshuList;
	}

	public void setBujiancanshuList(List<IdAndName> bujiancanshuList) {
		this.bujiancanshuList = bujiancanshuList;
	}

	public Bjlx(){
	}
	
	public Bjlx(int id,
			String name,
			List<IdAndName> bujiancanshuList){
		this.id = id;
		this.name = name;
		this.bujiancanshuList = bujiancanshuList;
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


}
