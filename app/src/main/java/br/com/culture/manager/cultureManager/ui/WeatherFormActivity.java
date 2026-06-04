package br.com.culture.manager.cultureManager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.culture.manager.cultureManager.R;
import br.com.culture.manager.cultureManager.data.data_access_objects.WeatherDAO;
import br.com.culture.manager.cultureManager.data.db.LocalDatabase;
import br.com.culture.manager.cultureManager.domain.entities.WeatherEntity;
import br.com.culture.manager.cultureManager.domain.enums.WindStrength;

public class WeatherFormActivity extends AppCompatActivity {
    public static final String ID_KEY = "id";
    public static final String SCREEN_MODE_KEY = "screenMode";
    public static final int SCREEN_MODE_EDIT = 1;
    public static final int SCREEN_MODE_REGISTER = 0;


    private EditText editTextRegisterWeatherName;
    private RadioGroup radioGroupWeather;
    private CheckBox checkBoxConfirm;
    private Spinner spinnerWindStrength;

    private WeatherDAO weatherDAO;
    private WeatherEntity editingWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_form);

        editTextRegisterWeatherName = findViewById(R.id.editTextRegisterWeatherName);
        radioGroupWeather = findViewById(R.id.radioGroupWeather);
        checkBoxConfirm = findViewById(R.id.checkBoxConfirm);
        spinnerWindStrength = findViewById(R.id.spinnerWind);

        Intent openIntent = getIntent();
        Bundle bundle = openIntent.getExtras();

        weatherDAO = LocalDatabase.getInstance(this).getWeatherDAO();

        if(bundle != null){
            int screenMode = bundle.getInt(SCREEN_MODE_KEY);

            if(screenMode == SCREEN_MODE_REGISTER){
                setTitle(getString(R.string.registerWeatherTitle));
            }else{
                setTitle(getString(R.string.edit_weather_title));

                long id = bundle.getLong(ID_KEY);

                editingWeather = weatherDAO.getById(id);

                String name = editingWeather.getName();
                String weather = editingWeather.getWeather();
                WindStrength windStrength = editingWeather.getWindStrength();


                editTextRegisterWeatherName.setText(name);

                if(weather.equals(getText(R.string.sunny).toString())){
                    radioGroupWeather.check(R.id.radioButtonSunny);
                }else if(weather.equals(getText(R.string.cloudy).toString())){
                    radioGroupWeather.check(R.id.radioButtonCloudy);
                }else if(weather.equals(getText(R.string.rain).toString())){
                    radioGroupWeather.check(R.id.radioButtonRain);
                }else if(weather.equals(getText(R.string.storm).toString())){
                    radioGroupWeather.check(R.id.radioButtonStorm);
                }

                if(windStrength == WindStrength.STRONG){
                    spinnerWindStrength.setSelection(0);
                }else if(windStrength == WindStrength.MODERATE){
                    spinnerWindStrength.setSelection(1);
                }else if(windStrength == WindStrength.WEAK){
                    spinnerWindStrength.setSelection(2);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuItemSave) {
            onSave();
            return true;
        }

        if (itemId == R.id.menuItemClear) {
            cleanFields();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void cleanFields() {
        editTextRegisterWeatherName.setText("");
        radioGroupWeather.clearCheck();
        checkBoxConfirm.setChecked(false);
        spinnerWindStrength.setSelection(0);

        Toast.makeText(this, getText(R.string.clean_weather_fields), Toast.LENGTH_SHORT).show();
    }

    public void onSave() {
        String name = editTextRegisterWeatherName.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this,  getText(R.string.fill_with_name), Toast.LENGTH_SHORT).show();
            return;
        }

        int windStrengthPosition = spinnerWindStrength.getSelectedItemPosition();

        if (windStrengthPosition == AdapterView.INVALID_POSITION) {
            Toast.makeText(this, getText(R.string.select_wind_strength), Toast.LENGTH_SHORT).show();
            return;
        }

        WindStrength windStrength = WindStrength.values()[windStrengthPosition];


        if (radioGroupWeather.getCheckedRadioButtonId() == AdapterView.INVALID_POSITION) {
            Toast.makeText(this, getText(R.string.select_weather), Toast.LENGTH_SHORT).show();
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

        if (weatherSelected.isEmpty()) {
            Toast.makeText(this, getText(R.string.select_weather), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!checkBoxConfirm.isChecked()) {
            Toast.makeText(this, getText(R.string.confim_the_declaration), Toast.LENGTH_SHORT).show();
            return;
        }

        WeatherEntity weather;

        if(editingWeather == null){
            weather = new WeatherEntity(windStrength, name, weatherSelected);

            long id = weatherDAO.insert(weather);
            weather.setId(id);
        }else{
            weather = editingWeather;
            weather.setName(name);
            weather.setWeather(weatherSelected);
            weather.setWindStrength(windStrength);
            int rowsUpdated = weatherDAO.update(editingWeather);

            if(rowsUpdated <= 0){
                Toast.makeText(this, getText(R.string.error_updating_weather), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Intent returnIntent = new Intent();

        returnIntent.putExtra(ID_KEY, weather.getId());

        setResult(RESULT_OK, returnIntent);
        finish();
    }
}