package com.cheesemobile.domain;

import com.cheesemobile.util.ShotNewsUtil.Direction;
import com.cheesemobile.util._Log;

public class Rectangle {
	private float x;
	private float y;
	private float width;
	private float height;
	private float right;
	private float buttom;

	public Rectangle() {
		super();
	}

	public Rectangle(float x, float y, float width, float height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Point[] getPoints() {
		return getPoints(true);
	}

	public Point[] getPoints(boolean ASC) {
		Point p1 = new Point(x, y);
		Point p2 = new Point(x + width, y);
		Point p3 = new Point(x + width, y + height);
		Point p4 = new Point(x, y + height);
		Point[] ps = { p1, p2, p3, p4 };
		if(!ASC){
			Point[]  pst = {p1,p4,p3,p2};
			ps = pst;
		}
		return ps;
	}

	public void scale(int len) {
		x -= len;
		y -= len;
		width += len * 2;
		height += len * 2;
	}

	public Direction orientation(){
		if(width > height){
			return Direction.VERTICAL;
		}else{
			return Direction.HORTICAL;
		}
	}
	
	public void attachLine(Rectangle line) {
		attachLine(line, 50);
	}

	public void attachLine(PixelDataBean line, int distance) {
		boundToRectStart();
		if (line.getWidth() == 1) {
			if (Math.abs(x - line.getMinx()) < distance) {
				x = (line.getMinx());
//				_Log.i("x:" + x);
			}
			if (Math.abs((x + width) - line.getMinx()) < distance) {
				right = (line.getMinx());
//				_Log.i("right:" + right + " " + line);
			}

		} else {
			if (Math.abs(y - line.getMiny()) < distance) {
				y = (line.getMiny());
//				_Log.i("y:" + y);
			}
			if (Math.abs((y + height) - line.getMiny()) < distance) {
				buttom = (line.getMiny());
//				_Log.i("buttom:" + buttom);
			}
		}
		boundToRectEnd();
	}

	public void attachLine(Rectangle line, int distance) {
		boundToRectStart();
		if (line.getWidth() == 1) {
			if (Math.abs(x - line.x) < distance) {
				x = (line.x);
//				_Log.i("x:" + x);
			}
			if (Math.abs((x + width) - line.x) < distance) {
				right = (line.x);
//				_Log.i("right:" + right + " " + line);
			}

		} else {
			if (Math.abs(y - line.y) < distance) {
				y = (line.y);
//				_Log.i("y:" + y);
			}
			if (Math.abs((y + height) - line.y) < distance) {
				buttom = (line.y);
//				_Log.i("buttom:" + buttom);
			}
		}
		boundToRectEnd();
	}

	private void boundToRectStart() {
		buttom = y + height;
		right = x + width;
	}

	private void boundToRectEnd() {
		width = right - x;
		height = buttom - y;
		_Log.i("rect:" + this.toString());
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "[" + x + "," + y + "," + width + "," + height + "]";
	}

	public boolean pointInRect(Point p) {
		float minX = x;
		float maxX = x + width;
		float minY = y;
		float maxY = y + height;
		if (p.x <= maxX && p.x > minX && p.y > minY && p.y <= maxY) {
			return true;
		}
		return false;
	}
}
