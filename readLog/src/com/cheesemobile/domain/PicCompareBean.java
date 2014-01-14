package com.cheesemobile.domain;

import java.util.ArrayList;
import java.util.List;

public class PicCompareBean {
	private List<PixelDataBean> _pics = new ArrayList<PixelDataBean>();
	private boolean _playing = false;
	private boolean _blackScreen = false;
	private int id;

	public String getStatus() {
		if (_blackScreen) {
			return "black screen";
		}
		if (_playing) {
			return "playing";
		}
		return "freeze";
	}

	public boolean is_playing() {
		return _playing;
	}

	public void set_playing(boolean _playing) {
		this._playing = _playing;
	}

	public boolean is_blackScreen() {
		return _blackScreen;
	}

	public void set_blackScreen(boolean _blackScreen) {
		this._blackScreen = _blackScreen;
	}

	public List<PixelDataBean> get_pics() {
		return _pics;
	}

	public void set_pics(List<PixelDataBean> _pics) {
		this._pics = _pics;
	}

	public PicCompareBean(List<PixelDataBean> _pics, int id) {
		this._pics = _pics;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "PicCompareBean [_pics=" + _pics.size() + ", _playing="
				+ _playing + ", _blackScreen=" + _blackScreen + ", id=" + id
				+ "]";
	}

}
