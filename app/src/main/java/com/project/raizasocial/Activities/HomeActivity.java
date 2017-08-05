package com.project.raizasocial.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.project.raizasocial.Adapters.CustomListAdapter;
import com.project.raizasocial.R;
import com.project.raizasocial.Services.DeviceManager;
import com.project.raizasocial.Services.LocationService;
import com.project.raizasocial.Services.SaveSharedPreference;

public class HomeActivity extends Activity {
    /**
     * {@inheritDoc}
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar();
        initHomeMenuList(savedInstanceState);
    }

    /**
     * Initiates the home menu list
     * @param savedInstanceState
     */
    private void initHomeMenuList(Bundle savedInstanceState) {
        final String[] itemName = new String[]{
                "Friends",
                "Events",
                "Location",
                "Settings"
        };

        final String[] itemDescription = new String[]{
                "View friend list",
                "Create and view events",
                "See the recent locations you've visited",
                "App settings"
        };

        final Integer[] imgId = new Integer[]{
                R.drawable.ic_action_friend,
                R.drawable.ic_action_event,
                R.drawable.ic_action_geofence,
                R.drawable.ic_action_settings
        };

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ListView listView = (ListView) findViewById(android.R.id.list);
        CustomListAdapter adapter = new CustomListAdapter(this, itemName, imgId, itemDescription);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * {@inheritDoc}
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(HomeActivity.this, FriendList.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(HomeActivity.this, EventList.class);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(HomeActivity.this, LocationActivity.class);
                    startActivity(intent);
                } else if (position == 3) {
                    Intent intent = new Intent(HomeActivity.this, Settings.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Set the action bar
     */
    private void setActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Community Organizer");
            actionBar.setLogo(R.drawable.ic_action_logo);
        }
    }

    /**
     * {@inheritDoc}
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                SaveSharedPreference.setUserName(HomeActivity.this, "");
                SaveSharedPreference.setUserEmail(HomeActivity.this, "");
                SaveSharedPreference.setLoggedInStatus(HomeActivity.this, false);
                LocationService locationService = new LocationService(HomeActivity.this);
                DeviceManager deviceManager = new DeviceManager();
                if (deviceManager.isGooglePlayServicesAvailable(getApplicationContext()))
                    locationService.stopLocationService(getApplicationContext());
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                HomeActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * {@inheritDoc}
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
}
