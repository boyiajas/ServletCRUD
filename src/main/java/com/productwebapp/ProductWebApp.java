package com.productwebapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebInitParam;
//import javax.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.productwebapp.dao.ProductDAO;
import com.productwebapp.model.Category;
import com.productwebapp.model.Product;
import com.productwebapp.model.Product_Images;
import com.productwebapp.helpers.UploadFileHelper;

/**
 * Servlet implementation class ProductWebApp
 */

@MultipartConfig(
	fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
	maxFileSize = 1024 * 1024 * 50, // 50 MB
	maxRequestSize = 1024 * 1024 * 100 // 100 MB
)

public class ProductWebApp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ProductDAO productDAO;
	
	private static final String UPLOAD_DIR = "productImg";
	private static final int RECORDS_PER_PAGE = 5;
	
	public void init() {
		productDAO = new ProductDAO();
		
		/*
		 * List < Product > listProduct = productDAO.selectAllProducts(); // Set data as
		 * an attribute in the ServletContext object ServletContext context =
		 * getServletContext(); context.setAttribute("listProduct", listProduct);
		 */
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//int id  = Integer.parseInt(request.getParameter("id")); 
		String action = request.getServletPath();
		//List < Product > listProduct = productDAO.selectAllProducts();
	     // Set data as an attribute in the ServletContext object
      //ServletContext context = getServletContext();
      //context.setAttribute("listProduct", listProduct);
		//int productId = Integer.parseInt(request.getParameter("id")); 
		//response.setContentType("text/html");
		//PrintWriter out = response.getWriter();
		//out.print("Product Id is "+productId);
		//out.print("Product Path is "+action);
		
		
	  try { 
		  switch (action) { 
		  	case "/new": 
		  		newProduct(request, response); 
		  		break;
		  	case "/insert": 
		  		insertProduct(request, response); 
		  		break; 
		  	case "/delete":
		  		deleteProduct(request, response); 
		  		break; 
		  	case "/delete-image":
		  		deleteImage(request, response);
		  		break;
		  	case "/edit": 
		  		showEditForm(request, response); 
		  		break; 
		  	case "/update": 
		  		updateProduct(request, response); 
		  		break;
		  	case "/view": 
		  		viewProduct(request, response); 
		  		break;
		  	case "/view-all": 
		  		listProduct(request, response);
	  			break;
		  	case "/create":
		  		createProduct(request, response);
		  		break;
		  	default:
		  		
				//out.print("Product Path is "+action);
		  		break; 
			  } 
		  } catch (SQLException ex) { 
			  throw new ServletException(ex); 
		  }
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//int id  = Integer.parseInt(request.getParameter("id")); 
		  //System.out.println(id);
		doGet(request, response);
	}
	
	private void createProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		List<String> photos = UploadFileHelper.uploadFile(UPLOAD_DIR, request);
		//System.out.println(request.getParts());
		int category_id = Integer.parseInt(request.getParameter("cat_id"));
		String product_name = request.getParameter("product_name");
		String description = request.getParameter("product_description");
		double price = Double.parseDouble(request.getParameter("product_price"));

		Product newProduct = new Product(category_id, product_name, description, price);
		int newProductId = productDAO.insertProductReturnId(newProduct);
		//PrintWriter out = response.getWriter();
		//out.print("Product Id is "+productId);
		//out.print("Product Path is "+newProductId);


		for (String photo : photos) {

			Product_Images productImages = new Product_Images(newProductId, "productImg/" + photo);
			productDAO.insertImage(productImages);
		}

		//RequestDispatcher dispatcher = request.getRequestDispatcher("product-list.jsp");
		//dispatcher.forward(request, response);
		
		response.sendRedirect("view-all");

	}
	
	private void listProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
        List < Product > listProduct = productDAO.selectAllProducts((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        int noOfRecords = productDAO.getNoOfRecords();
        
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        
        request.setAttribute("totalPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("listProduct", listProduct);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("product-list.jsp");
        dispatcher.forward(request, response);
    }
	
	private void newProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List < Category > listCategories = productDAO.selectAllCategories();
        request.setAttribute("listCategories", listCategories);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("product-new.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("product-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product existingProduct = productDAO.selectProduct(id);
        List < Product_Images > productImages = productDAO.selectImageByProductId(id);
        List < Category > listCategories = productDAO.selectAllCategories();
        
        request.setAttribute("product", existingProduct);
        request.setAttribute("productImages", productImages);
        request.setAttribute("listCategories", listCategories);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("product-edit.jsp");
        dispatcher.forward(request, response);

    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        
        int category_id = Integer.parseInt(request.getParameter("category_id"));
        String product_name = request.getParameter("product_name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        
        Product newProduct = new Product(category_id, product_name, description, price);
        productDAO.insertProductReturnId(newProduct);
        response.sendRedirect("list");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
    	List<String> photos = UploadFileHelper.uploadFile(UPLOAD_DIR, request);
    	
        int product_id = Integer.parseInt(request.getParameter("product_id"));
		
        int category_id = Integer.parseInt(request.getParameter("category_id"));
        String product_name = request.getParameter("product_name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        
        Product product = new Product(product_id, category_id, product_name, description, price);
        productDAO.updateProduct(product);
        
        for (String photo : photos) {

			Product_Images productImages = new Product_Images(product_id, "productImg/" + photo);
			productDAO.insertImage(productImages);
		}
        
        response.sendRedirect("view-all");
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productDAO.deleteProduct(id);
        
        List < Product_Images > productImages = productDAO.selectImageByProductId(id);
        if (productImages != null && !productImages.isEmpty()) {
			for (int i = 0; i < productImages.size(); i++) {
				Product_Images productImage = productImages.get(i);
				
				 //here we need to delete image from path
				UploadFileHelper.deleteFile(UPLOAD_DIR, productImage.getImage_url(), request);
				
			}
		}
       
        productDAO.deleteImageByProductId(id);        
        response.sendRedirect("view-all");

    }
    
    private void deleteImage(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
    	int id = Integer.parseInt(request.getParameter("id"));
    	Product_Images image = productDAO.selectImage(id);
    	
    	//here we need to delete image from path
		UploadFileHelper.deleteFile(UPLOAD_DIR, image.getImage_url(), request);
    	productDAO.deleteImage(id);
    	
    	response.sendRedirect("view-all");
    }
    
    private void viewProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
    	int id = Integer.parseInt(request.getParameter("id"));
    	Product product = productDAO.selectProduct(id);
    	List < Product_Images > productImages = productDAO.selectImageByProductId(id);
    	Category category = productDAO.selectCategory(product.getCategory_id());
    	Product_Images singleProductImage = productDAO.selectOneImageByProductId(id);
    	
    	
    	
        request.setAttribute("product", product);
        request.setAttribute("category", category);
        request.setAttribute("productImages", productImages);
        request.setAttribute("singleProductImage", singleProductImage);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("product-details.jsp");
        dispatcher.forward(request, response);
    }

}
