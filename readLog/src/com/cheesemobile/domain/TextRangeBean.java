package com.cheesemobile.domain;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import com.cheesemobile.util._Log;

public class TextRangeBean extends ArrayList<TextRange> {
	private StringBuilder _text;
	private List<TextRange> trs = new ArrayList<>();

	public TextRangeBean(String content) {
		_text = new StringBuilder();

		if(content == null){
			return ;
		}
		Document document = Jsoup.parse(content);
		Element child = document.child(0).child(1);
//		_Log.i("" +child.text());
//		_text.append(child.ownText());
		for (Node node : child.childNodes()) {
			String tex = "";
			if(Element.class == node.getClass()){
				tex = ((Element)node).textNodes().get(0).getWholeText();
			}else if(TextNode.class == node.getClass()){
				tex = ((TextNode)node).getWholeText();
			}
//			_Log.i(node.toString() + "");
			int s = _text.length();
			_text.append(tex);
			int e = _text.length();
//			if (trs.size() > 1) {
//				TextRange lastRange = trs.get(trs.size() - 1);
//				s = lastRange.getStart();
//				e = lastRange.getEnd();
//			}
			if(Element.class == node.getClass()){
				trs.add(new TextRange(s, e, node.nodeName()));
			}else{
//				trs.add(new TextRange(s, e, "正文"));
			}
		}

		_Log.i(_text + "");
		_Log.i(trs + "");

	}

	public String textContentTypes() {
		return trs.toString();
	}

	public String textContent() {
		return _text.toString();
	}
}

class TextRange {
	private int start;
	private int end;
	private String type;

	public TextRange(int start, int end, String type) {
		super();
		this.start = start;
		this.end = end;
		this.type = type;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	@Override
	public String toString() {
		return "[" + start + "," + end + "," + type + "]";
	}

}