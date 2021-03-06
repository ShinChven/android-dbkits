package com.github.shinchven.dbkits;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by ShinChven on 2017/6/11.
 */
public class TableKit {
    public static void createTable(Class type, SQLiteDatabase db, String... ignoredFieldNames) throws Exception {
        String tableName = type.getSimpleName();
        Field[] fields = type.getDeclaredFields();
        List<String> columnsSQL = new ArrayList<>();
        List<String> fieldsToIgnore = Arrays.asList(ignoredFieldNames);
        for (Field field : fields) {
            field.setAccessible(true);

            if (fieldsToIgnore.contains(field.getName()))
                continue;

            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            if (field.getName().equals("_id")) {
                continue;
            }

            StringBuilder sb = new StringBuilder(field.getName()).append(" ");

            if (field.getType() == Integer.class || field.getType().getName().equals("int")) {
                sb.append("INTEGER");
            } else if (field.getType() == Double.class || field.getType().getName().equals("double")) {
                sb.append("REAL");
            } else if (field.getType() == Float.class || field.getType().getName().equals("float")) {
                sb.append("REAL");
            } else if (field.getType() == Long.class || field.getType().getName().equals("long")) {
                sb.append("LONG");
            } else if (field.getType() == String.class) {
                sb.append("TEXT");
            } else if (field.getType() == Boolean.class || field.getType().getName().equals("boolean")) {
                sb.append("INTEGER");
            } else if (field.getType() == Date.class) {
                sb.append("LONG");
            } else if (field.getType() == Uri.class || field.getType() == File.class) {
                sb.append("TEXT");
            } else {
                Log.i("creating table", "field: " + field.getName() + " not filled");
                continue;
            }
            columnsSQL.add(sb.toString());
        }

        StringBuilder sql = new StringBuilder();
        sql.append("create table ").append(tableName).append("( _id INTEGER PRIMARY KEY AUTOINCREMENT,");

        for (int i = 0; i < columnsSQL.size(); i++) {
            sql.append(columnsSQL.get(i));
            if (i < columnsSQL.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(" )");

        Log.i("sql", sql.toString());

        db.execSQL(sql.toString());

    }

    public static void updateTable(Class type, SQLiteDatabase db, String... ignoredFieldNames) {

        String tableName = type.getSimpleName();
        Field[] fields = type.getDeclaredFields();
        List<String> columnsSQL = new ArrayList<>();
        List<String> fieldsToIgnore = Arrays.asList(ignoredFieldNames);
        for (Field field : fields) {
            field.setAccessible(true);

            if (fieldsToIgnore.contains(field.getName()))
                continue;

            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            if (field.getName().equals("_id")) {
                continue;
            }

            StringBuilder sb = new StringBuilder("ALTER TABLE ").append(tableName).append(" ADD ").append(field.getName());

            if (field.getType() == Integer.class || field.getType().getName().equals("int")) {
                sb.append(" INTEGER");
            } else if (field.getType() == Double.class || field.getType().getName().equals("double")) {
                sb.append(" REAL");
            } else if (field.getType() == Float.class || field.getType().getName().equals("float")) {
                sb.append(" REAL");
            } else if (field.getType() == Long.class || field.getType().getName().equals("long")) {
                sb.append(" LONG");
            } else if (field.getType() == String.class) {
                sb.append(" TEXT");
            } else if (field.getType() == Boolean.class || field.getType().getName().equals("boolean")) {
                sb.append(" INTEGER");
            } else if (field.getType() == Date.class) {
                sb.append(" LONG");
            } else if (field.getType() == Uri.class || field.getType() == File.class) {
                sb.append(" TEXT");
            } else {
                Log.i("creating table", "field: " + field.getName() + " not updated");
                continue;
            }
            columnsSQL.add(sb.toString());
        }

        StringBuilder sql = new StringBuilder();
        sql.append("create table ").append(tableName).append("( _id INTEGER PRIMARY KEY AUTOINCREMENT,");

        for (int i = 0; i < columnsSQL.size(); i++) {
            try {
                db.execSQL(columnsSQL.get(i));
                Log.i("sql", "table update completed: " + columnsSQL.get(i).toLowerCase());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
