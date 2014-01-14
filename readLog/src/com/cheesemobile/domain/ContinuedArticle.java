package com.cheesemobile.domain;

public class ContinuedArticle extends BaseNewsArticle{
	private String title;
	private String content;
	private Rectangle backgroundRect;
	public ContinuedArticle() {
		
	}
	
	
	public Rectangle getBackgroundRect() {
		return backgroundRect;
	}


	public void setBackgroundRect(Rectangle backgroundRect) {
		this.backgroundRect = backgroundRect;
	}


	public void setRects(float _ruler,float _contentWidth,int _articleHeightDefault){
		this._ruler = _ruler;
		this._contentWidth = _contentWidth;
		this._articleHeightDefault = _articleHeightDefault;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
