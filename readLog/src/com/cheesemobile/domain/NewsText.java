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
	private String title;
	private String contentTypes;//a formated string to store text styles
//	public NewsText(int id, NewsType type, String text, String contentTypes) {
//		super(id, type);
//		init(text, "",contentTypes);
//	}

	public NewsText(int id, NewsType type, String text, String title,String contentTypes) {
		super(id, type);
		init(text, title,contentTypes);
	}

	private void init(String text, String title,String contentTypes) {
		this.text = text;
		this.title = title;
		this.contentTypes = contentTypes;
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
//		params.addAll(assoicatedObjNames);
		JSXController.getInstance().invoke("makePathSelectArea",
				(String[]) params.toArray(new String[params.size()]));
		JSXController.getInstance().invoke("setContentTypes", getFullName(), contentTypes);
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
		Rectangle rectangle = parent.getChildScaledRects().get(toInd);
		List<String> foreigns = parent.getForeigns();

		assoicatedObjNames.addAll(foreigns);
		applyPath(rectangle);
		parent.addStaticText(this.title);
	}

	@Override
	public void scaleToFit(Rectangle rect) {
	}
	
	@Override
	protected boolean canDraw(){
		if(text  == null || text.length() == 0){
			return false;
		}
		return super.canDraw();
	}
	


}
