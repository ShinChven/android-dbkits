package com.github.shinchven.androiddbkits;

import android.net.Uri;

import java.io.File;
import java.util.Date;

/**
 * Created by shinc on 2017/6/11.
 */
public class User {

    private int _id;
    private String username;
    private int age;
    private Date birthday;
    private String profession;
    private boolean married;
    private float fValue;
    private double dValue;
    private long lValue;
    private File avatar;
    private String lastName;
    private Uri contentUri;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public float getfValue() {
        return fValue;
    }

    public void setfValue(float fValue) {
        this.fValue = fValue;
    }

    public double getdValue() {
        return dValue;
    }

    public void setdValue(double dValue) {
        this.dValue = dValue;
    }

    public long getlValue() {
        return lValue;
    }

    public void setlValue(long lValue) {
        this.lValue = lValue;
    }

    public File getAvatar() {
        return avatar;
    }

    public void setAvatar(File avatar) {
        this.avatar = avatar;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Uri getContentUri() {
        return contentUri;
    }

    public void setContentUri(Uri contentUri) {
        this.contentUri = contentUri;
    }
}
