package ca.bcit.ass2.findwater;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ExploreActivity extends AppCompatActivity {

    private Intent intent;

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
                    intent = new Intent(ExploreActivity.this,MapsActivity.class);
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

        String [] fountains = {"fountain1","fountain2","fountain3","fountain4"};

        ListAdapter nelsonsAdapter = new FountainAdapter(this,fountains);
        ListView nelsonsListView = (ListView)findViewById(R.id.fountainListView);
        nelsonsListView.setAdapter(nelsonsAdapter);



    }

}
