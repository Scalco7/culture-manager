package br.com.culture.manager.cultureManager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class FarmFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_form);
        setTitle("Dados da Fazenda");
    }
}