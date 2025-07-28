package com.todo.projekt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {
    private Connection connection;

    public TodoDAO() {
        String url = "jdbc:mysql://localhost:3306/tasks?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Verbindung zur Datenbank konnte nicht hergestellt werden.", e);
        }
    }

    public List<Todo> getAllTodos() {
        List<Todo> todos = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM todos");
            while (resultSet.next()) {
                Todo todo = mapResultSetToTodo(resultSet);
                todos.add(todo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Fehler beim Abrufen der Aufgaben.", e);
        }
        return todos;
    }

    public void addTask(Todo todo) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO todos (description, title) VALUES (?, ?)")) {
            preparedStatement.setString(1, todo.getDescription());
            preparedStatement.setString(2, todo.getTitle());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Fehler beim Hinzufügen der Aufgabe.", e);
        }
    }

    public void updateTask(Todo todo) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE todos SET description=?, title=? WHERE id=?")) {
            preparedStatement.setString(1, todo.getDescription());
            preparedStatement.setString(2, todo.getTitle());
            preparedStatement.setLong(3, todo.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Fehler beim Aktualisieren der Aufgabe.", e);
        }
    }

    public void deleteTask(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM todos WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Fehler beim Löschen der Aufgabe.", e);
        }
    }

    private Todo mapResultSetToTodo(ResultSet resultSet) throws SQLException {
        Todo todo = new Todo();
        todo.setId(resultSet.getLong("id"));
        todo.setDescription(resultSet.getString("description"));
        todo.setTitle(resultSet.getString("title"));
        return todo;
    }
}
