package com.example.vinayak.pakingmaster;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vinayak.pakingmaster.adapters.SlipListAdapter;
import com.example.vinayak.pakingmaster.baseclasses.BaseActivity;
import com.example.vinayak.pakingmaster.pojo.CustomerOrderListData;
import com.example.vinayak.pakingmaster.pojo.GetCustomerListResponse;
import com.example.vinayak.pakingmaster.pojo.SlipListResponseData;
import com.example.vinayak.pakingmaster.utils.Constant;
import com.example.vinayak.pakingmaster.volley.GsonRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CustomerSlipListActivity extends BaseActivity {

    SlipListResponseData slipListResponseData;
    ListView slipList;
    TextView txtDataNotFoundForSlip;
    List<CustomerOrderListData> customerOrderListData;
    SlipListAdapter slipListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_slip_list);
        this.setTitle("Customer Slip List");

        final Intent intent = getIntent();
        String customerId = intent.getStringExtra("customerId");
        String enterBy = intent.getStringExtra("enterBy");

        assignView();
        prepareCustomerSlipList(customerId,enterBy);

        slipList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent slipDetailsIntent = new Intent(CustomerSlipListActivity.this,CustomerDetailsActivity.class);
                slipDetailsIntent.putExtra("slipNumber",customerOrderListData.get(position).getSlipno());
                startActivity(slipDetailsIntent);
            }
        });


    }

    private void assignView() {
        slipList = (ListView)findViewById(R.id.slipList);
        txtDataNotFoundForSlip = (TextView)findViewById(R.id.txtDataNotFoundForSlip);
    }

    private void prepareCustomerSlipList(String customerId, String enterBy) {

        try{
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("CustomerId", customerId);
            jo.put("enteryby", enterBy);

            GsonRequest<SlipListResponseData> slipListResquest = new GsonRequest<>(Request.Method.POST, Constant.GET_CUSTOMER_ORDER_LIST, jo.toString(), SlipListResponseData.class,
                    new Response.Listener<SlipListResponseData>() {
                        @Override
                        public void onResponse(@NonNull SlipListResponseData response) {
                            hideBusyProgress();
                            showToast(""+response.getSuccess().toString());
                            if (response.getSuccess() == 1) {
                                slipListResponseData = response;
                                if (slipListResponseData.getCustomerOrderList() != null && slipListResponseData.getCustomerOrderList().size() > 0) {
                                    txtDataNotFoundForSlip.setVisibility(View.GONE);
                                    slipList.setVisibility(View.VISIBLE);
                                    customerOrderListData = slipListResponseData.getCustomerOrderList();
                                    slipListAdapter = new SlipListAdapter(CustomerSlipListActivity.this,customerOrderListData);
                                    slipList.setAdapter(slipListAdapter);

                                } else {
                                    slipList.setVisibility(View.GONE);
                                    txtDataNotFoundForSlip.setText("Data Not Found");
                                    txtDataNotFoundForSlip.setVisibility(View.VISIBLE);
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
            slipListResquest.setRetryPolicy(Application.getDefaultRetryPolice());
            slipListResquest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(slipListResquest, "slipListResquest");

        }catch (Exception e){
            hideBusyProgress();
            Log.e(CustomerSlipListActivity.class.getName(),e.getMessage());
        }
    }
}
