package com.mulcam.demo.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class UserAgentExercise {

	public static void main(String[] args) throws Exception{
		String url = "https://www.naver.com/";
		Document doc = Jsoup.connect(url).get();
		
		// 종종 사이트에서 크롤링을 허용하지 않는 경우에 (기계적으로 검색하는것을 막아놨을때)
		// 기계가 검색하는게 아니라 사람이 하는것을 상대방 사이트에 알려주는 역할 
		// userAgent 알아내는법 : F12 ->network -> name -> Header를 보면 User-Agent가 있음
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36";
		doc = Jsoup.connect(url).userAgent(userAgent).get();
		System.out.println(doc);
		
	}
}
