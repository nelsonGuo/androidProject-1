package ca.bcit.project.findwater;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Intent intent = getIntent();

        String jsonStr = intent.getStringExtra("Json");
        Double Xc = intent.getExtras().getDouble("Xc");
        Double Yc = intent.getExtras().getDouble("Yc");

        // Add a marker in Sydney and move the camera
        LatLng fountain = new LatLng(Xc, Yc);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fountain));

        if (jsonStr != null) {
            try {
                // Getting JSON Array node
                JSONArray JsonArray = new JSONArray(jsonStr);

                // looping through All Contacts
                for (int i = 0; i < JsonArray.length(); i++) {
                    JSONObject c = JsonArray.getJSONObject(i);

                    String parkName = c.getString("ParkName");
//                    if (parkName != "null")
//                        display += "Name: " + parkName + "\n";
//
//                    String installYear = c.getString("InstallYear");
//                    if (installYear != "null")
//                        display += "Install year: "+installYear + "\n";

                    Xc = Double.parseDouble(c.getString("X"));
                    Yc = Double.parseDouble(c.getString("Y"));
                    fountain = new LatLng(Xc, Yc);
                    String comments = c.getString("Comments");
                    if (comments != "null"){
                        mMap.addMarker(new MarkerOptions().position(fountain).title(parkName).snippet(comments));
                    } else {
                        mMap.addMarker(new MarkerOptions().position(fountain).title(parkName));
                    }
//                    String DedicationComments = c.getString("DedicationComments");
//                    if (DedicationComments != "null")
//                        display += "Dedicated Comments" + DedicationComments + "\n";

                }
            } catch (final JSONException e) {
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

        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }
}
