package com.cheesemobile.domain;

import com.cheesemobile.util.BitmapCompareUtil;

public class PixelDataBean {
	private int[][] sourceData;
	private int minx;
	private int miny;
	private int width;
	private int height;

	public PixelDataBean(int[][] sourceData) {
		init(sourceData, 0, 0, BitmapCompareUtil.listWidth(sourceData),
				BitmapCompareUtil.listHeight(sourceData));
	}

	public PixelDataBean(int[][] sourceData, int minx, int miny, int width,
			int height) {
		super();
		init(sourceData, minx, miny, width, height);
	}

	private void init(int[][] sourceData, int minx, int miny, int width,
			int height) {
		this.sourceData = sourceData;
		this.minx = minx;
		this.miny = miny;
		this.width = width;
		this.height = height;
	}

	public boolean compare(PixelDataBean t) {
		for (int i = 0; i < t.getWidth(); i++) {
			for (int j = 0; j < t.getHeight(); j++) {
				if (this.getSourceData()[this.getMinx() + i][this.getMiny() + j] != t.getSourceData()[t.getMinx() + i][t.getMiny() + j]) {
					return false;
				}
			}
		}
		return true;
	}

	
	public int[][] getSourceData() {
		return sourceData;
	}

	public void setSourceData(int[][] sourceData) {
		this.sourceData = sourceData;
	}

	public int getMinx() {
		return minx;
	}

	public void setMinx(int minx) {
		this.minx = minx;
	}

	public int getMiny() {
		return miny;
	}

	public void setMiny(int miny) {
		this.miny = miny;
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

	@Override
	public String toString() {
		return "PixelDataBean [minx=" + minx + ", miny=" + miny + ", width="
				+ width + ", height=" + height + "]";
	}
	
}
