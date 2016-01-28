var request = require('request');
var async = require('async');
var express = require('express');
const browser = require('./clientservice.js');

var app = express();
app.get('/getById',function(req,res){
  browser.handler(req,res);
});



app.listen(3001,function(){
  console.log('Example app listening on port 3001');
});
