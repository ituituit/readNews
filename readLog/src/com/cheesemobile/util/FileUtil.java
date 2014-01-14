package com.cheesemobile.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.GZIPInputStream;

public class FileUtil {

	public static void transferFile(File file) {
		try {
			BufferedInputStream bin = new BufferedInputStream(
					new FileInputStream(file));
			byte[] buff = new byte[(int) file.length()];
			bin.read(buff);
			FileOutputStream fout = new FileOutputStream(file);
			String str = new String(buff);
			str.replaceAll("\\+", "%2B");

			String line_changed = str;
			fout.write((line_changed).getBytes());

			fout.flush();
			fout.close();
			bin.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static void write(String filePath, String content) {
		BufferedWriter bw = null;
		
		try {
			// 鏍规嵁鏂囦欢璺緞鍒涘缓缂撳啿杈撳嚭娴�
			bw = new BufferedWriter(new FileWriter(filePath));
			// 灏嗗唴�?�瑰啓鍏ユ枃浠朵�?
			bw.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 鍏抽棴娴�?
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					bw = null;
				}
			}
		}
	}
	
	public static void replacePlusSign() {
		try {
			FileReader reader = new FileReader(new File("d:\\in"));
			FileWriter writer = new FileWriter(new File("d:\\out"));
			int c = -1;
			while ((c = reader.read()) != -1) {
				if (c == '+') {
					System.out.println(c);
					writer.write("\\2B");
				} else {
					writer.write(c);
				}
			}
			reader.close();
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String appendMethodA(String fileName, String content1,
			String content2) {
		try {
			byte[] bytes1 = content1.getBytes();
			byte[] bytes2 = content2.getBytes();
			System.out.println("appendMethodA_pos:" + bytes1.length);
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(fileName));
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(fileName + ".upl"));
			int len = 0;
			byte[] buffer = new byte[1024 * 100];
			bos.write(buffer, 0, bytes1.length);
			while ((len = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			bos.flush();
			bos.close();
			bis.close();

			RandomAccessFile randomFile = new RandomAccessFile(fileName
					+ ".upl", "rw");
			long fileLength = randomFile.length();
			randomFile.seek(0);
			for (int i = 0; i < bytes1.length; i++) {
				randomFile.write(bytes1[i]);
			}
			randomFile.seek(fileLength);
			for (int i = 0; i < bytes2.length; i++) {
				randomFile.write(bytes2[i]);
			}
			randomFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		File infile = new File(fileName);
		File outfile = new File(fileName + ".upl");
		infile.delete();
		outfile.renameTo(infile);
		return fileName + ".upl";
	}

	private static String changePlus(String line) {
		int currenti = 0;
		while (currenti < line.length()) {
			if (line.charAt(currenti) == '+') {
				String start = line.substring(0, currenti);
				String end = line.substring(currenti + 1);
				StringBuilder sb = new StringBuilder();
				sb.append(start);
				sb.append("%2B");
				sb.append(end);
				line = sb.toString();
				currenti += 2;
			}
			currenti++;
		}
		System.out.println(line);
		return line;
	}

	public static void replacePlus(File file) {
		FileOutputStream out;
		try {
			out = new FileOutputStream(file.getAbsolutePath() + ".rp");

			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			BufferedOutputStream bos = new BufferedOutputStream(out);
			byte[] buffer = new byte[3 * 512];
			int len = 0;
			while ((len = bis.read(buffer)) != -1) {
				byte[] buffers = new byte[len];
				for (int i = 0; i < buffers.length; i++) {
					buffers[i] = buffer[i];
				}
				char[] cs = getChars(buffers);
				char[] per2b = { '%', '2', 'B' };
				for (int j = 0; j < cs.length; j++) {
					if (cs[j] == '+') {
						System.out.println("'");
					}
				}
				out.write(getBytes(cs));

			}
			bos.flush();
			bos.close();
			bis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static char[] appendChar(char[] chars, int index, char[] rep) {
		char[] c = new char[chars.length + rep.length];
		for (int i = 0; i < c.length; i++) {
			if (i < index) {
				c[i] = chars[i];
			} else if ((i - index) < rep.length) {
				c[i] = rep[i - index];
			} else {
				c[i] = chars[i - rep.length];
			}
		}
		return c;
	}

	public static String readToString(String tmpFile) {
		try {
			StringBuffer sbFile = new StringBuffer();
			FileReader in = new FileReader(tmpFile);
			char[] buffer = new char[4096];
			int len;
			sbFile = new StringBuffer();
			while ((len = in.read(buffer)) != -1) {
				String s = new String(buffer, 0, len);
				sbFile.append(s);
			}
			in.close();
			return sbFile.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void stringToFile(String tmpFile, String str) {
		try {
			StringBuffer sbFile;
			FileWriter out;
			out = new FileWriter(tmpFile);
			out.write(str);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void appendToFile(String tmpFile, String str) {
		try {
			FileWriter out;
			RandomAccessFile randomFile = new RandomAccessFile(tmpFile, "rw");
			randomFile.seek(randomFile.length());
			randomFile.write(str.getBytes());
			randomFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void deleteFile(File oldPath) {
		if (oldPath.isDirectory()) {
			System.out.println(oldPath + "is dir");
			File[] files = oldPath.listFiles();
			for (File file : files) {
				deleteFile(file);
			}
		} else {
			oldPath.delete();
		}
	}

	public static void copyFile(String from, String to) {
		try {
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(new File(from)));
			System.out.println("bytes:" + in.available());
			FileOutputStream out = new FileOutputStream(new File(to));
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static void copyFile(InputStream instream, String to) {
		try {
			BufferedInputStream in = new BufferedInputStream(instream);

			FileOutputStream out = new FileOutputStream(new File(to));
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static byte[] getResource(String path) {
		JarFile jarFile;
		try {
			jarFile = new JarFile(URLDecoder.decode(FileUtil.class
					.getProtectionDomain().getCodeSource().getLocation()
					.getFile()));
			JarEntry entry = jarFile.getJarEntry(path);
			byte[] data = new byte[(int) entry.getSize()];
			BufferedInputStream in = new BufferedInputStream(
					jarFile.getInputStream(entry));
			in.read(data);
			in.close();
			return data;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] getBytes(char[] chars) {
		Charset cs = Charset.forName("UTF-8");
		CharBuffer cb = CharBuffer.allocate(chars.length);
		cb.put(chars);
		cb.flip();
		ByteBuffer bb = cs.encode(cb);

		return bb.array();

	}

	private static char[] getChars(byte[] bytes) {
		Charset cs = Charset.forName("UTF-8");
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		bb.flip();
		CharBuffer cb = cs.decode(bb);
		return cb.array();
	}

	public static List<String> unGzipAll(String path) {
		List<String> unzipedFileList = new ArrayList<String>();
		File folder = new File(path);
		if (folder.isDirectory()) {
			File[] fList = folder.listFiles();
			if (null != fList && fList.length > 0) {
				for (int i = 0; i < fList.length; i++) {
					if (fList[i].isFile() && isType(fList[i], ".gz")) {
						unzipedFileList.add(unGzip(fList[i].getAbsolutePath()));
					}
				}
			}
		} else {
			unzipedFileList.add(unGzip(path));
		}
		return unzipedFileList;
	}

	public static List<String> listFolderFiles(String path, String type) {
		List<String> fileList = new ArrayList<String>();
		File folder = new File(path);
		File[] fList = folder.listFiles();
		if (null != fList && fList.length > 0) {
			for (int i = 0; i < fList.length; i++) {
				if (!isType(fList[i], ".gz") && isType(fList[i], type)) {
					fileList.add(fList[i].getAbsolutePath());
				}
			}
		}
		return fileList;
	}

	public static String fileName(String path){
		int beginIndex = path.lastIndexOf("\\");
		int endIndex = path.lastIndexOf(".");
		return path.substring(beginIndex + 1, endIndex);
	}
	private static boolean isType(File file, String type) {
		String path = file.getAbsolutePath();
		// if(path.lastIndexOf(".") != -1 &&
		// path.substring(path.lastIndexOf(".")).equals(type)){
		// return true;
		// }
		while (path.lastIndexOf(".") != -1) {
			String lastDot = path.substring(path.lastIndexOf("."));
			if (lastDot.equals(type)) {
				return true;
			}
			path = path.substring(0,path.lastIndexOf("."));
		}
		return false;
	}

	public static String unGzip(String zipPath) {
		try {
			// Open the compressed file
			String inFilename = zipPath;
			GZIPInputStream in = new GZIPInputStream(new FileInputStream(
					inFilename));

			// Open the output file
			String outFilename = zipPath.substring(0, zipPath.lastIndexOf("."));
			File file = new File(outFilename);
			OutputStream out = new FileOutputStream(outFilename);

			// Transfer bytes from the compressed file to the output file
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}

			// Close the file and stream
			in.close();
			out.close();
			return outFilename;
		} catch (IOException e) {
		}
		return null;
	}

}
