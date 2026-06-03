package br.com.culture.manager.cultureManager.ui;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import br.com.culture.manager.cultureManager.R;
import br.com.culture.manager.cultureManager.data.data_access_objects.PlotDAO;
import br.com.culture.manager.cultureManager.data.db.LocalDatabase;
import br.com.culture.manager.cultureManager.domain.entities.PlotEntity;

public class PlotActivity extends AppCompatActivity {

    private final ArrayList<PlotEntity> plotEntities = new ArrayList<>();
    private ListView listView;

    private PlotDAO plotDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        setTitle(getString(R.string.plots));
        listView = findViewById(R.id.listViewPlots);

        plotDAO = LocalDatabase.getInstance(this).getPlotDAO();
    }
}