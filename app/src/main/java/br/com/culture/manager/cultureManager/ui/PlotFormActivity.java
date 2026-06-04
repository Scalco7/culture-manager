package br.com.culture.manager.cultureManager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.culture.manager.cultureManager.R;
import br.com.culture.manager.cultureManager.data.data_access_objects.PlotDAO;
import br.com.culture.manager.cultureManager.data.db.LocalDatabase;
import br.com.culture.manager.cultureManager.domain.entities.PlotEntity;

public class PlotFormActivity extends AppCompatActivity {

    public static final String ID_KEY = "id";
    public static final String SCREEN_MODE_KEY = "screenMode";
    public static final int SCREEN_MODE_EDIT = 1;
    public static final int SCREEN_MODE_REGISTER = 0;

    private PlotDAO plotDAO;
    private EditText editTextPlotFormName;
    private EditText editTextPlotFormArea;

    private PlotEntity editingPlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_form);

        editTextPlotFormName = findViewById(R.id.editTextPlotFormName);
        editTextPlotFormArea = findViewById(R.id.editTextPlotFormArea);

        Intent openIntent = getIntent();
        Bundle bundle = openIntent.getExtras();

        plotDAO = LocalDatabase.getInstance(this).getPlotDAO();

        if(bundle != null){
            int screenMode = bundle.getInt(SCREEN_MODE_KEY);

            if(screenMode == SCREEN_MODE_REGISTER){
                setTitle(getString(R.string.register_plot));
            }else{
                setTitle(getString(R.string.edit_plot));

                long id = bundle.getLong(ID_KEY);

                editingPlot = plotDAO.getById(id);

                String name = editingPlot.getName();
                Float area = editingPlot.getAreaSize();

                editTextPlotFormName.setText(name);
                editTextPlotFormArea.setText(area.toString());
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
        editTextPlotFormName.setText("");
        editTextPlotFormArea.setText("");

        Toast.makeText(this, getText(R.string.clean_weather_fields), Toast.LENGTH_SHORT).show();
    }

    public void onSave(){
        String name = editTextPlotFormName.getText().toString();
        String areaText = editTextPlotFormArea.getText().toString();

        if(name.isEmpty()){
            Toast.makeText(this, getText(R.string.fill_with_name), Toast.LENGTH_SHORT).show();
            return;
        }

        if(areaText.isEmpty()){
            Toast.makeText(this, getText(R.string.fill_with_name), Toast.LENGTH_SHORT).show();
            return;
        }

        Float area = Float.parseFloat(areaText);

        PlotEntity plot;

        if(editingPlot == null){
            plot = new PlotEntity(name, area);

            long id = plotDAO.insert(plot);
            plot.setId(id);
        }else{
            plot = editingPlot;
            plot.setName(name);
            plot.setAreaSize(area);

            int rowsUpdated = plotDAO.update(editingPlot);

            if(rowsUpdated <= 0){
                Toast.makeText(this, getText(R.string.error_updating_plot), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Intent returnIntent = new Intent();

        returnIntent.putExtra(ID_KEY, plot.getId());

        setResult(RESULT_OK, returnIntent);
        finish();
    }
}