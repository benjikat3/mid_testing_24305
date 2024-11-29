<%@page import="com.ben.mid_term.model.UserLocation"%>
<%@page import="com.ben.mid_term.dao.LocationDao"%>
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
    // Fetch all books from the database
    List<AppUser> users = (List<AppUser>) request.getAttribute("users");
    // Fetch all shelves for book category selection
    ShelfDao shelfDao = new ShelfDao();
    List<Shelf> shelves = shelfDao.showAll();
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
            margin: 0; /* Ensure no extra margins interfere with scroll */
            display: flex;
            flex-direction: column;
            color: #e0e0e0;
            overflow-y: auto; /* Allow vertical scrolling */
        }
        .manage-books-container {
            background: rgba(34, 34, 58, 0.95);
            padding: 2rem;
            border-radius: 1rem;
            box-shadow: 0px 0px 20px 5px rgba(0, 255, 255, 0.7);
            width: 100vw;
            max-width: 1000px;
            margin: 2rem auto; /* Ensure proper spacing */
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
        .navbar {
            width: 100%;
            background-color: rgba(34, 34, 58, 0.95);
            padding: 1rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            position: sticky; /* Keep navbar visible during scrolling */
            top: 0;
            z-index: 1000; /* Ensure it stays on top of other content */
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
            <%
                Role role = (Role) session.getAttribute("role");
            %>
            <% if (role == Role.LIBRARIAN) { %>
                <a href="BookServlet">Manage Books</a>
                <a href="ViewMemberServlet">Manage Users</a>
            <% } else if (role == Role.STUDENT || role == Role.TEACHER) { %>
                <a href="borrowServlet">Borrow Books</a>
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
        <h1 class="neon-text text-center mb-6">Manage Users</h1>
        <!--<a href="LocationServlet">Can't see locations? populate them here</a>-->

        <form id="signupForm" action="SignupServlet" method="POST" class="space-y-4">
            <!-- Name Fields -->
            <div class="flex flex-col space-y-2">
                <input type="text" name="firstName" placeholder="First Name" required
                       class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded focus:outline-none focus:border-teal-400">
                <input type="text" name="lastName" placeholder="Last Name" required
                       class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded focus:outline-none focus:border-teal-400">
            </div>

            <!-- Username and Password -->
            <div class="flex flex-col space-y-2">
                <input type="text" name="userName" placeholder="Username" required
                       class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded focus:outline-none focus:border-teal-400">
                <input type="password" name="password" placeholder="Password" required
                       class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded focus:outline-none focus:border-teal-400">
            </div>

            <!-- Gender Selection -->
            <select name="gender" required class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded focus:outline-none focus:border-teal-400">
                <option value="" disabled selected>Select Gender</option>
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
            </select>

            <!-- Phone and Role Selection -->
            <div class="flex flex-col space-y-2">
                <input type="tel" name="phoneNumber" placeholder="Phone Number" required
                       class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded focus:outline-none focus:border-teal-400">
                <select name="role" required class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded focus:outline-none focus:border-teal-400">
                    <option value="" disabled selected>Select Role</option>
                    <option value="STUDENT">Student</option>
                    <option value="MANAGER">Manager</option>
                    <option value="TEACHER">Teacher</option>
                    <option value="DEAN">Dean</option>
                    <option value="HOD">Head of Department</option>
                    <option value="LIBRARIAN">Librarian</option>
                </select>
            </div>

            <!-- Location Selection -->
            <select name="locationId" required class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded focus:outline-none focus:border-teal-400">
                <option value="" disabled selected>Select Location</option>

                <!-- Fetch locations from the database using Java code -->
                <%
                    LocationDao locationDao = new LocationDao();
                    List<UserLocation> locations = locationDao.getProvinces();
                    if (locations != null){
                        for (UserLocation location : locations) {
                    %>
                        <option value="<%= location.getLocationId()%>"><%= location.getLocationName()%></option>
                    <% }
                    }
                %>
            </select>

        <!-- Book List Table -->
        <h2 class="text-lg font-semibold">Existing Books</h2>
        <table>
            <thead>
                <tr>
                    <th>Names</th>
                    <th>Phone</th>
                    <th>UserName</th>
                    <th>Village</th>
                    <th>Role</th>
                    <th>Membership</th>
                </tr>
            </thead>
            <tbody>
                <% if (users != null && !users.isEmpty()) {
                    for (AppUser user : users) { %>
                        <tr>
                            <td><%= user.getFirstName() +" "+ user.getLastName()%></td>
                            <td><%=  user.getPhoneNumber() %></td>
                            <td><%= user.getUserName() %></td>
                            <td><%= user.getVillage() %></td>
                            <td><%= user.getRole() %></td>
                             <td><%= (user.getMembership() != null) ? user.getMembership().getMembershipType().getMembershipName() : "N/A" %></td>
                        </tr>
                <% } } else { %>
                    <tr>
                        <td colspan="6" class="text-center text-teal-400">No user found</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>

</body>
</html>