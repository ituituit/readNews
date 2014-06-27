package com.cheesemobile.domain;

import java.util.ArrayList;
import java.util.List;

import com.cheesemobile.parser.LayersInfoParser;
import com.cheesemobile.service.JSXController;
import com.cheesemobile.service.news.NewsController.NewsType;

public class NewsLine extends NewsStyle implements MovementsInterface {
	List<List<Rectangle>> linesGroup = null;
	private List<NewsShape> imagesRow = new ArrayList<>();
	private List<NewsShape> imagesCow = new ArrayList<>();

	public NewsLine(String parentName, List<Rectangle> placePointsLines) {
		super(-1, NewsType.SPLIT_LINES, parentName);
		linesGroup = spread(placePointsLines);
		for (int i = 0; i < linesGroup.size(); i++) {
			int ind = 0;
			NewsType rowType = NewsType.ROW;
			if(i == 1){
				rowType = NewsType.COW;
			}
			for (Rectangle rectangle : linesGroup.get(i)) {
				NewsShape newsImage = new NewsShape(ind++,rowType) {
					protected String dumplicateNew() {
						JSXController.getInstance().invoke(
								"dumplicate",
								this.getParentName() + "/"
										+ this.getType().toString(),
								this.getName());
						LayersInfoParser.getInstance().dumplicate(
								this.getParentName() + "/"
										+ this.getType().toString(),
								this.getName(), null);
						return this.getName();
					}

					@Override
					public void added(NewsStyle parent, int toInd) {
						JSXController.getInstance().invoke("objIndexJSX",this.getFullName());
						Rectangle bound2 = this.getBound();
						int i = 0;
						int j = 0;
						if(this.getType() == NewsType.ROW.toString()){
							i = 0;
							j = imagesRow.indexOf(this);
						}else{
							i = 1;
							j = imagesCow.indexOf(this);
						}
						Rectangle rectangle2 = linesGroup.get(i).get(j);
						bound2.setX(rectangle2.getX());
						bound2.setY(rectangle2.getY());
						if(rectangle2.getWidth() > rectangle2.getHeight()){
							bound2.setWidth(rectangle2.getWidth());
						}else{
							bound2.setHeight(rectangle2.getHeight());
						}
						this.setBound(bound2);
					}
				};
				if(i == 0){
					imagesRow.add(newsImage);
				}else{
					imagesCow.add(newsImage);
				}
			}

		}
		List<NewsShape> imagesRow2 = imagesRow;
		imagesRow2.addAll(imagesCow);
		this.addAll2(imagesRow2);
	}

	private static List<List<Rectangle>> spread(List<Rectangle> lines) {
		List<Rectangle> row = new ArrayList<Rectangle>();
		List<Rectangle> cow = new ArrayList<Rectangle>();
		for (Rectangle rectangle : lines) {
			if (rectangle.getWidth() > rectangle.getHeight()) {
				row.add(rectangle);
			} else {
				cow.add(rectangle);
			}
		}
		List<List<Rectangle>> val = new ArrayList<List<Rectangle>>();
		val.add(row);
		val.add(cow);
		return val;
	}

	@Override
	public void scaleToFit(Rectangle rect) {
	}
}