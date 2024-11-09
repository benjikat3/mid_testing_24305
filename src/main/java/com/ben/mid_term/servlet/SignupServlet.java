/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ben.mid_term.servlet;

import com.ben.mid_term.dao.LocationDao;
import com.ben.mid_term.dao.UserDao;
import com.ben.mid_term.model.AppUser;
import com.ben.mid_term.model.Gender;
import com.ben.mid_term.model.Role;
import com.ben.mid_term.model.UserLocation;
import com.ben.mid_term.util.PasswordHashing;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author benji
 */
public class SignupServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
         // Retrieve existing locations from the database
        LocationDao locationDao = new LocationDao();
        List<UserLocation> locations = locationDao.getProvinces();

        // Pass the locations to the signup page
        request.setAttribute("locations", locations);
        request.getRequestDispatcher("/signup.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        // Set request encoding for handling special characters
        request.setCharacterEncoding("UTF-8");

        // Retrieve form fields
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String hashedPass = PasswordHashing.hash(password);
        String gender = request.getParameter("gender");
        String phoneNumber = request.getParameter("phoneNumber");
        String role = request.getParameter("role");
        String locationId = request.getParameter("locationId");
        UUID LocId = UUID.fromString(locationId);

        // Validate if all required fields are present
        if (firstName == null || lastName == null || userName == null || password == null ||
                gender == null || phoneNumber == null || role == null || locationId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
            return;
        }
        try {
            // Create a user in the database (implement the logic in your DAO)
            AppUser user = new  AppUser();
            LocationDao locationDao = new LocationDao();
            UserLocation location = locationDao.findLocationById(LocId);
            
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setRole(Role.valueOf(role));
            user.setGender(Gender.valueOf(gender));
            user.setPassword(hashedPass);
            user.setUserName(userName);
            user.setPhoneNumber(phoneNumber);
            user.setVillage(location);
            
            UserDao userdao = new UserDao();
            
            if(userdao.insert(user)){
                // Redirect to success page or login
                RequestDispatcher dis = request.getRequestDispatcher("/login.jsp");
                dis.forward(request, response);                
            }


        } catch (Exception e) {
            // Handle exception (e.g., database error)
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while creating your account.");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
