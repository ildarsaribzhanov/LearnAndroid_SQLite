package com.example.udemylearnclubcrm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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

import com.example.udemylearnclubcrm.data.CRMContract.usersConf;
import com.example.udemylearnclubcrm.data.Gender;

public class AddUserActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EDIT_USER_LOADER = 875;
    private EditText firstName;
    private EditText lastName;
    private Spinner genderSpinner;
    private int gender = 0;
    private EditText group;
    Uri currentUserUri;

    private ArrayAdapter genderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        bindElements();
        initSpinner();

        Intent intent = getIntent();
        currentUserUri = intent.getData();

        if (currentUserUri == null) {
            setTitle("Add New User");
        } else {
            setTitle("Edit User");
            LoaderManager.getInstance(this).initLoader(EDIT_USER_LOADER, null, this);
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
                        gender = usersConf.GENDER_MALE;
                        break;

                    case "Female":
                        gender = usersConf.GENDER_FEMALE;
                        break;

                    default:
                        gender = usersConf.GENDER_UNKNOWN;
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
                saveUser();
                return true;

            case R.id.del_user:
                return true;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveUser() {
        String firstNameVal = firstName.getText().toString().trim();
        String lastNameVal = lastName.getText().toString().trim();
        String groupVal = group.getText().toString().trim();

        ContentValues contentValues = new ContentValues();
        contentValues.put(usersConf.KEY_F_NAME, firstNameVal);
        contentValues.put(usersConf.KEY_L_NAME, lastNameVal);
        contentValues.put(usersConf.KEY_GENDER, gender);
        contentValues.put(usersConf.KEY_GROUP, groupVal);

        if (currentUserUri == null) {
            createUser(contentValues);
            return;
        }

        updateUser(contentValues);
    }

    private void createUser(ContentValues contentValues) {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = contentResolver.insert(usersConf.CONTENT_URI, contentValues);

        if (uri == null) {
            Toast.makeText(this, "Insert failed", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show();
    }

    private void updateUser(ContentValues contentValues) {
        int updateCount = getContentResolver().update(currentUserUri, contentValues, null, null);

        if (updateCount > 0) {
            Toast.makeText(this, "User update", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "Update user failed", Toast.LENGTH_LONG).show();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                usersConf.KEY_ID,
                usersConf.KEY_F_NAME,
                usersConf.KEY_L_NAME,
                usersConf.KEY_GENDER,
                usersConf.KEY_GROUP,
        };

        return new CursorLoader(this, currentUserUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            int colFNameInd = data.getColumnIndex(usersConf.KEY_F_NAME);
            int colLNameInd = data.getColumnIndex(usersConf.KEY_L_NAME);
            int colGenderInd = data.getColumnIndex(usersConf.KEY_GENDER);
            int colGroupInd = data.getColumnIndex(usersConf.KEY_GROUP);

            String firstNameVal = data.getString(colFNameInd);
            String lastNameVal = data.getString(colLNameInd);
            Gender genderVal = new Gender(data.getInt(colGenderInd));
            String groupNameVal = data.getString(colGroupInd);

            firstName.setText(firstNameVal);
            lastName.setText(lastNameVal);
            genderSpinner.setSelection(genderVal.toInt());
            group.setText(groupNameVal);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}