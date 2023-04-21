<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%@ page import="java.util.List" %>
<%@ page import="com.productwebapp.model.Product" %>
<%@ page import="com.productwebapp.model.Product_Images" %>
<%@ page import="com.productwebapp.dao.ProductDAO" %>

<% ProductDAO productDAO = new ProductDAO(); 
int currentPage = 0;
int totalPages = 0;
try{
	//get the current page number
	currentPage = (Integer)request.getAttribute("currentPage");
	// get the total number of pages
	totalPages = (Integer)request.getAttribute("totalPages");
}catch(NullPointerException e){
	currentPage = 0;
	totalPages = 0;
}
%>

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
		
		
		
		<hr/>
		<br/>
		<h4>List of Available Products <span class="pull-right"><a href="new" class="btn">+ New Product</a> <a href="view-all" class="btn">View All Product</a></span></h4>
		
		<table class="table" cellspacing="0">
			<tr>
				<th>Id</th>
				<th>Category Id</th>
				<th>Product Image</th>
				<th>Product Name</th>
				<th>Product Price</th>
				<th>Action</th>
			</tr>		
			
			<%
			List<Product> listProduct = (List<Product>)request.getAttribute("listProduct");
			if (listProduct != null && !listProduct.isEmpty()) {
					int odd = 0;
				for (int i = 0; i < listProduct.size(); i++) {
				    Product product = listProduct.get(i);		
				    
				    if(odd++ ==1) {
				    	odd = 0;
				%>		
				    <tr class="odd">
				<%
				    }else{
				%>
					<tr>
				<%} %>
	                  <td>
	                      
	                      <%= product.getProduct_id() %>
	                  </td>
	                  <td>
	                     
	                      <%= product.getCategory_id() %>
	                  </td>
	                  <td>
	                 		<%
	                 			
	                 			Product_Images singleProductImage = productDAO.selectOneImageByProductId(product.getProduct_id()); 
	                 		%>
	                  		<img src="<%= singleProductImage.getImage_url() %>" alt="Image" height="50px">
	                  </td>
	                  <td>
	                      
	                      <%= product.getProduct_name() %>
	                  </td>
	                  
	                  <td>
	                     R <%= product.getPrice() %>
	                  </td>
	                  <td><a href="view?id=<%= product.getProduct_id() %>" class="btn" />View</a>
	                  	 <a href="edit?id=<%= product.getProduct_id() %>" class="btn btn-edit" />Edit</a> 
	                  	 <a href="delete?id=<%= product.getProduct_id() %>"  class="btn btn-del" >Delete</a></td>
	              </tr>
				<%
				}
			}else{
			%>		
						 <tr>
			            <td colspan="4">No products found.</td>
			        </tr>
			<%
			    }
			%>	
				
		      </table>
		      
		      
		      <p id="pagination">
			    <% if (currentPage > 1) { %>
			        <a href="view-all?page=<%= currentPage-1 %>" class="btn btn-home">&laquo; Previous Page</a>
			    <% }%>
			    
			    <% for (int i=1; i<=totalPages; i++) { %>
			        <% if (i == currentPage) { %>
			            <span class="btn btn-active btn-inactive">Page <%= i %></span>
			        <% } else { %>
			            <a href="view-all?page=<%= i %>" class="btn btn-home">Page <%= i %></a>
			        <% } %>
			    <% } %>
			    
			    <% if (currentPage < totalPages) { %>
			        <a href="view-all?page=<%= currentPage+1 %>" class="btn pull-right btn-home">Next Page &raquo;</a>
			    <% } %>
			</p>
	</section>
</body>
</html>