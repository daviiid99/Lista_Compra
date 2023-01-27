package com.daviiid99.basededatos;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Arrays;
import java.util.ArrayList;

import java.lang.reflect.Array;

public class agregarView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_view);

        // We'll declare all UI elements on java
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN );
        TextView addProductTitle =  findViewById(R.id.addProduct);
        TextInputEditText controllerName = findViewById(R.id.controllerName);
        TextInputEditText controllerPrice = findViewById(R.id.controllerPrice);
        Button create = findViewById(R.id.create);
        Button volver = findViewById(R.id.volver);
        Intent intento = new Intent(this, MainActivity.class);
        accederBaseDatos acceso = new accederBaseDatos(this);
        create.setOnClickListener(v -> addProduct(acceso, controllerName, controllerPrice, intento));
        volver.setOnClickListener(v -> startActivity(intento));
    }


    public ArrayList<String> retrieveFieldsValues(TextInputEditText controllerName, TextInputEditText controllerPrice){
        // A method to retrieve current text fields values
        ArrayList<String> valores = new ArrayList<String>(Arrays.asList(controllerName.getText().toString(), controllerPrice.getText().toString()));
        return valores;
    }

    private void addProduct(accederBaseDatos acceso, TextInputEditText controllerName, TextInputEditText controllerPrice, Intent intento) {
        // a method to manage database connections
        ArrayList<String> valores = retrieveFieldsValues(controllerName, controllerPrice);
        acceso.addValues(valores.get(0), Integer.valueOf(valores.get(1)));
        startActivity(intento);
    }
}