package com.mulcam.demo.crawling;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlExercise {

	public static void main(String[] args) throws Exception {
		// 인터파크 베스트셀러 주소
		String url = "http://book.interpark.com/display/collectlist.do?_method=BestsellerHourNew201605&bestTp=1&dispNo=028#";
		
		//site에 접속해서 html 데이터를 가져온후 Parsing
		Document doc = Jsoup.connect(url).get();
		
		// 찾고자 하는 항목들
		Elements lis = doc.select(".rankBestContentList > ol > li");		//Elements는 복수형, Element는 단수형	(타입은 list같은것)
		
		/**
		 *  아래의 내용은 한번해보고 반복적으로 할때는 주석처리
		// 찾는값 구하기 
		Element li = lis.get(5);
//		System.out.println(li.toString());		// li 이하 태그 전부보기
		String title = li.select(".itemName").text().strip();
		String author = li.select(".itemMeta > .author").text().strip(); 
		String company = li.select(".company").text().strip(); 
//		System.out.println(title + ", " + company + ", " + author);
		
		// 이미지 태그 찾기(이미지 주소 알아내기)
//		Elements imgs = li.selectFirst(".coverImage").select("img");
//		String src = imgs.attr("src");
		String src = li.select(".coverImage img").attr("src");		// 자손 셀럭터
		
		
		//스트링 조작
		String price_ = li.select(".price > em").text().strip();	// 19,800 원
		int price = Integer.parseInt(price_.replace(",",""));
//		System.out.println(price);
		
		//순위
		Elements spans = li.select(".rankNumber.digit2").select("span");
		String rank_ = "";
		for (Element span : spans) {
			String classes = span.attr("class").strip();
//			System.out.println(classes);
			rank_ += classes.substring(classes.length()-1);
		}
		int rank = Integer.parseInt(rank_);
		System.out.println(rank);
		
		//데이터 정리
		Interpark book = new Interpark(rank, src, title, author, company, price);
		System.out.println(book);
		*/
		
		// 반복문으로 데이터 정리
		List<Interpark> list = new ArrayList<>();
		for (Element li : lis) {
			String title = li.select(".itemName").text().strip();
			String author = li.select(".itemMeta > .author").text().strip(); 
			String company = li.select(".company").text().strip(); 
			
			// 이미지 태그 찾기(이미지 주소 알아내기)
			String src = li.select(".coverImage img").attr("src");		// 자손 셀럭터
			//스트링 조작
			String price_ = li.select(".price > em").text().strip();	// 19,800 원
			int price = Integer.parseInt(price_.replace(",",""));
			//순위
			Elements spans = li.select(".rankNumber.digit2").select("span");
			String rank_ = "";
			for (Element span : spans) {
				String classes = span.attr("class").strip();
				rank_ += classes.substring(classes.length()-1);
			}
			int rank = Integer.parseInt(rank_);		
			
			Interpark book = new Interpark(rank, src, title, author, company, price);
			list.add(book);
		}
		list.forEach(x -> System.out.println(x));
		
	}

}
 