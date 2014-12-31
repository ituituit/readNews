package com.cheesemobile.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cheesemobile.parser.LayersInfoParser;
import com.cheesemobile.service.news.NewsController.NewsType;
import com.cheesemobile.util._Log;

public class NewsBean {
	private List<NewsArticle> articles;
	private int releaseVer;

	public int getReleaseVer() {
		return releaseVer;
	}

	public void setReleaseVer(int releaseVer) {
		this.releaseVer = releaseVer;
	}

	public void expandStatic() {
		NewsStyle _rect1 = new NewsStyle(-1, NewsType.RECT_1, "");
		List<NewsArticle> pageArticle = pageMatched(articles, 1);
		// //////////////////////////////////
		List<NewsStyle> styles = new ArrayList<>();
		for (int i = 0; i < pageArticle.size(); i++) {
			NewsStyle style = new NewsStyle(i, pageArticle.get(i).getType(),
					_rect1.getFullName());
			styles.add(style);
		}
		_rect1.addStaticObjs(styles);
		for (int i = 0; i < pageArticle.size(); i++) {
			NewsArticle articleList = pageArticle.get(i);
			String content = articleList.getContent();
			List<String> pics = articleList.getPicsUrl();
			NewsImage image = new NewsImage(0, pics.get(0), styles.get(i)
					.getFullName());
			image.toggleForeign();
			styles.get(i).addStaticObjs(image);
			image.changeImage(image.getPath());
		}
		for (int i = 0; i < pageArticle.size(); i++) {
			LayersInfoParser.getInstance().mergeLayer(
					styles.get(i).getFullName());
		}
	}

	public void expand() {
		NewsStyle _rect1 = new NewsStyle(-1, NewsType.RECT_1, "");
		NewsStyle _rect2 = new NewsStyle(-1, NewsType.RECT_2, "");
		NewsStyle _rect3 = new NewsStyle(-1, NewsType.RECT_3, "");
		NewsStyle _rect4 = new NewsStyle(-1, NewsType.RECT_4, "");
		NewsStyle[] pagesObj = { _rect1, _rect2, _rect3, _rect4 };
		for (int i = 0; i < pagesObj.length; i++) {
			List<NewsArticle> thisPage = pageMatched(articles, i + 1);
			if (thisPage.size() == 0) {
				continue;
			}
			List<List<NewsArticle>> groups = groupsMatched(thisPage);
			List<NewsStyle> styles = new ArrayList<>();
			int orderInd = 0;
			for (List<NewsArticle> articleList : groups) {
				NewsType type = articleList.get(0).getType();
				NewsStyle style = new NewsStyle(orderInd++, type,
						pagesObj[i].getFullName());
				styles.add(style);
			}
			pagesObj[i].addAll2(styles);
			// //////////////////////////////////
			int curInd = 0;
			for (List<NewsArticle> articleList : groups) {
				List<BoundNewsObject> member = new ArrayList<BoundNewsObject>();
				List<NewsStyle> memberGroup = new ArrayList<NewsStyle>();
				List<List<BoundNewsObject>> memberGroupChilds = new ArrayList<List<BoundNewsObject>>();
				// List<NewsText> textsObj = new ArrayList<NewsText>();
				List<NewsImage> images = new ArrayList<>();
				for (NewsArticle newsArticle : articleList) {
					String content = newsArticle.getContent();
					String contentTypes = newsArticle.getContentTypes();
					List<String> pics = newsArticle.getPicsUrl();
					if (pics.size() != 0) {
						NewsText artText = new NewsText(0, NewsType.TEXT,
								content, newsArticle.getTitle(), contentTypes);
						List<BoundNewsObject> memberGroupChildCell = new ArrayList<>();
						NewsStyle style = new NewsStyle(newsArticle.getOrder(),
								NewsType.GROUP, pagesObj[i].getFullName());
						_Log.i(style.getFullName());
						int picsInd = 0;
						for (String string : pics) {
							NewsImage image = new NewsImage(picsInd++, string,
									style.getFullName());
							if (artText.canDraw()) {
								image.toggleForeign();
							}
							memberGroupChildCell.add(image);
							images.add(image);
						}
						memberGroup.add(style);
						memberGroupChildCell.add(artText);
						memberGroupChilds.add(memberGroupChildCell);
					} else {
						NewsText artText = new NewsText(newsArticle.getOrder(),
								NewsType.TEXT, content, newsArticle.getTitle(),
								contentTypes);
						member.add(artText);
					}
				}
				member.addAll(memberGroup);
				styles.get(curInd++).addAll2(member);
				// ///////////////////////////
				int ind = 0;
				for (NewsStyle member1 : memberGroup) {
					member1.addAllSubObjects(memberGroupChilds.get(ind++));
				}
			}
		}
	}

	public NewsBean(List<NewsArticle> articles, int releaseVer) {
		super();
		this.articles = articles;
		this.releaseVer = releaseVer;
	}

	public int size() {
		return articles.size();
	}

	public List<List<NewsArticle>> groupsMatched(List<NewsArticle> s) {
		List<List<NewsArticle>> groups = new ArrayList<>();
		Set<NewsType> typeSet = new HashSet<>();
		for (NewsArticle list : s) {
			NewsType type = list.getType();
			typeSet.add(type);
		}
		for (NewsType newsType : typeSet) {
			List<NewsArticle> group = new ArrayList<NewsArticle>();
			for (int i = 0; i < s.size(); i++) {
				if (s.get(i).getType().equals(newsType)) {
					group.add(s.get(i));
				}
			}
			groups.add(group);
		}
		return groups;
	}

	private List<NewsArticle> pageMatched(List<NewsArticle> s, int i) {
		List<NewsArticle> reurnVal = new ArrayList<NewsArticle>();
		for (NewsArticle newsArticle : s) {
			if (newsArticle.getPage() == i) {
				reurnVal.add(newsArticle);
			}
		}
		return reurnVal;
	}

	public NewsType getType(int i) {
		return articles.get(i).getType();
	}

	public String getContent(int i) {
		return articles.get(i).getContent();
	}

	public String[] getImgPaths(int i) {
		List<String> picsUrl = articles.get(i).getPicsUrl();
		return picsUrl.toArray(new String[picsUrl.size()]);
	}

	public void setAll(int page, NewsType type) {
		for (NewsArticle art : articles) {
			if (art.getPage() == -1) {
				art.setPage(page);
			}
			if (art.getType() == null) {
				art.setType(type);
			}
		}
		List<List<NewsArticle>> groups = groupsMatched(articles);
		for (List<NewsArticle> list : groups) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setOrder(i);
			}
		}
	}

	public int getArticlesSize() {
		if (this == null || this.articles == null) {
			return 0;
		}
		return articles.size();
	}

	public void pushArticles(List<NewsArticle> list, int page) {
		int reorderStart = 0;
		for (NewsArticle a : articles) {
			if (a.getPage() == page) {
				reorderStart++;
			}
		}
		for (NewsArticle a : list) {
			a.setPage(page);
			a.setOrder(reorderStart++);
		}
		articles.addAll(list);
	}

	public List<NewsArticle> getArticles() {
		return articles;
	}

	public List<NewsArticle> getArticles(int page) {
		List<NewsArticle> list = new ArrayList<NewsArticle>();
		for (NewsArticle a : articles) {
			if (a.getPage() == page) {
				list.add(a);
			}
		}
		return list;
	}

	public void setArticles(List<NewsArticle> articles) {
		this.articles = articles;
	}

	@Override
	public String toString() {
		return articles.toString();

	}

	public void status() {
		List<String> titles = new ArrayList<String>();
		List<String> contents = new ArrayList<String>();
		for (NewsArticle na : this.getArticles()) {
			titles.add(na.getTitle());
			contents.add(na.getContent());
		}

		Set<String> titleTypes = group(titles);
		List<List<Integer>> result = repeatList(titles);
		// List<List<Integer>> department = repeatList(contents);
		for (int i = 0; i < result.size(); i++) {
			List<Integer> list = result.get(i);
			System.out
					.print(titleTypes.toArray(new String[titleTypes.size()])[i]
							+ ":");
			for (Integer integer : list) {
				System.out.print(getArticles().get(integer).getContent() + ",");
			}
			System.out.print("\n");
		}

	}

	public static Set<String> group(List<String> list) {
		Set<String> mySet = new HashSet<String>();
		for (String str : list) {
			mySet.add(str);
		}
		return mySet;
	}

	// ͳ��ÿ������Ͷ����
	public static List<List<Integer>> repeatList(List<String> list) {
		List<List<Integer>> repeatList = new ArrayList<List<Integer>>();

		Set<String> mySet = group(list);
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

	// ��ʾÿ������Ͷ����
	public static List<ArticleStatus>  traceRepeatList(List<List<Integer>> department,
			List<String> dnames) {
		int count = 0;
		List<ArticleStatus> list = new ArrayList<ArticleStatus>();
		for (int i = 0; i < department.size(); i++) {
			int nameI = department.get(i).get(0);
			int numDepName = department.get(i).size();
			String depName = dnames.get(nameI);
			ArticleStatus as = new ArticleStatus();
			as.setAcceptCount(numDepName);
			as.setArticleCount(numDepName);
			as.setDepartmentName(depName);
//			_Log.i(depName + "投稿" + numDepName  +"篇," + "采用" +numDepName+ "篇;");
			_Log.i(as.toString());
			count += numDepName;
			list.add(as);
		}
		_Log.i("共" + count + "篇");
		return list;
	}

}
