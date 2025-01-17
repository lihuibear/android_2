package com.example.stage_2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stage_2.util.MyDatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    /*
    创建一个RecoverySystem对象和浮动操作按钮
     */
    FloatingActionButton add_button;

    MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);

    ArrayList<String> phone_id, phone_name, phone_phone, phone_address, phone_unit, phone_email, phone_qq;
    CustomAdapter customAdapter;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        add_button = findViewById(R.id.add_button);

        searchView = findViewById(R.id.searchview);

        searchView.setIconifiedByDefault(false);

        searchView.setSubmitButtonEnabled(true);

        searchView.setQueryHint("输入您想查找的内容");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //清除数据
                phone_id.clear();
                phone_name.clear();
                phone_phone.clear();
                phone_address.clear();
                phone_unit.clear();
                phone_email.clear();
                phone_qq.clear();
                displayData(s);
                //查询数据库
                customAdapter = new CustomAdapter(MainActivity.this, MainActivity.this, phone_id, phone_phone, phone_name, phone_address, phone_unit, phone_email, phone_qq);
                recyclerView.setAdapter(customAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                return false;
            }
        });


        //为浮动按钮添加一个设置onclick监听器
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddContacts.class);
                startActivity(intent);
            }
        });

        phone_id = new ArrayList<>();
        phone_name = new ArrayList<>();
        phone_phone = new ArrayList<>();
        phone_address = new ArrayList<>();
        phone_unit = new ArrayList<>();
        phone_email = new ArrayList<>();
        phone_qq = new ArrayList<>();

        displayData();

        customAdapter = new CustomAdapter(MainActivity.this, this, phone_id, phone_name, phone_phone, phone_address, phone_unit, phone_email, phone_qq);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override//重写获取菜单项的方法
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();//获取菜单Inflater,MenuInflater加载menu布局文件
        inflater.inflate(R.menu.my_menu, menu);//通过inflate获取菜单资源
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    public void displayData() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "没有数据可以显示", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                phone_id.add(cursor.getString(0));
                phone_name.add(cursor.getString(1));
                phone_phone.add(cursor.getString(2));
                phone_address.add(cursor.getString(3));
                phone_unit.add(cursor.getString(4));
                phone_email.add(cursor.getString(5));
                phone_qq.add(cursor.getString(6));
            }
        }
    }

    public void displayData(String s) {
        Cursor cursor = myDB.queryUser(s);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "没有数据可以显示", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                phone_id.add(cursor.getString(0));
                phone_name.add(cursor.getString(1));
                phone_phone.add(cursor.getString(2));
                phone_address.add(cursor.getString(3));
                phone_unit.add(cursor.getString(4));
                phone_email.add(cursor.getString(5));
                phone_qq.add(cursor.getString(6));
            }
        }
    }


}