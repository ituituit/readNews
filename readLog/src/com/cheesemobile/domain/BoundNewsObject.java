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
	private String type;
	private int id;
	private NewsBackground background;
	private String parentName;
	private String customName = null;
	private boolean foreign = false;

	public void toggleForeign() {
		if (!foreign) {
			this.customName = this.getName() + "_foreign";
			foreign = true;
		}
	}

	public boolean foreign() {
		return foreign;
	}

	public BoundNewsObject(int id, NewsType type) {
		super();
		init(id, type.toString(), "");
	}

	public BoundNewsObject(String fullName, NewsType type) {
		List<String> namesList = LayerInfoBean.getNamesList(fullName);
		customName = namesList.get(namesList.size() - 1);
		String parentName = LayerInfoBean.minusLast(fullName, customName);
		init(-1, type.toString(), parentName);
	}

	public BoundNewsObject(int id, String type, String parentName) {
		super();
		init(id, type, parentName);
	}

	public BoundNewsObject(int id, NewsType type, String parentName) {
		super();
		init(id, type.toString(), parentName);
	}

	private void init(int id, String type, String parentName) {
		this.id = id;
		this.type = type;
		this.parentName = parentName;

	}

	public String getType() {
		return type.toString();
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
		if (list.size() == 0) {
			tmpName = dumplicateNew();
			moveLayerInto(this.getParentName(), tmpName);
			return false;
		} else {
			layerExists();
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

	public void hide() {
		JSXController.getInstance().invoke("hide", this.getName());
	}

	public void show() {
		JSXController.getInstance().invoke("show", this.getName());
	}

	private void move(Point toPoint) {
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
		bound.setX((int) bound.getX());
		bound.setY((int) bound.getY());
		bound.setWidth((int) bound.getWidth());
		bound.setHeight((int) bound.getHeight());
		JSXController.getInstance().invoke("setBounds", this.getFullName(),
				"" + bound.getX(), "" + bound.getY(), "" + bound.getRight(),
				"" + bound.getButtom());
		LayersInfoParser.getInstance().setBound(this.getFullName(),
				this.getBound(), bound);
	}

	public Rectangle getBound() {
		return LayersInfoParser.getInstance().bound(this.getFullName());
	}

	public String getFullName() {
		String par = getParentName();
		_Log.i("this name is:" + getName());
		if(par.length() > 0){
			par = par + "/";
		}
		return par + getName();
	}

	public String getName() {
		if (customName != null) {
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

	protected boolean canDraw() {
		// if (foreign) {
		// return false;
		// }
		return true;
	}

	protected void layerExists() {

	}

	public void setBound(List<Rectangle> childScaledRects, int i){};//for addAllNoShot() in newsstyle
}
