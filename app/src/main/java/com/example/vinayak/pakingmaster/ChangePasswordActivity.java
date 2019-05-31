package com.example.vinayak.pakingmaster;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vinayak.pakingmaster.baseclasses.BaseActivity;
import com.example.vinayak.pakingmaster.pojo.ChangePasswordResponseData;
import com.example.vinayak.pakingmaster.pojo.LogoutResponseData;
import com.example.vinayak.pakingmaster.utils.Constant;
import com.example.vinayak.pakingmaster.volley.GsonRequest;

import org.json.JSONObject;

import static com.example.vinayak.pakingmaster.utils.Constant.userId;

public class ChangePasswordActivity extends BaseActivity {

    private static final String TAG = ChangePasswordActivity.class.getName();
    EditText password;
    EditText confirmPassword;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assignView();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFieldValidation() == true)
                    if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                        String password = confirmPassword.getText().toString();

                        try {
                            showBusyProgress();
                            JSONObject jo = new JSONObject();
                            jo.put("id", userId);
                            jo.put("password", password);

                            GsonRequest<ChangePasswordResponseData> barcodeScanResquest = new GsonRequest<>(Request.Method.POST, Constant.CHANGE_PASSWORD, jo.toString(), ChangePasswordResponseData.class,
                                    new Response.Listener<ChangePasswordResponseData>() {
                                        @Override
                                        public void onResponse(@NonNull ChangePasswordResponseData response) {
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
                    }else{
                        showToast("Password dose not match");
                    }
            }
        });
    }

    private boolean checkFieldValidation() {

        String str_password = password.getText().toString();
        String str_confirmPassword = confirmPassword.getText().toString().trim();

        if (str_password.isEmpty() || str_password == null) {
            showToast("Please fill password field");
            return false;
        } else if (str_confirmPassword.isEmpty() || str_confirmPassword == null) {
            showToast("Please fill confirm password field");
            return false;
        } else
            return true;
    }

    private void assignView() {
        password = (EditText) findViewById(R.id.userPassword);
        confirmPassword = (EditText) findViewById(R.id.userConfirmPassword);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

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
