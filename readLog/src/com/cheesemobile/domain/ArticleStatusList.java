package com.cheesemobile.domain;

import java.util.ArrayList;
import java.util.Collection;

import com.cheesemobile.util._Log;

public class ArticleStatusList extends ArrayList<ArticleStatus> {
	
	public ArticleStatusList(Collection<? extends ArticleStatus> c) {
		super(c);
	}

	@Override
	public String toString() {
		for (int i = 0; i < this.size(); i++) {
			_Log.i(this.get(i).toString());
		}
		return "";
	}
	
	public void minus(ArticleStatusList minus) {
		for (int i = 0; i < this.size() - 1; i++) {
			for (ArticleStatus articleStatus : minus) {
				if (articleStatus.getDepartmentName().equals(
						this.get(i).getDepartmentName())) {
					this.get(i).setAcceptCount(
							this.get(i).getAcceptCount()
									- articleStatus.getAcceptCount());
					break;
				}
			}
		}
	}
}
