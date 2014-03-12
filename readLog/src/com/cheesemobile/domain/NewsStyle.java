package com.cheesemobile.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cheesemobile.parser.LayersInfoParser;
import com.cheesemobile.service.Constants;
import com.cheesemobile.service.Pool;
import com.cheesemobile.service.news.NewsController.NewsType;
import com.cheesemobile.util._Log;

public class NewsStyle extends BoundNewsObject {
	private List<Rectangle> placePointsRects;
	private List<Rectangle> placePointsLines;
	private List<String> staticTexts;
	private List<BoundNewsObject> childs = new ArrayList<>();
	private boolean hasBackground = false;
	private boolean hasSplitLines = false;

	public NewsStyle(int ind, NewsType type, String parentName) {
		super(ind, type, parentName);
	}

	public void addStaticText(String str){
		if(staticTexts == null){
			staticTexts = new ArrayList();
		}
		this.staticTexts.add(str);
	}
	
	@Override
	public void added(NewsStyle parent, int toInd) {
		if (typeInLayer(NewsType.BACKGROUND).size() != 0) {
			this.setBackground(parent.getPlacesPointsRects().get(toInd));
			hasBackground = true;
		}
		if (typeInLayer(NewsType.SPLIT_LINES).size() != 0) {
			hasSplitLines = true;
		}
		updateStaticTexts();
	}

	public void updateStaticTexts(){
		List<String> typeInLayer = typeInLayer(NewsType.STATIC_TEXT);
		int ind = 0;
		for (String string : typeInLayer) {
			NewsText nt = new NewsText(string);
			if (staticTexts != null && staticTexts.size() > ind) {
				String str = staticTexts.get(ind);
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
		addAll(list);
	}

	public void addAll2(List list) {
		List<BoundNewsObject> bo = new ArrayList<BoundNewsObject>();
		for (Object object : list) {
			bo.add((BoundNewsObject) object);
		}
		addAll(bo);
	}

	public void addAll(List<BoundNewsObject> list) {
		Rectangle container = placesPointsBackgroundRect();
		if(container == null){
			container = getBound();
		}
		if (hasBackground) {
			container.scale(-Constants.RULER_1);
		}
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
		List<Rectangle> placesPointsRects = this.getPlacesPointsRects();
		List<Rectangle> newList = new ArrayList<>();
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
					newList.add(bound2);
				} else {
					newList.add(placesPointsRects.get(rectInd));
				}
			} else {
				Rectangle memBound = list.get(i).getBound();
				Rectangle rect = new Rectangle(memBound.getX(),
						memBound.getY(), memBound.getWidth(),
						memBound.getHeight());

				newList.add(rect);// ÎÄ±¾µÄrect
			}
		}
		enlargePlacesPointsRects(newList, container);

		for (int i = 0; i < newList.size(); i++) {
			get(i).move(get(i).getBound().getPoints()[0],
					newList.get(i).getPoints()[0]);
			get(i).added(this, i);
		}
		// for (int i = 0; i < notExists.size(); i++) {
		// get(i).move(newList.get(notExists.get(i)).getCenter());
		// get(i).added(this, notExists.get(i));
		// }
		addAllAfter();
	}

	// public void add(BoundNewsObject e) {
	// childs.add(e);
	// if (!_objInited) {
	// List<Rectangle> placesPointsRects = this.getPlacesPointsRects();
	// enlargePlacesPointsRects(placesPointsRects);
	// _objInited = true;
	// }
	// e.setParentName(this.getFullName());
	// boolean exists = e.existsLayer();
	//
	// if (exists) {
	// e.added(this, -1);
	// } else {
	// int rectInd = childs.size() - 1;
	// if (placePointsRects.size() < rectInd + 1) {
	// Rectangle lastRect = placePointsRects.get(placePointsRects
	// .size() - 1);
	// Rectangle bound2 = new Rectangle(lastRect.getX(),
	// lastRect.getY(), lastRect.getWidth(),
	// lastRect.getHeight());
	// // bound2.setX(lastRect.getX());
	// // bound2.setY(lastRect.getY());// + lastRect.getHeight());
	// placePointsRects.add(bound2);
	// }
	// e.move(getPlacesPointsRects().get(rectInd).getCenter());
	// e.added(this, rectInd);
	// }
	// }

	private void addAllAfter() {
		if (hasSplitLines) {
			this.setSplitLines();
		}
	}

	public int size() {
		return childs.size();
	}

	public BoundNewsObject get(int i) {
		// JSXController.getInstance().invoke("objIndexJSX",
		// childs.get(i).getFullName());
		return childs.get(i);
	}

	public List<Rectangle> getPlacesPointsRects() {
		if (placePointsRects == null) {
			placePointsRects = placesPointsRectsFromPlaces();
		}
		return placePointsRects;
	}

	public void enlargePlacesPointsRects(List<Rectangle> list,
			Rectangle contentBound) {
		for (Rectangle rect : list) {
			float level = 0;
			if (rect.getWidth() < rect.getHeight()) {
				level = rect.getWidth() / 2 + 1;
			} else {
				level = rect.getHeight() / 2 + 1;
			}
			rect.scale(-level);
		}
		Pool pool = new Pool(list, contentBound);
		placePointsRects = pool.getPlacesRects();
		placePointsLines = pool.getLines();
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

	private Rectangle placesPointsBackgroundRect(){
		Rectangle bound = null;
		List<Rectangle> rects = new ArrayList<Rectangle>();
		List<String> list = LayersInfoParser.getInstance().namesInLayer(
				this.getFullName() + "/places");
		String background = null;
		for (String string : list) {
			if(string.contains(NewsType.BACKGROUND.toString())){
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
		if(placesPointsBackgroundRect() != null){
			list.remove(NewsType.BACKGROUND.toString());
		}
		if (list.size() == 0) {
			Point p = this.getBound().getCenter();
			rects.add(new Rectangle(p.x, p.y, 1, 1));
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