package com.training.exercise.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.view.ViewResult;
import com.google.common.collect.Lists;
import com.training.exercise.CouchbaseService;
import com.training.exercise.vo.Product;



public class ProductCatalogueServiceImpl implements ProductCatalogueService {
  private final ConcurrentHashMap<Long, Product> productMap = new ConcurrentHashMap<Long, Product>();
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
  
  @Override
  public JsonDocument get(String productId) {
	JsonDocument doc = couchbaseService.read(productId);
    return doc;
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
  public List<Product> getAll(Integer offset,Integer limit) {
	//  ViewResult result = couchbaseService.findAllPrices(offset, limit);
    return Lists.newArrayList(productMap.values());
  }  
}