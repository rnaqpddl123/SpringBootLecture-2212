package com.mulcam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mulcam.demo.crawling.Crawling;
import com.mulcam.demo.crawling.FireStation;
import com.mulcam.demo.crawling.Genie;
import com.mulcam.demo.crawling.Interpark;

@Controller
@RequestMapping("/crawling")
public class CrawlingController {
	
	@Autowired private Crawling crawling;
	
	@GetMapping("/interpark")
	public String interpark(Model model) throws Exception {
		List<Interpark> list = crawling.interPark();
		
		model.addAttribute("bookList", list);
		
		return "crawling/interpark";
	}
	
	@GetMapping("/genie")
	public String genie(Model model) throws Exception {
		List<Genie> list = crawling.genie();
		
		model.addAttribute("songList", list);
		return "crawling/genie";
	}

	@GetMapping("/genie2")
	public String genie2(Model model) throws Exception {
		List<Genie> list = crawling.genie();
		
		model.addAttribute("songList", list);
		return "crawling/genie2";
	}
	
	@GetMapping("/fireStation")
	public String fireStationSpinner() {
		return "crawling/fireStationSpinner";
	}
	
	@PostMapping("/fireStation")
	public String fireStation(Model model) throws Exception {
		List<FireStation> list = crawling.fireStation();
		
		model.addAttribute("stationList", list);
		return "crawling/fireStation";
	}
}
