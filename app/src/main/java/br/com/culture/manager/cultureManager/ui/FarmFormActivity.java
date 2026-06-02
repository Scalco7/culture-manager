package br.com.culture.manager.cultureManager.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.com.culture.manager.cultureManager.R;

public class FarmFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_form);
        setTitle(R.string.farm_data);
    }
}