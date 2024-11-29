<%@page import="com.ben.mid_term.model.Borrower"%>
<%@page import="com.ben.mid_term.dao.BorrowerDao"%>
<%@page import="com.ben.mid_term.model.AppUser"%>
<%@page import="com.ben.mid_term.model.Role"%>
<%@page import="com.ben.mid_term.model.BookStatus"%>
<%@page import="java.util.UUID"%>
<%@page import="java.util.List"%>
<%@page import="com.ben.mid_term.dao.BookDao"%>
<%@page import="com.ben.mid_term.dao.ShelfDao"%>
<%@page import="com.ben.mid_term.model.Book"%>
<%@page import="com.ben.mid_term.model.Shelf"%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Role role = (Role) session.getAttribute("role");
    AppUser user = (AppUser) session.getAttribute("user");
    
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Books - AUCA Online Library</title>
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
        .manage-books-container {
            background: rgba(34, 34, 58, 0.95);
            padding: 2rem;
            border-radius: 1rem;
            box-shadow: 0px 0px 20px 5px rgba(0, 255, 255, 0.7);
            width: 90vw;
            max-width: 900px;
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
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1.5rem;
        }
        table th, table td {
            border: 1px solid #00ffff;
            padding: 0.75rem;
            text-align: left;
        }
        table th {
            background-color: rgba(0, 255, 255, 0.3);
        }
        table td {
            background-color: rgba(34, 34, 58, 0.8);
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
                <a href="ViewMemberServlet">Manage Users</a>
            <% } else if (role == Role.STUDENT || role == Role.TEACHER) { %>
                <a href="BorrowServlet">Borrow Books</a>
                <a href="MyBorrowedBookServlet">My Borrowed Books</a>
            <% } else if (role == Role.HOD || role == Role.DEAN || role == Role.MANAGER) { %>
                <a href="ViewLibraryServlet">View Library</a>
                <a href="ViewMemberServlet">View Members</a>
            <% } %>

            <a href="/LogoutServlet">Logout</a>
        </div>
    </div>

    <!-- Manage Books Container -->
    <div class="manage-books-container">
        <h1 class="neon-text text-center mb-6">My Borrowed Books</h1>
        
        <!-- Book List Table -->
        <h2 class="text-lg font-semibold">my borrowed Books</h2>
        <table class="text-center">
            <thead>
                <tr class="text-center">
                    <th>Title</th>
                    <th>Author</th>
                    <th>ISBN</th>
                    <th>Shelf</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Borrower> borrowedBooks = (List<Borrower>) request.getAttribute("borrowedBooks");
                    if (borrowedBooks != null && !borrowedBooks.isEmpty()) {
                        for (Borrower book : borrowedBooks) { %>
                        <tr class="text-center">
                            <td><%= book.getBook().getTitle() %></td>
                            <td><%= book.getBook().getAuthor() %></td>
                            <td><%= book.getBook().getIsbn() %></td>
                            <td><%= book.getShelf() != null ? book.getBook().getBookCategory() : "Not Assigned" %></td>
                            <td>
                                <!-- Delete Book Link -->
                                <a href="BorrowServlet?action=returnBook&id=<%= book.getBook().getBookId()%>" class="text-blue-500 hover:underline" onclick="return confirm('You are returning \''+<%= book.getBook().getTitle() %>+'\'press YES to continue');">Return</a>
                            </td>
                        </tr>
                <% } } else { %>
                    <tr>
                        <td colspan="5" class="text-center text-teal-400">No books found</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>

</body>
</html>
