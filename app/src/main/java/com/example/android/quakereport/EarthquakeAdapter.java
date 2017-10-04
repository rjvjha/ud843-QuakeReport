package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Rahul on 28-08-2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(@NonNull Context context, @NonNull List<Earthquake> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        Earthquake object = getItem(position);

        if(listItemView == null){
            LayoutInflater inflater = (LayoutInflater) (getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            listItemView = inflater.inflate(R.layout.earthquake_item, parent, false);
        }
        TextView magnitudeView = (TextView)listItemView.findViewById(R.id.magnitude_textView);
        TextView locationOffset = (TextView)listItemView.findViewById(R.id.location_offset);
        TextView primaryLocation = (TextView)listItemView.findViewById(R.id.location_primary);
        TextView date = (TextView)listItemView.findViewById(R.id.date_textView);
        TextView time = (TextView)listItemView.findViewById(R.id.timeTextView);

        // Formatted date & time
        Date dateFormat = new Date(object.getTimeInMilliSec());
        String formattedDate = formatDate(dateFormat);
        String formattedTime = formatTime(dateFormat);

        date.setText(formattedDate);
        time.setText(formattedTime);

        // Formatted location
        String [] formattedLocation = getFormattedLocation(object.getLocation());

        locationOffset.setText(formattedLocation[0]);
        primaryLocation.setText(formattedLocation[1]);

        // Formatted Magnitude
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        magnitudeView.setText(formatMagnitude(object.getMagnitude()));

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(object.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        return listItemView;
    }
    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd LLL, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    /**
     * Return the formatted location
     * @param location requires object's location string
     * @return array of string of formatted location
     */

    private String[] getFormattedLocation(String location){
        if(location.contains("of")) {
            String [] result = location.split(" of ",2);
            result[0] += " of";
            return result ;
        }else{
            String[] result = {"Near the", location};
            return result;
        }
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat dFormat = new DecimalFormat("0.0");
        return  dFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResId = R.color.magnitude10plus;
                break;

        }
        return ContextCompat.getColor(getContext(),magnitudeColorResId);
    }
}
