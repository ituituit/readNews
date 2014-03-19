package com.cheesemobile.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sun.security.action.GetLongAction;

import com.cheesemobile.domain.PixelDataBean;
import com.cheesemobile.domain.Point;
import com.cheesemobile.domain.Rectangle;
import com.cheesemobile.service.CustomCtrl;

public class ShotNewsUtil {
	private static float[][] textBounds = { { 372f, 222.96f, 509.52f, 424.8f },
			{ 65.52f, 132f, 323.76f, 298.8f },
			{ 64.08f, 339.84f, 186.48f, 436.8f },
			{ 58.08f, 483.36f, 518.88f, 633.12f },
			{ 216.72f, 333.36f, 346.08f, 447.84f },
			{ 375.36f, 131.04f, 523.68f, 193.2f },
			{ 57.6f, 639.36f, 516f, 860.4f } };
	private static String[] textNames = { "text_1", "text_2", "text_3",
			"text_4", "text_5", "text_6", "i_speak" };

	private static String iSpeakname = "i_speak";
	private static float[] iSpeakBound = { 57.6f, 639.36f, 516f, 860.4f };

	public static float[][] bounds = { { 57.12f, 467.76f, 189.6f, 687.12f },
			{ 378.48f, 237.12f, 511.92f, 509.28f },
			{ 65.52f, 132f, 323.76f, 316.32f },
			{ 370.56f, 133.44f, 510.24f, 212.88f },
			{ 207.84f, 530.88f, 510f, 680.4f },
			{ 220.32f, 341.28f, 349.68f, 455.76f },
			{ 207.12f, 462f, 355.44f, 524.16f },
			{ 55.68f, 335.76f, 195.12f, 453.36f } };
	private boolean ALIGN_TO_HORTICAL = true;

	public enum Direction {
		VERTICAL, HORTICAL
	};

	private static List<Rectangle> ensBounds(float[][] bounds) {
		List<Rectangle> rects = new ArrayList<Rectangle>();
		for (int i = 0; i < bounds.length; i++) {
			float x = bounds[i][0];
			float y = bounds[i][1];
			float w = bounds[i][2] - bounds[i][0];
			float h = bounds[i][3] - bounds[i][1];
			rects.add(new Rectangle(x, y, w, h));
		}
		return rects;
	}

	private static List<Rectangle> rectFromPixelDataBean(
			List<PixelDataBean> beans) {
		List<Rectangle> rects = new ArrayList<Rectangle>();
		for (PixelDataBean bean : beans) {
			Rectangle rect = new Rectangle();
			rect.setHeight(bean.getHeight());
			rect.setWidth(bean.getWidth());
			rect.setX(bean.getMinx());
			rect.setY(bean.getMiny());
			rects.add(rect);
		}
		return rects;
	}

	private static List<Rectangle> rectsOfLinePoint(List<Point[]> linePoints) {
		List<Rectangle> rectsReturn = new ArrayList<Rectangle>();
		for (Point[] points : linePoints) {
			Rectangle r = new Rectangle();
			Point p0 = points[0];
			Point p1 = points[1];
			r.setX((int) (p1.x < p0.x ? p1.x : p0.x));
			r.setY((int) (p1.y < p0.y ? p1.y : p0.y));
			r.setWidth(Math.abs(p1.x - p0.x) + 1);
			r.setHeight(Math.abs(p1.y - p0.y) + 1);
			rectsReturn.add(r);
		}
		return rectsReturn;
	}

	public static List<Point[]> linepointFromRect(
			List<Rectangle> rects) {
		List<Point[]> pointsList = new ArrayList<Point[]>();
		for (Rectangle bean : rects) {
			Point p1 = new Point(0, 0);
			Point p2 = new Point(0, 0);
			if (bean.getWidth() > bean.getHeight()) {// horizontal
				p1 = new Point(bean.getX(), bean.getY()
						- bean.getHeight() / 2);
				p2 = new Point(bean.getX() + bean.getWidth(), bean.getY()
						- bean.getHeight() / 2);
			} else {
				p1 = new Point(bean.getX() - bean.getWidth() / 2,
						bean.getY());
				p2 = new Point(bean.getX() - bean.getWidth() / 2,
						bean.getY() + bean.getHeight());
			}
			Point[] ps = { p1, p2 };
			pointsList.add(ps);
		}
		return pointsList;
	}
	
	private static List<Point[]> linepointFromPixelDataBean(
			List<PixelDataBean> beans) {
		List<Point[]> pointsList = new ArrayList<Point[]>();
		for (PixelDataBean bean : beans) {
			Point p1 = new Point(0, 0);
			Point p2 = new Point(0, 0);
			if (bean.getWidth() > bean.getHeight()) {// horizontal
				p1 = new Point(bean.getMinx(), bean.getMiny()
						- bean.getHeight() / 2);
				p2 = new Point(bean.getMinx() + bean.getWidth(), bean.getMiny()
						- bean.getHeight() / 2);
			} else {
				p1 = new Point(bean.getMinx() - bean.getWidth() / 2,
						bean.getMiny());
				p2 = new Point(bean.getMinx() - bean.getWidth() / 2,
						bean.getMiny() + bean.getHeight());
			}
			Point[] ps = { p1, p2 };
			pointsList.add(ps);
		}
		return pointsList;
	}

	private static Rectangle getContentRect(List<Rectangle> rects) {
		float x = Float.MAX_VALUE;
		float y = Float.MAX_VALUE;
		float width = 0;
		float height = 0;
		for (Rectangle rect : rects) {
			if (rect.getX() < x) {
				x = rect.getX();
			}
			if (rect.getY() < y) {
				y = rect.getY();
			}
			if (rect.getWidth() + rect.getX() > width) {
				width = rect.getWidth() + rect.getX();
			}
			if (rect.getHeight() + rect.getY() > height) {
				height = rect.getHeight() + rect.getY();
			}
		}
		Rectangle contentRect = new Rectangle(x, y, width, height);
		return contentRect;
	}

	private static PixelDataBean linePic(int len, Direction type) {
		PixelDataBean sampleData;
		if (type == Direction.HORTICAL) {
			int[][] ints = new int[len][1];
			for (int i = 0; i < len; i++) {
				ints[i][0] = BitmapCompareUtil.convertPixToInt("255,255,255");
			}
			sampleData = new PixelDataBean(ints);
		} else {
			int[][] ints = new int[1][len];
			for (int i = 0; i < len; i++) {
				ints[0][i] = BitmapCompareUtil.convertPixToInt("255,255,255");
			}
			sampleData = new PixelDataBean(ints);
		}
		return sampleData;
	}

	public static int[][] drawRects(Rectangle contentRect, List<Rectangle> rects) {
		int x = (int) contentRect.getX();
		int y = (int) contentRect.getY();
		int width = contentRect.getWidthInt();
		int height = contentRect.getHeightInt();
		int[][] gridData = new int[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				String drawString = "255,255,255";
				for (Rectangle rect : rects) {
					if (rect.pointInRect(new Point(x + i, y + j))) {
						drawString = "0,0,0";
					}
				}
				gridData[i][j] = BitmapCompareUtil.convertPixToInt(drawString);
			}
		}
		return gridData;
	}

	private static float[][] pathBounds = {
			{ 64.08f, 438.72f, 504.96f, 605.76f },
			{ 374.16f, 439.44f, 509.76f, 606.72f },
			{ 61.68f, 508.08f, 240.48f, 607.44f }, };

	// private static float[][] pathBounds =
	// {{65.76f,439.2f,360.72f,588.72f},{392.4f,439.92f,510.24f,585.12f},{66f,530.64f,210.72f,608.64f}};
	public static void pathObj() {
		List<Rectangle> pathRects = ensBounds(pathBounds);
		Rectangle textRect = pathRects.remove(0);
		List<Point> pPaths = new ArrayList<Point>();
		Point[] textPoints = textRect.getPoints();
		pPaths.add(textPoints[0]);
		pPaths.add(textPoints[1]);
		for (Rectangle pathRect : pathRects) {
			// pathRect.scale(Constants.NEWS_RULER_1);
			// pathRect.attachLine(textRect);
			Point[] pathRectPoints = pathRect.getPoints(false);
			pPaths.add(new Point(textPoints[1].x, pathRectPoints[0].y));
			pPaths.addAll(Arrays.asList(pathRectPoints));
			pPaths.add(new Point(textPoints[1].x, pathRectPoints[0].y));
		}
		pPaths.add(textPoints[2]);
		pPaths.add(textPoints[3]);
		_Log.i(pPaths + "");
	}

	public static List<Rectangle> genLines(List<Rectangle> rects, Rectangle contentRect) {
		PixelDataBean srcData = new PixelDataBean(drawRects(contentRect, rects));
		List<List<PixelDataBean>> groups = new ArrayList<>();
		for (int i = 0; i < srcData.getWidth(); i++) {
			groups.add(pixelDataLengthOfLineVertical(srcData,
					i, Direction.VERTICAL));
		}
		List<PixelDataBean> lineResult = delShorterCloseLine(groups);
		lineResult.get(0).setMinx(0);
		PixelDataBean last = lineResult.get(lineResult.size() - 1);
		last.setMinx((int)(srcData.getWidth()));

		List<List<PixelDataBean>> groupsH = new ArrayList<>();
		for (int i = 0; i < srcData.getHeight(); i+= 2) {
			List<PixelDataBean> pixelDataLengthOfLineVertical = pixelDataLengthOfLineVertical(srcData,
					i, Direction.HORTICAL);
			groupsH.add(pixelDataLengthOfLineVertical);
		}
		
		List<PixelDataBean> lineResultH = delShorterCloseLine(groupsH);
		lineResultH.get(0).setMiny(0);
		lineResultH.get(lineResultH.size() - 1).setMiny((int)(srcData.getHeight()));
		lineResult.addAll(lineResultH);
		
		List<Point[]> pointFromPixelDataBean = ensLines(lineResult);
		for(Point[] r : pointFromPixelDataBean){
			r[0].x += contentRect.getX() - 1;
			r[0].y += contentRect.getY() - 1;
			r[1].x += contentRect.getX() - 1;
			r[1].y += contentRect.getY() - 1;
		}
		
		List<Rectangle> lineRects = rectsOfLinePoint(pointFromPixelDataBean);
//		print(lineRects, contentRect);
		return lineRects;
	}
	
	public static void print(List<Rectangle> placesRects,
			Rectangle containerRect) {
		PixelDataBean srcData = new PixelDataBean(ShotNewsUtil.drawRects(
				containerRect, placesRects));
		BitmapCompareUtil.outputPx(srcData.getSourceData());
	}

	private static void genNewsRects() {
		List<Rectangle> rects = ensBounds(textBounds);
		Rectangle contentRect = getContentRect(rects);
		PixelDataBean srcData = new PixelDataBean(drawRects(contentRect, rects));
		PixelDataBean lineHorizontal = linePic(rects, Direction.VERTICAL);
		List<java.awt.Point> points = CustomCtrl.linearInData(lineHorizontal,
				srcData, true);
		List<List<PixelDataBean> groups = new ArrayList<PixelDataBean>();
		for (java.awt.Point point : points) {
			list.add(pixelDataLengthOfLineVertical(lineHorizontal, srcData,
					point.x, Direction.VERTICAL));
		}
		list = delShorterCloseLine(list);
		PixelDataBean lineVertical = linePic(rects, Direction.HORTICAL);
		List<java.awt.Point> pointsH = CustomCtrl.linearInData(lineVertical,
				srcData, false);
		List<PixelDataBean> listH = new ArrayList<PixelDataBean>();
		for (java.awt.Point point : pointsH) {
			listH.add(pixelDataLengthOfLineVertical(lineVertical, srcData,
					point.y, Direction.HORTICAL));
		}
		List<PixelDataBean> delShorterCloseLine = delShorterCloseLine(listH);
		delShorterCloseLine.addAll(list);
		// List<Rectangle> rectFromPixelDataBean =
		// rectFromPixelDataBean(delShorterCloseLine);
		// Rectangle contentRect2 = getContentRect(rectFromPixelDataBean);
		// PixelDataBean srcData2 = new PixelDataBean(drawRects(contentRect2,
		// rectFromPixelDataBean));
		// BitmapCompareUtil.outputPx(srcData2.getSourceData());

		for (Rectangle rect : rects) {
			for (PixelDataBean line : delShorterCloseLine) {
				rect.attachLine(new Rectangle(line.getMinx()
						+ contentRect.getX(), line.getMiny()
						+ contentRect.getY(), line.getWidth(), line.getHeight()));
			}
			rect.scale(-Constants.NEWS_RULER_1);
		}

		System.out.print("[");
		for (int i = 0; i < rects.size(); i++) {
			Rectangle rect = rects.get(i);
			System.out.print(rect + ",");
		}
		System.out.print("]\n");
		System.out.print("[");
		for (int i = 0; i < rects.size(); i++) {
			System.out.print("\"" + textNames[i] + "\",");
		}
		System.out.print("]");
		System.out.print("\n");
		// BitmapCompareUtil.outputPx(drawRects(getContentRect(rects), rects));

		ensLines(delShorterCloseLine);
	}

	public enum ReleativePlace{
		LEFT,TOP,RIGHT,BUTTOM;
		public boolean fit(Point from, Point to) {
			if(from == to){
				return false;
			}
			switch (this) {
			case LEFT:
				return to.x < from.x && to.y == from.y;
			case TOP:
				return to.y < from.y && to.x == from.x;
			case RIGHT:
				return to.x > from.x && to.y == from.y;
			case BUTTOM:
				return to.y > from.y && to.x == from.x;
			default:
				break;
			}
			return false;
		}
	};
	public static Point[] lineToLines(Point[] linePoint,List<Point[]> containers,ReleativePlace releativePlace){
			float[] dxy1 = { Integer.MAX_VALUE, linePoint[0].x, linePoint[0].y };
			float[] dxy2 = { Integer.MAX_VALUE, linePoint[1].x, linePoint[1].y };
			for (Point[] linePoint2 : containers) {
				float[] dxy1t = Vector3f.calculateDistance(linePoint[0],
						linePoint2[0], linePoint2[1]);
				float[] dxy2t = Vector3f.calculateDistance(linePoint[1],
						linePoint2[0], linePoint2[1]);
				Point cross1t = new Point(dxy1t[1], dxy1t[2]);
				Point cross2t = new Point(dxy2t[1], dxy2t[2]);
				if(releativePlace.fit(linePoint[0],cross1t) && dxy1[0] > dxy1t[0]){
					dxy1 = dxy1t;
				}

				if (releativePlace.fit(linePoint[1],cross2t) && dxy2[0] > dxy2t[0]) {
					dxy2 = dxy2t;
				}
			}
			// _Log.i("min:" + dxy1[0] + " " + dxy2[0]);
			if (dxy1[0] != 0) {
				Point[] linePointNew = { new Point(dxy1[1], dxy1[2]),
						linePoint[1] };
				linePoint = linePointNew;
			}
			if (dxy2[0] != 0) {
				Point[] linePointNew = { linePoint[0],
						new Point(dxy2[1], dxy2[2]) };
				linePoint = linePointNew;
			}
			return linePoint;
	}
	private static List<Point[]> ensLines(List<PixelDataBean> delShorterCloseLine) {
		List<Point[]> pointFromPixelDataBean = linepointFromPixelDataBean(delShorterCloseLine);
		for (int i = 0; i < pointFromPixelDataBean.size(); i++) {
			Point[] linePoint = pointFromPixelDataBean.get(i);
			float[] dxy1 = { Integer.MAX_VALUE, 0, 0 };
			float[] dxy2 = { Integer.MAX_VALUE, 0, 0 };
			for (Point[] linePoint2 : pointFromPixelDataBean) {
				if (linePoint2 ==linePoint) {
					continue;
				}
				float[] dxy1t = Vector3f.calculateDistance(linePoint[0],
						linePoint2[0], linePoint2[1]);
				float[] dxy2t = Vector3f.calculateDistance(linePoint[1],
						linePoint2[0], linePoint2[1]);
				if (dxy1[0] > dxy1t[0]) {
					dxy1 = dxy1t;
				}
				if (dxy2[0] > dxy2t[0]) {
					dxy2 = dxy2t;
				}
			}
			// _Log.i("min:" + dxy1[0] + " " + dxy2[0]);
			if (dxy1[0] != 0) {
				Point[] linePointNew = { new Point(dxy1[1], dxy1[2]),
						pointFromPixelDataBean.get(i)[1] };
				pointFromPixelDataBean.set(i, linePointNew);
			}
			if (dxy2[0] != 0) {
				Point[] linePointNew = { pointFromPixelDataBean.get(i)[0],
						new Point(dxy2[1], dxy2[2]) };
				pointFromPixelDataBean.set(i, linePointNew);
			}
		}
		return pointFromPixelDataBean;
		
//		BitmapCompareUtil.outputPx(drawRects(getContentRect(rectsOfLinePoint),
//				rectsOfLinePoint));
//
//		System.out.print("[");
//		for (Rectangle r : rectsOfLinePoint) {
//			if (r.orientation() == Direction.HORTICAL) {
//				System.out.print("\"\"\"cow_sample\"\"\",");
//			} else {
//				System.out.print("\"\"\"row_sample\"\"\",");
//			}
//		}
//		System.out.print("]\n");
//		_Log.i("" + rectsOfLinePoint);

	}

	public static void shot2() {
		genNewsRects();
		// List<Rectangle> rects = ensBounds(bounds);
		// Rectangle contentRect = getContentRect(rects);
		// int[][] gridData = drawRects(contentRect, rects);
		// PixelDataBean srcData = new PixelDataBean(gridData);
		// PixelDataBean lineHorizontal = linePic(rects, Direction.VERTICAL);
		// List<java.awt.Point> points = CustomCtrl.linearInData(lineHorizontal,
		// srcData, true);
		// List<PixelDataBean> list = new ArrayList<PixelDataBean>();
		// for (java.awt.Point point : points) {
		// list.add(pixelDataLengthOfLineVertical(lineHorizontal, srcData,
		// point.x, Direction.VERTICAL));
		// }
		// list = delShorterCloseLine(list);
		// PixelDataBean lineVertical = linePic(rects, Direction.HORTICAL);
		// List<java.awt.Point> pointsH = CustomCtrl.linearInData(lineVertical,
		// srcData, false);
		// List<PixelDataBean> listH = new ArrayList<PixelDataBean>();
		// for (java.awt.Point point : pointsH) {
		// listH.add(pixelDataLengthOfLineVertical(lineVertical, srcData,
		// point.y, Direction.HORTICAL));
		// }
		// List<PixelDataBean> delShorterCloseLine = delShorterCloseLine(listH);
		// delShorterCloseLine.addAll(list);
		// List<Rectangle> rectFromPixelDataBean =
		// rectFromPixelDataBean(delShorterCloseLine);
		// List<Point[]> pointFromPixelDataBean =
		// linepointFromPixelDataBean(delShorterCloseLine);
		// Rectangle contentRect2 = getContentRect(rectFromPixelDataBean);
		// PixelDataBean srcData2 = new PixelDataBean(drawRects(contentRect2,
		// rectFromPixelDataBean));
		// BitmapCompareUtil.outputPx(srcData2.getSourceData());
		// for (int i = 0; i<pointFromPixelDataBean.size(); i++) {
		// Point[] linePoint = pointFromPixelDataBean.get(i);
		// int[] dxy1 = {Integer.MAX_VALUE,0,0};
		// int[] dxy2 = {Integer.MAX_VALUE,0,0};
		// for (Point[] linePoint2 : pointFromPixelDataBean) {
		// if(linePoint2[0].x == linePoint[0].x && linePoint2[1].x ==
		// linePoint[1].x && linePoint2[0].y == linePoint[0].y &&
		// linePoint2[1].y == linePoint[1].y){
		// continue;
		// }
		// int[] dxy1t = Vector3f.calculateDistance(linePoint[0], linePoint2[0],
		// linePoint2[1]);
		// int[] dxy2t = Vector3f.calculateDistance(linePoint[1], linePoint2[0],
		// linePoint2[1]);
		// if(dxy1[0] > dxy1t[0]){
		// dxy1 = dxy1t;
		// }
		// if(dxy2[0] > dxy2t[0]){
		// dxy2 = dxy2t;
		// }
		// }
		// _Log.i("min:" + dxy1[0] + " " + dxy2[0]);
		// if(dxy1[0] != 0){
		// Point[] linePointNew = {new
		// Point(dxy1[1],dxy1[2]),pointFromPixelDataBean.get(i)[1]};
		// pointFromPixelDataBean.set(i, linePointNew);
		// }
		// if(dxy2[0] != 0){
		// Point[] linePointNew = {pointFromPixelDataBean.get(i)[0],new
		// Point(dxy2[1],dxy2[2])};
		// pointFromPixelDataBean.set(i, linePointNew);
		// }
		// }
		// List<Rectangle> rectsOfLinePoint =
		// rectsOfLinePoint(pointFromPixelDataBean);
		// for(int i =0; i < rectsOfLinePoint.size(); i++){
		// Rectangle r = rectsOfLinePoint.get(i);
		// r.scale(3);
		// }
		// BitmapCompareUtil.outputPx(drawRects(getContentRect(rectsOfLinePoint),
		// rectsOfLinePoint));
		//
		// System.out.print("[");
		// for(Rectangle r: rectsOfLinePoint){
		// if (r.orientation() == Direction.HORTICAL) {
		// System.out.print("\"\"\"cow_sample\"\"\",");
		// }else{
		// System.out.print("\"\"\"row_sample\"\"\",");
		// }
		// }
		// System.out.print("]\n");
		// _Log.i("" + rectsOfLinePoint);
		// if(0 == 0) return ;
		// for (Rectangle rect : rects) {
		// for (PixelDataBean line : delShorterCloseLine) {
		// rect.attachLine(new Rectangle(line.getMinx()
		// + contentRect.getX(), line.getMiny()
		// + contentRect.getY(), line.getWidth(), line.getHeight()));
		// }
		// rect.scale(-Constants.NEWS_RULER_1);
		// }
		//
		// contentRect = getContentRect(rects);
		// System.out.print("[");
		// gridData = drawRects(contentRect, rects);
		// for (int i = 0; i < rects.size(); i++) {
		// Rectangle rect = rects.get(i);
		// System.out.print(rect + ",");
		// }
		// System.out.print("]");
		// BitmapCompareUtil.outputPx(gridData);
	}

	private static PixelDataBean linePic(List<Rectangle> rects,
			Direction direction) {
		// int width = (int) (Constants.CONTENT_WIDTH +
		// Constants.CONTENT_WIDTH_FIX_VALUE);
		// int heigth = (int) (Constants.CONTENT_HEIGHT +
		// Constants.CONTENT_HEIGHT_FIX_VALUE);

		int len = Integer.MAX_VALUE;
		for (Rectangle rect : rects) {
			if (direction == Direction.HORTICAL) {
				if (rect.getWidth() < len) {
					len = (int) rect.getWidth();
				}
			} else {
				if (rect.getHeight() < len) {
					len = (int) rect.getHeight();
				}
			}
		}
		return linePic(len, direction);
	}

//	private static List<PixelDataBean> delShorterCloseLine(
//			List<PixelDataBean> list) {
//		List<List<PixelDataBean>> closeLines = CustomCtrl.closeLines(list);
//		closeLines = CustomCtrl.splitByShorter(closeLines);
//		List<PixelDataBean> delCloseLine = new ArrayList<PixelDataBean>();
//		for (List<PixelDataBean> closeLine : closeLines) {
//			PixelDataBean maxLenLine = closeLine.get(0);
//			for (PixelDataBean line : closeLine) {
//				if (line.getWidth() == maxLenLine.getWidth()
//						&& line.getHeight() > maxLenLine.getHeight()) {
//					maxLenLine = line;
//				}
//				if (line.getHeight() == maxLenLine.getHeight()
//						&& line.getWidth() > maxLenLine.getWidth()) {
//					maxLenLine = line;
//				}
//			}
//			List<PixelDataBean> maxLenLines = new ArrayList<>();
//			for (int i = 0; i < closeLine.size(); i++) {
//				if (closeLine.get(i).getWidth() == maxLenLine.getWidth()
//						&& closeLine.get(i).getHeight() == maxLenLine
//								.getHeight()) {
//					maxLenLines.add(closeLine.get(i));
//				}
//			}
//			delCloseLine.add(maxLenLines.get(maxLenLines.size() / 2));
//		}
//		return delCloseLine;
//	}

	private static List<PixelDataBean> delShorterCloseLine(
			List<List<PixelDataBean>> groups) {
		List<List<PixelDataBean>> closeLines  = CustomCtrl.splitByShorter(groups);
		List<PixelDataBean> delCloseLine = new ArrayList<PixelDataBean>();
		for (List<PixelDataBean> closeLine : closeLines) {
			PixelDataBean maxLenLine = closeLine.get(0);
			for (PixelDataBean line : closeLine) {
				if (line.getWidth() == maxLenLine.getWidth()
						&& line.getHeight() > maxLenLine.getHeight()) {
					maxLenLine = line;
				}
				if (line.getHeight() == maxLenLine.getHeight()
						&& line.getWidth() > maxLenLine.getWidth()) {
					maxLenLine = line;
				}
			}
			List<PixelDataBean> maxLenLines = new ArrayList<>();
			for (int i = 0; i < closeLine.size(); i++) {
				if (closeLine.get(i).getWidth() == maxLenLine.getWidth()
						&& closeLine.get(i).getHeight() == maxLenLine
								.getHeight()) {
					maxLenLines.add(closeLine.get(i));
				}
			}
			delCloseLine.add(maxLenLines.get(maxLenLines.size() / 2));
		}
		return delCloseLine;
	}
	
	private static PixelDataBean pixelDataLengthOfLineVertical(
			PixelDataBean line, PixelDataBean srcData, int pos,
			Direction direction) {
		int fixValue = 1;

		int x = pos;
		int y = 0;
		int w = 1;
		int h = srcData.getHeight();
		if (direction == Direction.HORTICAL) {
			x = 0;
			y = pos;
			w = srcData.getWidth();
			h = 1;
		}

		PixelDataBean longLine = BitmapCompareUtil
				.getSubPX(srcData, x, y, w, h);
		int[] point = BitmapCompareUtil.BigPicInSmallPic(longLine, line);
		if (point == null) {
			return null;
		}
		int previewPointX = point[0];
		int previewPointY = point[1];
		_Log.i(previewPointX + "  " + pos);

		int moveLen = 0;
		PixelDataBean rect;
		if (direction == Direction.VERTICAL) {
			while (point != null) {
				moveLen += fixValue;
				int cHeight = longLine.getHeight() - moveLen;
				point = BitmapCompareUtil.BigPicInSmallPic(BitmapCompareUtil
						.getSubPX(longLine, 0, moveLen, 1, cHeight), line);
			}

			rect = linePic(moveLen + 2 * line.getHeight() - previewPointY,
					direction);
			rect.setMinx(pos);
			rect.setMiny(previewPointY - line.getHeight());
		} else {
			while (point != null) {
				moveLen += fixValue;
				int cWidth = longLine.getWidth() - moveLen;
				point = BitmapCompareUtil.BigPicInSmallPic(BitmapCompareUtil
						.getSubPX(longLine, moveLen, 0, cWidth, 1), line);
			}
			rect = linePic(moveLen + 2 * line.getWidth() - previewPointX,
					direction);
			rect.setMinx(previewPointX - line.getWidth());
			rect.setMiny(pos);
		}

		return rect;
	}

	private static List<PixelDataBean> pixelDataLengthOfLineVertical(PixelDataBean srcData, int pos,
			Direction direction) {
//		_Log.i("start pixelDataLengthOfLineVertical");
		PixelDataBean line = linePic(1, direction);
		int x = pos;
		int y = 0;
		int w = 1;
		int h = srcData.getHeight();
		if (direction == Direction.HORTICAL) {
			x = 0;
			y = pos;
			w = srcData.getWidth();
			h = 1;
		}

		PixelDataBean longLine = BitmapCompareUtil
				.getSubPX(srcData, x, y, w, h);
		int[] points = BitmapCompareUtil.BigPicInSmallPic(longLine, line,true);
		if (points == null) {
			return null;
		}
		List<PixelDataBean> pointsBean = new ArrayList<PixelDataBean>();
		for(int i = 0; i < points.length; i+=2){
			int px = points[i];
			int py = points[i+1];
			if(direction == Direction.HORTICAL){
				line.setMiny(pos);
				line.setMinx(px);
			}else{
				line.setMinx(pos);
				line.setMiny(py);
			}
			pointsBean.add(new PixelDataBean(line.getSourceData(),line.getMinx(),line.getMiny(),line.getWidth(),line.getHeight()));
		}

//		_Log.i("end pixelDataLengthOfLineVertical");
		return CustomCtrl.mergeCloseLines(pointsBean);
	}
	
	/****
	 * 点到直线的距离***
	 * 
	 * 过点（x1,y1）和点（x2,y2）的直线方程为：KX -Y + (x2y1 - x1y2)/(x2-x1) = 0 设直线斜率为K =
	 * (y2-y1)/(x2-x1),C=(x2y1 - x1y2)/(x2-x1) 点P(x0,y0)到直线AX + BY +C =0DE
	 * 距离为：d=|Ax0 + By0 + C|/sqrt(A*A + B*B)
	 * 点（x3,y3）到经过点（x1,y1）和点（x2,y2）的直线的最短距离为： distance = |K*x3 - y3 +
	 * C|/sqrt(K*K + 1)
	 */

	// public static double GetMinDistance(double x, double y, double x1,
	// double y1, double x2, double y2) {
	// double dis = 0;
	// if (Math.abs(x1 - x2) < 0.00001) {
	// dis = Math.abs(x - x1);
	// return dis;
	// }
	// double lineK = (y2 - y1) / (x2 - x1);// 斜率
	// double lineC = (x2 * y1 - x1 * y2) / (x2 - x1);
	// dis = Math.abs(lineK * x - y + lineC) / (Math.sqrt(lineK * lineK + 1));
	// return dis;
	//
	// }

	private static double GetMinDistance(float x0, float y0, float x1,
			float y1, float x2, float y2) {
		double space = 0;
		double a, b, c;
		a = lineSpace(x1, y1, x2, y2);// 线段的长度
		b = lineSpace(x1, y1, x0, y0);// (x1,y1)到点的距离
		c = lineSpace(x2, y2, x0, y0);// (x2,y2)到点的距离
		if (c <= 0.000001 || b <= 0.000001) {
			space = 0;
			return space;
		}
		if (a <= 0.000001) {
			space = b;
			return space;
		}
		if (c * c >= a * a + b * b) {
			space = b;
			return space;
		}
		if (b * b >= a * a + c * c) {
			space = c;
			return space;
		}
		double p = (a + b + c) / 2;// 半周长
		double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积
		space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）
		return space;
	}

	// 计算两点之间的距离
	private static double lineSpace(float x1, float y1, float x2, float y2) {
		double lineLength = 0;
		lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return lineLength;
	}
	// public void ctrlsOnGrid2(String... strings) {
	// PixelDataBean srcData = BitmapCompareUtil.getPixelDataBean(strings[0]);
	// PixelDataBean lineHorizontal = BitmapCompareUtil
	// .getPixelDataBean(strings[1]);
	// PixelDataBean lineVertical = BitmapCompareUtil
	// .getPixelDataBean(strings[2]);
	// List<Integer> horIndexs = CustomCtrl.getPoints(
	// CustomCtrl.linearInData(lineHorizontal, srcData, false), false);
	//
	// List<Point> horLines = delCloseLines(horIndexs,
	// lineHorizontal.getHeight());
	// List<List<Point>> arrVertLines = new ArrayList<List<Point>>();
	// for (Point p : horLines) {
	// PixelDataBean horData = BitmapCompareUtil.getSubPX(srcData, 0, p.x,
	// srcData.getWidth(), p.y - p.x);
	// List<Integer> vertIndexs = CustomCtrl.getPoints(
	// CustomCtrl.linearInData(lineVertical, horData, true), true);
	// List<Point> vertLines = delCloseLines(vertIndexs,
	// lineVertical.getWidth());
	// arrVertLines.add(vertLines);
	// }
	//
	// encapsulateRectToControlList(gridBoundsToRect(horLines, arrVertLines),
	// "ctrlsOnGrid");
	// compareExtraSamplesToControlist(3, srcData, strings);
	// _Log.i(controlList.toString());
	// _Log.i("ctrlsOnGrid2");
	// }

	// public static void shot() {
	// List<Rectangle> rects = ensBounds(bounds);
	// Rectangle contentRect = getContentRect(rects);
	// // int width = (int) (Constants.CONTENT_WIDTH +
	// // Constants.CONTENT_WIDTH_FIX_VALUE);
	// // int heigth = (int) (Constants.CONTENT_HEIGHT +
	// // Constants.CONTENT_HEIGHT_FIX_VALUE);
	// int x = (int) contentRect.getX();
	// int y = (int) contentRect.getY();
	// int width = (int) contentRect.getWidth() - x + 2;
	// int height = (int) contentRect.getHeight() - y + 2;
	// int[][] gridData = new int[width][height];
	// for (int i = 0; i < width; i++) {
	// for (int j = 0; j < height; j++) {
	// String drawString = "255,255,255";
	// for (Rectangle rect : rects) {
	// if (rect.pointInRect(new Point(x + i, y + j))) {
	// drawString = "0,0,0";
	// }
	// }
	// gridData[i][j] = BitmapCompareUtil.convertPixToInt(drawString);
	// }
	// }
	// BitmapCompareUtil.outputPx(gridData);
	// int len = Integer.MAX_VALUE;
	// Direction direction = Direction.VERTICAL;
	// for (Rectangle rect : rects) {
	// if (direction == Direction.HORTICAL) {
	// if (rect.getWidth() < len) {
	// len = (int) rect.getWidth();
	// }
	// } else {
	// if (rect.getHeight() < height) {
	// len = (int) rect.getHeight();
	// }
	// }
	// }
	// pixelsPathFind(linePic(len, direction), new PixelDataBean(gridData));
	// }
	//
	// private static void pixelsPathFind(PixelDataBean line, PixelDataBean
	// grid) {
	// if (line.getHeight() == 1) {
	// } else {
	// resizeCX(0, grid);
	// int[] resultPoint = indexStretchLines(line, grid);
	// while (resultPoint != null) {
	// _Log.i(resultPoint[0] + "");
	// resizeCX(resultPoint[0] - 1, grid);
	// resultPoint = indexStretchLines(line, grid);
	// // BitmapCompareUtil.outputPx(subData);
	// }
	// }
	// }

	// public static int cX = 0;
	// public static int cY = 0;
	// public static int cWidth = 0;
	// public static int cHeight = 0;
	//
	// private static void resizeCX(int cx, PixelDataBean grid) {
	// cX += cx;
	// cWidth = grid.getWidth() - cX;
	// cHeight = grid.getHeight() - cY;
	// }

	// private static int[] indexStretchLines(PixelDataBean line,
	// PixelDataBean grid) {
	// int stretchVerticalPerfix = 1;
	// int lengthVertical = 0;
	//
	// int[] point = { 0, 0 };
	//
	// _Log.i("start:" + cX);
	//
	// while (point != null) {
	// if (cX >= grid.getWidth()) {
	// return null;
	// }
	// int length = lengthOfLineVertical(line,
	// BitmapCompareUtil.getSubPX(grid, cX, cY, 1, cHeight));
	// if (length == 0) {
	// break;
	// }
	// if (lengthVertical < length) {
	// lengthVertical = length;
	// }
	// resizeCX(stretchVerticalPerfix, grid);
	// }
	// if (lengthVertical != 0) {
	// System.out.println("get a long line:" + lengthVertical + " " + cX);
	// // BitmapCompareUtil.outputPx(BitmapCompareUtil.getSubPX(grid, cX,
	// // cY, cWidth, cHeight));
	// }
	// return BitmapCompareUtil
	// .BigPicInSmallPic(BitmapCompareUtil.getSubPX(grid, cX, cY,
	// cWidth, cHeight), line);
	// }

}