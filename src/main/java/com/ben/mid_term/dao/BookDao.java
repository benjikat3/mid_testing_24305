package com.ben.mid_term.dao;

import com.ben.mid_term.model.Book;
import com.ben.mid_term.model.BookStatus;
import com.ben.mid_term.util.HibernateUtil;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author the-ceo
 */
public class BookDao{

    private Session session;

    public boolean insert(Book pojo) {
        boolean res = false;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(pojo);
            tx.commit();
            res = true;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(BookDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
        
    }

 
    public boolean delete(Book pojo) {
        boolean res = false;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.remove(pojo);
            tx.commit();
            res = true;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(BookDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }

 
    public boolean update(Book pojo) {
        boolean res = false;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(pojo);
            tx.commit();
            res = true;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(BookDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }

 
    public List searchById(UUID id) {
        List<Book> list = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Book b WHERE b.bookId = :id", Book.class);
            query.setParameter("id", id);
            list = query.list();
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(BookDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return list;
    }
    
    public Book findByBookId(UUID id) {
        List<Book> list = null;
        Book b = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Book b WHERE b.bookId = :id", Book.class);
            query.setParameter("id", id);
            list = query.list();
            b = (list != null) ? list.get(0) : null;
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(BookDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return b;
    }
    
 
    public List showAll() {
        List<Book> list = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            list = session.createQuery("FROM Book", Book.class).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(BookDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return list;
    }
    
    // Get books by status
    public List<Book> getBooksByStatus(BookStatus status) {
        List<Book> list = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Book b WHERE b.status = :status", Book.class);
            query.setParameter("status", status);
            list = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(BookDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return list;
    }

    // Get books by shelf ID
    public List<Book> getBooksByShelfId(UUID shelfId) {
        List<Book> list = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Book b WHERE b.shelf.shelfId = :shelfId", Book.class);
            query.setParameter("shelfId", shelfId);
            list = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(BookDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return list;
    }

    public BookStatus checkBookStatusByISBN(String isbn) {
        BookStatus status = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("SELECT status FROM Book b WHERE b.isbn = :isbn", BookStatus.class);
            query.setParameter("isbn", isbn);
            status = (BookStatus) query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(BookDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return status;
    }

    // Update book status
    public boolean updateBookStatus(UUID bookId, BookStatus newStatus) {
        boolean res = false;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Book book = session.get(Book.class, bookId);
            if (book != null) {
                book.setStatus(newStatus);
                session.merge(book);
                tx.commit();
                res = true;
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(BookDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }
    
}
