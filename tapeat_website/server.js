var defaultPort = 3000;

var express = require('express');
var cors = require('cors');
var plivo = require('plivo-node');
var bodyParser = require('body-parser');
var p = plivo.RestAPI(require('./config.json'));
var app = express();

app.use(bodyParser.json());
app.use(express.static('./'));
app.use(cors());

app.post('/SMS', function(req, res) {
    var params = {
		'src': '+13305875476',
		'dst' : req.body.dst,
		'text' : req.body.msg,
		'type' : "sms"
	};
	p.send_message(params, function (status, response) {
		console.log(response);
		res.send('Status: ' + status + 'API Response: ' + response);
	});
	console.log(req.body);
});

var port = process.env.PORT || defaultPort;
app.listen(port);
console.log("listen on : localhost:" + port);