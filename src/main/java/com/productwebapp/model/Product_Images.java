package com.productwebapp.model;

public class Product_Images {
	public Product_Images(int image_id, int product_id, String image_url) {
		super();
		this.image_id = image_id;
		this.product_id = product_id;
		this.image_url = image_url;
	}
	
	public Product_Images(int product_id, String image_url) {
		super();
		this.product_id = product_id;
		this.image_url = image_url;
	}
	/**
	 * @return the image_id
	 */
	public int getImage_id() {
		return image_id;
	}
	/**
	 * @param image_id the image_id to set
	 */
	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}
	/**
	 * @return the product_id
	 */
	public int getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id the product_id to set
	 */
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return the image_url
	 */
	public String getImage_url() {
		return image_url;
	}
	/**
	 * @param image_url the image_url to set
	 */
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	protected int image_id, product_id;      
	protected String image_url; 
}
