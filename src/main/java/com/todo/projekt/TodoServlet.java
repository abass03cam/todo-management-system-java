package com.todo.projekt;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@WebServlet("/todos")
public class TodoServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TodoDAO todoDAO;

    @Override
    public void init() throws ServletException {
        todoDAO = new TodoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Todo> todos = todoDAO.getAllTodos();
        request.setAttribute("todos", todos);
        request.getRequestDispatcher("todos.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String description = request.getParameter("description");
            String title = request.getParameter("title");
            
            Todo newTodo = new Todo();
            newTodo.setDescription(description);
            newTodo.setTitle(title);

            todoDAO.addTask(newTodo);
        } else if ("update".equals(action)) {
            long id = Long.parseLong(request.getParameter("id"));
            String description = request.getParameter("description");
            String title = request.getParameter("title");
            
            Todo updatedTodo = new Todo();
            updatedTodo.setId(id);
            updatedTodo.setDescription(description);
            updatedTodo.setTitle(title);

            todoDAO.updateTask(updatedTodo);
        } else if ("delete".equals(action)) {
            long id = Long.parseLong(request.getParameter("id"));
            
            todoDAO.deleteTask(id);
        }

        response.sendRedirect(request.getContextPath() + "/todos");
    }
}
