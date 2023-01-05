<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>객체 탐지</title>
</head>
<body style="margin: 40px;">
	<h3>네이버 인공지능 API 객체 탐지</h3>
	<br>
	<hr>
	<form action="/detect/naver" method="post" enctype="multipart/form-data">
		탐지할 파일: <input type="file" name="upload">
		<br>
    	<input type="submit" value="업로드">
	</form>
	

</body>
</html>