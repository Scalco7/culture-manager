package br.com.culture.manager.cultureManager.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import br.com.culture.manager.cultureManager.data.data_access_objects.PlotDAO;
import br.com.culture.manager.cultureManager.data.db.LocalDatabase;
import br.com.culture.manager.cultureManager.domain.entities.PlotEntity;
import br.com.culture.manager.cultureManager.ui.adapters.PlotAdapter;
import br.com.culture.manager.cultureManager.ui.utils.AlertDialogUtils;

public class PlotActivity extends AppCompatActivity {

    private final ArrayList<PlotEntity> plotEntities = new ArrayList<>();
    private ListView listView;
    private PlotAdapter adapter;
    private PlotDAO plotDAO;
    private ActionMode actionMode;
    private int selectedPosition = -1;
    private View viewSelected;
    private Drawable backgroundDrawableSelected;

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
                removePlot();
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
            adapter.notifyDataSetChanged();
        }
    };

    private final ActivityResultLauncher<Intent> launcherRegisterPlot = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<>() {
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

                    PlotEntity weather = plotDAO.getById(id);
                    plotEntities.add(weather);

                    adapter.notifyDataSetChanged();
                }
            });

    private final ActivityResultLauncher<Intent> launcherEditPlot = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<>() {
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
                    PlotEntity weather = plotDAO.getById(id);

                    plotEntities.set(position, weather);
                    adapter.notifyDataSetChanged();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        setTitle(getString(R.string.plots));
        listView = findViewById(R.id.listViewPlots);

        plotDAO = LocalDatabase.getInstance(this).getPlotDAO();

        configureListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.entity_activity_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuItemCreate) {
            goToCreate();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void configureListView() {
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            PlotEntity plot = (PlotEntity) adapterView.getItemAtPosition(i);

            Toast.makeText(getApplicationContext(), plot.getName(), Toast.LENGTH_SHORT).show();
        });

        listView.setOnItemLongClickListener((adapterView, view, position, l) -> {
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
        });

        List<PlotEntity> plots = plotDAO.getAll();
        plotEntities.addAll(plots);

        adapter = new PlotAdapter(this, plotEntities);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    private void removePlot(){
        final int positionToRemove = selectedPosition;

        DialogInterface.OnClickListener listenerOk = (dialogInterface, i) -> {
            PlotEntity entityToRemove = plotEntities.get(positionToRemove);
            plotDAO.delete(entityToRemove);
            plotEntities.remove(positionToRemove);
            adapter.notifyDataSetChanged();
        };

        AlertDialogUtils.showConfirm(this,
                R.string.remove_plot,
                R.string.remove_plot_confirm_message,
                listenerOk,
                null);
        actionMode.finish();
    }
    private void goToEdit(){
        Intent intent = new Intent(this, PlotFormActivity.class);

        PlotEntity selectedWeather = plotEntities.get(selectedPosition);

        intent.putExtra(PlotFormActivity.SCREEN_MODE_KEY, PlotFormActivity.SCREEN_MODE_EDIT);
        intent.putExtra(PlotFormActivity.ID_KEY, selectedWeather.getId());
        launcherEditPlot.launch(intent);
    }

    private void goToCreate(){
        Intent intent = new Intent(this, PlotFormActivity.class);
        intent.putExtra(PlotFormActivity.SCREEN_MODE_KEY, PlotFormActivity.SCREEN_MODE_REGISTER);
        launcherRegisterPlot.launch(intent);
    }
}