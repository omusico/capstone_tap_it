var connect = require('connect');
var serveStatic = require('serve-static');
var port = 8080;
console.log("start listen on address    " + "localhost:" + port);
connect().use(serveStatic(__dirname)).listen(port);
