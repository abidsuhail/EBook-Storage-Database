package com.dragontelnet.ebookshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dragontelnet.ebookshop.R;
import com.dragontelnet.ebookshop.databinding.BookItemListLayoutBinding;
import com.dragontelnet.ebookshop.models.Book;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {
   private List<Book> oldBookList;
   private OnItemClicked listener;
    public void setBook(List<Book> newBooksList)
    {
        final DiffUtil.DiffResult result=DiffUtil
                .calculateDiff(new BookListDiffUtil(oldBookList,newBooksList),false);
        oldBookList=newBooksList;
        result.dispatchUpdatesTo(BookListAdapter.this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookItemListLayoutBinding bookItemListLayoutBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.book_item_list_layout,parent,false);
        return new ViewHolder(bookItemListLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book= oldBookList.get(position);
        holder.bookItemListLayoutBinding.setBook(book);
    }

    @Override
    public int getItemCount() {
        if (oldBookList !=null) {
            return oldBookList.size();
        }
        else
        {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        BookItemListLayoutBinding bookItemListLayoutBinding;
        public ViewHolder(@NonNull BookItemListLayoutBinding bookItemListLayoutBinding) {
            super(bookItemListLayoutBinding.getRoot());

            this.bookItemListLayoutBinding=bookItemListLayoutBinding;
            bookItemListLayoutBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition=getAdapterPosition();
                    if (listener!=null && clickedPosition!=RecyclerView.NO_POSITION)
                    {
                        listener.onItemClicked(oldBookList.get(clickedPosition));
                    }

                }
            });

        }
    }

    public interface OnItemClicked
    {
        void onItemClicked(Book book);
    }

    public void setListener(OnItemClicked listener) {
        this.listener = listener;
    }
}
