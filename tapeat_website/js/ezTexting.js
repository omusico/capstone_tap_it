'use strict'

var url = "https://app.eztexting.com/sending/messages?format=json";
var user = "tapeatinc";
var pass = "capstone15";

function sendSMS(number, title, msg) {
	/*var data = "User=" + user
				+ "&Password=" + pass
				+ "&PhoneNumbers[]=" + number
				+ "&Subject=" + title
				+ "&Message=" + msg;*/
	var data = {
		'format' : 'json',
		'User' : user,
		'Password' : pass,
		'PhoneNumbers[]' : number,
		'Subject' : title,
		'Message' : msg
	};
	console.log(data);
	$.ajax({
		type		: "POST",
		url			: "https://app.eztexting.com/sending/messages",
		data		: data,
		contentType	: "application/json; charset=utf-8",
		crossDomain	: true,
		dataType	: "json",
		success		: function (data, status, jqXHR) {
			console.log(status);
		},

		error		: function (jqXHR, status) {
			console.log(status);
		}
	});
}

sendSMS('2535088570', 'TapEat Notification', 'table ready');
