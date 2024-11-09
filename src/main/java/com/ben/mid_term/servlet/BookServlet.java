package com.ben.mid_term.servlet;

import com.ben.mid_term.dao.BookDao;
import com.ben.mid_term.dao.ShelfDao;
import com.ben.mid_term.model.Book;
import com.ben.mid_term.model.Shelf;
import com.ben.mid_term.model.AppUser;
import com.ben.mid_term.model.BookStatus;
import com.ben.mid_term.model.Role;
import com.ben.mid_term.util.RoomShelfPopulator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/BookServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class BookServlet extends HttpServlet {
    private BookDao bookDao = new BookDao();
    private ShelfDao shelfDao = new ShelfDao();

    // Directory to save uploaded files
    private static final String UPLOAD_DIR = "book_covers";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch the logged-in AppUser and check the role
        AppUser currentUser = (AppUser) request.getSession().getAttribute("user");
        Role role = currentUser.getRole();
        String room = request.getParameter("room");
        if (room.equals("none")){
            RoomShelfPopulator p = new RoomShelfPopulator();
            p.populateRoomShelfData();
            // Forward to the manageBooks.jsp page
            request.getRequestDispatcher("/lib/manageBooks.jsp").forward(request, response);

        }

        // Fetch all books to display
        List<Book> books = bookDao.showAll();
        request.setAttribute("books", books);

        // Fetch all shelves for display in the dropdown
        List<Shelf> shelves = shelfDao.showAll();
        request.setAttribute("shelves", shelves);

        // Check role and restrict access if not a librarian
        if (role != Role.LIBRARIAN) {
            request.setAttribute("restricted", true);
            
        }

        // Forward to the manageBooks.jsp page
        request.getRequestDispatcher("/lib/manageBooks.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // Handle adding a new book
        if ("addBook".equals(action)) {
            addBook(request, response);
        }
        // Handle editing an existing book
        else if ("updateBook".equals(action)) {
            editBook(request, response);
        }
        // Handle deleting a book
        else if ("deleteBook".equals(action)) {
            deleteBook(request, response);
        }

        // Redirect back to the GET method to refresh the page
        response.sendRedirect("BookServlet");
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String isbn = request.getParameter("isbn");
        String status = request.getParameter("status");
        
        BookStatus stat = BookStatus.valueOf(status);
        UUID shelfId = UUID.fromString(request.getParameter("shelfId"));

        // Handle file upload
        String fileName = uploadBookCover(request);

        Shelf shelf = shelfDao.findShelfById(shelfId);
        Book newBook = new Book();
        newBook.setAuthor(author);
        newBook.setBookCategory(shelf.getBookCategory());
        newBook.setIsbn(isbn);
        newBook.setPublicationYear(2024);
        newBook.setShelf(shelf);
        newBook.setStatus(stat);
        
        bookDao.insert(newBook);

        request.getSession().setAttribute("message", "Book added successfully!");
    }

    private void editBook(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UUID bookId = UUID.fromString(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String isbn = request.getParameter("isbn");
        UUID shelfId = UUID.fromString(request.getParameter("shelfId"));
        String status = request.getParameter("status");
        
        BookStatus stat = BookStatus.valueOf(status);

        // Handle file upload (optional)
        String fileName = uploadBookCover(request);

        Shelf shelf = shelfDao.findShelfById(shelfId);
        Book updatedBook = new Book();
        updatedBook.setAuthor(author);
        updatedBook.setBookCategory(shelf.getBookCategory());
        updatedBook.setIsbn(isbn);
        updatedBook.setPublicationYear(2024);
        updatedBook.setShelf(shelf);
        updatedBook.setStatus(stat);
        bookDao.update(updatedBook);

        request.getSession().setAttribute("message", "Book updated successfully!");
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UUID bookId = UUID.fromString(request.getParameter("id"));
        Book b = bookDao.findByBookId(bookId);
        bookDao.delete(b);

        request.getSession().setAttribute("message", "Book deleted successfully!");
    }

    private String uploadBookCover(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("bookCover");
        String fileName = null;

        // Ensure a file is uploaded
        if (filePart != null && filePart.getSize() > 0) {
            // Get the file name and save it
            fileName = UUID.randomUUID() + "_" + extractFileName(filePart);
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;

            // Create the upload directory if it doesn't exist
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            // Save the file
            filePart.write(uploadPath + File.separator + fileName);
        }

        return fileName;
    }

    private String extractFileName(Part part) {
        // Extracts file name from HTTP header content-disposition
        String contentDisposition = part.getHeader("content-disposition");
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
}
