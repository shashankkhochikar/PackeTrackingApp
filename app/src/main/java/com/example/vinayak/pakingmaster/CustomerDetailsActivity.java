package com.example.vinayak.pakingmaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.vinayak.pakingmaster.adapters.ItemListAdapter;

import java.util.ArrayList;

public class CustomerDetailsActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> items = new ArrayList<>();
    ItemListAdapter adapter;
    Spinner customerNameSipnner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        getSupportActionBar().setTitle("Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        assignview();
        items.add("Biscuts");
        items.add("Pen");
        items.add("Pencil");
        items.add("Book");
        items.add("Note-Book");
        items.add("Campas");
        items.add("Gometribox");
        items.add("Sugar");
        items.add("Washing Pawder");
        items.add("Banana");

        setAdapter(items);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.customer_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerNameSipnner.setAdapter(adapter);


    }

    private void setAdapter(ArrayList<String> items) {
        adapter = new ItemListAdapter(CustomerDetailsActivity.this, items);
        listView.setAdapter(adapter);
    }

    private void assignview() {
        listView = (ListView)findViewById(R.id.itemList);
        customerNameSipnner = (Spinner)findViewById(R.id.customerSpinner);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //  startActivity(new Intent(this, HotoSectionsListActivity.class));
                return true;
            /*case R.id.menuDone:
                submitDetails();
                finish();
                startActivity(new Intent(this, ServoStabilizer.class));
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }
}
