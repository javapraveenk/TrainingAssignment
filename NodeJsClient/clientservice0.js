var request = require('request');
var async = require('async');


exports.batchHandler2 = function(req, res) {
  async.parallel([
    /*
     * First external endpoint
     */
    function(callback) {
      var productType = req.param('productType');
      console.log(productType);
      var url = "http://localhost:8080/products?offset=0&limit=0";
      request(url, function(err, response, body) {
        // JSON body
          obj = JSON.parse(body);
        if(err) { console.log(err); callback(true); return; }
        callback(false, obj);
      });
    },

  ],
  /*
   * Collate results
   */
  function(err, results) {
    if(err) { console.log(err); res.send(500,"Server Error"); return; }
  var temp = results[0];
  var arr=[];
    for(var i=0; i<temp.length; i++){
      console.log('********');
      console.log(temp[i].productId);
      var tempUrl = 'http://localhost:3001/getById?productId=';
      var url = tempUrl+temp[i].productId;
      console.log(url);
      arr.push("'"+url+"'");
    }
  //  console.log('new arr'+arr);

var tArr = '['+arr[0]+','+arr[1]+','+arr[2]+','+arr[3]+','+arr[4]+','+arr[5]+','+arr[6]+','+arr[7]+','+arr[8]+']';
console.log('tArr : '+tArr);

var abc = ['http://localhost:3001/getById?productId=P11111',
     'http://localhost:3001/getById?productId=P11112',
     'http://localhost:3001/getById?productId=P11113'] ;

    async.map(tArr, function(item, callback){
      request(item, function (error, response, body) {
        callback(error, body);
      });
    }, function(err, results){
      console.log("merge:"+results);
      res.send(results);
    });


  }
  );
};
