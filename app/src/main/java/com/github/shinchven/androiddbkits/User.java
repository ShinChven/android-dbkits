package com.github.shinchven.androiddbkits;

import java.util.Date;

/**
 * Created by shinc on 2017/6/11.
 */
public class User {

    private int _id;
    private String username;
    private int age;
    private Date birthday;

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
}
