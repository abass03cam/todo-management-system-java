<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.todo.projekt.Todo" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Aufgabenliste</title>
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            width: 80%;
            max-width: 800px;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        form {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-wrap: wrap;
            margin-bottom: 20px;
        }

        label {
            font-weight: bold;
            margin-right: 10px;
        }

        input[type="text"] {
            flex: 1;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-bottom: 2px;
        }

        #title {
            width: 40%;
        }

        #description {
            width: 70%;
        }

        .action-button {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s ease;
            margin-left: 10px;
        }

        .action-button:hover {
            background-color: #0056b3;
        }

        .task-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        .task-table th,
        .task-table td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        .task-table td:last-child {
            text-align: center;
        }

        .popup {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
            z-index: 1000;
        }

        .overlay {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 999;
        }

        .logout-button {
            background-color: #dc3545;
            color: #fff;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s ease;
            margin-left: 10px;
        }

        .logout-button:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Aufgabenliste von: <%= session.getAttribute("name") %></h1>
    <form action="logout" method="post" class="logout-form">
        <input type="hidden" name="action" value="logout">
        <input type="submit" value="Abmelden" class="logout-button">
    </form>
    <form action="todos" method="post" class="add-form">
        <input type="hidden" name="action" value="add">
        <label for="title"></label> <input type="text" id="title" name="title" placeholder="Titel" style="width: 40px;" required>
        <label for="description"></label> <input type="text" id="description" name="description" placeholder="Beschreibung" style="width: 70px;" required>
        <input type="submit" value="Hinzufügen" class="action-button">
    </form>

    <table class="task-table">
        <% List<Todo> todos = (List<Todo>) request.getAttribute("todos"); %>
        <% if (todos != null) { %>
            <% for (Todo todo : todos) { %>
                <tr>
                    <td><%= todo.getId() %></td>
                    <td><%= todo.getTitle() %></td>
                    <td><%= todo.getDescription() %></td>
                    <td>
                        <button class="action-button" onclick="openPopup('<%= todo.getId() %>', '<%= todo.getDescription() %>', '<%= todo.getTitle() %>')">
                            <ion-icon name="create-outline"></ion-icon>
                        </button>
                    </td>
                    <td>
                        <form action="todos" method="post" class="inline-form">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="<%= todo.getId() %>">
                            <button type="submit" class="action-button">
                                <ion-icon name="trash-outline"></ion-icon>
                            </button>
                        </form>
                    </td>
                </tr>
            <% } %>
        <% } %>
    </table>

    <div id="editPopup" class="popup">
        <h2>Aufgabe bearbeiten</h2>
        <form id="editForm" action="todos" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" id="editId" name="id">
            <label for="editTitle">Titel</label> <input type="text" id="editTitle" name="title"><br>
            <label for="editDescription">Beschreibung</label> <input type="text" id="editDescription" name="description"><br>
            <input type="submit" value="Speichern" class="action-button">
            <button onclick="closePopup()">Abbrechen</button>
        </form>
    </div>

    <div id="overlay" class="overlay"></div>

    <script>
        function openPopup(id, description, title) {
            document.getElementById("editId").value = id;
            document.getElementById("editTitle").value = title;
            document.getElementById("editDescription").value = description;
            document.getElementById("editPopup").style.display = "block";
            document.getElementById("overlay").style.display = "block";
        }

        function closePopup() {
            document.getElementById("editPopup").style.display = "none";
            document.getElementById("overlay").style.display = "none";
        }
    </script>
</div>
</body>
</html>
