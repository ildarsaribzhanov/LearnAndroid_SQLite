package com.example.udemylearnclubcrm.data;

import android.provider.BaseColumns;

public final class CRMContract {
    public static final String DB_NAME = "crm_db";
    public static final int DB_VER = 1;

    private CRMContract() {
    }

    public static final class usersConf implements BaseColumns {
        public static final String TABLE_NAME = "users";

        public static final String KEY_ID = BaseColumns._ID;
        public static final String KEY_F_NAME = "firstName";
        public static final String KEY_L_NAME = "lastName";
        public static final String KEY_GENDER = "gender";
        public static final String KEY_GROUP = "group_name";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
    }
}
