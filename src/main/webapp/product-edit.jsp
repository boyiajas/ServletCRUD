<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.productwebapp.model.Category" %>
<%@ page import="com.productwebapp.model.Product" %>
<%@ page import="com.productwebapp.model.Product_Images" %>
<%@ page import="java.util.List" %>

<% Product_Images singleProductImage = (Product_Images) request.getAttribute("singleProductImage"); %>
<% Product product = (Product) request.getAttribute("product"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product Management Application</title>

			

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
		<h4>Edit Product <span class="pull-right"><a href="view-all" class="btn"> < Previous Page</a></span></h4>
		<form method="post" action="update" enctype="multipart/form-data">
		<table class="table">
			<tr>
				<td>Select Category</td>
				<td>
		
					<p>
						<select name="category_id" class="search-input">
							<option>select a Category</option>
			
						<%
							List<Category> listCategories = (List<Category>)request.getAttribute("listCategories");
							if (listCategories != null && !listCategories.isEmpty()) {
								for (int i = 0; i < listCategories.size(); i++) {
									Category category = listCategories.get(i);
									
									if(category.getCategory_id() == product.getCategory_id()){
								%>	
								<option id="<%= category.getCategory_id() %>" value="<%= category.getCategory_id() %>" selected><%= category.getCategory_name() %></option>
								<% }else{ %>
								<option id="<%= category.getCategory_id() %>" value="<%= category.getCategory_id() %>"><%= category.getCategory_name() %></option>
							<%
								}
							}
						}else{
							%>		
										
							        <option>No category </option>
							      
							<%
							    }
							%>	
								
						</select>
					</p>
				</td>
			</tr>
			<tr>
				<td>Product Name:</td>
				<input type="hidden" name="product_id" value="<%= product.getProduct_id() %>" />
				<td><input type="text" name="product_name" value="<%= product.getProduct_name() %>" placeholder="Enter Product Name" class="search-input" /></td>
			</tr>
			<tr>
				<td>Product Price:</td>
				<td><input type="text" name="price" value="<%= product.getPrice() %>" placeholder="Enter Product Price" class="search-input" /></td>
			</tr>
			<tr>
				<td>Product Description:</td>
				<td><textarea  name="description" class="search-input" rows="10" style="height: 60px;"><%= product.getDescription() %> </textarea></td>
			</tr>
			<tr>
				<td>Product Images:</td>
				<td>
					
					<input type="file" name="photos" multiple="multiple" /><br/><br/>
					
					<%
					List<Product_Images> listProductImages = (List<Product_Images>)request.getAttribute("productImages");
					if (listProductImages != null && !listProductImages.isEmpty()) {
						for (int i = 0; i < listProductImages.size(); i++) {
							Product_Images productImages = listProductImages.get(i);
						%>	
						<img src="<%= productImages.getImage_url() %>" alt="Image" height="50px" class="thumbnail"> <span class="pull-right"><a href="delete-image?id=<%= productImages.getImage_id() %>" class="btn btn-del"> Remove Image</a></span> <br/><br/>
													
					<%
						}
					}
					%>	
			        
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					
					<input type="submit" value="Update Product" class="btn pull-right" /><br><br>
			        
				</td>
			</tr>
		</table>
		
		</form>		
	</section>
</body>
</html>