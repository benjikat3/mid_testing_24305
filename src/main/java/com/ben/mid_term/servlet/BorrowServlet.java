/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ben.mid_term.servlet;

import com.ben.mid_term.dao.BookDao;
import com.ben.mid_term.dao.BorrowerDao;
import com.ben.mid_term.model.AppUser;
import com.ben.mid_term.model.Book;
import com.ben.mid_term.model.BookStatus;
import com.ben.mid_term.model.Borrower;
import com.ben.mid_term.model.Role;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author benji
 */
public class BorrowServlet extends HttpServlet {

    private final BookDao bookDao = new BookDao();
    private final BorrowerDao borrowerDao = new BorrowerDao();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        HttpSession session = request.getSession(false);
        AppUser user = (AppUser) session.getAttribute("user");
        
        String action = request.getParameter("action");
        Role role = (Role) session.getAttribute("role");
        
        if("borrowBook".equals(action)){
            String id = request.getParameter("id");
            UUID bookId = UUID.fromString(id);
            
            if (bookId != null){
                Book book = bookDao.findByBookId(bookId);
                Borrower borrower = new Borrower();
                
                book.setStatus(BookStatus.BORROWED);
                
                borrower.setBook(book);
                borrower.setBorrowDate(new Date(System.currentTimeMillis()));
                borrower.setFineAmount(0);
                borrower.setReturnDate(Date.valueOf(LocalDate.now().plusDays(7)));
                borrower.setShelf(book.getShelf());
                borrower.setUser(user);
                
                borrowerDao.insert(borrower);
                bookDao.update(book);
                
                BorrowerDao borrowerdao = new BorrowerDao();
                List<Borrower> borrowedBooks = borrowerdao.viewBorrowedBooks(user.getPersonId());

                request.setAttribute("borrowedBooks", borrowedBooks);

                RequestDispatcher dis = request.getRequestDispatcher("/my_borrowed_books.jsp");
                dis.forward(request, response);
            }
        }
        BookDao bdao = new BookDao();
        List<Book> books = bdao.showAll();
        request.setAttribute("books", books);

        RequestDispatcher dis = request.getRequestDispatcher("/borrowBooks.jsp");
        dis.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
