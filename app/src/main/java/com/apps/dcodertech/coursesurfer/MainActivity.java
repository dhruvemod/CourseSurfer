package com.apps.dcodertech.coursesurfer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    DatabaseReference databaseReference;

    private static List<Courses> courseList;
    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    String s;
    private String url = "http://13.127.127.229/predict";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        courseList = new ArrayList<Courses>();
        adapter=new RecyclerViewAdapter(this,courseList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("course_data");
        button = findViewById(R.id.submit);
        /*if(adapter!=null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Courses c = adapter.getItem(i);
                    String url = c.getCourse_link();

                    Toast.makeText(MainActivity.this,"aya hai", Toast.LENGTH_SHORT).show();

                }
            });
        }*/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerView.setAdapter(adapter);
                if (TextUtils.isEmpty(editText.getText())) {
                    Toast.makeText(MainActivity.this, "Enter first", Toast.LENGTH_SHORT).show();
                } else {
                    clear();
                    s = editText.getText().toString();
                    dataFetch(s);
                }
            }
        });
    }
    public void clear() {
        courseList.clear();
        adapter.notifyDataSetChanged();
    }
    public void dataFetch(String input) {
        Map<String, String> map = new HashMap<>();
        map.put("query", input);

        requestQueue = Volley.newRequestQueue(this);
        objectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(map), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray array=response.getJSONArray("sub");
                    String arr[]=new String[array.length()];
                    for(int i=0;i<array.length();i++){
                        arr[i]=array.getString(i);
                    }
                    String mod="";
                    mod=response.getString("query");
                    dataProcess(mod,arr);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(objectRequest);
    }
    public void dataProcess(String m,String li[]){

        final String g=m;

        for (int i = 0; i < li.length; i++) {
            String seg = li[i];
            databaseReference.child(seg).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Courses courses = dataSnapshot.getValue(Courses.class);
                    String keyword = courses.getCourse_keywords();

                    if (keyword.contains(g) && courses.getCourse_lang().equals("English")) {
                        courseList.add(courses);
                        if (adapter != null)
                            adapter.notifyDataSetChanged();

                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            // Log.i("Sub:",seg);
        }


        /*if(adapter!=null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Courses c = adapter.getItem(i);
                    String url = c.getCourse_link();
                    Intent intent=new Intent(MainActivity.this,webView.class);
                    intent.putExtra("webLink",url);
                    startActivity(intent);
                    //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();

                }
            });
        }*/
    }

}