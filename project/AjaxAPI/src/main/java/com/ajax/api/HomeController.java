package com.ajax.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	//api를 쓸거니까 key 값이 필요함
	private static final String serviceKey = "id4tSNgaZlcFDBFB3L2XhGe0YE%2FQQmaIXUeDUEltZcjSqHeM7RyVr3bEZ4tcUXwQjNJx4IgAY4TAyJ1gt1bzYQ%3D%3D";

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		System.out.println("[homecontroller] home method has been called");
		return "home";
	}

	@ResponseBody
	@RequestMapping(value="/parking.do", produces="application/json;charset=utf-8")//데이터는 제이슨 형태로 돌려줄거임
	public String query() throws Exception{
		System.out.println("[HomeController] query method has been called");
		String url = "http://apis.data.go.kr/B551177/StatusOfParking/getTrackingParking";//연결할주소
		url +="?servicekey=" + serviceKey;
		url+="&numOfRows=" + URLEncoder.encode("10","UTF-8");
		url+="&pageNo=" + URLEncoder.encode("1","UTF-8");
		url+="&type=" + URLEncoder.encode("json","UTF-8");

		URL requestURL = new URL(url);//URL 클래스를 이용해서
		HttpURLConnection urlConnection = (HttpURLConnection)requestURL.openConnection();
		urlConnection.setRequestMethod("GET");

		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

		String responseText = "";
		String line;

		while((line=br.readLine()) != null) {
			responseText += line;
		}
		System.out.println(responseText);
		br.close();
		urlConnection.disconnect();
		return responseText;

	}

}
