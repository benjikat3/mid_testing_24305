/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ben.mid_term.dao;

import com.ben.mid_term.model.LocationType;
import com.ben.mid_term.model.UserLocation;
import com.ben.mid_term.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


/**
 *
 * @author benji
 */
public class LocationDao {
     private Session session;

    public boolean insert(UserLocation pojo) {
        boolean res = false;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.persist(pojo);
            tx.commit();
            res = true;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(LocationDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
        
    }

    public boolean delete(UserLocation pojo) {
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
            Logger.getLogger(LocationDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }

    public boolean update(UserLocation pojo) {
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
            Logger.getLogger(LocationDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return res;
    }

    public List searchById(UUID id) {
        List<UserLocation> list = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM UserLocation l WHERE l.locationId = :id", UserLocation.class);
            query.setParameter("id", id);
            list = query.list();
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(LocationDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return list;
    }
    
    public UserLocation findLocationById(UUID id) {
        List<UserLocation> list = null;
        UserLocation loc = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM UserLocation l WHERE l.locationId = :id", UserLocation.class);
            query.setParameter("id", id);
            list = query.list();
            loc = (list != null) ? list.get(0) : null;
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(LocationDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return loc;
    }

    public List getAllLocations() {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM UserLocation", UserLocation.class).list();
            
        } catch (HibernateException e) {
            
            Logger.getLogger(LocationDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }

        return null;
    }
    
    public UserLocation findByLocationCode(String locationCode) {
        UserLocation location = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query<UserLocation> query = session.createQuery("FROM UserLocation l WHERE l.locationCode = :code", UserLocation.class);
            query.setParameter("code", locationCode);
            location = query.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            Logger.getLogger(LocationDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return location;
    }

    public List<UserLocation> getProvinces() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM UserLocation l WHERE l.locationType = com.ben.mid_term.model.LocationType.PROVINCE", UserLocation.class)
                    .list();
        }
    }
    public List<UserLocation> findByLocationType(LocationType locationType) {
        List<UserLocation> locations = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query<UserLocation> query = session.createQuery("FROM UserLocation l WHERE l.locationType = :type", UserLocation.class);
            query.setParameter("type", locationType);           
            locations = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            Logger.getLogger(LocationDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.close();
        }
        return locations;
    }

    // Retrieve child locations by parent ID
    public List<UserLocation> getChildLocations(UUID parentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM UserLocation l WHERE l.parentLocation.locationId = :parentId", UserLocation.class)
                    .setParameter("parentId", parentId)
                    .list();
        }
    }

}
