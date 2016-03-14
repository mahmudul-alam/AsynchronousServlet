<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Notification</title>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-2.1.3.min.js"></script>
<SCRIPT TYPE="text/javascript">
	var parsedResp = {
		"response": {
			"messages": [],
			"timestamp": 0
		}
	};
	function go() {
		var url = "async/publishNotification?type="
				+ document.getElementById("type").value + "&lastUpdateTime="
				+ parsedResp.response.timestamp;
		var request = new XMLHttpRequest();
		var counter = 0;
		request.open("GET", url, true);
		request.setRequestHeader("Content-Type", "application/x-javascript;");
		request.onreadystatechange = function() {
			if (request.readyState == 4) {
				if (request.status == 200) {
					if (request.responseText) {
						parsedResp = $.parseJSON(request.responseText);
						$('#messages').append(parsedResp.response.messages);
					}
				}
				var date = new Date();
				var curTime = date.getTime();
				go();
			}
		};
		request.send(null);
	}
	
	$(function(){});
</SCRIPT>
</head>
<body>
	<!-- <h1>Rapid Fire Weather</h1> -->
	<input type="text" id="type" name="type" value="" />
	<input type="button" onclick="go()" value="Go!" />
	<div id="messages"></div>
</body>
</html>