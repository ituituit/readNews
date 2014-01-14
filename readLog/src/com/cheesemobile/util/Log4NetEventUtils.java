package com.cheesemobile.util;

import java.util.Iterator;
import java.util.Map;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cheesemobile.domain.EventBean;

public class Log4NetEventUtils {
	/*
	 <log4net:event logger="TcpLogger" 
	 				timestamp="2013-03-07T00:00:06.194483-08:00" 
	 				level="ERROR" 
	 				thread="73" 
	 				domain="EnterpriseServer.exe" 
	 				username="NT AUTHORITY\SYSTEM">
	 			<log4net:message>TaskExecutor Failure </log4net:message>
	 			<log4net:properties>
	 				<log4net:data name="PID" value="2168" />
	 				<log4net:data name="log4net:HostName" value="VMS-PC" />
	 				<log4net:data name="UtcOffset" value="-08:00" />
	 			</log4net:properties>
	 			<log4net:exception>System.NullReferenceException: Object reference not set to an instance of an object.
	 										at ThreeVR.ContentServer.Tasks.TaskCommander.get_IsBusy()
	 									  	at ThreeVR.ContentServer.Tasks.TaskExecutor.checkForTasks(Object ignored)</log4net:exception>
	 </log4net:event>
	 */
	public static String findEvent(Element data ,String str) {
		Attributes attrs = data.attributes();
		for (Iterator<Attribute> it = attrs.iterator(); it.hasNext();) {
			Attribute att = it.next();
			String key = att.getKey();
//			System.out.println(key + " " + str );
			if (key.indexOf(str) != -1 ) {
				return att.getValue();
			}
		}
		Elements es = data.getAllElements();
		for (int i = 0; i < es.size(); i++) {
			Element e = es.get(i);
			if(e.nodeName().indexOf(str) != -1){
				if(e.nodeName().indexOf("log4net:message") != -1){
					return e.text();
				}
			}
		}
		es = getPropertiesData(data);
		for (int i = 0; i < es.size(); i++) {
			Element e = es.get(i);
			if(e.attr("name").indexOf(str) != -1){
				return e.attr("value");
			}
		}
		return "";
	}
	
	private static Elements getPropertiesData(Element data) {
		Elements properties = data.getElementsByTag("log4net:properties");
		if (properties.size() == 0) {
			return null;
		}
		Elements datas = properties.get(0).getElementsByTag("log4net:data");
		return datas;
	}
	
	public static EventBean newErrorEventBean(String error) {
		EventBean eb = new EventBean(StringUtil.getHostName(), "", "", "",
				error, "", "", "",
				Constants.LEVELS_DESC[Constants.LEVEL_ERROR], "", "");
		return eb;
	}
	
	public static EventBean encapsulateElementToEventBean(Element data) {
		if (data == null) {
			return null;
		}
		String msg = Log4NetEventUtils.findEvent(data, "log4net:message");
		String pid = Log4NetEventUtils.findEvent(data, "PID");
		String hostname = Log4NetEventUtils.findEvent(data, "log4net:HostName");
		String timeSatap = Log4NetEventUtils.findEvent(data, "timestamp");
		String domain = Log4NetEventUtils.findEvent(data, "domain");
		String thread = Log4NetEventUtils.findEvent(data, "Thread");
		String level = Log4NetEventUtils.findEvent(data, "level");
		String utcOffset = Log4NetEventUtils.findEvent(data, "UtcOffset");
		String username = Log4NetEventUtils.findEvent(data, "username");
		EventBean b = new EventBean(hostname, timeSatap, domain, "", msg, pid,
				thread, "", level, username, utcOffset);
		b.setSrcCode(data.toString());
		// System.out.println(b.toString());
		return b;
	}
	
	public static boolean mapsMatch(Element data,Map<String, String> treeMap) {
		  int i = 0;
		  for (String m : treeMap.keySet()) {
		   i++;
		   String pattern = treeMap.get(m);
		   String matchStr = Log4NetEventUtils.findEvent(data, m);
		   matchStr = matchStr.replaceAll(" ", "");
		   pattern = pattern.replaceAll(" ", "");
		   matchStr = matchStr.toLowerCase();
		   pattern = pattern.toLowerCase();
		//   matchStr = matchStr.replaceAll("\"", "'");
		//   pattern = pattern.replaceAll("\"", "'");
		   _Log.i("matching:" + i + matchStr + "," + pattern);
		   if (!matchStr.matches(pattern)) {
		    return false;
		   }
		  }
		  _Log.i("matched");
		  return true;
		 }
	
}
