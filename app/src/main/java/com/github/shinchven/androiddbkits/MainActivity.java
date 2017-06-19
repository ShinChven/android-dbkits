package com.github.shinchven.androiddbkits;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setAge(15);
            user.setBirthday(new Date(System.currentTimeMillis()));
            user.setUsername("user_" + System.currentTimeMillis());
            user.setProfession("dummy profession");
            user.setMarried(i % 2 == 0 ? true : false);
            user.setdValue(new Random().nextDouble());
            user.setfValue(new Random().nextFloat());
            user.setlValue(System.currentTimeMillis());
            DataBaseHelper.addUser(this, user);
        }


        List<User> users = DataBaseHelper.getUsers(this);
        if (users != null) {

            Gson g = new GsonBuilder().setPrettyPrinting().create();
            TextView txt = (TextView) findViewById(R.id.data);
            try {
                String data = g.toJson(users);
                txt.setText(data);
            } catch (Exception e) {
                e.printStackTrace();
                txt.setText("data error");
            }
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
