<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- BootstrapのCSS読み込み -->
<link href="http://localhost:8080/confab/css/bootstrap.min.css"
	rel="stylesheet">

<!-- jQuery読み込み -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

<!-- BootstrapのJS読み込み -->
<script src="http://localhost:8080/confab/js/bootstrap.min.js"></script>
</head>
<body>

</body>
<style>
footer {
	position: absolute;
	bottom: 0;
	width: 100%;
}

.foot {
	text-align: center;
}

.parent {
	position: relative;
	height: 100vh;;
	width: auto;
}

.inner {
	width: 60%;
	height: 200px;
	top: 0;
	bottom: 0;
	left: 0;
	right: 0;
	position: absolute;
	margin: auto;
	font-size: 20px;
}

.wakugumi {
	display: flex;
	height: 100%;
}

.leftNavigation {
	width: 400px;
	text-align: center;
	background-color: #66cdaa;
	color: #fff;
	min-height: 100vh;
}

.content {
	flex: 1;
	margin-left: 10px;
}
</style>
</html>