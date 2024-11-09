package com.ben.mid_term.servlet;

import com.ben.mid_term.dao.UserDao;
import com.ben.mid_term.model.AppUser;
import com.ben.mid_term.util.PasswordHashing;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author the-ceo
 */
public class LoginServlet extends HttpServlet {

    private final UserDao userDao = new UserDao();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            AppUser user = userDao.findByUsername(username);

            if (user != null && PasswordHashing.verify(password, user.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("role", user.getRole());

                // Redirect based on role or provide general dashboard
                response.sendRedirect("/home.jsp");
            } else {
                request.setAttribute("message", "Invalid username or password");
                request.setAttribute("messageType", "error");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Login error: " + e.getMessage());
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}