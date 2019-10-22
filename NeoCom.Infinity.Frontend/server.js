//Install express server
const express = require('express');
const path = require('path');
const compression = require('compression');
const request = require('request');
const config = require('config');

// - S E R V E R   O P T I O N S
const app = express();
app.use(compression());
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
};
// - S E R V E R   C O N F I G U R A T I O N
app.locals.appname = config.get('settings.appname');
app.locals.version = config.get('settings.version');
app.locals.port = process.env.PORT || config.get('settings.port');
app.locals.applicationhome = config.get('settings.applicationhome');
app.locals.backendproxy = config.get('settings.backendproxy');
app.locals.apppath = config.get('settings.apppath');
// app.locals.apipath = config.get('apipath');
app.locals.homepage = config.get('pages.homepage');
app.locals.staticpublic = config.get('pages.staticpublic');

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
    res.status(200).sendFile(app.locals.homepage, { root: app.locals.staticpublic });
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
    console.log("Node Express server for " + app.locals.appname +
        " listening on [" + app.locals.backendproxy + "] " +
        app.locals.port);
});