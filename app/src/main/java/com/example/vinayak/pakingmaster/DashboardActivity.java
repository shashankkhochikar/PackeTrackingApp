package com.example.vinayak.pakingmaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout newItem;
    LinearLayout modifyItem;
    LinearLayout viewItem;
    LinearLayout submitItem;
    LinearLayout deleteItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        assignView();
        init();
    }

    private void init() {

        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,CustomerDetailsActivity.class);
                startActivity(intent);

            }
        });
    }

    private void assignView() {

        newItem = (LinearLayout)findViewById(R.id.linearNewItem);
        modifyItem = (LinearLayout)findViewById(R.id.linearModifyItem);
        viewItem= (LinearLayout)findViewById(R.id.linearViewItem);
        submitItem = (LinearLayout)findViewById(R.id.linearSubmitItem);
        deleteItem = (LinearLayout)findViewById(R.id.linearDeleteItem);
    }
}
