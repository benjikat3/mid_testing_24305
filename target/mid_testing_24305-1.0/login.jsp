<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - AUCA Online Library</title>
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
        .login-container {
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
            gap: 4px;
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
    
    <%
        String message = (String) request.getAttribute("message");
        String messageType = (String) request.getAttribute("messageType");
        if (message != null && messageType != null) { %>
       <script>
           Swal.fire({
               icon: '<%= messageType %>',
               title: '<%= messageType.equals("success") ? "Success!" : "Error!" %>',
               text: '<%= message %>'
           });
       </script>
    <% } %>

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

    <!-- Login Form Container -->
    <div class="login-container">
        <h1 class="neon-text text-center mb-6">Welcome Back</h1>

        <form id="loginForm" action="LoginServlet" method="POST" class="space-y-4">
            <!-- Username and Password -->
            <div class="flex flex-col space-y-2">
                <input type="text" name="username" placeholder="Username" required
                       class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded focus:outline-none focus:border-teal-400">
                <input type="password" name="password" placeholder="Password" required
                       class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded focus:outline-none focus:border-teal-400">
            </div>

            <!-- Role Selection (for informational purposes, can be hidden if not needed) -->
            <select name="role" required class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded focus:outline-none focus:border-teal-400">
                <option value="" disabled selected>Select Role</option>
                <option value="STUDENT">Student</option>
                <option value="MANAGER">Manager</option>
                <option value="TEACHER">Teacher</option>
                <option value="DEAN">Dean</option>
                <option value="HOD">Head of Department</option>
                <option value="LIBRARIAN">Librarian</option>
            </select>

            <!-- Submit Button -->
            <button type="submit" name="action" value="login"
                    class="w-full neon-text bg-teal-500 text-black font-semibold p-3 rounded hover:bg-teal-400 transition-colors">
                Log In
            </button>

            <p class="text-center mt-4">Don't have an account? <a href="/signup.jsp" class="text-teal-400 hover:underline">Sign Up here</a></p>
        </form>
    </div>
</body>
</html>
