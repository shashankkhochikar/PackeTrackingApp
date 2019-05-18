package com.example.vinayak.pakingmaster;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vinayak.pakingmaster.baseclasses.BaseActivity;
import com.example.vinayak.pakingmaster.pojo.RegisterUserResponse;
import com.example.vinayak.pakingmaster.utils.Constant;
import com.example.vinayak.pakingmaster.volley.GsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class AddNewUserActivity extends BaseActivity {

    EditText addNewUserName;
    EditText addNewUserPassword;
    EditText addNewUserConfirmPassword;
    Button btnSubmit;

    String userType = "user";
    String str_UserName = "";
    String str_Password = "";
    String str_ConfirmPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);
        getSupportActionBar().setTitle("New User");
        assign();
        init();

    }

    private void init() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_UserName = addNewUserName.getText().toString().trim();
                str_Password = addNewUserPassword.getText().toString().trim();
                str_ConfirmPassword = addNewUserConfirmPassword.getText().toString().trim();

                if (!((str_UserName == null) || (str_UserName.isEmpty())) && !((str_Password == null) || (str_Password.isEmpty())) && !((str_ConfirmPassword == null) || (str_ConfirmPassword.isEmpty()))) {
                    submitDetails();
                } else {
                    Toast.makeText(AddNewUserActivity.this, "Please fill all details", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void submitDetails() {
        if (isOnline()) {
            String newUserName = addNewUserName.getText().toString().trim();
            String newUserPassword = addNewUserPassword.getText().toString().trim();
            String newConfirmPassword = addNewUserConfirmPassword.getText().toString().trim();

            if (newConfirmPassword.equals(newUserPassword)) {
                //Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show();
                doRegister(newUserName, newConfirmPassword, userType);
            } else {
                Toast.makeText(this, "Password and confirm password doesn't match", Toast.LENGTH_LONG).show();
            }
        } else {
            showToast("Internet Connection Not Available");
        }


    }

    private void assign() {
        addNewUserName = (EditText) findViewById(R.id.addNewUserName);
        addNewUserPassword = (EditText) findViewById(R.id.addNewUserPassword);
        addNewUserConfirmPassword = (EditText) findViewById(R.id.addNewUserConfirmPassword);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }
    private void doRegister(String name, String password, String usertype) {
        //showBusyProgress();
        JSONObject jo = new JSONObject();
        try {
            jo.put("Username", name);
            jo.put("Password", password);
            jo.put("Usertype", usertype);
        } catch (JSONException e) {
            Log.e(AddNewUserActivity.class.getName(), e.getMessage().toString());
            return;
        }
        GsonRequest<RegisterUserResponse> userLoginEmailRequest = new GsonRequest<>(Request.Method.POST, Constant.ADD_NEW_USER, jo.toString(), RegisterUserResponse.class,
                new Response.Listener<RegisterUserResponse>() {
                    @Override
                    public void onResponse(@NonNull RegisterUserResponse response) {
                        Log.e(AddNewUserActivity.class.getName(),response.toString());
                        //hideBusyProgress();
                        if (response.getError() != null) {
                            showToast(response.getError().getErrorMessage());
                        } else {
                            showToast(response.getMessage());
                            finish();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // hideBusyProgress();
                showToast(error.getMessage());
                Log.e(AddNewUserActivity.class.getName(),error.getMessage());
            }
        });
        userLoginEmailRequest.setRetryPolicy(Application.getDefaultRetryPolice());
        userLoginEmailRequest.setShouldCache(false);
        Application.getInstance().addToRequestQueue(userLoginEmailRequest, "login_requests");
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
