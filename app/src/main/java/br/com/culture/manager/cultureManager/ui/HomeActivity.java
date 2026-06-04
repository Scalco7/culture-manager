package br.com.culture.manager.cultureManager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.com.culture.manager.cultureManager.R;
import br.com.culture.manager.cultureManager.data.data_access_objects.FarmDAO;
import br.com.culture.manager.cultureManager.data.db.LocalDatabase;
import br.com.culture.manager.cultureManager.domain.entities.FarmEntity;

public class HomeActivity extends AppCompatActivity {
    private EditText editTextFarmName;
    private FarmEntity farm;
    private FarmDAO farmDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        farmDAO = LocalDatabase.getInstance(this).getFarmDAO();

        getFarm();

        if(farm == null){
            setContentView(R.layout.activity_initial);

            editTextFarmName = findViewById(R.id.editTextFarmName);
        }else{
            setContentView(R.layout.activity_home);
            setTitle(farm.getName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuItemPlot) {
            goToPlot();
            return true;
        }

        if (itemId == R.id.menuItemActivity) {
            goToActivity();
            return true;
        }

        if (itemId == R.id.menuItemWeather) {
            goToWeather();
            return true;
        }

        if (itemId == R.id.menuItemSettings) {
            goToSettings();
            return true;
        }

        if (itemId == R.id.menuItemAbout) {
            goToAbout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getFarm() {
        List<FarmEntity> farms = farmDAO.getAll();

        if(!farms.isEmpty()){
            farm = farms.get(0);
        }else {
            farm = null;
        }
    }

    private void goToPlot() {
        Intent intent = new Intent(this, PlotActivity.class);
        startActivity(intent);
    }

    private void goToActivity() {
        Intent intent = new Intent(this, ActivityLogActivity.class);
        startActivity(intent);
    }

    private void goToWeather() {
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }

    private void goToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void goToAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void onClickSaveFarm(View view){
        String farmName = editTextFarmName.getText().toString();

        if(farmName.isEmpty()){
            Toast.makeText(this,  getText(R.string.fill_with_name), Toast.LENGTH_SHORT).show();
            return;
        }

        FarmEntity newFarm = new FarmEntity(farmName, 0);
        farmDAO.insert(newFarm);

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}