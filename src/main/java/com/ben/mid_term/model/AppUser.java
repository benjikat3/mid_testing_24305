/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ben.mid_term.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;

/**
 *
 * @author benji
 */
@Entity
@Table(name = "app_user")
public class AppUser extends Person{
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Membership membership;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private UserLocation village;

    public AppUser() {
    }

    public AppUser(String userName, String password, Role role, Membership membership, UserLocation village) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.membership = membership;
        this.village = village;
    }

    public AppUser(String userName, String password, Role role, Membership membership, UserLocation village, UUID personId, String firstName, String lastName, Gender gender, String phoneNumber) {
        super(personId, firstName, lastName, gender, phoneNumber);
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.membership = membership;
        this.village = village;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public UserLocation getVillage() {
        return village;
    }

    public void setVillage(UserLocation village) {
        this.village = village;
    }
    
    
    
    
}
