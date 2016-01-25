package com.training.exercise.service;

import java.util.List;

import com.couchbase.client.java.document.JsonDocument;
import com.training.exercise.vo.Product;

public interface ProductCatalogueService {
  String add(Product product);

  JsonDocument get(String id);

  List<Product> getAll(Integer offset,Integer limit);

  JsonDocument update(String id, Product product);

  JsonDocument delete(String productId);
}
