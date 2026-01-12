package com.example.dsgroup1.model;

public class BookRecord {
    private String bookId;
    private String bookTitle;
    private String author;
    private String publication;

    public BookRecord(String bookId, String bookTitle, String author, String publication) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.author = author;
        this.publication = publication;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }
}
