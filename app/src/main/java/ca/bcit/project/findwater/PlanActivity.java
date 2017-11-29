package ca.bcit.project.findwater;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class PlanActivity extends AppCompatActivity {
    private ListView lv;
    private FountainAdapter adapter;
    String jsonStr;
    Double Xc;
    Double Yc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        // data from explore activity for map
        Intent intent = getIntent();
        jsonStr = intent.getStringExtra("Json");
        Xc = intent.getExtras().getDouble("Xc");
        Yc = intent.getExtras().getDouble("Yc");

        MyDbHelper helper = new MyDbHelper(PlanActivity.this);
        ArrayList<Fountain> fountains = new ArrayList<Fountain>();
        helper.getFountains(fountains);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        lv = (ListView)findViewById(R.id.fountainListView);
        adapter = new FountainAdapter(PlanActivity.this, fountains);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
    }


    //private ShareActionProvider shareActionProvider;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i = new Intent(PlanActivity.this, ExploreActivity.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_plan:
                    return true;
                case R.id.navigation_map:
                    Intent intent = new Intent(PlanActivity.this, MapsActivity.class);
                    intent.putExtra("Json",jsonStr);
                    intent.putExtra("Xc",Xc);
                    intent.putExtra("Yc",Yc);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

}
