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
@Table(name = "shelf")
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shelf_id", updatable = false, nullable = false)
    private UUID shelfId;

    @Column(name = "available_stock", nullable = false)
    private int availableStock;

    @Column(name = "book_category", nullable = false)
    private String bookCategory;

    @Column(name = "borrowed_number", nullable = false)
    private int borrowedNumber;

    @Column(name = "initial_stock", nullable = false)
    private int initialStock;

    // Foreign Key to Room
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", nullable=false)
    private Room room;

    public Shelf() {
    }

    public Shelf(int availableStock, String bookCategory, int borrowedNumber, int initialStock, Room room) {
//        this.shelfId = shelfId;
        this.availableStock = availableStock;
        this.bookCategory = bookCategory;
        this.borrowedNumber = borrowedNumber;
        this.initialStock = initialStock;
        this.room = room;
    }

    public UUID getShelfId() {
        return shelfId;
    }

    public void setShelfId(UUID shelfId) {
        this.shelfId = shelfId;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public int getBorrowedNumber() {
        return borrowedNumber;
    }

    public void setBorrowedNumber(int borrowedNumber) {
        this.borrowedNumber = borrowedNumber;
    }

    public int getInitialStock() {
        return initialStock;
    }

    public void setInitialStock(int initialStock) {
        this.initialStock = initialStock;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    
    
}
