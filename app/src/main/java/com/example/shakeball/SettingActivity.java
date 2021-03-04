package com.example.shakeball;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    private SharedPreferences memory_settings;
    public SeekBar seek_bar_set;

    private AppCompatSpinner sensibility_spinner;
    private AppCompatSpinner friction_spinner;
    private AppCompatSpinner vibration_spinner;
    private Switch vibro_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setActionBar();
        setBouncyBottons();
        setVibrationSwitch();

        sensibility_spinner = (AppCompatSpinner) findViewById(R.id.sens_spinner);
        friction_spinner = (AppCompatSpinner) findViewById(R.id.friction_spinner);
        vibration_spinner = (AppCompatSpinner) findViewById(R.id.vibration_spinner);
        vibro_switch = (Switch) findViewById(R.id.vibration_switch);

        seek_bar_set = (SeekBar) findViewById(R.id.seek_bar_setting);
        memory_settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (memory_settings.contains("bouncy")) {
             seek_bar_set.setProgress(memory_settings.getInt("bouncy", 0));
        }

        if (memory_settings.contains("sensibility"))
        {
            sensibility_spinner.setSelection((int) memory_settings.getLong("sensibility", 0));

        }

        if (memory_settings.contains("friction"))
        {
            friction_spinner.setSelection((int) memory_settings.getLong("friction", 0));

        }

        if (memory_settings.contains("vibration"))
        {
            int vibro_state_value = (int) memory_settings.getLong("vibration", 0);
            if (vibro_state_value != -1) {
                vibration_spinner.setSelection(vibro_state_value);
            }

            else{
                vibro_switch.setChecked(false);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = memory_settings.edit();
        editor.putInt("bouncy", seek_bar_set.getProgress());
        editor.putLong("sensibility", sensibility_spinner.getSelectedItemId());
        editor.putLong("friction", friction_spinner.getSelectedItemId());

        if (vibro_switch.isChecked()){
            editor.putLong("vibration", vibration_spinner.getSelectedItemId());
        }
        else {
            editor.putLong("vibration", -1);
        }
        editor.apply();
    }

    private void setBouncyBottons() {
        final TextView text_seek_bar = (TextView) findViewById(R.id.bouncy_text);
        final SeekBar seek_bar_setting = (SeekBar) findViewById(R.id.seek_bar_setting);
        seek_bar_setting.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                text_seek_bar.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setActionBar() {
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_setting);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setVibrationSwitch() {
        Switch vibr_switch = (Switch) findViewById(R.id.vibration_switch);
        final FrameLayout frame_vibration_strength = (FrameLayout) findViewById(R.id.strength_layout);
        vibr_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    frame_vibration_strength.setVisibility(View.VISIBLE);
                }
                else
                {
                    frame_vibration_strength.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

}