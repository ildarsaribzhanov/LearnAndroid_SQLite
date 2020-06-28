package com.example.udemylearnclubcrm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    private List genderArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        genderSpinner = findViewById(R.id.genderSpinner);
        group = findViewById(R.id.group);

        genderArray = new ArrayList();
        genderArray.add("Unknown");
        genderArray.add("Male");
        genderArray.add("Female");

        genderAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genderArray);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        genderSpinner.setAdapter(genderAdapter);
    }
}