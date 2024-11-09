/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ben.mid_term.dao;

import com.ben.mid_term.model.Room;
import com.ben.mid_term.model.Shelf;
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
 * @author benji
 */
public class ShelfDao {
    private Session session;

    public boolean insert(Shelf pojo) {
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
            Logger.getLogger(ShelfDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
        
    }

    public boolean delete(Shelf pojo) {
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
            Logger.getLogger(ShelfDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }

    public boolean update(Shelf pojo) {
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
            Logger.getLogger(ShelfDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }
    public List searchById(UUID id) {
        List<Shelf> list = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Shelf s WHERE s.shelfId = :id", Shelf.class);
            query.setParameter("id", id);
            list = query.list();
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(ShelfDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return list;
    }
    
    public Shelf findShelfById(UUID id) {
        List<Shelf> list = null;
        Shelf s = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Shelf s WHERE s.shelfId = :id", Shelf.class);
            query.setParameter("id", id);
            list = query.list();
            s = (list != null) ? list.get(0) : null;
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(ShelfDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return s;
    }
    
    public Shelf findShelfByRoom(Room room) {
        List<Shelf> list = null;
        Shelf s = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Shelf s WHERE s.room = :room", Shelf.class);
            query.setParameter("room", room);
            list = query.list();
            s = (list != null) ? list.get(0) : null;
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(ShelfDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return s;
    }
    public List<Shelf> findAllShelvesByRoom(Room room) {
        List<Shelf> list = null;
        Shelf s = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Shelf s WHERE s.room = :room", Shelf.class);
            query.setParameter("room", room);
            list = query.list();
//            s = (list != null) ? list.get(0) : null;
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(ShelfDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return list;
    }
    
    public List showAll() {
        List<Shelf> list = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            list = session.createQuery("FROM Shelf", Shelf.class).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(ShelfDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return list;
    }
    
    public List showAllByRoom(Room room) {
        List<Shelf> list = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Shelf s WHERE s.room =: room", Shelf.class);
            q.setParameter("room", room);
            list = q.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(ShelfDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return list;
    }
    
     public Shelf locateBook(String bookCategory) {
        Shelf locatedShelf = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query<Shelf> query = session.createQuery("FROM Shelf b WHERE b.bookCategory = :category", Shelf.class);
            query.setParameter("category", bookCategory);
            locatedShelf = query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            Logger.getLogger(ShelfDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return locatedShelf;
    }


    public boolean updateCapacity(UUID shelfId, int borrowedBooks) {
        boolean res = false;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Shelf shelf = session.get(Shelf.class, shelfId);
            if (shelf != null) {
                int newBorrowedNumber = shelf.getBorrowedNumber() + borrowedBooks;
                int newAvailableStock = shelf.getInitialStock() - newBorrowedNumber;

                if (newAvailableStock >= 0) {  // Ensure capacity isn't negative
                    shelf.setBorrowedNumber(newBorrowedNumber);
                    shelf.setAvailableStock(newAvailableStock);
                    session.merge(shelf);
                    tx.commit();
                    res = true;
                } else {
                    Logger.getLogger(ShelfDao.class.getName()).log(Level.WARNING, "Not enough stock available on shelf.");
                }
            }
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            Logger.getLogger(ShelfDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }
}
