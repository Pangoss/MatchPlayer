var createError = require('http-errors');
var bodyParser = require('body-parser');
const express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var app = express();
var router = express.Router();


//ROUTING
var userRouter = require('./routes/users');
var indexRouter = require('./routes/index');
var eventRouter = require('./routes/events');
app.use('/events', eventRouter);
app.use('/', indexRouter);
app.use(`/users`, userRouter);


//ROUTING

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');
// view engine setup

//
app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
//


// HEADERS MANAGEMENT
app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, x-access-token, Accept");
  next();
});
// HEADERS MANAGEMENT


// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});
// catch 404 and forward to error handler


// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
