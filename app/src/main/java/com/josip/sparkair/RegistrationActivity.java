package com.josip.sparkair;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    public EditText etName;
    public EditText etSurname;
    public EditText etUsername;
    public EditText etPassword;
    public Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etName = (EditText) findViewById(R.id.etName);
        etSurname = (EditText) findViewById(R.id.etSurname);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btSave = (Button) findViewById(R.id.btSaveToDatabase);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(etName) || isEmpty(etPassword) || isEmpty(etSurname) || isEmpty(etUsername)) {
                    Toast.makeText(getApplicationContext(), "Molimo da popunite SVA polja!", Toast.LENGTH_SHORT).show();
                }
                else {
                    //spremi u bazu
                    DatabaseHelper myDb;
                    myDb = new DatabaseHelper(getApplicationContext());
                    boolean isInserted = myDb.insertUser(etName.getText().toString(),etSurname.getText().toString(), etUsername.getText().toString(), etPassword.getText().toString());

                    if(isInserted == true)
                    {
                        etName.setText("");
                        etSurname.setText("");
                        etUsername.setText("");
                        etPassword.setText("");
                        Toast.makeText(getApplicationContext(), "Spremljeno u bazu!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Greska pri insertu u bazu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean isEmpty(EditText myEditText) {
        return myEditText.getText().toString().trim().length() == 0;
    }
}
