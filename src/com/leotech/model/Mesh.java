package com.leotech.model;

public class Mesh {
	private int 		uuid;
	private String		name;
	private String		type;
	private int		show;
	private double		length;
	private double		width;
	private double		height;
	private	 double		pos_x;
	private	 double		pos_y;
	private double 	pos_z;
	private String		rot_direction;
	private double 	rot_degree;
	private Boolean 	transparent;
	private double		opacity;
	private int		material;
	private Boolean		isDirty;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getShow() {
		return show;
	}
	public void setShow(int show) {
		this.show = show;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getPos_x() {
		return pos_x;
	}
	public void setPos_x(double pos_x) {
		this.pos_x = pos_x;
	}
	public double getPos_y() {
		return pos_y;
	}
	public void setPos_y(double pos_y) {
		this.pos_y = pos_y;
	}
	public double getPos_z() {
		return pos_z;
	}
	public void setPos_z(double pos_z) {
		this.pos_z = pos_z;
	}
	public String getRot_direction() {
		return rot_direction;
	}
	public void setRot_direction(String rot_direction) {
		this.rot_direction = rot_direction;
	}
	public double getRot_degree() {
		return rot_degree;
	}
	public void setRot_degree(double rot_degree) {
		this.rot_degree = rot_degree;
	}
	public Boolean getTransparent() {
		return transparent;
	}
	public void setTransparent(Boolean transparent) {
		this.transparent = transparent;
	}
	public double getOpacity() {
		return opacity;
	}
	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}
	public int getMaterial() {
		return material;
	}
	public void setMaterial(int material) {
		this.material = material;
	}
	public Boolean getIsDirty() {
		return isDirty;
	}
	public void setIsDirty(Boolean isDirty) {
		this.isDirty = isDirty;
	}
}
//class Dual {
//	
//}
//class Tuple{
//	private double 	first;
//	private double		second;
//	private double		third;
//	public double getFirst() {
//		return first;
//	}
//	public void setFirst(double first) {
//		this.first = first;
//	}
//	public double getSecond() {
//		return second;
//	}
//	public void setSecond(double second) {
//		this.second = second;
//	}
//	public double getThird() {
//		return third;
//	}
//	public void setThird(double third) {
//		this.third = third;
//	}
//}
