package com.cheesemobile.service;

public class BaseController {
	protected BaseController() {
	}

	private static BaseController instance = null;

	public static BaseController getInstance() {
		if (instance == null) {
			instance = new BaseController();
		}
		return instance;
	}
}
