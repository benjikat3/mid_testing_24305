<%@page import="com.ben.mid_term.model.Role"%>
<%@page import="com.ben.mid_term.model.AppUser"%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    Role role = (Role) session.getAttribute("role");
    AppUser user = (AppUser) session.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home - AUCA Online Library</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #1f1c2c, #928dab);
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            color: #e0e0e0;
        }
        .home-container {
            background: rgba(34, 34, 58, 0.95);
            padding: 2rem;
            border-radius: 1rem;
            box-shadow: 0px 0px 20px 5px rgba(0, 255, 255, 0.7);
            width: 90vw;
            max-width: 800px;
            animation: neon-glow 1.5s infinite alternate;
        }
        .neon-text {
            color: #00ffff;
            text-shadow: 0 0 8px #00ffff, 0 0 12px #00ffff, 0 0 16px #00ffff;
            font-size: 2rem;
        }
        .navbar {
            width: 100%;
            background-color: rgba(34, 34, 58, 0.95);
            padding: 1rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            position: absolute;
            top: 0;
        }
        .navbar a {
            color: #00ffff;
            text-decoration: none;
            font-size: 1.2rem;
            font-weight: 600;
        }
        .navbar a:hover {
            text-decoration: underline;
        }
        @keyframes neon-glow {
            from {
                box-shadow: 0px 0px 20px 5px rgba(0, 255, 255, 0.7);
            }
            to {
                box-shadow: 0px 0px 30px 10px rgba(0, 255, 255, 1);
            }
        }
    </style>
</head>
<body>

    <!-- Navbar -->
    <div class="navbar">
        <div class="logo">
            <a href="/">AUCA Library</a>
        </div>
        <div class="links">
            <a href="/home.jsp">Home</a>
            <a href="/catalog.jsp">Catalog</a>

            <!-- Show additional links based on user role -->
            <% if (role == Role.LIBRARIAN) { %>
                <a href="BookServlet">Manage Books</a>
                <a href="BookServlet">Manage Users</a>
            <% } else if (role == Role.STUDENT || role == Role.TEACHER) { %>
                <a href="/borrowBooks.jsp">Borrow Books</a>
                <a href="/myBorrowedBooks.jsp">My Borrowed Books</a>
            <% } else if (role == Role.HOD || role == Role.DEAN || role == Role.MANAGER) { %>
                <a href="/viewLibrary.jsp">View Library</a>
                <a href="/viewMembers.jsp">View Members</a>
            <% } %>

            <a href="/LogoutServlet">Logout</a>
        </div>
    </div>

    <!-- Home Content -->
    <div class="home-container">
        <h1 class="neon-text text-center mb-6">Welcome, <%= user.getFirstName() %>!</h1>

        <!-- Dynamic content based on role -->
        <% if (role == Role.LIBRARIAN) { %>
            <p class="text-lg text-center">You can manage the library's books, users, and handle member requests.</p>
            <div class="mt-6 text-center">
                <a href="/manageBooks.jsp" class="bg-teal-500 text-black font-semibold p-3 rounded hover:bg-teal-400 transition-colors">Manage Books</a>
                <a href="/manageUsers.jsp" class="bg-teal-500 text-black font-semibold p-3 rounded hover:bg-teal-400 transition-colors">Manage Users</a>
            </div>
           
        <% } else if (role == Role.STUDENT || role == Role.TEACHER) { %>
            <p class="text-lg text-center">You can borrow books and manage your borrowed items.</p>
            <div class="mt-6 text-center">
                <a href="/borrowBooks.jsp" class="bg-teal-500 text-black font-semibold p-3 rounded hover:bg-teal-400 transition-colors">Borrow Books</a>
                <a href="/myBorrowedBooks.jsp" class="bg-teal-500 text-black font-semibold p-3 rounded hover:bg-teal-400 transition-colors">My Borrowed Books</a>
            </div>
        <% } else if (role == Role.HOD || role == Role.DEAN || role == Role.MANAGER) { %>
            <p class="text-lg text-center">You can view library data and member information but cannot modify it.</p>
            <div class="mt-6 text-center">
                <a href="/viewLibrary.jsp" class="bg-teal-500 text-black font-semibold p-3 rounded hover:bg-teal-400 transition-colors">View Library</a>
                <a href="/viewMembers.jsp" class="bg-teal-500 text-black font-semibold p-3 rounded hover:bg-teal-400 transition-colors">View Members</a>
            </div>
        <% } %>
    </div>
</body>
</html>
