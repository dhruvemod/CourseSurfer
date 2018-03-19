package com.apps.dcodertech.coursesurfer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
    ListView listView;
    CardView cardView;
    customAdapter adapter;
    DatabaseReference databaseReference;

    private static ArrayList<Courses> courseList;
    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    String s;
    final String branches[] = {"Android Development", "Mobile Development", "Computer Science", "DevOps", "Course Development"};//to be received
    final String modified = "android";
    private String url = "http://13.127.127.229/predict";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);

        courseList = new ArrayList<Courses>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("course_data");
        button = findViewById(R.id.submit);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView = findViewById(R.id.my_recycler_view);
                adapter = new customAdapter(MainActivity.this, courseList);
                listView.setAdapter(adapter);
                if (TextUtils.isEmpty(editText.getText())) {
                    Toast.makeText(MainActivity.this, "Enter first", Toast.LENGTH_SHORT).show();
                } else {


                    s = editText.getText().toString();
                    dataFetch(s);
                    /*
                    for (int i = 0; i < branches.length; i++) {
                        String seg = branches[i];
                        databaseReference.child(seg).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                Courses courses = dataSnapshot.getValue(Courses.class);
                                String keyword = courses.getCourse_keywords();

                                if (keyword.contains(modified)) {
                                    courseList.add(courses);
                                    if (adapter != null)
                                        adapter.notifyDataSetChanged();

                                }
                                // Log.i("check",String.valueOf(courseList.size()));
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
                    }*/

                }
            }
        });
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
                    mod=response.getString("sub");
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
    public void dataProcess(String mod,String li[]){
        //s = editText.getText().toString();

        for (int i = 0; i < branches.length; i++) {
            String seg = branches[i];
            databaseReference.child(seg).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Courses courses = dataSnapshot.getValue(Courses.class);
                    String keyword = courses.getCourse_keywords();

                    if (keyword.contains(modified)) {
                        courseList.add(courses);
                        if (adapter != null)
                            adapter.notifyDataSetChanged();

                    }
                    // Log.i("check",String.valueOf(courseList.size()));
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

    }

}
