package com.apps.dcodertech.coursesurfer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by dhruv on 3/19/2018.
 */

public class customAdapter extends ArrayAdapter<Courses> {
    public customAdapter(Context context, List<Courses> courses) {
        super(context, 0, courses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_card, parent, false);
        }
        Courses courses = getItem(position);
        TextView title = listItemView.findViewById(R.id.titleView);
        title.setText(courses.getCourse_name());
        TextView authors = listItemView.findViewById(R.id.authorsView);
        authors.setText(courses.getCourse_provider());
        TextView certification = listItemView.findViewById(R.id.certificationView);
        certification.setText(courses.getCourse_certifications());
        TextView university = listItemView.findViewById(R.id.universityProvider);
        university.setText(courses.course_institution);
        TextView duration = listItemView.findViewById(R.id.weeksView);
        duration.setText(courses.getCourse_duration());
        TextView hours = listItemView.findViewById(R.id.hoursView);
        hours.setText(courses.getCourse_hours());
        TextView courseProvider = listItemView.findViewById(R.id.courseProvider);
        courseProvider.setText(courses.course_subject);
        return listItemView;

    }
}