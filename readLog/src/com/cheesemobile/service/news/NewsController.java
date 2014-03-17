package com.cheesemobile.service.news;

import java.util.ArrayList;
import java.util.List;

import com.cheesemobile.domain.NewsArticle;
import com.cheesemobile.domain.NewsBean;
import com.cheesemobile.domain.NewsBeanArray;
import com.cheesemobile.domain.NewsImage;
import com.cheesemobile.domain.NewsStyle;
import com.cheesemobile.domain.NewsText;
import com.cheesemobile.service.Constants;
import com.cheesemobile.service.JSXController;
import com.cheesemobile.util.FileUtil;
import com.cheesemobile.util.StringUtil;
import com.cheesemobile.util._Log;

public class NewsController {

	public String _str = null;
	private static int FIRST_CHARACTER = 0;

	public enum NewsType {
		FOREIGN, RECT_3, RECT_2, RECT_1, RECT_4, BACK_1, BACK_2, BACK_3, BACK_4, STATIC_TEXT, VISITORS_TRACK, SCENIC_BLOGS, SCENIC_NEWS, GROUP, I_SPEAK, TRAVEL_LINKS, TRAVEL_LAWS, GALLERY, CUSTOM, IMAGE, TEXT, PLACES,BACKGROUND, SPLIT_LINES, ROW, COW, BACKGROUND_TOP_LEFT, BACKGROUND_TOP_RIGHT, BACKGROUND_BTN_LEFT, BACKGROUND_BTN_RIGHT, BACKGROUND_CENTER, BACKGROUND_TOP, BACKGROUND_RIGHT, BACKGROUND_BUTTOM, BACKGROUND_LEFT, TITLE;
		public String toString() {
			String str = "";
			switch (this) {
			case STATIC_TEXT:
				str = "static";
				break;
			case FOREIGN:
				str = "foreign";
				break;
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
			case TRAVEL_LINKS:
				str = Constants.TRAVEL_LINKS_PSD_GROUP;
				break;
			case I_SPEAK:
				str = Constants.I_SPEAK_PSD_GROUP;
				break;
			case CUSTOM:
				str = "content";
				break;
			case GALLERY:
				str = "gallery";
				break;
			case IMAGE:
				str = "image";
				break;
			case TITLE:
				str = "标题";
				break;
			case TEXT:
				str = "正文";
				break;
			case GROUP:
				str = "正文组";
				break;
			case SPLIT_LINES:
				str = "split_lines";
				break;
			case ROW:
				str = "row";
				break;
			case COW:
				str = "cow";
				break;
			case BACKGROUND_BTN_LEFT:
				str = "background_btn_left";
				break;
			case BACKGROUND_BTN_RIGHT:
				str = "background_btn_right";
				break;
			case BACKGROUND_BUTTOM:
				str = "background_buttom";
				break;
			case BACKGROUND_RIGHT:
				str = "background_right";
				break;
			case BACKGROUND_TOP:
				str = "background_top";
				break;
			case BACKGROUND_CENTER:
				str = "background_center";
				break;
			case BACKGROUND_TOP_LEFT:
				str = "background_top_left";
				break;
			case BACKGROUND_TOP_RIGHT:
				str = "background_top_right";
				break;
			case BACKGROUND_LEFT:
				str = "background_left";
				break;
			case BACKGROUND:
				str = "background";
				break;
			case PLACES:
				str = "places";
				break;
			case BACK_1:
				str = "_back_1";
				break;
			case BACK_2:
				str = "_back_2";
				break;
			case BACK_3:
				str = "_back_3";
				break;
			case BACK_4:
				str = "_back_4";
				break;
			case RECT_1:
				str = "_rect_1";
				break;
			case RECT_2:
				str = "_rect_2";
				break;
			case RECT_3:
				str = "_rect_3";
				break;
			case RECT_4:
				str = "_rect_4";
				break;
			}
			return str;
		}

		public static NewsType typeFromString(String type) {
			if (type.contains("speak")) {
				return NewsType.I_SPEAK;
			}
			if (type.contains("第一版")) {
				return NewsType.SCENIC_NEWS;
			}
			return null;
		}
	};

	// public abstract interface DataCallback<T> {
	// public abstract void processData(T paramObject, boolean paramBoolean);
	// }

	public NewsController() {
		// 荣誉证
		// book(Constants.CUSTOM_LIBRARY_PATH,Constants.CUSTOM_TEXT_LIBRARY_PATH,NewsType.CUSTOM);
		// book("C:/Documents and Settings/Administrator/桌面/集体奖项证书.psd","C:/Documents and Settings/Administrator/桌面/集体奖项.txt",NewsType.CUSTOM);
		genOthers(5);
//		genPages(4);
		JSXController.getInstance().flush();
		if (0 == 0) {
			return;
		}
		// articleStatues();
		// String[] str = { "text_1" };
		// VoBean invoke = JSXController.getInstance().invoke("bounds",
		// Arrays.asList(str));
		// _Log.i("" + Arrays.asList(invoke.getValuesList()));
//		@SuppressWarnings("unused")
//		NewsBean nb = genNewsRects(genNewsContents());
//		for (NewsArticle na : nb.getArticles(3)) {
//			String[] str = { Constants.PSD_LIBRARY_PATH,
//					Constants.PAGE_2_3_PATH, na.getType().toString() };
//			JSXController.getInstance().invoke("dumplicateExtendPsd", str);
//			String[] str1 = { "content", "content_" + na.getOrder() };
//			JSXController.getInstance().invoke("changeName", str1);
//			String[] str2 = { "text", "text_" + na.getContent().substring(0, 2) };
//			JSXController.getInstance().invoke("changeName", str2);
//			String[] str3 = { "text_" + na.getContent().substring(0, 2),
//					na.getContent() };
//			JSXController.getInstance().invoke("changeText", str3);
//		}
	}

	private void genOthers(int releaseNumber) {
		NewsBeanArray articleStatues = articlesFromString(Constants.NEWS_LIBRARY_PATH_OTHERS);
		_Log.i(articleStatues.getReleaseSize(releaseNumber) + "");
		NewsBean release = articleStatues.getRelease(releaseNumber);

		NewsStyle _rect1 = new NewsStyle(-1, NewsType.RECT_1, "");
		NewsStyle _rect2 = new NewsStyle(-1, NewsType.RECT_2, "");
		NewsStyle _rect3 = new NewsStyle(-1, NewsType.RECT_3, "");
		NewsStyle _rect4 = new NewsStyle(-1, NewsType.RECT_4, "");

		NewsStyle _back1 = new NewsStyle(-1, NewsType.BACK_1,
				_rect1.getFullName());
		NewsStyle _back2 = new NewsStyle(-1, NewsType.BACK_2,
				_rect2.getFullName());
		NewsStyle _back3 = new NewsStyle(-1, NewsType.BACK_3,
				_rect3.getFullName());
		NewsStyle _back4 = new NewsStyle(-1, NewsType.BACK_4,
				_rect4.getFullName());
		_back1.addStaticText(release.getArticles().get(1).getContent());
		NewsImage newsImage = new NewsImage(0,"",_back1.getFullName());
		newsImage.changeImage(release.getArticles().get(1).getPicsUrl().get(0));
		_back2.addStaticText(release.getArticles().get(0).getContent());
		_back3.addStaticText(release.getArticles().get(0).getContent());
		_back4.addStaticText(release.getArticles().get(0).getContent());
		_back1.updateStaticTexts();
		_back2.updateStaticTexts();
		_back3.updateStaticTexts();
		_back4.updateStaticTexts();
	}

	private void genPages(int releaseNumber) {
		NewsBeanArray articleStatues = articlesFromString(Constants.NEWS_LIBRARY_PATH);
		_Log.i(articleStatues.getReleaseSize(releaseNumber) + "");
		NewsBeanArray articleStatues2 = articlesFromString(Constants.NEWS_LIBRARY_PATH_VISITORS_TRACK);
		_Log.i(articleStatues2.getReleaseSize(releaseNumber) + "");
		NewsBeanArray articleStatues3 = articlesFromString(Constants.NEWS_LIBRARY_PATH_TRAVEL_LAWS);
		_Log.i(articleStatues3.getReleaseSize(releaseNumber) + "");
		NewsBeanArray articleStatues4 = articlesFromString(Constants.NEWS_LIBRARY_PATH_SCENIC_BLOGS);
		_Log.i(articleStatues4.getReleaseSize(releaseNumber) + "");

		NewsBean release = articleStatues.getRelease(releaseNumber);
		release.setAll(2, NewsType.SCENIC_NEWS);
		NewsBean release2 = articleStatues2.getRelease(releaseNumber);
		release2.setAll(3, NewsType.VISITORS_TRACK);
		NewsBean release3 = articleStatues3.getRelease(releaseNumber);
		release3.setAll(4, NewsType.TRAVEL_LAWS);
		NewsBean release4 = articleStatues4.getRelease(releaseNumber);
		release4.setAll(3, NewsType.SCENIC_BLOGS);
		release2.getArticles().addAll(release4.getArticles());
//		release2.expand();// page3
		release3.expand();//page4
		// release.expand();//page2
	}

	private void transformNews(NewsBean articles) {

		for (int i = 0; i < articles.size(); i++) {
			NewsType type = articles.getType(i);
		}
	}

	private void test2() {
		NewsStyle _rect2 = new NewsStyle(-1, NewsType.RECT_2, "");
		NewsStyle link = new NewsStyle(1, NewsType.TRAVEL_LINKS,
				_rect2.getFullName());
		NewsStyle news = new NewsStyle(1, NewsType.SCENIC_NEWS,
				_rect2.getFullName());
		NewsText new0 = new NewsText(
				0,
				NewsType.TEXT,
				"一座名副其实的灵山，所以我对珏山不仅仅是一种简单的心驰神往，更多的是一种敬仰，一种庄重。陡峭的山峰凌空入云，是对我们最为严峻的考验，旁边揽车静静地座落在那里却也无法掩盖它极具诱惑的光芒。我们深深在心里衡量：一种是靠自己一步一个脚印，力争上游地向上爬，其中路途险阻需极强的毅力及勇气方可领略“一览众山小”；另一种则是迈向座落在山峰之中的揽车，便可安逸地飘入珏山的环抱。在凛冽的寒风下，我们决然地摒弃了后者，选择了前者，眼前的困难更使我们萌生了变阻力为助力的决心与意志。在登峰的路途中，我们不是最出色的，我们或许抱怨，或许懈怠，但我们依旧互相鼓励同伴，并坚持不懈。此时我们俨然成为了一个战胜自我的英雄。对我而言珏山不仅仅是一座名山，更是能磨炼人类意志的灵山，一个人只有真正地背水一战，才能了解你到底有多优秀。在日后的工作中我们也定会凭借这种不屈不挠的品质，脚踏实地出色地完成工作。(散文网)");
		NewsText new1 = new NewsText(
				1,
				NewsType.TEXT,
				"一座名副其实的灵山，所以我对珏山不仅仅是一种简单的心驰神往，更多的是一种敬仰，一种庄重。陡峭的山峰凌空入云，是对我们最为严峻的考验，旁边揽车静静地座落在那里却也无法掩盖它极具诱惑的光芒。我们深深在心里衡量：一种是靠自己一步一个脚印，力争上游地向上爬，其中路途险阻需极强的毅力及勇气方可领略“一览众山小”；另一种则是迈向座落在山峰之中的揽车，便可安逸地飘入珏山的环抱。在凛冽的寒风下，我们决然地摒弃了后者，选择了前者，眼前的困难更使我们萌生了变阻力为助力的决心与意志。在登峰的路途中，我们不是最出色的，我们或许抱怨，或许懈怠，但我们依旧互相鼓励同伴，并坚持不懈。此时我们俨然成为了一个战胜自我的英雄。对我而言珏山不仅仅是一座名山，更是能磨炼人类意志的灵山，一个人只有真正地背水一战，才能了解你到底有多优秀。在日后的工作中我们也定会凭借这种不屈不挠的品质，脚踏实地出色地完成工作。(散文网)");
		NewsText new2 = new NewsText(
				2,
				NewsType.TEXT,
				"一座名副其实的灵山，所以我对珏山不仅仅是一种简单的心驰神往，更多的是一种敬仰，一种庄重。陡峭的山峰凌空入云，是对我们最为严峻的考验，旁边揽车静静地座落在那里却也无法掩盖它极具诱惑的光芒。我们深深在心里衡量：一种是靠自己一步一个脚印，力争上游地向上爬，其中路途险阻需极强的毅力及勇气方可领略“一览众山小”；另一种则是迈向座落在山峰之中的揽车，便可安逸地飘入珏山的环抱。在凛冽的寒风下，我们决然地摒弃了后者，选择了前者，眼前的困难更使我们萌生了变阻力为助力的决心与意志。在登峰的路途中，我们不是最出色的，我们或许抱怨，或许懈怠，但我们依旧互相鼓励同伴，并坚持不懈。此时我们俨然成为了一个战胜自我的英雄。对我而言珏山不仅仅是一座名山，更是能磨炼人类意志的灵山，一个人只有真正地背水一战，才能了解你到底有多优秀。在日后的工作中我们也定会凭借这种不屈不挠的品质，脚踏实地出色地完成工作。(散文网)");
		_rect2.addAll(news);
		news.addAll(new0, new1, new2);
		// _rect2.add(link);
		// NewsText title = new NewsText(0,NewsType.TEXT,"the title");
		// NewsText newsText = new
		// NewsText(1,NewsType.TEXT,"一座名副其实的灵山，所以我对珏山不仅仅是一种简单的心驰神往，更多的是一种敬仰，一种庄重。陡峭的山峰凌空入云，是对我们最为严峻的考验，旁边揽车静静地座落在那里却也无法掩盖它极具诱惑的光芒。我们深深在心里衡量：一种是靠自己一步一个脚印，力争上游地向上爬，其中路途险阻需极强的毅力及勇气方可领略“一览众山小”；另一种则是迈向座落在山峰之中的揽车，便可安逸地飘入珏山的环抱。在凛冽的寒风下，我们决然地摒弃了后者，选择了前者，眼前的困难更使我们萌生了变阻力为助力的决心与意志。在登峰的路途中，我们不是最出色的，我们或许抱怨，或许懈怠，但我们依旧互相鼓励同伴，并坚持不懈。此时我们俨然成为了一个战胜自我的英雄。对我而言珏山不仅仅是一座名山，更是能磨炼人类意志的灵山，一个人只有真正地背水一战，才能了解你到底有多优秀。在日后的工作中我们也定会凭借这种不屈不挠的品质，脚踏实地出色地完成工作。(散文网)");
		// link.add(title);
		// link.add(newsText);
		JSXController.getInstance().flush();
	}

	
	public void book(String libPath, String textPath, NewsType layerDumped) {
		NewsBeanArray nbs = articlesFromString(textPath);
		List<NewsArticle> articles = new ArrayList<>();
		NewsBean nb = new NewsBean(articles, 0);
		NewsBean release = nbs.getRelease(0);
		release.setAll(1, layerDumped);
		_Log.i("");
		release.status();
		// for(NewsArticle na : nb.getArticles()){
		// String[] str = { libPath,libPath, na.getType().toString()};
		// JSXController.getInstance().invoke("dumplicateExtendPsd",str);
		// String[] str1 = {"content 副本","content_" + na.getOrder()};
		// JSXController.getInstance().invoke("changeName", (str1));
		// String textName = "text_" + na.getContent().substring(0,2) +
		// na.getOrder();
		// String[] str2 = {"text 副本",textName};
		// JSXController.getInstance().invoke("changeName", (str2));
		// String titleName = "title_" + na.getTitle().substring(0,2) +
		// na.getOrder();
		// String[] str3 = {"title 副本",titleName};
		// JSXController.getInstance().invoke("changeName", (str3));
		// String[] str4 = {textName,na.getTitle()};
		// JSXController.getInstance().invoke("changeText", (str4));
		// String[] str5 = {titleName,na.getContent()};
		// JSXController.getInstance().invoke("changeText", (str5));
		// }
	}

	private NewsBean genNewsRects(NewsBean nb) {
		// List<NewsArticle> articles = nb.getArticles(1);
		// List<NewsArticle> articles2 = nb.getArticles(2);
		// List<NewsArticle> articles3 = nb.getArticles(3);
		// for(NewsArticle na : articles3){
		// na.setBound(bound);
		// }
		// List<NewsArticle> articles4 = nb.getArticles(4);
		return nb;
	}

//	private NewsBean genNewsContents() {
//		List<NewsBean> visitorsTrackNews = articlesFromString(Constants.VISITORS_TRACK_LIBRARY_PATH);
//		List<NewsBean> scenicBlogsNews = articlesFromString(Constants.SCENIC_BLOGS_LIBRARY_PATH);
//		List<NewsBean> scenicNewsNews = articlesFromString(Constants.SCENIC_NEWS_LIBRARY_PATH);
//		List<NewsBean> travelLawsNews = articlesFromString(Constants.TRAVEL_LAWS_LIBRARY_PATH);
//		int releaseNum = 3;
//		List<NewsArticle> articles = new ArrayList<>();
//		NewsBean nb = new NewsBean("_" + releaseNum, null, articles, releaseNum);
//		nb.pushArticles(
//				typeOfArticles(
//						articlesInReleaseNumber(visitorsTrackNews, releaseNum),
//						NewsType.VISITORS_TRACK), 3);
//		nb.pushArticles(
//				typeOfArticles(
//						articlesInReleaseNumber(scenicBlogsNews, releaseNum),
//						NewsType.SCENIC_BLOGS), 3);
//		nb.pushArticles(
//				typeOfArticles(
//						articlesInReleaseNumber(travelLawsNews, releaseNum),
//						NewsType.TRAVEL_LAWS), 4);
//		nb.pushArticles(
//				typeOfArticles(
//						articlesInReleaseNumber(scenicNewsNews, releaseNum),
//						NewsType.SCENIC_NEWS), 2);
//		return nb;
//	}

	private List<NewsArticle> typeOfArticles(List<NewsArticle> list,
			NewsType type) {
		for (NewsArticle n : list) {
			n.setType(type);
		}
		return list;
	}

//	private List<NewsArticle> articlesInReleaseNumber(List<NewsBean> newsBean,
//			int releaseNum) {
//		List<NewsArticle> returnList = null;
//		for (NewsBean bean : newsBean) {
//			if (bean.getReleaseNumber() == releaseNum) {
//				returnList = bean.getArticles();
//				break;
//			}
//		}
//		return returnList;
//	}

	// private NewsBeanArray articleStatues(String path) {
	// NewsBeanArray articlesFromString = articlesFromString(path);
	// reFormNews(articlesFromString);
	// List<String> names = new ArrayList<String>();
	// List<String> dnames = new ArrayList<String>();

	// for (NewsBean newBean : articlesFromString) {
	// for (NewsArticle na : newBean.getArticles()) {
	// names.add(na.getAuthor());
	// dnames.add(na.getDepartment());
	// // _Log.i(na.toString());
	// }
	// }
	// return articlesFromString;
	// List<List<Integer>> result = repeatList(names);
	// List<List<Integer>> department = repeatList(dnames);
	// _Log.i(result + "\n" + department);
	// traceRepeatList(result, names);
	// traceRepeatList(department,dnames);
	// }

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

//	public void continuedFromString() {
//		_str = FileUtil.readToString(Constants.TRAVEL_LAWS_LIBRARY_PATH);
//		List<String> lines = StringUtil.arrayToList(_str.split("\n"));
//		List<Integer> indexesOfEachVersions = articleIndexesByVersions(lines);
//		List<NewsBean> releases = new ArrayList<NewsBean>();
//		for (int i = 0; i < indexesOfEachVersions.size() - 1; i++) {
//			NewsBean newRelease = ensNews(lines, indexesOfEachVersions.get(i),
//					indexesOfEachVersions.get(i + 1),
//					StringUtil.intInString(lines.get(indexesOfEachVersions
//							.get(i))));
//			releases.add(newRelease);
//		}
//		NewsBean releaseUsing = releases.get(0);
//
//		reboundArticle(releaseUsing);
//		OutputController.vBScriptPrint(releaseUsing);
//	}

	public NewsBeanArray articlesFromString(String path) {
		_str = FileUtil.readToString(path, true);
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
		NewsBeanArray releases = new NewsBeanArray();
		indexesOfEachVersions.add(lines.size() - 1);
		for (int i = 0; i < indexesOfEachVersions.size() - 1; i++) {
			NewsBean newRelease = ensNews(lines, indexesOfEachVersions.get(i),
					indexesOfEachVersions.get(i + 1),
					StringUtil.intInString(lines.get(indexesOfEachVersions
							.get(i))));
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
		return new NewsBean(articles, ind);
	}

	private NewsArticle articleFromVersion(List<String> lines) {
		String content = "";
		List<String> urls = new ArrayList<String>();
		String title = "";
		String department = "";
		String pick = "";
		int page = -1;
		NewsType type = null;
		for (String line : lines) {
			String getImgs = getImgs(line);
			if (!getImgs.equals("")) {
				urls.add(getImgs);
			}
			String getType = getType(line);
			if (!getType.equals("")) {
				type = NewsType.typeFromString(getType);
			}
			String getPage = getPage(line);
			if (!getPage.equals("")) {
				page = Integer.parseInt(getPage);
			}
			String date = getDate(line);
			title = title + getTitles(line);
			department = department + getDepartment(line);
			pick = getPick(line);
			if (getImgs(line) != "" ||getDate(line) != "" || getType(line) != ""
					|| getTitles(line) != "" || getDepartment(line) != ""
					|| getPick(line) != "") {
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
		return new NewsArticle(articlesId++, null, content, pick, department,
				title, urls, type, page);
	}

//	private void reboundArticle(NewsBean news) {
//		for (NewsArticle article : news.getArticles()) {
//			List<Point> points = new ArrayList<Point>();
//			Rectangle rect = new Rectangle();
//			int x = 0;
//			int y = 0;
//			float width = Constants.CONTENT_WIDTH;
//			float height = Constants.ARTICLE_HEIGHT_DEFAULT;
//			if (article.getPicsUrl() != null) {
//				List<Rectangle> subObjBounds = new ArrayList<Rectangle>();
//				float imageWidth = (float) (width * .39);
//				float imageHeight = 100;
//				float imageLasty = 0;
//				for (int i = 0; i < article.getPicsUrl().size(); i++) {
//					imageLasty = (y + i * (imageHeight + _ruler));
//					subObjBounds.add(new Rectangle(width - imageWidth,
//							imageLasty, imageWidth, imageHeight));
//				}
//				article.setSubObjBounds(subObjBounds);
//
//				Point clipPoint = new Point((width - _ruler - imageWidth),
//						(imageLasty + imageHeight + _ruler));
//				rect = new Rectangle(x, y, width, height);
//				points.add(new Point(x, y));
//				points.add(new Point(x + clipPoint.x, y));
//				points.add(new Point(x + clipPoint.x, y + clipPoint.y));
//				points.add(new Point(x + width, y + clipPoint.y));
//				points.add(new Point(x + width, y + height));
//				points.add(new Point(x, y + height));
//				article.setPoints(points);
//				article.setBound(rect);
//
//			} else {
//				rect = new Rectangle(x, y, Constants.CONTENT_WIDTH,
//						Constants.ARTICLE_HEIGHT_DEFAULT);
//				points.add(new Point(x, y));
//				points.add(new Point(x + width, y));
//				points.add(new Point(x + width, y + height));
//				points.add(new Point(x, y + height));
//				article.setPoints(points);
//				article.setBound(rect);
//			}
//		}
//	}

	private String getPage(String str) {
		String tag = "<page>";
		String result = rowByTag(str, tag);
		if (result == null) {
			return "";
		}
		return result;
	}
	
	private String getImgs(String str) {
		String tag = "<img>";
		String result = rowByTag(str, tag);
		if (result == null) {
			return "";
		}
		return Constants.IMG_DESTINATION_PATH + result;
	}

	private String getDate(String str) {
		String tag = "<date>";
		String result = rowByTag(str, tag);
		if (result == null) {
			return "";
		}
		return result;
	}

	private String getType(String str) {
		String tag = "<type>";
		String result = rowByTag(str, tag);
		if (result == null) {
			return "";
		}
		return result;
	}

	private String getTitles(String str) {
		String tag = "<title>";
		String result = rowByTag(str, tag);
		if (result == null) {
			return "";
		}
		return result;
	}

	private String getPick(String str) {
		String tag = "<pick>";
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
		if (str.indexOf(tag) == 0) {
			str = str.replaceFirst(tag, "");
			str = str.substring(0, str.lastIndexOf("\r"));
			return str;
		}
		return null;
	}
}
