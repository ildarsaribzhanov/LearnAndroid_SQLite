package com.example.udemylearnclubcrm;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.udemylearnclubcrm.data.CRMContract.usersConf;
import com.example.udemylearnclubcrm.data.Gender;

public class UserCursorAdapter extends CursorAdapter {
    public UserCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView userId = view.findViewById(R.id.userId);
        TextView userFName = view.findViewById(R.id.userFName);
        TextView userLName = view.findViewById(R.id.userLName);
        TextView userGender = view.findViewById(R.id.userGender);
        TextView userGroup = view.findViewById(R.id.userGroup);

        Integer userIdVal = cursor.getInt(cursor.getColumnIndexOrThrow(usersConf.KEY_ID));
        String userFNameVal = cursor.getString(cursor.getColumnIndexOrThrow(usersConf.KEY_F_NAME));
        String userLNameVal = cursor.getString(cursor.getColumnIndexOrThrow(usersConf.KEY_L_NAME));
        Integer userGenderVal = cursor.getInt(cursor.getColumnIndexOrThrow(usersConf.KEY_GENDER));
        String userGroupVal = cursor.getString(cursor.getColumnIndexOrThrow(usersConf.KEY_GROUP));

        Gender gender = new Gender(userGenderVal);

        userId.setText(userIdVal.toString());
        userFName.setText(userFNameVal);
        userLName.setText(userLNameVal);
        userGender.setText(gender.toString());
        userGroup.setText(userGroupVal);
    }
}
