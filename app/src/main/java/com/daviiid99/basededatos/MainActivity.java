package com.daviiid99.basededatos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // We'll declare all UI elements
        TextView title = findViewById(R.id.lista_compra_title);
        Button button = findViewById(R.id.add);
        Button remove = findViewById(R.id.removeall);
        ListView lista = findViewById(R.id.listview);
        Intent intento = new Intent(MainActivity.this, agregarView.class);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        ArrayList<String> listOfItems = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfItems);
        lista.setAdapter(adapter);
        accederBaseDatos acceso = new accederBaseDatos(this);
        getDataBaseEntries(acceso, listOfItems, adapter, remove);
        button.setOnClickListener(v -> startActivity(intento));
        getDataBaseEntries(acceso, listOfItems, adapter, remove);
        String product;

        remove.setOnClickListener(v -> dropAll(acceso));


        lista.setOnItemClickListener((adapter1, arg1, position, arg3) -> {
            getItem(adapter1, arg1, position, arg3, acceso);
        });
    }

    public void dropAll(accederBaseDatos acceso){
        acceso.deleteAll();
        finish();
        startActivity(getIntent());
        showDeletedAllToast();
    }

    public void showDeletedItemToast(String product){
        // A toast to show deleted items on display
        Toast toast = Toast.makeText(this, "Se ha borrado el producto " + product, Toast.LENGTH_LONG);
        toast.show();
    }

    public void showDeletedAllToast(){
        // A toast to show deleted items on display
        Toast toast = Toast.makeText(this, "Se han borrado todos los productos", Toast.LENGTH_LONG);
        toast.show();
    }

    private void getItem(AdapterView adapter1, View arg1, int position, long arg3, accederBaseDatos acceso){
        // A simple method to retrieve pressed item from listview
        // Saves item into a variable
        String producto = adapter1.getItemAtPosition(position).toString();
        System.out.println("PRODUCTO" + producto);
        removeDataBaseEntry(acceso, producto);
    }



    private void getDataBaseEntries(accederBaseDatos acceso, ArrayList<String> listOfItems, ArrayAdapter<String> adapter, Button remove){
    // A simple method to read and backup current entries from database
    ArrayList<String> temp  = acceso.fetchRows();

    int index = temp.size();

    for (int i = 0 ; i < temp.size(); i++){
        if (!listOfItems.contains(temp.get(i))){
            listOfItems.add(temp.get(i));
        }
    }

    if(!listOfItems.isEmpty()){
        remove.setVisibility(View.VISIBLE);
    }

    Collections.sort(listOfItems);
    notifyAdapter(adapter);
}

    public void removeDataBaseEntry(accederBaseDatos acceso, String producto){
        // A simple method to remove choosed entry from database
        // Show a TOAST on display on delete
        showDeletedItemToast(producto);
        acceso.deleteItem(producto);
        finish();
        startActivity(getIntent());
    }

    public void notifyAdapter(ArrayAdapter<String> adapter){
        // A method to notify adapter about changes
        adapter.notifyDataSetInvalidated();

    }
}