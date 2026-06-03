package br.com.culture.manager.cultureManager.ui;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import java.util.ArrayList;
import java.util.List;

import br.com.culture.manager.cultureManager.R;
import br.com.culture.manager.cultureManager.data.data_access_objects.PlotDAO;
import br.com.culture.manager.cultureManager.data.db.LocalDatabase;
import br.com.culture.manager.cultureManager.domain.entities.PlotEntity;

public class PlotActivity extends AppCompatActivity {

    private final ArrayList<PlotEntity> plotEntities = new ArrayList<>();
    private ListView listView;
    private ArrayAdapter<PlotEntity> adapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        setTitle(getString(R.string.plots));
        listView = findViewById(R.id.listViewPlots);

        plotDAO = LocalDatabase.getInstance(this).getPlotDAO();

        configureListView();
    }

    private void configureListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PlotEntity plot = (PlotEntity) adapterView.getItemAtPosition(i);

                Toast.makeText(getApplicationContext(), plot.getName(), Toast.LENGTH_SHORT).show();
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

        List<PlotEntity> plots = plotDAO.getAll();
        plotEntities.addAll(plots);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, plotEntities);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    private void goToEdit(){

    }

    private void removePlot(){

    }
}