<html>
<head>
<title>captura</title>

<link href="frameworks/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1.0, maximum-scale=1.0, minimal-ui" />
<link rel="stylesheet" type="text/css" href="frameworks/styles.css">
<link href='https://fonts.googleapis.com/css?family=Comfortaa|Lato:400,400italic,700,700italic' rel='stylesheet' type='text/css'>
</head>
<body>
	<br>
	<div>
		<div class="logo">captura</div>
		<div style="display:-webkit-box; margin-left: 45%;" class="center">
			<div class="green round-button small"></div>
			<div class="red round-button small"></div>
			<div class="orange round-button small"></div>
			<div class="blue round-button small"></div>
		</div>
		<br>
		<form action="LoginServlet" method="POST">
			<input type="text" name="username" class="form-control" placeholder="Username"> <br>
			<input type="password" name="password" class="form-control" placeholder="Password"> <br>
			<br>
			<input type="submit" class="form-control ptext" value="Login"/>
		</form>
		<button type="button" class="form-control ptext" onclick="window.location.href='new_user.html'">
			Create new account
        </button>
	</div> ${message}
</body>
</html>