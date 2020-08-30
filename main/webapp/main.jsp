<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Users,model.Mutter,java.util.List"%>
<%@ include file="./common.jsp"%>
<%
	// セッションスコープに保存されたユーザー情報を取得
Users loginUser = (Users) session.getAttribute("loginUser");
//セッションスコープに保存されたtopicSelectSessionを取得
Mutter topicSelectSession = (Mutter) session.getAttribute("topicSelectSession");
//リクエストスコープに保存されたつぶやきリストを取得
List<Mutter> mutterList = (List<Mutter>) request.getAttribute("mutterList");
//リクエストスコープに保存されたつぶやきリストを取得
List<Mutter> topicsList = (List<Mutter>) request.getAttribute("topicsList");
//リクエストスコープに保存されたエラーメッセージを取得
String errorMsg = (String)request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メインページ</title>
</head>
<body>
	<!--トピック記入欄にinitialが記述されないようにinitialを消す。-->
	<%
	String topic;
	if (topicSelectSession.getTopic().equals("initial")){
		topic = "";
	}else{
		topic = topicSelectSession.getTopic();
	}
	%>
	<nav class="navbar navbar-expand-sm navbar-dark bg-success mt-3 mb-3">
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav4" aria-controls="navbarNav4"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<a class="navbar-brand" href="#">Confab</a>
		<div class="collapse navbar-collapse justify-content-start">
			<ul class="navbar-nav">
				<li class="nav-item"><a class="nav-link"
					href="./Logout">ログアウト</a></li>
			</ul>
		</div>
		<div class="collapse navbar-collapse justify-content-end">
			<ul class="navbar-nav">
				<li class="nav-item"><a class="nav-link"
					href="./ChangeID">ID変更</a></li>
				<li class="nav-item"><a class="nav-link"
					href="./ChangePass">パスワード変更</a></li>
				<li class="nav-item"><a class="nav-link"
					href="./Unsubscribe">アカウント削除</a></li>
			</ul>
		</div>
	</nav>
	<div class="wakugumi">
		<header class="leftNavigation">
			<div style="text-align: left; margin-left: 25px; margin-right: 10px;">
				※使い方<br>
				右の記入欄にトピック名と書き込み内容を記入し、
				投稿ボタンを押してください。<br>
				<br>
				既存のトピックについて書き込みをする場合は、
				下記一覧にあるトピック名をクリックして下さい。
				選択したトピックについての表示および
				書き込みができます。<br>
				<br>
				新規のトピックを作成する場合は、トピック名を選ばず
				右のトピック記入欄に記入して投稿してください。
			</div>
			<br> トピック一覧
			<!--左枠のトピック名をクリックしたときにそのトピック名をMain.javaに送る。。-->
			<form name="form_all" action="Main" method="post">
				<input type=hidden name="topicSelect" value="initial">
				<a href="" onclick="document.form_all.submit();return false;"> <b>(ALL)</b>
				</a>
			</form>
			<%
			int i=0;
		for(Mutter mutter : topicsList) {
			String s=String.valueOf(i);
			%>
			<form name="form_<%=s%>" action="Main" method="post">
				<input type="hidden" name="topicSelect" value=<%=mutter.getTopic()%>>
				<a href=""onclick="document.form_<%=s%>.submit();return false;">
					<b><%=mutter.getTopic()%></b>
				</a><br>
			</form>
			<%
		i++;
 		}
 			%>
		</header>
		<div class="content">
			<h1>メインページ</h1>
			<p>
				<a href="./Main">更新</a>
			</p>
			<b><%=loginUser.getName()%></b>さん、ログイン中<br>

			<form action="./Main" method="post">
				トピック：<br> <input type="text" value="<%=topic%>"name="topicPost"><br>
				書き込み：<br>
				<textarea name="text" rows="8" cols="50"></textarea>
				<br> <input type="submit" value="投稿"
					style="width: 70px; height: 25px; font-size: 15px">
			</form>
			<br>
			<%
	if (errorMsg != null){
			%>
			<b><font color="#ff0000"><%=errorMsg%></font></b>
			<%
	}else{
			%>
			<br>
			<%
	}
			%>
			<%
	for(Mutter mutter : mutterList) {
			%>
			<div style="padding: 10px; border: 1px solid #333333;">
				<p>
					<font color="red"><i><b><%= mutter.getTopic() %></b></i></font>
				</p>
				<p>
					<b><%= mutter.getUserName() %></b><br>
					<%= mutter.getText() %></p>
			</div>
			<%}%>
		</div>
	</div>
</body>
<style>
#bp {
	margin-left: 15px;
	font-size: 20px;
}

#bo {
	margin-left: 50px;
}
</style>
</html>