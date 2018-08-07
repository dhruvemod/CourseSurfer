package com.apps.dcodertech.coursesurfer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private Toolbar mToolbar;
    private TextView empty;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    DatabaseReference databaseReference;
    private MenuItem mSearchAction;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static final int RC_SIGN_IN = 1;
    public static final String ANONYMOUS = "anonymous";
    private String mUsername;
    private DrawerLayout drawerLayout;
    private boolean isSearchOpened = false;
    private NetworkInfo info;
    private Button share;
    private ProgressBar progressBar;
    private EditText edtSeach;
    private static List<Courses> courseList;
    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    String s;
    private String url = "http://13.127.127.229/predict";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerList = findViewById(R.id.navList);
        recyclerView =findViewById(R.id.recycler_view);
        mToolbar = findViewById(R.id.toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        share = findViewById(R.id.share);
        mActivityTitle = getTitle().toString();

        mFirebaseAuth = FirebaseAuth.getInstance();
        ArrayList<String> categories=new ArrayList<>();

        addDrawerItems();

        empty=findViewById(R.id.empty_view);
        imageView=findViewById(R.id.imageView);
        progressBar=findViewById(R.id.progressBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        courseList = new ArrayList<Courses>();
        adapter=new RecyclerViewAdapter(this,courseList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("course_data");
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    onSignedInInitialize(user.getDisplayName());
                } else {
                    signUp();
                }
            }
        };
        mDrawerList.bringToFront();
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imageView.setVisibility(View.INVISIBLE);
                empty.setVisibility(View.INVISIBLE);
                clear();
                String it= (String) parent.getItemAtPosition(position);
                dataProcessCategory(it);
                drawerLayout.closeDrawers();
                //Toast.makeText(getApplicationContext(), it, Toast.LENGTH_SHORT).show();
            }
        });
        mDrawerList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    mDrawerList.bringToFront();
                    drawerLayout.requestLayout();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        setupDrawer();
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Categories");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()

            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
       drawerLayout.setDrawerListener(mDrawerToggle);

    }

    public void clear() {
        courseList.clear();
        adapter.notifyDataSetChanged();
    }
    private void addDrawerItems() {
        String osArray[]={"Accounting", "Algorithms and Data Structures", "Android Development", "Anthropology", "Art & Design", "Artificial Intelligence", "Astronomy", "Big Data", "Bioinformatics", "Biology", "Business", "Business Intelligence", "Calculus", "Career Development", "Chemistry", "Civil Engineering", "Climate Change", "Communication Skills", "Computer Networking", "Computer Science", "Course Development", "Culture", "Cybersecurity", "Data Analysis", "Data Mining", "Data Science", "Data Visualization", "Databases", "Deep Learning", "Design & Creativity", "DevOps", "Digital Media", "Disease & Disorders", "ESL", "Economics", "Education & Teaching", "Electrical Engineering", "Engineering", "Entrepreneurship", "Environmental Science", "Film & Theatre", "Finance", "Foreign Language", "Foundations of Mathematics", "GIS", "Game Development", "Grammar & Writing", "Health & Medicine", "Health Care", "Higher Education", "History", "Human Resources", "Human Rights", "Humanities", "Industry Specific", "Information Technology", "Internet of Things", "K12", "Law", "Literature", "Machine Learning", "Management & Leadership", "Marketing", "Mathematics", "Mechanical Engineering", "Mobile Development", "Music", "Nanotechnology", "Nursing", "Nutrition & Wellness", "Online Education", "Personal Development", "Philosophy", "Physics", "Political Science", "Professional Development", "Programming", "Programming Languages", "Project Management", "Psychology", "Public Health", "Quantum Mechanics", "Religion", "Robotics", "STEM", "Science", "Self Improvement", "Social Sciences", "Sociology", "Software Development", "Sports", "Statistics & Probability", "Strategic Management", "Teacher Development", "Test Prep", "Urban Planning", "Visual Arts", "Web Development", "iOS Development"};



        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }
    public void dataFetch(String input){
        Map<String, String> map = new HashMap<>();
        map.put("query", input);

        requestQueue = Volley.newRequestQueue(this);
        objectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(map), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.VISIBLE);
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
                StyleableToast.makeText(getApplicationContext(), "Error: No Internet Connection!", R.style.mytoast).show();
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
                    progressBar.setVisibility(View.INVISIBLE);
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


    }

    //Adding for category overflow
    public void dataProcessCategory(final String n){
        checkConnection();
        databaseReference.child(n).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Courses courses = dataSnapshot.getValue(Courses.class);
                //String keyword = courses.getCourse_keywords();
                progressBar.setVisibility(View.INVISIBLE);


                    courseList.add(courses);
                    if (adapter != null)
                        adapter.notifyDataSetChanged();



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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded, set up the UI

                StyleableToast.makeText(this, "Signed in!", R.style.mytoast).show();
            } else if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, finish the activity
                StyleableToast.makeText(this, "Restore internet connection and restart app!", R.style.mytoast).show();

                finish();
            }

        }
    }
    public void signUp() {
        onSignedOutCleanup();
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .build(),
                RC_SIGN_IN);
    }

    private void onSignedInInitialize(String username) {
        mUsername = username;
    }

    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.icon_search));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.icon_close));

            isSearchOpened = true;
        }
    }
    @Override
    public void onBackPressed() {
        if(isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
    }
    private void doSearch() {

        recyclerView.setAdapter(adapter);
        if (TextUtils.isEmpty(edtSeach.getText())) {
            StyleableToast.makeText(getApplicationContext(), "Please enter the course first!", R.style.mytoast).show();
        } else {
            imageView.setVisibility(View.INVISIBLE);
            empty.setVisibility(View.INVISIBLE);
            clear();
            s = edtSeach.getText().toString();
            dataFetch(s);
        }
    }
    protected boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public void checkConnection(){
        if(isOnline()){

            //Toast.makeText(MainActivity.this, "You are connected to Internet", Toast.LENGTH_SHORT).show();
        }else{
            empty.setVisibility(View.VISIBLE);
            empty.setText("No internet connection!");
            Toast.makeText(MainActivity.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();

        }

    }
}