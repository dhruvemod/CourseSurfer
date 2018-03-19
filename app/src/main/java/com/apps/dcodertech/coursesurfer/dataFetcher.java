package com.apps.dcodertech.coursesurfer;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhruv on 3/17/2018.
 */

public class dataFetcher {

    public static List<Courses> listCreate(String userEntry) {
        DatabaseReference databaseReference;
        final List<Courses> list;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference reference = databaseReference.child("course_data");
        list = new ArrayList<>();
        final String branches[] = {"Android Development", "Mobile Development", "Computer Science", "DevOps", "Course Development"};//to be received
        final String modified = "android";
        for (int i = 0; i < branches.length; i++) {
            String seg = branches[i];
            reference.child(seg).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Courses courses = dataSnapshot.getValue(Courses.class);
                    String keyword = courses.getCourse_keywords();

                    if (keyword.contains(modified)) {
                        list.add(courses);

                    }
                    Log.i("check", String.valueOf(list.size()));
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
        return list;
    }

}
