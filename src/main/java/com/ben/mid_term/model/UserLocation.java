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
import jakarta.persistence.Table;
import java.util.UUID;

/**
 *
 * @author benji
 */
@Entity
@Table(name = "location")
public class UserLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_id", updatable = false, nullable = false)
    private UUID locationId;
    
    @Column(name = "location_code", nullable = false, length = 50)
    private String locationCode;
    
    @Column(name = "location_name", nullable = false, length = 50)
    private String locationName;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private UserLocation parentLocation;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "location_type")
    private LocationType locationType;

    public UserLocation() {
    }

    public UserLocation(UUID locationId, String locationCode, String locationName, UserLocation parentLocation, LocationType locationType) {
        this.locationId = locationId;
        this.locationCode = locationCode;
        this.locationName = locationName;
        this.parentLocation = parentLocation;
        this.locationType = locationType;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public UserLocation getParentLocation() {
        return parentLocation;
    }

    public void setParentLocation(UserLocation parentLocation) {
        this.parentLocation = parentLocation;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }
    
    
}
