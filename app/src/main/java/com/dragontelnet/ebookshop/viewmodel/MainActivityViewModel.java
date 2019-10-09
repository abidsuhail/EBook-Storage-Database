package com.dragontelnet.ebookshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dragontelnet.ebookshop.models.Book;
import com.dragontelnet.ebookshop.models.Category;
import com.dragontelnet.ebookshop.EBookShopRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private EBookShopRepository eBookShopRepository;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Book>> booksOfSelectedCategory;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        eBookShopRepository=new EBookShopRepository(application);
    }

    public LiveData<List<Category>> getGetAllCategories() {
        allCategories=eBookShopRepository.getCategory();
        return allCategories;
    }

    public LiveData<List<Book>> getBooksOfSelectedCategory(int categoryId) {
        booksOfSelectedCategory=eBookShopRepository.getBooks(categoryId);
        return booksOfSelectedCategory;
    }

    public void addNewBook(Book book)
    {
        eBookShopRepository.insertBook(book);
    }
    public void updateBook(Book book)
    {
        eBookShopRepository.updateBook(book);
    }
    public void deleteBook(Book book)
    {
        eBookShopRepository.deleteBook(book);
    }
}
