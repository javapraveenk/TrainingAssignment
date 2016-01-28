package com.training.exercise.service;

import java.util.List;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.training.exercise.vo.Product;

public interface ProductCatalogueService {
  String add(Product product);
  
  String addAsync(Product product);
  
  JsonDocument addSyncBlockObservable(Product product) ;

  JsonDocument get(String id);

  JsonArray getAll(Integer offset,Integer limit);
  
  JsonArray getProductsInBatch(List<String> documents);

  JsonDocument update(String id, Product product);

  JsonDocument delete(String productId);
  
  JsonArray getProductsByType(String pType);
}
