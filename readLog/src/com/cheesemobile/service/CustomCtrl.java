package com.cheesemobile.service;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cheesemobile.domain.CustomCtrlBean;
import com.cheesemobile.domain.PixelDataBean;
import com.cheesemobile.util.BitmapCompareUtil;
import com.cheesemobile.util.FileUtil;
import com.cheesemobile.util._Log;

public class CustomCtrl {
	protected CustomCtrl() {
	}

	private static CustomCtrl instance = null;
	private ArrayList<CustomCtrlBean> controlList = new ArrayList<CustomCtrlBean>();

	private float offsetRows = 0;

	public static CustomCtrl getInstance() {
		if (instance == null) {
			instance = new CustomCtrl();
		}
		return instance;
	}

	public void ctrlOnSample(String... strings) {
		PixelDataBean srcData = BitmapCompareUtil.getPixelDataBean(strings[0]);
		String[] extraSample = getExtraParams(1, strings);
		controlList = picsInData(srcData, extraSample);
		for (int i = 0; i < controlList.size(); i++) {
			CustomCtrlBean ccb = controlList.get(i);
			ccb.setExtra(ccb.getName());
			ccb.setName("ctrlOnSample" + i + "_0");
		}
		_Log.i(controlList.toString());
	}

	// private CustomCtrlBean ctrlOnSample(String srcPath, String samplePath,
	// Boolean getAll) {
	// PixelDataBean src = BitmapCompareUtil.getPixelDataBean(srcPath);
	// PixelDataBean sample = BitmapCompareUtil.getPixelDataBean(samplePath);
	// int[] ints = BitmapCompareUtil.BigPicInSmallPic(src, sample, getAll);
	// if (ints == null) {
	// return null;
	// }
	// return cmpResultToCustomCtrl(ints, sample,
	// FileUtil.fileName(samplePath));
	// }

	private String[] getExtraParams(int startInd, String... strings) {
		String[] strs = new String[strings.length - startInd];
		for (int i = startInd; i < strings.length; i++) {
			strs[i - startInd] = strings[i];
		}
		return strs;
	}

	@Deprecated
	public void ctrlsOnGridRight(String... strings) {
		ctrlsOnGrid(strings);
		for (int i = 0; i < controlList.size(); i++) {
			int pos = controlList.get(i).getX() + controlList.get(i).getWidth()
					- 10;
			controlList.get(i).setActX(pos);
		}
		_Log.i(controlList.toString());
	}

	@Deprecated
	public void ctrlsOnListLeft(String... strings) {
		ctrlsOnList(strings);
		for (int i = 0; i < controlList.size(); i++) {
			int pos = controlList.get(i).getX() + 10;
			controlList.get(i).setActX(pos);
		}
		_Log.i(controlList.toString());
	}

	/**
	 * get every Rectangle in grid by horizontal sample and vertical sample data
	 * then encapsulate data to a global variable 'controlList'
	 * 
	 * @param strings
	 *            params invoked by reflect the first parameter is the path of
	 *            source data the second parameter is horizontal sample data the
	 *            third parameter is vertical sample data the parameter after
	 *            third is parameters pass to 'compareExtraSamplesToControlist'
	 */
	public void ctrlsOnGrid2(String... strings) {
		PixelDataBean srcData = BitmapCompareUtil.getPixelDataBean(strings[0]);
		PixelDataBean lineHorizontal = BitmapCompareUtil
				.getPixelDataBean(strings[1]);
		PixelDataBean lineVertical = BitmapCompareUtil
				.getPixelDataBean(strings[2]);
		List<Integer> horIndexs = getPoints(
				linearInData(lineHorizontal, srcData, false), false);

		List<Point> horLines = delCloseLines(horIndexs,
				lineHorizontal.getHeight());
		List<List<Point>> arrVertLines = new ArrayList<List<Point>>();
		for (Point p : horLines) {
			PixelDataBean horData = BitmapCompareUtil.getSubPX(srcData, 0, p.x,
					srcData.getWidth(), p.y - p.x);
			List<Integer> vertIndexs = getPoints(
					linearInData(lineVertical, horData, true), true);
			List<Point> vertLines = delCloseLines(vertIndexs,
					lineVertical.getWidth());
			arrVertLines.add(vertLines);
		}

		encapsulateRectToControlList(gridBoundsToRect(horLines, arrVertLines),
				"ctrlsOnGrid");
		compareExtraSamplesToControlist(3, srcData, strings);
		_Log.i(controlList.toString());
		_Log.i("ctrlsOnGrid2");
	}

	/**
	 * get every line in list by sample then encapsulate data to a global
	 * variable 'controlList'
	 * 
	 * @param strings
	 *            params invoked by reflect the first parameter is the path of
	 *            source data the second parameter is sample data compared with
	 *            the parameter after second is parameters pass to
	 *            'compareExtraSamplesToControlist'
	 */
	public void ctrlsOnList2(String... strings) {
		PixelDataBean srcData = BitmapCompareUtil.getPixelDataBean(strings[0]);
		PixelDataBean lineData = BitmapCompareUtil.getPixelDataBean(strings[1]);
		List<Point> lineIndexs = linearInData(lineData, srcData, false);

		List<Point> horLines = delCloseLines(getPoints(lineIndexs, false),
				lineData.getHeight());
		List<Integer> xValues = getPoints(lineIndexs, true);
		List<List<Point>> arrVertLines = new ArrayList<List<Point>>();
		for (int i = 0; i < xValues.size(); i++) {
			List<Point> vertLines = new ArrayList<Point>();
			Point p = new Point(xValues.get(i), srcData.getWidth());
			vertLines.add(p);
			arrVertLines.add(vertLines);
		}

		encapsulateRectToControlList(gridBoundsToRect(horLines, arrVertLines),
				"ctrlsOnList");
		compareExtraSamplesToControlist(2, srcData, strings);
		_Log.i(controlList.toString());
		_Log.i("ctrlsOnList2");
	}

	public void customTreeView(String... strings) {
		PixelDataBean srcData = BitmapCompareUtil.getPixelDataBean(strings[0]);
		offsetRows = -.5f;
		ctrlsOnList2(strings);
		for (CustomCtrlBean ccb : controlList) {
			ccb.setY(Math.round(ccb.getY() + (ccb.getHeight() * offsetRows)));
			ccb.setX(0);
			setCCActToCenter(ccb);
		}

		String[] names = { "plus", "minus", "plus_opcenter_treeview",
				"minus_opcenter_treeview" };
		// List<Integer> indexs = hasExtra(names);
		// _Log.i(controlList.toString());
		// for (int i : indexs) {
		// CustomCtrlBean ccb = controlList.get(i);
		// ccb.setX(ccb.getX() - 6);
		// }
		CustomCtrlBean ccb = cpyCustomCtrlBean(controlList.get(controlList
				.size() - 1));
		ccb.setName("ctrlsOnList" + controlList.size() + "_0");
		ccb.setY(ccb.getY() + ccb.getHeight());
		setCCActToCenter(ccb);
		controlList.add(ccb);
		compareExtraSamplesToControlist(2, srcData, strings);
		_Log.i(controlList.toString());
		_Log.i("customTreeView");
	}

	private CustomCtrlBean cpyCustomCtrlBean(CustomCtrlBean ccb) {
		CustomCtrlBean ccbt = new CustomCtrlBean(ccb.getName());
		ccbt.setX(ccb.getX());
		ccbt.setY(ccb.getY());
		ccbt.setWidth(ccb.getWidth());
		ccbt.setHeight(ccb.getHeight());
		ccbt.setActX(ccb.getActX());
		ccbt.setActY(ccb.getActY());
		return ccbt;
	}

	private List<Integer> hasExtra(String[] names) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < controlList.size(); i++) {
			CustomCtrlBean ccb = controlList.get(i);
			for (String name : names) {
				if (ccb.getExtra().contains(name)) {
					list.add(i);
				}
			}
		}
		return list;
	}

	// private void encapsulateRectToControlList(List<Rectangle> rects,String
	// name){
	// List<List<Rectangle>> rectsList= new ArrayList<List<Rectangle>>();
	// rectsList.add(rects);
	// encapsulateRectToControlList(rectsList,name);
	// }

	private void encapsulateRectToControlList(List<List<Rectangle>> rectsList,
			String name) {
		for (int i = 0; i < rectsList.size(); i++) {
			List<Rectangle> rects = rectsList.get(i);
			for (int j = 0; j < rects.size(); j++) {
				Rectangle r = rects.get(j);
				CustomCtrlBean cc = new CustomCtrlBean(name + i + "_" + j);
				cc.setX(r.x);
				cc.setY(r.y);
				cc.setWidth(r.width);
				cc.setHeight(r.height);
				setCCActToCenter(cc);
				controlList.add(cc);
			}
		}
	}

	private List<List<Rectangle>> gridBoundsToRect(List<Point> horLines,
			List<List<Point>> arrVertLines) {
		List<List<Rectangle>> rectsList = new ArrayList<List<Rectangle>>();
		for (int i = 0; i < horLines.size(); i++) {
			List<Point> vertLines = arrVertLines.get(i);
			List<Rectangle> rects = new ArrayList<Rectangle>();
			for (int j = 0; j < vertLines.size(); j++) {
				Rectangle rect = new Rectangle();
				rect.x = vertLines.get(j).x;
				rect.y = horLines.get(i).x;
				rect.width = vertLines.get(j).y - vertLines.get(j).x;
				rect.height = horLines.get(i).y - horLines.get(i).x;
				rects.add(rect);
			}
			rectsList.add(rects);
		}
		return rectsList;
	}

	public static List<Point> delCloseLines(List<Integer> list, int sampleLen) {
		List<Point> returnVals = new ArrayList<Point>();
		for (int i = 0; i < list.size() - 1; i++) {
			int cur = list.get(i);
			int nex = list.get(i + 1);
			boolean isClose = (cur == nex - sampleLen) ? true : false;
			if (!isClose) {
				Point p = new Point(cur, nex);
				returnVals.add(p);
			}
		}
		return returnVals;
	}

	public static List<List<PixelDataBean>> closeLines(List<PixelDataBean> list) {
		List<PixelDataBean> group = new ArrayList<PixelDataBean>();
		List<List<PixelDataBean>> groups = new ArrayList<List<PixelDataBean>>();
		group.add(list.get(0));
		for (int i = 0; i < list.size() - 1; i++) {
			PixelDataBean cur = list.get(i);
			PixelDataBean nex = list.get(i + 1);
			boolean isCloseX = cur.getMinx() == nex.getMinx() - nex.getWidth();
			boolean isCloseY = cur.getMiny() == nex.getMiny() - nex.getHeight();
			if (!isCloseX && !isCloseY) {
				groups.add(group);
				group = new ArrayList<PixelDataBean>();
			}
			group.add(nex);
		}
		groups.add(group);
		return groups;
	}

	
	// private List<Integer> getBlankLines(int[][] srcData,boolean isVertical){
	// int width = BitmapCompareUtil.listWidth(srcData);
	// int height = BitmapCompareUtil.listHeight(srcData);
	// List<Integer> returnVals = new ArrayList<Integer>();
	// for( int i = 0; i < height; i++){
	// int[][] lineSrc = BitmapCompareUtil.getSubPX(srcData, 0, i, width, 1);
	// int[][] lineSameCol = new int[width][1];
	// int pixel = srcData[0][i];
	// for (int j = 0; j < width; j++) {
	// lineSameCol[j][0] = pixel;
	// }
	// if(BitmapCompareUtil.BigPicInSmallPic(lineSrc,lineSameCol) != null){
	// returnVals.add(i);
	// }
	// }
	// return returnVals;
	// }

	public static List<Point> linearInData(PixelDataBean sampleData,
			PixelDataBean srcData, boolean isVertical) {
		int height = srcData.getHeight();
		int width = srcData.getWidth();
		int sHeight = sampleData.getHeight();
		int sWidth = sampleData.getWidth();
		int bLen = 0;
		int sLen = 0;
		if (!isVertical) {
			bLen = height;
			sLen = sHeight;
		} else {
			bLen = width;
			sLen = sWidth;
		}
		List<Point> returnVals = new ArrayList<Point>();
		for (int i = 0; i < bLen; i += sLen) {
			int minx = !isVertical ? 0 : i;
			int miny = !isVertical ? i : 0;
			int cWidth = !isVertical ? width : sWidth;
			int cHeight = !isVertical ? sHeight : height;
			PixelDataBean subData = BitmapCompareUtil.getSubPX(srcData, minx,
					miny, cWidth, cHeight);
			int[] pos = BitmapCompareUtil.BigPicInSmallPic(subData, sampleData);
			if (pos != null) {
				if (!isVertical) {
					returnVals.add(new Point(pos[0], i));
				} else {
					returnVals.add(new Point(i, pos[1]));
				}
			}
		}
		return returnVals;
	}

	// /**
	// * compare every line in source data is same with sample then return all
	// * lines matched
	// *
	// * @param sampleData
	// * data of line to compare
	// * @param srcData
	// * data of source
	// * @param isVertical
	// * {@code true} if true compare every vertical line in
	// * source,means sample data is a horizontal data in source data
	// * @return a list contains all sample match linear data in source data
	// */
	// private List<Integer> compareAllLinearWithData(PixelDataBean sampleData,
	// PixelDataBean srcData, boolean isVertical) {
	// List<Point> points = linearInData(sampleData, srcData, isVertical);
	// getPoints
	// }
	public static List<Integer> getPoints(List<Point> points, boolean isVertical) {
		List<Integer> returnVals = new ArrayList<Integer>();
		for (Point p : points) {
			if (!isVertical) {
				returnVals.add(p.y);
			} else {
				returnVals.add(p.x);
			}
		}
		return returnVals;
	}

	@Deprecated
	public void ctrlsOnGrid(String... strings) {
		String srcPath = strings[0];
		String samplePath = strings[1];
		PixelDataBean srcData = BitmapCompareUtil.getPixelDataBean(srcPath);
		PixelDataBean sample = BitmapCompareUtil.getPixelDataBean(samplePath);

		int[] ints = BitmapCompareUtil.BigPicInSmallPic(srcData, sample, true,
				BitmapCompareUtil.CLIP_CENTER);
		if (ints == null) {
			return;
		}
		ArrayList<ArrayList<int[]>> lineList = calcRowAndCowNum(ints);
		int row = lineList.get(0).size() + 1;
		int cow = lineList.size() + 1;
		int height = 20;
		ArrayList<Integer> heights = new ArrayList<Integer>();
		for (int i = 1; i < lineList.get(0).size(); i++) {
			int yCurrent = lineList.get(0).get(i)[1];
			int yBefore = lineList.get(0).get(i - 1)[1];
			int hei = yCurrent - yBefore;
			heights.add(hei);
		}
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < heights.size(); i++) {
			Integer count = map.get(heights.get(i));
			map.put(heights.get(i), count == null ? 1 : count + 1);
		}
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			height = (Integer) it.next();
			break;
		}
		for (int i = 0; i < cow; i++) {
			for (int j = 0; j < row; j++) {
				int x;
				if (i == 0) {
					x = 0;
				} else {
					x = lineList.get(i - 1).get(0)[0];
				}
				int y;
				if (j == 0) {
					y = lineList.get(0).get(0)[1] - height;
				} else {
					y = lineList.get(0).get(j - 1)[1];
				}

				int width;
				if (i != cow - 1) {
					width = lineList.get(i).get(0)[0];
				} else {
					width = srcData.getWidth() - x;
				}
				CustomCtrlBean cc = new CustomCtrlBean(
						FileUtil.fileName(samplePath) + j + "_" + i);
				cc.setX(x);
				cc.setY(y);
				cc.setWidth(width);
				cc.setHeight(height);
				setCCActToCenter(cc);
				controlList.add(cc);
			}
		}
		compareExtraSamplesToControlist(2, srcData, strings);
		_Log.i(controlList.toString());
	}

	private ArrayList<ArrayList<int[]>> calcRowAndCowNum(int[] ints) {
		int rowNum = 0;
		int cowNum = 0;
		int tmp = 0;
		for (int i = 0; i < ints.length; i += 2) {
			if (ints[i] != tmp) {
				rowNum++;
			}
			tmp = ints[i];
		}
		tmp = 0;
		for (int i = 1; i < ints.length; i += 2) {
			if (ints[i] != tmp) {
				cowNum++;
			}
			tmp = ints[i];
		}
		ArrayList<ArrayList<int[]>> list = new ArrayList<ArrayList<int[]>>();
		int indInt = 0;
		for (int i = 0; i < rowNum; i++) {
			ArrayList<int[]> row = new ArrayList<int[]>();
			for (int j = 0; j < cowNum; j += 1) {
				int[] point = new int[2];
				point[0] = ints[indInt];
				indInt++;
				point[1] = ints[indInt];
				indInt++;
				row.add(point);
			}
			list.add(row);
		}
		return list;
	}

	// public void ctrlsOnList(String... strings) {
	// String srcPath = strings[0];
	// String samplePath = strings[1];
	// try {
	// InputStream srcStream = new FileInputStream(srcPath);
	// InputStream sampleStream = new FileInputStream(samplePath);
	// calcForRowsNumber(BitmapCompareUtil.getPX(srcStream),
	// BitmapCompareUtil.getPX(sampleStream),
	// FileUtil.fileName(samplePath));
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// }
	// }

	private void compareExtraSamplesToControlist(int startInd,
			PixelDataBean srcData, String... strings) {
		String[] extraSample = getExtraParams(startInd, strings);
		ArrayList<CustomCtrlBean> extraSamples = picsInData(srcData,
				extraSample);
		for (int i = 0; i < controlList.size(); i++) {
			CustomCtrlBean ctrl = controlList.get(i);
			for (int j = 0; j < extraSamples.size(); j++) {
				if (ctrl.inCtrl(extraSamples.get(j))) {
					ctrl.setExtra(extraSamples.get(j).getName());
				}
			}
		}
	}

	private ArrayList<CustomCtrlBean> picsInData(PixelDataBean srcData,
			String[] picPaths) {
		ArrayList<CustomCtrlBean> extraSamples = new ArrayList<CustomCtrlBean>();
		for (int i = 0; i < picPaths.length; i++) {
			PixelDataBean extraData = BitmapCompareUtil
					.getPixelDataBean(picPaths[i]);
			int[] ints = BitmapCompareUtil.BigPicInSmallPic(srcData, extraData,
					true);
			if (ints == null) {
				continue;
			}
			for (int j = 0; j < ints.length; j += 2) {
				int pos[] = { ints[j], ints[j + 1] };
				extraSamples.add(cmpResultToCustomCtrl(pos, extraData,
						FileUtil.fileName(picPaths[i])));
			}
		}
		return extraSamples;
	}

	@Deprecated
	public void ctrlsOnList(String... strings) {
		String srcPath = strings[0];
		PixelDataBean srcData = BitmapCompareUtil.getPixelDataBean(srcPath);
		int width = srcData.getWidth() - 20;
		int[][] sampleDataSrc = new int[width][1];
		for (int i = 0; i < width; i++) {
			sampleDataSrc[i][0] = BitmapCompareUtil
					.convertPixToInt("255,255,255");
		}
		PixelDataBean sampleData = new PixelDataBean(sampleDataSrc);
		calcForRowsNumber(srcData, sampleData, "ctrlsOnList");
		compareExtraSamplesToControlist(1, srcData, strings);
		_Log.i(controlList.size() + controlList.toString());
	}

	private void calcForRowsNumber(PixelDataBean samplePicData,
			PixelDataBean dottedTableViewDatas, String name) {
		PixelDataBean bigger = samplePicData;
		int[] ints;
		int rowMinHeight = 7;
		int maxWhiteLine = 40;
		int sumY = 0;
		controlList = new ArrayList<CustomCtrlBean>();
		ArrayList<Integer> listOfLinePos = new ArrayList<Integer>();
		int sum = 0;
		while ((ints = BitmapCompareUtil.BigPicInSmallPic(bigger,
				dottedTableViewDatas)) != null) {
			_Log.i(ints[0] + " " + ints[1]);
			int splitH = ints[1];
			sumY += splitH;
			if (bigger.getHeight() < splitH) {
				break;
			}
			sum++;
			if (sum == 8) {

			}
			bigger = BitmapCompareUtil.getSubPX(bigger, 0, splitH,
					bigger.getWidth(), bigger.getHeight() - splitH);
			if (splitH > rowMinHeight || listOfLinePos.size() == 0) {
				listOfLinePos.add(sumY);
				maxWhiteLine = 40;
			} else {
				if (--maxWhiteLine < 0) {
					break;
				}
			}
		}
		for (int i = 0; i < listOfLinePos.size() - 1; i++) {
			CustomCtrlBean cc = new CustomCtrlBean(name + (i) + "_0");
			cc.setY(listOfLinePos.get(i));
			cc.setWidth(samplePicData.getWidth());
			cc.setHeight(listOfLinePos.get(i + 1) - listOfLinePos.get(i) + 2);
			setCCActToCenter(cc);
			controlList.add(cc);
		}

		// //add firstRow
		// CustomCtrlBean cc = new CustomCtrlBean(name + "0_0");
		// cc.setY(controlList.get(0).getY() - controlList.get(0).getHeight());
		// cc.setWidth(BitmapCompareUtil.listWidth(samplePicData));
		// cc.setHeight(listOfLinePos.get(1) - listOfLinePos.get(0));
		// setCCActToCenter(cc);
		// controlList.add(0, cc);
		// //add firstRow
		// _Log.i(controlList.toString());
		// _Log.i("row number is:" + listOfLinePos.size());
	}

	private CustomCtrlBean cmpResultToCustomCtrl(int[] ints,
			PixelDataBean data, String name) {
		if (ints != null) {
			int width = data.getWidth();
			int height = data.getHeight();
			CustomCtrlBean cc = new CustomCtrlBean(name);
			cc.setX(ints[0] - width);
			cc.setY(ints[1] - height);
			cc.setWidth(width);
			cc.setHeight(height);
			setCCActToCenter(cc);
			return cc;
		}
		return null;
	}

	private void setCCActToCenter(CustomCtrlBean cc) {
		cc.setActX(cc.getX() + cc.getWidth() / 2);
		cc.setActY(cc.getY() + cc.getHeight() / 2);
	}

	public void write(String iniPath) {
		_Log.i("wirting");
		IniReader reader = new IniReader(iniPath);
		StringBuilder sb = new StringBuilder();
		if (controlList.size() == 0) {
			reader.setKey("array_of_custom_ctrl", "parameters", "0");
			return;
		}
		Iterator<CustomCtrlBean> it = controlList.iterator();
		for (;;) {
			CustomCtrlBean e = it.next();
			sb.append(e);
			if (!it.hasNext())
				break;
			sb.append(',');
		}
		// "C:\\Users\\peiwen\\Desktop\\readLog\\0221_OpCenter_Completed\\myini.ini");
		reader.setKey("array_of_custom_ctrl", "parameters", sb.toString());
	}
}

// public void _customFormsunfoldMinus(String... strings) {
// String samplePath = strings[0];
// try {
// _Log.i("_customConfigureForms:" + samplePath);
// InputStream inStream = this.getClass().getResourceAsStream(
// minusFroms);
// minusFromsDatas = BitmapCompareUtil.getPX(inStream);
// InputStream img1 = new FileInputStream(samplePath);
// samplePicData = BitmapCompareUtil.getPX(img1);
// int[] ints = BitmapCompareUtil.BigPicInSmallPic(samplePicData,
// minusFromsDatas);
// controlList = new ArrayList<CustomCtrlBean>();
// if (ints != null) {
// int width = BitmapCompareUtil.listWidth(minusFromsDatas);
// int height = BitmapCompareUtil.listHeight(minusFromsDatas);
// CustomCtrlBean cc = new CustomCtrlBean("minus");
// cc.setX(ints[0] - width);
// cc.setY(ints[1] - height);
// cc.setWidth(width);
// cc.setHeight(height);
// setCCActToCenter(cc);
// controlList.add(cc);
// } else {
// controlList = null;
// }
// } catch (FileNotFoundException e) {
// e.printStackTrace();
// }
// }
// public void _customTableView(String... strings) {
// String samplePath = strings[0];
// try {
// _Log.i("" + samplePath);
// InputStream inStream = this.getClass().getResourceAsStream(
// dottedTableView);
// dottedTableViewDatas = BitmapCompareUtil.getPX(inStream);
// InputStream img1 = new FileInputStream(samplePath);
// samplePicData = BitmapCompareUtil.getPX(img1);
// calcForRowsNumber();
// } catch (FileNotFoundException e) {
// e.printStackTrace();
// }
// }
