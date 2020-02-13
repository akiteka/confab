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
					<p>パスワードを変更しました。</p>


					<br> <br> <a href="./Main">メインページに戻る</a>
				</div>

			</div>
		</div>
	</div>
</body>
</html>