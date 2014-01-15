package com.cheesemobile.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * ��ϵͳ��ص�һЩ���ù��߷���.
 * 
 * @author lvbogun
 * @version 1.0.0
 */
public class SystemTool {

	public static Process runCommand(String destDir) throws IOException {
		String[] cmd = null;
		if (getOSName().startsWith("windows")) {
			String[] cmdt = {"cmd" ,"/c",destDir};
			cmd = cmdt;
		} else {
			String[] cmdt = { "open",destDir 
			// "/Applications/Adobe ExtendScript Toolkit CC/ExtendScript Toolkit.app",
			};
			cmd = cmdt;
		}
		return Runtime.getRuntime().exec(cmd);
	}

	/**
	 * ��ȡ��ǰ����ϵͳ����. return ����ϵͳ���� ����:windows xp,linux ��.
	 */
	public static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}

	/**
	 * ��ȡunix������mac��ַ. ��windows��ϵͳĬ�ϵ��ñ�������ȡ. ���������ϵͳ����������µ�ȡmac��ַ����.
	 * 
	 * @return mac��ַ
	 */
	public static String getUnixMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			// linux�µ����һ��ȡeth0��Ϊ����������
			process = Runtime.getRuntime().exec("ifconfig eth0");
			// ��ʾ��Ϣ�а�����mac��ַ��Ϣ
			bufferedReader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				// Ѱ�ұ�ʾ�ַ���[hwaddr]
				index = line.toLowerCase().indexOf("hwaddr");
				if (index >= 0) {// �ҵ���
					// ȡ��mac��ַ��ȥ��2�߿ո�
					mac = line.substring(index + "hwaddr".length() + 1).trim();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}
		return mac;
	}

	/**
	 * ��ȡwidnows������mac��ַ.
	 * 
	 * @return mac��ַ
	 */
	public static String getWindowsMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			// windows�µ������ʾ��Ϣ�а�����mac��ַ��Ϣ
			process = Runtime.getRuntime().exec("ipconfig /all");
			bufferedReader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
				// Ѱ�ұ�ʾ�ַ���[physical
				index = line.toLowerCase().indexOf("physical address");

				if (index >= 0) {// �ҵ���
					index = line.indexOf(":");// Ѱ��":"��λ��
					if (index >= 0) {
						System.out.println(mac);
						// ȡ��mac��ַ��ȥ��2�߿ո�
						mac = line.substring(index + 1).trim();
					}
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	/**
	 * windows 7 ר�� ��ȡMAC��ַ
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getMACAddress() throws Exception {

		// ��ȡ����IP����
		InetAddress ia = InetAddress.getLocalHost();
		// �������ӿڶ��󣨼������������õ�mac��ַ��mac��ַ������һ��byte�����С�
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

		// ��������ǰ�mac��ַƴװ��String
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < mac.length; i++) {
			if (i != 0) {
				sb.append("-");
			}
			// mac[i] & 0xFF ��Ϊ�˰�byteת��Ϊ������
			String s = Integer.toHexString(mac[i] & 0xFF);
			sb.append(s.length() == 1 ? 0 + s : s);
		}

		// ���ַ�������Сд��ĸ��Ϊ��д��Ϊ�����mac��ַ������
		return sb.toString().toUpperCase();
	}

	/**
	 * �����õ�main����.
	 * 
	 * @param argc
	 *            ���в���.
	 * @throws Exception
	 */
	public static void main(String[] argc) throws Exception {
		String os = getOSName();
		System.out.println(os);
		if (os.equals("windows 7")) {
			String mac = getMACAddress();
			System.out.println(mac);
		} else if (os.startsWith("windows")) {
			// ������windows
			String mac = getWindowsMACAddress();
			System.out.println(mac);
		} else {
			// �����Ƿ�windowsϵͳ һ�����unix
			String mac = getUnixMACAddress();
			System.out.println(mac);
		}
	}
}