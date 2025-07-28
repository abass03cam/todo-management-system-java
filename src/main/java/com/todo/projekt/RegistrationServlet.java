package com.todo.projekt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
//@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 
    
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("pass");
		RequestDispatcher dispatcher = null;
		
//		 con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/mytodo?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin","root","");  
			PreparedStatement pst = con.prepareStatement("insert into users(uname,upwd,uemail) values(?,?,?) ");
			pst.setString(1, uname);
			pst.setString(2, upwd);
			pst.setString(3, uemail);

			
			
			int rowCount = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("login.jsp");
			
			if (rowCount > 0) {
				request.setAttribute("status", "success");
			}else {
				request.setAttribute("status", "failed");
			} dispatcher.forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
