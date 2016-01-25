package com.training.exercise;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.training.exercise.resources.ProductResource;
import com.training.exercise.service.ProductCatalogueService;
import com.training.exercise.service.ProductCatalogueServiceImpl;


public class App extends Application<ProductsConfiguration> {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	@Override
	public void initialize(Bootstrap<ProductsConfiguration> b) {
	}

	@Override
	public void run(ProductsConfiguration p, Environment e)
			throws Exception {
		LOGGER.info("Method App#run() called");	;
		System.out.println("Coucbase Bucket : " + p.getCouchbaseBucket());
		CouchbaseService cs = new CouchbaseService(p);
		ProductCatalogueService ps = new ProductCatalogueServiceImpl(cs);
		e.jersey().register(new ProductResource(ps));
	}

	public static void main(String[] args) throws Exception {
		new App().run(args);
	}
}