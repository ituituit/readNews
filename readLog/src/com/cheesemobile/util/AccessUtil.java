package com.cheesemobile.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class AccessUtil {
	private static String ENCODE_CHARSET_UTF8 = "UTF-8";
 
	
	@SuppressWarnings("deprecation")
	public static List<String> post(InputStream requeStream, String postUrl,
			Integer msTimeout) throws Exception {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.getParams().setConnectionTimeout(msTimeout);
		HttpClient httpClient = new HttpClient(connectionManager);
		PostMethod postMethod = new PostMethod(postUrl);
		// httpClient.getState ().setProxyCredentials (AuthScope.ANY, new
		// UsernamePasswordCredentials ("domain_user","domain_user"));
		try {
			List<Header> headers = new ArrayList<Header>();
			String type = "application/x-www-form-urlencoded";
			headers.add(new Header("Content-Type",type+";charset="
					+ ENCODE_CHARSET_UTF8));
			httpClient.getHostConfiguration().getParams()
					.setParameter("http.default-headers", headers);
			postMethod.setRequestBody(requeStream);
//			postMethod.setRequestContentLength(EntityEnclosingMethod.CONTENT_LENGTH_CHUNKED); 
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(1, false));
			postMethod.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, ENCODE_CHARSET_UTF8);
			postMethod.getParams().setParameter(
					HttpMethodParams.HTTP_URI_CHARSET, ENCODE_CHARSET_UTF8);
			postMethod.getParams().setParameter(
					HttpMethodParams.HTTP_ELEMENT_CHARSET, ENCODE_CHARSET_UTF8);
//			postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,
//					msTimeout);
			Integer status = httpClient.executeMethod(postMethod);
			List<String> responseContents = new ArrayList<String>();
			responseContents.add(postMethod.getResponseBodyAsString());
			responseContents.add(status + "");
			if (status == 200) {
				return responseContents;
			}else if(status == 302){
				System.out.println("return 302:" + postMethod.getResponseBodyAsString());
				return responseContents;
			}else {
				throw new Exception(
						String.format(
								"Invoke remote server address %1$s error, return status = %2$s!,return contetn = %s",
								postUrl, status,postMethod.getResponseBodyAsString()));
			}
		} catch (HttpException e) {
			throw new Exception(String.format(
					"Invoke remote server address %1$s error!", postUrl), e);
		} catch (IOException e) {
			throw new Exception(String.format(
					"Invoke remote server address %1$s error!", postUrl), e);
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	@SuppressWarnings("deprecation")
	public static List<String> post(String requeString, String postUrl,
			Integer msTimeout,int typeRequested) throws Exception {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.getParams().setConnectionTimeout(msTimeout);
		HttpClient httpClient = new HttpClient(connectionManager);
		PostMethod postMethod = new PostMethod(postUrl);
		// httpClient.getState ().setProxyCredentials (AuthScope.ANY, new
		// UsernamePasswordCredentials ("domain_user","domain_user"));
		try {
			List<Header> headers = new ArrayList<Header>();
			String type = "application/x-www-form-urlencoded";
			headers.add(new Header("Content-Type",type+";charset="
					+ ENCODE_CHARSET_UTF8));
			httpClient.getHostConfiguration().getParams()
					.setParameter("http.default-headers", headers);
			postMethod.setRequestBody(requeString);
			postMethod.setRequestContentLength(requeString.length());
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(1, false));
			postMethod.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, ENCODE_CHARSET_UTF8);
			postMethod.getParams().setParameter(
					HttpMethodParams.HTTP_URI_CHARSET, ENCODE_CHARSET_UTF8);
			postMethod.getParams().setParameter(
					HttpMethodParams.HTTP_ELEMENT_CHARSET, ENCODE_CHARSET_UTF8);
//			postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,
//					msTimeout);
			Integer status = httpClient.executeMethod(postMethod);
			List<String> responseContents = new ArrayList<String>();
			responseContents.add(postMethod.getResponseBodyAsString());
			responseContents.add(status + "");
			if (status == 200) {
				return responseContents;
			}else if(status == 302){
				System.out.println("return 302:" + postMethod.getResponseBodyAsString());
				return responseContents;
			}else {
				throw new Exception(
						String.format(
								"Invoke remote server address %1$s error, return status = %2$s!,return contetn = %s",
								postUrl, status,postMethod.getResponseBodyAsString()));
			}
		} catch (HttpException e) {
			throw new Exception(String.format(
					"Invoke remote server address %1$s error!", postUrl), e);
		} catch (IOException e) {
			throw new Exception(String.format(
					"Invoke remote server address %1$s error!", postUrl), e);
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	@SuppressWarnings("deprecation")
	public static List<String> get(String requeString, String postUrl,
			Integer msTimeout,int typeRequested) throws Exception {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.getParams().setConnectionTimeout(msTimeout);
		HttpClient httpClient = new HttpClient(connectionManager);
		PostMethod postMethod = new PostMethod(postUrl);
		// httpClient.getState ().setProxyCredentials (AuthScope.ANY, new
		// UsernamePasswordCredentials ("domain_user","domain_user"));
		try {
			List<Header> headers = new ArrayList<Header>();
			String type = "application/x-www-form-urlencoded";
			headers.add(new Header("Content-Type",type+";charset="
					+ ENCODE_CHARSET_UTF8));
			httpClient.getHostConfiguration().getParams()
					.setParameter("http.default-headers", headers);
			postMethod.setRequestBody(requeString);
			postMethod.setRequestContentLength(requeString.length());
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(1, false));
			postMethod.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, ENCODE_CHARSET_UTF8);
			postMethod.getParams().setParameter(
					HttpMethodParams.HTTP_URI_CHARSET, ENCODE_CHARSET_UTF8);
			postMethod.getParams().setParameter(
					HttpMethodParams.HTTP_ELEMENT_CHARSET, ENCODE_CHARSET_UTF8);
//			postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,
//					msTimeout);
			Integer status = httpClient.executeMethod(postMethod);
			List<String> responseContents = new ArrayList<String>();
			responseContents.add(postMethod.getResponseBodyAsString());
			responseContents.add(status + "");
			if (status == 200) {
				return responseContents;
			}else if(status == 302){
				System.out.println("return 302:" + postMethod.getResponseBodyAsString());
				return responseContents;
			}else {
				throw new Exception(
						String.format(
								"Invoke remote server address %1$s error, return status = %2$s!,return contetn = %s",
								postUrl, status,postMethod.getResponseBodyAsString()));
			}
		} catch (HttpException e) {
			throw new Exception(String.format(
					"Invoke remote server address %1$s error!", postUrl), e);
		} catch (IOException e) {
			throw new Exception(String.format(
					"Invoke remote server address %1$s error!", postUrl), e);
		} finally {
			postMethod.releaseConnection();
		}
	}
	public static String get(String getUrl){
		try {
			return get(getUrl,3000,"",80,"","");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static String get(String getUrl,Integer msTimeout,String ip,int port,String user,String password) throws Exception{
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager ();
        connectionManager.getParams ().setConnectionTimeout (msTimeout);
        HttpClient httpClient = new HttpClient (connectionManager);
        httpClient.getHostConfiguration ().setHost (ip, port);
        GetMethod postMethod = new GetMethod (getUrl);
        httpClient.getParams ().setAuthenticationPreemptive (true);
        httpClient.getState ().setProxyCredentials (AuthScope.ANY, new UsernamePasswordCredentials (user,password));
        try {
            List<Header> headers = new ArrayList<Header> ();
            headers.add(new Header("Content-Type","charset="
					+ ENCODE_CHARSET_UTF8));
            
            headers.add (new Header ("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
            headers.add (new Header ("Authorization","Basic ZG9tYWluX3VzZXI6ZG9tYWluX3VzZXI="));
            //headers.add (new Header ("Accept-Encoding","gzip, deflate"));
            headers.add (new Header ("Accept-Language","en-us,zh-cn;q=0.8,zh;q=0.5,en;q=0.3"));
            headers.add (new Header ("Connection","keep-alive"));
            headers.add (new Header ("User-Agent","	Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1"));
            httpClient.getHostConfiguration ().getParams ().setParameter ("http.default-headers", headers);
            postMethod.getParams ().setParameter (HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler (1,false));
            postMethod.getParams ().setParameter (HttpMethodParams.HTTP_CONTENT_CHARSET,ENCODE_CHARSET_UTF8);
            postMethod.getParams ().setParameter (HttpMethodParams.HTTP_URI_CHARSET, ENCODE_CHARSET_UTF8);
            postMethod.getParams ().setParameter (HttpMethodParams.SO_TIMEOUT, msTimeout);
            Integer status = httpClient.executeMethod (postMethod);
            if (status == 200) {
                return postMethod.getResponseBodyAsString ();
            } else {
                throw new Exception (String.format ("Invoke remote server address %1$s error, return status = %2$s!", getUrl, status));
            }
        } catch (HttpException e) {
            throw new Exception (String.format ("Invoke remote server address %1$s error!", getUrl),e);
        } catch (IOException e) {
            throw new Exception (String.format ("Invoke remote server address %1$s error!", getUrl),e);
        } finally {
            postMethod.releaseConnection ();
        }
    }
	
	public static List<Integer> getStartEnd(String title) {
		List<Integer> al = new ArrayList<Integer>();
		Pattern pattern = Pattern.compile("\\d+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(title);
		while (matcher.find()) {
			al.add(Integer.parseInt(matcher.group()));
		}
		return al;
	}

}
