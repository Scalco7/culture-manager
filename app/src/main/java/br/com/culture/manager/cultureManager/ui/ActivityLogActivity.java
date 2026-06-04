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
import br.com.culture.manager.cultureManager.data.data_access_objects.ActivityLogDAO;
import br.com.culture.manager.cultureManager.data.data_access_objects.PlotDAO;
import br.com.culture.manager.cultureManager.data.db.LocalDatabase;
import br.com.culture.manager.cultureManager.domain.entities.ActivityLogEntity;
import br.com.culture.manager.cultureManager.domain.entities.PlotEntity;
import br.com.culture.manager.cultureManager.domain.mappers.ActivityLogMapper;
import br.com.culture.manager.cultureManager.ui.adapters.ActivityLogAdapter;
import br.com.culture.manager.cultureManager.ui.models.ActivityLogModel;
import br.com.culture.manager.cultureManager.ui.utils.AlertDialogUtils;

public class ActivityLogActivity extends AppCompatActivity {

    private final ArrayList<ActivityLogModel> activityLogs = new ArrayList<>();
    private ListView listView;
    private ActivityLogAdapter adapter;
    private PlotDAO plotDAO;
    private ActivityLogDAO activityLogDAO;
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
                removeActivityLog();
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

    private final ActivityResultLauncher<Intent> launcherRegisterActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
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

//                    long id = bundle.getLong(WeatherFormActivity.ID_KEY);
//
//                    ActivityLogEntity activity = activityLogDAO.getById(id);
//                    activityLogs.add(activity);

                    adapter.notifyDataSetChanged();
                }
            });

    private final ActivityResultLauncher<Intent> launcherEditActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
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

//                    long id = bundle.getLong(WeatherFormActivity.ID_KEY);
//
//                    ActivityLogEntity activity = activityLogDAO.getById(id);
//                    activityLogs.add(activity);

                    adapter.notifyDataSetChanged();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_log);

        setTitle(getString(R.string.activities));
        listView = findViewById(R.id.listViewActivityLog);

        plotDAO = LocalDatabase.getInstance(this).getPlotDAO();
        activityLogDAO = LocalDatabase.getInstance(this).getActivityLogDAO();

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
            ActivityLogEntity activity = (ActivityLogEntity) adapterView.getItemAtPosition(i);

            Toast.makeText(getApplicationContext(), activity.getName(), Toast.LENGTH_SHORT).show();
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

        List<ActivityLogEntity> logs = activityLogDAO.getAll();
        List<ActivityLogModel> logModels = new ArrayList<>();

        for(ActivityLogEntity log : logs){
            PlotEntity plot = plotDAO.getById(log.getPlotId());
            ActivityLogModel logModel = ActivityLogMapper.entityToModel(log, plot);
            logModels.add(logModel);
        }

        activityLogs.addAll(logModels);

        adapter = new ActivityLogAdapter(this, activityLogs);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    private void removeActivityLog(){
        final int positionToRemove = selectedPosition;

        DialogInterface.OnClickListener listenerOk = (dialogInterface, i) -> {
            ActivityLogModel logToRemove = activityLogs.get(positionToRemove);
            activityLogDAO.delete(ActivityLogMapper.modelToEntity(logToRemove));
            activityLogs.remove(positionToRemove);
            adapter.notifyDataSetChanged();
        };

        AlertDialogUtils.showConfirm(this,
                R.string.remove_activity,
                R.string.remove_activity_confirm_message,
                listenerOk,
                null);
        actionMode.finish();
    }
    private void goToEdit(){
        Intent intent = new Intent(this, PlotFormActivity.class);

        ActivityLogModel selectedActivity = activityLogs.get(selectedPosition);

//        intent.putExtra(PlotFormActivity.SCREEN_MODE_KEY, PlotFormActivity.SCREEN_MODE_EDIT);
//        intent.putExtra(PlotFormActivity.ID_KEY, selectedActivity.getId());
        launcherEditActivity.launch(intent);
    }

    private void goToCreate(){
        Intent intent = new Intent(this, PlotFormActivity.class);
//        intent.putExtra(PlotFormActivity.SCREEN_MODE_KEY, PlotFormActivity.SCREEN_MODE_REGISTER);
        launcherRegisterActivity.launch(intent);
    }
}