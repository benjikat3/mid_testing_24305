/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ben.mid_term.dao;
import com.ben.mid_term.model.Membership;
import com.ben.mid_term.model.MembershipType;
import com.ben.mid_term.util.HibernateUtil;
import java.util.Calendar;
import java.util.Date;
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
public class MembershipDao {
    private Session session;

    public boolean insert(Membership pojo) {
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
            Logger.getLogger(MembershipDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
        
    }

    public boolean delete(Membership pojo) {
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
            Logger.getLogger(MembershipDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }

    public boolean update(Membership pojo) {
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
            Logger.getLogger(MembershipDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }

    public List searchById(UUID id) {
        List<Membership> list = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Membership m WHERE m.membershipId = :id", Membership.class);
            query.setParameter("id", id);
            list = query.list();
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(MembershipDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return list;
    }
    
    public Membership findMembershipByUserId(UUID userId) {
        List<Membership> list = null;
        Membership m = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Membership m WHERE m.user.personId = :id", Membership.class);
            query.setParameter("id", userId);
            list = query.list();
            m = (list != null) ? list.get(0) : null;
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(MembershipDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return m;
    }

    public List showAll() {
        List<Membership> list = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            list = session.createQuery("FROM Membership", Membership.class).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(MembershipDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return list;
    }
    
    public int calculateFines(UUID membershipId) {
        int calculatedFine = 0;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Membership membership = session.get(Membership.class, membershipId);
            Date today = new Date();
            if (membership != null && today.after(membership.getExpiringTime())) {
                long daysOverdue = (today.getTime() - membership.getExpiringTime().getTime()) / (1000 * 60 * 60 * 24);
                calculatedFine = (int) (daysOverdue * membership.getFine());  // Assuming `fine` represents daily fine rate
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            Logger.getLogger(MembershipDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return calculatedFine;
    }

    public boolean changeMembershipType(UUID membershipId, MembershipType newType) {
        boolean res = false;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Membership membership = session.get(Membership.class, membershipId);
            if (membership != null) {
                membership.setMembershipType(newType);  // Update to the new membership type
                session.merge(membership);
                tx.commit();
                res = true;
            }
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            Logger.getLogger(MembershipDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }

    public boolean renewMembership(UUID membershipId, int extensionDays) {
        boolean res = false;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Membership membership = session.get(Membership.class, membershipId);
            if (membership != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(membership.getExpiringTime());
                calendar.add(Calendar.DATE, extensionDays);  // Extend expiration by specified days
                membership.setExpiringTime(calendar.getTime());
                session.merge(membership);
                tx.commit();
                res = true;
            }
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            Logger.getLogger(MembershipDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }
    
}

    

