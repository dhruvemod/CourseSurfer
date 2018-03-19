package com.apps.dcodertech.coursesurfer;

import android.support.v4.content.AsyncTaskLoader;

import android.content.Context;

import java.util.List;

/**
 * Created by dhruv on 3/17/2018.
 */

public class CourseLoader extends AsyncTaskLoader<List<Courses>> {
    private String userEntry;
    public CourseLoader(Context context, String mUserEntry) {
        super(context);
        userEntry=mUserEntry;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Courses> loadInBackground() {
        return dataFetcher.listCreate(userEntry);
    }
}
