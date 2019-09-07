// - M A I N   S E R V E R
const express = require('express')
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
    }
}
app.locals.appname = "NEOCOM"
app.locals.version = "v0.16.0POC"
app.locals.port = 3000
app.locals.apppath = "app"
app.locals.apipath = "api"

// - S T A T I C
app.use(express.static('public', options))
app.get('/', function (req, res) {
    console.log("Routing to page: ./public/index.html")
    res.status(200).sendFile(`/`, {root: "./public"});
})

// - A P P   M O U N T P O I N T
const frontend = express()
frontend.get('/', function (req, res) {
  console.log("Page: "+frontend.mountpath+ res.path)
  res.status(200).sendFile(`/`, {root: app.locals.apppath});
})
app.use('/app', frontend)

// - A P I   M O U N T P O I N T
const backend = express()
backend.get('/', function (req, res) {
  console.log("Backend: "+backend.mountpath+ res.path)
  res.status(200).sendFile(`/`, {root: app.locals.apipath});
})
app.use('/api', backend)

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

app.listen(app.locals.port)
