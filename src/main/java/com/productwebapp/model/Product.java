package com.productwebapp.model;

public class Product {
	
	public Product(int product_id, int category_id, String product_name, String description, double price) {
		super();
		this.product_id = product_id;
		this.category_id = category_id;
		this.product_name = product_name;
		this.description = description;
		this.price = price;
	}
	
	public Product(int category_id, String product_name, String description, double price) {
		super();
		this.category_id = category_id;
		this.product_name = product_name;
		this.description = description;
		this.price = price;
	}
	
	public Product() {
		super();
	}

	protected int product_id, category_id;
	protected String product_name, description;
	protected double price;
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
	 * @return the category_id
	 */
	public int getCategory_id() {
		return category_id;
	}
	/**
	 * @param category_id the category_id to set
	 */
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	/**
	 * @return the product_name
	 */
	public String getProduct_name() {
		return product_name;
	}
	/**
	 * @param product_name the product_name to set
	 */
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Product [product_id=" + product_id + ", category_id=" + category_id + ", product_name=" + product_name
				+ ", description=" + description + ", price=" + price + "]";
	} 
	
	
}
