package com.dragontelnet.ebookshop.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dragontelnet.ebookshop.adapter.BookListAdapter;
import com.dragontelnet.ebookshop.R;
import com.dragontelnet.ebookshop.databinding.ActivityMainBinding;
import com.dragontelnet.ebookshop.models.Book;
import com.dragontelnet.ebookshop.models.Category;
import com.dragontelnet.ebookshop.viewmodel.MainActivityViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MainActivityViewModel mainActivityViewModel;
    ActivityMainBinding activityMainBinding;
    List<Category> categoryList;
    List<Book> bookList;
    ClickHandlers clickHandlers;
    RecyclerView recyclerView;
    Category selectedCategory;
    public static final String BOOK_INTENT="book";
    public static final int ADDING_REQ_CODE=1;
    public static final int EDITING_REQ_CODE=2;
    public static final String EDIT_KEY="edit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        categoryList =new ArrayList<>();
        bookList=new ArrayList<>();
        clickHandlers=new ClickHandlers(this);
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityMainBinding.setClickHandler(clickHandlers);

        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getGetAllCategories()
                .observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        categoryList =categories;
                        setSpinnerItem();
                    }
                });


    }


    private void setSpinnerItem()
    {
        ArrayAdapter<Category> arrayAdapter=new ArrayAdapter<>
                (MainActivity.this,android.R.layout.simple_spinner_item, categoryList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityMainBinding.setSpinnerAdapter(arrayAdapter);
    }

    private void loadBooksOfSelCategory(int categoryId)
    {
        mainActivityViewModel.getBooksOfSelectedCategory(categoryId)
                .observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                bookList=books;
                setBookToAdapter();
            }
        });
    }

    private void setBookToAdapter()
    {
        BookListAdapter bookListAdapter=new BookListAdapter();
        bookListAdapter.setBook(bookList);
        recyclerView=activityMainBinding.secondaryLayout.bookListRv;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(bookListAdapter);
        bookListAdapter.setListener(new BookListAdapter.OnItemClicked() {
            @Override
            public void onItemClicked(Book book) {
                //editing book
                Intent intent=new Intent(MainActivity.this,AddEditActivity.class);
                intent.putExtra(EDIT_KEY,book);
                startActivityForResult(intent,EDITING_REQ_CODE);

            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Book bookToDel=bookList.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteBook(bookToDel);
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK && requestCode==1)
        {
            //adding
            Book book=(Book) data.getSerializableExtra(BOOK_INTENT);
            book.setCategoryId(selectedCategory.getId()); //we need category to know new book belongs to which category
            mainActivityViewModel.addNewBook(book);
        }
        else if(resultCode==RESULT_OK && requestCode==2)
        {
            Book book=(Book)data.getSerializableExtra(BOOK_INTENT);
            mainActivityViewModel.updateBook(book);
            //editing
        }
    }

    public class ClickHandlers
    {
        Context context;
        public ClickHandlers(Context context) {
            this.context=context;
        }

        public void onClickFab(View view)
        {
            //adding book
            Intent intent=new Intent(MainActivity.this,AddEditActivity.class);
            startActivityForResult(intent,ADDING_REQ_CODE);
        }

        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            selectedCategory=(Category)parent.getItemAtPosition(position);
            loadBooksOfSelCategory(selectedCategory.getId());
        }
    }
}
