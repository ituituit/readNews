package com.cheesemobile.domain;


import java.util.List;

public class BaseNewsObject {
	private List<Point> points;
	private Rectangle bound;
	private List<Rectangle> subObjBounds;
	
	public List<Rectangle> getSubObjBounds() {
		return subObjBounds;
	}
	public void setSubObjBounds(List<Rectangle> subObjBounds) {
		this.subObjBounds = subObjBounds;
	}
	public List<Point> getPoints() {
		return points;
	}
	public void setPoints(List<Point> points) {
		this.points = points;
	}
	public Rectangle getBound() {
		return bound;
	}
	public void setBound(Rectangle bound) {
		this.bound = bound;
	}
}
