//Install express server
const express = require('express');
const path = require('path');
const http = require('http');
const request = require('request');

const app = express();
var options = {
        dotfiles: 'ignore',
        etag: true,
        extensions: ['htm', 'html'],
        index: false,
        maxAge: '1d',
        lastModified: true,
        redirect: false,
        setHeaders: function(res, path, stat) {
            res.set('x-timestamp', Date.now())
            res.set('x-app-name', 'NeoCom.Infinity.Frontend')
            res.set('Access-Control-Allow-Origin', '*');
            res.set('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
            res.set('Access-Control-Allow-Methods', 'OPTIONS, GET, POST, PUT, DELETE');
        }
    }
    // var backendOptions = {
    //     host: 'neocom.infinity.local',
    //     port: 6099,
    //     headers: req.headers
    // };
app.locals.appname = "NEOCOM"
app.locals.version = "v0.16.0POC"
app.locals.port = 8080
app.locals.applicationhome = "/dist/NeoCom-Infinity"
app.locals.backendproxy = "http://neocom.infinity.local:6099"
app.locals.apppath = "/app"
app.locals.apipath = "/api"

// - L O G G I N G
app.use(function(req, res, next) {
    res.header('Access-Control-Allow-Origin', '*')
    res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept')
    res.header('Access-Control-Allow-Methods', 'OPTIONS, GET, POST, PUT, DELETE')
    if ('OPTIONS' === req.method) {
        res.sendStatus(200)
    } else {
        console.log(req.method + " " + req.url)
        next()
    }
})

// - H O M E   P A G E
app.use(express.static(__dirname + '/public', options))
app.get('/', function(req, res) {
    //  console.log(req.method + ' ' + '/public' + '/home.html')
    console.log("Home: " + __dirname + req.url)
    res.status(200).sendFile('/home.html', { root: "./public" });
});
// - S T A T I C
app.use(express.static(__dirname + app.locals.apppath, options));
app.get('*.*', function(req, res) {
    console.log("Static: " + __dirname + app.locals.applicationhome + req.url)
    res.status(200).sendFile(path.join(__dirname + app.locals.applicationhome + req.url));
});
// - A P P   M O U N T P O I N T
app.get('/app/*', function(req, res) {
    console.log('App: ' + __dirname + app.locals.applicationhome + req.url)
        // res.sendFile(path.join(__dirname + req.url));
    res.sendFile(path.join(__dirname + app.locals.applicationhome + '/index.html'));
});
// - A P I   M O U N T P O I N T
app.get('/api/*', function(req, res) {
    console.log("Backend: " + app.locals.backendproxy + req.url);
    request(app.locals.backendproxy + req.url).pipe(res);
})

// - L I S T E N
app.listen(process.env.PORT || app.locals.port || 3000, function() {
    console.log("Node Express server for " + app.locals.appname + " listening on http://localhost:" + app.locals.port);
});