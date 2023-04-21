package com.productwebapp.helpers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

public class UploadFileHelper {
	public static List<String> uploadFile(String UPLOAD_DIR, HttpServletRequest request) {
		List<String> fileNames = new ArrayList<String>();
		try {
			List<Part> parts = (List<Part>) request.getParts();
			for (Part part : parts) {
				if (part.getName().equalsIgnoreCase("photos")) {
					String fileName = getFileName(part);
					fileNames.add(fileName);
					//String applicationPath = System.getProperty("user.dir");
					String appPath = request.getServletContext().getRealPath("");
					
					String basePath = "/home/lordwiz/eclipse-workspace/ProductWebApp/src/main/webapp";
					
					//String basePath = appPath.replaceAll("(/|.metadata/.*|/.*/WEB-INF.*)", "") + File.separator + UPLOAD_DIR;
					//String basePath = appPath.replaceAll("(/wtpweb.*)|(/org.*)|(/.plugin)|(/tmp.*)|(.metadata/)|(WEB-INF/)", "");
					//String basePath = "." + File.separator + "WebContent" + File.separator + UPLOAD_DIR;
					
					//String applicationPath = request.getServletContext().getRealPath("");
					//String basePath = applicationPath + File.separator+ UPLOAD_DIR;
					
					//System.out.println(basePath + File.separator + UPLOAD_DIR);
					
					InputStream inputStream = null;
					OutputStream outputStream = null;
					try {
						//File outputFilePath = new File(basePath + fileName);
						File outputFilePath = new File(basePath + File.separator + UPLOAD_DIR);
						
						if(!outputFilePath.exists()) {
							outputFilePath.mkdir();
						}
						
						inputStream = part.getInputStream();
						outputStream = new FileOutputStream(outputFilePath + File.separator + fileName);
						int read = 0;
						final byte[] bytes = new byte[1024];
						while ((read = inputStream.read(bytes)) != -1) {
							outputStream.write(bytes, 0, read);
						}
					} catch (Exception ex) {
						fileName = null;
					} finally {
						if (outputStream != null) {
							outputStream.close();
						}
						if (inputStream != null) {
							inputStream.close();
						}
					}
				}
			}
		} catch (Exception e) {
			fileNames = null;
			return fileNames; 
		}
		return fileNames;
	}
	
	public static boolean deleteFile(String UPLOAD_DIR, String fileName, HttpServletRequest request) {
        boolean deleted = false;
        //String appPath = request.getServletContext().getRealPath("");
        //String basePath = appPath + File.separator + UPLOAD_DIR;
        String basePath = "/home/lordwiz/eclipse-workspace/ProductWebApp/src/main/webapp";
        try {
            File file = new File(basePath + File.separator + fileName);
            if (file.exists()) {
                file.delete();
                deleted = true;
            }
        } catch (Exception ex) {
            deleted = false;
        }
        return deleted;
    }

	private static String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}
