package com.cheesemobile.service;

import java.awt.Image;
import java.awt.Label;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.cheesemobile.domain.PicCompareBean;
import com.cheesemobile.domain.PixelDataBean;
import com.cheesemobile.util.BitmapCompareUtil;
import com.cheesemobile.util.FileUtil;
import com.cheesemobile.util.PictureNotFoundError;
import com.cheesemobile.util._Log;

public class BitmapCompareController {
	private static BitmapCompareController instance = null;

//	private List<int[][]> picDataList = new ArrayList<int[][]>();
	private List<PixelDataBean> noDatas = new ArrayList<PixelDataBean>();
	private List<PicCompareBean> pcbs = new ArrayList<PicCompareBean>();
	private BitmapCompareController() {
	}

	public static BitmapCompareController getInstance() {
		if (instance == null) {
			instance = new BitmapCompareController();
		}
		return instance;
	}

	private void getStreams(String[] strs) {
			int perfix = 0;
			int perfixLast = 0;
			for (int i = 0; i < strs.length; i++) {
				String fileName = FileUtil.fileName(strs[i]);
				int ind = fileName.indexOf("_");
				if(ind != -1){
					perfix = Integer.parseInt(fileName.substring(ind + 1,fileName.length()));
				}else{
					perfix = Integer.parseInt(fileName);
				}
				if(perfix < perfixLast){
					perfix = i;
					break;
				}
				perfixLast = perfix;
			}
			if (perfix == 0) perfix = 1;
			int size = strs.length / perfix ;
			for(int j = 0; j < perfix; j++){
				List<PixelDataBean> srcDatas = new ArrayList<PixelDataBean>();
				for (int i = 0; i < size; i++) {
					int ind = (i * perfix + j);
					String path = strs[ind];
					_Log.i(ind + " " + path + " " + size);
					srcDatas.add(BitmapCompareUtil.getPixelDataBean(path));
				}
				_Log.i("break");
				PicCompareBean pcb = new PicCompareBean(srcDatas,j);
				pcbs.add(pcb);
			}
			
			for (int i = 0; i < Constants.noDataFileNames.length; i++) {
				PixelDataBean noWordData = BitmapCompareUtil.getPixelDataBean(Constants.defControllsPath
						+ Constants.noDataFileNames[i]);
				noDatas.add(noWordData);
			}
		 
	}

	public void compareImages(String[] strs) throws FileNotFoundException {
		getStreams(strs);
		for(PicCompareBean pcb : pcbs){
			for (int i = 0; i < pcb.get_pics().size(); i++) {
				if (i != 0){
					compareImage(pcb,pcb.get_pics().get(i),pcb.get_pics().get(i - 1));
				}else{
					compareImage(pcb,pcb.get_pics().get(i),null);
				}
			}
		}
	}

	private void compareImage(PicCompareBean pcb,PixelDataBean list1, PixelDataBean list2) {
		if (null != list2) {
			if (!list1.compare(list2)) {
				pcb.set_playing(true);
			}
		}

		for (int j = 0; j < noDatas.size(); j++) {
			if (BitmapCompareUtil.BigPicInSmallPic(list1, noDatas.get(j)) != null) {
				pcb.set_blackScreen(true);
				break;
			}
		}

		if (BitmapCompareUtil.picIsBlack(list1)) {
			pcb.set_blackScreen(true);
		}
	}

	public void write(String iniPath) {
		_Log.i("wirting");
		IniReader reader = new IniReader(iniPath);
		StringBuilder sb = new StringBuilder();
		for(PicCompareBean pcb : pcbs){
			sb.append(pcb.getId() + pcb.getStatus() + "_");
			_Log.i(pcb.toString());
		}
		_Log.i(sb.toString());
		reader.setKey("video_status", "parameters", sb.toString());
	}

	private static InputStream getImageStreamFromWeb(String urlAddress) {
		URL url = null;
		HttpURLConnection conn = null;
		try {
			url = new URL(urlAddress);
			conn = (HttpURLConnection) url.openConnection();
			if (conn.getResponseCode() == 200) {
				InputStream in = conn.getInputStream();
				return in;
			} else {
				throw new PictureNotFoundError("can not found #ADD# picture: "
						+ urlAddress);
			}
		} catch (Exception e) {
		}
		return null;
	}

	public static Image toimage(byte[] b) {
		Image image = Toolkit.getDefaultToolkit().createImage(b);
		try {
			MediaTracker mt = new MediaTracker(new Label());
			mt.addImage(image, 0);
			mt.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return image;
	}

	// public static boolean compareImage(String[][] list1, String[][] list2) {
	// if (BigPicInSmallPic(list1, list2)) {
	// return true;
	// }
	// return listCompare(list1, list2);
	// }

}