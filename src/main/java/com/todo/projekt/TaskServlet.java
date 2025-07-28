package com.todo.projekt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@WebServlet("/TaskServlet")
public class TaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null)
            action = "list"; 

        switch (action) {
            case "list":
                listTasks(request, response);
                break;
            case "insert":
                insertTask(request, response);
                break;
            case "delete":
                deleteTask(request, response);
                break;
            default:
                listTasks(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); 
    }

    private void listTasks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Task> tasks = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mytodo?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin",
                    "root", "");
            PreparedStatement pst = con.prepareStatement("SELECT * FROM tasks");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setName(rs.getString("name"));

                tasks.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("tasks", tasks);
        request.getRequestDispatcher("task-list.jsp").forward(request, response);
    }

    private void insertTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String taskName = request.getParameter("taskName");
        int userId = getUserIdFromSession(request); 

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mytodo?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin",
                    "root", "");
            PreparedStatement pst = con.prepareStatement("INSERT INTO tasks (name, user_id) VALUES (?, ?)");
            pst.setString(1, taskName);
            pst.setInt(2, userId);

            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("TaskServlet");
    }



    private int getUserIdFromSession(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return 0;
	}

	private void deleteTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int taskId = Integer.parseInt(request.getParameter("id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mytodo?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin",
                    "root", "");
            PreparedStatement pst = con.prepareStatement("DELETE FROM tasks WHERE id=?");
            pst.setInt(1, taskId);

            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("TaskServlet");
    }
}
