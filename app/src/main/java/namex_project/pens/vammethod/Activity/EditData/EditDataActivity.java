package namex_project.pens.vammethod.Activity.EditData;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import namex_project.pens.vammethod.Database.DatabaseHandler;
import namex_project.pens.vammethod.R;

public class EditDataActivity extends AppCompatActivity {
    public TextView total_source, total_destination;
    public static String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        total_source = (TextView)findViewById(R.id.total_source);
        total_destination = (TextView)findViewById(R.id.total_destination);


        getSupportActionBar().hide();

        Intent data_from = getIntent();
        id = data_from.getStringExtra("id");

        refreshTotalData();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("SOURCES"));
        tabLayout.addTab(tabLayout.newTab().setText("DESTINATIONS"));
        tabLayout.addTab(tabLayout.newTab().setText("COSTS"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), id);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public void refreshTotalData(){
        DatabaseHandler db = new DatabaseHandler(EditDataActivity.this);
        total_destination.setText("Destination : "+db.totalDataDestination(id));
        total_source.setText("Source : "+db.totalDataSource(id));
        db.close();
    }
}
