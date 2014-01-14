package com.cheesemobile.domain;

public class Point {
	public float x;
	public float y;
	
	public Point(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "[" + x + "," + y + "]";
	}
	
	
	
}
