package com.leotech.model;

public class Text {
	int 		uuid;
	String		name;
	String		font;
	String		text;
	int			labelId;
	double		offX;
	double		offY;
	public int getUuid() {
		return uuid;
	}
	public void setUuid(int uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFont() {
		return font;
	}
	public void setFont(String font) {
		this.font = font;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getLabelId() {
		return labelId;
	}
	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}
	public double getOffX() {
		return offX;
	}
	public void setOffX(double offX) {
		this.offX = offX;
	}
	public double getOffY() {
		return offY;
	}
	public void setOffY(double offY) {
		this.offY = offY;
	}
	
}
