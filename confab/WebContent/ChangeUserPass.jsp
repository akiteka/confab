<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	//リクエストスコープに保存されたエラーメッセージを取得
	String errorMsg = (String) request.getAttribute("errorMsg");
%>
<%@ include file="./common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>パスワード変更</title>
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
					<h1>パスワードの変更</h1>
					<p>
					<form action="./ChangePass" method="post">
						現在のパスワード：<input type="password" name="old"><br>
						新規のパスワード：<input type="password" name="new"><br>
						新規のパスワード(確認)：<input type="password" name="confirm"><br>
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
					<a href="./Main">メインページに戻る</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>