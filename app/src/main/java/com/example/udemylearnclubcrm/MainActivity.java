package com.example.udemylearnclubcrm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.udemylearnclubcrm.data.CRMContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    ListView userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addUserBtn = findViewById(R.id.floatingActionButton);

        userListView = findViewById(R.id.userListView);

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

        UserCursorAdapter adapter = new UserCursorAdapter(this, cursor, true);

        userListView.setAdapter(adapter);
    }
}