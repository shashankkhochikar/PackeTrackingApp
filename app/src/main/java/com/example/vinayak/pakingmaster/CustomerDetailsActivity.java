package com.example.vinayak.pakingmaster;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vinayak.pakingmaster.adapters.ItemListAdapter;
import com.example.vinayak.pakingmaster.baseclasses.BaseActivity;
import com.example.vinayak.pakingmaster.pojo.GetCustomerListResponse;
import com.example.vinayak.pakingmaster.utils.Constant;
import com.example.vinayak.pakingmaster.volley.GsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomerDetailsActivity extends BaseActivity {
    ListView listView;
    ArrayList<String> items = new ArrayList<>();
    ItemListAdapter adapter;
    Spinner customerNameSipnner;
    GetCustomerListResponse getCustomerListResponse;
    ArrayList<String> customer = new ArrayList<>();

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

       /* ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.customer_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerNameSipnner.setAdapter(adapter);*/

        prepareCustomerList();


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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.item_add_new, menu);
        final MenuItem menuItem = menu.findItem(R.id.menuAddItem);
        View actionView = MenuItemCompat.getActionView(menuItem);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //  startActivity(new Intent(this, HotoSectionsListActivity.class));
                return true;
            case R.id.menuAddItem:
                showDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(CustomerDetailsActivity.this).create();
        LayoutInflater inflater = CustomerDetailsActivity.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_new_item_alert_dialog, null);

        final EditText itemName = (EditText)dialogView.findViewById(R.id.edTxtItemName);
        final ImageView imgBarcodeScanner = (ImageView)dialogView.findViewById(R.id.imgBarcodeScanner);
        final EditText itemQuantity = (EditText)dialogView.findViewById(R.id.edTxtItemQuantity);
        final EditText itemBoxNo = (EditText)dialogView.findViewById(R.id.edTxtBoxNo);
        Button btnSubmit = (Button)dialogView.findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void prepareCustomerList() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("", "");

            GsonRequest<GetCustomerListResponse> getCustomerListResquest = new GsonRequest<>(Request.Method.POST, Constant.GET_CUSTOMER_LIST, "", GetCustomerListResponse.class,
                    new Response.Listener<GetCustomerListResponse>() {
                        @Override
                        public void onResponse(@NonNull GetCustomerListResponse response) {
                            hideBusyProgress();
                            //showToast(""+response.getSuccess().toString());
                            if (response.getSuccess() == 1) {
                                getCustomerListResponse = response;
                                if (getCustomerListResponse.getCustomerList() != null && getCustomerListResponse.getCustomerList().size() > 0) {
                                    //txtDataNotFound.setVisibility(View.GONE);
                                    //customerList.setVisibility(View.VISIBLE);

                                    customer = new ArrayList<>(getCustomerListResponse.getCustomerList().size());
                                    for (int i = 0; i < getCustomerListResponse.getCustomerList().size(); i++) {
                                        customer.add(getCustomerListResponse.getCustomerList().get(i).getCustname());
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                            (CustomerDetailsActivity.this, android.R.layout.simple_spinner_item,
                                                    customer);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    customerNameSipnner.setAdapter(adapter);
                                } else {
                                    showToast("No Customer Found");
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideBusyProgress();
                    showToast(error.getMessage());
                }
            });
            getCustomerListResquest.setRetryPolicy(Application.getDefaultRetryPolice());
            getCustomerListResquest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(getCustomerListResquest, "getCustomerListResquest");
        } catch (JSONException e) {
            hideBusyProgress();
            Log.e(PakingListActivity.class.getName(),e.getMessage());
        }


    }
}
