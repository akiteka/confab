<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Users"%>
<%@ include file="./common.jsp"%>
<%
	// セッションスコープからユーザー情報を取得
	Users loginUser = (Users) session.getAttribute("loginUser");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン結果</title>
</head>
<body>
	<div class="wakugumi">
		<header class="leftNavigation">
		</header>
		<div class="content">
			<div class="parent">
				<div class="inner">
					<h2></h2>
					<%
						if (loginUser != null) {
					%>
					<p>ログインに成功しました</p>
					<p>
						ようこそ<b><%=loginUser.getName()%></b>さん
					</p>
					<a href="./Main">書き込み・閲覧へ</a>
					<%
						} else {
					%>
					<p>ログインに失敗しました</p>
					<a href="./loginDB">TOPページ戻る</a>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>