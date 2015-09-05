<html>
<head>
<title>captura</title>

<link href="frameworks/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1.0, maximum-scale=1.0, minimal-ui" />
<link rel="stylesheet" type="text/css" href="frameworks/styles.css">
<link href='https://fonts.googleapis.com/css?family=Comfortaa|Lato:400,400italic,700,700italic' rel='stylesheet' type='text/css'>

<link rel="shortcut icon" href="images/captura_mini.png"/>
<link rel="apple-touch-icon" href="images/captura_apple_icon.png">

<script>
function play(speed) {
	var word = document.getElementById('word').innerHTML;
	var lang = document.getElementById('lang').innerHTML;

	var audioElement = document.createElement('audio');
	var audioSpeed;
	if(speed == 1) {
		audioSpeed = -5;
	} else if(speed == 2) {
		audioSpeed = 0;
	} else {
		audioSpeed = 5;
	}
    var url = "http://api.voicerss.org/?";
    var key = "2d7308298de44ed8b20a93a1a4361d60";
	var codec;
    
   	if (audioElement.canPlayType('audio/mpeg') != "")
   		codec = "mp3";
   	else
   		codec = "wav";
   	
   	var audioSrc = url + "key=" + key +"&hl=" + lang + "&src=" + word + "&r=" + audioSpeed + "&c=" + codec + "&rnd=" + Math.random();
   	console.log(audioSrc);
   	
   	audioElement.src = audioSrc;
   	audioElement.play();
}
</script>

</head>
<body>
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
		<div class="ptext">
			${model.message}
			<div id="word">${model.word}</div>
			<div class="info">
				<h3>Pronunciation</h3>
				<button class="form-control speed" id="play" onclick="play(1);">></button>
				<button class="form-control speed" id="play" onclick="play(2);">>></button>
				<button class="form-control speed" id="play" onclick="play(3);">>>></button>
			</div>
			${model.decide}
		</div>
		<br>
		<br>
	</div>
</body>
</html>