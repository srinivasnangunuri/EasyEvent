package com.asep.dao;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class DatabaseXMLAcces implements DatabaseAdaptor {

	String webServiceUrlRoot = "http://easyevent.leowrd.com/";

	@Override
	public Document getResultXML(String uriScript, String param) {
		HttpURLConnection conn = null;
		Document doce = null;
		try {
			conn = (HttpURLConnection) new URL(webServiceUrlRoot + uriScript)
					.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			OutputStreamWriter bw = new OutputStreamWriter(
					conn.getOutputStream());
			bw.write(param);
			bw.flush();

			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			doce = db.parse(conn.getInputStream());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
		return doce;

	}

}
