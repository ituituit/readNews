package com.cheesemobile.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cheesemobile.domain.Rectangle.ReleativePosition;
import com.cheesemobile.parser.LayersInfoParser;
import com.cheesemobile.service.Constants;
import com.cheesemobile.service.Pool;
import com.cheesemobile.service.news.NewsController.NewsType;
import com.cheesemobile.util._Log;

public class NewsStyle extends BoundNewsObject {
	private static int _placePointsRectsMin = 1;
	// private List<Rectangle> placePointsRects;
	private static List<Rectangle> childScaledRects;
	private List<Rectangle> placePointsLines;
	private List<String> staticTexts;
	private List<BoundNewsObject> staticObjs = new ArrayList<>();
	private List<BoundNewsObject> childs = new ArrayList<>();
	private boolean hasBackground = false;
	private boolean hasSplitLines = false;
	private Rectangle _container;

	public NewsStyle(int ind, NewsType type, String parentName) {
		super(ind, type, parentName);
	}

	public void addStaticObjs(List objs) {
		List<BoundNewsObject> bo = new ArrayList<BoundNewsObject>();
		for (Object object : objs) {
			bo.add((BoundNewsObject) object);
		}
		for (BoundNewsObject boundNewsObject : bo) {
			addStaticObjs(boundNewsObject);
		}
	}

	public int staticSize() {
		return staticObjs.size();
	}

	public void addStaticObjs(BoundNewsObject obj) {
		staticObjs.add(obj);
		obj.setParentName(this.getFullName());
		boolean exists = obj.existsLayer();
		obj.added(this, staticObjs.size() - 1);
	}

	public void addStaticText(String str) {
		if (staticTexts == null) {
			staticTexts = new ArrayList();
		}
		this.staticTexts.add(str);
	}

	private void attatchForeigns() {
		List<String> foreigns = this.getForeigns();
		for (String fullName : foreigns) {
			NewsImage newsImage = new NewsImage(fullName);
			newsImage.attatch(_container);
		}
	}

	public List<Rectangle> getChildScaledRects() {
		return childScaledRects;
	}

	@Override
	public void added(NewsStyle parent, int toInd) {// //no childs add allafter
													// has childs
		if (typeInLayer(NewsType.BACKGROUND).size() != 0) {
			this.setBackground(parent.getChildScaledRects().get(toInd));
			hasBackground = true;
		}
		if (typeInLayer(NewsType.SPLIT_LINES).size() != 0) {
			hasSplitLines = true;
		}
		if (parent.getChildScaledRects() != null
				&& parent.getChildScaledRects().size() > 0) {
			_container = parent.getChildScaledRects().get(toInd);
		}
	}

	public void updateStaticTexts() {
		List<String> typeInLayer = typeInLayer(NewsType.STATIC_TEXT);
		int ind = 0;
		for (String string : typeInLayer) {
			NewsText nt = new NewsText(string);
			if (staticTexts != null && staticTexts.size() > ind) {
				String str = NewsArticle.jsxOutputFormat1(staticTexts.get(ind));
				if (!str.equals("")) {
					nt.refreshText(str);
				}
			}
			ind++;
		}
	}

	private void setSplitLines() {
		NewsLine newsLine = new NewsLine(this.getFullName(), placePointsLines);
	}

	public void addAll(BoundNewsObject obj, BoundNewsObject... objs) {
		List<BoundNewsObject> list = new ArrayList<>();
		list.add(obj);
		for (BoundNewsObject e : objs) {
			list.add(e);
		}
		addAllSubObjects(list);
	}

	public void addAll2(List list) {
		List<BoundNewsObject> bo = new ArrayList<BoundNewsObject>();
		for (Object object : list) {
			bo.add((BoundNewsObject) object);
		}
		addAllSubObjects(bo);
	}

	public void deleteCantDraws(List<BoundNewsObject> list) {
		// List<BoundNewsObject> nlist = new ArrayList<>();
		List<Integer> inds = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			BoundNewsObject boundNewsObject = list.get(i);
			if (boundNewsObject.canDraw()) {
			} else {
				inds.add(i);
			}
		}
		for (int i = 0; i < inds.size(); i++) {
			// list.set(inds.get(i),null);
			list.remove((int) inds.get(i));
		}
	}

	public void setContainer() {

	}

	public void addAllSubObjects(List<BoundNewsObject> list) {
		deleteCantDraws(list);
		Rectangle background = placesPointsBackgroundRect();
		if (background != null) {
			_container = background;
		}
		if (_container == null) {
			_container = getBound();
		}
		if (hasBackground) {
			_container.scale(-Constants.RULER_1);
		}
		addAllBefore();
		List<Integer> notExists = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			childs.add(list.get(i));
			list.get(i).setParentName(this.getFullName());
			boolean exists = list.get(i).existsLayer();
			if (exists) {
			} else {
				notExists.add(i);
			}
		}
		List<Rectangle> placesPointsRects = placesPointsRectsFromPlaces();
		childScaledRects = new ArrayList<>();
		int rectInd = -1;
		for (int i = 0; i < list.size(); i++) {
			if (notExists.contains(i)) {
				rectInd++;
				if (placesPointsRects.size() <= rectInd) {
					Rectangle lastRect = placesPointsRects
							.get(placesPointsRects.size() - 1);
					Rectangle bound2 = new Rectangle(lastRect.getX(),
							lastRect.getY(), lastRect.getWidth(),
							lastRect.getHeight());
					childScaledRects.add(bound2);
				} else {
					childScaledRects.add(placesPointsRects.get(rectInd));
				}
			} else {
				Rectangle memBound = list.get(i).getBound();
				Rectangle rect = new Rectangle(memBound.getX(),
						memBound.getY(), memBound.getWidth(),
						memBound.getHeight());
				childScaledRects.add(rect);// �ı���rect
			}
		}
		// skip foreigns:
		List<Rectangle> listT = new ArrayList<>();
		List<Integer> inds = new ArrayList<>();
		for (int i = 0; i < childScaledRects.size(); i++) {
			if (!get(i).foreign()) {
				listT.add(childScaledRects.get(i));
				inds.add(i);
			}
		}

		// newList data always in memory
		enlargePlacesPointsRects(listT, _container);
		for (int i = 0; i < inds.size(); i++) {
			childScaledRects.set(inds.get(i), listT.get(i));
		}
		for (int i = 0; i < childScaledRects.size(); i++) {
			if (inds.contains(i)) {
				_Log.i("x");
			}
			get(i).move(get(i).getBound().getPoints()[0],
					childScaledRects.get(i).getPoints()[0]);
			get(i).added(this, i);
		}
		// for (int i = 0; i < notExists.size(); i++) {
		// get(i).move(newList.get(notExists.get(i)).getCenter());
		// get(i).added(this, notExists.get(i));
		// }
		addAllAfter();
	}

	private void addAllBefore() {// inited _container only
		attatchForeigns();
	}

	private void addAllAfter() {
		if (hasSplitLines) {
			this.setSplitLines();
		}
		updateStaticTexts();
	}

	public int size() {
		return childs.size();
	}

	public BoundNewsObject get(int i) {
		// JSXController.getInstance().invoke("objIndexJSX",
		// childs.get(i).getFullName());
		return childs.get(i);
	}

	// public List<Rectangle> getPlacesPointsRects() {
	// if (placePointsRects == null) {
	// placePointsRects = placesPointsRectsFromPlaces();
	// }
	// return placePointsRects;
	// }

	public void enlargePlacesPointsRects(List<Rectangle> list,
			Rectangle contentBound) {
		spreadPlacesPointsRects(list, contentBound);
		for (Rectangle rect : list) {
			int level;
			if (rect.getWidth() < rect.getHeight()) {
				level = (int) Math.floor(rect.getWidth() / 2);
			} else {
				level = (int) Math.floor(rect.getHeight() / 2);
			}
			rect.scale(-level);
		}

		Pool pool = new Pool(list, contentBound, hasSplitLines);
		placePointsLines = pool.getLines();
	}

	private void spreadPlacesPointsRects(List<Rectangle> list,
			Rectangle contentBound) {
		float maxY = contentBound.getY();
		List<Rectangle> pointRects = new ArrayList<Rectangle>();
		for (int i = 0; i < list.size(); i++) {
			Rectangle rectangle = list.get(i);
			boolean same = false;
			if (rectangle.getWidth() == _placePointsRectsMin
					&& rectangle.getHeight() == _placePointsRectsMin) {
				same = true;
				pointRects.add(rectangle);
			}
			if ((i != 0 && rectangle.rectInRect(list.get(i - 1)).inside())) {
				same = true;
				pointRects.add(rectangle);
				pointRects.add(list.get(i - 1));
			}
			if (!same) {
				if (maxY < rectangle.getY()) {
					maxY = rectangle.getY();
				}
			}
		}
		for (int i = 0; i < pointRects.size(); i++) {
			Rectangle rectangle = pointRects.get(i);
			float length = contentBound.getButtom() - maxY;
			float y = (i + 1) * (length / (pointRects.size() + 1));
			rectangle.setY(contentBound.getY() + y);
		}
	}

	public BoundNewsObject getStatic(NewsType type) {
		int i = 0;
		int ind = 0;
		for (BoundNewsObject c : staticObjs) {
			if (c.getType() == type) {
				if (ind++ == i) {
					return c;
				}
			}
		}
		return null;
	}

	public BoundNewsObject get(int i, NewsType type) {
		int ind = 0;
		for (BoundNewsObject c : childs) {
			if (c.getType() == type) {
				if (ind++ == i) {
					return c;
				}
			}
		}
		return null;
	}

	private Rectangle placesPointsBackgroundRect() {
		Rectangle bound = null;
		List<Rectangle> rects = new ArrayList<Rectangle>();
		List<String> list = LayersInfoParser.getInstance().namesInLayer(
				this.getFullName() + "/places");
		String background = null;
		for (String string : list) {
			if (string.contains(NewsType.BACKGROUND.toString())) {
				bound = LayersInfoParser.getInstance().bound(
						this.getFullName() + "/places" + "/" + string);
			}
		}
		return bound;
	}

	private List<Rectangle> placesPointsRectsFromPlaces() {
		List<Rectangle> rects = new ArrayList<Rectangle>();
		List<String> list = LayersInfoParser.getInstance().namesInLayer(
				this.getFullName() + "/places");
		if (placesPointsBackgroundRect() != null) {
			list.remove(NewsType.BACKGROUND.toString());
		}
		if (list.size() == 0) {
			Point p = _container.getCenter();
			rects.add(new Rectangle(p.x, p.y, _placePointsRectsMin,
					_placePointsRectsMin));
			return rects;
		}
		for (String str : list) {
			Rectangle bound = LayersInfoParser.getInstance().bound(
					this.getFullName() + "/places" + "/" + str);
			rects.add(bound);
		}
		return rects;
	}

	public List<String> typeInLayer(NewsType type) {
		List<String> list = LayersInfoParser.getInstance().namesInLayer(
				this.getFullName());
		if (list == null) {
			return null;
		}
		List<String> returnVal = new ArrayList<>();
		for (String string : list) {
			if (string.indexOf(type.toString()) != -1) {
				returnVal.add(this.getFullName() + "/" + string);
			}
		}
		Collections.reverse(returnVal);
		return returnVal;
	}

	public List<String> getForeigns() {
		return typeInLayer(NewsType.FOREIGN);
	}
}