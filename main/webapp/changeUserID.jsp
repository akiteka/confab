<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	//リクエストスコープに保存されたエラーメッセージを取得
	String errorMsg = (String) request.getAttribute("errorMsg");
%>
<%@ page import="model.Users,model.Mutter"%>
<%@ include file="./common.jsp"%>
<%
	// セッションスコープに保存されたユーザー情報を取得
	Users loginUser = (Users) session.getAttribute("loginUser");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー名の変更</title>
</head>
<body>

	<div class="wakugumi">
		<header class="leftNavigation">
		</header>
		<div class="content">
			<div class="parent">
				<div class="inner">
					<h1>ユーザー名の変更</h1>
					<p>
						現在のユーザー名：<%=loginUser.getName()%><br>
					<form action="./ChangeID" method="post">
						新規のユーザー名：<input type="text" name="name"><br>
						<input	type="submit" value="確認">
					</form>
					<%
						if (errorMsg != null) {
					%>
					<b><font color="#ff0000"><%=errorMsg%></font></b><br>
					<%
						} else {
					%>
					<br>
					<%
						}
					%>
					<a href="./Main">メインページに戻る</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>