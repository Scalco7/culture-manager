package br.com.culture.manager.cultureManager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.culture.manager.cultureManager.R;
import br.com.culture.manager.cultureManager.data.data_access_objects.FarmDAO;
import br.com.culture.manager.cultureManager.data.db.LocalDatabase;
import br.com.culture.manager.cultureManager.domain.entities.FarmEntity;

public class FarmFormActivity extends AppCompatActivity {

    private EditText editTextFarmName;
    private FarmDAO farmDAO;
    private FarmEntity farm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_form);
        setTitle(R.string.farm_data);

        editTextFarmName = findViewById(R.id.editTextFarmNameForm);
        farmDAO = LocalDatabase.getInstance(this).getFarmDAO();
        farm = farmDAO.getAll().get(0);
        editTextFarmName.setText(farm.getName());
    }

    public void onSaveNewName(View view){
        String newFarmName = editTextFarmName.getText().toString();

        if(newFarmName.isEmpty()){
            Toast.makeText(this,  getText(R.string.fill_with_name), Toast.LENGTH_SHORT).show();
            return;
        }

        farm.setName(newFarmName);
        int rowsUpdated = farmDAO.update(farm);

        if(rowsUpdated <= 0){
            Toast.makeText(this, getText(R.string.error_updating_farm_name), Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, getText(R.string.success_updating_farm_name), Toast.LENGTH_SHORT).show();

        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}