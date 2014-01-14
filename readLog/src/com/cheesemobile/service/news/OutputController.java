package com.cheesemobile.service.news;

import com.cheesemobile.domain.NewsArticle;
import com.cheesemobile.domain.NewsBean;
import com.cheesemobile.domain.Point;
import com.cheesemobile.domain.Rectangle;

public class OutputController {
	public static void vBScriptPrint(NewsBean project) {
		// print1(project);

		// System.out.print(new NewsLabel(1, "2014Äê1ÔÂ1ÈÕ"));
	}

	private static void pirntImagesAttribute(NewsBean project) {
		System.out.print("[");
		StringBuilder sb = new StringBuilder();
		for (NewsArticle article : project.getArticles()) {
			if (article == null) {
				continue;
			}
			if (article.getPicsUrl() == null) {
				continue;
			}
			// for (String p : article.getPicsUrl()) {
			// sb.append("\"" + p + "\"" + ",");
			// }
			for (Rectangle r : article.getSubObjBounds()) {
				sb.append(r + ",");
			}
		}

		sb.replace(sb.length() - 1, sb.length(), "");
		String str = sb.toString().replaceAll("\n", "");
		System.out.print(str.toString());
		System.out.print("]");
	}

	private static void pirntTextPaths(NewsBean project) {
		System.out.print("[");
		for (NewsArticle article : project.getArticles()) {
			if (article == null) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for (Point p : article.getPoints()) {
				sb.append("[" + p.x + "," + p.y + "],");
			}
			sb.replace(sb.length() - 1, sb.length(), "");
			sb.append("],");
			System.out.print(sb.toString());
		}
		System.out.print("]");
	}

	private static void print1(NewsBean project) {
		System.out.print("[");
		for (NewsArticle article : project.getArticles()) {
			if (article == null) {
				continue;
			}
			System.out.print("\"\"\"" + article.getTitle() + "\"\"\"" + ",");
		}
		System.out.print("]");
	}

	public static void printAboutDetail() {

	}
}
