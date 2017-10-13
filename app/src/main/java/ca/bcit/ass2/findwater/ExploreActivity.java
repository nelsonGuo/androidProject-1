package ca.bcit.ass2.findwater;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ExploreActivity extends AppCompatActivity {

    Context _context;

    private String TAG = ExploreActivity.class.getSimpleName();
    private Intent intent;
    private ArrayList<Fountain> fountainList;
    private ListView lv;

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
                    intent = new Intent(ExploreActivity.this, MapsActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fountainList = new ArrayList<Fountain>();


        ListAdapter myAdapter = new FountainAdapter(this, fountainList);
        lv= (ListView)findViewById(R.id.fountainListView);
        new GetFountain().execute();
    }


    public String loadJSONFromAsset() {
        String json = null;
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            String jsonStr = loadJSONFromAsset();

            if (jsonStr != null) {
                try {
                    //JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray toonJsonArray = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < toonJsonArray.length(); i++) {
                        JSONObject c = toonJsonArray.getJSONObject(i);

                        String parkName = c.getString("ParkName");

                        // tmp hash map for single contact
                        Fountain fountain = new Fountain();

                        // adding each child node to HashMap key => value
                        fountain.setParkName(parkName);


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

            //Toon[] toonArray = toonList.toArray(new Toon[toonList.size()]);

            FountainAdapter adapter = new FountainAdapter(ExploreActivity.this, fountainList);

            // Attach the adapter to a ListView
            lv.setAdapter(adapter);
        }


    }


}
