package br.com.culture.manager.cultureManager.entities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.com.culture.manager.cultureManager.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Configurações");
    }
}