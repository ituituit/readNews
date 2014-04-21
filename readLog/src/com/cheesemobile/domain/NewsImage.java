package com.cheesemobile.domain;

import java.io.Serializable;

import com.cheesemobile.parser.LayersInfoParser;
import com.cheesemobile.service.JSXController;
import com.cheesemobile.service.news.NewsController.NewsType;
import com.cheesemobile.util._Log;

public class NewsImage extends BoundNewsObject implements MovementsInterface,
		Serializable {
	private static final long serialVersionUID = -6800973055100673960L;
	private String _imagePath;
	private Rectangle rectMask;

	public NewsImage(int id, String imagePath, String parentName) {
		super(id, NewsType.IMAGE, parentName);
		_imagePath = imagePath;
		_Log.i(this.getFullName());

	}

	public NewsImage(String fullName) {
		super(fullName, NewsType.IMAGE);
	}

	public NewsImage(int id, NewsType type) {
		super(id, type);
	}

	@Override
	protected String dumplicateNew() {
		JSXController.getInstance().invoke("addImage", _imagePath,
				this.getName());
		LayersInfoParser.getInstance().importImage(_imagePath, this.getName());
		return this.getName();
	}

	public void applyMask(Rectangle bound) {
		JSXController.getInstance().invoke("selectBound", "" + bound.getX(),
				"" + bound.getY(), "" + bound.getRight(),
				"" + bound.getButtom());
		JSXController.getInstance().invoke("addMask", "0");
		this.rectMask = bound;
	}

	public void mergeMask() {
		JSXController.getInstance().invoke("applyMask", "0");
		LayersInfoParser.getInstance().applyMask(this.getName(), rectMask);
		this.setBound(rectMask);
	}

	@Override
	public void scaleToFit(Rectangle rect) {
		Rectangle bound2 = new Rectangle();
		bound2.setX(rect.getX());
		bound2.setY(rect.getY());
		bound2.setWidth(rect.getWidth());
		bound2.setHeight(0);
		this.setBound(bound2);
		// mergeMask();
		applyMask(rect);
	}

	@Override
	public void added(NewsStyle parent, int toInd) {
		if (parent.getChildScaledRects() != null) {
			Rectangle rectangle = parent.getChildScaledRects().get(toInd);
			if(rectangle.getWidth() > 0){
				scaleToFit(rectangle);
			}
		}
	}

	public void changeImage(String path) {
		this._imagePath = path;
		Rectangle rect = this.getBound();
		// delete
		LayersInfoParser.getInstance().deleteLayer(this.getFullName());
		// dumplicate
		moveLayerInto(this.getParentName(), dumplicateNew());
		scaleToFit(rect);
	}

	public String getPath() {
		return _imagePath;
	}
	
	public void attatch(Rectangle _container) {
		Rectangle one = this.getBound().attachMove(_container);
		this.setBound(one);
	}
	
	@Override
	protected void layerExists(){
		this.changeImage(this.getPath());
	}
}
