package com.example.udemylearnclubcrm.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class CrmContentProvider extends ContentProvider {
    CrmDbHelper crmDbHelper;

    private static final int USERS_ALL = 1;
    private static final int USER_ONE = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(CRMContract.AUTHORITY, CRMContract.PATH_USERS, USERS_ALL);
        uriMatcher.addURI(CRMContract.AUTHORITY, CRMContract.PATH_USERS + "/#", USER_ONE);
    }

    @Override
    public boolean onCreate() {
        crmDbHelper = new CrmDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = crmDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = uriMatcher.match(uri);

        switch (match) {
            case USERS_ALL:
                cursor = db.query(CRMContract.usersConf.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case USER_ONE:
                selection = CRMContract.usersConf._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(CRMContract.usersConf.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Can't query incorrect URI: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String fName = values.getAsString(CRMContract.usersConf.KEY_F_NAME);
        if (fName == null) {
            throw new IllegalArgumentException("First Name must be set");
        }

        String lName = values.getAsString(CRMContract.usersConf.KEY_L_NAME);
        if (lName == null) {
            throw new IllegalArgumentException("Last Name must be set");
        }

        Integer gender = values.getAsInteger(CRMContract.usersConf.KEY_GENDER);
        if (gender == null || gender < CRMContract.usersConf.GENDER_UNKNOWN || gender > CRMContract.usersConf.GENDER_FEMALE) {
            throw new IllegalArgumentException("Illegal gender value");
        }

        String group = values.getAsString(CRMContract.usersConf.KEY_GROUP);
        if (group == null) {
            throw new IllegalArgumentException("Group must be set");
        }

        SQLiteDatabase db = crmDbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        switch (match) {
            case USERS_ALL:
                long id = db.insert(CRMContract.usersConf.TABLE_NAME, null, values);
                if (id == -1) {
                    Log.e("InsertMethod", "Insert failed for " + uri);

                    return null;
                }

                getContext().getContentResolver().notifyChange(uri, null);

                return ContentUris.withAppendedId(uri, id);

            default:
                throw new IllegalArgumentException("Insert failed for " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = crmDbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        int deletedRawCount = 0;

        switch (match) {
            case USERS_ALL:
                deletedRawCount = db.delete(CRMContract.usersConf.TABLE_NAME, null, null);
                break;

            case USER_ONE:
                selection = CRMContract.usersConf._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                deletedRawCount = db.delete(CRMContract.usersConf.TABLE_NAME, selection, selectionArgs);
                break;


            default:
                throw new IllegalArgumentException("Can't delete incorrect URI: " + uri);
        }

        if (deletedRawCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deletedRawCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(CRMContract.usersConf.KEY_F_NAME)) {
            String fName = values.getAsString(CRMContract.usersConf.KEY_F_NAME);
            if (fName == null) {
                throw new IllegalArgumentException("First Name must be set");
            }
        }

        if (values.containsKey(CRMContract.usersConf.KEY_L_NAME)) {
            String lName = values.getAsString(CRMContract.usersConf.KEY_L_NAME);
            if (lName == null) {
                throw new IllegalArgumentException("Last Name must be set");
            }
        }

        if (values.containsKey(CRMContract.usersConf.KEY_GENDER)) {
            Integer gender = values.getAsInteger(CRMContract.usersConf.KEY_GENDER);
            if (gender == null || gender < CRMContract.usersConf.GENDER_UNKNOWN || gender > CRMContract.usersConf.GENDER_FEMALE) {
                throw new IllegalArgumentException("Illegal gender value");
            }
        }

        if (values.containsKey(CRMContract.usersConf.KEY_GROUP)) {
            String group = values.getAsString(CRMContract.usersConf.KEY_GROUP);
            if (group == null) {
                throw new IllegalArgumentException("Group must be set");
            }
        }

        SQLiteDatabase db = crmDbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        int updatedRawCount = 0;

        switch (match) {
            case USER_ONE:
                selection = CRMContract.usersConf._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            case USERS_ALL:
                updatedRawCount = db.update(CRMContract.usersConf.TABLE_NAME, values, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Can't update incorrect URI: " + uri);
        }

        if (updatedRawCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updatedRawCount;
    }

    @Override
    public String getType(Uri uri) {

        int match = uriMatcher.match(uri);

        switch (match) {
            case USERS_ALL:
                return CRMContract.usersConf.TYPE_MULTI;

            case USER_ONE:
                return CRMContract.usersConf.TYPE_SINGLE;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
