// - M A I N   S E R V E R
const express = require('express')
const path = require('path');
const compression = require("compression");

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
}
app.locals.appname = "NEOCOM"
app.locals.version = "v0.16.0-LOCAL"
app.locals.port = 8080
app.locals.apppath = "/app"
app.locals.apipath = "/api"
app.locals.appservepath = "dist/NeoCom-Infinity"

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
    console.log(req.method + ' ' + '/public' + '/home.html')
    res.status(200).sendFile('/home.html', { root: "./public" });
})

// - S T A T I C
app.server.get('*.*', express.static(app.locals.appservepath, { maxAge: '1y' }));

// - A P P   M O U N T P O I N T
// app.use(express.static(__dirname + app.locals.apppath, options));
app.all('*', function(req, res) {
    console.log('/app: ' + __dirname + req.url)
        // res.sendFile(path.join(__dirname + req.url));
    res.status(200).sendFile(`/`, { root: app.locals.appservepath });
});
// app.get('/app/*', function(req, res) {
//     res.sendFile(path.join(__dirname + app.locals.apppath + '/index.html'));
// });
// app.get('/app/', function(req, res) {
//     res.sendFile(path.join(__dirname + app.locals.apppath + '/index.html'));
// });
// app.get('/app', function(req, res) {
//     res.sendFile(path.join(__dirname + app.locals.apppath + '/index.html'));
// });

// - A P I   M O U N T P O I N T
const backend = express()
backend.get('/api', function(req, res) {
    console.log("Backend: " + backend.mountpath + res.path)
    res.status(200).sendFile(`/`, { root: app.locals.apipath });
})
app.use('/api', backend)

app.listen(process.env.PORT || app.locals.port || 3000, function() {
    console.log("Node Express server for " + app.locals.appname + " listening on http://localhost:" + app.locals.port);
});