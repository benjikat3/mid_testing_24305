<%@page import="com.ben.mid_term.dao.LocationDao"%>
<%@page import="com.ben.mid_term.model.UserLocation"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign Up - AUCA Online Library</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
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
        .signup-container {
            background: rgba(34, 34, 58, 0.95);
            padding: 2rem;
            border-radius: 1rem;
            box-shadow: 0px 0px 20px 5px rgba(0, 255, 255, 0.7);
            width: 90vw;
            max-width: 600px;
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
            <a href="/login.jsp">Login</a>
            <a href="/signup.jsp">Sign Up</a>
        </div>
    </div>

    <!-- Signup Form Container -->
    <div class="signup-container">
        <h1 class="neon-text text-center mb-6">Create Your Account</h1>
        <a href="LocationServlet">Can't see locations? populate them here</a>

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

            <!-- Submit Button -->
            <button type="submit" name="action" value="signup"
                    class="w-full bg-teal-500 text-black font-semibold p-3 rounded hover:bg-teal-400 transition-colors">
                Create Account
            </button>
            <p class="text-center mt-4">Already have an account? <a href="/login.jsp" class="text-teal-400 hover:underline">Login here</a></p>
        </form>
    </div>
</body>
</html>
