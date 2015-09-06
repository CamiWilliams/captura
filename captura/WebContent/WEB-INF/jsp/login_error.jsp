<html>
<head>
<title>captura</title>

<link href="frameworks/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1.0, maximum-scale=1.0, minimal-ui" />
<link rel="stylesheet" type="text/css" href="frameworks/styles.css">
<link href='https://fonts.googleapis.com/css?family=Comfortaa|Lato:400,400italic,700,700italic' rel='stylesheet' type='text/css'>

<link rel="shortcut icon" href="images/captura_mini.png"/>
<link rel="apple-touch-icon" href="images/captura_apple_icon.png">

</head>
<body>
	<br>
	<div style="text-align:center">
		<div class="logo">captura</div>
		<div style="display: -webkit-inline-box;">
			<div class="teal round-button small"></div>
			<div class="red round-button small"></div>
			<div class="orange round-button small"></div>
			<div class="green round-button small"></div>
		</div>
		<br>
		<p class="ptext" style="text-align:center;">Your username or password was incorrect!</p>
		
		<form action="LoginServlet" method="POST" style="display: inline-block;">
			<input type="text" name="username" class="form-control" placeholder="Username"> <br>
			<input type="password" name="password" class="form-control" placeholder="Password"> <br>
			<br>
			<input type="submit" class="form-control ptext" value="Login"/>
			<br>
			<button type="button" class="form-control ptext" onclick="window.location.href='new_user.html'">
				Create new account
	        </button>
		</form>
		<br>
	</div> ${message}
</body>
</html>