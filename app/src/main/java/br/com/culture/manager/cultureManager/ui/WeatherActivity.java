package br.com.culture.manager.cultureManager.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
import androidx.appcompat.view.ActionMode;

import java.util.ArrayList;
import java.util.List;

import br.com.culture.manager.cultureManager.R;
import br.com.culture.manager.cultureManager.data.data_access_objects.WeatherDAO;
import br.com.culture.manager.cultureManager.data.db.LocalDatabase;
import br.com.culture.manager.cultureManager.domain.entities.WeatherEntity;
import br.com.culture.manager.cultureManager.ui.utils.AlertDialogUtils;

public class WeatherActivity extends AppCompatActivity {
    public static final String PREFERENCES_KEY = "preferences";
    public static final String SORT_BY_OLDER_KEY = "sortByOlder";
    private final ArrayList<WeatherEntity> weatherEntities = new ArrayList<>();
    private ListView listView;
    private WeatherAdapter weatherAdapter;
    private View viewSelected;
    private Drawable backgroundDrawableSelected;
    private ActionMode actionMode;
    private boolean sortByOlder = false;
    private int selectedPosition = -1;

    private WeatherDAO weatherDAO;

    private final ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.item_selected_options, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int itemId = item.getItemId();

            if (itemId == R.id.menuItemEdit) {
                goToEdit();
                return true;
            }

            if (itemId == R.id.menuItemRemove) {
                removeWeather();
                return true;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            selectedPosition = -1;

            if (viewSelected != null) {
                viewSelected.setBackground(backgroundDrawableSelected);
            }

            actionMode = null;
            viewSelected = null;
            backgroundDrawableSelected = null;
            listView.setEnabled(true);
            weatherAdapter.notifyDataSetChanged();
        }
    };

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

                    long id = bundle.getLong(WeatherFormActivity.ID_KEY);

                    WeatherEntity weather = weatherDAO.getById(id);

                    if (sortByOlder) {
                        weatherEntities.add(weather);
                    } else {
                        weatherEntities.add(0, weather);
                    }

                    weatherAdapter.notifyDataSetChanged();
                }
            });

    private final ActivityResultLauncher<Intent> launcherEditWeather = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int position = selectedPosition;

                    if (actionMode != null) {
                        actionMode.finish();
                    }

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

                    long id = bundle.getLong(WeatherFormActivity.ID_KEY);
                    WeatherEntity weather = weatherDAO.getById(id);

                    weatherEntities.set(position, weather);
                    weatherAdapter.notifyDataSetChanged();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        setTitle(getString(R.string.weathers));
        listView = findViewById(R.id.listViewWeathers);

        weatherDAO = LocalDatabase.getInstance(this).getWeatherDAO();

        getPreferences();
        configureListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.entity_details_options, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menuItemSort);
        item.setChecked(sortByOlder);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuItemCreate) {
            goToCreate();
            return true;
        }

        if (itemId == R.id.menuItemSort) {
            sortByOlder = !sortByOlder;
            savePreferences();
            item.setChecked(sortByOlder);

            List<WeatherEntity> weathers = sortByOlder ? weatherDAO.getAllOlders() : weatherDAO.getAllRecents();
            weatherEntities.clear();
            weatherEntities.addAll(weathers);
            weatherAdapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }

    private void removeWeather() {
        final int positionToRemove = selectedPosition;

        DialogInterface.OnClickListener listenerOk = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                WeatherEntity entityToRemove = weatherEntities.get(positionToRemove);
                weatherDAO.delete(entityToRemove);
                weatherEntities.remove(positionToRemove);
                weatherAdapter.notifyDataSetChanged();
            }
        };

        AlertDialogUtils.showConfirm(this, R.string.remove_weather, R.string.remove_weather_message_confirm, listenerOk, null);
        actionMode.finish();
    }

    private void goToEdit() {
        Intent intent = new Intent(this, WeatherFormActivity.class);

        WeatherEntity selectedWeather = weatherEntities.get(selectedPosition);

        intent.putExtra(WeatherFormActivity.SCREEN_MODE_KEY, WeatherFormActivity.SCREEN_MODE_EDIT);
        intent.putExtra(WeatherFormActivity.ID_KEY, selectedWeather.getId());
        launcherEditWeather.launch(intent);
    }

    private void goToCreate() {
        Intent intent = new Intent(this, WeatherFormActivity.class);
        intent.putExtra(WeatherFormActivity.SCREEN_MODE_KEY, WeatherFormActivity.SCREEN_MODE_REGISTER);
        launcherRegisterWeather.launch(intent);
    }

    private void getPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_KEY, MODE_PRIVATE);
        sortByOlder = sharedPreferences.getBoolean(SORT_BY_OLDER_KEY, sortByOlder);
    }

    private void savePreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SORT_BY_OLDER_KEY, sortByOlder);
        editor.apply();
    }

    private void configureListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WeatherEntity weather = (WeatherEntity) adapterView.getItemAtPosition(i);

                Toast.makeText(getApplicationContext(), weather.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (actionMode != null) {
                    return false;
                }

                selectedPosition = position;

                viewSelected = view;
                backgroundDrawableSelected = view.getBackground();

                view.setBackgroundColor(Color.LTGRAY);

                listView.setEnabled(false);

                actionMode = startSupportActionMode(actionModeCallback);
                return false;
            }
        });

        List<WeatherEntity> weathers = sortByOlder ? weatherDAO.getAllOlders() : weatherDAO.getAllRecents();
        weatherEntities.addAll(weathers);

        weatherAdapter = new WeatherAdapter(this, weatherEntities);
        listView.setAdapter(weatherAdapter);
        registerForContextMenu(listView);
    }
}