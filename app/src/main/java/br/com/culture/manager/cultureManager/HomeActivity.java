package br.com.culture.manager.cultureManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // setContentView(R.layout.activity_initial);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.menuItemPlot){
            goToPlot();
            return true;
        }

        if(itemId == R.id.menuItemActivity){
            goToActivity();
            return true;
        }

        if(itemId == R.id.menuItemWeather){
            goToWeather();
            return true;
        }

        if(itemId == R.id.menuItemSettings){
            goToSettings();
            return true;
        }

        if(itemId == R.id.menuItemAbout){
            goToAbout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToPlot(){
        //Intent intent = new Intent(this, PlotActivity.class);
        //startActivity(intent);
    }

    private void goToActivity(){
        //Intent intent = new Intent(this, ActivityActivity.class);
        //startActivity(intent);
    }

    private void goToWeather(){
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }

    private void goToSettings(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void goToAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}