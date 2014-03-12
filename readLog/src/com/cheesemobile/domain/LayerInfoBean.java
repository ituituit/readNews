package com.cheesemobile.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cheesemobile.util._Log;

public class LayerInfoBean {
	private String fullName;
	private int nameStartInd = -1;
	private int stack;
	private Rectangle rect;

	public void setParent(String parent) {
		String sb = getName();
		if(parent.endsWith("/")){
			parent = parent.substring(0,parent.length() - 1);
		}
		if(sb.startsWith("/")){
			sb = sb.substring(1);
		}
		this.fullName = parent + "/" + sb;
	}

	public LayerInfoBean dumplicate(){
		LayerInfoBean lib = new LayerInfoBean();
		lib.setFullName(fullName);
		lib.setNameStartInd(nameStartInd);
		lib.setStack(stack);
		lib.setRect(rect);
		return lib;
	}
	public String getParent(){
		return minusLast(fullName,getName());
	}
	
	public static String minusLast(String fullStr,String minus){
		String substring = fullStr.substring(0,fullStr.lastIndexOf(minus));
		if(substring.endsWith("/")){
			substring = substring.substring(0,substring.length() - 1);
		}
		return substring;
	}
	
	public String getName(){
		List<String> nameStack = getNamesList();
		StringBuilder sb = new StringBuilder();
		if(nameStartInd < 0){
			nameStartInd += nameStack.size();
		}
		for (int i = nameStartInd; i < nameStack.size(); i++) {
			if(sb.length() != 0){
				sb.append("/");
			}
			sb.append(nameStack.get(i));
		}
		return sb.toString();
	}
	
	public List<String> getNamesList(){
		return getNamesList(fullName);
	}
	
	public static List<String> getNamesList(String name){
		if(name.indexOf("/") == 0){
			name = name.substring(1);
		}
		return Arrays.asList(name.split("/"));
	}
	public boolean compareNameSetInd(String fn){
		if(fn.indexOf("/") == 0){
			fn = fn.substring(1);
		}
		List<String> namesList = getNamesList();

		List<String> foreignNames = Arrays.asList(fn.split("/"));
		
		if(namesList.size() < foreignNames.size()){
			return false;
		}
		List<Integer> firstInds = new ArrayList<>();
		for (int i = 0; i < namesList.size(); i++) {
			if(namesList.get(i).equals(foreignNames.get(0))){
				firstInds.add(i);
			}
		}
		for (int i = 0; i < firstInds.size(); i++) {
			for(int j = 0; j < foreignNames.size(); j++){
				if(firstInds.get(i) + j == namesList.size()){
					return false;
				}
				if(foreignNames.get(j).equals(namesList.get(firstInds.get(i) + j))){
				}else{
					break;
				}
				if(j == foreignNames.size() - 1){
					nameStartInd = j + firstInds.get(i) + 1;
					return true;
				}
			}
		}
		return false;
	}
	
	private void setNameStartInd(int nameStackUsed) {
		this.nameStartInd = nameStackUsed;
	}

//	public String getName() {
//		return getNamesList(nameStartInd);
//	}

	public void setName(String name) {
		setFullName(getParent() + name);
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getStack() {
		return stack;
	}

	public void setStack(int stack) {
		this.stack = stack;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	@Override
	public String toString() {
		return "LayerInfoBean [name=" + getName() + ", fullName=" + fullName
				+ ", stack=" + stack + ", rect=" + rect + "]";
	}
	
}
