package com.example.vinayak.pakingmaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vinayak.pakingmaster.adapters.CustomerListAdapter;
import com.example.vinayak.pakingmaster.adapters.ItemListAdapter;

import java.util.ArrayList;

public class PakingListActivity extends AppCompatActivity {
    TextView titleCustomer;
    TextView titleDate;
    ListView customerList;
    CustomerListAdapter customerListAdapter;

    ArrayList<String> customer = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paking_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        customer.add("Ram Patil");
        customer.add("Raju Mane");
        customer.add("Pranav Bandekar");
        customer.add("Shashank Khochikar");
        customer.add("Rahul Patil");
        customer.add("Sagar Kumbhar");
        customer.add("Ajay Rane");
        customer.add("Vinod Mane");

        date.add("02/03/2019");
        date.add("12/03/2019");
        date.add("05/03/2019");
        date.add("04/03/2019");
        date.add("01/03/2019");
        date.add("14/03/2019");
        date.add("17/03/2019");
        date.add("20/03/2019");

        assignview();
        setAdapter(customer,date);


        customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PakingListActivity.this,CustomerDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void assignview() {
        titleCustomer = (TextView)findViewById(R.id.cutomerName);
        titleDate = (TextView)findViewById(R.id.date);
        customerList = (ListView)findViewById(R.id.customerList);
    }

    private void setAdapter(ArrayList<String> customer,ArrayList<String> date) {
        customerListAdapter = new CustomerListAdapter(PakingListActivity.this, customer,date);
        customerList.setAdapter(customerListAdapter);
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
