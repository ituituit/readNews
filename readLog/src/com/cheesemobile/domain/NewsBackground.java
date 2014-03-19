package com.cheesemobile.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cheesemobile.parser.LayersInfoParser;
import com.cheesemobile.service.Constants;
import com.cheesemobile.service.JSXController;
import com.cheesemobile.service.news.NewsController.NewsType;
import com.cheesemobile.util._Log;

public class NewsBackground extends NewsStyle implements
		MovementsInterface {
	private Point scalePoint = null;
	private static List<Rectangle> _bounds;
	private final NewsType[] backgroundInds = {NewsType.BACKGROUND_TOP_LEFT,NewsType.BACKGROUND_TOP_RIGHT,
			NewsType.BACKGROUND_BTN_RIGHT,NewsType.BACKGROUND_BTN_LEFT,
			NewsType.BACKGROUND_TOP,NewsType.BACKGROUND_RIGHT,
			NewsType.BACKGROUND_BUTTOM,NewsType.BACKGROUND_LEFT,NewsType.BACKGROUND_CENTER};
	
	public NewsBackground(String parentName) {
		super(-1, NewsType.BACKGROUND,parentName);
		scalePoint = this.getBound().getCenter();
//		List<String> list = JSXController.getInstance().invoke("namesInLayer", this.getFullName());
		List<String> list = LayersInfoParser.getInstance().namesInLayer(this.getFullName());
		
		if (list.size() == 1 && list.get(0).equals(this.getName())) {
			return;
		}

		_bounds = new ArrayList<Rectangle>();
		Rectangle bound2 = getBound();
		_bounds.add(new Rectangle().buildBound(bound2.getX(), bound2.getY(),
				scalePoint.x, scalePoint.y));
		_bounds.add(new Rectangle().buildBound(scalePoint.x, bound2.getY(),
				bound2.getRight(), scalePoint.y));
		_bounds.add(new Rectangle().buildBound(scalePoint.x, scalePoint.y,
				bound2.getRight(), bound2.getButtom()));
		_bounds.add(new Rectangle().buildBound(bound2.getX(), scalePoint.y,
				scalePoint.x, bound2.getButtom()));

		int zone = 1;
		List<Rectangle> _boundsCenter = new ArrayList<Rectangle>();
		_boundsCenter.add(new Rectangle().buildBound(scalePoint.x - zone,
				bound2.getY(), scalePoint.x + zone, scalePoint.y - zone));
		_boundsCenter.add(new Rectangle().buildBound(scalePoint.x + zone,
				scalePoint.y - zone, bound2.getRight(), scalePoint.y + zone));
		_boundsCenter
				.add(new Rectangle().buildBound(scalePoint.x - zone,
						scalePoint.y + zone, scalePoint.x + zone,
						bound2.getButtom()));
		_boundsCenter.add(new Rectangle().buildBound(bound2.getX(),
				scalePoint.y - zone, bound2.getRight(), scalePoint.y + zone));
		_boundsCenter.add(new Rectangle().buildBound(scalePoint.x - zone,
				scalePoint.y - zone, scalePoint.x + zone, scalePoint.y
						+ zone));
		_bounds.addAll(_boundsCenter);
		final List<BoundNewsObject> images = new ArrayList<>();
		for (int i = 0; i < backgroundInds.length; i++) {
			NewsImage newsImage = new NewsImage(-1,backgroundInds[i]) {
				protected String dumplicateNew() {
					JSXController.getInstance().invoke("dumplicate",this.getParentName() + "/0", this.getName());
					LayersInfoParser.getInstance().dumplicate(this.getParentName() + "/0",this.getName(),null);
					this.applyMask(_bounds.get(images.indexOf(this)));
					this.mergeMask();
					return this.getName();
				}
				@Override
				public void added(NewsStyle parent, int toInd) {
					JSXController.getInstance().invoke("objIndexJSX",this.getFullName());
				}
			};
			images.add(newsImage);
		}
		this.addAll(images);
	}

	public void scaleToFit(Rectangle rect) {
		if(this.size() == 0){
			this.setBound(rect);
			return;
		}
		Point[] points = rect.getPoints();

		BoundNewsObject topLeft = get(0, NewsType.BACKGROUND_TOP_LEFT);
		topLeft.move(topLeft.getBound().getPoints()[0], points[0]);
		BoundNewsObject topRight = get(0, NewsType.BACKGROUND_TOP_RIGHT);
		topRight.move(topRight.getBound().getPoints()[1], points[1]);
		BoundNewsObject btnRight = get(0, NewsType.BACKGROUND_BTN_RIGHT);
		btnRight.move(btnRight.getBound().getPoints()[2], points[2]);
		BoundNewsObject btnLeft = get(0, NewsType.BACKGROUND_BTN_LEFT);
		btnLeft.move(btnLeft.getBound().getPoints()[3], points[3]);
		
		List<Rectangle> bounds = new ArrayList<Rectangle>();
		bounds.add(new Rectangle().buildBound(topLeft.getBound().getPoints()[1],topRight.getBound().getPoints()[3]));
		bounds.add(new Rectangle().buildBound(topRight.getBound().getPoints()[3],btnRight.getBound().getPoints()[1]));
		bounds.add(new Rectangle().buildBound(btnLeft.getBound().getPoints()[1],btnRight.getBound().getPoints()[3]));
		bounds.add(new Rectangle().buildBound(topLeft.getBound().getPoints()[3],btnLeft.getBound().getPoints()[1]));
		bounds.add(new Rectangle().buildBound(topLeft.getBound().getPoints()[2],btnRight.getBound().getPoints()[0]));
		
		get(0, NewsType.BACKGROUND_TOP).setBound(bounds.get(0));
		BoundNewsObject right = get(0, NewsType.BACKGROUND_RIGHT);
		right.setBound(bounds.get(1));
		BoundNewsObject buttom = get(0, NewsType.BACKGROUND_BUTTOM);
		buttom.setBound(bounds.get(2));
		BoundNewsObject left = get(0, NewsType.BACKGROUND_LEFT);
		left.setBound(bounds.get(3));
		BoundNewsObject center = get(0, NewsType.BACKGROUND_CENTER);
		center.setBound(bounds.get(4));
//		Rectangle rectangle = _bounds.get(Arrays.asList(_newLayerInd).indexOf(top.getType()));
	}

	@Override
	public void added(NewsStyle boundNewsObject, int addedTo) {
		
	}

}
