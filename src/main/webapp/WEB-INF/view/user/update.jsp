<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<style>
th, td {
	text-align: center;
}
</style>
</head>

<body>
	<h3>
		<strong>회원정보수정</strong>
	</h3>
	<hr>
	<form action="/user/update" method="post">
		<input type="hidden" name="uid" value="${uid}">
		<table class="table table-borderless">
			<tr>
				<td><label for="uid">사용자 ID</label></td>
				<td><input type="text" name="uid" value="${uid}" disabled></td>
			</tr>
			<tr>
				<td><label for="uname">이름</label></td>
				<td><input type="text" name="uname" value="${uname}"></td>
			</tr>
			<tr>
				<td><label for="email">이메일</label></td>
				<td><input type="text" name="email" value="${email}"></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><input
					class="btn btn-primary" type="submit" value="제출"> <input
					class="btn btn-secondary" type="reset" value="취소"></td>
			</tr>
		</table>
	</form>
	<!-- ================main========================= -->
</body>
</html>