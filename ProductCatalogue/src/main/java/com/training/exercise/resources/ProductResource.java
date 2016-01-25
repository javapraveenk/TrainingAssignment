package com.training.exercise.resources;

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
		return Response.created(null).build();
	}

	
	@GET
	public Response getProducts(@QueryParam("offset") String offset,@QueryParam("limit") String limit) {
		return Response.ok(productCatalogueService.getAll(Integer.valueOf(offset),Integer.valueOf(limit)).toString()).build();
	}
	
	@GET
	@Path("/{productId}")
	public Response getProductById(@PathParam("productId") String productId) {		
		return Response.ok(productCatalogueService.get(productId).toString()).build();
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
