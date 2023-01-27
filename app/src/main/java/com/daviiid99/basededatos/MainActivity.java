package com.daviiid99.basededatos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Arrays;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // We'll declare all UI elements
        TextView title = findViewById(R.id.lista_compra_title);
        Button button = findViewById(R.id.add);
        ListView lista = findViewById(R.id.listview);
        Intent intento = new Intent(MainActivity.this, agregarView.class);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN );
        ArrayList<String> listOfItems =  new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfItems);
        lista.setAdapter(adapter);
        accederBaseDatos acceso = new accederBaseDatos(this);
        getDataBaseEntries(acceso, listOfItems, adapter);
        button.setOnClickListener(v -> startActivity(intento));
        getDataBaseEntries(acceso, listOfItems, adapter);

    }

    private void getDataBaseEntries(accederBaseDatos acceso, ArrayList<String> listOfItems, ArrayAdapter<String> adapter){
        // A simple method to read and backup current entries from database
        ArrayList<String> temp  = acceso.fetchRows();

        int index = temp.size();

        for (int i = 0 ; i < temp.size() - 1; i++){
            if (!listOfItems.contains(temp.get(i))){
                listOfItems.add(temp.get(i));
                adapter.notifyDataSetInvalidated();
            }
        }

        notifyAdapter(adapter);
    }

    public void notifyAdapter(ArrayAdapter<String> adapter){
        // A method to notify adapter about changes
        adapter.notifyDataSetInvalidated();

    }
}