package com.amosayomide.radio;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;

public class StreamListActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_list);

        // Initialize DrawerLayout and ActionBarDrawerToggle
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // Enable the navigation icon on the ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize NavigationView and set the item selected listener
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(StreamListActivity.this, StreamListActivity.class));
            } else if (itemId == R.id.nav_recording) {
                startActivity(new Intent(StreamListActivity.this, RecordingsActivity.class));
            } else if (itemId == R.id.nav_schedule) {
                startActivity(new Intent(StreamListActivity.this, ScheduleActivity.class));
            }
            drawerLayout.closeDrawers();
            return true;
        });

        // Set up the RecyclerView for displaying the stream list
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Stream> streamList = new ArrayList<>();
        streamList.add(new Stream("Stream 1", "https://streamlive2.hearthis.at:8000/9065169.ogg", R.drawable.radiorock));
        streamList.add(new Stream("Stream 2", "https://streamlive2.hearthis.at:8000/9065169.ogg", R.drawable.radiorock));
        streamList.add(new Stream("Stream 3", "https://streamlive2.hearthis.at:8000/9065169.ogg", R.drawable.radiorock));
        streamList.add(new Stream("Stream 4", "https://streamlive2.hearthis.at:8000/9065169.ogg", R.drawable.radiorock));

        StreamAdapter adapter = new StreamAdapter(streamList, stream -> {
            Intent intent = new Intent(StreamListActivity.this, MainActivity.class);
            intent.putExtra("STREAM_URL", stream.getUrl());
            intent.putExtra("STREAM_NAME", stream.getName());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }

    // Override onOptionsItemSelected to handle navigation toggle clicks
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
