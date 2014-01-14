package com.cheesemobile.service.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cheesemobile.domain.NewsArticle;
import com.cheesemobile.domain.NewsBean;
import com.cheesemobile.domain.Point;
import com.cheesemobile.domain.Rectangle;
import com.cheesemobile.service.Constants;
import com.cheesemobile.service.JSXController;
import com.cheesemobile.util.FileUtil;
import com.cheesemobile.util.StringUtil;
import com.cheesemobile.util._Log;

public class NewsController {
	public static float _ruler = Constants.NEWS_RULER_1;

	public String _str = null;
	private static int FIRST_CHARACTER = 0;

	// public abstract interface DataCallback<T> {
	// public abstract void processData(T paramObject, boolean paramBoolean);
	// }

	public NewsController() {
		try {
			JSXController.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(0 == 0){
			return;
		}
		List<NewsBean> articlesFromString = articlesFromString();
		// _Log.i(articlesFromString + "");
		int sum = 0;
		List<String> names = new ArrayList<String>();
		for (NewsBean newBean : articlesFromString) {
			for (NewsArticle na : newBean.getArticles()) {
				sum++;
				StringBuilder builder = new StringBuilder();
				String author = na.getAuthor();
				for (int i = 0; i < author.length(); i++) {
					char c1 = author.charAt(i);
					for (int j = i; j < author.length(); j++) {
						int ind = author.substring(j).indexOf(c1);
						if (ind != -1 && builder.indexOf(c1 + "") == -1) {
							builder.append(c1);
						}
					}
				}
				author = builder.toString();
				author = author.replace("\r", " ");
				author = author.replace("\n", " ");
				author = author.replace("(", "");
				author = author.replace(")", "");
				author = author.replace("¡ª", "");
				author = author.replace("-", "");
				author = author.replace(":", "");
				author = author.replace("_", "");
				author = author.replace("£¬", "");
				author = author.replace("  ", " ");
				na.setAuthor(author);
				if (author.split(" ").length > 1) {
					names.add(author.split(" ")[1]);
				}
			}
		}
		_Log.i(names + "");
		// for (int i = 0; i < names.size(); i++) {
		// String currentName = names.get(i);
		// int number = 0;
		// // for (int j = i+1; j < names.size(); j++) {
		// // String name = names.get(j);
		// // if (name != " " && currentName.indexOf(name) != -1 &&
		// // currentName.indexOf("EMPTY") == -1) {
		// // names.set(j, "EMPTY");
		// // number++;
		// // }
		// // }
		// for (String name : names) {
		// names.lastIndexOf(currentName);
		// }
		// names.set(i, currentName + number);
		// }
		List<List<Integer>> result = repeatList(names);
		
//		while (names.remove("EMPTY0")) {};
		_Log.i(result + "");
	}

	public List<List<Integer>> repeatList(List<String> list) {
		List<int[]> repeatList = new ArrayList<int[]>();
		while (list.size() > 0) {
			List<Integer> result = new ArrayList<Integer>();
			int[] selected = new int[list.size()];
			String current = list.get(0);
			int i = 0;
			for (String str : list) {
				if (current.indexOf(str) != -1) {
					selected[i] = 1;
				}else{
					selected[i] = 0;
				}
				i++;
			}
			repeatList.add();
		}
		return repeatList;
	}

	public NewsController(String path) {
		super();
		// ShotNewsUtil.shot();
		// _str = FileUtil.readToString(path);
		// articlesFromString();
	}

	private List<Integer> articleIndexesByVersions(List<String> lines) {
		int sum = 0;
		int lineIndexSum = 0;
		List<Integer> articleIndexes = new ArrayList<Integer>();
		for (String line : lines) {
			if (org.jsoup.helper.StringUtil.isNumeric(line.substring(0, 1))
					&& line.length() < 6) {
				articleIndexes.add(lineIndexSum);
			}
			lineIndexSum++;
		}
		return articleIndexes;
	}

	public void continuedFromString() {
		_str = FileUtil.readToString(Constants.TRAVEL_LAWS_LIBRARY_PATH);
		List<String> lines = StringUtil.arrayToList(_str.split("\n"));
		List<Integer> indexesOfEachVersions = articleIndexesByVersions(lines);
		List<NewsBean> releases = new ArrayList<NewsBean>();
		for (int i = 0; i < indexesOfEachVersions.size() - 1; i++) {
			NewsBean newRelease = ensNews(lines, indexesOfEachVersions.get(i),
					indexesOfEachVersions.get(i + 1), i);
			releases.add(newRelease);
		}
		NewsBean releaseUsing = releases.get(0);

		reboundArticle(releaseUsing);
		OutputController.vBScriptPrint(releaseUsing);
	}

	public List<NewsBean> articlesFromString() {
		if (_str == null) {
			_str = FileUtil.readToString(Constants.NEWS_LIBRARY_PATH_MAC);
		}
		List<String> lines = StringUtil.arrayToList(_str.split("\n"));
		// fix format
		int sum = 0;
		for (String line : lines) {
			if (line.indexOf(">") != -1) {
				sum++;
				if (line.length() > 30) {
					line.replaceFirst(" ", "\n");
				}
			}
		}
		// fix format
		List<Integer> indexesOfEachVersions = articleIndexesByVersions(lines);
		List<NewsBean> releases = new ArrayList<NewsBean>();
		indexesOfEachVersions.add(lines.size() - 1);
		for (int i = 0; i < indexesOfEachVersions.size() - 1; i++) {
			NewsBean newRelease = ensNews(lines, indexesOfEachVersions.get(i),
					indexesOfEachVersions.get(i + 1), i);
			releases.add(newRelease);
		}
		return releases;
		// NewsBean releaseUsing = releases.get(releases.size() - 2);
		// reboundArticle(releaseUsing);
		// OutputController.vBScriptPrint(releaseUsing);
	}

	private NewsBean ensNews(List<String> lines, Integer fromLine,
			Integer toLine, Integer ind) {
		List<NewsArticle> articles = new ArrayList<NewsArticle>();

		for (int i = fromLine; i < toLine; i++) {
			String line = lines.get(i);
			if (line.indexOf(">") == FIRST_CHARACTER) {
				int j = i + 1;
				while (lines.get(j).indexOf(">") != FIRST_CHARACTER
						&& j < toLine) {
					j++;
				}
				articles.add(articleFromVersion(lines.subList(i, j)));
			}
		}
		return new NewsBean("_" + ind, null, articles, ind + "");
	}

	private NewsArticle articleFromVersion(List<String> lines) {
		String content = "";
		List<String> urls = null;
		String title = "";
		String department = "";
		String author = "";

		for (String line : lines) {
			String url = getImgs(line);
			if (url != null) {
				if (urls == null) {
					urls = new ArrayList<String>();
				}
				urls.add(url);
			}
			title = title + getTitles(line);
			department = department + getDepartment(line);
			content = content + line;
		}

		if (Constants._SET_CONTENT_NOTE_TO_TITLE) {

			String note = "";
			if (content.lastIndexOf("(") != -1) {
				note = content.substring(content.lastIndexOf("("),
						content.lastIndexOf(")") + 1);
			}
			author = department + note;
			content = content.replace(note, "");
		}
		int articlesId = 0;
		return new NewsArticle(articlesId++, null, content, author, department,
				title, urls);
	}

	private void reboundArticle(NewsBean news) {
		for (NewsArticle article : news.getArticles()) {
			List<Point> points = new ArrayList<Point>();
			Rectangle rect = new Rectangle();
			int x = 0;
			int y = 0;
			float width = Constants.CONTENT_WIDTH;
			float height = Constants.ARTICLE_HEIGHT_DEFAULT;
			if (article.getPicsUrl() != null) {
				List<Rectangle> subObjBounds = new ArrayList<Rectangle>();
				float imageWidth = (float) (width * .39);
				float imageHeight = 100;
				float imageLasty = 0;
				for (int i = 0; i < article.getPicsUrl().size(); i++) {
					imageLasty = (y + i * (imageHeight + _ruler));
					subObjBounds.add(new Rectangle(width - imageWidth,
							imageLasty, imageWidth, imageHeight));
				}
				article.setSubObjBounds(subObjBounds);

				Point clipPoint = new Point((width - _ruler - imageWidth),
						(imageLasty + imageHeight + _ruler));
				rect = new Rectangle(x, y, width, height);
				points.add(new Point(x, y));
				points.add(new Point(x + clipPoint.x, y));
				points.add(new Point(x + clipPoint.x, y + clipPoint.y));
				points.add(new Point(x + width, y + clipPoint.y));
				points.add(new Point(x + width, y + height));
				points.add(new Point(x, y + height));
				article.setPoints(points);
				article.setBound(rect);

			} else {
				rect = new Rectangle(x, y, Constants.CONTENT_WIDTH,
						Constants.ARTICLE_HEIGHT_DEFAULT);
				points.add(new Point(x, y));
				points.add(new Point(x + width, y));
				points.add(new Point(x + width, y + height));
				points.add(new Point(x, y + height));
				article.setPoints(points);
				article.setBound(rect);
			}
		}
	}

	private String getImgs(String str) {
		String tag = "<img>";
		String result = rowByTag(str, tag);
		if (result == null) {
			return null;
		}
		return Constants.IMG_DESTINATION_PATH + result;
	}

	private String getTitles(String str) {
		String tag = "<title>";
		String result = rowByTag(str, tag);
		if (result == null) {
			return "";
		}
		return result;
	}

	private String getDepartment(String str) {
		String tag = ">";
		String result = rowByTag(str, tag);
		if (result == null) {
			return "";
		}
		return result.replaceFirst(">", "");
	}

	private String rowByTag(String str, String tag) {
		if (str.indexOf(tag) != -1) {
			str = str.replaceFirst(tag, "");
			return str;
		}
		return null;
	}
}
