var plivo = require('plivo');
var p = plivo.RestAPI(require('./config'));

var params = {
	'src': '3305875476',
	'dst' : '2535088570',
	'text' : "Hi, message from Plivo",
	'type' : "sms",
};

p.send_message(params, function (status, response) {
	console.log('Status: ', status);
	console.log('API Response:\n', response);
});