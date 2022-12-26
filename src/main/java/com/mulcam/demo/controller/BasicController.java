package com.mulcam.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/basic")
public class BasicController {
	
	@RequestMapping("/basic")
	public String basic() {
		return "/basic/basic1";
	}
	
	@ResponseBody
	@RequestMapping("/basic1")
	public String basic1() {
		return "<h1>@Controller에서는 문자열을 웹화면으로 보낼때 @ResponseBody를 사용 ";
	}
	
	@RequestMapping("/basic2")
	public String basic2(Model model) {
		model.addAttribute("fileName", "basic2.jsp");
		model.addAttribute("message", "Model로 데이터를 전달");
		List<String> fruits = new ArrayList<>();
		fruits.add("사과"); fruits.add("귤"); fruits.add("오렌지");
		model.addAttribute("fruits", fruits);
		return "/basic/basic2";
	}
	
	// 파라메터 패싱
	@RequestMapping("/basic3")
	public String basic3(@RequestParam(name="id", defaultValue = "1") String id) {
		// http://localhost:8090/basic/basic3?id=2와같이 스면 파라메터값(id)를 2로 받음
		System.out.println("id: " + id );
		return "redirect:/basic/basic" + id;	//Redirection (http://localhost:8090/basic/basic1로 보냄)
	}
	
	// Servlet에서는 안되는 방식
	// http://localhost:8090/basic/basic3/2		(id=2값을 받을수있다.)
	@RequestMapping("/basic4/{id}")
	public String basic4(@PathVariable int id) {
		System.out.println("id: " + id);
		return "redirect:/basic/basic" + id;
	}
	
	// 기존 Servlet방식		(basic3과 같은방식으로 받음 단, 3과는 다르게 초기값이 없고 Object형태로 받는다.)
	@RequestMapping("/basic5")
	public String basic5(HttpServletRequest req) {
		String id = req.getParameter("id");
		System.out.println("id: " + id);
		return "redirect:/basic/basic" + id;
	}
}
