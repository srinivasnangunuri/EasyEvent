package com.asep.util;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;

public class DBConn {
	public static boolean login(String username, String password) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(
					"http://easyevent.leowrd.com/login.php").openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			String param = "username=" + username + "&password=" + password;

			OutputStreamWriter bw = new OutputStreamWriter(
					conn.getOutputStream());
			bw.write(param);
			bw.flush();

			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doce = db.parse(conn.getInputStream());
			conn.disconnect();
			Element elem = doce.getDocumentElement();
			NodeList nl = elem.getElementsByTagName("result");
			// System.out.println(nl.getLength()+"  "+nl.item(0).getChildNodes().item(0).getNodeValue());

			String result = nl.item(0).getChildNodes().item(0).getNodeValue();
			if (result.trim().equals("true"))
				return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean register(String username, String password,
			String email) {
		if (!email.contains("@") || username.trim().length() == 0
				|| password.trim().length() == 0)
			return false;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(
					"http://easyevent.leowrd.com/register.php")
					.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			String param = "username=" + username.trim() + "&password="
					+ password.trim() + "&email=" + email.trim();

			OutputStreamWriter bw = new OutputStreamWriter(
					conn.getOutputStream());
			bw.write(param);
			bw.flush();

			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doce = db.parse(conn.getInputStream());
			conn.disconnect();
			Element elem = doce.getDocumentElement();
			NodeList nl = elem.getElementsByTagName("result");
			String result = nl.item(0).getChildNodes().item(0).getNodeValue();
			if (result.trim().equals("success"))
				return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static String getEventsXML() {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(
					"http://easyevent.leowrd.com/events.php").openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			String param = "o=get";
			OutputStreamWriter bw = new OutputStreamWriter(
					conn.getOutputStream());
			bw.write(param);
			bw.flush();
			StringBuilder sb = new StringBuilder();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String s = "";
			while( (s = br.readLine())!=null)
				sb.append(s);
			conn.disconnect();
			return sb.toString();
		} catch (Exception e) {
			return "error:" + e.getMessage();
		}
	}

	public static boolean addEvent(String name, String desc, String venue,
			int topic_id, Date event_date) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(
					"http://easyevent.leowrd.com/events.php").openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			java.text.SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String param = String.format(
					"o=add&name=%s&desc=%s&venue=%s&topic_id=%s&event_date=%s",
					new String[] { name.trim(), desc.trim(), venue.trim(),
							topic_id + "", sdf.format(event_date) });

			OutputStreamWriter bw = new OutputStreamWriter(
					conn.getOutputStream());
			bw.write(param);
			bw.flush();

			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doce = db.parse(conn.getInputStream());
			conn.disconnect();
			Element elem = doce.getDocumentElement();
			NodeList nl = elem.getElementsByTagName("result");
			String result = nl.item(0).getChildNodes().item(0).getNodeValue();
			if (result.trim().equals("success"))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean editEvent(int event_id, String name, String desc,
			String venue, int topic_id, Date event_date) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(
					"http://easyevent.leowrd.com/events.php").openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			java.text.SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String param = String
					.format("o=edit&event_id=%s&name=%s&desc=%s&venue=%s&topic_id=%s&event_date=%s",
							new String[] { event_id + "", name.trim(),
									desc.trim(), venue.trim(), topic_id + "",
									sdf.format(event_date) });

			OutputStreamWriter bw = new OutputStreamWriter(
					conn.getOutputStream());
			bw.write(param);
			bw.flush();

			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doce = db.parse(conn.getInputStream());
			conn.disconnect();
			Element elem = doce.getDocumentElement();
			NodeList nl = elem.getElementsByTagName("result");
			String result = nl.item(0).getChildNodes().item(0).getNodeValue();
			if (result.trim().equals("success"))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getTopicsXML() {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(
					"http://easyevent.leowrd.com/topics.php").openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			String param = "o=get";
			OutputStreamWriter bw = new OutputStreamWriter(
					conn.getOutputStream());
			bw.write(param);
			bw.flush();
			StringBuilder sb = new StringBuilder();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String s = "";
			while( (s = br.readLine())!=null)
				sb.append(s);
			conn.disconnect();
			return sb.toString();
		} catch (Exception e) {
			return "error:" + e.getMessage();
		}
	}

	public static boolean addTopic(String title) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(
					"http://easyevent.leowrd.com/topics.php").openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			String param = String.format("o=add&title=%s",
					new String[] { title.trim() });

			OutputStreamWriter bw = new OutputStreamWriter(
					conn.getOutputStream());
			bw.write(param);
			bw.flush();

			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doce = db.parse(conn.getInputStream());
			conn.disconnect();
			Element elem = doce.getDocumentElement();
			NodeList nl = elem.getElementsByTagName("result");
			String result = nl.item(0).getChildNodes().item(0).getNodeValue();
			if (result.trim().equals("success"))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean subscribe(String user_name, int topic_id) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(
					"http://easyevent.leowrd.com/subscribe.php")
					.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			String param = String.format("o=s&user_name=%s&topic_id=%s",
					new String[] { user_name.trim(), topic_id + "" });

			OutputStreamWriter bw = new OutputStreamWriter(
					conn.getOutputStream());
			bw.write(param);
			bw.flush();

			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doce = db.parse(conn.getInputStream());
			conn.disconnect();
			Element elem = doce.getDocumentElement();
			NodeList nl = elem.getElementsByTagName("result");
			// System.out.println(nl.getLength()+"  "+nl.item(0).getChildNodes().item(0).getNodeValue());

			String result = nl.item(0).getChildNodes().item(0).getNodeValue();
			if (result.trim().equals("success"))
				return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static String getSubscriptionXML(String user_name) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(
					"http://easyevent.leowrd.com/subscribe.php")
					.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			String param = "o=get&user_name=" + user_name.trim();
			OutputStreamWriter bw = new OutputStreamWriter(
					conn.getOutputStream());
			bw.write(param);
			bw.flush();
			StringBuilder sb = new StringBuilder();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String s = "";
			while( (s = br.readLine())!=null)
				sb.append(s);
			conn.disconnect();
			return sb.toString();
		} catch (Exception e) {
			return "error:" + e.getMessage();
		}
	}

	public static boolean unSubscribe(String user_name, int topic_id) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(
					"http://easyevent.leowrd.com/topics.php").openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			String param = String.format("o=un&user_name=%s&topic_id=%s",
					new String[] { user_name.trim(), topic_id + "" });

			OutputStreamWriter bw = new OutputStreamWriter(
					conn.getOutputStream());
			bw.write(param);
			bw.flush();

			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doce = db.parse(conn.getInputStream());
			conn.disconnect();
			Element elem = doce.getDocumentElement();
			NodeList nl = elem.getElementsByTagName("result");
			String result = nl.item(0).getChildNodes().item(0).getNodeValue();
			if (result.trim().equals("success"))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
