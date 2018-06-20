package com.ieslab.baojingzhongxin.service;

import java.util.ArrayList;

public class ParseResult {
	ComTradeCfg 	cfg;
	ArrayList<BinaryBlock>	bllist;
	public ParseResult()
	{
		this.cfg = null;
		this.bllist = null;
	}
	public ComTradeCfg getCfg() {
		return cfg;
	}
	public void setCfg(ComTradeCfg cfg) {
		this.cfg = cfg;
	}
	public ArrayList<BinaryBlock> getBllist() {
		return bllist;
	}
	public void setBllist(ArrayList<BinaryBlock> bllist) {
		this.bllist = bllist;
	}
}