var request = require('request');
var async = require('async');

exports.handler = function(req, res) {
  async.parallel([
    /*
     * First external endpoint
     */
    function(callback) {
      var productId = req.param('productId');
      console.log(productId);
      var url = "http://localhost:8080/products/"+productId;
      request(url, function(err, response, body) {
        // JSON body
        if(err) { console.log(err); callback(true); return; }
        obj = JSON.parse(body);
        callback(false, obj);
      });
    },
    /*
     * Second external endpoint
     */
    function(callback) {
      var productId = req.param('productId');
      var url = "http://localhost:8085/pricing/"+productId;
      request(url, function(err, response, body) {
        // JSON body
        if(err) { console.log(err); callback(true); return; }
        obj = JSON.parse(body);
        callback(false, obj);
      });
    },
  ],
  /*
   * Collate results
   */
  function(err, results) {
    if(err) { console.log(err); res.send(500,"Server Error"); return; }
    mergeResult = {};
    mergeResult.productId = results[0].productId;
    mergeResult.productName = results[0].productName;
    mergeResult.content = results[0].content;
    mergeResult.productType = results[0].productType;
    mergeResult.price = results[1].price;

    res.send(mergeResult);

  //  res.send({productInfo:results[0], priceInfo:results[1]});
  }
  );
};


exports.batchHandler = function(req, res) {
  async.parallel([
    /*
     * First external endpoint
     */
    function(callback) {
      var productType = req.param('productType');
      console.log(productType);
      var url = "http://localhost:8080/products?productType="+productType;
      request(url, function(err, response, body) {
        // JSON body
        if(err) { console.log(err); callback(true); return; }
        obj = JSON.parse(body);
        console.log(obj);
        for(var i=0; i<obj.length; i++){
          console.log('********');
          console.log(obj[i]);
        }
        //callback(false, obj);
      });
    },
    /*
     * Second external endpoint
     */
    function(callback) {
      var productType = req.param('productType');
      var url = "http://localhost:9090/productprices/05e01471-cb7d-46b3-9b1e-5c08e34a5a69";

      request(url, function(err, response, body) {
        // JSON body
        if(err) { console.log(err); callback(true); return; }
        obj = JSON.parse(body);
        callback(false, obj);
      });

    },
  ],
  /*
   * Collate results
   */
  function(err, results) {
    if(err) { console.log(err); res.send(500,"Server Error"); return; }
    res.send({productInfo:results[0], priceInfo:results[1]});
  }
  );
};
