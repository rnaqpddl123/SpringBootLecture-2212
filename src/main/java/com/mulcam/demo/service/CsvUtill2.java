package com.mulcam.demo.service;

import java.util.List;


public interface CsvUtill2 {
	
	List<List<String>> readCsv(String filename);
	
	List<List<String>> readCsv(String filename, String separator);
	
	List<List<String>> readCsv(String filename, String separator, int skipLine);
	
	void writeCsv(String filename, List<List<String>> dataList);
	
	void writeCsv(String filename, List<List<String>> dataList, String separator);
	
	
	
	
}


