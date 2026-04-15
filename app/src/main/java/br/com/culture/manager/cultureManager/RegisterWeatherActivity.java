package br.com.culture.manager.cultureManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.culture.manager.cultureManager.enums.WindStrength;

public class RegisterWeatherActivity extends AppCompatActivity {
    public static final String NAME_KEY = "name";
    public static final String WEATHER_KEY = "weather";
    public static final String WIND_STRENGTH_KEY = "windStrength";

    private EditText editTextRegisterWeatherName;
    private RadioGroup radioGroupWeather;
    private CheckBox checkBoxConfirm;
    private Spinner spinnerWindStrength;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_weather);

        setTitle(getString(R.string.registerWeatherTitle));

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

        int windStrengthPosition = spinnerWindStrength.getSelectedItemPosition();

        if(windStrengthPosition == AdapterView.INVALID_POSITION){
            Toast.makeText(this, "Selecione a força do vento", Toast.LENGTH_SHORT).show();
            return;
        }

        WindStrength windStrength = WindStrength.values()[windStrengthPosition];


        if(radioGroupWeather.getCheckedRadioButtonId() == AdapterView.INVALID_POSITION){
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


        Intent returnIntent = new Intent();

        returnIntent.putExtra(NAME_KEY, name);
        returnIntent.putExtra(WEATHER_KEY, weatherSelected);
        returnIntent.putExtra(WIND_STRENGTH_KEY, windStrength.name());

        setResult(RESULT_OK, returnIntent);
        finish();
    }
}