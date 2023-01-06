package com.mulcam.demo.crawling;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumExercise {
	//Driver
	private static WebDriver driver;
	// Properties
	private static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	private static final String WEB_DRIVER_PATH = "/DevTools/chromedriver/chromedriver.exe";
	
	public static void main(String[] args) throws Exception {
		// Druver Setup
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		driver = new ChromeDriver();
		String url = "https://www.nfa.go.kr/nfa/introduce/status/firestationidfo";
		driver.get(url);
		
		// 화면을 바꿀때는 항상 약간의 지연이 필요함
		// 페이지가 띄워지고 난뒤에 검색을 하기위해서
		Thread.sleep(4000);		// 4초지연
		WebElement inputBox = driver.findElement(By.cssSelector("#searchKeyword"));
		inputBox.sendKeys("서울");	//위에 셀럭터에서 value입력
		WebElement searchBtn = driver.findElement(By.cssSelector("#fsSearchBtn"));
		searchBtn.click();			// 버튼 클릭
		Thread.sleep(2000);	
		
		
		// //*[@id="listForm"]/div/section/div/p/strong[2]
		String xpath = "//*[@id=\"listForm\"]/div/section/div/p/strong[2]";		// 건수
		String num_ = driver.findElement(By.xpath(xpath)).getText().strip();	// 100건
		int num = Integer.parseInt(num_.substring(0, num_.length()-1));
		int pages = (int) Math.ceil(num/10);
		System.out.println("pages: " + pages);
		
		/*
		// 페이지 이동 - 셀레니움, 페이지 내에서 작업 - JSoup
		Document doc = Jsoup.parse(driver.getPageSource());
		Elements lis = doc.select(".stations-list > li");
		Element li = lis.get(0);
		
		String name = li.select(".title").text().strip();
		String addr = li.select("address").text().strip();
		String tel = li.select(".tel").text().strip();
		
		FireStation fs = new FireStation(name, addr, tel);
		System.out.println(fs);
		*/
		
		/*
		// 2 페이지로 이동
		String page2 = "//*[@id=\"listForm\"]/div/section/ul/li[1]/div/div/ul/li[4]/a";
		driver.findElement(By.xpath(page2)).click();
		Thread.sleep(4000);
		
		Document doc = Jsoup.parse(driver.getPageSource());
		Elements lis = doc.select(".stations-list > li");
		Element li = lis.get(0);
		
		String name = li.select(".title").text().strip();
		String addr = li.select("address").text().strip();
		String tel = li.select(".tel").text().strip();
		
		FireStation fs = new FireStation(name, addr, tel);
		System.out.println(fs);
		
		//next 페이지로 이동
		driver.findElement(By.cssSelector(".next_page")).click();
		Thread.sleep(3000);
		*/
		
		// 한꺼번에 서울의 모든 소방서 정보를 가져오는 코드
		List<FireStation> list = new ArrayList<>();
		for (int page =1; page <=pages; page ++) {
			if (page > 1 && page % 2 ==0) {
				driver.findElement(By.xpath("//*[@id=\"listForm\"]/div/section/div/p/strong[2]")).click();
				Thread.sleep(1000);
			}
			if (page >1 && page %2 ==1) {
				driver.findElement(By.cssSelector(".next_page")).click();
				Thread.sleep(1000);
			}
			
			Document doc = Jsoup.parse(driver.getPageSource());
			Elements lis = doc.select(".stations-list > li");
			for (Element li : lis) {
				String name = li.select(".title").text().strip();
				String addr = li.select("address").text().strip();
				String tel = li.select(".tel").text().strip();
				FireStation fs = new FireStation(name, addr, tel);
				list.add(fs);
			}
		}
		System.out.println("리스트 크기: " + list.size());
		driver.close();
	}

}
