package com.example.vinayak.pakingmaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.vinayak.pakingmaster.adapters.ItemListAdapter;

import java.util.ArrayList;

public class CustomerListActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        getSupportActionBar().setTitle("Customer List");


    }




}
