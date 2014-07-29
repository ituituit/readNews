package com.cheesemobile.service.news;

import java.io.File;
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
		custom, ARTICLE, SUPERVISE, SAFETY_LAW, FILTER_1, FILTER_2, TOP_LINE, POAM, SAFETY, SPRING_THEME, CENTER_1, CENTER_2, CENTER_FRONT, CENTER_BACKWARD, FOREIGN, RECT_3, RECT_2, RECT_1, RECT_4, BACK_1, BACK_2, BACK_3, BACK_4, DEVELOP_PROJECT, STATIC_TEXT, VISITORS_TRACK, SCENIC_BLOGS, SCENIC_NEWS, GROUP, I_SPEAK, TRAVEL_LINKS, TRAVEL_LAWS, GALLERY, CUSTOM, IMAGE, TEXT, PLACES, BACKGROUND, SPLIT_LINES, ROW, COW, BACKGROUND_TOP_LEFT, BACKGROUND_TOP_RIGHT, BACKGROUND_BTN_LEFT, BACKGROUND_BTN_RIGHT, BACKGROUND_CENTER, BACKGROUND_TOP, BACKGROUND_RIGHT, BACKGROUND_BUTTOM, BACKGROUND_LEFT, TITLE;
		String customVal;
		private int nCode;

		private NewsType() {
		}

		private NewsType(int _nCode) {
			this.nCode = _nCode;
		}

		public String toString() {
			String str = "";
			switch (this) {
			case FILTER_1:
				str = "filter1";
				break;
			case FILTER_2:
				str = "filter2";
				break;
			case TOP_LINE:
				str = "刊头";
				break;
			case POAM:
				str = "唐宋游人诗词";
				break;
			case CENTER_FRONT:
				str = "中缝正";
				break;
			case CENTER_BACKWARD:
				str = "中缝反";
				break;
			case CENTER_1:
				str = "景区应知应会";
				break;
			case CENTER_2:
				str = "国学解析";
				break;
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
			case DEVELOP_PROJECT:
				str = "重点工程进行时";
				break;
			case SPRING_THEME:
				str = "春的声音";
				break;
			case SUPERVISE:
				str = "曝光台";
				break;
			case SAFETY:
				str = "安全专栏";
				break;
			case SAFETY_LAW:
				str = "聚焦安全生产法";
				break;
			case ARTICLE:
				str = "文章";
				break;
			case CUSTOM:
				str = customVal;
				break;
			}
			return str;
		}

		public static NewsType typeFromString(String type) {
			if (type.contains("聚焦安全生产法")) {
				return NewsType.SAFETY_LAW;
			}
			if (type.contains("图片")) {
				return NewsType.GROUP;
			}
			if (type.contains("我来说两句")) {
				return NewsType.I_SPEAK;
			}
			if (type.contains("重点工程进行时")) {
				return NewsType.DEVELOP_PROJECT;
			}
			if (type.contains("刊头")) {
				return NewsType.TOP_LINE;
			}
			if (type.contains("组")) {
				return NewsType.GROUP;
			}
			if (type.contains("春的声音")) {
				return NewsType.SPRING_THEME;
			}
			if (type.contains("曝光台")) {
				return NewsType.SUPERVISE;
			}
			if (type.contains("文章")) {
				return NewsType.ARTICLE;
			}
			if (type.contains("唐宋游人诗词")) {
				return NewsType.POAM;
			}
			for (NewsType iterable_element : NewsType.values()) {
				if (iterable_element.toString().equals(type)) {
					return iterable_element;
				}
			}
			return null;
		}

		public static NewsType setCustom(String str) {
			NewsType n = NewsType.CUSTOM;
			n.customVal = str;
			return n;
		}
	};

	// public abstract interface DataCallback<T> {
	// public abstract void processData(T paramObject, boolean paramBoolean);
	// }

	public NewsController() {
		// 荣誉证
		// book(Constants.CUSTOM_LIBRARY_PATH,Constants.CUSTOM_TEXT_LIBRARY_PATH,NewsType.CUSTOM);
		// 牌
		// card();

//		 printPics();
		// book("C:/Documents and Settings/Administrator/桌面/集体奖项证书.psd","C:/Documents and Settings/Administrator/桌面/集体奖项.txt",NewsType.CUSTOM);
		// genCenter1(24);
		// genCenter2(24);
		
//		outputImage(22);
//		String[] names13 = {"第十九期", "第二十期", "第二十一期", "第二十二期",
//				"第二十三期" };
//		 printNewPage(names13, "/Users/pwl/Desktop/",new printNewPageCallBack(){
//			 @Override
//			public void invoke(String name) {
//				 outputImage(name);
//			}
//		 });
		
//		 articleStatues(Constants.NEWS_LIBRARY_PATH);
		
		
//		FootBall fb = new FootBall();
		 printSide(false,3);
//		 genOthers(23);
//		 genPages(23);
		// genSafe(16);
//		 genPoam(22);
		// String content = "123\n321<hw>hel\nlo你</hw>123\n321<hw>好</hw>123321";
		// TextRangeBean tx = new TextRangeBean(content);
		// genTravelLaw(59);
//		manPath();
		// String [] strs =
		// {"/Users/pwl/Desktop/Sam.bmp","/Users/pwl/Desktop/T.bmp"};
		// CustomCtrl.getInstance().ctrlOnSample(strs);
		JSXController.getInstance().flush();
		if (0 == 0) {
			return;
		}
		// articleStatues();
		// String[] str = { "text_1" };
		// VoBean invoke = JSXController.getInstance().invoke("bounds",
		// Arrays.asList(str));
		// _Log.i("" + Arrays.asList(invoke.getValuesList()));
		// @SuppressWarnings("unused")
		// NewsBean nb = genNewsRects(genNewsContents());
		// for (NewsArticle na : nb.getArticles(3)) {
		// String[] str = { Constants.PSD_LIBRARY_PATH,
		// Constants.PAGE_2_3_PATH, na.getType().toString() };
		// JSXController.getInstance().invoke("dumplicateExtendPsd", str);
		// String[] str1 = { "content", "content_" + na.getOrder() };
		// JSXController.getInstance().invoke("changeName", str1);
		// String[] str2 = { "text", "text_" + na.getContent().substring(0, 2)
		// };
		// JSXController.getInstance().invoke("changeName", str2);
		// String[] str3 = { "text_" + na.getContent().substring(0, 2),
		// na.getContent() };
		// JSXController.getInstance().invoke("changeText", str3);
		// }
	}

	private void manPath() {
		JavaApplescriptTest as = new JavaApplescriptTest();
		JSXController.getInstance().flush();
		as.copy();
		JSXController.getInstance().invoke("manPath", "0");
		JSXController.getInstance().flush();
		as.paste();
	}

	interface printNewPageCallBack {
		public void invoke(String fileName);
	}

	private void printNewPage(String[] names, String Path,
			printNewPageCallBack cb) {
		for (int i = 0; i < names.length; i++) {
			String path = Path + names[i] + ".psd";
			JSXController.getInstance().invoke("openDoc", path);
			// printSide(false);
			cb.invoke(names[i]);
			JSXController.getInstance().invoke("save", path);
			JSXController.getInstance().invoke("closeDoc", "0");
		}
	}

	private void outputImage(int releaseNum) {
		String[] names = { "", "2014珏山动态第一期", "2014珏山动态第二期", "2014珏山动态第三期",
				"2014珏山动态第四期", "2014珏山动态第五期", "2014珏山动态第六期", "2014珏山动态第七期",
				"2014珏山动态第八期", "2014珏山动态第九期", "2014珏山动态第十期", "2014珏山动态第十一期",
				"2014珏山动态第十二期", "2014珏山动态第十三期", "2014珏山动态第十四期", "2014珏山动态第十五期",
				"2014珏山动态第十六期", "2014珏山动态第十七期", "2014珏山动态第十八期", "2014珏山动态第十九期",
				"2014珏山动态第二十期", "2014珏山动态第二十一期", "2014珏山动态第二十二期",
				"2014珏山动态第二十三期", "2014珏山动态第二十四期", "2014珏山动态第二十五期",
				"2014珏山动态第二十六期", "2014珏山动态第二十七期", "2014珏山动态第二十八期",
				"2014珏山动态第二十九期", "2014珏山动态第三十期", "2014珏山动态第三十一期",
				"2014珏山动态第三十二期", "2014珏山动态第三十三期", "2014珏山动态第三十四期",
				"2014珏山动态第三十五期", "2014珏山动态第三十六期", "2014珏山动态第三十七期",
				"2014珏山动态第三十八期", "2014珏山动态第三十九期", "2014珏山动态第四十期",
				"2014珏山动态第四十一期", "2014珏山动态第四十二期", "2014珏山动态第四十三期",
				"2014珏山动态第四十四期", "2014珏山动态第四十五期", "2014珏山动态第四十六期",
				"2014珏山动态第四十七期", "2014珏山动态第四十八期", "2014珏山动态第四十九期",
				"2014珏山动态第五十期", "2014珏山动态第五十一期", "2014珏山动态第五十二期" };
		String name = names[releaseNum];
		String folder = "/Users/pwl/Desktop/";
		showPage(false);
		JSXController.getInstance().invoke("saveAsImage", "newOutput.jpg");
		JSXController.getInstance().flush();
		FileUtil.rename(folder + "newOutput.jpg", folder + name + "2.jpg");
		showPage(true);
		JSXController.getInstance().invoke("saveAsImage", "newOutput.jpg");
		JSXController.getInstance().flush();
		FileUtil.rename(folder + "newOutput.jpg", folder + name + "1.jpg");
		_Log.i(folder + name + "1.jpg");
	}

	private void outputImage(String name) {
		String[] names = { "", "2014珏山动态第一期", "2014珏山动态第二期", "2014珏山动态第三期",
				"2014珏山动态第四期", "2014珏山动态第五期", "2014珏山动态第六期", "2014珏山动态第七期",
				"2014珏山动态第八期", "2014珏山动态第九期", "2014珏山动态第十期", "2014珏山动态第十一期",
				"2014珏山动态第十二期", "2014珏山动态第十三期", "2014珏山动态第十四期", "2014珏山动态第十五期",
				"2014珏山动态第十六期", "2014珏山动态第十七期", "2014珏山动态第十八期", "2014珏山动态第十九期",
				"2014珏山动态第二十期", "2014珏山动态第二十一期", "2014珏山动态第二十二期",
				"2014珏山动态第二十三期", "2014珏山动态第二十四期", "2014珏山动态第二十五期",
				"2014珏山动态第二十六期", "2014珏山动态第二十七期", "2014珏山动态第二十八期",
				"2014珏山动态第二十九期", "2014珏山动态第三十期", "2014珏山动态第三十一期",
				"2014珏山动态第三十二期", "2014珏山动态第三十三期", "2014珏山动态第三十四期",
				"2014珏山动态第三十五期", "2014珏山动态第三十六期", "2014珏山动态第三十七期",
				"2014珏山动态第三十八期", "2014珏山动态第三十九期", "2014珏山动态第四十期",
				"2014珏山动态第四十一期", "2014珏山动态第四十二期", "2014珏山动态第四十三期",
				"2014珏山动态第四十四期", "2014珏山动态第四十五期", "2014珏山动态第四十六期",
				"2014珏山动态第四十七期", "2014珏山动态第四十八期", "2014珏山动态第四十九期",
				"2014珏山动态第五十期", "2014珏山动态第五十一期", "2014珏山动态第五十二期" };
		int releaseNum = -1;
		for(int i = 0; i < names.length; i++){
			if(names[i].indexOf(name) != -1){
				releaseNum = i;
			};
		}
		if(releaseNum != -1){
			outputImage(releaseNum);
		}
	}
	
	private void printSide(boolean pageOne, int number) {
		showPage(pageOne);
		for (int i = 0; i < number; i++) {
			JSXController.getInstance().invoke("printJSX", "0");
		}
	}

	private void showPage(boolean pageOne) {
		NewsStyle _rect1 = new NewsStyle(-1, NewsType.RECT_1, "");
		NewsStyle _rect2 = new NewsStyle(-1, NewsType.RECT_2, "");
		NewsStyle _rect3 = new NewsStyle(-1, NewsType.RECT_3, "");
		NewsStyle _rect4 = new NewsStyle(-1, NewsType.RECT_4, "");
		NewsStyle centerFront = new NewsStyle(-1, NewsType.CENTER_FRONT, "");
		NewsStyle centerBackward = new NewsStyle(-1, NewsType.CENTER_BACKWARD,
				"");
		NewsStyle filterFront = new NewsStyle(-1, NewsType.FILTER_1, "");
		NewsStyle filterBackward = new NewsStyle(-1, NewsType.FILTER_2, "");
		_rect1.hide();
		_rect2.hide();
		_rect3.hide();
		_rect4.hide();
		centerFront.hide();
		centerBackward.hide();
		filterFront.hide();
		filterBackward.hide();

		if (pageOne) {
			_rect1.show();
			_rect4.show();
			centerFront.show();
			filterFront.show();
		} else {
			_rect2.show();
			_rect3.show();
			centerBackward.show();
			filterBackward.show();
		}
	}

	private void gendishes() {
		Constants.IMG_DESTINATION_PATH = "C:/Documents and Settings/Administrator/桌面/dishes/";
		String LIB_DISHES_PATH = "C:/Documents and Settings/Administrator/桌面/dishes.txt";
		NewsBeanArray articleStatues = articlesFromString(LIB_DISHES_PATH);
		NewsBean release = articleStatues.getRelease(4);
		release.setAll(1, NewsType.GROUP);
		release.expandStatic();
	}

	private void genSafe(int i) {
		// Constants.IMG_DESTINATION_PATH =
		// "C:/Documents and Settings/Administrator/桌面/dishes/";
		String LIB_DISHES_PATH = Constants.HOME_FOLDER
				+ "texts/safety_publicity.txt";
		NewsBeanArray articleStatues = articlesFromString(LIB_DISHES_PATH);
		NewsBean release = articleStatues.getRelease(i);
		release.setAll(1, NewsType.SAFETY);
		release.expand();
	}

	private void genPoam(int i) {
		// Constants.IMG_DESTINATION_PATH =
		// "C:/Documents and Settings/Administrator/桌面/dishes/";
		String LIB_DISHES_PATH = Constants.HOME_FOLDER + "texts/travel.txt";
		NewsBeanArray articleStatues = articlesFromString(LIB_DISHES_PATH);
		NewsBean release = articleStatues.getRelease(i);
		release.setAll(4, NewsType.POAM);
		release.expand();
	}

	private void genTravelLaw(int i) {
		String LIB_DISHES_PATH = Constants.HOME_FOLDER + "texts/travel_law.txt";
		NewsBeanArray articleStatues = articlesFromString(LIB_DISHES_PATH);
		NewsBean release = articleStatues.getRelease(i);
		release.setAll(1, NewsType.SAFETY_LAW);
		release.expand();
	}

	private void genOthers(int releaseNumber) {
		NewsBeanArray articleStatues = articlesFromString(Constants.NEWS_LIBRARY_PATH_OTHERS);
		_Log.i(articleStatues.getReleaseSize(releaseNumber) + "");
		NewsBean release = articleStatues.getRelease(releaseNumber);

		NewsStyle _rect1 = new NewsStyle(-1, NewsType.RECT_1, "");
		NewsStyle _rect2 = new NewsStyle(-1, NewsType.RECT_2, "");
		NewsStyle _rect3 = new NewsStyle(-1, NewsType.RECT_3, "");
		NewsStyle _rect4 = new NewsStyle(-1, NewsType.RECT_4, "");
		NewsStyle centerFront = new NewsStyle(-1, NewsType.CENTER_FRONT, "");
		NewsStyle centerBackward = new NewsStyle(-1, NewsType.CENTER_BACKWARD,
				"");

		NewsStyle _back1 = new NewsStyle(-1, NewsType.BACK_1,
				_rect1.getFullName());
		NewsStyle _back2 = new NewsStyle(-1, NewsType.BACK_2,
				_rect2.getFullName());
		NewsStyle _back3 = new NewsStyle(-1, NewsType.BACK_3,
				_rect3.getFullName());
		NewsStyle _back4 = new NewsStyle(-1, NewsType.BACK_4,
				_rect4.getFullName());
		_back1.addStaticText(release.getArticles().get(1).getContent());
		NewsImage newsImage = new NewsImage(0, "", _back1.getFullName());
		newsImage.changeImage(release.getArticles().get(1).getPicsUrl().get(0));
		NewsImage frontImage = new NewsImage(0, "", centerFront.getFullName());
		frontImage
				.changeImage(release.getArticles().get(1).getPicsUrl().get(1));
		NewsImage backwardImage = new NewsImage(0, "",
				centerBackward.getFullName());
		backwardImage.changeImage(release.getArticles().get(1).getPicsUrl()
				.get(2));
		_back2.addStaticText(release.getArticles().get(0).getContent());
		_back3.addStaticText(release.getArticles().get(0).getContent());
		_back4.addStaticText(release.getArticles().get(0).getContent());
		_back1.updateStaticTexts();
		_back2.updateStaticTexts();
		_back3.updateStaticTexts();
		_back4.updateStaticTexts();
	}

	private void genCenter2(int releaseNumber) {
		NewsBeanArray articleStatues = articlesFromString(Constants.NEWS_LIBRARY_DAODE);
		_Log.i(articleStatues.getReleaseSize(releaseNumber) + "");
		NewsBean release = articleStatues.getRelease(releaseNumber);

		NewsStyle _rect1 = new NewsStyle(-1, NewsType.CENTER_2, "");

		_rect1.addStaticText(release.getArticles().get(0).getContent());
		_rect1.addStaticText(release.getArticles().get(1).getContent());
		// NewsImage newsImage = new NewsImage(0,"",_rect1.getFullName());
		// newsImage.changeImage(release.getArticles().get(1).getPicsUrl().get(0));
		_rect1.updateStaticTexts();
	}

	private void genCenter1(int releaseNumber) {
		NewsBeanArray articleStatues = articlesFromString(Constants.NEWS_LIBRARY_PATH_EXAMS);
		_Log.i(articleStatues.getReleaseSize(releaseNumber) + "");
		NewsBean release = articleStatues.getRelease(releaseNumber);

		NewsStyle _rect1 = new NewsStyle(-1, NewsType.CENTER_1, "");

		_rect1.addStaticText(release.getArticles().get(0).getContent());
		// NewsImage newsImage = new NewsImage(0,"",_rect1.getFullName());
		// newsImage.changeImage(release.getArticles().get(1).getPicsUrl().get(0));
		_rect1.updateStaticTexts();
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
		release4.setAll(3, NewsType.POAM);
		// release4.setAll(3, NewsType.SCENIC_BLOGS);
		// release2.getArticles().addAll(release4.getArticles());
		// release4.expand();// page3
		// release4.expand();
		// release3.expand();// page4
		release.expand();// page2
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

	public void printPics() {
		String[] names = { "0758248951.jpg", "135441468878-1 (dragged).tiff",
				"08162080923.jpg", "13544151729.jpg",
				"09071100134ee51a7b20094a29.jpg", "135471912147.gif",
				"09111214512523274e811dca9e.jpg",
				"135497440364-1 (dragged).tiff", "1-14010G01P9150.gif",
				"13551155278.jpg", "10190775168.jpg",
				"135532267994-1 (dragged).tiff",
				"1111181432b6a474e174460580.jpg",
				"135563301244-1 (dragged).tiff",
				"11122716401a9a983cae91bfc7.jpg", "135573028139.gif",
				"11P45B164-8.jpg", "13562286386.jpg",
				"1201051203b8bd1d18df4ede28.gif", "1377504019_b.jpg",
				"135095439786.jpg", "20130227162745_JEzzZ.jpeg",
				"135095440067.jpg", "3360_1290149540lR6F.jpg",
				"135115197398.gif", "4c91e6bdb88ef8f4c07fe333e5ca545f.jpg",
				"135115197443.jpg", "8-130Q6102I3.jpg", "135175845364.gif",
				"8e42fc75097afe8cbc293057f781fd74.gif",
				"135187774703-1 (dragged).tiff", "f8QVZ.jpeg",
				"135204523089-1 (dragged).tiff",
				"jbh10072322030c7e91a8cb4cfba0.gif", "135237962449.jpg",
				"jianbihua558.gif", "135259674356.jpg", "sy_57164692682.jpg",
				"135383449733.jpg" };
		String path = "/Users/pwl/Desktop/untitled folder 2/";

		for (String string : names) {
			String url = path + string;
			NewsImage backwardImage = new NewsImage(0, "", "_rect_1");
			backwardImage.changeImage("" + url);
			// JSXController.getInstance().invoke("printJSX", "0");
		}
	}

	public void printPapersDate() {
		String[] names = { "2012珏山动态第一期", "2012珏山动态第二期", "2012珏山动态第三期",
				"2012珏山动态第四期", "2012珏山动态第五期", "2012珏山动态第六期", "2012珏山动态第七期",
				"2012珏山动态第八期", "2012珏山动态第九期", "2012珏山动态第十期", "2012珏山动态第十一期",
				"2012珏山动态第十二期", "2012珏山动态第十三期", "2012珏山动态第十四期", "2012珏山动态第十五期",
				"2012珏山动态第十六期", "2012珏山动态第十七期", "2012珏山动态第十八期", "2012珏山动态第十九期",
				"2012珏山动态第二十期", "2012珏山动态第二十一期", "2012珏山动态第二十二期",
				"2012珏山动态第二十三期", "2012珏山动态第二十四期", "2012珏山动态第二十五期",
				"2012珏山动态第二十六期", "2012珏山动态第二十七期", "2012珏山动态第二十八期",
				"2012珏山动态第二十九期", "2012珏山动态第三十期", "2012珏山动态第三十一期",
				"2012珏山动态第三十二期", "2012珏山动态第三十三期", "2012珏山动态第三十四期",
				"2012珏山动态第三十五期", "2012珏山动态第三十六期", "2012珏山动态第三十七期",
				"2012珏山动态第三十八期", "2012珏山动态第三十九期", "2012珏山动态第四十期",
				"2012珏山动态第四十一期", "2012珏山动态第四十二期", "2012珏山动态第四十三期",
				"2012珏山动态第四十四期", "2012珏山动态第四十五期", "2012珏山动态第四十六期",
				"2012珏山动态第四十七期", "2012珏山动态第四十八期", "2012珏山动态第四十九期",
				"2012珏山动态第五十期", "2012珏山动态第五十一期", "2012珏山动态第五十二期" };
		String[] names13 = { "第一期", "第二期", "第三期", "第四期", "第五期", "第六期", "第七期",
				"第八期", "第九期", "第十期", "第十一期", "第十二期", "第十三期", "第十四期", "第十五期",
				"第十六期", "第十七期", "第十八期", "第十九期", "第二十期", "第二十一期", "第二十二期",
				"第二十三期", "第二十四期", "第二十五期", "第二十六期", "第二十七期", "第二十八期", "第二十九期",
				"第三十期", "第三十一期", "第三十二期", "第三十三期", "第三十四期", "第三十五期", "第三十六期",
				"第三十七期", "第三十八期" };
		String[] names14 = { "第四期", "第五期", "第六期", "第七期", "第八期", "第九期", "第十期",
				"第十一期", "第十二期", "第十三期" };
		// printAllPapers(names,2012 + "");
		// printAllPapers(names13,2013 + "H:/备份/2014珏山动态/" "年");
		printAllPapers(names14, "H:/备份/2014珏山动态/");
		// printNewPage(names14,"H:/备份/2014珏山动态/");
	}

	public void printAllPapers(String[] names, String year) {

		for (int i = 0; i < names.length; i++) {
			String path = year + names[i];
			File file = new File(path);
			List picPaths = new ArrayList();
			if (file.isDirectory()) {
				String newPath = path + "/" + names[i];
				picPaths.add(newPath + "1.psd");
				picPaths.add(newPath + "2.psd");
				picPaths.add(newPath + "3.psd");
				picPaths.add(newPath + "4.psd");
			} else {
				if (!file.exists()) {
					picPaths.add(path + "1.psd");
					picPaths.add(path + "2.psd");
				}
			}
			// _Log.i("" + picPaths);
			FileUtil.checkFilesExists((String[]) picPaths
					.toArray(new String[picPaths.size()]));
			_Log.i("" + picPaths.get(0));

			NewsImage backwardImage = new NewsImage(0, "", "_rect_1");
			backwardImage.changeImage("" + picPaths.get(0));
			JSXController.getInstance().invoke("printJSX", "0");
			if (picPaths.size() > 2) {
				backwardImage.changeImage("" + picPaths.get(2));
				JSXController.getInstance().invoke("printJSX", "0");
			}

		}
	}

	public void card() {
		String[] array = { "毋 永", "城 区", "泽 州", "陵 川", "高 平", "沁 水", "阳 城",
				"佛 教", "道 教", "伊斯兰教", "天主教", "基督教" };
		for (int i = 0; i < array.length; i++) {
			NewsStyle _rect1 = new NewsStyle(-1, NewsType.RECT_1, "");
			_rect1.addStaticText(array[i]);
			_rect1.addStaticText(array[i]);
			_rect1.updateStaticTexts();
			JSXController.getInstance().invoke("printJSX", "0");
			// JSXController.getInstance().flush();
		}
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

	// private NewsBean genNewsContents() {
	// List<NewsBean> visitorsTrackNews =
	// articlesFromString(Constants.VISITORS_TRACK_LIBRARY_PATH);
	// List<NewsBean> scenicBlogsNews =
	// articlesFromString(Constants.SCENIC_BLOGS_LIBRARY_PATH);
	// List<NewsBean> scenicNewsNews =
	// articlesFromString(Constants.SCENIC_NEWS_LIBRARY_PATH);
	// List<NewsBean> travelLawsNews =
	// articlesFromString(Constants.TRAVEL_LAWS_LIBRARY_PATH);
	// int releaseNum = 3;
	// List<NewsArticle> articles = new ArrayList<>();
	// NewsBean nb = new NewsBean("_" + releaseNum, null, articles, releaseNum);
	// nb.pushArticles(
	// typeOfArticles(
	// articlesInReleaseNumber(visitorsTrackNews, releaseNum),
	// NewsType.VISITORS_TRACK), 3);
	// nb.pushArticles(
	// typeOfArticles(
	// articlesInReleaseNumber(scenicBlogsNews, releaseNum),
	// NewsType.SCENIC_BLOGS), 3);
	// nb.pushArticles(
	// typeOfArticles(
	// articlesInReleaseNumber(travelLawsNews, releaseNum),
	// NewsType.TRAVEL_LAWS), 4);
	// nb.pushArticles(
	// typeOfArticles(
	// articlesInReleaseNumber(scenicNewsNews, releaseNum),
	// NewsType.SCENIC_NEWS), 2);
	// return nb;
	// }

	private List<NewsArticle> typeOfArticles(List<NewsArticle> list,
			NewsType type) {
		for (NewsArticle n : list) {
			n.setType(type);
		}
		return list;
	}

	// private List<NewsArticle> articlesInReleaseNumber(List<NewsBean>
	// newsBean,
	// int releaseNum) {
	// List<NewsArticle> returnList = null;
	// for (NewsBean bean : newsBean) {
	// if (bean.getReleaseNumber() == releaseNum) {
	// returnList = bean.getArticles();
	// break;
	// }
	// }
	// return returnList;
	// }

	private NewsBeanArray articleStatues(NewsBeanArray articlesFromString) {
		// reFormNews(articlesFromString);
		List<String> names = new ArrayList<String>();
		List<String> dnames = new ArrayList<String>();

		for (NewsBean newBean : articlesFromString) {
			for (NewsArticle na : newBean.getArticles()) {
				_Log.i(na.toString());
				names.add(na.getAuthor());
				dnames.add(na.getDepartment());
			}
		}

		List<List<Integer>> result = NewsBean.repeatList(names);
		List<List<Integer>> department = NewsBean.repeatList(dnames);
		// _Log.i(result + "\n" + department);
		// NewsBean.traceRepeatList(result, names);
		NewsBean.traceRepeatList(department, dnames);
		return articlesFromString;
	}

	private NewsBeanArray articleStatues(String path) {
		NewsBeanArray articleStatues = articleStatues(articlesFromString(path));
		NewsBeanArray nNewsBeanArray = new NewsBeanArray();
		NewsBean newsBean = articleStatues.get(articleStatues.size() - 1);
		nNewsBeanArray.add(newsBean);
		articleStatues(nNewsBeanArray);
		return articleStatues;
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

	// public void continuedFromString() {
	// _str = FileUtil.readToString(Constants.TRAVEL_LAWS_LIBRARY_PATH);
	// List<String> lines = StringUtil.arrayToList(_str.split("\n"));
	// List<Integer> indexesOfEachVersions = articleIndexesByVersions(lines);
	// List<NewsBean> releases = new ArrayList<NewsBean>();
	// for (int i = 0; i < indexesOfEachVersions.size() - 1; i++) {
	// NewsBean newRelease = ensNews(lines, indexesOfEachVersions.get(i),
	// indexesOfEachVersions.get(i + 1),
	// StringUtil.intInString(lines.get(indexesOfEachVersions
	// .get(i))));
	// releases.add(newRelease);
	// }
	// NewsBean releaseUsing = releases.get(0);
	//
	// reboundArticle(releaseUsing);
	// OutputController.vBScriptPrint(releaseUsing);
	// }

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
		int bracketsType = 1;
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
			String getbracketsType = getbracketsType(line);
			if (!getbracketsType.equals("")) {
				bracketsType = Integer.parseInt(getbracketsType);
			}
			String date = getDate(line);
			title = title + getTitles(line);
			department = department + getDepartment(line);
			pick = pick + getPick(line);
			if (getbracketsType(line) != "" || getPage(line) != ""
					|| getImgs(line) != "" || getDate(line) != ""
					|| getType(line) != "" || getTitles(line) != ""
					|| getDepartment(line) != "" || getPick(line) != "") {
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
				title, urls, type, page, bracketsType);
	}

	// private void reboundArticle(NewsBean news) {
	// for (NewsArticle article : news.getArticles()) {
	// List<Point> points = new ArrayList<Point>();
	// Rectangle rect = new Rectangle();
	// int x = 0;
	// int y = 0;
	// float width = Constants.CONTENT_WIDTH;
	// float height = Constants.ARTICLE_HEIGHT_DEFAULT;
	// if (article.getPicsUrl() != null) {
	// List<Rectangle> subObjBounds = new ArrayList<Rectangle>();
	// float imageWidth = (float) (width * .39);
	// float imageHeight = 100;
	// float imageLasty = 0;
	// for (int i = 0; i < article.getPicsUrl().size(); i++) {
	// imageLasty = (y + i * (imageHeight + _ruler));
	// subObjBounds.add(new Rectangle(width - imageWidth,
	// imageLasty, imageWidth, imageHeight));
	// }
	// article.setSubObjBounds(subObjBounds);
	//
	// Point clipPoint = new Point((width - _ruler - imageWidth),
	// (imageLasty + imageHeight + _ruler));
	// rect = new Rectangle(x, y, width, height);
	// points.add(new Point(x, y));
	// points.add(new Point(x + clipPoint.x, y));
	// points.add(new Point(x + clipPoint.x, y + clipPoint.y));
	// points.add(new Point(x + width, y + clipPoint.y));
	// points.add(new Point(x + width, y + height));
	// points.add(new Point(x, y + height));
	// article.setPoints(points);
	// article.setBound(rect);
	//
	// } else {
	// rect = new Rectangle(x, y, Constants.CONTENT_WIDTH,
	// Constants.ARTICLE_HEIGHT_DEFAULT);
	// points.add(new Point(x, y));
	// points.add(new Point(x + width, y));
	// points.add(new Point(x + width, y + height));
	// points.add(new Point(x, y + height));
	// article.setPoints(points);
	// article.setBound(rect);
	// }
	// }
	// }

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

	private String getbracketsType(String str) {
		String tag = "<brackets_type>";
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
		return "摘自" + result;
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
