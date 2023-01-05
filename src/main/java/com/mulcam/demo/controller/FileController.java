package com.mulcam.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mulcam.demo.entiry.FileEntity;

@Component
@RequestMapping("file")
public class FileController {
	
	@GetMapping("/upload")
	public String uploadForm() {
		return "file/upload";
	}
	
	@PostMapping("/upload")
	public String upload(@RequestParam MultipartFile[] files, Model model) {
		List<FileEntity> list = new ArrayList<>();
		for (MultipartFile file : files) {
			FileEntity fe = new FileEntity();
			fe.setFileName(file.getOriginalFilename());		//파일 이름
			fe.setContentType(file.getContentType());		//파일 타입
			list.add(fe);
			
			//물리적 저장
			File fileName = new File(file.getOriginalFilename());
			try {
				file.transferTo(fileName);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		model.addAttribute("uploadFiles", list);
		return "file/result";
	}
	
	// application.properties에 저장된경로
	@Value("${spring.servlet.multipart.location}")
	String uploadDir;
	
	// 다운로드, (display와 같은 역할도 가능함)
	@GetMapping("/download")
	public ResponseEntity<Resource> download(@ModelAttribute FileEntity fe) {
		// 다운로드받을파일(첨부파일)이 존재하는 경로 설정
		Path path = Paths.get(uploadDir + "/" + fe.getFileName());
		try {
			// 헤더 만들어주는것 (크롬에서 F12 -> network에서 헤더 정보를 볼수있
			String contentType = Files.probeContentType(path);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(
					ContentDisposition.builder("attachment")
					.filename(fe.getFileName(), StandardCharsets.UTF_8)
					.build()
			);
			headers.add(HttpHeaders.CONTENT_TYPE, contentType);
			System.out.println(contentType);
			System.out.println(fe.getFileName());
			// 파일 데이터
			Resource resource = new InputStreamResource(Files.newInputStream(path));
			return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 화면 출력
	@GetMapping("/display")
	public ResponseEntity<Resource> display(@RequestParam("fileName") String fileName) {
		String path = "C:\\Temp\\Spring\\";
		String folder = "";
		Resource resource = new FileSystemResource(path + folder + fileName);
		if(!resource.exists()) 
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try{
			filePath = Paths.get(path + folder + fileName);
			header.add("Content-type", Files.probeContentType(filePath));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
}
