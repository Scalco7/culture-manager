package br.com.culture.manager.cultureManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
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

    private int selectedPosition = -1;

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

    private final ActivityResultLauncher<Intent> launcherEditWeather = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int position = selectedPosition;
                    selectedPosition = -1;
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

                    Weather selectedWeather = weathers.get(position);

                    selectedWeather.setName(name);
                    selectedWeather.setWeather(weather);
                    selectedWeather.setWindStrength(WindStrength.valueOf(windStrength));

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
        registerForContextMenu(listView);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.weather_item_selected_options, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        if (itemId == R.id.menuItemEdit) {
            goToEdit(position);
            return true;
        }

        if (itemId == R.id.menuItemRemove) {
            removeWeather(position);
            return true;
        }

        return super.onContextItemSelected(item);
    }

    private void removeWeather(int position) {
        weathers.remove(position);
        weatherAdapter.notifyDataSetChanged();
    }

    private void goToEdit(int position) {
        selectedPosition = position;
        Intent intent = new Intent(this, RegisterWeatherActivity.class);

        Weather selectedWeather = weathers.get(selectedPosition);

        intent.putExtra(RegisterWeatherActivity.SCREEN_MODE_KEY, RegisterWeatherActivity.SCREEN_MODE_EDIT);
        intent.putExtra(RegisterWeatherActivity.NAME_KEY, selectedWeather.getName());
        intent.putExtra(RegisterWeatherActivity.WEATHER_KEY, selectedWeather.getWeather());
        intent.putExtra(RegisterWeatherActivity.WIND_STRENGTH_KEY, selectedWeather.getWindStrength().name());

        launcherEditWeather.launch(intent);
    }

    private void goToAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void goToCreate() {
        Intent intent = new Intent(this, RegisterWeatherActivity.class);
        intent.putExtra(RegisterWeatherActivity.SCREEN_MODE_KEY, RegisterWeatherActivity.SCREEN_MODE_REGISTER);
        launcherRegisterWeather.launch(intent);
    }
}