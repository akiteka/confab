<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Users"%>
<%@ include file="./common.jsp"%>
<%
	Users registerUser = (Users) session.getAttribute("registerUser");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー登録</title>
</head>
<body>
	<div class="wakugumi">
		<header class="leftNavigation">
		</header>
		<div class="content">
			<div class="parent">

				<div class="inner">
					<p>下記のユーザーを登録します</p>
					<p>
						名前:<%=registerUser.getName()%><br>
					</p>
					<a href="./RegisterUserDB">戻る</a> <a
						href="./RegisterUserDB?action=done">登録</a> <br> <br> <a
						href="./loginDB">TOPページに戻る</a>
				</div>

			</div>
		</div>
	</div>
</body>
</html>