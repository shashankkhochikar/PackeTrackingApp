package com.example.vinayak.pakingmaster;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vinayak.pakingmaster.baseclasses.BaseActivity;
import com.example.vinayak.pakingmaster.pojo.LogoutResponseData;
import com.example.vinayak.pakingmaster.pojo.SlipDetailsResponseData;
import com.example.vinayak.pakingmaster.utils.Constant;
import com.example.vinayak.pakingmaster.volley.GsonRequest;

import org.json.JSONObject;
import org.w3c.dom.Text;

import static com.example.vinayak.pakingmaster.utils.Constant.userId;

public class DashboardActivity extends BaseActivity {

    private static final String TAG = DashboardActivity.class.getName();

    TextView changePassword;
    TextView linkForApp;
    TextView linkForWeb;
    LinearLayout newItem;
    LinearLayout modifyItem;
    LinearLayout viewItem;
    LinearLayout submitItem;
    LinearLayout deleteItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setTitle("Dashboard");
        assignView();
        init();

    }

    private void init() {

        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, CustomerDetailsActivity.class);
                intent.putExtra("slipNumber", "");
                intent.putExtra("modeOfOpration", "");
                startActivity(intent);

            }
        });
        modifyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(DashboardActivity.this,PakingListActivity.class);
                startActivity(intent);*/
            }
        });
        viewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, CustomerSlipListActivity.class);
                intent.putExtra("customerId", userId);
                intent.putExtra("enterBy", Constant.userName);
                intent.putExtra("modeOfOpration", "1");
                startActivity(intent);
            }
        });
        submitItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, CustomerSlipListActivity.class);
                intent.putExtra("customerId", userId);
                intent.putExtra("enterBy", Constant.userName);
                intent.putExtra("modeOfOpration", "2");
                startActivity(intent);
            }
        });
        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, CustomerSlipListActivity.class);
                intent.putExtra("customerId", userId);
                intent.putExtra("enterBy", Constant.userName);
                intent.putExtra("modeOfOpration", "3");
                startActivity(intent);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        linkForWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,WebViewActivity.class);
                startActivity(intent);
            }
        });


    }

    private void assignView() {

        newItem = (LinearLayout) findViewById(R.id.linearNewItem);
        modifyItem = (LinearLayout) findViewById(R.id.linearModifyItem);
        viewItem = (LinearLayout) findViewById(R.id.linearViewItem);
        submitItem = (LinearLayout) findViewById(R.id.linearSubmitItem);
        deleteItem = (LinearLayout) findViewById(R.id.linearDeleteItem);
        changePassword = (TextView)findViewById(R.id.changePassword);
        linkForApp = (TextView)findViewById(R.id.linkForApp);
        linkForWeb = (TextView)findViewById(R.id.linkForWeb);
    }

    @Override
    public void onBackPressed() {

        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("id", userId);
            jo.put("Loginstatus", "0");

            GsonRequest<LogoutResponseData> barcodeScanResquest = new GsonRequest<>(Request.Method.POST, Constant.LOGOUT, jo.toString(), LogoutResponseData.class,
                    new Response.Listener<LogoutResponseData>() {
                        @Override
                        public void onResponse(@NonNull LogoutResponseData response) {
                            hideBusyProgress();

                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                                Log.e(TAG, response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    showToast(response.getMessage().toString());
                                    finish();
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
            hideBusyProgress();
            Log.e(TAG, e.getMessage());
        }
    }
}
