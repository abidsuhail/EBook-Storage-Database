package com.dragontelnet.ebookshop.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dragontelnet.ebookshop.R;
import com.dragontelnet.ebookshop.databinding.ActivityAddEditBinding;
import com.dragontelnet.ebookshop.models.Book;

import static com.dragontelnet.ebookshop.ui.MainActivity.BOOK_INTENT;
import static com.dragontelnet.ebookshop.ui.MainActivity.EDIT_KEY;

public class AddEditActivity extends AppCompatActivity {

    Book book;
    ActivityAddEditBinding activityAddEditBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        book=new Book();
        activityAddEditBinding= DataBindingUtil.setContentView(this,R.layout.activity_add_edit);
        activityAddEditBinding.setClickHandler(new AddEditClickHandler(this));
        activityAddEditBinding.setBook(book);

        if (getIntent().hasExtra(EDIT_KEY))
        {
            //editing
            Book intentBook=(Book) getIntent().getSerializableExtra(EDIT_KEY);
            setTitle(intentBook.getBookName());
            book.setBookName(intentBook.getBookName());
            book.setUnitPrice(intentBook.getUnitPrice());
            book.setBookId(intentBook.getBookId());
            book.setCategoryId(intentBook.getCategoryId());
        }
        else
        {
            //adding
            setTitle("Add Book");

        }
    }
    public class AddEditClickHandler
    {
        Context context;

        public AddEditClickHandler(Context context) {
            this.context = context;
        }

        public void onSubmitClick(View view)
        {

            if (book!=null && book.getBookName()!=null && book.getUnitPrice()!=null)
            {
                Intent intent=new Intent();
                intent.putExtra(BOOK_INTENT,book);
                setResult(RESULT_OK,intent);
                finish();
            }
            else
            {
                Toast.makeText(AddEditActivity.this,"empty fields",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
