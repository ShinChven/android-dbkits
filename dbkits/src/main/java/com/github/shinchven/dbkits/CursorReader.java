package com.github.shinchven.dbkits;

import android.database.Cursor;
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
 * Created by ShinChven on 2016/10/4.
 */

public class CursorReader {

    private final static String TAG = CursorReader.class.getSimpleName();

    /**
     * read a cursor and reflect data to the type provided.
     *
     * @param cursor            a cursor from SQLite query.
     * @param type              the type you with to convert your data to.
     * @param <T>               the type you with to convert your data to.
     * @param ignoredFieldNames you can ignore some fields if you provide its name.
     * @return
     */
    public static <T> List<T> read(Cursor cursor, Class<T> type, String... ignoredFieldNames) {
        List<String> fieldsToIgnore = Arrays.asList(ignoredFieldNames);
        List<T> list = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                T obj = null;
                try {
                    obj = type.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "instance creating failed: " + type.getName());
                    continue;
                }
                Field[] fields = type.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);

                    if (fieldsToIgnore.contains(field.getName()))
                        continue;

                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }

                    int columnIndex = -1;
                    try {
                        columnIndex = cursor.getColumnIndex(field.getName());

                        if (columnIndex < 0) {
                            Log.i(TAG, "field: " + field.getName() + " matches no column in cursor.");
                            continue;
                        }

                        if (field.getType() == Integer.class || field.getType().getName().equals("int")) {
                            int data = cursor.getInt(columnIndex);
                            field.setInt(obj, data);
                        } else if (field.getType() == Double.class || field.getType().getName().equals("double")) {
                            double data = cursor.getDouble(columnIndex);
                            field.setDouble(obj, data);
                        } else if (field.getType() == Float.class || field.getType().getName().equals("float")) {
                            float data = cursor.getFloat(columnIndex);
                            field.setFloat(obj, data);
                        } else if (field.getType() == Short.class || field.getType().getName().equals("short")) {
                            short data = cursor.getShort(columnIndex);
                            field.setShort(obj, data);
                        } else if (field.getType() == Long.class || field.getType().getName().equals("long")) {
                            long data = cursor.getLong(columnIndex);
                            field.setLong(obj, data);
                        } else if (field.getType() == String.class) {
                            String data = cursor.getString(columnIndex);
                            field.set(obj, data);
                        } else if (field.getType() == Boolean.class || field.getType().getName().equals("boolean")) {
                            boolean data = cursor.getInt(columnIndex) > 0;
                            field.setBoolean(obj, data);
                        } else if (field.getType() == Date.class) {
                            long raw = cursor.getLong(columnIndex);
                            Date date = new Date(raw);
                            field.set(obj, date);
                        } else if (field.getType() == Uri.class) {
                            String uriString = cursor.getString(columnIndex);
                            Uri uri = Uri.parse(uriString);
                            field.set(obj, uri);
                        } else if (field.getType() == File.class) {
                            String filePath = cursor.getString(columnIndex);
                            File file = new File(filePath);
                            field.set(obj, file);
                        } else {
                            Log.i(TAG, "field: " + field.getName() + " not filled");
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "reflecting field: " + field.getName() + " error @ column:" + columnIndex);
                        e.printStackTrace();
                    }


                }
                try {
                    list.add(((T) obj));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        return list;
    }


}
