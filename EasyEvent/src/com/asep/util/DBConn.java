package com.asep.util;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.asep.easyevent.R;

import android.webkit.WebView.FindListener;

public class DBConn {
	public static boolean login(String username, String password){
		try {
			HttpURLConnection conn = (HttpURLConnection)new URL("http://easyevent.leowrd.com/login.php").openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			String param = "username="+username+"&password="+password;
			
			OutputStreamWriter bw = new OutputStreamWriter(conn.getOutputStream());
			bw.write(param);
			bw.flush();
			
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doce = db.parse(conn.getInputStream());
			conn.disconnect();
			Element elem = doce.getDocumentElement();
			NodeList nl = elem.getElementsByTagName("result");
			//System.out.println(nl.getLength()+"  "+nl.item(0).getChildNodes().item(0).getNodeValue());
			
			String result = nl.item(0).getChildNodes().item(0).getNodeValue();
			if(result.trim().equals("true"))
				return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
