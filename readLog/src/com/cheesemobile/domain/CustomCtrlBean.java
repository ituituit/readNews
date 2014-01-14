package com.cheesemobile.domain;

public class CustomCtrlBean {
	private String name;
	private int x;
	private int y;
	private int width;
	private int height;
	private int actX;
	private int actY;
	private String extra;

	public CustomCtrlBean(String name) {
		super();
		this.name = name;
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
		this.actX = 0;
		this.actY = 0;
		this.extra = "0";
	}

	public boolean inCtrl(CustomCtrlBean c) {
		if (!(this.x < c.getX())) {
			return false;
		}
		if (!(this.y < c.getY())) {
			return false;
		}
		if (!(this.x + this.width > c.getX() + c.getWidth())) {
			return false;
		}
		if (!(this.y + this.height > c.getY() + c.getHeight())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name + "," + x + "," + y + "," + width + "," + height + ","
				+ actX + "," + actY + "," + extra;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getActX() {
		return actX;
	}

	public void setActX(int actX) {
		this.actX = actX;
	}

	public int getActY() {
		return actY;
	}

	public void setActY(int actY) {
		this.actY = actY;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		if (this.extra.length() != 0) {
			this.extra += "_" + extra;
		} else {
			this.extra = extra;
		}
	}
}
