<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Fire Station</title>
	<script>
		window.onload =function() {
			// 페이지가 켜지면 form에 action이 시작되고 controller에서 결과가 다처리되면 작동
			const form = document.getElementById("form");
			form.submit();
		}
	</script>
</head>
<body>
	<h3>서울 소재 소방서 현황</h3>
	<hr>
	<img src="/img/please-wait.gif">
	<form style="display:none; " action="/crawling/fireStation" method="post" id="form">
		<input type="hidden" name="dummy" value="0">
	</form>
</body>
</html>