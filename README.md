# TrainingAssignment
TrainingAssignment

This project has 3 services module :

1.ProductCatalogue microservice

2.pricing microservice

3.NodeJsClient Service


ProductCatalogue service is built using DropWizard and Gradle and currently using embedded jetty server running on port 8080.
To run this service , run Application.java as SpringBoot application .
Following are the end points of this service :

- Adds a product (saving into couchbase using rxJava) asynchronously

POST 

http://localhost:8080/products/async

body
{
  "productId": "P11111",
  "content": "samsung tablet",
  "productName": "samsung tablet",
  "productType": "tablet"
}

- Add Product implementation to Synchronous using BlockingObservable
POST
http://localhost:8080/products/syncBlockObs

body
{
  "productId": "P11111",
  "content": "samsung tablet",
  "productName": "samsung tablet",
  "productType": "tablet"
}

- Retrieve the list of products based on simple search criteria (by creating and using a view in couchbase) Search criteria is Product Type 

GET

http://localhost:8080/products/search?pType=mobile

- Get at least ten products in a batch from couchbase (using RxJava) and expose an API for the same

GET

http://localhost:8080/products/batch/P11111,P11112,P11113,P11114,P11115,P11116,P11117

- Implement remove a product feature from the catalogue. 

DELETE

http://localhost:8080/products/P11112




pricing service is built using SpringBoot and Maven and currently using embedded tomcat server running on port 8085.
To run this service , run gradle clean run or using Eclipse gradle plugin .
Following are the end points of this service :

- Return the price of a given product.

GET

http://localhost:8085/pricing/P11111





NodeJsClient is built using Node.js.

There are 2 services running on port 2001 and 3001

- To get a single product and price information together in a JSON format

GET

http://localhost:3001/ getById?productId=P111110

- To get all the products and price information together in a JSON format

GET

http://localhost:2001/getInBatch
