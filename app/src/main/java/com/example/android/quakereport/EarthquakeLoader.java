package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * [@link)EarthquakeLoader does background work of network call and is created by LoaderManager Callback method.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    /** Tag for log messages */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();
    private String [] urls;

    public EarthquakeLoader(Context context, String... url){
        super(context);
        urls = url;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if(urls.length < 1 || urls[0]== null ) {
            return null;
        }
        return QueryUtils.fetchEarthquakeData(urls[0]);

    }
}
