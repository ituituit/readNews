package com.cheesemobile.domain;

public class BaseNewsArticle {
	protected float _ruler = 11;
	protected float _contentWidth = 459.36f;
	protected int _articleHeightDefault = 300;

	public BaseNewsArticle(float _ruler, float _contentWidth,
			int _articleHeightDefault) {
		super();
		this._ruler = _ruler;
		this._contentWidth = _contentWidth;
		this._articleHeightDefault = _articleHeightDefault;
	}

	public BaseNewsArticle() {
		super();
	}
}