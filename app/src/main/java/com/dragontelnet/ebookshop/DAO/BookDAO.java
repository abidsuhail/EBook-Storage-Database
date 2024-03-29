package com.dragontelnet.ebookshop.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dragontelnet.ebookshop.models.Book;

import java.util.List;

@Dao
public interface BookDAO {
    @Insert
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);

    @Query("select * from books_table")
    LiveData<List<Book>> getAllBooks();

    @Query("select * from books_table where category_id==:categoryId")
    LiveData<List<Book>> getBooks(int categoryId);
}
