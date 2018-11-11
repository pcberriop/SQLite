package com.example.user.reto8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddUpdateEnterprise extends AppCompatActivity {

    private static final String EXTRA_EMP_ID = "id";
    private static final String EXTRA_ADD_UPDATE = "add_update";
    //   private ImageView calendarImage;
    // private RadioGroup radioGroup;
    // private RadioButton maleRadioButton,femaleRadioButton;
    private EditText nameEditText;
    private EditText urlEditText;
    private EditText phoneEditText;
    private EditText mailEditText;
    private EditText productsEditText;
    private EditText classificationEditText;
    private Button addUpdateButton;
    private CheckBox consultoriaCheckBox, desarolloCheckBox, fabricacionCheckBox;
    private Enterprise newEnterprise;
    private Enterprise oldEnterprise;
    private String mode;
    private long entId;
    private EnterpriseOperations entrepriseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_enterprise);
        newEnterprise = new Enterprise();
        oldEnterprise = new Enterprise();
        nameEditText = (EditText) findViewById(R.id.edit_text_name);
        urlEditText = (EditText) findViewById(R.id.edit_text_url);
        phoneEditText = (EditText) findViewById(R.id.edit_text_phone);
        mailEditText = (EditText) findViewById(R.id.edit_text_mail);
        productsEditText = (EditText) findViewById(R.id.edit_text_products);
        consultoriaCheckBox = (CheckBox) findViewById(R.id.checkbox_consultoria);
        desarolloCheckBox = (CheckBox) findViewById(R.id.checkbox_desarollo);
        fabricacionCheckBox = (CheckBox) findViewById(R.id.checkbox_fabricacion);
        addUpdateButton = (Button) findViewById(R.id.button_add_update_enterprise);
        entrepriseData = new EnterpriseOperations(this);
        entrepriseData.open();


        mode = getIntent().getStringExtra(EXTRA_ADD_UPDATE);
        if (mode.equals("Update")) {

            addUpdateButton.setText("Actualizar empresa");
            entId = getIntent().getLongExtra(EXTRA_EMP_ID, 0);

            initializeEnterprise(entId);

        }

        addUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mode.equals("Add")) {
                    newEnterprise.setName(nameEditText.getText().toString());
                    newEnterprise.setUrl(urlEditText.getText().toString());
                    newEnterprise.setPhone(phoneEditText.getText().toString());
                    newEnterprise.setEmail(mailEditText.getText().toString());
                    newEnterprise.setProducts(productsEditText.getText().toString());
                    newEnterprise.setClassification(getClassification());
                    entrepriseData.addEnterprise(newEnterprise);
                    Toast t = Toast.makeText(AddUpdateEnterprise.this, "La empresa " + newEnterprise.getName() + " fue creada !", Toast.LENGTH_SHORT);
                    t.show();
                    Intent i = new Intent(AddUpdateEnterprise.this, MainActivity.class);
                    startActivity(i);
                } else {
                    oldEnterprise.setName(nameEditText.getText().toString());
                    oldEnterprise.setUrl(urlEditText.getText().toString());
                    oldEnterprise.setPhone(phoneEditText.getText().toString());
                    oldEnterprise.setEmail(mailEditText.getText().toString());
                    oldEnterprise.setProducts(productsEditText.getText().toString());
                    oldEnterprise.setClassification(getClassification());
                    entrepriseData.updateEnterprise(oldEnterprise);
                    Toast t = Toast.makeText(AddUpdateEnterprise.this, "La empresa " + oldEnterprise.getName() + " fue actualizada", Toast.LENGTH_SHORT);
                    t.show();
                    Intent i = new Intent(AddUpdateEnterprise.this, MainActivity.class);
                    startActivity(i);

                }


            }
        });


    }

    public void onCheckboxClicked(View view) {}

    public String getClassification(){
        String classification="";
        if(consultoriaCheckBox.isChecked())
            classification+="Consultoria;";
        if(desarolloCheckBox.isChecked())
            classification+="Desarollo a medida;";
        if(fabricacionCheckBox.isChecked())
            classification+="Fabrica a medida;";
        return classification;
    }


    private void initializeEnterprise(long entId) {
        oldEnterprise = entrepriseData.getEnterprise(entId);
        nameEditText.setText(oldEnterprise.getName());
        urlEditText.setText(oldEnterprise.getUrl());
        phoneEditText.setText(oldEnterprise.getPhone());
        mailEditText.setText(oldEnterprise.getEmail());
        productsEditText.setText(oldEnterprise.getProducts());
        initializeCheckbox(oldEnterprise);
    }

    private void initializeCheckbox(Enterprise ent){
        String classif = ent.getClassification();
        if(classif.contains("Consultoria"))
            consultoriaCheckBox.setChecked(true);
        if(classif.contains("Desarollo a medida"))
            desarolloCheckBox.setChecked(true);
        if(classif.contains("Fabrica a medida"))
            fabricacionCheckBox.setChecked(true);
    }

/*
    @Override
    public void onFinishDialog(Date date) {
        hireDateEditText.setText(formatDate(date));

    }

    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String hireDate = sdf.format(date);
        return hireDate;
    }
    */
}