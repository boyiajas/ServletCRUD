<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.productwebapp.model.Product" %>
<%@ page import="com.productwebapp.model.Category" %>
<%@ page import="com.productwebapp.model.Product_Images" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product Management Application</title>

<% Product product = (Product) request.getAttribute("product"); %>
<% Category category = (Category) request.getAttribute("category"); %>
<% Product_Images singleProductImage = (Product_Images) request.getAttribute("singleProductImage"); %>


<link href="styles.css" rel="stylesheet" type="text/css">
</head>
<body>
	
	<h2>Welcome to Product Management Application</h2>
	<section class="section">
	
		<form action="submitProduct">
			<input type="text" name="id" class="search-input" placeholder="   Enter a search for product by Name, Category or Price"/>
			<input type="submit" class="btn pull-right btn-search" value="-- Search --" />
		</form>
				
		 
		<br/>
		<h4>Product Details <span class="pull-right"><a href="view-all" class="btn"> < Previous Page</a></span></h4>
		
		<table class="table">
		
			<tr>
				<td><img src="<%= singleProductImage.getImage_url() %>" alt="Image" height="250px"></td>
				<td><p><b>Product Name:</b> <%= product.getProduct_name() %></p><hr/><p><b>Product Description:</b></p>
					<p><%= product.getDescription() %></p>
					<p><b>Category Name:</b> <%= category.getCategory_name() %></p>
					
					<p style="text-align:right;">
						<a href="edit?id=<%= product.getProduct_id() %>" class="btn btn-edit">Edit</a> 
						<a href="delete?id=<%= product.getProduct_id() %>"  class="btn btn-del" >Remove</a></td>
						
					</p>
				</td>				
			</tr>
			<tr>
				<td>Other Images</td>
			</tr>
			<tr>
				<td>
					
					<%
					List<Product_Images> listProductImages = (List<Product_Images>)request.getAttribute("productImages");
					if (listProductImages != null && !listProductImages.isEmpty()) {
						for (int i = 0; i < listProductImages.size(); i++) {
							Product_Images productImages = listProductImages.get(i);
						%>	
						<img src="<%= productImages.getImage_url() %>" alt="Image" height="50px" class="thumbnail">
													
					<%
						}
					}
					%>		
										
				</td>
			</tr>
		</table>
	</section>
</body>
</html>