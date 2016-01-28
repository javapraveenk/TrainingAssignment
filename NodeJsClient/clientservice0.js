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
  var getAll = '[';
    for(var i=0; i<temp.length; i++){
      var tempUrl = 'http://localhost:3001/getById?productId=';
      var url = tempUrl+temp[i].productId;
      arr.push(url);
    }

    async.map(arr, function(item, callback){
      request(item, function (error, response, body) {
        callback(error, body);
      });
    }, function(err, results){
      console.log('********    merge      ***************************************');
      console.log(results);
      console.log('**************************');

      res.send(results);
    });
  }
  );
};
