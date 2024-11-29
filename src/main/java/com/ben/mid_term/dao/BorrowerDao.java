package com.ben.mid_term.dao;

import com.ben.mid_term.model.Book;
import com.ben.mid_term.model.BookStatus;
import com.ben.mid_term.model.Borrower;
import com.ben.mid_term.util.HibernateUtil;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class BorrowerDao{

    private Session session;

    public boolean insert(Borrower pojo) {
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
            Logger.getLogger(BorrowerDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }

    public boolean delete(Borrower pojo) {
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
            Logger.getLogger(BorrowerDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }

    public boolean update(Borrower pojo) {
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
            Logger.getLogger(BorrowerDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }

    public List<Borrower> searchById(UUID id) {
        List<Borrower> list = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Borrower b WHERE b.borrowerId = :id", Borrower.class);
            query.setParameter("id", id);
            list = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(BorrowerDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return list;
    }
    
    public Borrower getBorrowerById(UUID bookId) {
        List<Borrower> list = null;
        Borrower b = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Borrower b WHERE b.book.bookId = :id", Borrower.class);
            query.setParameter("id", bookId);
            list = query.list();
            b = (list != null) ? list.get(0) : null;
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(BorrowerDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return b;
    }
    

    public List showAll() {
        List<Borrower> list = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            list = session.createQuery("FROM Borrower", Borrower.class).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(BorrowerDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return list;
    }
    
    public boolean borrowBook(Borrower borrower, Book book) {
        boolean res = false;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            borrower.setBook(book);  // Set the book in the borrower record
            borrower.setBorrowDate(java.sql.Date.valueOf(LocalDate.now()));  // Set current date as borrow date
            book.setStatus(BookStatus.BORROWED);  // Update book status to BORROWED
            session.merge(borrower);  // Save borrower with the updated book
            
            session.merge(book);  // Update book status in the database
            tx.commit();
            res = true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            Logger.getLogger(BorrowerDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }

    public boolean returnBook(Borrower borrower) {
        boolean res = false;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            borrower.setReturnDate(java.sql.Date.valueOf(LocalDate.now()));
            Book book = borrower.getBook();
            book.setStatus(BookStatus.AVAILABLE);
            session.merge(borrower);
            tx.commit();
            session.merge(book);
            tx.commit();
            res = true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            Logger.getLogger(BorrowerDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }


    public List<Borrower> viewBorrowedBooks(UUID userId) {
        List<Borrower> borrowedBooks = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query<Borrower> query = session.createQuery("FROM Borrower b WHERE b.user.personId = :userId ", Borrower.class);
            query.setParameter("userId", userId);
            borrowedBooks = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            Logger.getLogger(BorrowerDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return borrowedBooks;
    }


    public int checkFines(UUID borrowerId) {
        int totalFine = 0;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query<Borrower> query = session.createQuery("SELECT SUM(b.fineAmount) FROM Borrower b WHERE b.borrowerId = :borrower_id AND b.fineAmount > 0", Borrower.class);
            query.setParameter("borrower_id", borrowerId);
            if(query.uniqueResult() != null)
                totalFine = query.uniqueResult().getFineAmount();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            Logger.getLogger(BorrowerDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return totalFine;
    }
    
}
