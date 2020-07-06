package com.example.udemylearnclubcrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.udemylearnclubcrm.data.CRMContract;

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

        bindElements();
        initSpinner();


        Intent intent = getIntent();
        Uri currentUserUri = intent.getData();

        if (currentUserUri == null) {
            setTitle("Add New User");
        } else {
            setTitle("Edit User");
        }
    }

    private void bindElements() {
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        genderSpinner = findViewById(R.id.genderSpinner);
        group = findViewById(R.id.group);
    }

    private void initSpinner() {

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
                        gender = CRMContract.usersConf.GENDER_MALE;
                        break;

                    case "Female":
                        gender = CRMContract.usersConf.GENDER_FEMALE;
                        break;

                    default:
                        gender = CRMContract.usersConf.GENDER_UNKNOWN;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gender = 0;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_user:
                insertUser();
                return true;

            case R.id.del_user:
                return true;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertUser() {
        String firstNameVal = firstName.getText().toString().trim();
        String lastNameVal = lastName.getText().toString().trim();
        String groupVal = group.getText().toString().trim();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CRMContract.usersConf.KEY_F_NAME, firstNameVal);
        contentValues.put(CRMContract.usersConf.KEY_L_NAME, lastNameVal);
        contentValues.put(CRMContract.usersConf.KEY_GENDER, gender);
        contentValues.put(CRMContract.usersConf.KEY_GROUP, groupVal);

        ContentResolver contentResolver = getContentResolver();
        Uri uri = contentResolver.insert(CRMContract.usersConf.CONTENT_URI, contentValues);

        if (uri == null) {
            Toast.makeText(this, "Insert failed", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show();
    }
}