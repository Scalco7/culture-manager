package br.com.culture.manager.cultureManager;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterWeatherActivity extends AppCompatActivity {
    EditText editTextRegisterWeatherName;
    RadioGroup radioGroup;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_weather);

        editTextRegisterWeatherName = findViewById(R.id.editTextRegisterWeatherName);
        radioGroup = findViewById(R.id.radioGroupWeather);
        checkBox = findViewById(R.id.checkBoxConfirm);
    }

    public void cleanFields(View view) {
        editTextRegisterWeatherName.setText("");
        radioGroup.clearCheck();
        checkBox.setChecked(false);

        Toast.makeText(this, getText(R.string.clean_weather_fields), Toast.LENGTH_SHORT).show();
    }

    public void onSave(View view) {
        String name = editTextRegisterWeatherName.getText().toString();
        String weatherSelected = "";

    }
}