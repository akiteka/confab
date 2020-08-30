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
		</header>
		<div class="content">
			<div class="parent">
				<div class="inner">
					<h1>Confab</h1>
					あなたの好きを発信しよう<br>
					<br>
					<form action="./loginDB" method="post">
						ユーザー名：<input type="text" name="name"><br>
						パスワード：<input type="password" name="pass"><br>
						<input	type="submit" value="ログイン">
					</form>
					<br> <a href="./RegisterUserDB">新規登録</a>
					<br>
				</div>
			</div>
		</div>
	</div>
</body>
</html>