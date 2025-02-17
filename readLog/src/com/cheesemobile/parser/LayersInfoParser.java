package com.cheesemobile.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.parser.XmlTreeBuilder;
import org.jsoup.select.Elements;

import com.cheesemobile.domain.LayerInfoBean;
import com.cheesemobile.domain.Point;
import com.cheesemobile.domain.Rectangle;
import com.cheesemobile.service.Constants;
import com.cheesemobile.service.JSXController;
import com.cheesemobile.util.FileUtil;
import com.cheesemobile.util._Log;

class LayerInfoList extends ArrayList<LayerInfoBean> {
	private static final long serialVersionUID = -1582549382175866395L;

	public List<LayerInfoBean> indexByName(String fullName) {
		List<LayerInfoBean> returnVal = new ArrayList<LayerInfoBean>();
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i).compareNameSetInd(fullName)) {
				returnVal.add(this.get(i));
			}
		}
		return returnVal;
	}

}

public class LayersInfoParser {
	private static LayersInfoParser instance;

	private LayersInfoParser() {
		parse(Constants.PREFERENCE_XML_PATH, list);
		parse(Constants.PREFERENCE_LIB_XML_PATH, lib);
	}

	public void reload(){
		parse(Constants.PREFERENCE_XML_PATH, list);
		parse(Constants.PREFERENCE_LIB_XML_PATH, lib);
	}
	
	public static LayersInfoParser getInstance() {
		if (instance == null) {
			instance = new LayersInfoParser();
		}
		return instance;
	}

	private LayerInfoList list = new LayerInfoList();
	private LayerInfoList lib = new LayerInfoList();

	public String objExists(String layerName){
		List<LayerInfoBean> indexByName = list.indexByName(layerName);
		if(indexByName.size() == 1){
			return indexByName.get(0).getFullName();
		}
		return indexByName.get(0).getParent();
	}
	
	private static void parse(String path, LayerInfoList list) {
		String str = FileUtil.readToString(path, true);
		Document document = Jsoup.parse(str, "", new Parser(
				new XmlTreeBuilder()));
		Elements childs = document.children().get(0).children();
		for (org.jsoup.nodes.Element element : childs) {
			LayerInfoBean bean = new LayerInfoBean();
			bean.setFullName(element.attr("name"));
			_Log.i(bean.getFullName());
			// bean.setName(element.ownText());
			String pos = element.attr("position");
			String[] point = pos.split(",");
			Rectangle bound = new Rectangle();
			bound.setX(Float.parseFloat(point[0]));
			bound.setY(Float.parseFloat(point[1]));
			bound.setWidth(Float.parseFloat(element.attr("layerwidth")));
			bound.setHeight(Float.parseFloat(element.attr("layerheight")));
			bean.setRect(bound);
			bean.setStack(Integer.parseInt(element.attr("stack")));
			list.add(bean);
		}
		_Log.i(list + "");
	}

	public List<String> namesInLayer(String fullName) {
		List<LayerInfoBean> indexByName = list.indexByName(fullName);
		List<String> returnVal = new ArrayList<String>();
		for (LayerInfoBean bean : indexByName) {
			if (bean.getName().equals("")) {// add self's name
				List<String> nl = bean.getNamesList();
				returnVal.add(nl.get(nl.size() - 1));
			} else {
				if (bean.getName().indexOf("/") != -1) {// skip group
					returnVal.add(bean.getName().split("/")[0]);
				} else {
					returnVal.add(bean.getName());
				}
			}
		}
		return returnVal;
	}

	public Rectangle bound(String fullName) {
		List<LayerInfoBean> indexByName = list.indexByName(fullName);
		Rectangle rect = indexByName.get(0).getRect();
		Rectangle largest = new Rectangle(rect.getX(), rect.getY(),
				rect.getWidth(), rect.getHeight());
		for (LayerInfoBean bean : indexByName) {
			largest.mergeRect(bean.getRect());
		}
		return largest;
	}

	public void setBound(String fullName, Rectangle from, Rectangle to) {
		Point place = new Point(from.getX() - to.getX(), from.getY()
				- to.getY());
		float percentX = to.getWidth() / from.getWidth();
		float percentY = to.getHeight() / from.getHeight();
		List<LayerInfoBean> indexByName = list.indexByName(fullName);
		if (indexByName.size() == 1) {
			Rectangle rect = indexByName.get(0).getRect();
			Rectangle newRect = new Rectangle();
			newRect.setX(rect.getX() - place.x);
			newRect.setY(rect.getY() - place.y);
			newRect.setWidth(rect.getWidth() * percentX);
			newRect.setHeight(rect.getHeight() * percentX);
			indexByName.get(0).setRect(newRect);
		} else {
			for (LayerInfoBean bean : indexByName) {
				Rectangle rect = bean.getRect();
				Rectangle newRect = new Rectangle();
				newRect.setX(rect.getX() - place.x);
				newRect.setY(rect.getY() - place.y);
				newRect.setWidth(rect.getWidth());
				newRect.setHeight(rect.getHeight());
				bean.setRect(newRect);
			}
		}
	}

	public void moveLayersInto(String destFolder, String src) {
		List<LayerInfoBean> indexByName = list.indexByName(src);
		for (LayerInfoBean layerInfoBean : indexByName) {
			layerInfoBean.setParent(destFolder + "/"
					+ layerInfoBean.getParent());
			_Log.i("moveInto:" + destFolder + "/" + layerInfoBean.getParent()
					+ " " + layerInfoBean.getFullName());
		}
	}

	public void importImage(String path, String tempName,Rectangle rect) {
		// try {
		// Iterator readers = ImageIO.getImageReadersByFormatName("psd");
		// ImageReader reader = (ImageReader) readers.next();
		// ImageInputStream iis = ImageIO.createImageInputStream(path);
		// reader.setInput(iis, true);
		// width = reader.getWidth(0);
		// height = reader.getHeight(0);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		Rectangle newRect = new Rectangle(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
		LayerInfoBean bean = new LayerInfoBean();
		bean.setFullName(tempName);
		bean.setRect(newRect);
		list.add(bean);
	}

	public void dumplicate(String inLibName, String newName, LayerInfoList lib) {
		if (lib == null) {
			lib = list;
		}
		List<LayerInfoBean> indexByName = lib.indexByName(inLibName);
		List<LayerInfoBean> newIndexByName = new ArrayList<>();

		for (LayerInfoBean layerInfoBean : indexByName) {
			LayerInfoBean newBean = layerInfoBean.dumplicate();
			newBean.setParent(newName);
			newIndexByName.add(newBean);
		}
		if(list.size() == 0){
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		list.addAll(newIndexByName);
	}

	public void applyMask(String fullName, Rectangle maskRect) {
		List<LayerInfoBean> indexByName = list.indexByName(fullName);
		if (indexByName.size() != 1) {
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		indexByName.get(0).setRect(maskRect);
	}

	public void dumplicateFromLib(String inLibName, String newName) {
		dumplicate(inLibName, newName, lib);
	}

	public void deleteLayer(String fullName) {
		JSXController.getInstance().invoke("deleteLayerJSX", fullName);
		List<LayerInfoBean> indexByName = list.indexByName(fullName);
		if (indexByName.size() != 1) {
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		list.remove(indexByName.get(0));
	}
	
	public void drawLine(Point[] Points,String lineNames){
//		String paramPoints = Arrays.asList(Points).toString() ;
		float x1 = Points[0].x;
		float y1 = Points[0].y;
		float x2 = Points[1].x;
		float y2 = Points[1].y;
//		String paramLineNames = Arrays.asList(lineNames).toString();
		JSXController.getInstance().invoke("createPath", x1 + "",y1 + "",x2 + "",y2 + "",lineNames);
	}
	
	public void mergeLayer(String groupName){
		JSXController.getInstance().invoke("mergeLayerJSX", groupName);
	}
	
}
