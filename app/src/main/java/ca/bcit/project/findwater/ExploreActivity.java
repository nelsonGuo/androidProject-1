package ca.bcit.project.findwater;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ExploreActivity extends AppCompatActivity {


    private  static final int REQUEST_LOCATION =1;
    private String TAG = ExploreActivity.class.getSimpleName();
    //private Intent intent;
    private ArrayList<Fountain> fountainList;
    private ListView lv;
    private FountainAdapter adapter;
    double longitude;
    double latitude;
    double distance;

    //private ShareActionProvider shareActionProvider;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_plan:
                    return true;
                case R.id.navigation_map:
                    Intent intent = new Intent(ExploreActivity.this, MapsActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchbar, menu);

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        //ask user for gps permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fountainList = new ArrayList<>();
        lv= (ListView)findViewById(R.id.fountainListView);
        new GetFountain().execute();

        Toolbar tb = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                                String url = "https://www.newwestcity.ca/parks-and-recreation/parks";
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(url));
                                startActivity(intent);
                           }
         });
    }

/*
    get json data from local resource
 */
    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = this.getAssets().open("DRINKING_FOUNTAINS.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetFountain extends AsyncTask<Void, Void, Void> {

        /*
         calculate the distance between current location and the locations of water fountain
         */
        public double calculateDistance(double x,double y){
            GPSTracker gps = new GPSTracker(ExploreActivity.this);
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

            Collections.sort(fountainList, new Comparator<Fountain>() {
                @Override
                public int compare(Fountain data1, Fountain data2) {
                    return Double.compare(data1.getDistance(),data2.getDistance());
                }
            });
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            String jsonStr = loadJSONFromAsset();

            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONArray JsonArray = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < JsonArray.length(); i++) {
                        JSONObject c = JsonArray.getJSONObject(i);

                        String parkName = c.getString("ParkName");

                        // tmp hash map for single contact
                        Fountain fountain = new Fountain(parkName);
                        fountain.setX(c.getString("X"));
                        fountain.setY(c.getString("Y"));

                        longitude = Double.parseDouble(fountain.getX());
                        latitude = Double.parseDouble(fountain.getY());
                        distance = calculateDistance(longitude,latitude);
                        fountain.setDistance(distance);

                        // adding contact to contact list
                        fountainList.add(fountain);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            sortBasedOnDistance();
            adapter = new FountainAdapter(ExploreActivity.this, fountainList);
            adapter.notifyDataSetChanged();
            lv.setAdapter(adapter);
            // Attach the adapter to a ListView
        }
    }
}
