package com.example.udemylearnclubcrm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.udemylearnclubcrm.data.CRMContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    TextView dataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addUserBtn = findViewById(R.id.floatingActionButton);

        dataTextView = findViewById(R.id.dataTextView);

        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewData();
    }

    private void viewData() {
        String[] projection = {
                CRMContract.usersConf.KEY_ID,
                CRMContract.usersConf.KEY_F_NAME,
                CRMContract.usersConf.KEY_L_NAME,
                CRMContract.usersConf.KEY_GENDER,
                CRMContract.usersConf.KEY_GROUP,
        };

        Cursor cursor = getContentResolver().query(CRMContract.usersConf.CONTENT_URI, projection, null, null, null);

        dataTextView.setText("All users\n\n");
        dataTextView.append(CRMContract.usersConf.KEY_ID + " " +
                CRMContract.usersConf.KEY_F_NAME + " " +
                CRMContract.usersConf.KEY_L_NAME + " " +
                CRMContract.usersConf.KEY_GENDER + " " +
                CRMContract.usersConf.KEY_GROUP + "\n");

        int idIndex = cursor.getColumnIndex(CRMContract.usersConf.KEY_ID);
        int fNameIndex = cursor.getColumnIndex(CRMContract.usersConf.KEY_F_NAME);
        int lNameIndex = cursor.getColumnIndex(CRMContract.usersConf.KEY_L_NAME);
        int genderIndex = cursor.getColumnIndex(CRMContract.usersConf.KEY_GENDER);
        int groupIndex = cursor.getColumnIndex(CRMContract.usersConf.KEY_GROUP);

        while (cursor.moveToNext()) {
            int currentId = cursor.getInt(idIndex);
            String currentFName = cursor.getString(fNameIndex);
            String currentLName = cursor.getString(lNameIndex);
            int currentGender = cursor.getInt(genderIndex);
            String currentGroup = cursor.getString(groupIndex);

            dataTextView.append(currentId + " " +
                    currentFName + " " +
                    currentLName + " " +
                    currentGender + " " +
                    currentGroup + "\n");
        }

        cursor.close();
    }
}