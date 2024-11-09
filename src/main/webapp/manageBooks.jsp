<%@page import="com.ben.mid_term.model.BookStatus"%>
<%@page import="java.util.UUID"%>
<%@page import="java.util.List"%>
<%@page import="com.ben.mid_term.dao.BookDao"%>
<%@page import="com.ben.mid_term.dao.ShelfDao"%>
<%@page import="com.ben.mid_term.model.Book"%>
<%@page import="com.ben.mid_term.model.Shelf"%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Initialize variables for form pre-fill during editing
    String bookId = request.getParameter("id");
    String action = request.getParameter("action");
    String title = "";
    String author = "";
    String isbn = "";
    String shelfId = "";
    int publishingYear = 0;
    BookStatus status = null;

    // Fetch all books from the database
    BookDao bookDao = new BookDao();
    List<Book> books = bookDao.showAll();

    // Fetch all shelves for book category selection
    ShelfDao shelfDao = new ShelfDao();
    List<Shelf> shelves = shelfDao.showAll();

    // If editing, fetch the book details by ID to pre-fill the form
    if ("edit".equals(action) && bookId != null) {
        Book bookToEdit = bookDao.findByBookId((UUID.fromString(bookId)));
        if (bookToEdit != null) {
            title = bookToEdit.getTitle();
            author = bookToEdit.getAuthor();
            isbn = bookToEdit.getIsbn();
            publishingYear = bookToEdit.getPublicationYear();
            shelfId = bookToEdit.getShelf() != null ? bookToEdit.getShelf().getShelfId().toString() : "";
            status = bookToEdit.getStatus();
        }
    }
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

    <!-- Navbar -->
    <div class="navbar">
        <div class="logo">
            <a href="/">AUCA Library</a>
        </div>
        <div class="links">
            <a href="/home.jsp">Home</a>
            <a href="/catalog.jsp">Catalog</a>
            <a href="/LogoutServlet">Logout</a>
        </div>
    </div>

    <!-- Manage Books Container -->
    <div class="manage-books-container">
        <h1 class="neon-text text-center mb-6">Manage Books</h1>
        <%
            if (shelves != null){
                %>
                <a href="manageBooks.jsp?room=none" >Populate room and shelves</a>
        <%
            }
        %>
        <!-- Add/Edit Book Form -->
        <form id="manageBookForm" action="BookServlet" method="POST" enctype="multipart/form-data" class="space-y-4 mb-8">
            <h2 class="text-lg font-semibold"><%= bookId != null ? "Edit Book" : "Add a New Book" %></h2>
            <input type="hidden" name="id" value="<%= bookId %>">
            
            <!-- Book Details -->
            <div class="flex flex-col space-y-2">
                <input type="text" name="title" placeholder="Book Title" value="<%= title %>" required
                       class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded focus:outline-none focus:border-teal-400">
                <input type="text" name="author" placeholder="Author" value="<%= author %>" required
                       class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded focus:outline-none focus:border-teal-400">
                <input type="text" name="isbn" placeholder="ISBN" value="<%= isbn %>" required
                       class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded focus:outline-none focus:border-teal-400">
            </div>
            <div class="flex flex-col md:flex-row md:space-x-4">
                <input type="number" name="publicationYear" id="publicationYear" placeholder="Publication Year" required
                       value="<%= (String.valueOf(publishingYear) != null) ? String.valueOf(publishingYear) : "" %>"
                       class="flex-1 p-2 border border-gray-300 rounded focus:border-blue-500 focus:ring-1 focus:ring-blue-500">
                <select name="status" id="status" required
                        value="<%= (status != null && status.name() != null) ? status.name() : "" %>"
                        class="flex-1 p-2 border border-gray-300 rounded focus:border-blue-500 focus:ring-1 focus:ring-blue-500">
                    <option value="" disabled selected>Select Status</option>
                    <option value="AVAILABLE">Available</option>
                    <option value="BORROWED">Borrowed</option>
                    <option value="RESERVED">Reserved</option>
                    <option value="ARCHIVED">Archived</option>
                </select>
                </div>

            <!-- Shelf Category -->
            <select name="shelfId" required class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded focus:outline-none focus:border-teal-400">
                <option value="" disabled selected>Select Shelf Category</option>
                <% if (shelves != null) {
                    for (Shelf shelf : shelves) { %>
                        <option value="<%= shelf.getShelfId().toString() %>" <%= shelf.getShelfId().toString().equals(shelfId) ? "selected" : "" %>>
                            <%= shelf.getBookCategory() %> (Room: <%= shelf.getRoom().getRoomCode() %>)
                        </option>
                <% } } %>
            </select>

            <!-- Book Cover Upload -->
            <div class="flex flex-col space-y-2">
                <label class="text-teal-400">Upload Book Cover</label>
                <input type="file" name="bookCover" accept="image/*" class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded">
            </div>

            <!-- Submit Button -->
            <button type="submit" name="action" value="<%= bookId != null ? "updateBook" : "addBook" %>"
                    class="w-full bg-teal-500 text-black font-semibold p-3 rounded hover:bg-teal-400 transition-colors">
                <%= bookId != null ? "Update Book" : "Add Book" %>
            </button>
        </form>

        <!-- Book List Table -->
        <h2 class="text-lg font-semibold">Existing Books</h2>
        <table>
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>ISBN</th>
                    <th>Shelf</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% if (books != null && !books.isEmpty()) {
                    for (Book book : books) { %>
                        <tr>
                            <td><%= book.getTitle() %></td>
                            <td><%= book.getAuthor() %></td>
                            <td><%= book.getIsbn() %></td>
                            <td><%= book.getShelf() != null ? book.getShelf().getBookCategory() : "Not Assigned" %></td>
                            <td>
                                <!-- Edit Book Link -->
                                <a href="manageBooks.jsp?id=<%= book.getBookId()%>&action=edit" class="text-teal-400 hover:underline">Edit</a> |
                                <!-- Delete Book Link -->
                                <a href="BookServlet?action=deleteBook&id=<%= book.getBookId()%>" class="text-red-500 hover:underline" onclick="return confirm('Are you sure you want to delete this book?');">Delete</a>
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
