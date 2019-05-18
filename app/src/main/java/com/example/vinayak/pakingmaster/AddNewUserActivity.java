package com.example.vinayak.pakingmaster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewUserActivity extends AppCompatActivity {

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

        String newUserName = addNewUserName.getText().toString().trim();
        String newUserPassword = addNewUserPassword.getText().toString().trim();
        String newConfirmPassword = addNewUserConfirmPassword.getText().toString().trim();

        if (newConfirmPassword.equals(newUserPassword)) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Password and confirm password doesn't match", Toast.LENGTH_LONG).show();
        }


    }

    private void assign() {
        addNewUserName = (EditText) findViewById(R.id.addNewUserName);
        addNewUserPassword = (EditText) findViewById(R.id.addNewUserPassword);
        addNewUserConfirmPassword = (EditText) findViewById(R.id.addNewUserConfirmPassword);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }
}
