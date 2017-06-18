package com.josip.sparkair;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.josip.sparkair.Util.Util;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        TextView tv_profileCurrentUserNameID = (TextView) findViewById(R.id.tv_profileCurrentUserNameID);
        TextView tv_profileCurrentUserType = (TextView) findViewById(R.id.tv_profileCurrentUserType);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



    }
}
