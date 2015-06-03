var express = require('express');
var plivo = require('plivo-node');
var p = plivo.RestAPI(require('./config.json'));
var app = express();

app.get('/', function(req, res){
    var params = {
		'src': '+13305875476',
		'dst' : '+12535088570',
		'text' : "Hi, message from Plivo",
		'type' : "sms",
	};
	p.send_message(params, function (status, response) {
		console.log(response);
		res.send('Status: ' + status + 'API Response: ' + response);
	});
  
});

app.listen(3000);
console.log("listen")