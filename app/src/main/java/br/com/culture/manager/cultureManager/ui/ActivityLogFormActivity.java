package br.com.culture.manager.cultureManager.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import br.com.culture.manager.cultureManager.R;
import br.com.culture.manager.cultureManager.data.data_access_objects.ActivityLogDAO;
import br.com.culture.manager.cultureManager.data.data_access_objects.PlotDAO;
import br.com.culture.manager.cultureManager.data.db.LocalDatabase;
import br.com.culture.manager.cultureManager.domain.entities.ActivityLogEntity;
import br.com.culture.manager.cultureManager.domain.entities.PlotEntity;

public class ActivityLogFormActivity extends AppCompatActivity {

    public static final String ID_KEY = "id";
    public static final String SCREEN_MODE_KEY = "screenMode";
    public static final int SCREEN_MODE_EDIT = 1;
    public static final int SCREEN_MODE_REGISTER = 0;

    private EditText editTextActivityLogName;
    private EditText editTextActivityLogTimeSent;
    private EditText editTextActivityLogDate;
    private EditText editTextActivityLogTime;
    private LocalDate activityDate;
    private LocalTime activityTime;

    private PlotDAO plotDAO;
    private ActivityLogDAO activityLogDAO;

    private List<PlotEntity> plots;

    private ActivityLogEntity editingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_log_form);

        editTextActivityLogName = findViewById(R.id.editTextActivityLogName);
        editTextActivityLogTimeSent = findViewById(R.id.editTextActivityLogTimeSpent);
        editTextActivityLogDate = findViewById(R.id.editTextActivityLogDate);
        editTextActivityLogTime = findViewById(R.id.editTextActivityLogTime);

        editTextActivityLogDate.setFocusable(false);
        editTextActivityLogTime.setFocusable(false);

        editTextActivityLogDate.setOnClickListener(
                view -> showDatePickerDialog()
        );

        editTextActivityLogTime.setOnClickListener(
                view -> showTimePickerDialog()
        );

        Intent openIntent = getIntent();
        Bundle bundle = openIntent.getExtras();

        plotDAO = LocalDatabase.getInstance(this).getPlotDAO();
        activityLogDAO = LocalDatabase.getInstance(this).getActivityLogDAO();

        plots = plotDAO.getAll();

        if (bundle != null) {
            int screenMode = bundle.getInt(SCREEN_MODE_KEY);

            if (screenMode == SCREEN_MODE_REGISTER) {
                setTitle(getString(R.string.register_activity));
            } else {
                setTitle(getString(R.string.edit_activity));

                long id = bundle.getLong(ID_KEY);

                editingActivity = activityLogDAO.getById(id);

                String name = editingActivity.getName();
                Integer timeSpent = editingActivity.getTimeSpent();
                LocalDate date = editingActivity.getDate().toLocalDate();
                LocalTime time = editingActivity.getDate().toLocalTime();

                editTextActivityLogName.setText(name);
                editTextActivityLogTimeSent.setText(timeSpent);
                editTextActivityLogDate.setText(date.toString());
                editTextActivityLogDate.setText(time.toString());
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

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener listener = (datePicker, year, month, day) -> {
            activityDate = LocalDate.of(year, month + 1, day);
            editTextActivityLogDate.setText(activityDate.toString());
        };

        if(activityDate == null){
            if(editingActivity == null){
                activityDate = LocalDate.now();
            }else{
                activityDate = editingActivity.getDate().toLocalDate();
            }
        }

        DatePickerDialog picker = new DatePickerDialog(this,
                listener, activityDate.getYear(),
                activityDate.getMonthValue() - 1,
                activityDate.getDayOfMonth());

        picker.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener listener = (timePicker, hour, minute) -> {
            activityTime = LocalTime.of(hour, minute);
            editTextActivityLogTime.setText(activityTime.toString());
        };

        if(activityTime == null){
            if(editingActivity == null){
                activityTime = LocalTime.now();
            }else{
                activityTime = editingActivity.getDate().toLocalTime();
            }
        }

        TimePickerDialog picker = new TimePickerDialog(this,
                listener, activityTime.getHour(),
                activityTime.getMinute(), true);

        picker.show();
    }

    private void cleanFields() {
        editTextActivityLogName.setText("");
        editTextActivityLogTimeSent.setText("");

        Toast.makeText(this, getText(R.string.cleaned_fields), Toast.LENGTH_SHORT).show();
    }

    private void onSave() {
//        String name = editTextPlotFormName.getText().toString();
//        String areaText = editTextPlotFormArea.getText().toString();

//        if(name.isEmpty()){
//            Toast.makeText(this, getText(R.string.fill_with_name), Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if(areaText.isEmpty()){
//            Toast.makeText(this, getText(R.string.fill_with_name), Toast.LENGTH_SHORT).show();
//            return;
//        }

//        Float area = Float.parseFloat(areaText);
//
//        PlotEntity plot;
//
//        if(editingPlot == null){
//            plot = new PlotEntity(name, area);
//
//            long id = plotDAO.insert(plot);
//            plot.setId(id);
//        }else{
//            plot = editingPlot;
//            plot.setName(name);
//            plot.setAreaSize(area);
//
//            int rowsUpdated = plotDAO.update(editingPlot);
//
//            if(rowsUpdated <= 0){
//                Toast.makeText(this, getText(R.string.error_updating_plot), Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }
//
//        Intent returnIntent = new Intent();
//
//        returnIntent.putExtra(ID_KEY, plot.getId());
//
//        setResult(RESULT_OK, returnIntent);
//        finish();
    }
}