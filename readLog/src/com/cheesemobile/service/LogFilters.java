package com.cheesemobile.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cheesemobile.util.Log4NetEventUtils;
import com.cheesemobile.util.StringUtil;
import com.cheesemobile.util._Log;

public class LogFilters {
	private Element eventData;
	private static LogFilters instance = null;
	private static String hostname;
	private static Date startDate;
	private static Date endDate;
	public static int caseInd;
	private static int logMatched;// 1.match;0.noMatch;-1.noCurrentCase
	private static ArrayList<Map<String, String>> maps = new ArrayList<Map<String, String>>();

	private LogFilters() {
	}

	public static LogFilters getInstance(int ind) {
		if (instance == null) {
			instance = new LogFilters();
			
		}
		caseInd = ind;
		logMatched = -1;
		hostname = StringUtil.getHostName();
		startDate = TestInfos.startDate.get(ind);
		endDate = TestInfos.endDate.get(ind);
		return instance;
	}

	public void setData(Element data) {
		this.eventData = data;
	}

	public String logMatched() {
		if (logMatched == 1 || logMatched == -1) {
			return null;
		}
		return " Log Not Match";
	}

	public boolean matchLogErrors() {
		String[] caseNames = TestInfos.caseNames;

		// System.out.println("213321" + caseInd);
		try {
			// _Log.i(caseInd + "" + caseNames[caseInd]);
			_Log.d("will invoke;" + caseNames[caseInd]);
			Method medhod = this.getClass().getMethod(caseNames[caseInd]);
			boolean match = (Boolean) medhod.invoke(this);
			if (match) {
				_Log.i("match" + caseNames[caseInd]);
				logMatched = 1;
			}

		} catch (Exception e) {
			// e.printStackTrace();
			_Log.d("no function for LogErrors:" + caseNames[caseInd]);
		}
		return true;
	}

	private boolean mapsMatch(Map<String, String>... map) {
		if (logMatched == -1) {
			logMatched = 0;
			maps.clear();
			for (int i = 0; i < map.length; i++) {
				maps.add(map[i]);
			}
		}
		for (int i = 0; i < maps.size(); i++) {
			Map<String, String> mapInList = maps.get(i);
			if (Log4NetEventUtils.mapsMatch(eventData, mapInList)) {
				maps.remove(i);
			}
		}
		System.out.println("remains:" + maps.size());
		if (maps.size() == 0) {
			return true;
		}
		return false;
	}

	public boolean filterLevels() {// (int... levels) {
		String[] levelsDescSpec = new String[Constants.LEVELS_USEING.length];
		for (int j = 0; j < Constants.LEVELS_USEING.length; j++) {
			levelsDescSpec[j] = Constants.LEVELS_USEING[j];
		}

		if (!StringUtil.strInStrs(levelsDescSpec, eventData.attr("level"))) {
			return false;
		}
		return true;
	}

	public boolean filterHostName() {// (String hostname) {
		Elements prop = null;
		if ((prop = getPropertiesData()) == null) {
			return false;
		}
		String hostName = "";
		for (int i = 0; i < prop.size(); i++) {
			if (prop.get(i).attr("name").equals("log4net:HostName")) {
				hostName = prop.get(i).attr("value");
			}
		}
		if (hostname != ""
				&& !(hostName.contains(hostname) || hostname.contains(hostName))) {
			return false;
		}
		return true;
	}

	public boolean filterTime() {// Date startDate, Date endDate) {
		Elements prop = null;
		if ((prop = getPropertiesData()) == null) {
			return false;
		}

		String timeZone = "0";
		for (int i = 0; i < prop.size(); i++) {
			if (prop.get(i).attr("name").equals("UtcOffset")) {
				timeZone = prop.get(i).attr("value");
			}
		}
		String timestamp = eventData.attr("timestamp");
		if (timestamp.length() == 0) {
			return false;
		}
		Date date = StringUtil.StringToDate(timestamp + "," + timeZone,
				StringUtil.FROM_LIVE);
		// System.out.println(timestamp + "," + date.toGMTString() + "," +
		// startDate.toGMTString()
		// + "," + endDate.toGMTString());
		if (null != startDate && null != endDate) {
			if (!(date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0)) {
				return false;
			}
		} else if (null != startDate) {
			if (!(date.compareTo(startDate) >= 0)) {
				return false;
			}
		}
		_Log.d("filterTime retrun true");
		return true;
	}

	private Elements getPropertiesData() {
		Elements properties = eventData.getElementsByTag("log4net:properties");
		if (properties.size() == 0) {
			return null;
		}
		Elements datas = properties.get(0).getElementsByTag("log4net:data");
		return datas;
	}

	public boolean _login_as_L4_user() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Signed into System Manager ("
				+ Constants.patternWord + Constants.patternBracketIp + ")");
		map.put("UserName", "3vr");
		map.put("SourceIP", Constants.patternBracketIp);

		map.put("Action", "Sign In");
		return mapsMatch(map);
	}

	public boolean _login_as_normal_user() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Signed into " + Constants.patternWord
				+ Constants.patternIp);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);

		map.put("Action", "Sign In");
		return mapsMatch(map);
	}

	public boolean _Health_Alert_Generated_And_Listed() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Removed appliance '" + Constants.patternWord + " "
				+ Constants.patternBracketIp + "' from enterprise "
				+ Constants.patternIp);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);

		map.put("Action", "Remove Appliance");
		return mapsMatch(map);
	}
	
	public boolean _Alert_Email_Configaration_and_Validation() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Alert Email: Subject:  3VR Alert from '" + Constants.patternWord + " " + Constants.patternMarksIp
				+ "': '" + Constants.patternWord +"' "
				+ " Body: " + Constants.patternWord
				+ " Time: " + Constants.ptnDate + " " + Constants.ptnTime
				+ "Server: " +  Constants.patternWord + " " + Constants.patternBracketIp
				+ "Severity: "  + Constants.patternWord
				+ "Description: Removed appliance " 
                + Constants.patternWord + " " + Constants.patternMarksIp
                + ". The 3VR enterprise now has " + Constants.patternWord
                + " appliances. (SN: " +  Constants.patternWord + ")"
				);
		
				
		return mapsMatch(map);
	}
	
	
	public boolean _Acknowledge_Single_and_Multiple_Alerts() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Acknoledged the following health alert: " + "Id "+ Constants.patternWord 
				+ " on appliance " + Constants.patternWord + " " + Constants.patternBracketIp
				+ ": 'Added " + Constants.patternWord + " cameras. The 3VR system now has "
				+ Constants.patternWord
				+ " cameras. (SN: " +  Constants.patternWord + ")'"
				);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Acknowledge Health Alert");
		
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Acknoledged the following health alerts: " +  "Id "+ Constants.patternWord 
				+ " on appliance " + Constants.patternWord + " " + Constants.patternBracketIp
				+ ": 'Added " + Constants.patternWord + " cameras. The 3VR system now has "
				+ Constants.patternWord
				+ " cameras. (SN: " +  Constants.patternWord + ")'"
				
				+ " Id "+ Constants.patternWord 
				+ " on appliance " + Constants.patternWord + " " + Constants.patternBracketIp
				+ ": 'Added " + Constants.patternWord + " cameras. The 3VR system now has "
				+ Constants.patternWord
				+ " cameras. (SN: " +  Constants.patternWord + ")'"
				
                + " Id "+ Constants.patternWord 
                + " on appliance " + Constants.patternWord + " " + Constants.patternBracketIp
                + ": 'Added appliance " + Constants.patternWord + " " + Constants.patternBracketIp
                +  ". The " + Constants.patternWord +" now has " + Constants.patternWord
                + "appliances. (SN: " +  Constants.patternWord + ")'"
                
                + " Id "+ Constants.patternWord 
                + " on appliance " + Constants.patternWord + " " + Constants.patternBracketIp
                + ": 'Resolved on " + Constants.ptnDate + " at " + Constants.ptnTime 
				
				);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Acknowledge Health Alert");
		
		
		return mapsMatch(map,map2);
	}

	public boolean _Disable_and_Enable_Health_Alerts() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Removed appliance '" + Constants.patternWord + " "
				+ Constants.patternBracketIp + "' from enterprise "
				+ Constants.patternIp);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);

		map.put("Action", "Remove Appliance");
		
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Successfully added new appliance '" + Constants.patternWord
				+ Constants.patternBracketIp + "' to enterprise "
				+ Constants.patternIp);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);

		map2.put("Action", "Add Appliance");
		
		return mapsMatch(map,map2);
	}
	
	
	public boolean _Peak_Hours_SetUp() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Button " + Constants.patternWord + " clicked on "
				+ Constants.patternWord + " ("
				+ Constants.patternWord + ") in " + Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);

		map.put("Action", "Button Click");			
		
		return mapsMatch(map);
	   }
	
	public boolean _Video_Playing_on_Peak_Off_Peak_Hours() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", Constants.patternWord + " peak changed from " + Constants.patternWord
				+ " to " + Constants.patternWord
				+ " and off-peak changed from " + Constants.patternWord
				+ " to " + Constants.patternWord + " on server '" + Constants.patternIp + "'."
				);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Change Scheduled Value");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", Constants.patternWord + " peak changed from " + Constants.patternWord
				+ " to " + Constants.patternWord
				+ " and off-peak changed from " + Constants.patternWord
				+ " to " + Constants.patternWord + " on server '" + Constants.patternIp + "'."
				);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Change Scheduled Value");
		return mapsMatch(map,map2);
	}
	
	public boolean _Rename_Enterprise_and_Core_Display_Name() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Server display name changed from " + Constants.patternWord
				+ " to " + Constants.patternWord
				+ " on server '" + Constants.patternIp + "'."
				);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Server Display Name");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Server display name changed from " + Constants.patternWord
				+ " to " + Constants.patternWord
				+ " on server '" + Constants.patternIp + "'."
				);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Server Display Name");
		return mapsMatch(map,map2);
	}	
	
	public boolean _System_Storage_Option_Setup() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Value changed from " + Constants.patternWord
				+ " to " + Constants.patternWord
				+ " on server '" + Constants.patternIp + "'."
				);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "SettingValue 'Default Storage Time for Custom Event Types' changed");  // in the log info both are "" for the action contents 
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Value changed from " + Constants.patternWord
				+ " to " + Constants.patternWord
				+ " on server '" + Constants.patternIp + "'."
				);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "SettingValue 'Default Video Export Extension' changed");
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message", "Value changed from " + Constants.patternWord
				+ " to " + Constants.patternWord
				+ " on server '" + Constants.patternIp + "'."
				);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "SettingValue 'Minimum Days of Video' changed");
		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message", "Value changed from " + Constants.patternWord
				+ " to " + Constants.patternWord
				+ " on server '" + Constants.patternIp + "'."
				);
		map4.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "SettingValue 'Normalize Sample Aspect Ratio for Exported Video' changed");
		Map<String, String> map5 = new TreeMap<String, String>();
		map5.put("log4net:message", "Value changed from " + Constants.patternWord
				+ " to " + Constants.patternWord
				+ " on server '" + Constants.patternIp + "'."
				);
		map5.put("UserName", "techrep");
		map5.put("SourceIP", Constants.patternBracketIp);
		map5.put("Action", "SettingValue 'Special Reduced Storage Cameras' changed");
		Map<String, String> map6 = new TreeMap<String, String>();
		map6.put("log4net:message", "Value changed from " + Constants.patternWord
				+ " to " + Constants.patternWord
				+ " on server '" + Constants.patternIp + "'."
				);
		map6.put("UserName", "techrep");
		map6.put("SourceIP", Constants.patternBracketIp);
		map6.put("Action", "SettingValue 'Special Reduced Storage Duration' changed");
		Map<String, String> map7 = new TreeMap<String, String>();
		map7.put("log4net:message", "Smart Storage for " + Constants.patternWord + " changed from " + Constants.patternWord
				+ " to " + Constants.patternWord
				+ " on " + Constants.patternIp + " on server '" + Constants.patternIp + "'"
				);
		map7.put("UserName", "techrep");
		map7.put("SourceIP", Constants.patternBracketIp);
		map7.put("Action", "Smart Storage Setting");
		Map<String, String> map8 = new TreeMap<String, String>();
		map8.put("log4net:message", "Smart Storage for " + Constants.patternWord + " changed from " + Constants.patternWord
				+ " to " + Constants.patternWord
				+ " on " + Constants.patternIp + " on server '" + Constants.patternIp + "'"
				);
		map8.put("UserName", "techrep");
		map8.put("SourceIP", Constants.patternBracketIp);
		map8.put("Action", "Smart Storage Setting");
		Map<String, String> map9 = new TreeMap<String, String>();
		map9.put("log4net:message", "Smart Storage for " + Constants.patternWord + " changed from " + Constants.patternWord
				+ " to " + Constants.patternWord
				+ " on " + Constants.patternIp + " on server '" + Constants.patternIp + "'"
				);
		map9.put("UserName", "techrep");
		map9.put("SourceIP", Constants.patternBracketIp);
		map9.put("Action", "Smart Storage Setting");
		Map<String, String> map10 = new TreeMap<String, String>();
		map10.put("log4net:message", "Smart Storage for " + Constants.patternWord + " changed from " + Constants.patternWord
				+ " to " + Constants.patternWord
				+ " on " + Constants.patternIp + " on server '" + Constants.patternIp + "'"
				);
		map10.put("UserName", "techrep");
		map10.put("SourceIP", Constants.patternBracketIp);
		map10.put("Action", "Smart Storage Setting");
		return mapsMatch(map,map2,map3,map4,map5,map6,map7,map8,map9,map10);
	}	
	
	public boolean _change_network_settings() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Server " + Constants.patternMarksIp
				+ " was set to use static IP on " + Constants.patternWord
				+ ":Server " + Constants.patternMarksIp
				+ " was set to use static IP: IP Address = "
				+ Constants.patternIp + ", Subnet Mask = " + Constants.patternIp
				+ ", Default Gateway = " + Constants.patternIp
				+ ", Primary DNS Server = " + Constants.patternIp
				+ ", Secondary DNS Server = " + Constants.patternIp
				+ ", DNS Suffix = 3vr.net.");
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Change Network Settings");
		return mapsMatch(map);
	}

	public boolean _change_date_and_time_settings() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Button 'Test Connection' clicked on 'Date and Time Settings' (EditTimeDialog) in 'SystemManager'");
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Button Click");
		Map<String, String> map2 = new TreeMap<String, String>();
//		map.put("log4net:message", "Modified time zone to "
//				+ Constants.patternWord
//				+ " and set daylight savings on Set NTP server to 10.10.1.1 ");
//		map.put("UserName", "techrep");
//		map.put("SourceIP", Constants.patternBracketIp);
//		map.put("Action", "Modified time on server " + Constants.patternWord + Constants.patternBracketIp + ".");
//		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Modified time zone to "
				+ Constants.patternWord
				+ " and set daylight savings on Modified date and time to "+ Constants.patternWord + "");
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Modified time on server " + Constants.patternWord + Constants.patternBracketIp + ".");
		return mapsMatch(map2);
	}

	public boolean _connect_each_core() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", Constants.patternWord + " on "
				+ Constants.patternWord + " clicked on " + Constants.patternWord
				+ " (" + Constants.patternWord + ") in " + Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "TreeNode Click");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", Constants.patternWord + " on "
				+ Constants.patternWord + " clicked on " + Constants.patternWord
				+ " (" + Constants.patternWord + ") in " + Constants.patternWord);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "TreeNode Click");
		return mapsMatch(map, map2);
	}

	public boolean _Create_IP_Channel() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Added channel " + Constants.patternWord
				+ " to " + Constants.patternMarksIp);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Add Channel");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Changed channel " + Constants.patternWord
 			+ " from " + Constants.patternWord + " to "
				+ Constants.patternWord + " on " + Constants.patternMarksIp);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Changed Channel");
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message", "Channel " + Constants.patternWord
				+ " network address changed from " + Constants.patternWord
				+ " to " + Constants.patternMarksIp + " on "
				+ Constants.patternIp + " on server " + Constants.patternMarksIp);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Network Camera: Network Address");
		return mapsMatch(map, map3);
	}

	public boolean _Change_Channle_Name() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Channel " + Constants.patternWord
				+ " name changed from " + Constants.patternWord + " to "
				+ Constants.patternWord + " on " + Constants.patternIp
				+ " on server " + Constants.patternMarksIp);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Channel Name");
		return mapsMatch(map);
	}

	public boolean _change_channel_quality_setting() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Set property value "
				+ Constants.patternWord + " to " + Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Property Value Set");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", Constants.patternWord
				+ " peak changed from " + Constants.patternWord + " to "
				+ Constants.patternWord + "and off-peak changed from"
				+ Constants.patternWord + "to" + Constants.patternWord
				+ "on server" + Constants.patternMarksIp);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Change Scheduled Value");
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message", Constants.patternWord
				+ " peak changed from " + Constants.patternWord + " to "
				+ Constants.patternWord + "and off-peak changed from"
				+ Constants.patternWord + "to" + Constants.patternWord
				+ "on server" + Constants.patternMarksIp);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Change Scheduled Value");
		return mapsMatch(map, map2, map3);
	}

	public boolean _Remove_IP_Channel() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Removed channel " + Constants.patternWord
				+ " from " + Constants.patternIp);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Remove Channel");
		return mapsMatch(map);
	}

	public boolean _Create_Analog_Channel() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Changed channel " + Constants.patternWord
				+ " from " + Constants.patternWord + " to "
				+ Constants.patternWord + " on " + Constants.patternMarksIp);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Changed Channel");
		return mapsMatch(map);
	}

	public boolean _Enable_and_Disable_Channel() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Enabled channel " + Constants.patternWord
				+ " on " + Constants.patternMarksIp);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Enable Channel");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Disabled channel " + Constants.patternWord
				+ " on " + Constants.patternMarksIp);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Disable Channel");
		return mapsMatch(map, map2);
	}

	public boolean _Remove_Channel() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Removed channel " + Constants.patternWord
				+ " from " + Constants.patternMarksIp);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Remove Channel");
		return mapsMatch(map);
	}

	public boolean _watch_video_via_core_right_context_menu() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Menu item " + Constants.patternWord
				+ " clicked on " + Constants.patternWord
				+ Constants.patternBracketIp);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Menu Item Click");
		return mapsMatch(map);
	}

	public boolean _switch_to_Accounts_panel() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Tab changed to " + Constants.patternWord
				+ " on " + Constants.patternWord + " (" + Constants.patternWord + ") in "
				+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Tab Change");
		return mapsMatch(map);
	}

	public boolean _create_a_new_role() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Button " + Constants.patternWord
				+ " clicked on " + Constants.patternWord
				+ " (" + Constants.patternWord + ") in " + Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Button Click");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Button "
				+ Constants.patternWord + " clicked on " + Constants.patternWord
				+ " (" + Constants.patternWord + ") in " + Constants.patternWord);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Button Click");
		return mapsMatch(map, map2);
	}

	public boolean _create_a_new_user() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Button " + Constants.patternWord
				+ " clicked on " + Constants.patternWord
				+ " (" + Constants.patternWord + ") in " + Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Button Click");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", Constants.patternWord
				+ " selected from ComboBox " + Constants.patternWord 
				+ Constants.patternWord
				+ " (" + Constants.patternWord + ") in " + Constants.patternWord);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "List Item Selected");
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message", Constants.patternWord 
				+ Constants.patternWord + " clicked on " + Constants.patternWord
				+ " (" + Constants.patternWord + ") in " + Constants.patternWord);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "TreeNode Click");
		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message", "Button " + Constants.patternWord
				+ " clicked on " + Constants.patternWord
				+ " (" + Constants.patternWord + ") in " + Constants.patternWord);
		map4.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Button Click");
		return mapsMatch(map, map2, map3, map4);
	}

	public boolean _login_new_user_and_check_permission() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Signed into OpCenter ("
				+ Constants.patternWord
				+ Constants.patternBracketIp + ")");
		map.put("UserName", "zzz_user");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Sign In");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put(
				"log4net:message",
				"Starting viewing monitor panel. Channels: None Events: Click here to choose events... Time Range: 30m");
		map2.put("UserName", "zzz_user");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Start Monitor Panel");
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put(
				"log4net:message",
				"Changed channel selection. Currently viewing monitor panel on: Channels: None Events: Click here to choose events... Time Range: 30m");
		map3.put("UserName", "zzz_user");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Monitor Channel Selection Changed");
		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message", "Stopped viewing monitor panel");
		map4.put("UserName", "zzz_user");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Stop Monitor Panel");
		return mapsMatch(map, map2, map3, map4);
	}

	public boolean _edit_a_user() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Button " + Constants.patternWord
				+ " clicked on " + Constants.patternWord
				+ " (" + Constants.patternWord +") in " + Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Button Click");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", Constants.patternWord
				+ " selected from ComboBox " + Constants.patternWord + "on"
				+ Constants.patternWord + " (" + Constants.patternWord +") in SystemManager");
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "List Item Selected");
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message", Constants.patternWord + " on "
				+ Constants.patternWord + " clicked on " + Constants.patternWord
				+ " (" + Constants.patternWord +") in " + Constants.patternWord);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "TreeNode Click");
		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message", "Button " + Constants.patternWord
				+ " clicked on " + Constants.patternWord
				+ " (" + Constants.patternWord +") in " + Constants.patternWord);
		map4.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Button Click");
		Map<String, String> map5 = new TreeMap<String, String>();
		//map5.put("log4net:message", "Signed into " + Constants.patternWord);
		// Signed into OpCenter (3VR P Enterprise ENT (10.30.4.85))
		//Signed into 3VR P Enterprise ENT (10.30.4.85) ()
		//map5.put("UserName", "zzz_user");
		//map5.put("SourceIP", Constants.patternBracketIp);
		//map5.put("Action", "Sign In");
		return mapsMatch(map, map2, map3, map4);
	}

	public boolean _delete_a_user() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Deleting user " + Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Remove User");
		return mapsMatch(map);
	}

	public boolean _copy_log() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Downloaded " + Constants.patternWord
				+ " from appliance " + Constants.patternWord + " to "
				+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Download Logs");
		return mapsMatch(map);
	}

	public boolean _System_Diagnositcs_output() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", Constants.patternWord + " on "
				+ Constants.patternWord + " clicked on " + Constants.patternWord
				+ " (" + Constants.patternWord + ") in " + Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "TreeNode Click");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Button " + Constants.patternWord
				+ " clicked on " + Constants.patternWord
				+ " (" + Constants.patternWord + ") in " + Constants.patternWord);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Button Click");
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message", "Button " + Constants.patternWord
				+ " clicked on " + Constants.patternWord + " (" + Constants.patternWord + ") in "
				+ Constants.patternWord);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Button Click");
		return mapsMatch(map, map2, map3);
	}

	public boolean _login() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Signed into OpCenter (" + Constants.patternWord + " "
				+ Constants.patternBracketIp + ")");
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Sign In");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Connected to appliance " + Constants.patternWord + " "
				+ Constants.patternBracketIp);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Appliance Connect");
		return mapsMatch(map, map2);
	}

	public boolean _Switch_To_Video_Panel() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Tab changed to " + Constants.patternWord
				+ " on " + Constants.patternWord + " (" + Constants.patternWord + ") in "
				+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Tab Change");
		return mapsMatch(map);
	}

	public boolean _Add_Remove_Camears_Via_Drag_And_Drop() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Added the following channel to the channel view list: " + Constants.patternWord + " "
						+ Constants.patternBracketIp
						+ ": " + Constants.patternWord + " Currently viewing video at: " + Constants.patternWord + "Channel" + Constants.addS + ": " + Constants.patternWord + " "
						+ Constants.patternBracketIp + ": " + Constants.patternWord + "On "
						+ Constants.patternWord
						+ " (" + Constants.patternWord + ") in OpCenter");
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Channels Added To View");
		return mapsMatch(map);
	}

	public boolean _Add_Remove_Camears_Via_Right_Click_Context_Menus() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Added the following channel to the channel view list: " + Constants.patternWord + " "
						+ Constants.patternBracketIp
						+ ": " + Constants.patternWord + " Currently viewing video at: " + Constants.patternWord + " Channel" + Constants.addS + ": " + Constants.patternWord + " "
						+ Constants.patternBracketIp + ": " + Constants.patternWord + " On "
						+ Constants.patternWord
						+ " (" + Constants.patternWord + ") in OpCenter");
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Channels Added To View");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put(
				"log4net:message",
				"Removed the following channel from the channel view list: " + Constants.patternWord + Constants.patternWord + " "
						+ Constants.patternBracketIp
						+ " Currently viewing video at: " + Constants.patternWord + "Channel" + Constants.addS + ": None On "
						+ Constants.patternWord
						+ " (" + Constants.patternWord + ") in OpCenter");
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Channel Removed From View");
		return mapsMatch(map, map2);
	}


	public boolean _Video_Playback_Sigle_Channel() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Changed video view grid size from " + Constants.patternWord + " to " + Constants.patternWord);                         //" Changed video view grid size from 16 to 1 "
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "View Layout Changed");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put(
				"log4net:message",
				"Added the following channel to the channel view list: " + Constants.patternWord                    //"Added the following channel to the channel view list: 2. VMS_Dell "
						+ Constants.patternBracketIp
						+ ": " + Constants.patternWord + "Currently viewing video at: " + Constants.patternWord + "Channel"+ Constants.addS +": " + Constants.patternWord         //": 1 Currently viewing video at: 3/4/2013 12:57:37 AM -08:00 Channels: 2. VMS_Dell"
						+ Constants.patternBracketIp 
						+ ": " + Constants.patternWord + "On "                                                  //": 1 On"
						+ Constants.patternWord 
						+ " (" + Constants.patternWord + ") in OpCenter");                                    //"(VideoTabPanel) in OpCenter"
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Channels Added To View");
		return mapsMatch(map, map2);
	}

	public boolean _Video_Playback_4_Channels() {                                 //add 5 action 'Channels Added To View'
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Added the following channel to the channel view list: " 
		                + Constants.patternWord                                                      
						+ Constants.patternBracketIp
						+ ": " + Constants.patternWord);                           
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Channels Added To View");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message",
				"Added the following channel to the channel view list: " 
		                + Constants.patternWord                                                       
						+ Constants.patternBracketIp
						+ ": " + Constants.patternWord);                            
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Channels Added To View");
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message",
				"Added the following channel to the channel view list: " 
		                + Constants.patternWord                                                     
						+ Constants.patternBracketIp
						+ ": " + Constants.patternWord);                            
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Channels Added To View");
		
		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message",
				"Added the following channel to the channel view list: " 
		                + Constants.patternWord                                                      
						+ Constants.patternBracketIp
						+ ": " + Constants.patternWord);                           
		map4.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Channels Added To View");
		
		Map<String, String> map5 = new TreeMap<String, String>();
		map5.put("log4net:message",
				"Changed video view grid size from " 
		                + Constants.patternWord 
						+ " to " + Constants.patternWord);                           
		map5.put("UserName", "techrep");
		map5.put("SourceIP", Constants.patternBracketIp);
		map5.put("Action", "View Layout Changed");
		
		Map<String, String> map6 = new TreeMap<String, String>();
		map6.put("log4net:message",
				"Currently viewing video at: " + Constants.patternWord +  " Channels: " + Constants.patternWord       //"Currently viewing video at: 3/4/2013 12:59:49 AM -08:00 Channels: 2. VMS_Dell  "
						+ Constants.patternBracketIp
						+ ": " + Constants.patternWord + " On "                       // " : 1,2,4,11 On "
						+ Constants.patternWord
						+ " (" + Constants.patternWord + ") in OpCenter ");                          //" (VideoTabPanel) in OpCenter "
		map6.put("UserName", "techrep");
		map6.put("SourceIP", Constants.patternBracketIp);
		map6.put("Action", "Channels Added To View");
		return mapsMatch(map,map2,map3,map4,map5);
	}

	public boolean _Video_Playback_9_Channels() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Changed video view grid size from " + Constants.patternWord + " to " + Constants.patternWord);                    //" Changed video view grid size from 4 to 9 "
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "View Layout Changed");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message",
				"Added the following channels to the channel view list: " + Constants.patternWord           //" Added the following channels to the channel view list: 2. VMS_Dell   "
						+ Constants.patternBracketIp
						+ ": " + Constants.patternWord + " Currently viewing video at: " + Constants.patternWord + "Channels: " + Constants.patternWord        //" : 8,10,14,15,51,59,60,111 Currently viewing video at: 3/4/2013 1:00:29 AM -08:00 Channels: 2. VMS_Dell  "
						+ Constants.patternBracketIp
						+ ": " + Constants.patternWord + " On "                      //" : 1,2,4,8,10,11,14,15,51 On "
						+ Constants.patternWord
						+ " (" + Constants.patternWord + ") in OpCenter");                            //" (VideoTabPanel) in OpCenter"
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Channels Added To View");
		return mapsMatch(map,map2);
	}

	public boolean _Video_Playback_16_Channels_From_Both_Cores() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Changed video view grid size from " + Constants.patternWord + " to " + Constants.patternWord);                       //" Changed video view grid size from 9 to 16 "
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "View Layout Changed");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message",
				"Added the following channels to the channel view list: " + Constants.patternWord + " Currently viewing video at: " + Constants.patternWord + "Channel"+ Constants.addS +": " + Constants.patternWord        //" Added the following channels to the channel view list: None Currently viewing video at: 3/4/2013 1:02:40 AM -08:00 Channels: 2. VMS_Dell "
						+ Constants.patternBracketIp
						+ ": " + Constants.patternWord + " On "                   //" : 1,2,4,8,10,11,14,15,51,59,60,111 On  "
						+ Constants.patternWord
						+ " (" + Constants.patternWord + ") in OpCenter");                           //" (VideoTabPanel) in OpCenter"
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Channels Added To View");
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message",
				"Added the following channels to the channel view list: " + Constants.patternWord + Constants.patternBracketIp + ": " + Constants.patternWord
				+ " Currently viewing video at: " + Constants.patternWord + "Channel"+ Constants.addS +": " + Constants.patternWord        //" Currently viewing video at: 3/4/2013 1:03:22 AM -08:00 Channels: 2. VMS_Dell "
				+ Constants.patternBracketIp
				+ ": " + Constants.patternWord                                             //": 1,2,4,8,10,11,14,15,51,59,60,111 1. VMS_Pohang"
				+ Constants.patternBracketIp
				+ ": " + Constants.patternWord + " On "                         //": 1,2,3,4 On"
				+ Constants.patternWord
				+ " (" + Constants.patternWord + ") in OpCenter");                                // "(VideoTabPanel) in OpCenter"
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Channels Added To View");
		return mapsMatch(map,map2,map3);
	}

	public boolean _Stored_Video_Playback_Multiple_Channels() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Viewing video at " + Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Video Timeline Box Drag");
		return mapsMatch(map);
	}

	 public boolean _Video_Export_Multiple_Channels_1_Minute() {
		  Map<String, String> map = new TreeMap<String, String>();
		   map.put("log4net:message", "Video export initialized with the following options: Start Time: "+ Constants.patternWord 
		                        + "End Time: "                               //" AM End Time: "
		                              + Constants.patternWord +"Selected Path: "+ Constants.patternWord +"Total Minutes of Video: "+ Constants.patternNumber +"Cameras: "
		                        + Constants.patternWord 
		                              + Constants.patternBracketIp
		                              + ": " + Constants.patternWords()
		                              + Constants.patternBracketIp 
		                              + "" + Constants.patternWords());
		  map.put("UserName", "techrep");
		  map.put("SourceIP", Constants.patternBracketIp);
		  map.put("Action", "Video Export Initiated");
		  return mapsMatch(map);
		 }

	public boolean _Switch_To_Monitor_Panel() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Tab changed to " + Constants.patternWord
				+ " on " + Constants.patternWord + " (" + Constants.patternWord + ") in "
				+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Tab Change");
		return mapsMatch(map);
	}

	
	public boolean _Camera_Selection_From_Right_Context_Menu() {
        Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Changed channel selection. Currently viewing monitor panel on:"
		        +" Channels: " + Constants.patternWords()
		        +" Events: " + Constants.patternWord
		        +" Time Range: "+ Constants.patternWord
				);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Monitor Channel Selection Changed");
		return mapsMatch(map);
	}
	
	public boolean _Camera_Selection_From_Camera_Picker_Dropdown() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Button " + Constants.patternWord + " clicked on " 
		        + Constants.patternWord 
		        + " (" + Constants.patternWord + ") in "+ Constants.patternWord 
				);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
        map.put("Action", "Button Click");
	
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Link "+ Constants.patternWord +" clicked on "+ Constants.patternWord+ " in "
				+ Constants.patternWord
				);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Link Click");
		
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message", "Changed channel selection. Currently viewing monitor panel on:"
		        +" Channels: " + Constants.patternWords()
		        +" Events: " + Constants.patternWord
		        +" Time Range: "+ Constants.patternWord
				);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Monitor Channel Selection Changed");
		
		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message", "Changed channel selection. Currently viewing monitor panel on:"
		        +" Channels: None"
		        +" Events: "+ Constants.patternWord
		        +" Time Range: "+ Constants.patternWord
				);
		map4.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Monitor Channel Selection Changed");
		return mapsMatch(map,map2,map3,map4);
	}
	
	public boolean _Opt_Preferences_Play_Event_Video_Checkbox() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Checkbox " + Constants.patternWord + " unchecked on " 
		        + Constants.patternWord 
		        + " (" + Constants.patternWord + ") in "+ Constants.patternWord 
				);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
        map.put("Action", "Checkbox Click");
	
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Checkbox "+ Constants.patternWord + " checked on "
				 + Constants.patternWord 
			        + " (" + Constants.patternWord + ") in "+ Constants.patternWord 
				);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Checkbox Click");

		return mapsMatch(map,map2);
	}
	
	public boolean _Play_video_by_Left_Click_and_Save_Frame() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Event card " + Constants.patternWord + " (" + Constants.patternWord + " on "
						+ Constants.patternWord + ") clicked on "
						+ Constants.patternWord + " (" + Constants.patternWord + ") in "
						+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Event Card Click");
	
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Video playback button "+ Constants.patternWord
				+ " clicked at speed " + Constants.patternWord
				);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Video Playback Button Click");

		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message", "Video playback button "+ Constants.patternWord
				+ " clicked"
				);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Video Playback Button Click");
		
		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message", "Saved frame (" + Constants.patternWord
				+ ") for event " + Constants.patternWord + "on channel " +  Constants.patternWord
				);
		map4.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Save Event Frame");

		return mapsMatch(map,map2,map3,map4);
	}
	
	public boolean _Play_video_from_Rigth_Context_Menu_and_Save_Frame() {
		/*Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Menu item " + Constants.patternWord + " clicked");
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Menu Item Click");
	*/
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Video playback button "+ Constants.patternWord
				+ " clicked"
				);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Video Playback Button Click");
		
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message", "Saved frame (" + Constants.patternWord
				+ ") for event " + Constants.patternWord + " on channel " +  Constants.patternWord
				);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Save Event Frame");

		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message", "Button " + Constants.patternWord
				+ " clicked on " + Constants.patternWord + " (" +  Constants.patternWord
				+ ") in " + Constants.patternWord
				);
		map4.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Button Click");
		
		Map<String, String> map5 = new TreeMap<String, String>();
		map5.put("log4net:message", 
				"Event card " + Constants.patternWord + " (" + Constants.patternWord + " on "
						+ Constants.patternWord + ") clicked on "
						+ Constants.patternWord + " (" + Constants.patternWord + ") in "
						+ Constants.patternWord
				);
		map5.put("UserName", "techrep");
		map5.put("SourceIP", Constants.patternBracketIp);
		map5.put("Action", "Event Card Click");
		
		return mapsMatch(map2,map3,map4,map5);
	}
	
	
	
	public boolean _Generate_Face_Event_From_Single_Channel() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Changed event type. Currently viewing monitor panel on: Channel: " + Constants.patternWord + " Events: " + Constants.patternWord + " Time Range: " + Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Monitor Event Type Change");

		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put(
				"log4net:message",
				"Changed event type. Currently viewing monitor panel on: Channel: " + Constants.patternWord + " Events: " + Constants.patternWord + "Time Range: " + Constants.patternWord);
		map2.put("UserName", "techrep" + Constants.patternBracketIp);
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Monitor Event Type Change");
		return mapsMatch(map);
	}

	public boolean _Generate_Motion_Events_From_Multiple_Channels() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Changed event type. Currently viewing monitor panel on: Channels: " + Constants.patternWord + "Events: " + Constants.patternWord + "Time Range: " + Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Monitor Event Type Change");
		return mapsMatch(map);
	}

	public boolean _Generate_Transaction_Events() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Changed event type. Currently viewing monitor panel on: Channel: " + Constants.patternWord + " Events: " + Constants.patternWord + " Time Range: " + Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Monitor Event Type Change");
		return mapsMatch(map);
	}

	public boolean _Generate_LPR_Events() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Changed event type. Currently viewing monitor panel on: Channel: " + Constants.patternWord + " Events: " + Constants.patternWord + " Time Range: " + Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Monitor Event Type Change");
		return mapsMatch(map);
	}

	public boolean _Creat_A_Person_From_Face_Event() {
		  Map<String, String> map = new TreeMap<String, String>();
		  map.put("log4net:message", "Menu item " 
		          + Constants.patternWord
		    + " clicked");
		  map.put("UserName", "techrep");
		  map.put("SourceIP", Constants.patternBracketIp);
		  map.put("Action", "Menu Item Click");
		  Map<String, String> map2 = new TreeMap<String, String>();
		  map2.put("log4net:message", Constants.patternWord 
		                        + " selected from ComboBox " 
		                        + Constants.patternWord
		                        + " on "
		                        + Constants.patternWord
		                        + " (" + Constants.patternWord + ") in OpCenter");
		  map2.put("UserName", "techrep");
		  map2.put("SourceIP", Constants.patternBracketIp);
		  map2.put("Action", "List Item Selected");
		  Map<String, String> map3 = new TreeMap<String, String>();
		  map3.put("log4net:message", Constants.patternWord 
		                + " selected from ComboBox " 
		                + Constants.patternWord
		                + " on "
		                + Constants.patternWord
		                + " (" + Constants.patternWord + ") in OpCenter");
		  map3.put("UserName", "techrep");
		  map3.put("SourceIP", Constants.patternBracketIp);
		  map3.put("Action", "List Item Selected");
		  Map<String, String> map4 = new TreeMap<String, String>();
		  map4.put("log4net:message", Constants.patternWord 
		                + " selected from ComboBox " 
		                + Constants.patternWord
		                + " on "
		                + Constants.patternWord
		                + " (" + Constants.patternWord + ") in OpCenter");
		  map4.put("UserName", "techrep");
		  map4.put("SourceIP", Constants.patternBracketIp);
		  map4.put("Action", "List Item Selected");
		  Map<String, String> map5 = new TreeMap<String, String>();
		  map5.put("log4net:message", "Created new person " + Constants.patternWord
		    +  " (" + Constants.patternWord +")");
		  map5.put("UserName", "techrep");
		  map5.put("SourceIP", Constants.patternBracketIp);
		  map5.put("Action", "Create Person");
		  return mapsMatch(map,map2,map3,map4,map5);
	}

	public boolean _Switch_Core() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Appliance switched from " + Constants.patternWord
				+ Constants.patternBracketIp + " to " + Constants.patternWord
				+ Constants.patternBracketIp);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Appliance Switch");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Appliance switched from " + Constants.patternWord
				+ Constants.patternBracketIp + " to " + Constants.patternWord
				+ Constants.patternBracketIp);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Appliance Switch");
		return mapsMatch(map,map2);
	}

	public boolean _Event_Video_Playback() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Event card " + Constants.patternWord + " (" + Constants.patternWord + " on "
						+ Constants.patternWord + ") clicked on "
						+ Constants.patternWord + " (" + Constants.patternWord + ") in "
						+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Event Card Click");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message",
				"Video playback button "
						+ Constants.patternWord + " clicked at speed "
						+ Constants.patternWord);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Video Playback Button Click");
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message",
				"Video playback button "
						+ Constants.patternWord + " clicked at speed "
						+ Constants.patternWord);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Video Playback Button Click");
		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message",
				"Video playback button"
						+ Constants.patternWord + " clicked at speed "
						+ Constants.patternWord);
		map4.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Video Playback Button Click");
		Map<String, String> map5 = new TreeMap<String, String>();
		map5.put("log4net:message",
				"Video playback button "
						+ Constants.patternWord + " clicked at speed "
						+ Constants.patternWord);
		map5.put("UserName", "techrep");
		map5.put("SourceIP", Constants.patternBracketIp);
		map5.put("Action", "Video Playback Button Click");
		return mapsMatch(map,map2,map3,map4,map5);
	}

	public boolean _View_Face_Event_Information_Tabs() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Event card " + Constants.patternWord + " (" + Constants.patternWord + " on "
						+ Constants.patternWord + ") clicked on "
						+ Constants.patternWord + " (" + Constants.patternWord + ") in "
						+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Event Card Click");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message",
				"Tab changed to "
						+ Constants.patternWord + " on "
						+ Constants.patternWord
						+ " (" + Constants.patternWord + ") in "
						+ Constants.patternWord);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Tab Change");
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message",
				"Tab changed to "
						+ Constants.patternWord + " on "
						+ Constants.patternWord
						+ " (" + Constants.patternWord + ") in "
						+ Constants.patternWord);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Tab Change");
		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message",
				"Tab changed to "
						+ Constants.patternWord + " on "
						+ Constants.patternWord
						+ " (" + Constants.patternWord + ") in "
						+ Constants.patternWord);
		map4.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Tab Change");
		Map<String, String> map5 = new TreeMap<String, String>();
		map5.put("log4net:message",
				"Tab changed to "
						+ Constants.patternWord + " on "
						+ Constants.patternWord
						+ " (" + Constants.patternWord + ") in "
						+ Constants.patternWord);
		map5.put("UserName", "techrep");
		map5.put("SourceIP", Constants.patternBracketIp);
		map5.put("Action", "Tab Change");
		return mapsMatch(map,map2,map3,map4,map5);
	}

	public boolean _Export_Event_Via_Right_Click_Context_Menu() {                    //change scripts for problem: cannot check the correct event type
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Menu item " + Constants.patternWord
				+ " clicked");
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Menu Item Click");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", Constants.patternWord
				        + " on "
						+ Constants.patternWord + " clicked on " + "(" + Constants.patternWord + ") in "     // use " (" + Constants.patternWord + ") in " to replace 锟�FlowLayoutPanel) in锟�
						+ Constants.patternWord);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "TreeNode Click");
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message", "Checkbox "
						+ Constants.patternWord + " on "           //map3.map4.map5:use Constants.patternWord + " on " replace 'checked on'
						+ Constants.patternWord
						+ " (" + Constants.patternWord + ") in "
						+ Constants.patternWord);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Checkbox Click");
		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message",
				"Checkbox "
						+ Constants.patternWord + " on "
						+ Constants.patternWord
						+ " (" + Constants.patternWord + ") in "
						+ Constants.patternWord);
		map4.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Checkbox Click");
		Map<String, String> map5 = new TreeMap<String, String>();
		map5.put("log4net:message",
				"Checkbox "
						+ Constants.patternWord + " on "         
						+ Constants.patternWord
						+ " (" + Constants.patternWord + ") in "
						+ Constants.patternWord);
		map5.put("UserName", "techrep");
		map5.put("SourceIP", Constants.patternBracketIp);
		map5.put("Action", "Checkbox Click");
		Map<String, String> map6 = new TreeMap<String, String>();
		map6.put("log4net:message",
				"Exported the following event to "
						+ Constants.patternWord + ":" + " Motion event " + Constants.patternWord + " (" + Constants.patternWord + " on "   //use patterWord to replace date
						+ Constants.patternWord
						+ ")");
		map6.put("UserName", "techrep");
		map6.put("SourceIP", Constants.patternBracketIp);
		map6.put("Action", "Export Event");
		return mapsMatch(map,map2,map3,map4,map5,map6);
	}

	public boolean _Switch_To_Search_Panel() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Tab changed to " + Constants.patternWord
				+ " on " + Constants.patternWord + "(" + Constants.patternWord + ") in "
				+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Tab Change");
		return mapsMatch(map);
	}

	public boolean _Edit_Camera_Group() {                                     
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Modified channel group " + Constants.patternWord +": " 
				 + Constants.patternWord
		       
				);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Modify Channel Group");
		
	/*	Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message",
				"Modified channel group " + Constants.patternWords()
		         +"Added " + Constants.patternWord
				);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Modify Channel Group");
	*/	
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message",
				"Changed channel selection. Currently viewing monitor panel on: Channels: " + Constants.patternWord
				 +" Events: " + Constants.patternWord
		         + " Time Range: " + Constants.patternWord
				);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Monitor Channel Selection Changed");
		

	/*	Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message",
				"Added alert: id = " + Constants.patternWord + ", name = " 
			    + Constants.patternWord
			    +". Alert details: " 
			    + Constants.patternWord + "Camera #: " + Constants.patternWord + "Motion Events" + Constants.patternWord
			    );   
				
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Add Alert");
		

		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message",
				"Currently viewing video at: " + Constants.patternWord 
				 +"Channels: " 
				 + Constants.patternBracketIp + ": " + Constants.patternWord 
				 + "On " + Constants.patternWord
				 + " (" + Constants.patternWord + ") in OpCenter");
		        
		map4.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Channels Added To View");
	*/	
		return mapsMatch(map,map2);
		
		
	}
	
	public boolean _Delete_Camera_Group() {                                     
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Deleted channel group " + Constants.patternWord
		        );
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Delete Channel Group");
		return mapsMatch(map);	
		
	}
	
	public boolean _Create_Camera_Group() {                                     
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Button " + Constants.patternWord + " clicked on " + Constants.patternWord 
				  + " (" + Constants.patternWord +") in " + Constants.patternWord 
		        );
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Button Click");
		
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message",
				"Button " + Constants.patternWord + " clicked on " + Constants.patternWord 
				  + " (" + Constants.patternWord +") in " + Constants.patternWord 
		        );
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Button Click");
		
	/*	 Map<String, String> map2 = new TreeMap<String, String>();             //
		map2.put("log4net:message",
				"Created new channel group " + Constants.patternWord + " with the following channels:"
		         + Constants.patternWord 
				  );
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "New Channel Group");
	*/	
		return mapsMatch(map,map2);
	}
	
	public boolean _Specify_regions_and_clear_them() {                                     
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Searched for: Total Time Range: " + Constants.fromToDate 
				+" User Selected Range: " + Constants.patternWord  
				+" Days: " + Constants.patternWord 
				+ " Time: " + Constants.patternWord 
				+ " Channels: "  + Constants.patternWords() 
				+ " Events: " + Constants.patternWord
		        );
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Search");
		
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message",
				"Button " + Constants.patternWord + " clicked on "
		         + Constants.patternWord 
		         +" (" + Constants.patternWord +") in " + Constants.patternWord
				  );
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Button Click");
		return mapsMatch(map,map2);
	}
	
	public boolean _Group_Selection_for_Search() {                                     //锟斤拷锟斤拷contents锟斤拷锟斤拷锟斤拷锟�? fromToDate
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Searched for: Total Time Range: " + Constants.fromToDate +"User Selected Range: " + Constants.patternWord + "Days: " + Constants.patternWord + "Time: " + Constants.patternWord + "Channels: "  + Constants.patternWord
				 + "Events: " + Constants.patternWord + " "
				 + "Input Types: " + Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Search");
		
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message",
				"Searched for: Total Time Range: " + Constants.fromToDate +"User Selected Range: " + Constants.patternWord + "Days: " + Constants.patternWord + "Time: " + Constants.patternWord + "Channels: "  + Constants.patternWord
				 + "Events: " + Constants.patternWord +" "
				 + "Input Types: " + Constants.patternWord);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Search");
		
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message", "Link "+ Constants.patternWord +" clicked on "+ Constants.patternWord+ "(" + Constants.patternWord+ ") in "
				+ Constants.patternWord
				);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Link Click");
		
		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message", "Link "+ Constants.patternWord +" clicked on "+ Constants.patternWord+ "(" + Constants.patternWord+ ") in "
				+ Constants.patternWord
				);
		map2.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Link Click");
		
		return mapsMatch(map,map2,map3,map4);
	}
	
	public boolean _Search_Result_Play_video_by_Left_Click_and_Save_Frame() {                                     //锟斤拷锟斤拷contents锟斤拷锟斤拷锟斤拷锟�? fromToDate
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Event card " + Constants.patternWord + " (" + Constants.patternWord + " on "
						+ Constants.patternWord + ") clicked on "
						+ Constants.patternWord + " (" + Constants.patternWord + ") in "
						+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Event Card Click");
		
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message",
				"Video playback button "
						+ Constants.patternWord + " clicked at speed "
						+ Constants.patternWord);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Video Playback Button Click");
		
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message",
				"Video playback button "
						+ Constants.patternWord + " clicked at speed "
						+ Constants.patternWord);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Video Playback Button Click");
		
		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message",
				"Video playback button "
						+ Constants.patternWord + " clicked"
						);
		map4.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Video Playback Button Click");
		
		Map<String, String> map5 = new TreeMap<String, String>();
		map5.put("log4net:message", "Saved frame (" + Constants.patternWord
				+ ") for event " + Constants.patternWord + "on channel " +  Constants.patternWord
				);
		map5.put("UserName", "techrep");
		map5.put("SourceIP", Constants.patternBracketIp);
		map5.put("Action", "Save Event Frame");
		
		return mapsMatch(map,map2,map3,map4,map5);
	}
	
	public boolean _Search_Result_Play_video_from_Rigth_Context_Menu_and_Save_Frame() {
		Map<String, String> map = new TreeMap<String, String>();
		/*map.put("log4net:message", "Menu item " + Constants.patternWord
				+ " clicked");
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Menu Item Click");
	*/	
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message",
				"Video playback button "
						+ Constants.patternWord + " clicked"
						);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Video Playback Button Click");
		
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message", "Saved frame (" + Constants.patternWord
				+ ") for event " + Constants.patternWord + "on channel " +  Constants.patternWord
				);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Save Event Frame");
		
		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message", "Button " + Constants.patternWord
				+ " clicked on " + Constants.patternWord
				+ " (" + Constants.patternWord + ") in " + Constants.patternWord);
		map4.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Button Click");
		
		Map<String, String> map5 = new TreeMap<String, String>();
		map5.put("log4net:message",
				"Event card " + Constants.patternWord + " (" + Constants.patternWord + " on "
						+ Constants.patternWord + ") clicked on "
						+ Constants.patternWord + " (" + Constants.patternWord + ") in "
						+ Constants.patternWord);
		map5.put("UserName", "techrep");
		map5.put("SourceIP", Constants.patternBracketIp);
		map5.put("Action", "Event Card Click");
		
		Map<String, String> map6 = new TreeMap<String, String>();
		map6.put("log4net:message",
				"Event card " + Constants.patternWord + " (" + Constants.patternWord + " on "
						+ Constants.patternWord + ") clicked on "
						+ Constants.patternWord + " (" + Constants.patternWord + ") in "
						+ Constants.patternWord);
		map6.put("UserName", "techrep");
		map6.put("SourceIP", Constants.patternBracketIp);
		map6.put("Action", "Event Card Click");
		
		return mapsMatch(map2,map3,map4,map5,map6);
	}
	
	public boolean _Search_for_Related_Event_Saved_Search() {                                     //锟斤拷锟斤拷contents锟斤拷锟斤拷锟斤拷锟�? fromToDate
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Saved search " + Constants.patternWord +" with the following details: " + Constants.patternWord + " Date and Time Range: "+ Constants.ptnDate + " - " + Constants.ptnDate + " Days: " + Constants.patternWord + " Time: " + Constants.patternWord + " Cameras "  + Constants.patternWord + " Events " + Constants.patternWord
				 
				 +" Include events within 3 minutes of the events above that meet the following criteria: "
				 + " Face Events "+ Constants.patternWord
				 );
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Save Search");
		
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Signed into" + Constants.patternWord + " "
				+ Constants.patternBracketIp + "()");
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Sign In");
		
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message", "Connected to appliance " + Constants.patternWord
				+ Constants.patternBracketIp);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Appliance Connect");
		
		return mapsMatch(map);
		
		}
	
	public boolean _Search_for_Related_Event_Conbination() {                                     //锟斤拷锟斤拷contents锟斤拷锟斤拷锟斤拷锟�? fromToDate
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Searched for: Total Time Range: " + Constants.fromToDate +" User Selected Range: " + Constants.patternWord + " Days: " + Constants.patternWord + " Time: " + Constants.patternWord + " Channels: "  + Constants.patternWords() + " Events: " + Constants.patternWord
				 + " Including related events that occured within 3 minutes that meet the following criteria:"
				 + " Events: " + Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Search");
		return mapsMatch(map);
	}
	
	
	public boolean _Search_Latest_3_Days_Face_Events_Associated_Motion_Within_3_Mins() {                                     //锟斤拷锟斤拷contents锟斤拷锟斤拷锟斤拷锟�? fromToDate
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Searched for: Total Time Range: " + Constants.fromToDate +" User Selected Range: " + Constants.patternWord + " Days: " + Constants.patternWord + " Time: " + Constants.patternWord + " Channels: "  + Constants.patternWords() + " Events: " + Constants.patternWord
				 + " Including related events that occured within 3 minutes that meet the following criteria:"
				 + " Events: " + Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Search");
		return mapsMatch(map);
	}

	public boolean _Similarity_Search_for_Person_Created_Previously() {                                    //锟侥达拷锟斤�?, Constants.fromToDate
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Searched for: Total Time Range: " + Constants.fromToDate + " User Selected Range: " + Constants.patternWord + " Days: " + Constants.patternWord + " Time: " + Constants.patternWord + " Channels: " + Constants.patternWord
				+ " Events: "+ Constants.patternWord + " "
			    + "Input Types: " + Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Search");
		return mapsMatch(map);
	}

	public boolean _Switch_To_People_Panel() {                                                     // (MainForm) 锟斤拷为"(" + Constants.patternWord + ")
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Tab changed to " + Constants.patternWord
				+ " on " + Constants.patternWord + " (" + Constants.patternWord + ") in"
				+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Tab Change");
		return mapsMatch(map);
	}
   
	public boolean _Edit_Person_Card() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Created new person " + Constants.patternWord + " (" + Constants.patternWord + ")"
				);     
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Create Person");
		
	
		
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message",
				"Modified person " + Constants.patternWord + " (" + Constants.patternWord + ")"
				+ "Changed group from " + Constants.patternWord + "to" + Constants.patternWord
				+ "Changed first name from " + Constants.patternWord + "to" + Constants.patternWord
				+ "Changed last name from " + Constants.patternWord + "to" + Constants.patternWord
				+ "Changed Employee ID from " + Constants.patternWord + "to" + Constants.patternWord
				+ "Changed Hair Color from " + Constants.patternWord + "to" + Constants.patternWord
				);     
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Modify Person");
		return mapsMatch(map,map2);
		
	}	
	
	
	public boolean _Add_and_Edit_Notes_to_Person() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Added " + Constants.patternWord + " " + Constants.patternWord
				+ " to person " + Constants.patternWord);     
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Add Note");
		
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message",
				"Added " + Constants.patternWord + " " + Constants.patternWord
				+ " to person " + Constants.patternWord);    
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Add Note");
		
		return mapsMatch(map,map2);
	}

	public boolean _Add_New_Group_And_Add_Member() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Added group " + Constants.patternWord 
				);     
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Add Group");
		
		/*Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", Constants.patternWord +
				" selected from ComboBox " + Constants.patternWord 
				+ " on " + Constants.patternWord + " (" + Constants.patternWord + ") in OpCenter");     
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "List Item Selected");
	*/	
		return mapsMatch(map);
	}

	public boolean _Delete_Person_Group() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Deleted group " + Constants.patternWord 
				);     
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Delete Group");
		
		return mapsMatch(map);
	}
	
	public boolean _Edit_Person_Group() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Modified group " + Constants.patternWord
				+ " Changed group name from " + Constants.patternWord + " to " + Constants.patternWord
			//	+ " Changed group description from " + Constants.patternWord + "to" + Constants.patternWord
				);     
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Modify Group");
		
		return mapsMatch(map);
	}
	
	public boolean _Create_Person_Card_From_Imported_Image() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Created new person " + Constants.patternWord + " (" + Constants.patternWord + ")"
			
				);     
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Create Person");
		
		return mapsMatch(map);
	}
	
	
	public boolean _Check_Person_Created_From_Monitor_Panel_Exist() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", Constants.patternWord
				+ " selected from ComboBox " + Constants.patternWord + "on"
				+ Constants.patternWord + " ("+ Constants.patternWord + ") in OpCenter");     //   (MainForm) 锟斤拷为"(" + Constants.patternWord + ")
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "List Item Selected");
		return mapsMatch(map);
	}

	// public boolean Func _Check_Person_Info() {
	// Map<String, String> map = new TreeMap<String, String>();
	// map.put("log4net:message", Constants.patternWord +
	// " selected from ComboBox " + Constants.patternWord + "on" +
	// Constants.patternWord +"(PeopleTabPanel) in OpCenter");
	// map.put("UserName", "techrep");
	// map.put("SourceIP", Constants.patternBracketIp);
	// map.put("Action", "List Item Selected");
	// return mapsMatch(map);
	// }

	public boolean _Check_Each_Tab_Under_Person_Info() {                                      //  (PeopleTabPanel) 锟斤拷为"(" + Constants.patternWord + ")
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Tab changed to " + Constants.patternWord
				+ " on " + Constants.patternWord + " (" + Constants.patternWord + ") in "
				+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Tab Change");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Tab changed to " + Constants.patternWord
				+ " on " + Constants.patternWord + " (" + Constants.patternWord + ") in "
				+ Constants.patternWord);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Tab Change");
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message", "Tab changed to " + Constants.patternWord
				+ " on " + Constants.patternWord + " (" + Constants.patternWord + ") in "
				+ Constants.patternWord);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Tab Change");
		return mapsMatch(map,map2,map3);
	}

	public boolean _Switch_To_Alerts_Panel() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Tab changed to " + Constants.patternWord                            //add space behind 'to'
				+ " on " + Constants.patternWord + " (" + Constants.patternWord + ") in "               //use " (" + Constants.patternWord + ") in " replace "(MainForm) in"
				+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Tab Change");
		return mapsMatch(map);
	}

	public boolean _Create_A_New_Action() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Added settings change action "                                                       //add space behind 'action'
						+ Constants.patternWord
						+ " with details: " + Constants.patternWord + "Post Alert Recording Period " + Constants.patternWord + "Cameras 'Cameras Selected: None"); //" with details: Scheduled Values Post Alert Recording Period  # of Seconds: 120 Cameras  Cameras Selected: None "
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Add Settings Change Action");
		return mapsMatch(map);
	}

	public boolean _Edit_An_Action() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Modified settings change action "
						+ Constants.patternWord
						+ ", the new settings are: " + Constants.patternWord + "Post Alert Recording Period " + Constants.patternWord + "Cameras 'Cameras Selected: None");
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Modify Settings Change Action");
		return mapsMatch(map);
	}

	// public boolean _Delete_An_Action()             no log in the case Excel for this action
	

	public boolean _Create_A_New_Alert() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Added alert: id = " + Constants.patternWord + ", name = "              //"Added alert: id = 117, name =" change to "Added alert: id = " + Constants.patternWord + ", name = " 
						+ Constants.patternWord
						//add '.'
                        +". Alert details: " + Constants.patternWord + "Camera #: " + Constants.patternWord + "Face Events" + Constants.patternWord);    //" Alert details: All days, any time Camera #: 1 Face EventsAll face events " change to " Alert details: " + Constants.patternWord + " Camera #: " + Constants.patternWord + " Face Events" + Constants.patternWord
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Add Alert");
		return mapsMatch(map);
	}

	public boolean _Edit_An_Alert_Twice() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Modified alert: id = "                                  //Added alert change to 
		                + Constants.patternWord + ", name = "         //117, name =" change to "Added alert: id = " + Constants.patternWord + ", name = "
						+ Constants.patternWord
						+ ". Alert details: " + Constants.patternWord + " Camera #: " + Constants.patternWord + " Motion Events" + Constants.patternWord);       //" Alert details: All days, any time Camera #: 1 Motion EventsAll motion events " change to " Alert details: " + Constants.patternWord + " Camera #: " + Constants.patternWord + " Motion Events" + Constants.patternWord
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Modified Alert");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message",
				"Modified alert: id = " + Constants.patternWord + ", name = "                       //"Added alert: id = 117, name =" change to "Added alert: id = " + Constants.patternWord + ", name = "
						+ Constants.patternWord
						+ ". Alert details: " + Constants.patternWord + "Camera #: " + Constants.patternWord + " Motion Events" + Constants.patternWord);    //" Alert details: All days, any time Camera #: 2 Motion EventsAll motion events " change to " Alert details: " + Constants.patternWord + " Camera #: " + Constants.patternWord + " Motion Events" + Constants.patternWord
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Modified Alert");
		return mapsMatch(map,map2);
	}

	public boolean _View_Alerts_Events_In_Monitor_Panel() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Tab changed to " + Constants.patternWord      //add space behind 'to'
				+ " on " + Constants.patternWord + " (" + Constants.patternWord + ") in "          //"(AlertsTabPanel) in" change to " (" + Constants.patternWord + ") in "
				+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Tab Change");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Enabled alert " + Constants.patternWord
				+ " (" + Constants.patternWord + ")");                                                  //" (#117) " change to " (" + Constants.patternWord + ")"
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Enable Alert");
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message", "Tab changed to " + Constants.patternWord                                   //add space behind 'to'
				+ " on " + Constants.patternWord + " (" + Constants.patternWord + ") in "                          //"(MainForm) in"
				+ Constants.patternWord);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Tab Change");                                 //change lower 'c' to upper 'C'
		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message", "Button " + Constants.patternWord
				+ " clicked on " + Constants.patternWord + " (" + Constants.patternWord + ") in "                       //"(ChannelSelector) in" 
				+ Constants.patternWord);
		map4.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Button Click");
		Map<String, String> map5 = new TreeMap<String, String>();
		map5.put("log4net:message", "Checkbox " + Constants.patternWord
				+ " checked on " + Constants.patternWord + " (" + Constants.patternWord + ") in "
				+ Constants.patternWord);
		map5.put("UserName", "techrep");
		map5.put("SourceIP", Constants.patternBracketIp);
		map5.put("Action", "Checkbox Click");
		Map<String, String> map6 = new TreeMap<String, String>();
		map6.put("log4net:message", "Checkbox" + Constants.patternWord
				+ " checked on " + Constants.patternWord + " (" + Constants.patternWord + ") in "                                //"(CheckboxRow) in"
				+ Constants.patternWord);
		map6.put("UserName", "techrep");
		map6.put("SourceIP", Constants.patternBracketIp);
		map6.put("Action", "Checkbox Click");
		return mapsMatch(map,map2,map3,map4,map5,map6);
	}

	public boolean _Delete_An_Alert_Via_Edit() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Deleted alert: id = " + Constants.patternWord + ", name = "                        //"Deleted alert: id = 117, name =" 
				+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Delete Alert");
		return mapsMatch(map);
	}

	public boolean _Switch_To_Cases_Panel() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Tab changed to " + Constants.patternWord              //add space behind 'to'
				+ " on " + Constants.patternWord 
				+ " (" + Constants.patternWord + ") in "                        //"(MainForm) in" 
				+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Tab Change");
		return mapsMatch(map);
	}

	public boolean _Create_A_New_Case() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Created case" + Constants.patternWord
				+ " (" + Constants.patternWord + ")");                                        //" (2013-03-11.0848) "
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Create Case");
		return mapsMatch(map);
	}

	public boolean _View_Case_Details() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Button " + Constants.patternWord                 //add space behind 'Button'
				+ " clicked on " + Constants.patternWord
				+ " (" + Constants.patternWord + ") in "                            //"(CaseDetailsTabPanel) in"
				+ Constants.patternWord);               
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Button Click");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", "Button " + Constants.patternWord                //add space behind 'Button'
				+ " clicked on " + Constants.patternWord
				+ " (" + Constants.patternWord + ") in "                                             // "(CasesTabPanel) in"
				+ Constants.patternWord);                    
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Button Click");
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message", "Viewing case details for " + Constants.patternWord      //add space behind 'for'   
				+ " (" + Constants.patternWord + ")");                                                    //" (2013-03-11.0848) "
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "View Case Details");
		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message", "Tab changed to " + Constants.patternWord                // //add space behind 'to'
				+ " on " + Constants.patternWord
				+ " (" + Constants.patternWord + ") in "                                                //"(CaseDetailsTabPanel) in"
				+ Constants.patternWord);
		map4.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Tab Change");
		Map<String, String> map5 = new TreeMap<String, String>();
		map5.put("log4net:message", "Tab changed to " + Constants.patternWord          //add space          
				+ " on " + Constants.patternWord
				+ " (" + Constants.patternWord + ") in "                                           //"(CaseDetailsTabPanel) in"
				+ Constants.patternWord);
		map5.put("UserName", "techrep");
		map5.put("SourceIP", Constants.patternBracketIp);
		map5.put("Action", "Tab Change");
		Map<String, String> map6 = new TreeMap<String, String>();
		map6.put("log4net:message", "Tab changed to " + Constants.patternWord            //add space
				+ " on " + Constants.patternWord
				+ " (" + Constants.patternWord + ") in "                                             //"(CaseDetailsTabPanel) in"
				+ Constants.patternWord);
		map6.put("UserName", "techrep");
		map6.put("SourceIP", Constants.patternBracketIp);
		map6.put("Action", "Tab Change");
		Map<String, String> map7 = new TreeMap<String, String>();
		map7.put("log4net:message", "Tab changed to " + Constants.patternWord                //add space
				+ " on " + Constants.patternWord
				+ " (" + Constants.patternWord + ") in "                                            // "(CaseDetailsTabPanel) in"
				+ Constants.patternWord);
		map7.put("UserName", "techrep");
		map7.put("SourceIP", Constants.patternBracketIp);
		map7.put("Action", "Tab Change");
		Map<String, String> map8 = new TreeMap<String, String>();
		map8.put("log4net:message", "Tab changed to " + Constants.patternWord               //add space
				+ " on " + Constants.patternWord
				+ " (" + Constants.patternWord + ") in "                                           //"(CaseDetailsTabPanel) in"
				+ Constants.patternWord);
		map8.put("UserName", "techrep");
		map8.put("SourceIP", Constants.patternBracketIp);
		map8.put("Action", "Tab Change");
		return mapsMatch(map,map2,map3,map4,map5,map6,map7,map8);
	}

	public boolean _Export_A_Case() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Button" + Constants.patternWord
				+ " clicked on " + Constants.patternWord 
				+ " (" + Constants.patternWord + ") in "          //"(CasesTabPanel) in"
				+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Button Click");
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message", Constants.patternWord
				+ " on " + Constants.patternWord 
				+ " clicked on " + Constants.patternWord + " in "            //"clicked on [Unknown Control] in"
				+ Constants.patternWord);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "TreeNode Click");                  //log match: need to manually click triangle to choose location
		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message", "Checkbox " + Constants.patternWord
				+ " checked on " + Constants.patternWord 
				+ " (" + Constants.patternWord + ") in "                           //"(XmlExportControl) in"
				+ Constants.patternWord);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Checkbox Click");
		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message", "Checkbox " + Constants.patternWord
				+ " checked on " + Constants.patternWord 
				+ " (" + Constants.patternWord + ") in "                               //"(XmlExportControl) in"
				+ Constants.patternWord);
		map4.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Checkbox Click");
		Map<String, String> map5 = new TreeMap<String, String>();
		map5.put("log4net:message", "Checkbox " + Constants.patternWord
				+ " checked on " + Constants.patternWord 
				+ " (" + Constants.patternWord + ") in "                   //"(XmlExportControl) in"
				+ Constants.patternWord);
		map5.put("UserName", "techrep");
		map5.put("SourceIP", Constants.patternBracketIp);
		map5.put("Action", "Checkbox Click");
		Map<String, String> map6 = new TreeMap<String, String>();
		map6.put("log4net:message", "Initiated XML import of type " + Constants.patternWord
				+ " to file " + Constants.patternWord);
		map6.put("UserName", "techrep");
		map6.put("SourceIP", Constants.patternBracketIp);
		map6.put("Action", "Export XML");
		System.out.println("_Export_A_Case");
		return mapsMatch(map,map2,map3,map4,map5,map6);
	}

	public boolean _Delete_A_Case() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Deleted case " + Constants.patternWord
				+ " (" + Constants.patternWord + ")");           //" (2013-03-11.0848) "
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Delete Case");
		return mapsMatch(map);
	}

	public boolean _3VR_Icon_Launch_SM() {
		
		Map<String, String> map = new TreeMap<String, String>();
	    map.put("log4net:message",
				"Clicked menu item "+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Menu Item Click");
		
		
		Map<String, String> map2 = new TreeMap<String, String>();
	    map2.put("log4net:message", "Signed into System Manager ("
				+ Constants.patternWord + Constants.patternBracketIp + ")");
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Sign In");
	 
		return mapsMatch(map,map2);
	}
	

	public boolean _Check_Serial__Software_Version_AppConn() {
		
		Map<String, String> map = new TreeMap<String, String>();
	    map.put("log4net:message",
				"Initial message:"+ Constants.patternWord
				 +"Software Version: "+ Constants.patternWord
				 +"OS Version: "+ Constants.patternWord
				 +"Feature Set: "+ Constants.patternWord
				 +"Serial Number: "+ Constants.patternWord
				 +"Part Number: "+ Constants.patternWord
				 +"Boot stats: Boot time: " + Constants.ptnDate + " " + Constants.ptnDate + " (GMT-11:00)"
				 +"Dirty shutdowns: "+ Constants.patternWord
				 +"Recent dirty shutdowns: "+ Constants.ptnDate + " " + Constants.ptnDate + " (GMT-11:00)"
				);
	
	    Map<String, String> map2 = new TreeMap<String, String>();
	    map2.put("log4net:message", 
	    		Constants.patternWord + "clicked on " + Constants.patternWord + " in " + Constants.patternWord
	    		);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Menu Link Click");
		
        Map<String, String> map3 = new TreeMap<String, String>();
	    map3.put("log4net:message", 
	    		"Tab changed to " + Constants.patternWord + " on " + Constants.patternWord
	    		+ " (" +  Constants.patternWord + ") in "+ Constants.patternWord
				);
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Tab Change");
	 
		Map<String, String> map4 = new TreeMap<String, String>();
	    map4.put("log4net:message", 
	    		"Tab changed to " + Constants.patternWord + " on " + Constants.patternWord
	    		+ " (" +  Constants.patternWord + ") in "+ Constants.patternWord
				);
		map4.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Tab Change");
		
		Map<String, String> map5 = new TreeMap<String, String>();
	    map5.put("log4net:message", 
	    		"Tab changed to " + Constants.patternWord + " on " + Constants.patternWord
	    		+ " (" +  Constants.patternWord + ") in "+ Constants.patternWord
				);
		map5.put("UserName", "techrep");
		map5.put("SourceIP", Constants.patternBracketIp);
		map5.put("Action", "Tab Change");
		
		Map<String, String> map6 = new TreeMap<String, String>();
	    map6.put("log4net:message", 
	    		"Button " + Constants.patternWord + " clicked on " + Constants.patternWord
	    		+ " (" +  Constants.patternWord + ") in "+ Constants.patternWord
				);
		map6.put("UserName", "techrep");
		map6.put("SourceIP", Constants.patternBracketIp);
		map6.put("Action", "Button Click");
		
		return mapsMatch(map,map2,map3,map4,map5,map6);
	}
	
	
	
     public boolean _3VR_Icon_About_OpCenter_And_Cancel() {
		
		Map<String, String> map = new TreeMap<String, String>();
	    map.put("log4net:message",
				"Clicked menu item "+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Menu Item Click");
		
		
		Map<String, String> map2 = new TreeMap<String, String>();
	    map2.put("log4net:message", 
	    		"Button " + Constants.patternWord + " clicked on " + Constants.patternWord
	    		+ " (" +  Constants.patternWord + ") in "+ Constants.patternWord
				);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Button Click");
	 
		return mapsMatch(map,map2);
	}
	

     
     public boolean _Email_Event_To_One_Or_Multiple_Recipients() {
  		
  	    Map<String, String> map = new TreeMap<String, String>();
  	    map.put("log4net:message", 
  	    		"Email with subject " + Constants.patternWord + " sent from " + Constants.patternWord
  	    		+ " to: " +  Constants.patternWord
  				);
  		map.put("UserName", "techrep");
  		map.put("SourceIP", Constants.patternBracketIp);
  		map.put("Action", "Email Send");
  	 
  		Map<String, String> map2 = new TreeMap<String, String>();
  	    map2.put("log4net:message", 
  	    		"Email with subject " + Constants.patternWord + " sent from " + Constants.patternWord
  	    		+ " to: " +  Constants.patternWord + "," +  Constants.patternWord
  				);
  		map2.put("UserName", "techrep");
  		map2.put("SourceIP", Constants.patternBracketIp);
  		map2.put("Action", "Email Send");
  		
  		
  		return mapsMatch(map,map2);
  	}
     
   
     
     public boolean _Email_Event_From_Core_Without_Enterprise_Email_Address_Configured() {
   		
   	    Map<String, String> map = new TreeMap<String, String>();
   	    map.put("log4net:message", 
   	    		"Email with subject " + Constants.patternWord + " sent from " + Constants.patternWord
   	    		+ " to: " +  Constants.patternWord
   				);
   		map.put("UserName", "techrep");
   		map.put("SourceIP", Constants.patternBracketIp);
   		map.put("Action", "Email Send");
   	 
   		return mapsMatch(map);
   	}
     
    /* public boolean _Open_Multiple_Opc_To_Same_Server_Different_Cores() {
 		Map<String, String> map = new TreeMap<String, String>();
 		map.put("log4net:message", "Signed into OpCenter (" + Constants.patternWord + " "
 				+ Constants.patternBracketIp + ")");
 		map.put("UserName", "techrep");
 		map.put("SourceIP", Constants.patternBracketIp);
 		map.put("Action", "Sign In");
 		Map<String, String> map2 = new TreeMap<String, String>();
 		map2.put("log4net:message", "Connected to appliance " + Constants.patternWord + " "
 				+ Constants.patternBracketIp);
 		map2.put("UserName", "techrep");
 		map2.put("SourceIP", Constants.patternBracketIp);
 		map2.put("Action", "Appliance Connect");
 		return mapsMatch(map, map2);
     }
     
     */
     public boolean _open_Two_Opc_And_Export_Video() {
		  Map<String, String> map = new TreeMap<String, String>();
		   map.put("log4net:message", "Video export initialized with the following options: Start Time: "+ Constants.patternWord 
		                        + "End Time: "                               //" AM End Time: "
		                              + Constants.patternWord +"Selected Path: "+ Constants.patternWord +"Total Minutes of Video: "+ Constants.patternNumber +"Cameras: "
		                        + Constants.patternWord 
		                              + Constants.patternBracketIp
		                              + ": " + Constants.patternWords()
		                              + Constants.patternBracketIp 
		                              + "" + Constants.patternWords());
		  map.put("UserName", "techrep");
		  map.put("SourceIP", Constants.patternBracketIp);
		  map.put("Action", "Video Export Initiated");
		  
		  Map<String, String> map2 = new TreeMap<String, String>();
	   	    map2.put("log4net:message", 
	   	    		"Tab changed to " + Constants.patternWord + " on " + Constants.patternWord
	   	    		+ " (" + Constants.patternWord + ") " +  Constants.patternWord
	   				);
	   		map2.put("UserName", "techrep");
	   		map2.put("SourceIP", Constants.patternBracketIp);
	   		map2.put("Action", "Tab Change");
		  
	   		Map<String, String> map3 = new TreeMap<String, String>();
	   		map3.put("log4net:message", 
	   	    		"Tab changed to " + Constants.patternWord + " on " + Constants.patternWord
	   	    		+ " (" + Constants.patternWord + ") " +  Constants.patternWord
	   				);
	   		map3.put("UserName", "techrep");
	   		map3.put("SourceIP", Constants.patternBracketIp);
	   		map3.put("Action", "Tab Change");
	   		
	   		Map<String, String> map4 = new TreeMap<String, String>();
	   		map4.put("log4net:message", 
	   	    		"Tab changed to " + Constants.patternWord + " on " + Constants.patternWord
	   	    		+ " (" + Constants.patternWord + ") " +  Constants.patternWord
	   				);
	   		map4.put("UserName", "techrep");
	   		map4.put("SourceIP", Constants.patternBracketIp);
	   		map4.put("Action", "Tab Change");
	   		
	   		
	   		Map<String, String> map5 = new TreeMap<String, String>();
	   		map5.put("log4net:message", 
	   	    		"Tab changed to " + Constants.patternWord + " on " + Constants.patternWord
	   	    		+ " (" + Constants.patternWord + ") " +  Constants.patternWord
	   				);
	   		map5.put("UserName", "techrep");
	   		map5.put("SourceIP", Constants.patternBracketIp);
	   		map5.put("Action", "Tab Change");
	   		
	   		
	   		return mapsMatch(map,map2,map3,map4,map5);
		 }
     
     public boolean _Switch_Cores_Repeatedly_In_Multi_windows() {
   		
        Map<String, String> map = new TreeMap<String, String>();
   	    map.put("log4net:message", 
   	    		"Appliance switched from " + Constants.patternWord + " "
   	    		+ Constants.patternBracketIp
   	    		+ " to " +  Constants.patternWord + " " + Constants.patternBracketIp
   				);
   		map.put("UserName", "techrep");
   		map.put("SourceIP", Constants.patternBracketIp);
   		map.put("Action", "Appliance Switch");
   		
   	    Map<String, String> map2 = new TreeMap<String, String>();
	    map.put("log4net:message", 
	    		"Appliance switched from " + Constants.patternWord + " "
	    		+ Constants.patternBracketIp
	    		+ " to " +  Constants.patternWord + " " + Constants.patternBracketIp
				);
		map2.put("UserName", "techrep");
		map2.put("SourceIP", Constants.patternBracketIp);
		map2.put("Action", "Appliance Switch");
   		
		 Map<String, String> map3 = new TreeMap<String, String>();
	   	    map.put("log4net:message", 
	   	    		"Appliance switched from " + Constants.patternWord + " "
	   	    		+ Constants.patternBracketIp
	   	    		+ " to " +  Constants.patternWord + " " + Constants.patternBracketIp
	   				);
	   		map3.put("UserName", "techrep");
	   		map3.put("SourceIP", Constants.patternBracketIp);
	   		map3.put("Action", "Appliance Switch");
	   		
	   	 Map<String, String> map4 = new TreeMap<String, String>();
	   	    map4.put("log4net:message", 
	   	    		"Appliance switched from " + Constants.patternWord + " "
	   	    		+ Constants.patternBracketIp
	   	    		+ " to " +  Constants.patternWord + " " + Constants.patternBracketIp
	   				);
	   		map4.put("UserName", "techrep");
	   		map4.put("SourceIP", Constants.patternBracketIp);
	   		map4.put("Action", "Appliance Switch");
	   		
	   	 Map<String, String> map5 = new TreeMap<String, String>();
	   	    map5.put("log4net:message", 
	   	    		"Appliance switched from " + Constants.patternWord + " "
	   	    		+ Constants.patternBracketIp
	   	    		+ " to " +  Constants.patternWord + " " + Constants.patternBracketIp
	   				);
	   		map5.put("UserName", "techrep");
	   		map5.put("SourceIP", Constants.patternBracketIp);
	   		map5.put("Action", "Appliance Switch");
	   		
	   	 Map<String, String> map6 = new TreeMap<String, String>();
	   	    map6.put("log4net:message", 
	   	    		"Appliance switched from " + Constants.patternWord + " "
	   	    		+ Constants.patternBracketIp
	   	    		+ " to " +  Constants.patternWord + " " + Constants.patternBracketIp
	   				);
	   		map6.put("UserName", "techrep");
	   		map6.put("SourceIP", Constants.patternBracketIp);
	   		map6.put("Action", "Appliance Switch");
	   		
	   	 Map<String, String> map7 = new TreeMap<String, String>();
	   	    map7.put("log4net:message", 
	   	    		"Appliance switched from " + Constants.patternWord + " "
	   	    		+ Constants.patternBracketIp
	   	    		+ " to " +  Constants.patternWord + " " + Constants.patternBracketIp
	   				);
	   		map7.put("UserName", "techrep");
	   		map7.put("SourceIP", Constants.patternBracketIp);
	   		map7.put("Action", "Appliance Switch");
   		
	   		
	   		Map<String, String> map8 = new TreeMap<String, String>();
	   	    map8.put("log4net:message", 
	   	    		"Appliance switched from " + Constants.patternWord + " "
	   	    		+ Constants.patternBracketIp
	   	    		+ " to " +  Constants.patternWord + " " + Constants.patternBracketIp
	   				);
	   		map8.put("UserName", "techrep");
	   		map8.put("SourceIP", Constants.patternBracketIp);
	   		map8.put("Action", "Appliance Switch");
	   		
   	   return mapsMatch(map,map2,map3,map4,map5,map6,map7,map8);
   	}
     
     
     public boolean _Add_Motion_Event_To_New_Case_From_Case_Tab() {
    	 
    	 Map<String, String> map = new TreeMap<String, String>();
 		map.put("log4net:message", "Button " + Constants.patternWord
 				+ " clicked on " + Constants.patternWord 
 				+ " ("  + Constants.patternWord + ") in "  + Constants.patternWord 
 				);                                        
 		map.put("UserName", "techrep");
 		map.put("SourceIP", Constants.patternBracketIp);
 		map.put("Action", "Button Click");
    	 
    	 
 		Map<String, String> map2 = new TreeMap<String, String>();
 		map2.put("log4net:message", "Created case " + Constants.patternWord
 				+ " (" + Constants.patternWord + ")");                                        
 		map2.put("UserName", "techrep");
 		map2.put("SourceIP", Constants.patternBracketIp);
 		map2.put("Action", "Create Case");
 		
 		Map<String, String> map3 = new TreeMap<String, String>();
 		map3.put("log4net:message", "Case " + Constants.patternWord
 				+ " from server " + Constants.patternIp 
 				+ "; Added " + Constants.patternWord +" event to case."
 				);                                        
 		map3.put("UserName", "techrep");
 		map3.put("SourceIP", Constants.patternBracketIp);
 		map3.put("Action", "Added Events to Case");
 		
 		
 		return mapsMatch(map,map2,map3);
 	}
     
     
     public boolean _Add_Motion_Event_To_New_Case_By_Rightclicking() {
    	 
    	Map<String, String> map = new TreeMap<String, String>();
   		map.put("log4net:message", "Menu item " + Constants.patternWord
   				+ " clicked");                                        
   		map.put("UserName", "techrep");
   		map.put("SourceIP", Constants.patternBracketIp);
   		map.put("Action", "Menu Item Click");
    	 
    	 
  		Map<String, String> map2 = new TreeMap<String, String>();
  		map2.put("log4net:message", "Created case " + Constants.patternWord
  				+ " (" + Constants.patternWord + ")");                                        
  		map2.put("UserName", "techrep");
  		map2.put("SourceIP", Constants.patternBracketIp);
  		map2.put("Action", "Create Case");
  		
  		Map<String, String> map3 = new TreeMap<String, String>();
  		map3.put("log4net:message", "Case " + Constants.patternWord
  				+ " from server " + Constants.patternIp 
  				+ "; Added " + Constants.patternWord +" event to case."
  				);                                        
  		map3.put("UserName", "techrep");
  		map3.put("SourceIP", Constants.patternBracketIp);
  		map3.put("Action", "Added Events to Case");
  		
  		
  		return mapsMatch(map,map2,map3);
  	}
     
     
 
     
     public boolean _Add_Motion_Event_To_Existing_Case_From_Case_Tab() {
  	
    	 Map<String, String> map = new TreeMap<String, String>();
  		map.put("log4net:message", "Button " + Constants.patternWord
  				+ " clicked on " + Constants.patternWord 
  				+ " ("  + Constants.patternWord + ") in "  + Constants.patternWord 
  				);                                        
  		map.put("UserName", "techrep");
  		map.put("SourceIP", Constants.patternBracketIp);
  		map.put("Action", "Button Click");
    	 
    	 
  		Map<String, String> map2 = new TreeMap<String, String>();
  		map2.put("log4net:message", "Case " + Constants.patternWord
  				+ " from server " + Constants.patternIp 
  				+ "; Added " + Constants.patternWord +" event to case."
  				);                                        
  		map2.put("UserName", "techrep");
  		map2.put("SourceIP", Constants.patternBracketIp);
  		map2.put("Action", "Added Events to Case");
  		
  		
  		return mapsMatch(map,map2);
  	}
     
     
     public boolean _Add_Motion_Event_To_Existing_Case_By_Rightclicking() {
    	 
     	Map<String, String> map = new TreeMap<String, String>();
    		map.put("log4net:message", "Menu item " + Constants.patternWord
    				+ " clicked");                                        
    		map.put("UserName", "techrep");
    		map.put("SourceIP", Constants.patternBracketIp);
    		map.put("Action", "Menu Item Click");
     	 
   		
   		Map<String, String> map2 = new TreeMap<String, String>();
   		map2.put("log4net:message", "Case " + Constants.patternWord
   				+ " from server " + Constants.patternIp
   				+ "; Added " + Constants.patternWord +" event to case."
   				);                                        
   		map2.put("UserName", "techrep");
   		map2.put("SourceIP", Constants.patternBracketIp);
   		map2.put("Action", "Added Events to Case");
   		
   		
   		return mapsMatch(map,map2);
   	}

public boolean _Add_Face_Event_To_New_Case_From_Case_Tab() {
    	 
    	 Map<String, String> map = new TreeMap<String, String>();
 		map.put("log4net:message", "Button " + Constants.patternWord
 				+ " clicked on " + Constants.patternWord 
 				+ " ("  + Constants.patternWord + ") in "  + Constants.patternWord 
 				);                                        
 		map.put("UserName", "techrep");
 		map.put("SourceIP", Constants.patternBracketIp);
 		map.put("Action", "Button Click");
    	 
    	 
 		Map<String, String> map2 = new TreeMap<String, String>();
 		map2.put("log4net:message", "Created case " + Constants.patternWord
 				+ " (" + Constants.patternWord + ")");                                        
 		map2.put("UserName", "techrep");
 		map2.put("SourceIP", Constants.patternBracketIp);
 		map2.put("Action", "Create Case");
 		
 		Map<String, String> map3 = new TreeMap<String, String>();
 		map3.put("log4net:message", "Case " + Constants.patternWord
 				+ " from server " + Constants.patternIp 
 				+ "; Added " + Constants.patternWord +" event to case."
 				);                                        
 		map3.put("UserName", "techrep");
 		map3.put("SourceIP", Constants.patternBracketIp);
 		map3.put("Action", "Added Events to Case");
 		
 		
 		return mapsMatch(map,map2,map3);
 	}
     
     
     public boolean _Add_Face_Event_To_New_Case_By_Rightclicking() {
    	 
    	Map<String, String> map = new TreeMap<String, String>();
   		map.put("log4net:message", "Menu item " + Constants.patternWord
   				+ " clicked");                                        
   		map.put("UserName", "techrep");
   		map.put("SourceIP", Constants.patternBracketIp);
   		map.put("Action", "Menu Item Click");
    	 
    	 
  		Map<String, String> map2 = new TreeMap<String, String>();
  		map2.put("log4net:message", "Created case " + Constants.patternWord
  				+ " (" + Constants.patternWord + ")");                                        
  		map2.put("UserName", "techrep");
  		map2.put("SourceIP", Constants.patternBracketIp);
  		map2.put("Action", "Create Case");
  		
  		Map<String, String> map3 = new TreeMap<String, String>();
  		map3.put("log4net:message", "Case " + Constants.patternWord
  				+ " from server " + Constants.patternIp 
  				+ "; Added " + Constants.patternWord +" event to case."
  				);                                        
  		map3.put("UserName", "techrep");
  		map3.put("SourceIP", Constants.patternBracketIp);
  		map3.put("Action", "Added Events to Case");
  		
  		
  		return mapsMatch(map,map2,map3);
  	}
     
     public boolean _Add_Face_Event_To_Existing_Case_From_Case_Tab() {
    	  	
    	 Map<String, String> map = new TreeMap<String, String>();
  		map.put("log4net:message", "Button " + Constants.patternWord
  				+ " clicked on " + Constants.patternWord 
  				+ " ("  + Constants.patternWord + ") in "  + Constants.patternWord 
  				);                                        
  		map.put("UserName", "techrep");
  		map.put("SourceIP", Constants.patternBracketIp);
  		map.put("Action", "Button Click");
    	 
    	 
  		Map<String, String> map2 = new TreeMap<String, String>();
  		map2.put("log4net:message", "Case " + Constants.patternWord
  				+ " from server " + Constants.patternIp 
  				+ "; Added " + Constants.patternWord +" event to case."
  				);                                        
  		map2.put("UserName", "techrep");
  		map2.put("SourceIP", Constants.patternBracketIp);
  		map2.put("Action", "Added Events to Case");
  		
  		
  		return mapsMatch(map,map2);
  	}
     
     
     public boolean _Add_Face_Event_To_Existing_Case_By_Rightclicking() {
    	 
     	Map<String, String> map = new TreeMap<String, String>();
    		map.put("log4net:message", "Menu item " + Constants.patternWord
    				+ " clicked");                                        
    		map.put("UserName", "techrep");
    		map.put("SourceIP", Constants.patternBracketIp);
    		map.put("Action", "Menu Item Click");
     	 
   		
   		Map<String, String> map2 = new TreeMap<String, String>();
   		map2.put("log4net:message", "Case " + Constants.patternWord
   				+ " from server " + Constants.patternIp
   				+ "; Added " + Constants.patternWord +" event to case."
   				);                                        
   		map2.put("UserName", "techrep");
   		map2.put("SourceIP", Constants.patternBracketIp);
   		map2.put("Action", "Added Events to Case");
   		
   		
   		return mapsMatch(map,map2);
   	} 

     
     public boolean _Add_Event_With_Alert_To_New_Case() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Added alert: id = " + Constants.patternWord + ", name = "              
						+ Constants.patternWord
						
                        +". Alert details: " + Constants.patternWord
                        + "Camera #: " + Constants.patternWord
                        + "Motion Events" + Constants.patternWord
                        );    
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Add Alert");
		
		Map<String, String> map2 = new TreeMap<String, String>();
  		map2.put("log4net:message", "Created case " + Constants.patternWord
  				+ " (" + Constants.patternWord + ")");                                        
  		map2.put("UserName", "techrep");
  		map2.put("SourceIP", Constants.patternBracketIp);
  		map2.put("Action", "Create Case");
  		
  		Map<String, String> map3 = new TreeMap<String, String>();
  		map3.put("log4net:message", "Case " + Constants.patternWord
  				+ " from server " + Constants.patternIp
  				+ "; Added " + Constants.patternWord +" event to case."
  				);                                        
  		map3.put("UserName", "techrep");
  		map3.put("SourceIP", Constants.patternBracketIp);
  		map3.put("Action", "Added Events to Case");
		
		
		return mapsMatch(map,map2,map3);
	}

     public boolean _Add_Event_With_Alert_To_Existing_Case() {
 		Map<String, String> map = new TreeMap<String, String>();
        map.put("log4net:message", "Case " + Constants.patternWord
   				+ " from server " + Constants.patternIp 
   				+ "; Added " + Constants.patternWord +" event to case."
   				);                                        
   		map.put("UserName", "techrep");
   		map.put("SourceIP", Constants.patternBracketIp);
   		map.put("Action", "Added Events to Case");
 		
 		
 		return mapsMatch(map);
 	}

     public boolean _Add_Event_With_Note_To_New_Case() {
 		Map<String, String> map = new TreeMap<String, String>();
 		map.put("log4net:message",
 				"Added Note " + Constants.patternWord 
 				+ " to " + Constants.patternEvent 
 				+" " +  Constants.patternWord
 				+" (" + Constants.ptnDate + " " + Constants.ptnTime + ")"
 				+ " on " +  Constants.patternWord
                );    
 		map.put("UserName", "techrep");
 		map.put("SourceIP", Constants.patternBracketIp);
 		map.put("Action", "Add Note");
 		
 		Map<String, String> map2 = new TreeMap<String, String>();
   		map2.put("log4net:message", "Created case " + Constants.patternWord
   				+ " (" + Constants.patternWord + ")");                                        
   		map2.put("UserName", "techrep");
   		map2.put("SourceIP", Constants.patternBracketIp);
   		map2.put("Action", "Create Case");
   		
   		Map<String, String> map3 = new TreeMap<String, String>();
   		map3.put("log4net:message", "Case " + Constants.patternWord
   				+ " from server " + Constants.patternIp 
   				+ "; Added " + Constants.patternWord +" event to case."
   				);                                        
   		map3.put("UserName", "techrep");
   		map3.put("SourceIP", Constants.patternBracketIp);
   		map3.put("Action", "Added Events to Case");
 		
 		
 		return mapsMatch(map,map2,map3);
 	}
     
     
     public boolean _Add_Event_With_Note_To_Existing_Case() {
  		Map<String, String> map = new TreeMap<String, String>();
         map.put("log4net:message", "Case " + Constants.patternWord
    				+ " from server " + Constants.patternIp
    				+ "; Added " + Constants.patternWord +" event to case."
    				);                                        
    		map.put("UserName", "techrep");
    		map.put("SourceIP", Constants.patternBracketIp);
    		map.put("Action", "Added Events to Case");
  		
  		
  		return mapsMatch(map);
  	}
    
     
     public boolean _Add_Event_For_Imported_Image_To_New_Case() {
  		Map<String, String> map = new TreeMap<String, String>();
  		map.put("log4net:message", "Created case " + Constants.patternWord
  				+ " (" + Constants.patternWord + ")");                                        
  		map.put("UserName", "techrep");
  		map.put("SourceIP", Constants.patternBracketIp);
  		map.put("Action", "Create Case");
  		
  		Map<String, String> map2 = new TreeMap<String, String>();
  		map2.put("log4net:message", "Case " + Constants.patternWord
  				+ " from server " + Constants.patternIp 
  				+ "; Added " + Constants.patternWord +" event to case."
  				);                                        
  		map2.put("UserName", "techrep");
  		map2.put("SourceIP", Constants.patternBracketIp);
  		map2.put("Action", "Added Events to Case");
  		
  		
  		return mapsMatch(map,map2);
  	}
     
     
     
     public boolean _Add_Event_For_Imported_Image_To_Existing_Case() {
   		Map<String, String> map = new TreeMap<String, String>();
          map.put("log4net:message", "Case " + Constants.patternWord
     				+ " from server " + Constants.patternIp 
     				+ "; Added " + Constants.patternWord +" event to case."
     				);                                        
     		map.put("UserName", "techrep");
     		map.put("SourceIP", Constants.patternBracketIp);
     		map.put("Action", "Added Events to Case");
   		
   		
   		return mapsMatch(map);
   	}
     
     
     public boolean _Add_Person_To_New_Case_And_View_Details() {
    	 
    	Map<String, String> map = new TreeMap<String, String>();
    		map.put("log4net:message", "Button " + Constants.patternWord
    				+ " clicked on " + Constants.patternWord 
    				+ " ("  + Constants.patternWord + ") in "  + Constants.patternWord 
    				);                                        
    		map.put("UserName", "techrep");
    		map.put("SourceIP", Constants.patternBracketIp);
    		map.put("Action", "Button Click");
    		
    		Map<String, String> map2 = new TreeMap<String, String>();
    		map2.put("log4net:message", "Link " + Constants.patternWord
    				+ " clicked on " + Constants.patternWord 
    				+ " ("  + Constants.patternWord + ") in "  + Constants.patternWord 
    				);                                        
    		map2.put("UserName", "techrep");
    		map2.put("SourceIP", Constants.patternBracketIp);
    		map2.put("Action", "Link Click");
    	 
   		Map<String, String> map3 = new TreeMap<String, String>();
   		map3.put("log4net:message", "Created case " + Constants.patternWord
   				+ " (" + Constants.patternWord + ")");                                        
   		map3.put("UserName", "techrep");
   		map3.put("SourceIP", Constants.patternBracketIp);
   		map3.put("Action", "Create Case");
   		
   		Map<String, String> map4 = new TreeMap<String, String>();
   		map4.put("log4net:message", "Case " + Constants.patternWord
   				+ " from server " + Constants.patternIp 
   				+ "; Added " + Constants.patternWord +" person to case."
   				);                                        
   		map4.put("UserName", "techrep");
   		map4.put("SourceIP", Constants.patternBracketIp);
   		map4.put("Action", "Added People to Case");
   		
   		
   		return mapsMatch(map,map2,map3,map4);
   	}
      
     public boolean _Add_Person_To_Existing_Case_By_RightClicking() {
    	 
     	Map<String, String> map = new TreeMap<String, String>();
     		map.put("log4net:message", "Menu item " + Constants.patternWord
     				+ " clicked"
     				
     				);                                        
     		map.put("UserName", "techrep");
     		map.put("SourceIP", Constants.patternBracketIp);
     		map.put("Action", "Menu Item Click");
     		
     		Map<String, String> map2 = new TreeMap<String, String>();
     		map2.put("log4net:message", "Link " + Constants.patternWord
     				+ " clicked on " + Constants.patternWord 
     				+ " ("  + Constants.patternWord + ") in "  + Constants.patternWord 
     				);                                        
     		map2.put("UserName", "techrep");
     		map2.put("SourceIP", Constants.patternBracketIp);
     		map2.put("Action", "Link Click");
     	 
    		Map<String, String> map3 = new TreeMap<String, String>();
    		map3.put("log4net:message", "Created case " + Constants.patternWord
    				+ " (" + Constants.patternWord + ")");                                        
    		map3.put("UserName", "techrep");
    		map3.put("SourceIP", Constants.patternBracketIp);
    		map3.put("Action", "Create Case");
    		
    		Map<String, String> map4 = new TreeMap<String, String>();
    		map4.put("log4net:message", "Case " + Constants.patternWord
    				+ " from server " + Constants.patternIp 
    				+ "; Added " + Constants.patternWord +" person to case."
    				);                                        
    		map4.put("UserName", "techrep");
    		map4.put("SourceIP", Constants.patternBracketIp);
    		map4.put("Action", "Added People to Case");
    		
    		
    		return mapsMatch(map,map2,map3,map4);
    	}
     
     
     public boolean _Add_Person_To_Existing_Case_From_Case_Tab() {
    	 
      	Map<String, String> map = new TreeMap<String, String>();
      		map.put("log4net:message", "Menu item " + Constants.patternWord
      				+ " clicked"
      				);                                        
      		map.put("UserName", "techrep");
      		map.put("SourceIP", Constants.patternBracketIp);
      		map.put("Action", "Menu Item Click");
      		
     		Map<String, String> map2 = new TreeMap<String, String>();
     		map2.put("log4net:message", "Case " + Constants.patternWord
     				+ " from server " + Constants.patternIp 
     				+ "; Added " + Constants.patternWord +" person to case."
     				);                                        
     		map2.put("UserName", "techrep");
     		map2.put("SourceIP", Constants.patternBracketIp);
     		map2.put("Action", "Added People to Case");
     		
     		return mapsMatch(map,map2);
     	}
      
     public boolean _Add_Person_With_Note_To_New_Case() {
    	 
      	Map<String, String> map = new TreeMap<String, String>();
      		map.put("log4net:message", "Added Note " + Constants.patternWord
      				+ " to person " + Constants.patternWord
      				
      				);                                        
      		map.put("UserName", "techrep");
      		map.put("SourceIP", Constants.patternBracketIp);
      		map.put("Action", "Add Note");
      	
     		Map<String, String> map2 = new TreeMap<String, String>();
     		map2.put("log4net:message", "Created case " + Constants.patternWord
     				+ " (" + Constants.patternWord + ")");                                        
     		map2.put("UserName", "techrep");
     		map2.put("SourceIP", Constants.patternBracketIp);
     		map2.put("Action", "Create Case");
     		
     		Map<String, String> map3 = new TreeMap<String, String>();
     		map3.put("log4net:message", "Case " + Constants.patternWord
     				+ " from server " + Constants.patternIp 
     				+ "; Added " + Constants.patternWord +" to case."
     				);                                        
     		map3.put("UserName", "techrep");
     		map3.put("SourceIP", Constants.patternBracketIp);
     		map3.put("Action", "Added People to Case");
     		
     		Map<String, String> map4 = new TreeMap<String, String>();
     		map4.put("log4net:message", "Tab changed to " + Constants.patternWord
     				+ " on " + Constants.patternWord 
     				+ " (" + Constants.patternWord + ") in " + Constants.patternWord
     				
     				);                                        
     		map4.put("UserName", "techrep");
     		map4.put("SourceIP", Constants.patternBracketIp);
     		map4.put("Action", "Tab Change");
     		
     		return mapsMatch(map,map2,map3,map4);
     	}
     
     public boolean _Add_Person_With_Metadata_To_Existing_Case() {
    	 
       	Map<String, String> map = new TreeMap<String, String>();
       		map.put("log4net:message", "Created new person " + Constants.patternWord
       				+ " (" + Constants.patternWord +")"
       				);                                        
       		map.put("UserName", "techrep");
       		map.put("SourceIP", Constants.patternBracketIp);
       		map.put("Action", "Create Person");
       
      		
      		Map<String, String> map2 = new TreeMap<String, String>();
      		map2.put("log4net:message", "Case " + Constants.patternWord
      				+ " from server " + Constants.patternIp 
      				+ "; Added " + Constants.patternWord +" to case."
      				);                                        
      		map2.put("UserName", "techrep");
      		map2.put("SourceIP", Constants.patternBracketIp);
      		map2.put("Action", "Added People to Case");
      		
      		
      		return mapsMatch(map,map2);
      	}
     
     
     public boolean _New_Case_and_Delete_Assignee_Valid() {
    	 
    		Map<String, String> map = new TreeMap<String, String>();
     		map.put("log4net:message", "Link " + Constants.patternWord
     				+ " clicked on " + Constants.patternWord 
     				+ " ("  + Constants.patternWord + ") in "  + Constants.patternWord 
     				);                                        
     		map.put("UserName", "techrep");
     		map.put("SourceIP", Constants.patternBracketIp);
     		map.put("Action", "Link Click");
    	 
    	 
        	Map<String, String> map2 = new TreeMap<String, String>();
        		map2.put("log4net:message", "Checkbox " + Constants.patternWord
        				+ " checked on "+ Constants.patternWord
        				+ " (" + Constants.patternWord +") in " + Constants.patternWord
        				);                                        
        		map2.put("UserName", "techrep");
        		map2.put("SourceIP", Constants.patternBracketIp);
        		map2.put("Action", "Checkbox Click");
        
        		Map<String, String> map3 = new TreeMap<String, String>();
         		map3.put("log4net:message", "Created case " + Constants.patternWord
         				+ " (" + Constants.patternWord + ")");                                        
         		map3.put("UserName", "techrep");
         		map3.put("SourceIP", Constants.patternBracketIp);
         		map3.put("Action", "Create Case");
         		
         		
       		Map<String, String> map4 = new TreeMap<String, String>();
       		map4.put("log4net:message", "Button " + Constants.patternWord
       				+ " clicked on " + Constants.patternWord 
       				+ " (" + Constants.patternWord +") in " + Constants.patternWord
       				);                                        
       		map4.put("UserName", "techrep");
       		map4.put("SourceIP", Constants.patternBracketIp);
       		map4.put("Action", "Button Click");
       		
       		Map<String, String> map5 = new TreeMap<String, String>();
       		map5.put("log4net:message", "Deleted case " + Constants.patternWord
       				+ " (" + Constants.patternWord + ")"
       				);                                        
       		map5.put("UserName", "techrep");
       		map5.put("SourceIP", Constants.patternBracketIp);
       		map5.put("Action", "Delete Case");
       		
       		return mapsMatch(map,map2,map3,map4,map5);
       	}
     
     public boolean _Remove_Single_and_Multipel_Events_and_Persons_From_Case() {
    	 
        	Map<String, String> map = new TreeMap<String, String>();
        		map.put("log4net:message", "Case " + Constants.patternWord
        				+ " from server " + Constants.patternIp
        				+"; Added " + Constants.patternWord +" events to case."
        				);                                        
        		map.put("UserName", "techrep");
        		map.put("SourceIP", Constants.patternBracketIp);
        		map.put("Action", "Added Events to Case");
        
       		
       		Map<String, String> map2 = new TreeMap<String, String>();
       		map2.put("log4net:message", "Case " + Constants.patternWord
       				+ " from server " + Constants.patternIp 
       				+ "; Added " + Constants.patternWord +" to case."
       				);                                        
       		map2.put("UserName", "techrep");
       		map2.put("SourceIP", Constants.patternBracketIp);
       		map2.put("Action", "Added People to Case");
       		
       		
       		return mapsMatch(map,map2);
       	}
      
     public boolean _Validate_Right_Click_Context_Menu_Items_of_Event_in_Case() {
    	 
     	Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Added Note " + Constants.patternWord + "  to  " + Constants.patternWord
				+ " on " + Constants.patternWord + " event");     
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Add Note");
     
    		
		Map<String, String> map2 = new TreeMap<String, String>();
  	    map.put("log4net:message", 
  	    		"Email with subject " + Constants.patternWord + " sent from " + Constants.patternWord
  	    		+ " to: " +  Constants.patternWord
  				);
  		map2.put("UserName", "techrep");
  		map2.put("SourceIP", Constants.patternBracketIp);
  		map2.put("Action", "Email Send");
    		
  		Map<String, String> map3 = new TreeMap<String, String>();
		map3.put("log4net:message",
				"Exported the following event to "
						+ Constants.patternWord + ":" + Constants.patternEvent + Constants.patternWord + " (" + Constants.patternWord + " on "   //use patterWord to replace date
						+ Constants.patternWord
						+ ")");
		map3.put("UserName", "techrep");
		map3.put("SourceIP", Constants.patternBracketIp);
		map3.put("Action", "Export Event");
    	
		Map<String, String> map4 = new TreeMap<String, String>();
		map4.put("log4net:message",
				"Exported video from the following event to "
						+ Constants.patternWord + ":" + Constants.patternEvent + Constants.patternWord + " (" + Constants.patternWord + " on "   //use patterWord to replace date
						+ Constants.patternWord
						+ ")");
		map4.put("UserName", "techrep");
		map4.put("SourceIP", Constants.patternBracketIp);
		map4.put("Action", "Export Event");
		
		
		  Map<String, String> map5 = new TreeMap<String, String>();
		   map5.put("log4net:message", "Video export initialized with the following options: Start Time: "+ Constants.patternWord 
		                        + "End Time: "                               //" AM End Time: "
		                              + Constants.patternWord +"Selected Path: "+ Constants.patternWord +"Total Minutes of Video: "+ Constants.patternNumber +"Cameras: "
		                        + Constants.patternWord 
		                              + Constants.patternBracketIp
		                              + ": " + Constants.patternWords()
		                           );
		  map5.put("UserName", "techrep");
		  map5.put("SourceIP", Constants.patternBracketIp);
		  map5.put("Action", "Video Export Initiated");
		  
		  Map<String, String> map6 = new TreeMap<String, String>();
  		map6.put("log4net:message", "Case " + Constants.patternWord
  				+ " from server " + Constants.patternIp
  				+"; Added " + Constants.patternWord +" events to case."
  				);                                        
  		map6.put("UserName", "techrep");
  		map6.put("SourceIP", Constants.patternBracketIp);
  		map6.put("Action", "Added Events to Case");
  		
  		Map<String, String> map7 = new TreeMap<String, String>();
 		map7.put("log4net:message", "Created case " + Constants.patternWord
 				+ " (" + Constants.patternWord + ")");                                        
 		map7.put("UserName", "techrep");
 		map7.put("SourceIP", Constants.patternBracketIp);
 		map7.put("Action", "Create Case");
 		
 		  Map<String, String> map8 = new TreeMap<String, String>();
 	  		map8.put("log4net:message", "Case " + Constants.patternWord
 	  				+ " from server " + Constants.patternIp
 	  				+"; Added " + Constants.patternWord +" events to case."
 	  				);                                        
 	  		map8.put("UserName", "techrep");
 	  		map8.put("SourceIP", Constants.patternBracketIp);
 	  		map8.put("Action", "Added Events to Case");
 	  		
 	  		return mapsMatch(map,map2,map3,map4,map5,map6,map7,map8);
  		
		
    		
    	}
     
     public boolean _Save_Person_For_Face_Event_And_Edit_It() {
    	 
     	Map<String, String> map = new TreeMap<String, String>();
     		map.put("log4net:message", "Tab changed to " + Constants.patternWord
     				+ " on " + Constants.patternWord
     				+" (" + Constants.patternWord +") in " + Constants.patternWord
     				);                                        
     		map.put("UserName", "techrep");
     		map.put("SourceIP", Constants.patternBracketIp);
     		map.put("Action", "Tab Change");
     
    		
     		Map<String, String> map2 = new TreeMap<String, String>();
    		map2.put("log4net:message",
    				"Modified person " + Constants.patternWord + " (" + Constants.patternWord + ")"
    				+ "Changed group from " + Constants.patternWord + "to" + Constants.patternWord
    				+ "Changed first name from " + Constants.patternWord + "to" + Constants.patternWord
    				+ "Changed last name from " + Constants.patternWord + "to" + Constants.patternWord
    				+ "Changed Employee ID from " + Constants.patternWord + "to" + Constants.patternWord
    				+ "Changed Hair Color from " + Constants.patternWord + "to" + Constants.patternWord
    				);     
    		map2.put("UserName", "techrep");
    		map2.put("SourceIP", Constants.patternBracketIp);
    		map2.put("Action", "Modify Person");
    		
    		
    		return mapsMatch(map,map2);
    	}
   
	public boolean _login_SpotMonitor() {
		Map<String, String> map = new TreeMap<String, String>();
	
		map.put("log4net:message",
				"Signed into "+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Sign In");
	 
		return mapsMatch(map);
	}

	public boolean _showConfigurationPanel() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message", "Clicked menu item "
				+ Constants.patternWord);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "Menu Item Click");
		return mapsMatch(map);
	}

	public boolean _handleSeqView() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Saved SpotMonitor configuration on "
						+ Constants.patternIp
						+ " with the following settings:"+ " Mode: " + Constants.patternWord +" Channels: " + Constants.patternWords() + " Details:" + Constants.patternWords()
						+ " DisplayNames = "
						+ Constants.patternWord
						+ " DisplayTime = "
						+ Constants.patternWord
						+ " AlertInterval = "
						+ Constants.patternWord
						+ " SequenceInterval = "
						+ Constants.patternWord
						+ " KillInterval = "
						+ Constants.patternWord
						+ " NoVideoInterval = "
						+ Constants.patternWord
						+ " InactivityThreshold = "
						+ Constants.patternWord
						+ " RestartOnInactivity = "
						+ Constants.patternWord
						+ " IsSequential = "
						+ Constants.patternWord
						+ " AudioMode = "
						+ Constants.patternWord
						+ " Volume = "
						+ Constants.patternWord
						+ " InterruptOnAlerts = "
						+ Constants.patternWord
						+ " HasConfiguration = "
						+ Constants.patternWord
						//+ "Loaded Configuration: " + Constants.patternWord + " Channels: " + Constants.patternWords()
						);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "SpotMonitorConfigurationSave");
		
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message",
				"Loaded Configuration: " + Constants.patternWord + " Channels: " + Constants.patternWords());
		
		return mapsMatch(map,map2);
	}

	public boolean _handleMultiView() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("log4net:message",
				"Saved SpotMonitor configuration on "
						+ Constants.patternIp
						+ " with the following settings:"+ " Mode: " + Constants.patternWord +" Channels: " + Constants.patternWords() + " Details:" + Constants.patternWords()
						+ Constants.patternWord
						+ " DisplayTime = "
						+ Constants.patternWord
						+ " AlertInterval = "
						+ Constants.patternWord
						+ " SequenceInterval = "
						+ Constants.patternWord
						+ " KillInterval = "
						+ Constants.patternWord
						+ " NoVideoInterval = "
						+ Constants.patternWord
						+ " InactivityThreshold = "
						+ Constants.patternWord
						+ " RestartOnInactivity = "
						+ Constants.patternWord
						+ " IsSequential = "
						+ Constants.patternWord
						+ " AudioMode = "
						+ Constants.patternWord
						+ " Volume = "
						+ Constants.patternWord
						+ " InterruptOnAlerts = "
						+ Constants.patternWord
						+ " HasConfiguration = "
						+ Constants.patternWord
						//+ "Loaded Configuration: " + Constants.patternWord + " Channels: " + Constants.patternWords()
						);
		map.put("UserName", "techrep");
		map.put("SourceIP", Constants.patternBracketIp);
		map.put("Action", "SpotMonitorConfigurationSave");
		
		Map<String, String> map2 = new TreeMap<String, String>();
		map2.put("log4net:message",
				"Loaded Configuration: " + Constants.patternWord + " Channels: " + Constants.patternWords());
		
		return mapsMatch(map,map2);
	}
	
	
}
