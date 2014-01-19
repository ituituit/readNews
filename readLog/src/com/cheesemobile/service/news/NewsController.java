package com.cheesemobile.service.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cheesemobile.domain.NewsArticle;
import com.cheesemobile.domain.NewsBean;
import com.cheesemobile.domain.Point;
import com.cheesemobile.domain.Rectangle;
import com.cheesemobile.domain.VoBean;
import com.cheesemobile.service.Constants;
import com.cheesemobile.service.JSXController;
import com.cheesemobile.util.FileUtil;
import com.cheesemobile.util.StringUtil;
import com.cheesemobile.util._Log;

public class NewsController {
	public static float _ruler = Constants.NEWS_RULER_1;

	public String _str = null;
	private static int FIRST_CHARACTER = 0;

	public enum NewsType {
		VISITORS_TRACK, SCENIC_BLOGS, SCENIC_NEWS, TRAVEL_LAWS;
		public String toString() {
			String str = "";
			switch (this) {
			case VISITORS_TRACK:
				str = Constants.VISITORS_TRACK_PSD_GROUP;
				break;
			case SCENIC_BLOGS:
				str = Constants.SCENIC_BLOGS_PSD_GROUP;
				break;
			case SCENIC_NEWS:
				str = Constants.SCENIC_NEWS_PSD_GROUP;
				break;
			case TRAVEL_LAWS:
				str = Constants.TRAVEL_LAWS_PSD_GROUP;
				break;
			}
			return str;
		}
	};
	
	// public abstract interface DataCallback<T> {
	// public abstract void processData(T paramObject, boolean paramBoolean);
	// }

	public NewsController() {
		// articleStatues();
		// String[] str = { "text_1" };
		// VoBean invoke = JSXController.invoke("bounds", Arrays.asList(str));
		// _Log.i("" + Arrays.asList(invoke.getValuesList()));
		NewsBean nb = genNewsRects(genNewsContents());
		for(NewsArticle na : nb.getArticles(3)){
			String[] str = { Constants.PSD_LIBRARY_PATH,Constants.PAGE_2_3_PATH, na.getType().toString()};
			JSXController.invoke("dumplicateExtendPsd", Arrays.asList(str));
			String[] str1 = {"content","content_" + na.getOrder()};
			JSXController.invoke("changeName", Arrays.asList(str1));
			String[] str2 = {"text","text_" + na.getContent().substring(0,5)};
			JSXController.invoke("changeName", Arrays.asList(str2));
			String[] str3 = {"text_" + na.getContent().substring(0,5),na.getContent()};
			JSXController.invoke("changeText", Arrays.asList(str3));

		}
	}

	private NewsBean genNewsRects(NewsBean nb) {
//		List<NewsArticle> articles = nb.getArticles(1);
//		List<NewsArticle> articles2 = nb.getArticles(2);
//		List<NewsArticle> articles3 = nb.getArticles(3);
//		for(NewsArticle na : articles3){
//			na.setBound(bound);
//		}
//		List<NewsArticle> articles4 = nb.getArticles(4);
		return nb;
	}

	private NewsBean genNewsContents() {
		List<NewsBean> visitorsTrackNews = articlesFromString(Constants.VISITORS_TRACK_LIBRARY_PATH);
		List<NewsBean> scenicBlogsNews = articlesFromString(Constants.SCENIC_BLOGS_LIBRARY_PATH);
		List<NewsBean> scenicNewsNews = articlesFromString(Constants.SCENIC_NEWS_LIBRARY_PATH);
		List<NewsBean> travelLawsNews = articlesFromString(Constants.TRAVEL_LAWS_LIBRARY_PATH);
		int releaseNum = 3;
		List<NewsArticle> articles = new ArrayList<>();
		NewsBean nb = new NewsBean("_" + releaseNum, null, articles, releaseNum);
		nb.pushArticles(typeOfArticles(articlesInReleaseNumber(visitorsTrackNews, releaseNum),NewsType.VISITORS_TRACK),3);
		nb.pushArticles(typeOfArticles(articlesInReleaseNumber(scenicBlogsNews, releaseNum),NewsType.SCENIC_BLOGS), 3);
		nb.pushArticles(typeOfArticles(articlesInReleaseNumber(travelLawsNews, releaseNum),NewsType.TRAVEL_LAWS), 4);
		nb.pushArticles(typeOfArticles(articlesInReleaseNumber(scenicNewsNews, releaseNum),NewsType.SCENIC_NEWS), 2);
		return nb;
	}

	private List<NewsArticle> typeOfArticles(List<NewsArticle> list,
			NewsType type) {
		for (NewsArticle n : list) {
			n.setType(type);
		}
		return list;
	}
	private List<NewsArticle> articlesInReleaseNumber(List<NewsBean> newsBean,
			int releaseNum) {
		List<NewsArticle> returnList = null;
		for (NewsBean bean : newsBean) {
			if (bean.getReleaseNumber() == releaseNum) {
				returnList = bean.getArticles();
				break;
			}
		}
		return returnList;
	}

	private void articleStatues() {
		List<NewsBean> articlesFromString = articlesFromString(Constants.NEWS_LIBRARY_PATH);
		reFormNews(articlesFromString);
		List<String> names = new ArrayList<String>();
		List<String> dnames = new ArrayList<String>();
		for (NewsBean newBean : articlesFromString) {
			for (NewsArticle na : newBean.getArticles()) {
				names.add(na.getAuthor());
				dnames.add(na.getDepartment());
				// _Log.i(na.getDepartment() + " " + na.getAuthor());
			}
		}
		List<List<Integer>> result = repeatList(names);
		List<List<Integer>> department = repeatList(dnames);
		// _Log.i(result + "\n" + department);
		traceRepeatList(result, names);
		// traceRepeatList(department,dnames);
	}

	private void traceRepeatList(List<List<Integer>> department,
			List<String> dnames) {
		for (int i = 0; i < department.size(); i++) {
			int nameI = department.get(i).get(0);
			int numDepName = department.get(i).size();
			String depName = dnames.get(nameI);
			_Log.i(depName + " " + numDepName);
		}
	}

	private void reFormNews(List<NewsBean> articlesFromString) {
		int sum = 0;
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
				author = author.replaceAll("-", " ");
				author = author.replaceAll("—", " ");
				author = author.replace("\r", " ");
				author = author.replace("\n", " ");
				author = author.replace("(", "");
				author = author.replace(")", "");
				author = author.replace("—", "");
				author = author.replace(":", "");
				author = author.replace("_", "");
				author = author.replace("，", "");
				author = author.replaceAll("  ", " ");
				author = author.replaceAll("   ", " ");
				na.setAuthor(author);

				if (author.split(" ").length > 1) {
					na.setDepartment(na.getAuthor().split(" ")[0]);
					na.setAuthor(na.getAuthor().split(" ")[na.getAuthor()
							.split(" ").length - 1]);
				} else {
					na.setDepartment(na.getAuthor());
					na.setAuthor("佚名");
				}
				na.setDepartment(na.getDepartment().replaceAll(" ", ""));
				na.setAuthor(na.getAuthor().replaceAll(" ", ""));
			}
		}
	}

	public List<List<Integer>> repeatList(List<String> list) {
		List<List<Integer>> repeatList = new ArrayList<List<Integer>>();
		Set<String> mySet = new HashSet<String>();
		for (String str : list) {
			mySet.add(str);
		}

		Iterator<String> it = mySet.iterator();
		while (it.hasNext()) {
			List<Integer> newSubList = new ArrayList<>();
			String current = it.next().toString();
			for (int j = 0; j < list.size(); j++) {
				if (current.indexOf(list.get(j)) != -1
						&& current.length() == list.get(j).length()) {
					newSubList.add(j);
				}
			}
			repeatList.add(newSubList);
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
				articleIndexes.add(lineIndexSum);// isNumberic(line));
			}
			lineIndexSum++;
		}
		return articleIndexes;
	}

	private int isNumberic(String str) {
		List<Integer> inds = new ArrayList<>();
		for (int i = 0; i < str.length(); i++) {
			String s = str.substring(i, i + 1);
			if (org.jsoup.helper.StringUtil.isNumeric(s)) {
				inds.add(i);
			} else {
				continue;
			}
		}
		if (inds.size() == 1) {
			return Integer
					.parseInt(str.substring(inds.get(0), inds.get(0) + 1));
		} else if (inds.size() == 0) {
			return 0;
		} else {
			int end = inds.get(0);
			for (int i = 0; i < inds.size() - 1; i++) {
				int current = inds.get(i);
				int next = inds.get(i + 1);
				boolean close = current == next - 1 ? true : false;
				end = next;
				if (!close) {
					break;
				}

			}
			return Integer.parseInt(str.substring(inds.get(0), end));
		}
	}

	public void continuedFromString() {
		_str = FileUtil.readToString(Constants.TRAVEL_LAWS_LIBRARY_PATH);
		List<String> lines = StringUtil.arrayToList(_str.split("\n"));
		List<Integer> indexesOfEachVersions = articleIndexesByVersions(lines);
		List<NewsBean> releases = new ArrayList<NewsBean>();
		for (int i = 0; i < indexesOfEachVersions.size() - 1; i++) {
			NewsBean newRelease = ensNews(lines, indexesOfEachVersions.get(i),
					indexesOfEachVersions.get(i + 1),
					isNumberic(lines.get(indexesOfEachVersions.get(i))));
			releases.add(newRelease);
		}
		NewsBean releaseUsing = releases.get(0);

		reboundArticle(releaseUsing);
		OutputController.vBScriptPrint(releaseUsing);
	}

	public List<NewsBean> articlesFromString(String path) {
		_str = FileUtil.readToString(path);
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
					indexesOfEachVersions.get(i + 1),
					isNumberic(lines.get(indexesOfEachVersions.get(i))));
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
		return new NewsBean("_" + ind, null, articles, ind);
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
			author = author + getAuthors(line);
			if (getTitles(line) != "" || getDepartment(line) != "" || getAuthors(line) != "") {
				continue;
			}
			content = content + line;
		}

		if (Constants._SET_CONTENT_NOTE_TO_TITLE) {

			String note = "";
			if (content.lastIndexOf("(") != -1) {
				note = content.substring(content.lastIndexOf("("),
						content.lastIndexOf(")") + 1);
			}
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

	private String getAuthors(String str) {
		String tag = "<author>";
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
