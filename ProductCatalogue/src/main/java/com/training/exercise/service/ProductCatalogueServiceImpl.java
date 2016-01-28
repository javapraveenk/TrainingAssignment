package com.training.exercise.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;
import com.training.exercise.CouchbaseService;

import rx.Observable;

import com.training.exercise.vo.Product;



public class ProductCatalogueServiceImpl implements ProductCatalogueService {
  
  private final CouchbaseService couchbaseService;
  
  public ProductCatalogueServiceImpl(CouchbaseService couchbaseService) {
	  this.couchbaseService = couchbaseService;
  }
  
  @Override
  public String add(Product product) {
	String pId = String.valueOf(product.getProductId());
    JsonObject prd = JsonObject.create();
	prd.put("productId",product.getProductId());
	prd.put("content",product.getContent());
	prd.put("productName",product.getProductName());
	prd.put("productType",product.getProductType());
    JsonDocument doc = CouchbaseService.createDocument(pId,prd);
    couchbaseService.create(doc);
    return pId;
  }
  
  public String addAsync(Product product) {
		String pId = String.valueOf(product.getProductId());
	    JsonObject prd = JsonObject.create();
		prd.put("productId",product.getProductId());
		prd.put("content",product.getContent());
		prd.put("productName",product.getProductName());
		prd.put("productType",product.getProductType());
	    JsonDocument doc = CouchbaseService.createDocument(pId,prd);
	    Observable<JsonDocument> returnObservable = couchbaseService.createAsync(doc);
	    returnObservable.forEach(jsonDoc->System.out.println("---------------Data paersited in couchbase async manner"+jsonDoc.toString()));
	    return pId;
	  }
  
  public JsonDocument addSyncBlockObservable(Product product) {
		String pId = String.valueOf(product.getProductId());
	    JsonObject prd = JsonObject.create();
		prd.put("productId",product.getProductId());
		prd.put("content",product.getContent());
		prd.put("productName",product.getProductName());
		prd.put("productType",product.getProductType());
	    JsonDocument doc = CouchbaseService.createDocument(pId,prd);
	    JsonDocument Jdoc = couchbaseService.createAsync(doc).toBlocking().single();
	   
	    return Jdoc;
	  }
  
  
  @Override
  public JsonDocument get(String productId) {
	JsonDocument doc = couchbaseService.read(productId);
    return doc;
  }
  
  @Override
  public JsonArray getProductsByType(String pType) {
	JsonArray arr = couchbaseService.findProductsByType(pType);
    return arr;
  }

  @Override
  public JsonDocument update(String id, Product product) {
	  JsonObject prd = JsonObject.create();
		prd.put("productId",product.getProductId());
		prd.put("content",product.getContent());
		prd.put("productName",product.getProductName());
		prd.put("productType",product.getProductType());
		JsonDocument doc = couchbaseService.update(CouchbaseService.createDocument(id, prd));
		return doc;
  }

  @Override
  public JsonDocument delete(String productId) {
	   JsonDocument deleted = couchbaseService.delete(productId);
	   return deleted;
  }

  @Override
  public JsonArray getAll(Integer offset,Integer limit) {
	 JsonArray arr = JsonArray.create();
	ViewResult result = couchbaseService.findAllProducts(offset, limit);	
	 for (ViewRow row : result) {
		    JsonDocument doc = row.document();
		    System.out.println(doc.content());
		    arr.add(doc.content());
		}
    return arr;
  } 
  
  @Override
  public JsonArray getProductsInBatch(List<String> documents) {
	JsonArray arr = JsonArray.create();
	List<JsonDocument> result = couchbaseService.findAllProductsInBatch(documents);	

	 for (JsonDocument row : result) {		   
		  System.out.println("returned --"+row.content());
		    arr.add(row.content());
		}
    return arr;
  } 
  
  
}