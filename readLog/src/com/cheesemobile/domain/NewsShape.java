package com.cheesemobile.domain;

import com.cheesemobile.service.JSXController;
import com.cheesemobile.service.news.NewsController.NewsType;

public class NewsShape extends NewsImage {

	public NewsShape(int id, NewsType type) {
		super(id, type);
	}

	public NewsShape(String fullName) {
		super(fullName);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void setBound(Rectangle bound) {
		JSXController.getInstance().invoke("setBoundsEnlargeType", "2");
		super.setBound(bound);
		JSXController.getInstance().invoke("setBoundsEnlargeType", "1");
	}
}
