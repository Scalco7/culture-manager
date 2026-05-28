package br.com.culture.manager.cultureManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(R.string.settings);
    }

    public void onClickGoToFarmDetailsForm(View v){
        Intent intent = new Intent(this, FarmFormActivity.class);
        startActivity(intent);
    }
}