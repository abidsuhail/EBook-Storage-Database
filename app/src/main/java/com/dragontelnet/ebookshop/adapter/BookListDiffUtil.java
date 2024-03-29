package com.dragontelnet.ebookshop.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.dragontelnet.ebookshop.models.Book;

import java.util.List;

public class BookListDiffUtil extends DiffUtil.Callback {

    private List<Book> newBooks,oldBooks;

    public BookListDiffUtil(List<Book> newBooks, List<Book> oldBooks) {
        this.newBooks = newBooks;
        this.oldBooks = oldBooks;
    }

    @Override
    public int getOldListSize() {
        return (oldBooks==null)?0:oldBooks.size();
    }

    @Override
    public int getNewListSize() {
        return (newBooks==null)?0:newBooks.size();

    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldBooks.get(oldItemPosition).getBookId()==newBooks.get(newItemPosition).getBookId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldBooks.get(oldItemPosition).equals(newBooks.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }

}
