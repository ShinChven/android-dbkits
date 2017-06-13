package com.github.shinchven.androiddbkits;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.support.annotation.Nullable;
import com.github.shinchven.dbkits.ContentValueWriter;
import com.github.shinchven.dbkits.CursorReader;
import com.github.shinchven.dbkits.TableKit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


/**
 * Created by shinc on 2017/6/10.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "db.sqlite";
    private static final int DB_VERSION = 8;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            TableKit.createTable(User.class, db, new String[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void moveDataBaseOut(Context context) {
        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            copyFile(new File(db.getPath()), new File(Environment.getDataDirectory() + "data.sqlite"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void copyFile(File src, File dst) throws IOException {
        FileInputStream var2 = new FileInputStream(src);
        FileOutputStream var3 = new FileOutputStream(dst);
        byte[] var4 = new byte[1024];

        int var5;
        while ((var5 = var2.read(var4)) > 0) {
            var3.write(var4, 0, var5);
        }

        var2.close();
        var3.close();
    }

    public static long addUser(Context context, User user) {
        if (user == null) {
            return 0;
        }

        ContentValues values = ContentValueWriter.objectToContentValues(user, new String[]{"_id"});
        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        long result = 0;
        try {
            result = db.insert("user", "_id", values);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (db != null) {
                try {
                    db.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static @Nullable
    List<User> getUsers(Context context) {
        List<User> users = null;

        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = helper.getReadableDatabase();
            cursor = db.query("user", null, null, null, null, null, null);
            users = CursorReader.read(cursor, User.class, new String[]{});
            while (cursor.moveToNext()) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                try {
                    db.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return users;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        TableKit.updateTable(User.class, db, new String[]{});
    }
}
