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
			<img class="meeting"
				src="file:///C:/eclipse_work/confab/WebContent/pic/meetingw.png"
				style="margin-top: 100px;">
		</header>
		<div class="content">
			<div class="parent">

				<div class="inner">
					<p>下記のユーザーを登録します</p>
					<p>
						名前:<%=registerUser.getName()%><br>
					</p>
					<a href="./RegisterUser_db">戻る</a> <a
						href="./RegisterUser_db?action=done">登録</a> <br> <br> <a
						href="./login_db">TOPページに戻る</a>
				</div>

			</div>
		</div>
	</div>
</body>
</html>