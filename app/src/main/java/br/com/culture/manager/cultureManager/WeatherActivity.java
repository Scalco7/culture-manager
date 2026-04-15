package br.com.culture.manager.cultureManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.util.ArrayList;

import br.com.culture.manager.cultureManager.entities.Weather;
import br.com.culture.manager.cultureManager.enums.WindStrength;

public class WeatherActivity extends AppCompatActivity {

    private final ArrayList<Weather> weathers = new ArrayList<>();
    private ListView listView;
    private WeatherAdapter weatherAdapter;

    private Weather mockWeather0 = new Weather(WindStrength.STRONG, "Clima de segunda", "Tempestade", LocalDateTime.now());
    private Weather mockWeather1 = new Weather(WindStrength.STRONG, "Clima de terça", "Ensolarado", LocalDateTime.now());
    private Weather mockWeather2 = new Weather(WindStrength.STRONG, "Ventando", "Nublado", LocalDateTime.now());
    private Weather mockWeather3 = new Weather(WindStrength.WEAK, "Dia do gado", "Chuvoso", LocalDateTime.now());
    private Weather mockWeather4 = new Weather(WindStrength.MODERATE, "Dia de plantar", "Chuvoso", LocalDateTime.now());
    private Weather mockWeather5 = new Weather(WindStrength.WEAK, "Friozinho", "Nublado", LocalDateTime.now());
    private Weather mockWeather6 = new Weather(WindStrength.WEAK, "Solzão", "Ensolarado", LocalDateTime.now());
    private Weather mockWeather7 = new Weather(WindStrength.MODERATE, "Céu aberto", "Ensolarado", LocalDateTime.now());
    private Weather mockWeather8 = new Weather(WindStrength.MODERATE, "Tudo Junto", "Chuvoso", LocalDateTime.now());
    private Weather mockWeather9 = new Weather(WindStrength.STRONG, "Chuva", "Tempestade", LocalDateTime.now());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        listView = findViewById(R.id.listViewWeathers);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Weather weather = (Weather) adapterView.getItemAtPosition(i);

                Toast.makeText(getApplicationContext(), weather.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        int importantForAccessibility = listView.getImportantForAccessibility();

        populateWeathers();
    }

    private void populateWeathers(){
        weathers.add(mockWeather0);
        weathers.add(mockWeather1);
        weathers.add(mockWeather2);
        weathers.add(mockWeather3);
        weathers.add(mockWeather4);
        weathers.add(mockWeather5);
        weathers.add(mockWeather6);
        weathers.add(mockWeather7);
        weathers.add(mockWeather8);
        weathers.add(mockWeather9);

        weatherAdapter = new WeatherAdapter(this, weathers);
        listView.setAdapter(weatherAdapter);
    }

    public void goToAbout(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}