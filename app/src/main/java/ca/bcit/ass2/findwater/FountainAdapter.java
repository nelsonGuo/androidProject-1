package ca.bcit.ass2.findwater;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * Created by nelson on 10/11/2017.
 */

public class FountainAdapter extends ArrayAdapter<String> {

    Context _context;

    public FountainAdapter(Context context, String[] fountains) {
        super(context, 0, fountains);
        _context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Activity activity = (Activity) _context;
        // Get the data item for this position
        String fountain = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row_layout, parent, false);
        }

        // Lookup view for data population
      //  TextView tvFirstName = (TextView) convertView.findViewById(R.id.firstName);
       // TextView tvLastName = (TextView) convertView.findViewById(R.id.lastName);
        // Populate the data into the template view using the data object
     //   tvFirstName.setText(toon.getFirstName());
       // tvLastName.setText(toon.getLastName());

        ImageView imgOnePhoto = (ImageView) convertView.findViewById(R.id.fountainImage);
        imgOnePhoto.setImageResource(R.drawable.waterfountain);
        //DownloadImageTask dit = new DownloadImageTask(_context, imgOnePhoto);
        //dit.execute(toon.getPicture());

      //  if (toon.getPicture() != null) {
        //    new ImageDownloaderTask(imgOnePhoto).execute(toon.getPicture());
        //}

        // Return the completed view to render on screen
        return convertView;
    }


}
