package com.example.udemylearnclubcrm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.udemylearnclubcrm.data.CRMContract.usersConf;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int USER_LOADER = 123;
    UserCursorAdapter userCursorAdapter;
    ListView userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addUserBtn = findViewById(R.id.floatingActionButton);

        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });

        userListView = findViewById(R.id.userListView);
        userCursorAdapter = new UserCursorAdapter(this, null, true);
        userListView.setAdapter(userCursorAdapter);

        LoaderManager.getInstance(this).initLoader(USER_LOADER, null, this);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
                Uri needUserUri = ContentUris.withAppendedId(usersConf.CONTENT_URI, id);

                intent.setData(needUserUri);

                startActivity(intent);
            }
        });

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

        CursorLoader cursorLoader = new CursorLoader(this, usersConf.CONTENT_URI, projection, null, null, usersConf.KEY_ID + " DESC");

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        userCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        userCursorAdapter.swapCursor(null);
    }
}