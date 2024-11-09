/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ben.mid_term.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author benji
 */
@Entity
@Table(name = "membership")
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "membership_id", updatable = false, nullable = false)
    private UUID membershipId;

    @Column(name = "membership_code", unique = true, nullable = false)
    private String membershipCode;

    @Temporal(TemporalType.DATE)
    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "expiring_time", nullable = false)
    private Date expiringTime;

    @Column(name = "fine", nullable = false)
    private int fine;

    @Enumerated(EnumType.STRING)
    @Column(name = "membership_status", nullable = false)
    private Status membershipStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "membership_type_id", nullable = false)
    private MembershipType membershipType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reader_id", nullable = false, unique = true)
    private AppUser user;

    public Membership(UUID membershipId, String membershipCode, Date registrationDate, Date expiringTime, int fine, Status membershipStatus, MembershipType membershipType, AppUser user) {
        this.membershipId = membershipId;
        this.membershipCode = membershipCode;
        this.registrationDate = registrationDate;
        this.expiringTime = expiringTime;
        this.fine = fine;
        this.membershipStatus = membershipStatus;
        this.membershipType = membershipType;
        this.user = user;
    }

    public Membership() {
    }

    public UUID getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(UUID membershipId) {
        this.membershipId = membershipId;
    }

    public String getMembershipCode() {
        return membershipCode;
    }

    public void setMembershipCode(String membershipCode) {
        this.membershipCode = membershipCode;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getExpiringTime() {
        return expiringTime;
    }

    public void setExpiringTime(Date expiringTime) {
        this.expiringTime = expiringTime;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public Status getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(Status membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
    
    
}

