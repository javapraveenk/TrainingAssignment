package com.training.exercise.resources;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.training.exercise.service.ProductCatalogueService;
import com.training.exercise.vo.Product;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {
	 
	 private  ProductCatalogueService productCatalogueService;

	   
	 public ProductResource(ProductCatalogueService productCatalogueService) {
	        this.productCatalogueService = productCatalogueService;
	    }
	    
	@POST	
	public Response createProduct(Product p) {		
        String pId = productCatalogueService.add(p);        
		return Response.ok(null).build();
	}
	
	@POST	
	@Path("/async")
	public Response createProductAsync(Product p){
		productCatalogueService.addAsync(p);
		return Response.ok(null).build();
		
	}
	
	@POST	
	@Path("/syncBlockObs")
	public Response createProductSyncBlockObservable(Product p){
		productCatalogueService.addSyncBlockObservable(p);
		return Response.ok(null).build();
		
	}

	
	@GET	
	public Response getProducts(@QueryParam("offset") String offset,@QueryParam("limit") String limit) {
		JsonArray result = productCatalogueService.getAll(Integer.valueOf(offset),Integer.valueOf(limit));
	
		if (result == null) {
            //TODO maybe detect type of error and change error code accordingly
            return  Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        } else {
            return Response.ok(result.toString()).build();
        }
		
	}
	
	@GET	
	@Path("/batch/{Ids}")	
	public Response getProductsInBatch(@PathParam("Ids") List<String> Ids) {
		System.out.println("params :"+Ids);
		System.out.println("size :"+Ids.size());
		String commaSeperatedString = Ids.get(0);
		String[] arr = commaSeperatedString.split(",");
		List<String> docs = Arrays.asList(arr);
		System.out.println("new size :"+docs.size());
		JsonArray result = productCatalogueService.getProductsInBatch(docs);
	
		if (result == null) {
            //TODO maybe detect type of error and change error code accordingly
            return  Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        } else {
            return Response.ok(result.toString()).build();
        }
		
	}
	
	
	@GET
	@Path("/search")
	public Response getProductsByType(@QueryParam("pType") String pType) {
		JsonArray result = productCatalogueService.getProductsByType(pType);
		return Response.ok(result.toString()).build();
	}
	
	@GET
	@Path("/{productId}")
	public Response getProductById(@PathParam("productId") String productId) {	
		
		return Response.ok(productCatalogueService.get(productId).content().toMap()).build();
	}
	
	@DELETE
	@Path("/{productId}")
	public Response deleteProduct(@PathParam("productId") String productId){
		JsonDocument document = productCatalogueService.delete(productId);;
		
		if(document != null){
			return Response.status(HttpStatus.NO_CONTENT_204).build();
		}
		else{
			return Response.status(HttpStatus.NOT_FOUND_404).build();
		}
	}
}
