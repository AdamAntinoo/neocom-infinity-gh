// - M A I N   S E R V E R
const express = require('express')
const path = require('path');

const app = express()
var options = {
    dotfiles: 'ignore',
    etag: true,
    extensions: ['htm', 'html'],
    index: false,
    maxAge: '1d',
    lastModified: true,
    redirect: false,
    setHeaders: function (res, path, stat) {
        res.set('x-timestamp', Date.now())
        res.set('x-app-name', 'NeoCom.Infiniti POC')
        res.set('Access-Control-Allow-Origin', '*');
        res.set('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
        res.set('Access-Control-Allow-Methods', 'OPTIONS, GET, POST, PUT, DELETE');
    }
}
app.locals.appname = "NEOCOM"
app.locals.version = "v0.16.0POC"
app.locals.port = 8080
app.locals.apppath = "/ui"
app.locals.apipath = "api"

// - L O G G I N G
app.use(function (req, res, next) {
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

// - S T A T I C
app.use(express.static(__dirname + '/public', options))
app.get('/', function (req, res) {
    console.log(req.method + ' ' + '/public' + '/home.html')
    res.status(200).sendFile('/home.html', { root: "./public" });
})

// - A P P   M O U N T P O I N T
// app.use(express.static(__dirname + app.locals.apppath, options));
// app.get('/app/*.*', function (req, res) {
//     res.sendFile(path.join(__dirname + req.url));
// });
// app.get('/app/*', function (req, res) {
//     res.sendFile(path.join(__dirname + app.locals.apppath + '/index.html'));
// });
// app.get('/app/', function (req, res) {
//     res.sendFile(path.join(__dirname + app.locals.apppath + '/index.html'));
// });
// app.get('/app', function (req, res) {
//     res.sendFile(path.join(__dirname + app.locals.apppath + '/index.html'));
// });

app.use(express.static(__dirname + app.locals.apppath, options));
app.get('/ui/*.*', function (req, res) {
    res.sendFile(path.join(__dirname + req.url));
});
app.get('/ui/*', function (req, res) {
    res.sendFile(path.join(__dirname + app.locals.apppath + '/index.html'));
});
app.get('/ui/', function (req, res) {
    res.sendFile(path.join(__dirname + app.locals.apppath + '/index.html'));
});
app.get('/ui', function (req, res) {
    res.sendFile(path.join(__dirname + app.locals.apppath + '/index.html'));
});

// - A P I   M O U N T P O I N T_port
const backend = express()
backend.get('/', function (req, res) {
    console.log("Backend: " + backend.mountpath + res.path)
    res.status(200).sendFile(`/`, { root: app.locals.apipath });
})
app.use('/api', backend)

app.listen(process.env.PORT || app.locals.port || 3000, function () {
    console.log("Node Express server for " + app.locals.appname + " listening on http://localhost:" + app.locals.port);
});
// app.get('*.*', express.static(app.locals.apppath, { maxAge: '1y' }));

// ---- SERVE APLICATION PATHS ---- //
// app.all('app/*', function (req, res) {
//     console.log("Ruting to page: ".res.path)
//     res.status(200).sendFile(`/`, {root: app.locals.apppath});
// });


// app.use(express.static('assets', options))
// app.use('/assets', express.static('assets'))
// app.get('/', function (req, res) {
//     res.send('hello world')
// })

// app.listen(app.locals.port)
