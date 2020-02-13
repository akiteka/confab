<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./common.jsp"%>
<%
	//リクエストスコープに保存されたエラーメッセージを取得
	String errorMsg = (String) request.getAttribute("errorMsg");
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
					<h2>ユーザー登録</h2>
					<form action="./RegisterUser_db" method="post">
						ユーザー名：<input type="text" name="name"><br>
						パスワード：<input type="password" name="pass"><br>
						<input type="submit" value="確認">
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
					<a href="./login_db">TOPページに戻る</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>