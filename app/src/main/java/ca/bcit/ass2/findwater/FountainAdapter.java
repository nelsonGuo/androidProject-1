package ca.bcit.ass2.findwater;

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
            //   tvFirstName.setText(toon.getFirstName());
            Fountain fountain = fountains.get(position);
            parkName.setText(fountain.getParkName());
            Distance.setText("Distance: 0.9 KM");
            ImageView imgOnePhoto = (ImageView) convertView.findViewById(R.id.fountainImage);
            imgOnePhoto.setImageResource(R.drawable.waterfountain);
            //DownloadImageTask dit = new DownloadImageTask(_context, imgOnePhoto);
            //dit.execute(toon.getPicture());

            //  if (toon.getPicture() != null) {
            //    new ImageDownloaderTask(imgOnePhoto).execute(toon.getPicture());
            //}

            // Return the completed view to render on screen
        }

        return convertView;
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


