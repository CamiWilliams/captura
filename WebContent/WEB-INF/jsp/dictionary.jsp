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
	<div class="content">
		<div class="logo">captura</div>
		<div style="display:-webkit-inline-box;">
			<a href="home.html" class="green round-button medium"><span class="glyphicon glyphicon-home medium" aria-hidden="true"></span></a>
			<a href="camera.html" class="red round-button medium"><span class="glyphicon glyphicon-camera medium" aria-hidden="true"></span></a>
			<a href="dictionary.html" class="orange round-button medium"><span class="glyphicon glyphicon-book medium" aria-hidden="true"></span></a>
			<a href="settings.html" class="blue round-button medium"><span class="glyphicon glyphicon-cog medium" aria-hidden="true"></span></a>
		</div>
		<br>
		<br>
		<h2 class="ptext">Dictionaries</h2>
		<form method="POST" action="dictionary.html" commandName="newDictionary">
			<div class="col-md-4">
				<button type="submit" class="form-control">Add Dictionary</button>
			</div>
			<div class="col-md-7">
				<select name="newDictionary" class="form-control">
					${model.addLanguages}
				</select>
			</div>
		</form>	
		<br>
		<br>
		<br>
		<br>
		<form method="POST" action="lang_dictionary.html" commandName="langSelect">
			${model.message}
		</form>
		<br>
		<br>
	</div>
</body>
</html>