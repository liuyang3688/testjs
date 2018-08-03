package com.leotech.model;

public class Triple {
	public int bjlx;
	public int bjid;
	public int bjcs;
	public Triple(){
		this.bjlx = 0;
		this.bjid = 0;
		this.bjcs = 0;
	}
	public Triple(int bjlx, int bjid, int bjcs) {
		this.bjlx = bjlx;
		this.bjid = bjid;
		this.bjcs = bjcs;
	}

	@Override
	public boolean equals(Object obj) {
		Boolean bRet = false;
		if (obj instanceof Triple) {
			Triple tri = (Triple)obj;
			if (this.bjlx == tri.bjlx && this.bjid == tri.bjid && this.bjcs == this.bjcs) {
				bRet = true;
			}
		}
		return bRet;
	}

	@Override
	public int hashCode() {
		return this.bjlx*1000000 + this.bjid*1000 + this.bjcs;
	}
}
