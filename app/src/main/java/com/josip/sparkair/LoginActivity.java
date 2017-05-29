package com.josip.sparkair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public EditText etUsername;
    public EditText etPassword;
    public Button btLogin;
    public TextView tvNotAMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        etUsername = (EditText) findViewById(R.id.login_etUsername);
        etPassword = (EditText) findViewById(R.id.login_etPassword);
        btLogin = (Button) findViewById(R.id.login_btLogin);
        tvNotAMember = (TextView) findViewById(R.id.login_tvNotAMeber);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 login();
            }
        });
    }

    //Ukoliko korisnik izabere SignUp opciju otvaramo RegistrationActivity
    public void onClick(View v){
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
    }

    private boolean login()
    {
        if (isEmpty(etUsername) || isEmpty(etPassword)) {
            return false;
        }

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        DatabaseHelper myDb;
        myDb = new DatabaseHelper(this);
        User user = myDb.getUserInfo(username, password);

        if(user.getType() == -1) {
            Toast.makeText(getApplicationContext(), "Neuspjesan login", Toast.LENGTH_SHORT).show();
            etPassword.setText("");
            return false;
        }
        else
        {
            //uspjesno loginran korisnik
            Toast.makeText(getApplicationContext(), Integer.toString(user.getUserID()) + "," + user.getName() + "," + user.getSurname(), Toast.LENGTH_SHORT).show();
            etUsername.setText("");
            etPassword.setText("");

            //Spremanje korisnika u sharedPreferences
            saveInfo(username, password);


            //*************************************************************************************
            return true;
        }
    }

    private boolean isEmpty(EditText myEditText) {
        return myEditText.getText().toString().trim().length() == 0;
    }

    //Spremanje informacija o trenutnom useru u shared preferences
    public void saveInfo(String username, String password) {
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);

        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();

        Toast.makeText(getApplicationContext(), "Saved in SP", Toast.LENGTH_SHORT).show();
    }

    //Vraca trenutnog usera
    public void getCurrentUser() {
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);

        String username = settings.getString("username", "guest");
        String password = settings.getString("password", "guest");


        Toast.makeText(getApplicationContext(), username + " " + password, Toast.LENGTH_SHORT).show();

    }


}