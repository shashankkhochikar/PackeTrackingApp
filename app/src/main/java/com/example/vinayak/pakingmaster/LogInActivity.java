package com.example.vinayak.pakingmaster;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vinayak.pakingmaster.baseclasses.BaseActivity;
import com.example.vinayak.pakingmaster.pojo.UserLoginResponseData;
import com.example.vinayak.pakingmaster.utils.Constant;
import com.example.vinayak.pakingmaster.volley.GsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LogInActivity extends BaseActivity {

    private EditText mUsername;
    private EditText mPassword;
    private Button mBtnLogin;
    private TextView txtNewUser;

    String isUserLogged = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        /*getSupportActionBar().setTitle("Login");*/
        assignViews();
        init();
    }

    private void init() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOnline()) {

                    String userName = mUsername.getText().toString().trim();
                    String password = mPassword.getText().toString().trim();
                    isUserLogged = "1";
                    doLogin(userName, password, isUserLogged);

                } else {
                    showToast("Internet Connection Not Available");
                }
            }
        });

        txtNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, AddNewUserActivity.class));
            }
        });
    }

    private void doLogin(String userName, String password, String isUserLogged) {
        showBusyProgress();
        JSONObject jo = new JSONObject();

        try {
            jo.put("Username", userName);
            jo.put("Password", password);
            jo.put("Loginstatus", isUserLogged);
        } catch (JSONException e) {
            Log.e(LogInActivity.class.getName(), e.getMessage().toString());
            return;
        }
        GsonRequest<UserLoginResponseData> userLoginRequest = new GsonRequest<>(Request.Method.POST, Constant.CHECK_LOGIN_USER, jo.toString(), UserLoginResponseData.class,
                new Response.Listener<UserLoginResponseData>() {
                    @Override
                    public void onResponse(@NonNull UserLoginResponseData response) {
                        Log.e(LogInActivity.class.getName(), response.getMessage());
                        hideBusyProgress();
                        if (response.getError() != null) {
                            showToast(response.getError().getErrorMessage());
                        } else {


                            /*if (response.getMessage().equals("Success")) {*/
                                Constant.userId = response.getUser().getId();
                                Constant.userName = response.getUser().getUsername();
                                startActivity(new Intent(LogInActivity.this, DashboardActivity.class));
                                finish();
                            /*} else {
                                showToast(response.getMessage());
                            }*/
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideBusyProgress();
                showToast(error.getMessage());
            }
        });
        userLoginRequest.setRetryPolicy(Application.getDefaultRetryPolice());
        userLoginRequest.setShouldCache(false);
        Application.getInstance().addToRequestQueue(userLoginRequest, "login_requests");

    }

    private void assignViews() {
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        txtNewUser = (TextView) findViewById(R.id.txtNewUser);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}
