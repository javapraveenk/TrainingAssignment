var request = require('request');
var async = require('async');
var express = require('express');

const browser2 = require('./clientservice0.js');
var app = express();


app.get('/getInBatch',function(req,res){
  browser2.batchHandler2(req,res);
});

app.listen(2001,function(){
  console.log('Example app listening on port 2001');
});
