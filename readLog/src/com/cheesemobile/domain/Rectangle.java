package com.cheesemobile.domain;

import java.util.ArrayList;
import java.util.List;

import com.cheesemobile.util.ShotNewsUtil;
import com.cheesemobile.util.ShotNewsUtil.Direction;
import com.cheesemobile.util.ShotNewsUtil.ReleativePlace;
import com.cheesemobile.util.StringUtil;
import com.cheesemobile.util._Log;

public class Rectangle {
	private float x;
	private float y;
	private float width;
	private float height;

	// private float right;
	// private float buttom;

	public enum ReleativePosition {
		TOUCH, INSIDE, OUTSIDE;
		public boolean inside() {
			return !(this == ReleativePosition.OUTSIDE);
		}
	}

	public Rectangle() {
		super();
	}

	public Rectangle(float x, float y, float width, float height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		// boundToRectStart();
	}

	public Rectangle buildBound(float x, float y, float right, float buttom) {
		this.x = x;
		this.y = y;
		this.setRight(right);
		this.setButtom(buttom);
		// boundToRectEnd();
		return this;
	}

	public Point[] getPoints() {
		return getPoints(true);
	}

	public Rectangle buildBound(Point topLeft, Point buttomRight) {
		return buildBound(topLeft.x, topLeft.y, buttomRight.x, buttomRight.y);
	}

	public Point[] getPoints(boolean ASC) {
		Point p1 = new Point(x, y);
		Point p2 = new Point(x + width, y);
		Point p3 = new Point(x + width, y + height);
		Point p4 = new Point(x, y + height);
		Point[] ps = { p1, p2, p3, p4 };
		if (!ASC) {
			Point[] pst = { p1, p4, p3, p2 };
			ps = pst;
		}
		return ps;
	}

	public void setPoints(Point[] points) {

	}

	public Point getCenter() {
		Point p = new Point(this.getWidth() / 2 + this.getX(), this.getHeight()
				/ 2 + this.getY());
		return p;
	}

	public void scale(float len) {
		setX(x - len);
		setY(y - len);
		setWidth(width + len * 2);
		setHeight(height + len * 2);
	}

	public Direction orientation() {
		if (width > height) {
			return Direction.VERTICAL;
		} else {
			return Direction.HORTICAL;
		}
	}

	public List<Point[]> getLines() {
		Point[] thisPoints = this.getPoints();
		Point[] left = { thisPoints[0], thisPoints[3] };
		Point[] top = { thisPoints[0], thisPoints[1] };
		Point[] right = { thisPoints[1], thisPoints[2] };
		Point[] buttom = { thisPoints[2], thisPoints[3] };
		List<Point[]> lines = new ArrayList<>();
		lines.add(left);
		lines.add(top);
		lines.add(right);
		lines.add(buttom);
		return lines;
	}

	public void attachLine(Rectangle rect) {
		attachLine(rect.getLines());
		// attachLine(line, 50);
	}

	public void mergeRect(Rectangle rect) {
		if (this.rectInRect(rect) == ReleativePosition.OUTSIDE) {
			float sX = rect.getX() < this.getX() ? rect.getX() : this.getX();
			float sY = rect.getY() < this.getY() ? rect.getY() : this.getY();
			float bRight = rect.getRight() > this.getRight() ? rect.getRight()
					: this.getRight();
			float bButtom = rect.getButtom() > this.getButtom() ? rect
					.getButtom() : this.getButtom();
			rect = new Rectangle();
			this.buildBound(sX, sY, bRight, bButtom);
		} else {
			attachLine(rect);
		}
	}

	public Rectangle attachMove(Rectangle line) {
		List<Float> attachValues = attachValues(line.getLines());
		int rtype = 0;
		float minVal = Float.MAX_VALUE;
		float[] r = new float[4];
		r[0] = getX() - attachValues.get(0);
		r[1] = getY() - attachValues.get(1);
		r[0] = attachValues.get(2) - getRight();
		r[0] = attachValues.get(3) - getButtom();
		for (int i = 0; i < r.length; i++) {
			if (r[i] != 0 && r[i] < minVal) {
				minVal = r[i];
				rtype = i;
			}
		}
		Rectangle val = new Rectangle(getX(), getY(), getWidth(), getHeight());
		switch (rtype) {
		case 0:
			val.setX(attachValues.get(0));
			break;
		case 1:
			val.setY(attachValues.get(1));
			break;
		case 2:
			val.setRight(attachValues.get(2));
			break;
		case 3:
			val.setButtom(attachValues.get(3));
			break;
		}
		return val;
	}

	public void attachLine(List<Point[]> lines) {// not recommed
		attachLine(lines, 0);
	}

	public void attachLine(List<Point[]> lines, float scale) {// not recommed
		Float[] xtrb = attachValues(lines).toArray(new Float[4]);
		xtrb[0] += scale;
		xtrb[1] += scale;
		xtrb[2] -= scale;
		xtrb[3] -= scale;
		this.buildBound(xtrb[0], xtrb[1], xtrb[2], xtrb[3]);
	}

	private List<Float> attachValues(List<Point[]> lines) {
		List<Float> xtrb = new ArrayList<>();
		Point[] thisPoints = getPoints();
		Point[] left = { thisPoints[0], thisPoints[3] };
		Point[] leftAfter = ShotNewsUtil.lineToLines(left, lines,
				ReleativePlace.LEFT);
		xtrb.add(leftAfter[0].x);
		Point[] top = { thisPoints[0], thisPoints[1] };
		Point[] topAfter = ShotNewsUtil.lineToLines(top, lines,
				ReleativePlace.TOP);
		xtrb.add(topAfter[0].y);
		Point[] right = { thisPoints[1], thisPoints[2] };
		Point[] rightAfter = ShotNewsUtil.lineToLines(right, lines,
				ReleativePlace.RIGHT);
		xtrb.add(rightAfter[0].x);
		Point[] buttom = { thisPoints[2], thisPoints[3] };
		Point[] buttomAftert = ShotNewsUtil.lineToLines(buttom, lines,
				ReleativePlace.BUTTOM);
		xtrb.add(buttomAftert[0].y);
		return xtrb;
	}

	public void attachLine(PixelDataBean line, int distance) {
		// boundToRectStart();
		if (line.getWidth() == 1) {
			if (Math.abs(x - line.getMinx()) < distance) {
				x = (line.getMinx());
				// _Log.i("x:" + x);
			}
			if (Math.abs((x + width) - line.getMinx()) < distance) {
				setRight(line.getMinx());
				// _Log.i("right:" + right + " " + line);
			}

		} else {
			if (Math.abs(y - line.getMiny()) < distance) {
				y = (line.getMiny());
				// _Log.i("y:" + y);
			}
			if (Math.abs((y + height) - line.getMiny()) < distance) {
				setButtom(line.getMiny());
				// _Log.i("buttom:" + buttom);
			}
		}
		// boundToRectEnd();
	}

	public void attachLine(Rectangle line, int distance) {
		// boundToRectStart();
		if (line.getWidth() == 1) {
			if (Math.abs(x - line.x) < distance) {
				x = (line.x);
				// _Log.i("x:" + x);
			}
			if (Math.abs((x + width) - line.x) < distance) {
				setRight(line.x);
				// _Log.i("right:" + right + " " + line);
			}

		} else {
			if (Math.abs(y - line.y) < distance) {
				y = (line.y);
				// _Log.i("y:" + y);
			}
			if (Math.abs((y + height) - line.y) < distance) {
				setButtom(line.y);
				// _Log.i("buttom:" + buttom);
			}
		}
		// boundToRectEnd();
	}

	// private void boundToRectStart() {
	// buttom = y + height;
	// right = x + width;
	// }
	//
	// private void boundToRectEnd() {
	// width = right - x;
	// height = buttom - y;
	// _Log.i("rect:" + this.toString());
	// }

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		// boundToRectStart();
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		// boundToRectStart();
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
		// boundToRectStart();
	}

	public int getWidthInt() {
		return (int) Math.ceil(width);
	}

	public float getHeight() {
		return height;
	}

	public int getHeightInt() {
		return (int) Math.ceil(height);
	}

	public void setHeight(float height) {
		this.height = height;
		// boundToRectStart();
	}

	public float getRight() {
		return this.getX() + this.getWidth();
	}

	public void setRight(float right) {
		this.width = right - this.getX();
	}

	public void setButtom(float buttom) {
		this.height = buttom - this.getY();
	}

	public float getButtom() {
		return this.getY() + this.getHeight();
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

	public ReleativePosition rectInRect(Rectangle rect) {

		int edgeSum = 0;
		Point[] points = this.getPoints();
		Point[] pointsOut = rect.getPoints();
		for (Point p : points) {
			if (rect.pointInRect(p)) {
				edgeSum++;
			}
		}
		for (Point p : pointsOut) {
			if (this.pointInRect(p)) {
				edgeSum++;
			}
		}

		switch (edgeSum) {
		case 0:
			return ReleativePosition.OUTSIDE;
		case 1:
			return ReleativePosition.TOUCH;
		case 2:
			return ReleativePosition.TOUCH;
		case 4:
			return ReleativePosition.INSIDE;
		}
		return ReleativePosition.OUTSIDE;
	}

	public Rectangle rectFromJSX(List<String> asList) {
		this.x = StringUtil.floatInString(asList.get(0));
		this.y = StringUtil.floatInString(asList.get(1));
		this.setRight(StringUtil.floatInString(asList.get(2)));
		this.setButtom(StringUtil.floatInString(asList.get(3)));
		return this;
	}
}
