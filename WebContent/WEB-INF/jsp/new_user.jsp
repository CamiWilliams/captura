<html>
<head>
<title>captura</title>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1.0, maximum-scale=1.0, minimal-ui" />

<link href="frameworks/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
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
			<input type="text" name="name" class="form-control ptext" placeholder="Name"> <br>
			<input type="email" name="email" class="form-control ptext" placeholder="Email"> <br>
			<input type="text" name="username" class="form-control ptext" placeholder="Username"> <br>
			<input type="password" name="password" class="form-control ptext" placeholder="Password"> <br>
			<select name="initLang" class="form-control ptext">
    			<option value="English (US)">English (US)</option>
    			<option value="Spanish">Spanish</option>
    			<option value="French">French</option>
    			<option value="German">German</option>
    			<option value="Chinese">Chinese</option>
			</select>
			<br>
			<br>
			<br>
			<input type="submit" class="form-control" value="Login"/>
		</form>
		<button type="button" class="form-control ptext" onclick="window.location.href='index.html'">
			Cancel
        </button>
	</div>
      ${message}
</body>
</html>