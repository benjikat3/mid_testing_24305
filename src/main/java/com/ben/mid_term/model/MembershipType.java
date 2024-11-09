/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ben.mid_term.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

/**
 *
 * @author benji
 */
@Entity
@Table(name = "membership_type")
public class MembershipType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "membership_type_id", updatable = false, nullable = false)
    private UUID membershipTypeId;

    @Column(name = "membership_name", nullable = false, unique = true)
    private String membershipName;

    @Column(name = "max_books", nullable = false)
    private int maxBooks;

    @Column(name = "price", nullable = false)
    private int price;
    
    @Column(name = "daily_fine", nullable = false)
    private int dailyFine;

    public MembershipType() {
    }

    public UUID getMembershipTypeId() {
        return membershipTypeId;
    }

    public void setMembershipTypeId(UUID membershipTypeId) {
        this.membershipTypeId = membershipTypeId;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public int getMaxBooks() {
        return maxBooks;
    }

    public void setMaxBooks(int maxBooks) {
        this.maxBooks = maxBooks;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDailyFine() {
        return dailyFine;
    }

    public void setDailyFine(int dailyFine) {
        this.dailyFine = dailyFine;
    }
    
    
}
