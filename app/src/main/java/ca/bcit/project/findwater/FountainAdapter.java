package ca.bcit.project.findwater;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by nelson on 10/11/2017.
 */

public class FountainAdapter extends BaseAdapter implements Filterable {

    private Context _context;
    private List<Fountain> fountains;
    private List<Fountain> mStringFilterList;
    private ValueFilter valueFilter;


    public FountainAdapter(Context context, ArrayList<Fountain> fountains) {
        this._context = context;
        this.fountains = fountains;
        mStringFilterList = fountains;
    }


    @Override
    public int getCount() {
        return fountains.size();
    }

    @Override
    public Object getItem(int position) {
        return fountains.get(position);
    }

    @Override
    public long getItemId(int position) {
        return fountains.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String parkname;
        double longitude;
        double latitude;
        double distance;
        LayoutInflater mInflater = (LayoutInflater) _context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = null;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_row_layout, parent, false);

            // Lookup view for data population
            TextView parkName = (TextView) convertView.findViewById(R.id.parkName);
            TextView Distance = (TextView) convertView.findViewById(R.id.distance);

            // TextView tvLastName = (TextView) convertView.findViewById(R.id.lastName);
            // Populate the data into the template view using the data object
            //Fountain fountain = fountains.get(position);
            parkname = fountains.get(position).getParkName();
            //get x and y coordinate
            longitude = Double.parseDouble(fountains.get(position).getX());
            latitude = Double.parseDouble(fountains.get(position).getY());
            distance = calculateDistance(longitude,latitude);
            fountains.get(position).setDistance(distance);

            parkName.setText(parkname);
            Distance.setText(distance+"KM");
            ImageView imgOnePhoto = (ImageView) convertView.findViewById(R.id.fountainImage);

            //replace the space with dash for park image and then get the id
            int imageResource = _context.getResources().getIdentifier("@drawable/"+parkname.replace(' ','_').toLowerCase() , null, _context.getPackageName());

            if(imageResource != 0){
                imgOnePhoto.setImageResource(imageResource);
            }else {
                imgOnePhoto.setImageResource(R.drawable.not_found);
            }
        }
        return convertView;
    }

    /*
     calculate the distance between current location and the locations of water fountain
     */
    public double calculateDistance(double x,double y){
        GPSTracker gps = new GPSTracker(_context);
        double longitude = gps.getLongitude();
        double latitude = gps.getLatitude();

        final int R = 6371;  //kilometers
        double latDistance = Math.toRadians(latitude - y);
        double lonDistance = Math.toRadians(longitude - x);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(y))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c ;
        distance = Math.pow(distance, 2);

        return  Math.round(Math.sqrt(distance)*100.0)/100.0;
    }

    public void sortBasedOnDistance(){

        Collections.sort(fountains, new Comparator<Fountain>() {
            @Override
            public int compare(Fountain data1, Fountain data2) {
                if( data1.getDistance() > data2.getDistance() )
                    return 1;
                else
                    return 0;
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<Fountain> filterList = new ArrayList<>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getParkName().toUpperCase()).contains(constraint.toString().toUpperCase())) {

                        Fountain fountain = new Fountain(mStringFilterList.get(i).getParkName());
                        filterList.add(fountain);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            fountains = (ArrayList<Fountain>) results.values;

            notifyDataSetChanged();
        }
    }


}


