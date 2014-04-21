package com.cheesemobile.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.cheesemobile.domain.PixelDataBean;
import com.cheesemobile.domain.Point;
import com.cheesemobile.domain.Rectangle;
import com.cheesemobile.domain.Rectangle.ReleativePosition;
import com.cheesemobile.util.BitmapCompareUtil;
import com.cheesemobile.util.ShotNewsUtil;
import com.cheesemobile.util._Log;

public class Pool {
	private List<Rectangle> placesRects;
	private List<Rectangle> lines;
	private Rectangle containerRect;
	private boolean hasSplitLine = true;
	public Pool(List<Rectangle> placesRects, Rectangle containerRect,boolean hasSplitLine) {
		super();
		this.placesRects = placesRects;
		this.containerRect = containerRect;
		this.hasSplitLine = hasSplitLine;
		if(placesRects.size() == 1){
			placesRects.remove(0);
			placesRects.add(containerRect);
			lines = containerRect.getLinesRect();
			return;
		}
		scaleSquare();
		fixCracks();
	}
	
	

//	private void setPlacesRects(List<Point> placesPoints) {
//		if (placesRects == null) {
//			placesRects = new ArrayList<>();
//		}
//		for (Point p : placesPoints) {
//			Rectangle rect = new Rectangle(p.x, p.y, 1, 1);
//			placesRects.add(rect);
//		}
//	}

	private void fixCracks() {
		int board = Constants.RULER_1/2;
		if (hasSplitLine) {
			board = Constants.RULER_1;
		}
		containerRect.scale(board);
		List<Rectangle> lines = ShotNewsUtil.genLines(placesRects,
				containerRect);
		containerRect.scale(-board);
		this.lines = lines;
		ShotNewsUtil.print(placesRects, containerRect);
		for (Rectangle rectangle : placesRects) {
			
			rectangle.attachLine(ShotNewsUtil.linepointFromRect(lines),board);
		}
		 ShotNewsUtil.print(placesRects, containerRect);
		 _Log.i("containerRect");
	}

	private void scaleSquare() {
		_Log.i("///start///" + placesRects + containerRect);

		List<Rectangle> passed = new ArrayList<>();
		while (placesRects.size() != passed.size()) {
			for (Rectangle rect : placesRects) {
				for (int j = 0; j < placesRects.size(); j++) {
					if (placesRects.get(j) == rect || passed.contains(rect)) {
						continue;
					}
					ReleativePosition pos = rect.rectInRect(placesRects.get(j));
					if (pos.inside()
							|| rect.rectInRect(containerRect) != ReleativePosition.INSIDE) {
						passed.add(rect);
					}
				}
				if (passed.contains(rect)) {
					continue;
				}
				rect.scale(1);
			}
		}
		for (Rectangle rect : placesRects) {
			rect.scale(-1);
			_Log.i("enlarged:" + rect);
		}
		_Log.i("end");
	}

	public List<Rectangle> getPlacesRects() {
		return placesRects;
	}



	public List<Rectangle> getLines() {
		return lines;
	}
}