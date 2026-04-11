package br.com.culture.manager.cultureManager;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterWeatherActivity extends AppCompatActivity {
    EditText editTextRegisterWeatherName;
    RadioGroup radioGroupWeather;
    CheckBox checkBoxConfirm;
    Spinner spinnerWindStrength;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_weather);

        editTextRegisterWeatherName = findViewById(R.id.editTextRegisterWeatherName);
        radioGroupWeather = findViewById(R.id.radioGroupWeather);
        checkBoxConfirm = findViewById(R.id.checkBoxConfirm);
        spinnerWindStrength = findViewById(R.id.spinnerWind);
    }

    public void cleanFields(View view) {
        editTextRegisterWeatherName.setText("");
        radioGroupWeather.clearCheck();
        checkBoxConfirm.setChecked(false);
        spinnerWindStrength.setSelection(0);

        Toast.makeText(this, getText(R.string.clean_weather_fields), Toast.LENGTH_SHORT).show();
    }

    public void onSave(View view) {
        String name = editTextRegisterWeatherName.getText().toString();

        if(name.isEmpty()){
            Toast.makeText(this, "Preencha o nome", Toast.LENGTH_SHORT).show();
            return;
        }



        if(radioGroupWeather.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Selecione o clima", Toast.LENGTH_SHORT).show();
            return;
        }

        String weatherSelected;

        int checkedRadioButtonId = radioGroupWeather.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.radioButtonSunny) {
            weatherSelected = getText(R.string.sunny).toString();
        } else if (checkedRadioButtonId == R.id.radioButtonCloudy) {
            weatherSelected = getText(R.string.cloudy).toString();
        } else if (checkedRadioButtonId == R.id.radioButtonRain) {
            weatherSelected = getText(R.string.rain).toString();
        } else if (checkedRadioButtonId == R.id.radioButtonStorm) {
            weatherSelected = getText(R.string.storm).toString();
        } else {
            weatherSelected = "";
        }

        if(weatherSelected.isEmpty()){
            Toast.makeText(this, "Selecione o clima", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!checkBoxConfirm.isChecked()){
            Toast.makeText(this, "Confirme a declaração", Toast.LENGTH_SHORT).show();
            return;
        }

        String windStrength = spinnerWindStrength.getSelectedItem().toString();

        if(windStrength.isEmpty()){
            Toast.makeText(this, "Selecione a força do vento", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Nome: " + name + "\nClima: " + weatherSelected + "\nForça do vento: " + windStrength, Toast.LENGTH_SHORT).show();

    }
}