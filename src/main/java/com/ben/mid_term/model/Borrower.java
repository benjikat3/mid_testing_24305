/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ben.mid_term.model;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.UUID;

/**
 *
 * @author benji
 */
@Entity
@Table(name = "borrower")
public class Borrower {
     @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "borrower_id", updatable = false, nullable = false)
    private UUID borrowerId;

    @Column(name = "borrow_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date borrowDate;

    @Column(name = "return_date")
    @Temporal(TemporalType.DATE)
    private Date returnDate;

    @Column(name = "fine_amount", nullable = false)
    private int fineAmount;

    // Foreign key to UseR (the person borrowing the book)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    // Foreign key to Book (the book being borrowed)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    // Foreign key to Shelf (where the book is borrowed from)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelf_id", nullable = false)
    private Shelf shelf;

    public Borrower() {
    }

    public Borrower(UUID borrowerId, Date borrowDate, Date returnDate, int fineAmount, AppUser user, Book book, Shelf shelf) {
        this.borrowerId = borrowerId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.fineAmount = fineAmount;
        this.user = user;
        this.book = book;
        this.shelf = shelf;
    }

    public UUID getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(UUID borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(int fineAmount) {
        this.fineAmount = fineAmount;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    
    
}
