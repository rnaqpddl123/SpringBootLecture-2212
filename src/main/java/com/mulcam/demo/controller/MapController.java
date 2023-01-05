package com.mulcam.demo.controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mulcam.demo.entiry.StaticMap;
import com.mulcam.demo.service.CsvUtill;
import com.mulcam.demo.service.MapUtill;

@Controller
@RequestMapping("/map")
public class MapController {
	
	@Value("${naver.accessId}")
	private String accessId;
	
	@Value("${naver.secretKey}")
	private String secretKey;
	
	@Value("${roadAddrKey}")
	private String roadAddrKey;
	
	
	@GetMapping("staticMap")
	public String staticForm() {
		String url = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?w=300&h=300&center=127.1054221,37.3591614&level=16";
		url += "&X-NCP-APIGW-API-KEY-ID=" + accessId; 
		url += "&X-NCP-APIGW-API-KEY=" + secretKey;
		return "map/staticForm";
	}
	
	@PostMapping("staticMap")
	public String staticMap(StaticMap map, Model model) throws UnsupportedEncodingException {
		String url = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster"
					+ "?w=" + map.getWidth()
					+ "&h=" + map.getHeight()
					+ "&center=" + map.getLng() + "," + map.getLat()
					+ "&level=" + map.getLevel()
					+ "&maptype=" + map.getMaptype()
					+ "&format=" + map.getFormat()
					+ "&scale=" + map.getScale()
					+ "&lang=" + map.getLang()
			 		+ "&X-NCP-APIGW-API-KEY-ID=" + accessId 
			 		+ "&X-NCP-APIGW-API-KEY=" + secretKey;
		//&markers=type:d|size:tiny|pos:127.1054221%2037.3591614
		String marker = "type:d|size:mid|pos:127.0724 37.5383";
		marker = URLEncoder.encode(marker, "utf-8");		// blank때문에 필요
		url += "&markers="+ marker;

		marker = "type:t|size:tiny|pos:127.0824 37.5383|label:광진구청|color:red";
		marker = URLEncoder.encode(marker, "utf-8");		// blank때문에 필요
		url += "&markers="+ marker;
		model.addAttribute("url", url);	
		return "map/staticResult";
	}
	
	@ResponseBody
	@GetMapping("/roadAddr/{keyword}")
	public String roadAddr(@PathVariable String keyword ) throws Exception {
		int currentPage =1;
		int countPerPage = 10;
		String resultType = "json";
		keyword = URLEncoder.encode(keyword, "utf-8");
		String apiUrl = "https://business.juso.go.kr/addrlink/addrLinkApi.do"
						+ "?confmKey=" + roadAddrKey
						+ "&currentPage=" + currentPage
						+ "&countPerPage=" + countPerPage
						+ "&keyword=" + keyword
						+ "&resultType=" + resultType;
		
		URL url = new URL(apiUrl);
		BufferedReader br = new BufferedReader( new InputStreamReader(url.openStream(),"UTF-8"));
		StringBuffer sb = new StringBuffer();
		String line = null;
		
		while((line = br.readLine()) != null)
			sb.append(line);
		br.close();
		
		// JSON 데이터에서 원하는 값 추출하기
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(sb.toString());
		JSONObject results = (JSONObject) object.get("results");
		JSONArray juso = (JSONArray) results.get("juso");
		JSONObject jusoItem = (JSONObject) juso.get(0);
		String roadAddr = (String) jusoItem.get("roadAddr");
		
		
		return sb.toString() + "<br>"+ roadAddr;
	}
	
	@ResponseBody
	@GetMapping("/gecode")
	public String gecode() throws Exception {
		String apiUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";
		String query = "서울특별시 광진구 자양로 117(자양동)";
		query = URLEncoder.encode(query, "utf-8");
		apiUrl += "?query=" + query;
		
		URL url = new URL(apiUrl);
		// 헤더 설정
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", accessId);
		conn.setRequestProperty("X-NCP-APIGW-API-KEY", secretKey);
		conn.setDoInput(true);
		
		// 응답 결과 확인
		int responseCode = conn.getResponseCode();
		
		// 데이터 수신
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
		StringBuffer sb = new StringBuffer();
		String line = null;
		
		while((line = br.readLine()) != null)
			sb.append(line);
		br.close();
		
		// JSON 데이터에서 원하는 값 추출하기
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(sb.toString());
//		JSONArray addresses = (JSONArray) object.get("addresses");
//		JSONObject address = (JSONObject) addresses.get(0);
		JSONObject address = (JSONObject) ((JSONArray) object.get("addresses")).get(0);
		String lng_ = (String) address.get("x");
		String lat_ = (String) address.get("y");
		Double lng = Double.parseDouble(lng_);
		Double lat = Double.parseDouble(lat_);
		
//		return object.toString();
		return "경도: " + lng + ", 위도: " + lat;
	}
	

	@GetMapping("/hotPlaces")
	public String hotplaces() throws Exception {
		String[] hotPlaces = {"광진구청", "건국대학교", "세종대학교", "워커힐호텔"};
		String filename = "c:/Temp/광진구명소.csv";
		MapUtill mu = new MapUtill();
				
		String output = "";
		List<List<String>> dataList = new ArrayList<>();
		for (String place : hotPlaces) {
			List<String> row = new ArrayList<>();
			String roadAddr = mu.getAddr(place, roadAddrKey);
			output += roadAddr + "<br>";
			List<String> geocode = mu.getGeocode(roadAddr, accessId, secretKey);
			row.add(place);
			row.add(roadAddr);
			row.add(geocode.get(0));		//	Longitude(경도)
			row.add(geocode.get(1));		//  Latitude (위도)
			dataList.add(row);
		}

		CsvUtill cu = new CsvUtill();
		cu.writeCsv(filename, dataList);
		return "redirect:/map/hotPlaceResult";
	}
	
	@GetMapping("/hotPlaceResult")
	public String hotPlaceResult(Model model) throws Exception{
		CsvUtill cu = new CsvUtill();
		List<List<String>> dataList = cu.readCsv("c:/Temp/광진구명소.csv");
		String marker = "";
		double lngSum = 0.0, latSum = 0.0;
		// "type:t|size:tiny|pos:127.0824 37.5383|label:광진구청|color:red";
		for (List<String> list : dataList) {
			double lng = Double.parseDouble(list.get(2));
			double lat = Double.parseDouble(list.get(3));
			lngSum += lng; latSum += lat;
			marker += "&markers=type:t|size:tiny|pos:" + lng + "%20" + lat + "|label:"
					+ URLEncoder.encode(list.get(0), "utf-8") + "|color:red";
		}
		double lngCenter = lngSum / dataList.size();
		double latCenter = latSum / dataList.size();
		
		String url = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster"
				+ "?w=" + 600
				+ "&h=" + 400
				+ "&center=" + lngCenter + "," + latCenter
				+ "&level=" + 12
				+ "&scale=" + 2
		 		+ "&X-NCP-APIGW-API-KEY-ID=" + accessId 
		 		+ "&X-NCP-APIGW-API-KEY=" + secretKey;
		
		System.out.println(url+marker);
		model.addAttribute("url", url+marker);
		return "map/staticResult";
	}
	
	@GetMapping("/myHotPlace")
	public String myHotPlace(Model model) throws Exception{
		CsvUtill cu = new CsvUtill();
		List<List<String>> dataList = cu.readCsv("c:/Temp/중구명소.csv");
		String marker = "";
		int i=0;
		double lngSum = 0.0, latSum = 0.0;
		// "type:t|size:tiny|pos:127.0824 37.5383|label:광진구청|color:red";
		String[] makerType = {"0xEE82EE","red","blue","green","0x4B0082"};
		for (List<String> list : dataList) {
			double lng = Double.parseDouble(list.get(2));
			double lat = Double.parseDouble(list.get(3));
			lngSum += lng; latSum += lat;
			marker += "&markers=type:t|size:tiny|pos:" + lng + "%20" + lat + "|label:"
					+ URLEncoder.encode(list.get(0), "utf-8") 
					+ "|color:" + makerType[i++];
		}
		double lngCenter = lngSum / dataList.size();
		double latCenter = latSum / dataList.size();
		
		String url = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster"
				+ "?w=" + 600
				+ "&h=" + 400
				+ "&center=" + lngCenter + "," + latCenter
				+ "&level=" + 14
				+ "&scale=" + 2
		 		+ "&X-NCP-APIGW-API-KEY-ID=" + accessId 
		 		+ "&X-NCP-APIGW-API-KEY=" + secretKey;
		
		model.addAttribute("url", url+marker);
		return "map/staticResult";
	}
	
}
