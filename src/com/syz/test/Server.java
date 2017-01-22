package com.syz.test;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class Server extends HttpServlet implements Servlet {
	private int num;

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("-----------doGet------------");
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("-----------doPost------------");

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		System.out.println("" + isMultipart);

		if (isMultipart) {
			String realPath = this.getServletContext().getRealPath("upload");
			System.out.println(realPath);
			File dir = new File("C:/Sun/DATA");

			if (!dir.exists()) {
				dir.mkdir();
			}

			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);

			upload.setHeaderEncoding("utf-8");

			try {
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem item : items) {

					if (item.isFormField()) { // username="username"
						String name2 = item.getFieldName();
						String value = item.getString("utf-8");
						System.out.println(name2 + " = " + value);
					} else { // 文件
						String fileName = item.getName();
						item.write(new File(dir, fileName));
						System.out.println("文件名:" + item.getName());

						// System.out.println("大小:" + item.getSize());
						// System.out.println("表单名:" + item.getFieldName());
						// System.out.println("表单名:" + item.getFieldName());
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("////////////num//////////" + (++num));
	}

	public void init() throws ServletException {
		System.out.println("-----------init------------");
		num = 0;
	}

}
/*
 * System.out.println(parameter1); System.out.println(parameter2);
 * 
 * byte[] bytes = parameter2.getBytes();
 * 
 * // request.
 * 
 * File file = new File("E:/DATA", parameter1); // ServletInputStream
 * inputStream = request.getInputStream(); // BufferedInputStream bis = new
 * BufferedInputStream(inputStream); BufferedOutputStream bos = new
 * BufferedOutputStream( new FileOutputStream(file));
 * 
 * bos.write(bytes); // // byte[] buf = new byte[1024]; // int length; // while
 * ((length = bis.read(buf)) != -1) { // bos.write(buf); // } // bis.close(); //
 * inputStream.close(); bos.flush(); bos.close(); if (file.length() > 10) {
 * writer.write("ok"); } else { writer.write("failed"); }
 */
