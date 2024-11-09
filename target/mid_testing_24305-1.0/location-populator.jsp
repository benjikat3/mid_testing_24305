<%@page import="com.ben.mid_term.dao.LocationDao"%>
<%@ page import="com.ben.mid_term.model.UserLocation" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Location Populator - AUCA Library</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #1f1c2c, #928dab);
            color: #e0e0e0;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
        }
        .container {
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
            text-align: center;
        }
        .big-button {
            display: block;
            margin: 2rem auto;
            padding: 1rem 2rem;
            font-size: 1.5rem;
            color: white;
            background-color: teal;
            border: none;
            border-radius: 8px;
            box-shadow: 0 0 10px #00ffff;
            cursor: pointer;
        }
        .big-button:hover {
            background-color: #00cc99;
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

<div class="container">
    <h1 class="neon-text">Location Populator</h1>

    <form method="GET" action="LocationServlet" class="space-y-4">
        <!-- Province Selection -->
        <div class="flex flex-col space-y-2">
            <label for="province">Select Province</label>
            <select name="province" id="province" class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded focus:outline-none focus:border-teal-400" onchange="this.form.submit()">
                <option value="">Select Province</option>
                <%
                    LocationDao locationDao = new LocationDao();
                    List<UserLocation> provinces = locationDao.getAllLocations();
                    String selectedProvince = (String) request.getAttribute("selectedProvince");
                    if(provinces != null){
                        for (UserLocation province : provinces) {
                    %>
                        <option value="<%= province.getLocationId() %>" <%= selectedProvince != null && selectedProvince.equals(String.valueOf(province.getLocationId())) ? "selected" : "" %>>
                            <%= province.getLocationName() %>
                        </option>
                    <% }
                    }
                %>
            </select>
        </div>

        <!-- District Selection -->
        <%
            List<UserLocation> districts = (List<UserLocation>) request.getAttribute("districts");
            if (districts != null && !districts.isEmpty()) {
        %>
        <div class="flex flex-col space-y-2">
            <label for="district">Select District</label>
            <select name="district" id="district" class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded focus:outline-none focus:border-teal-400" onchange="this.form.submit()">
                <option value="">Select District</option>
                <%
                    String selectedDistrict = (String) request.getAttribute("selectedDistrict");
                    if (selectedDistrict != null){
                        for (UserLocation district : districts) {
                    %>
                        <option value="<%= district.getLocationId() %>" <%= selectedDistrict != null && selectedDistrict.equals(String.valueOf(district.getLocationId())) ? "selected" : "" %>>
                            <%= district.getLocationName() %>
                        </option>
                    <%}
                    }
                %>
            </select>
        </div>
        <%
            }
        %>

        <!-- Sector Selection -->
        <%
            List<UserLocation> sectors = (List<UserLocation>) request.getAttribute("sectors");
            if (sectors != null && !sectors.isEmpty()) {
        %>
        <div class="flex flex-col space-y-2">
            <label for="sector">Select Sector</label>
            <select name="sector" id="sector" class="w-full bg-gray-800 text-white border border-gray-500 p-3 rounded focus:outline-none focus:border-teal-400">
                <option value="">Select Sector</option>
                <%
                    for (UserLocation sector : sectors) {
                %>
                    <option value="<%= sector.getLocationId() %>"><%= sector.getLocationName() %></option>
                <%
                    }
                %>
            </select>
        </div>
        <%
            }
        %>
    </form>

    <!-- Populate Button -->
    <form method="POST" action="LocationServlet">
        <button type="submit" name="action" value="populate" class="big-button">Populate Locations</button>
    </form>

    <!-- Success Message -->
    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
    <p class="text-center mt-4 text-green-500"><%= message %></p>
    <%
        }
    %>
</div>

</body>
</html>
