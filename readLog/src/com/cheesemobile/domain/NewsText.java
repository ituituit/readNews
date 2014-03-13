package com.cheesemobile.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cheesemobile.service.JSXController;
import com.cheesemobile.service.news.NewsController.NewsType;

public class NewsText extends BoundNewsObject implements MovementsInterface,
		Serializable {
	private List<String> assoicatedObjNames;
	private String text;
	private String title;

	public NewsText(int id, NewsType type, String text) {
		super(id, type);
		this.text = text;
	}

	public NewsText(String fullName) {
		super(fullName, NewsType.TEXT);
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
		boolean val = super.existsLayer();
		refreshText(text);
		return val;
	}

	public void refreshText(String text) {
		JSXController.getInstance().invoke("setText", getFullName(), text);
	}

	@Override
	public void added(NewsStyle parent, int toInd) {
		Rectangle rectangle = parent.getPlacesPointsRects().get(toInd);
		List<String> foreigns = parent.typeInLayer(NewsType.FOREIGN);
		List<String> images = parent.typeInLayer(NewsType.IMAGE);
		assoicatedObjNames = new ArrayList<>();
		assoicatedObjNames.addAll(foreigns);
		assoicatedObjNames.addAll(images);
		applyPath(rectangle);
		parent.addStaticText(title);
		parent.updateStaticTexts();
	}

	@Override
	public void scaleToFit(Rectangle rect) {
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

}
