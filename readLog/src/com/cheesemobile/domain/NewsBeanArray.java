package com.cheesemobile.domain;

import java.util.ArrayList;

public class NewsBeanArray extends ArrayList<NewsBean> {
	private static final long serialVersionUID = 2603142798914064624L;
	public NewsBean getRelease(int release){
		for (NewsBean bean : this) {
			if (release == bean.getReleaseVer()){
				return bean;
			}
		}
		return null;
	}
	public int getReleaseSize(int release){
		if(this.getRelease(release) == null){
			return 0;
		}
		else{
			return this.getRelease(release).getArticlesSize();
		}
	}
	
}
