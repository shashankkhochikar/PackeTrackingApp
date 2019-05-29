package com.example.vinayak.pakingmaster;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vinayak.pakingmaster.adapters.SlipListAdapter;
import com.example.vinayak.pakingmaster.baseclasses.BaseActivity;
import com.example.vinayak.pakingmaster.pojo.CustomerOrderListData;
import com.example.vinayak.pakingmaster.pojo.DeleteSlipResponseData;
import com.example.vinayak.pakingmaster.pojo.GetCustomerListResponse;
import com.example.vinayak.pakingmaster.pojo.SlipDetailsResponseData;
import com.example.vinayak.pakingmaster.pojo.SlipListResponseData;
import com.example.vinayak.pakingmaster.pojo.SubmitOrderResponse;
import com.example.vinayak.pakingmaster.pojo.SubmitSlipResponseData;
import com.example.vinayak.pakingmaster.utils.Constant;
import com.example.vinayak.pakingmaster.volley.GsonRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CustomerSlipListActivity extends BaseActivity {

    private static final String TAG = CustomerSlipListActivity.class.getName();

    SlipListResponseData slipListResponseData;
    ListView slipList;
    TextView txtDataNotFoundForSlip;
    List<CustomerOrderListData> customerOrderListData;
    SlipListAdapter slipListAdapter;
    String modeOfOpration = "";
    String customerId = "";
    String enterBy = "";

    SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_slip_list);
        getSupportActionBar().setTitle("Packing List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent intent = getIntent();

        modeOfOpration = intent.getStringExtra("modeOfOpration");
        customerId = intent.getStringExtra("customerId");
        enterBy = intent.getStringExtra("enterBy");

        assignView();
        prepareCustomerSlipList(customerId, enterBy);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               try{      customerOrderListData.clear();     }
               catch(Exception ex){     Log.e("Exp",ex.getMessage());   }
               prepareCustomerSlipList(customerId, enterBy);
               pullToRefresh.setRefreshing(false);
           }
       });

                slipList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        if (modeOfOpration.equals("1")) {

                            Intent slipDetailsIntent = new Intent(CustomerSlipListActivity.this, CustomerDetailsActivity.class);
                            slipDetailsIntent.putExtra("slipNumber", customerOrderListData.get(position).getSlipno());
                            slipDetailsIntent.putExtra("modeOfOpration", modeOfOpration);
                            startActivity(slipDetailsIntent);

                        } else if (modeOfOpration.equals("2")) {// when mode of opration submit

                            Intent slipDetailsIntent = new Intent(CustomerSlipListActivity.this, CustomerDetailsActivity.class);
                            slipDetailsIntent.putExtra("slipNumber", customerOrderListData.get(position).getSlipno());
                            slipDetailsIntent.putExtra("modeOfOpration", modeOfOpration);
                            startActivity(slipDetailsIntent);

                            /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CustomerSlipListActivity.this);
                            alertDialogBuilder.setMessage("Are you sure, Do you wanted to submit this slip ?");
                            alertDialogBuilder.setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {

                                            submitCustomerSlip(customerOrderListData.get(position).getSlipno(), position);

                                        }
                                    });
                            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();*/


                        } else if (modeOfOpration.equals("3")) { //when mode of opration is delete

                            Intent slipDetailsIntent = new Intent(CustomerSlipListActivity.this, CustomerDetailsActivity.class);
                            slipDetailsIntent.putExtra("slipNumber", customerOrderListData.get(position).getSlipno());
                            slipDetailsIntent.putExtra("modeOfOpration", modeOfOpration);
                            startActivity(slipDetailsIntent);

                            /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CustomerSlipListActivity.this);
                            alertDialogBuilder.setMessage("Are you sure, Do you wanted to delete this slip ?");
                            alertDialogBuilder.setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {

                                            deleteCustomerSlip(customerOrderListData.get(position).getSlipno(), position);

                                        }
                                    });
                            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();*/

                        }
                    }
                });

                slipList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int pos, long id) {

                        if (modeOfOpration.equals("2")) {

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CustomerSlipListActivity.this);
                            alertDialogBuilder.setMessage("Are you sure, Do you wanted to submit this slip ?");
                            alertDialogBuilder.setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {

                                            submitCustomerSlip(customerOrderListData.get(pos).getSlipno(), pos);

                                        }
                                    });
                            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();


                        } else if (modeOfOpration.equals("3")) { //when mode of opration is delete

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CustomerSlipListActivity.this);
                            alertDialogBuilder.setMessage("Are you sure, Do you wanted to delete this slip ?");
                            alertDialogBuilder.setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {

                                            deleteCustomerSlip(customerOrderListData.get(pos).getSlipno(), pos);

                                        }
                                    });
                            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();

                        }
                        return true;
                    }
                });


    }

    private void assignView() {
        slipList = (ListView) findViewById(R.id.slipList);
        txtDataNotFoundForSlip = (TextView) findViewById(R.id.txtDataNotFoundForSlip);
        pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);
    }

    private void prepareCustomerSlipList(String customerId, String enterBy) {

        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("enterby", enterBy);

            GsonRequest<SlipListResponseData> slipListResquest = new GsonRequest<>(Request.Method.POST, Constant.GET_CUSTOMER_PENDING_ORDER_LIST, jo.toString(), SlipListResponseData.class,
                    new Response.Listener<SlipListResponseData>() {
                        @Override
                        public void onResponse(@NonNull SlipListResponseData response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    slipListResponseData = response;
                                    if (slipListResponseData.getCustomerOrderList() != null && slipListResponseData.getCustomerOrderList().size() > 0) {
                                        txtDataNotFoundForSlip.setVisibility(View.GONE);
                                        slipList.setVisibility(View.VISIBLE);
                                        customerOrderListData = slipListResponseData.getCustomerOrderList();
                                        slipListAdapter = new SlipListAdapter(CustomerSlipListActivity.this, customerOrderListData);
                                        slipList.setAdapter(slipListAdapter);

                                    } else {
                                        slipList.setVisibility(View.GONE);
                                        txtDataNotFoundForSlip.setText("Data Not Found");
                                        txtDataNotFoundForSlip.setVisibility(View.VISIBLE);
                                    }
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

        } catch (Exception e) {
            hideBusyProgress();
            Log.e(CustomerSlipListActivity.class.getName(), e.getMessage());
        }
    }

    private void deleteCustomerSlip(final String slipno, final int position) {

        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("Slipno", slipno);

            GsonRequest<DeleteSlipResponseData> barcodeScanResquest = new GsonRequest<>(Request.Method.POST, Constant.DELETE_SLIP, jo.toString(), DeleteSlipResponseData.class,
                    new Response.Listener<DeleteSlipResponseData>() {
                        @Override
                        public void onResponse(@NonNull DeleteSlipResponseData response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                                Log.e(TAG, response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    showToast("Slip "+slipno+" deleted successfully");
                                    customerOrderListData.remove(position);
                                    slipListAdapter.notifyDataSetChanged();


                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideBusyProgress();
                    showToast(error.getMessage());
                    Log.e(TAG, error.getMessage());
                }
            });
            barcodeScanResquest.setRetryPolicy(Application.getDefaultRetryPolice());
            barcodeScanResquest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(barcodeScanResquest, "barcodeScanResquest");

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

    private void submitCustomerSlip(final String slipno, final int position) {

        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("Slipno", slipno);

            GsonRequest<SubmitSlipResponseData> submitSlipResquest = new GsonRequest<>(Request.Method.POST, Constant.SUBMIT_SLIP, jo.toString(), SubmitSlipResponseData.class,
                    new Response.Listener<SubmitSlipResponseData>() {
                        @Override
                        public void onResponse(@NonNull SubmitSlipResponseData response) {
                            hideBusyProgress();

                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                                Log.e(TAG, response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    showToast("Slip "+slipno+" submitted successfully");
                                    customerOrderListData.remove(position);
                                    slipListAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideBusyProgress();
                    showToast(error.getMessage());
                    Log.e(TAG, error.getMessage());
                }
            });
            submitSlipResquest.setRetryPolicy(Application.getDefaultRetryPolice());
            submitSlipResquest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(submitSlipResquest, "submitSlipResquest");

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
