/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ben.mid_term.util;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author benji
 */
public class PasswordHashing {

    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12)); // Generate hash with a salt strength of 12
    }

    // Method to check if the provided password matches the stored hash
    public static boolean verify(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}

