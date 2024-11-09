/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ben.mid_term.model;
import jakarta.persistence.*;
import java.util.UUID;

/**
 *
 * @author benji
 */
@Entity
@Table(name = "room")
public class Room {
     @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_id", updatable = false, nullable = false)
    private UUID roomId;

    @Column(name = "room_code", nullable = false, unique = true)
    private String roomCode;
    
    @Column(name = "capacity")
    private Integer capacity;

    public Room() {
    }

    public Room(UUID roomId, String roomCode, Integer capacity) {
        this.roomId = roomId;
        this.roomCode = roomCode;
        this.capacity = capacity;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
