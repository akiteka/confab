<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
</head>
<body>
	<div class="wakugumi">
		<header class="leftNavigation">
			<img class="meeting" src="./pic/meetingw.png"
				style="margin-top: 100px;">
		</header>
		<div class="content">
			<div class="parent">
				<div class="inner">
					<h1>Confab</h1>
					<form action="./servlet/login_db" method="post">
						ユーザー名：<input type="text" name="name"><br>
						パスワード：<input type="password" name="pass"><br>
						<input	type="submit" value="ログイン">
					</form>
					<br> <a href="./servlet/RegisterUser_db">新規登録</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>