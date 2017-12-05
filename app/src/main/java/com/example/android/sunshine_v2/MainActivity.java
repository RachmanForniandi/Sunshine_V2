package com.example.android.sunshine_v2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.sunshine_v2.data.SunshinePreferences;
import com.example.android.sunshine_v2.utilities.NetworkUtils;
import com.example.android.sunshine_v2.utilities.OpenWeatherJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //Create a field to store the weather display TextView
    private TextView mWeatherTxtView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        //Use findViewById to get a reference to the weather display TextView
        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        mWeatherTxtView = (TextView)findViewById(R.id.tv_weather_data);
        mErrorMessageDisplay = (TextView)findViewById(R.id.txt_view_error_message_display);
        mLoadingIndicator = (ProgressBar)findViewById(R.id.pb_loading);

        /* Once all of our views are setup, we can load the weather data. */
        loadWeatherData();
    }

    private void loadWeatherData() {
        //Call showWeatherDataView before executing the AsyncTask
        showWeatherDataView();
        
        String location = SunshinePreferences.getPreferredWeatherLocation(this);
        new FetchWeatherTask().execute(location);
    }

    //Create a method called showWeatherDataView that will hide the error message and show the weather data
    /**
     * This method will make the View for the weather data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showWeatherDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mWeatherTxtView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        /* First, hide the currently visible data */
        mWeatherTxtView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    //Within your AsyncTask, override the method onPreExecute and show the loading indicator
    public class FetchWeatherTask extends AsyncTask<String, Void, String[]>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {
            if (params.length ==0){
                return null;
            }

            String location = params[0];
            URL weatherRequestUrl = NetworkUtils.buildUrl(location);

            try {
                String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);

                String[]simpleJsonWeatherData = OpenWeatherJsonUtils
                        .getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);

                return simpleJsonWeatherData;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] weatherData){
            //As soon as the data is finished loading, hide the loading indicator
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (weatherData != null){
                //If the weather data was not null, make sure the data view is visible
                showWeatherDataView();
                for (String weatherString : weatherData){
                    mWeatherTxtView.append((weatherString)+ "\n\n\n");
                }
            }else {
                //If the weather data was null, show the error message
                showErrorMessage();
            }
        }
    }

    //Override onCreateOptionsMenu to inflate the menu for this Activity
    //Return true to display the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.forecast, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    //Override onOptionsItemSelected to handle clicks on the refresh button
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_refresh){
            mWeatherTxtView.setText("");
            loadWeatherData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
