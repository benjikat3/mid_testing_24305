/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ben.mid_term.dao;

import com.ben.mid_term.model.AppUser;
import com.ben.mid_term.model.Membership;
import com.ben.mid_term.model.Status;
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
public class UserDao {
     private Session session;

    public boolean insert(AppUser pojo) {
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
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
        
    }

    public boolean delete(AppUser pojo) {
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
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }

    public boolean update(AppUser pojo) {
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
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }

    public List searchById(UUID id) {
        List<AppUser> list = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM AppUser u WHERE u.personId = :id", AppUser.class);
            query.setParameter("id", id);
            list = query.list();
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return list;
    }
    
    public List getPendingMembers() {
        List<AppUser> list = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM AppUser u WHERE u.personId = :id", AppUser.class);
            list = query.list();
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return list;
    }
    
    public AppUser findByUsername(String username) {
        AppUser user = null;
        List<AppUser> list = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM AppUser u WHERE u.userName = :username", AppUser.class);
            query.setParameter("username", username);
            list = query.list();
            user = (list != null) ? list.get(0) : null;
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return user;
    }
    
    public AppUser findById(UUID id) {
        AppUser user = null;
        List<AppUser> list = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM AppUser u WHERE u.personId = :id", AppUser.class);
            query.setParameter("id", id);
            list = query.list();
            user = (list != null) ? list.get(0) : null;
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return user;
    }

    public List showAll() {
        List<AppUser> list = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            list = session.createQuery("FROM UseR", AppUser.class).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return list;
    }
    
    public void updateMembershipStatus(UUID userId, boolean approved){
        MembershipDao dao = new MembershipDao();
        Membership membership = dao.findMembershipByUserId(userId);
        
        if (approved){
            membership.setMembershipStatus(Status.APPROVED);
            dao.update(membership);
        } else {
            membership.setMembershipStatus(Status.REJECTED);
            dao.update(membership);
        }
    }
}
