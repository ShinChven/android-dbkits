package com.github.shinchven.dbkits;

import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by ShinChven on 2016/9/30.
 */

public class ContentValueWriter {

    private final static String LOG_TAG = ContentValueWriter.class.getSimpleName();

    public static ContentValues objectToContentValues(Object o, String... ignoredFieldNames) {
        ContentValues values = new ContentValues();
        try {
            //Will ignore any of the fields you pass in here
            List<String> fieldsToIgnore = Arrays.asList(ignoredFieldNames);

            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                if (fieldsToIgnore.contains(field.getName()))
                    continue;

                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                Object value = field.get(o);
                if (value != null) {
                    //This part just makes sure the content values can handle the field
                    if (value instanceof Double || value instanceof Integer || value instanceof String || value instanceof Boolean
                            || value instanceof Long || value instanceof Float || value instanceof Short) {
                        values.put(field.getName(), value.toString());
                    } else if (value instanceof Date) {
                        values.put(field.getName(), ((Date) value).getTime());
                    } else if(value instanceof Uri){
                        values.put(field.getName(), ((Uri) value).toString());
                    }else
                        Log.w(LOG_TAG, "value could not be handled by field: " + field.getName()+":"+value.toString());
                } else
                    Log.i(LOG_TAG, "value is null, so we don't include it");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }
}
