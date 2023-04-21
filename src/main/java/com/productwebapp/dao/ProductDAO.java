package com.productwebapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

import com.productwebapp.model.Product;
import com.productwebapp.model.Category;
import com.productwebapp.model.Product_Images;

public class ProductDAO {

	private String jdbcURL = "jdbc:mysql://localhost:3306/servletproductmgt_db?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";

    private static final String INSERT_PRODUCTS_SQL = "INSERT INTO products" + "  (category_id, product_name, description, price) VALUES " +
        " (?, ?, ?, ?)";
    private static final String INSERT_PRODUCT_IMAGE_SQL = "INSERT INTO product_images" + "  (product_id, image_url) VALUES " +
            " (?, ?);";

    private static final String SELECT_PRODUCT_BY_ID = "select id, category_id, product_name, description, price from products where id =?";
    private static final String SELECT_IMAGE_BY_ID = "select * from product_images where image_id =?";    
    private static final String SELECT_CATEGORY_BY_ID = "select id, category_name from categories where id =?";
    private static final String SELECT_IMAGES_BY_PRODUCT_ID = "select * from product_images where product_id =?";
    private static final String SELECT_ONE_IMAGE_BY_PRODUCT_ID = "select * from product_images where product_id =? LIMIT 1";
    
    
    
    private static final String SELECT_ALL_PRODUCTS = "select * from products";
    private static final String SELECT_ALL_PRODUCTS_WITH_LIMIT = "select * from products LIMIT ?, ?";
    private static final String SELECT_ALL_CATEGORIES = "select * from categories";
    private static final String GET_NO_OF_RECORDS = "select COUNT(*) from products";
    
    private static final String DELETE_PRODUCTS_SQL = "delete from products where id = ?";
    private static final String DELETE_IMAGE_SQL = "delete from product_images where image_id = ?";
    private static final String DELETE_IMAGE_BY_PRODUCTS_ID_SQL = "delete from product_images where product_id = ?";
    private static final String UPDATE_PRODUCTS_SQL = "update products set category_id = ?, product_name = ?,description = ?, price =? where id = ?";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }
    
    public int insertProductReturnId(Product product) throws SQLException {
        System.out.println(INSERT_PRODUCTS_SQL);
        int generatedId = -1;
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCTS_SQL,  Statement.RETURN_GENERATED_KEYS)) {
        	preparedStatement.setInt(1, product.getCategory_id());
            preparedStatement.setString(2, product.getProduct_name());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setDouble(4, product.getPrice());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }
            }
            
        } catch (SQLException e) {
            printSQLException(e);
        }
        return 0;
    }
    
    public int insertImage(Product_Images productImages) throws SQLException {
        System.out.println(INSERT_PRODUCT_IMAGE_SQL);
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_IMAGE_SQL)) {
        	preparedStatement.setInt(1, productImages.getProduct_id());
            preparedStatement.setString(2, productImages.getImage_url());
           
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            printSQLException(e);
        }
        return 0;
    }
    
    public Product_Images selectImage(int id) {
    	Product_Images image = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_IMAGE_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
            	int image_id = rs.getInt("image_id");
                int product_id = rs.getInt("product_id");
                String image_url = rs.getString("image_url");
               
                image = new Product_Images(image_id, product_id, image_url);                
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return image;
    }
    
    public Product selectProduct(int id) {
        Product product = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
            	int category_id = rs.getInt("category_id");
                String product_name = rs.getString("product_name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                product = new Product(id, category_id, product_name, description, price);
                
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return product;
    }
    
    
    public List < Product_Images > selectImageByProductId(int id) {
    	 // using try-with-resources to avoid closing resources (boiler plate code)
        List < Product_Images > productImages = new ArrayList < > ();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_IMAGES_BY_PRODUCT_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int image_id = rs.getInt("image_id");
                int product_id = rs.getInt("product_id");
                String image_url = rs.getString("image_url");

                productImages.add(new Product_Images(image_id, product_id, image_url));
                
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return productImages;
    }
    
    public Product_Images selectOneImageByProductId(int id) {
    	Product_Images singleProductImage = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ONE_IMAGE_BY_PRODUCT_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
            	int image_id = rs.getInt("image_id");
                int product_id = rs.getInt("product_id");
                String image_url = rs.getString("image_url");
               
                singleProductImage = new Product_Images(image_id, product_id, image_url);                
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return singleProductImage;
    }
    
    public Category selectCategory(int cat_id) {
        Category category = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORY_BY_ID);) {
            preparedStatement.setInt(1, cat_id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
            	int category_id = rs.getInt("id");
                String category_name = rs.getString("category_name");
               
                category = new Category(category_id, category_name);
                
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return category;
    }
    
    public List < Product > selectAllProducts() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List < Product > products = new ArrayList < > ();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int product_id = rs.getInt("id");
                int category_id = rs.getInt("category_id");
                String product_name = rs.getString("product_name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                products.add(new Product(product_id, category_id, product_name, description, price));
                
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;
    }
    
    public List < Product > selectAllProducts(int offset, int limit){
    	 List < Product > products = new ArrayList < > ();
    	 
    	// Step 1: Establishing a Connection
         try (Connection connection = getConnection();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS_WITH_LIMIT);) {
        	 preparedStatement.setInt(1, offset);
        	 preparedStatement.setInt(2, limit);
             System.out.println(preparedStatement);
             // Step 3: Execute the query or update query
             ResultSet rs = preparedStatement.executeQuery();

             // Step 4: Process the ResultSet object.
             while (rs.next()) {
                 int product_id = rs.getInt("id");
                 int category_id = rs.getInt("category_id");
                 String product_name = rs.getString("product_name");
                 String description = rs.getString("description");
                 double price = rs.getDouble("price");
                 products.add(new Product(product_id, category_id, product_name, description, price));
                 
             }
         } catch (SQLException e) {
             printSQLException(e);
         }
         return products;
    	 
    }
    
    public int getNoOfRecords() {
    	int noOfRecords = 0;
    	
    	// Step 1: Establishing a Connection
        try (Connection connection = getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(GET_NO_OF_RECORDS);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            if (rs.next()) {
            	noOfRecords = rs.getInt(1);                
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return noOfRecords;
    }
    
    public List < Category > selectAllCategories() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List < Category > categories = new ArrayList < > ();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CATEGORIES);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int category_id = rs.getInt("id");
                String category_name = rs.getString("category_name");
                categories.add(new Category(category_id, category_name));
                
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return categories;
    }
    
    public boolean deleteProduct(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCTS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
    
    public boolean deleteImageByProductId(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_IMAGE_BY_PRODUCTS_ID_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
    
    public boolean deleteImage(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_IMAGE_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateProduct(Product product) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCTS_SQL);) {
            
            statement.setInt(1, product.getCategory_id());
            statement.setString(2, product.getProduct_name());
            statement.setString(3, product.getDescription());
            statement.setDouble(4, product.getPrice());
            statement.setInt(5, product.getProduct_id());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
    
    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
