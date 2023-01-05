package com.mulcam.demo.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CsvUtill implements CsvUtill2{
	
	@Override
	public List<List<String>> readCsv(String filename){
		return readCsv(filename, ",", 0);
	}
	
	@Override
	public List<List<String>> readCsv(String filename, String separator){
		return readCsv(filename, separator, 0);
		
	}
	
	// csv파일 읽기
	@Override
	public List<List<String>> readCsv(String filename, String separator, int skipLine){
		List<List<String>> csvList = new ArrayList<>();
		File csv = new File(filename);
		BufferedReader br = null;	
		String line = "";
		int lineNo = 0;
		
		try {
			br = new BufferedReader(new FileReader(csv));
			while((line = br.readLine()) != null) {
				// skipLine 동작이 행해져야 하는 경우에는 continue
				if (skipLine > lineNo++)
					continue;
				String[] lineArray = line.split(separator);
//				List<String> aLine = new ArrayList<>();
//				for (String s : lineArray)
//					aLine.add(s);			//한줄의 데이터가 리스트로 만들어짐
				List<String> aLine = Arrays.asList(lineArray);
				csvList.add(aLine);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvList;
	}
	
	@Override
	public void writeCsv(String filename, List<List<String>> dataList) {
		writeCsv(filename, dataList, ",");
	}
	
	
	
	@Override
	public void writeCsv(String filename, List<List<String>> dataList, String separator) {
		File csv = new File(filename);
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(csv));				// overwrite, 덮어쓰기
//			bw = new BufferedWriter(new FileWriter(csv), true);			//append, 뒤에 붙이기
			for (List<String> data : dataList) {
				String line = "";
				for (int i=0; i<data.size(); i++) {
					line += data.get(i);
					if (i < data.size()-1)
						line += separator;
				}
				bw.write(line + "\n");
			}
		} catch (Exception e) {
			
		} finally {
			try {
				bw.flush();bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
