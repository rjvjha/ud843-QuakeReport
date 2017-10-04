package com.example.android.quakereport;

/**
 * Created by Rahul on 15-08-2017.
 * This class contains the all the relevant data of recent earthquakes.
 */

class Earthquake {
    private double mMagnitude;
    private String mLocation;
    // DateFormat df = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH);
    private long timeInMilliSec;
    private String url;

    public Earthquake(double magnitude, String location, long date, String url) {
        mMagnitude = magnitude;
        mLocation = location;
        timeInMilliSec = date;
        this.url = url;

    }

    // Getters Methods
    public double getMagnitude(){
        return mMagnitude;
    }

    public String getLocation(){
        return mLocation;
    }

    public long getTimeInMilliSec() {
        return timeInMilliSec;
    }

    public String getUrl(){
        return url;
    }


    @Override
    public String toString() {
        return "Earthquake{" +
                "mMagnitude='" + mMagnitude + '\'' +
                ", mLocation='" + mLocation + '\'' +
                ", mDate=" + timeInMilliSec + '\'' +
                ", url=" + url +
                '}';
    }
}
