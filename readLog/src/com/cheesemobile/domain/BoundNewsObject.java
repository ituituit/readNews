package com.cheesemobile.domain;

import java.io.Serializable;
import java.util.List;

import com.cheesemobile.parser.LayersInfoParser;
import com.cheesemobile.service.Constants;
import com.cheesemobile.service.JSXController;
import com.cheesemobile.service.news.NewsController.NewsType;
import com.cheesemobile.util._Log;

public abstract class BoundNewsObject implements Serializable {
	private static final long serialVersionUID = -4076032451122684376L;
	private NewsType type;
	private int id;
	private Rectangle bound;
	private NewsBackground background;
	private String parentName;
	private String customName = null;
//	private NewsBean article;
//	
//	public NewsBean getArticle() {
//		return article;
//	}
//
//	public void setArticle(NewsBean article) {
//		this.article = article;
//	}

	public BoundNewsObject(int id, NewsType type) {
		super();
		init(id, type, "");
	}

	public BoundNewsObject(String fullName, NewsType type) {
		List<String> namesList = LayerInfoBean.getNamesList(fullName);
		customName = namesList.get(namesList.size() - 1);
		String parentName = LayerInfoBean.minusLast(fullName, customName);
		init(-1, type, parentName);
	}

	public BoundNewsObject(int id, NewsType type, String parentName) {
		super();
		init(id, type, parentName);
	}

	private void init(int id, NewsType type, String parentName) {
		this.id = id;
		this.type = type;
		this.parentName = parentName;

	}

	public NewsType getType() {
		return type;
	}

	// public List<Point> getPlacesPoints() {
	// List<String> list = JSXController.getInstance().invoke("namesInLayer",
	// this.getName()
	// + "/places");
	// if (list == null) {
	// return null;
	// }
	// for (String str : list) {
	// List<String> bounds = JSXController.getInstance().invoke("bounds",
	// this.getName()
	// + "/places" + "/" + str);
	// Rectangle rect = new Rectangle().rectFromJSX(bounds);
	// placesPoints.add(rect.getCenter());
	// }
	// return placesPoints;
	// }

	public abstract void added(NewsStyle parent, int toInd);

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	protected boolean existsLayer() {
		List<String> list = LayersInfoParser.getInstance().namesInLayer(
				this.getFullName());
		String tmpName = "";
		if (this.getName().equals("background_top_left")) {
			_Log.i("");
		}
		if (list.size() == 0) {
			tmpName = dumplicateNew();
			moveLayerInto(this.getParentName(), tmpName);
			return false;
		}else{
			return true;
		}
	}

	public void moveLayerInto(String fullname, String srcFullName) {
		JSXController.getInstance().invoke("moveLayerInto", fullname);
		LayersInfoParser.getInstance().moveLayersInto(fullname, srcFullName);
	}

	protected String dumplicateNew() {
		JSXController.getInstance().invoke("dumplicateExtendPsd",
				Constants.PSD_LIBRARY_PATH, Constants.PAGE_2_3_PATH,
				type.toString(), this.getName());
		LayersInfoParser.getInstance().dumplicateFromLib(
				this.getType().toString(), this.getName());
		return this.getName();
	}

	public void move(Point toPoint) {
		Rectangle bound2 = getBound();
		move(bound2.getCenter(), toPoint);
	}

	public void move(Point thisPoint, Point toPoint) {
		Rectangle bound2 = getBound();
		float x = toPoint.x - (thisPoint.x - bound2.getX());
		float y = toPoint.y - (thisPoint.y - bound2.getY());
		Rectangle rect = new Rectangle(x, y, bound2.getWidth(),
				bound2.getHeight());
		setBound(rect);
	}

	// public void move(float absX, float absY) {
	// Rectangle bound2 = getBound();
	// Rectangle rect = new Rectangle(absX, absY, 0,0);
	// setBound(rect);
	// JSXController.getInstance().invoke("moveTo",
	// this.getFullName(),""+rect.getX(),""+rect.getY());
	// }

	public void resize(float absW, float absH) {
		Rectangle bound2 = getBound();
		Rectangle rect = new Rectangle(bound2.getX(), bound2.getY(), absW, absH);
		setBound(rect);
	}

	public void setBound(Rectangle bound) {
		_Log.i("setBound:" + bound);
		JSXController.getInstance().invoke("setBounds", this.getFullName(),
				"" + bound.getX(), "" + bound.getY(), "" + bound.getRight(),
				"" + bound.getButtom());
		LayersInfoParser.getInstance().setBound(this.getFullName(), this.bound,
				bound);
		this.bound = bound;
	}

	public Rectangle getBound() {
		bound = LayersInfoParser.getInstance().bound(this.getFullName());
		return bound;
	}

	public String getFullName() {
		String par = getParentName();
		// if(par == ""){
		// return getName();
		// }
		return par + "/" + getName();
	}

	public String getName() {
		if(customName != null){
			return customName;
		}
		String idStr = id + "";
		if (id == -1) {
			idStr = "";
		}
		return this.getType().toString() + idStr;
	}

	public NewsBackground getBackground() {
		this.background = new NewsBackground(getFullName());
		return background;
	}

	public void setBackground(Rectangle rect) {
		getBackground().scaleToFit(rect);
	}
}
