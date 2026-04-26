package br.com.culture.manager.cultureManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import br.com.culture.manager.cultureManager.entities.Weather;
import br.com.culture.manager.cultureManager.enums.WindStrength;

public class WeatherActivity extends AppCompatActivity {

    private final ArrayList<Weather> weathers = new ArrayList<>();
    private ListView listView;
    private WeatherAdapter weatherAdapter;

    private final ActivityResultLauncher<Intent> launcherRegisterWeather = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() != RESULT_OK) {
                        return;
                    }

                    Intent data = result.getData();
                    if (data == null) {
                        return;
                    }

                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }

                    String name = bundle.getString(RegisterWeatherActivity.NAME_KEY);
                    String weather = bundle.getString(RegisterWeatherActivity.WEATHER_KEY);
                    String windStrength = bundle.getString(RegisterWeatherActivity.WIND_STRENGTH_KEY);

                    Weather weatherEntity = new Weather(WindStrength.valueOf(windStrength), name, weather);

                    weathers.add(weatherEntity);
                    weatherAdapter.notifyDataSetChanged();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        setTitle(getString(R.string.weathers_register));
        listView = findViewById(R.id.listViewWeathers);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Weather weather = (Weather) adapterView.getItemAtPosition(i);

                Toast.makeText(getApplicationContext(), weather.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        weatherAdapter = new WeatherAdapter(this, weathers);
        listView.setAdapter(weatherAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuItemAbout) {
            goToAbout();
            return true;
        }

        if (itemId == R.id.menuItemCreate) {
            goToCreate();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void goToCreate() {
        Intent intent = new Intent(this, RegisterWeatherActivity.class);
        launcherRegisterWeather.launch(intent);
    }
}