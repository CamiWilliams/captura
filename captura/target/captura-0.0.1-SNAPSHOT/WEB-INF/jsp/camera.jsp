<html>
<head>
<title>captura</title>

<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1.0, maximum-scale=1.0, minimal-ui" />
<link href="frameworks/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet" type="text/css" href="frameworks/styles.css">
<link href='https://fonts.googleapis.com/css?family=Comfortaa|Lato:400,400italic,700,700italic' rel='stylesheet' type='text/css'>
<link rel="shortcut icon" href="images/captura_mini.png"/>
<link rel="apple-touch-icon" href="images/captura_apple_icon.png">

</head>
<body>
	<br>
	<div class="content">
		<div class="logo">captura</div>
		<div style="display:-webkit-inline-box;">
			<a href="home.html" class="teal round-button medium"><span class="glyphicon glyphicon-home medium" aria-hidden="true"></span></a>
			<a href="camera.html" class="red round-button medium"><span class="glyphicon glyphicon-camera medium" aria-hidden="true"></span></a>
			<a href="dictionary.html" class="orange round-button medium"><span class="glyphicon glyphicon-book medium" aria-hidden="true"></span></a>
			<a href="settings.html" class="green round-button medium"><span class="glyphicon glyphicon-cog medium" aria-hidden="true"></span></a>
		</div>
		<br>
		<br>
		<h2 class="ptext">Decipher</h2>
		<br>
		<form class="" method="POST" action="upload.html" enctype="multipart/form-data" style="display: inline-block;">
      		<div class="col-md-3">
      			<p class="ptext">Language</p>
      		</div>
      		<div class="col-md-8">
				<select name="langSelect" class="form-control ptext">
							${message}
				</select>
			</div>
			<br>
			<br>
			<br>
      		<input type="file" name="pic" accept="image/*" class="ptext">
			<br>
			<br>
  			<input class="form-control" type="submit" value="Upload">
		</form>
		<br>
		<br>
		<br>
		
		${notwork}
		</div>
	</body>
</html>