package com.example.vinayak.pakingmaster;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vinayak.pakingmaster.adapters.CustomerListAdapter;
import com.example.vinayak.pakingmaster.adapters.ItemListAdapter;
import com.example.vinayak.pakingmaster.baseclasses.BaseActivity;
import com.example.vinayak.pakingmaster.pojo.Customer;
import com.example.vinayak.pakingmaster.pojo.GetCustomerListResponse;
import com.example.vinayak.pakingmaster.utils.Constant;
import com.example.vinayak.pakingmaster.volley.GsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PakingListActivity extends BaseActivity {
    TextView titleCustomer;
    TextView titleDate;
    TextView txtDataNotFound;
    ListView customerList;
    CustomerListAdapter customerListAdapter;

    ArrayList<String> customer = new ArrayList<>();
    ArrayList<String> customerId = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();

    GetCustomerListResponse getCustomerListResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paking_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


       /* customer.add("Ram Patil");
        customer.add("Raju Mane");
        customer.add("Pranav Bandekar");
        customer.add("Shashank Khochikar");
        customer.add("Rahul Patil");
        customer.add("Sagar Kumbhar");
        customer.add("Ajay Rane");
        customer.add("Vinod Mane");*/

        date.add("02/03/2019");
        date.add("12/03/2019");
        date.add("05/03/2019");
        date.add("04/03/2019");
        date.add("01/03/2019");
        date.add("14/03/2019");
        date.add("17/03/2019");
        date.add("20/03/2019");

        assignview();
        prepareCustomerList();
        setAdapter(customer, date);

        customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PakingListActivity.this, CustomerSlipListActivity.class);
                String customerIdVal = customerId.get(position).toString();
                intent.putExtra("customerId", customerIdVal);
                intent.putExtra("enterBy", "shashank");
                startActivity(intent);
            }
        });
    }

    private void prepareCustomerList() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("", "");

            GsonRequest<GetCustomerListResponse> getCustomerListResquest = new GsonRequest<>(Request.Method.POST, Constant.GET_CUSTOMER_LIST, jo.toString(), GetCustomerListResponse.class,
                    new Response.Listener<GetCustomerListResponse>() {
                        @Override
                        public void onResponse(@NonNull GetCustomerListResponse response) {
                            hideBusyProgress();
                            showToast(""+response.getSuccess().toString());
                            if (response.getSuccess() == 1) {
                                getCustomerListResponse = response;
                                if (getCustomerListResponse.getCustomerList() != null && getCustomerListResponse.getCustomerList().size() > 0) {
                                    txtDataNotFound.setVisibility(View.GONE);
                                    customerList.setVisibility(View.VISIBLE);

                                    customer = new ArrayList<>(getCustomerListResponse.getCustomerList().size());
                                    customerId = new ArrayList<>(getCustomerListResponse.getCustomerList().size());
                                    date  = new ArrayList<>(getCustomerListResponse.getCustomerList().size());

                                    for (int i = 0; i < getCustomerListResponse.getCustomerList().size(); i++) {
                                        customer.add(getCustomerListResponse.getCustomerList().get(i).getCustname());
                                        customerId.add(getCustomerListResponse.getCustomerList().get(i).getId());
                                        date.add("18/05/2019");
                                    }
                                    setAdapter(customer,date);
                                } else {
                                    customerList.setVisibility(View.GONE);
                                    txtDataNotFound.setVisibility(View.VISIBLE);
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

    private void assignview() {
        titleCustomer = (TextView) findViewById(R.id.cutomerName);
        titleDate = (TextView) findViewById(R.id.date);
        customerList = (ListView) findViewById(R.id.customerList);
        txtDataNotFound = (TextView) findViewById(R.id.txtDataNotFound);
    }

    private void setAdapter(ArrayList<String> customer, ArrayList<String> date) {
        customerListAdapter = new CustomerListAdapter(PakingListActivity.this, customer, date);
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
