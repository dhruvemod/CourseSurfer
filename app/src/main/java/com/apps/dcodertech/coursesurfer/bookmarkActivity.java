package com.apps.dcodertech.coursesurfer;

import android.app.ActionBar;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.apps.dcodertech.coursesurfer.data.courseDB;

import java.util.ArrayList;

public class bookmarkActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    courseDB courseDB;
    private RecyclerView recyclerView;
    public RecyclerViewAdapter adapter;
    ArrayList<Courses> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        mToolbar=findViewById(R.id.toolbar);
        TextView textView=findViewById(R.id.text_bookmark);
        mToolbar.setTitle("My Bookmarks");
        courseDB=new courseDB(getApplicationContext());
        Cursor cursor= courseDB.readCourseInfo();
        recyclerView =findViewById(R.id.recycler_bookmarks);
        arrayList=new ArrayList<>();

        if (cursor!=null && cursor.moveToFirst()){
            do{
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String authors = cursor.getString(cursor.getColumnIndex("author"));
                String company = cursor.getString(cursor.getColumnIndex("company"));
                String provider = cursor.getString(cursor.getColumnIndex("provider"));
                String university = cursor.getString(cursor.getColumnIndex("university"));
                String certifications = cursor.getString(cursor.getColumnIndex("certification"));
                String weeks = cursor.getString(cursor.getColumnIndex("weeks"));
                String hours = cursor.getString(cursor.getColumnIndex("hours"));
                Courses courses=new Courses(name,authors,company,provider,university,certifications,weeks,hours);
                arrayList.add(courses);
            }while(cursor.moveToNext());
        }
        cursor.close();
        adapter=new RecyclerViewAdapter(this,arrayList,0);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback=new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };

        try{
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
    }catch (Exception e){ }
    if(adapter.getItemCount()<=0){

            textView.setVisibility(View.VISIBLE);
        View parentLayout = findViewById(android.R.id.content);
        // progressBar.setVisibility(View.VISIBLE);
        Snackbar snackbar=Snackbar.make(parentLayout,"No Bookmark found!",Snackbar.LENGTH_LONG);
        snackbar.show();

    }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
