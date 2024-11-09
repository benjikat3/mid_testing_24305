/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ben.mid_term.util;

import com.ben.mid_term.model.AppUser;
import com.ben.mid_term.model.Book;
import com.ben.mid_term.model.Borrower;
import com.ben.mid_term.model.Membership;
import com.ben.mid_term.model.MembershipType;
import com.ben.mid_term.model.Room;
import com.ben.mid_term.model.Shelf;
import com.ben.mid_term.model.UserLocation;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author benji
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration conf = new Configuration();
                Properties settings = new Properties();

                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/auca_library_db");
                settings.put(Environment.USER, "ben");
                settings.put(Environment.PASS, "root");
                settings.put(Environment.HBM2DDL_AUTO, "update"); // To change to "update" after first attempt
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                conf.setProperties(settings);
                
                conf.addAnnotatedClass(AppUser.class);
                conf.addAnnotatedClass(UserLocation.class);
                conf.addAnnotatedClass(Membership.class);
                conf.addAnnotatedClass(MembershipType.class);
                conf.addAnnotatedClass(Book.class);
                conf.addAnnotatedClass(Room.class);
                conf.addAnnotatedClass(Shelf.class);
                conf.addAnnotatedClass(Borrower.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(conf.getProperties())
                        .build();
                sessionFactory = conf.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace(); // For debugging
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
