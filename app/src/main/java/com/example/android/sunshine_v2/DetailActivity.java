package com.example.android.sunshine_v2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private static final String FORECAST_SHARE_HASHTAG = "#SunshineApp";
    private TextView mWeatherDisplay;
    private String mForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mWeatherDisplay =(TextView)findViewById(R.id.txt_view_display_weather);

        Intent intentInitiator = getIntent();

        //Display the weather forecast that was passed from MainActivity
        if (intentInitiator != null){
            if (intentInitiator.hasExtra(Intent.EXTRA_TEXT)){
                mForecast = intentInitiator.getStringExtra(Intent.EXTRA_TEXT);
                mWeatherDisplay.setText(mForecast);
            }
        }
    }
}
