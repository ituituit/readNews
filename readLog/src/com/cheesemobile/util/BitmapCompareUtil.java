package com.cheesemobile.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.cheesemobile.domain.PixelDataBean;

public class BitmapCompareUtil {
	public static int CLIP_BOTTOMRIGHT = 0;
	public static int CLIP_CENTER = 1;
	public static PixelDataBean getPixelDataBean(String SamplePath){
		int[][] sourceData = getPX(SamplePath);
		PixelDataBean pdb = new PixelDataBean(sourceData,0,0,BitmapCompareUtil.listWidth(sourceData),BitmapCompareUtil.listHeight(sourceData));
		return pdb;
	}
	public static int[][] getPX(String SamplePath) {
		InputStream srcStream;
		try {
			srcStream = new FileInputStream(SamplePath);
			return getPX(srcStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int[][] getPX(InputStream in) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int width = bi.getWidth();
		int height = bi.getHeight();
		int minx = bi.getMinX();
		int miny = bi.getMinY();
		int[][] list = new int[width][height];
		for (int i = minx; i < width; i++) {
			for (int j = miny; j < height; j++) {
				list[i][j] = bi.getRGB(i, j);//
			}
		}
		return list;
	}

//	public static int[][] getSubPX(int[][] source, int minx, int miny,
//			int width, int height) {
//		// _Log.i(minx + " " + miny + " " + width + " " + height);
//		int[][] list = new int[width][height];
//		for (int i = minx; i < width + minx; i++) {
//			for (int j = miny; j < height + miny; j++) {
//				list[i - minx][j - miny] = source[i][j];
//			}
//		}
//		return list;
//	}

	public static PixelDataBean getSubPX(PixelDataBean source, int minx, int miny,
			int width, int height) {
		// _Log.i(minx + " " + miny + " " + width + " " + height);
		PixelDataBean pdb = new PixelDataBean(source.getSourceData(),source.getMinx() + minx,source.getMiny() + miny,width,height);
		return pdb;
	}
	
	public static int[] BigPicInSmallPic(PixelDataBean list1, PixelDataBean list2) {
		return BigPicInSmallPic(list1, list2, false, CLIP_BOTTOMRIGHT);
	}

	public static int[] BigPicInSmallPic(PixelDataBean list1, PixelDataBean list2,
			boolean getAll) {
		return BigPicInSmallPic(list1, list2, getAll, CLIP_BOTTOMRIGHT);
	}

	public static int[] BigPicInSmallPic(PixelDataBean list1, PixelDataBean list2,
			boolean compareAll, int clipType) {
		int bWidth = list1.getWidth();
		int bHeight = list1.getHeight();
		int sWidth = list2.getWidth();
		int sHeight = list2.getHeight();
		int[] clipCoord = { sWidth, sHeight };
		if (clipType == CLIP_CENTER) {
			int[] tmp = { sWidth / 2, sHeight / 2 };
			clipCoord = tmp;
		}
//		_Log.i("BigPicInSmallPicStart");
		int[] intsPair = new int[0];
		for (int y = 0; y < bHeight - sHeight + 1; y++) {
			//_Log.i("line " + y);
			for (int x = 0; x < bWidth - sWidth + 1; x++) {
				PixelDataBean listT = getSubPX(list1, x, y, sWidth, sHeight);
				if (listT.compare(list2)) {
					if (compareAll) {
						int[] ints = new int[intsPair.length + 2];
						for (int i = 0; i < intsPair.length; i++) {
							ints[i] = intsPair[i];
						}
						ints[ints.length - 2] = x + clipCoord[0];
						ints[ints.length - 1] = y + clipCoord[1];
						intsPair = ints;
					} else {
						int[] ints = { x + clipCoord[0], y + clipCoord[1] };
						return ints;
					}
				}
			}
		}
//		_Log.i("BigPicInSmallPicEndNoFound");
		if (intsPair.length > 0) {
			return intsPair;
		}
		return null;
	}

//	public static boolean listCompare(PixelDataBean list1, PixelDataBean list2) {
//		for (int i = 0; i < list2.getWidth(); i++) {
//			for (int j = 0; j < list2.getHeight(); j++) {
//				if (list1.getSourceData()[i][j] != list2[i][j]) {
//					return false;
//				}
//			}
//		}
//		return true;
//	}

	public static boolean picIsBlack(PixelDataBean list1) {
		for (int i = 0; i < listWidth(list1.getSourceData()); i++) {
			for (int j = 0; j < listHeight(list1.getSourceData()); j++) {
				if (list1.getSourceData()[i][j] != convertPixToInt("0,0,0")) {
					return false;
				}
			}
		}
		return true;
	}

	public static int convertPixToInt(String str) {
		String[] rgbs = str.split(",");
		int r = Integer.parseInt(rgbs[0]);
		int g = Integer.parseInt(rgbs[1]);
		int b = Integer.parseInt(rgbs[2]);
		int a = 255;
		int pixel = r << 16;
		pixel += g << 8;
		pixel += b;
		pixel += a << 24;
		return pixel;
	}

	public static String convertIntToPic(int pixel) {
		int[] rgb = new int[3];
		rgb[0] = (pixel & 0xff0000) >> 16;
		rgb[1] = (pixel & 0xff00) >> 8;
		rgb[2] = (pixel & 0xff);
		return rgb[0] + "," + rgb[1] + "," + rgb[2];
	}

	public static void outputPx(int[][] list) {
		BufferedImage bi = new BufferedImage(listWidth(list), listHeight(list),
				BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < listWidth(list); i++) {
			for (int j = 0; j < listHeight(list); j++) {
				bi.setRGB(i, j, list[i][j]);
			}
		}
		try {
			ImageIO.write(bi, "bmp", new File(
					"/Users/pwl/Desktop/sample.bmp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int listHeight(int[][] list) {
		return list[0].length;
	}

	public static int listWidth(int[][] list1) {
		return list1.length;
	}
}
