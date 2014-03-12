package com.cheesemobile.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cheesemobile.service.JSXController;
import com.cheesemobile.service.news.NewsController.NewsType;

public class NewsText extends BoundNewsObject implements MovementsInterface,
		Serializable {
	private List<String> assoicatedObjNames = new ArrayList<>();
	private String text;

	public NewsText(int id, NewsType type, String text) {
		super(id, type);
		this.text = text;
	}

	public NewsText(String fullName) {
		super(fullName,NewsType.TEXT);
	}

	private void applyPath(Rectangle bound) {
		String[] bounds = { "" + bound.getX(), "" + bound.getY(),
				"" + bound.getRight(), "" + bound.getButtom() };
		List<String> params = new ArrayList<>();
		params.addAll(Arrays.asList(bounds));
		params.add(this.getFullName());
		params.addAll(assoicatedObjNames);
		JSXController.getInstance().invoke("makePathSelectArea",
				(String[]) params.toArray(new String[params.size()]));
	}

	@Override
	protected boolean existsLayer() {
		boolean val =  super.existsLayer();
		refreshText(text);
		return val;
	}
	
	public void refreshText(String text) {
		JSXController.getInstance().invoke("setText", getFullName(), text);
	}

	@Override
	public void added(NewsStyle parent, int toInd) {
		Rectangle rectangle = parent.getPlacesPointsRects().get(toInd);
		List<String> foreigns = parent.getForeigns();
//		for (String fullName : foreigns) {
//			NewsImage newsImage = new NewsImage(fullName);
//			Rectangle one = newsImage.getBound().attachMove(rectangle);
//		//	Rectangle attachMove = one.attachMove(rectangle);
//			newsImage.setBound(one);
//		}
		assoicatedObjNames.addAll(foreigns);
		applyPath(rectangle);
	}

	@Override
	public void scaleToFit(Rectangle rect) {
	}

}
