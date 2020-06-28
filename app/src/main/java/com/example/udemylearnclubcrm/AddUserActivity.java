package com.example.udemylearnclubcrm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AddUserActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private Spinner genderSpinner;
    private int gender = 0;
    private EditText group;

    private ArrayAdapter genderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        genderSpinner = findViewById(R.id.genderSpinner);
        group = findViewById(R.id.group);

        genderAdapter = ArrayAdapter.createFromResource(this, R.array.array_gender, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        genderSpinner.setAdapter(genderAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);

                if (TextUtils.isEmpty(selected)) {
                    return;
                }

                switch (selected) {
                    case "Male":
                        gender = 1;
                        break;

                    case "Female":
                        gender = 2;
                        break;

                    default:
                        gender = 0;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gender = 0;
            }
        });
    }
}