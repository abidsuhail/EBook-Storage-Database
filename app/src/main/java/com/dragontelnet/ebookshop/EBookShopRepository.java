package com.dragontelnet.ebookshop;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.dragontelnet.ebookshop.BooksDatabase;
import com.dragontelnet.ebookshop.DAO.BookDAO;
import com.dragontelnet.ebookshop.DAO.CategoryDAO;
import com.dragontelnet.ebookshop.models.Book;
import com.dragontelnet.ebookshop.models.Category;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EBookShopRepository {
    private CategoryDAO categoryDAO;
    private BookDAO bookDAO;
    private LiveData<List<Category>> category;
    private LiveData<List<Book>> books;

    public EBookShopRepository(Application application) {
        BooksDatabase booksDatabase=BooksDatabase.getInstance(application);
        categoryDAO=booksDatabase.getCategoryDAO();
        bookDAO=booksDatabase.getBookDAO();
    }

    public LiveData<List<Category>> getCategory() {
        category=categoryDAO.getAllCategories();
        return category;
    }

    public LiveData<List<Book>> getBooks(int categoryId) {
         books=bookDAO.getBooks(categoryId);
         return books;
    }

    public void insertCategory(final Category category)
    {
        Executor executor=Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                categoryDAO.insert(category);
            }
        });
    }

    public void insertBook(final Book book)
    {
        Executor executor=Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                bookDAO.insert(book);
            }
        });
    }

    public void deleteCategory(final Category category)
    {
        Executor executor=Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                categoryDAO.delete(category);
            }
        });
    }
    public void updateBook(final Book book)
    {
        Executor executor=Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                bookDAO.update(book);
            }
        });
    }

    public void deleteBook(final Book book)
    {
        Executor executor=Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                bookDAO.delete(book);
            }
        });
    }
}
