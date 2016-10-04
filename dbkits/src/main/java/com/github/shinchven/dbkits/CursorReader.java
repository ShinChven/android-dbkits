package com.github.shinchven.dbkits;

import android.database.Cursor;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ShinChven on 2016/10/4.
 */

public class CursorReader {

    private final static String TAG = CursorReader.class.getSimpleName();

    public static <T> List<T> read(Cursor cursor, Class<T> type) {
        List<T> list = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Object obj = null;
                try {
                    obj = type.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "instance creating failed: " + type.getName());
                    continue;
                }
                Class<?> clazz = obj.getClass();
                Field[] fields = clazz.getFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    int columnIndex = 0;
                    try {
                        Object value = field.get(obj);

                        columnIndex = cursor.getColumnIndexOrThrow(field.getName());
                        if (value instanceof Integer) {
                            int data = cursor.getInt(columnIndex);
                            field.setInt(obj, data);
                        } else if (value instanceof Double) {
                            double data = cursor.getDouble(columnIndex);
                            field.setDouble(obj, data);
                        } else if (value instanceof Float) {
                            float data = cursor.getFloat(columnIndex);
                            field.setFloat(obj, data);
                        } else if (value instanceof Long) {
                            long data = cursor.getLong(columnIndex);
                            field.setLong(obj, data);
                        } else if (value instanceof String) {
                            String data = cursor.getString(columnIndex);
                            field.set(obj, data);
                        } else if (value instanceof Boolean) {
                            boolean data = cursor.getInt(columnIndex) > 0;
                            field.setBoolean(obj, data);
                        } else if (value instanceof Date) {
                            long raw = cursor.getLong(columnIndex);
                            Date date = new Date(raw);
                            field.set(obj, date);
                        } else {
                            Log.i(TAG, "field: " + field.getName() + " not filled");
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "reflecting field: " + field.getName() + " error @ column:" + columnIndex);
                        e.printStackTrace();
                    }


                }
                list.add(((T) obj));

            }
        }

        return list;
    }


}
