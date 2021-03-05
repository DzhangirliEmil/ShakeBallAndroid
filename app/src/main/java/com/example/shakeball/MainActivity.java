package com.example.shakeball;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements ball_fragment.NextValue{
    private SharedPreferences memory_settings;
    private ball_fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memory_settings = getSharedPreferences("settings", Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.main_navigation_view);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.ball_list_item){
                    ball_items_fragment frag = new ball_items_fragment();

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment_container, frag, null)
                            .setReorderingAllowed(true)
                            .commit();
                }

                if (item.getItemId() == R.id.ball_item){
                    fragment = new ball_fragment();

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment_container, fragment, null)
                            .setReorderingAllowed(true)
                            .commit();
                }

                if (item.getItemId() == R.id.choose_ball_item){
                    all_balls_fragment frag = new all_balls_fragment();

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment_container, frag, null)
                            .setReorderingAllowed(true)
                            .commit();
                }
                return true;
            }
        });
    }

    @Override
    public void setBallParameters(ball_fragment fragment) {
        fragment.setSensibility((int) memory_settings.getLong("sensibility", 0));
        fragment.setBouncy((int) memory_settings.getInt("bouncy", 0));
        fragment.setFriction((int) memory_settings.getLong("friction", 0));
        fragment.setVibration((int) memory_settings.getLong("vibration", 0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu_start, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_call_setting:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public String getAdviceText() {
        return "advice_generated";
    }
}