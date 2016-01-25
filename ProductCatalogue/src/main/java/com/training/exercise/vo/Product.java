package com.training.exercise.vo;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class Product {
	private String productId;

	private String content;

	private String productName;

	private String productType;

	

	public Product() {
	}
	
	public Product(String productId, String content) {
		this.productId = productId;
		this.content = content;
	}
	
	public Product(String productId, String content,String productName,String productType) {
		this.productId = productId;
		this.content = content;
		this.productName = productName;
		this.productType = productType;
		
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	


	public String getProductId() {
		return productId;
	}

	public void setProduct(String productId) {
		this.productId = productId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
}
