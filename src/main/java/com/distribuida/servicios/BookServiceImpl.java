package com.distribuida.servicios;

import com.distribuida.config.DbConfig;
import com.distribuida.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jdbi.v3.core.Handle;
import java.util.List;

@ApplicationScoped
public class BookServiceImpl implements BookService {

    @Inject
    DbConfig dbConfig;
    private Book book;

    public List<Book> findAll() {
        List<Book> ListB = null;

        Handle h = dbConfig.test1();
        ListB = h.createQuery("SELECT * FROM books").mapToBean(Book.class).list();
        h.close();
        return ListB;

    }

    public Book findById(Integer id) {
        Book Fid = new Book();
        Handle h = dbConfig.test1();
        Fid = h.createQuery("SELECT * FROM books WHERE id=?;").bind(0, id).mapToBean(Book.class).findOnly();
        h.close();
        return Fid;

    }

    @Override
    public void insert(Book book) {
        Handle jdbi = dbConfig.test1();
        jdbi.createUpdate("INSERT INTO books (isbn,title,author,price) VALUES (:isbn,:title,:author,:price)")
                .bind("isbn", book.getIsbn())
                .bind("title", book.getTitle())
                .bind("author", book.getAuthor())
                .bind("price", book.getPrice()).execute();
    }

    @Override
    public void update(Integer id, Book book) {
        Handle jdbi = dbConfig.test1();
        jdbi.createUpdate("UPDATE books SET isbn=:isbn, title=:title, author=:author,price=:price WHERE id=:id")
                .bind("id", id)
                .bind("isbn", book.getIsbn())
                .bind("title", book.getTitle())
                .bind("author", book.getAuthor())
                .bind("price", book.getPrice()).execute();
    }

    @Override
    public void delete(Integer id) {
        Book del = new Book();
        Handle jdbi = dbConfig.test1();
        del = jdbi.createQuery("DELETE FROM books WHERE id=?;").bind(0, id).mapToBean(Book.class).findOnly();

    }

    /**
     * Referencias:
     *https://jdbi.org/
     */


}
