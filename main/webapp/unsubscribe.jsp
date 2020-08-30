<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>退会</title>
</head>
<body>

	<div class="wakugumi">
		<header class="leftNavigation">
		</header>
		<div class="content">
			<div class="parent">
				<div class="inner">
					<h1>アカウント削除</h1>
					アカウントを削除しますか?
					<form action="./Unsubscribe" method="post">
						<input type="submit" value="削除">
					</form>
					<a href="./Main">メインページに戻る</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>