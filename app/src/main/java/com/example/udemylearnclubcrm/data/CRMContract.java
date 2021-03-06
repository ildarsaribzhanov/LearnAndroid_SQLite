package com.example.udemylearnclubcrm.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class CRMContract {
    private CRMContract() {
    }

    public static final String DB_NAME = "crm_db";
    public static final int DB_VER = 2;

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.example.udemylearnclubcrm";
    public static final String PATH_USERS = "users";

    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);

    public static final class usersConf implements BaseColumns {
        public static final String TABLE_NAME = "users";

        public static final String KEY_ID = BaseColumns._ID;
        public static final String KEY_F_NAME = "firstName";
        public static final String KEY_L_NAME = "lastName";
        public static final String KEY_GENDER = "gender";
        public static final String KEY_GROUP = "groupName";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_USERS);

        public static final String TYPE_MULTI = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "." + PATH_USERS;
        public static final String TYPE_SINGLE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "." + AUTHORITY + "." + PATH_USERS;
    }
}
