package com.cheesemobile.util;

public class _Log {
	private static int Level = 5;

	public static void e(String str){
		if(Level >= 3){
			System.out.println(str);
		}
	}
	public static void i(String str) {
		if (Level >= 4) {
			System.out.println(str);
		}
	}
	public static void d(String str) {
		if (Level >= 5) {
			System.out.println(str);
		}
	}
}
