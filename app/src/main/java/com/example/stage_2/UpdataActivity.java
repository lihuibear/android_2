package com.example.stage_2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.lang.UProperty;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stage_2.util.MyDatabaseHelper;

public class UpdataActivity extends AppCompatActivity {
    EditText pt_name1, pt_phone1, pt_address1, pt_unit1, pt_email1, pt_qq1;
    Button update_button,delete_button;
    String id, name, phone, address, unit, email, qq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata);
        pt_name1 = findViewById(R.id.pt_name1);
        pt_phone1 = findViewById(R.id.pt_phone1);
        pt_address1 = findViewById(R.id.pt_address1);
        pt_unit1 = findViewById(R.id.pt_unit1);
        pt_email1 = findViewById(R.id.pt_email1);
        pt_qq1 = findViewById(R.id.pt_qq1);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdataActivity.this);
                //获取数据
                name = pt_name1.getText().toString();
                phone = pt_phone1.getText().toString();
                address = pt_address1.getText().toString();
                unit = pt_unit1.getText().toString();
                email = pt_email1.getText().toString();
                qq = pt_qq1.getText().toString();
                myDB.updateData(id,name,phone,address,unit,email,qq);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confiormDialog();
            }
        });
    }
    public void getAndSetIntentData() {
        if (getIntent().hasExtra("name") && getIntent().hasExtra("phone")
                && getIntent().hasExtra("address") && getIntent().hasExtra("unit")
                && getIntent().hasExtra("email") && getIntent().hasExtra("qq")) {
            //从这里获取数据，并设置意图在数据下方
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            phone = getIntent().getStringExtra("phone");
            address = getIntent().getStringExtra("address");
            unit = getIntent().getStringExtra("unit");
            email = getIntent().getStringExtra("email");
            qq = getIntent().getStringExtra("qq");
            //编辑文本
            pt_name1.setText(name);
            pt_phone1.setText(phone);
            pt_address1.setText(address);
            pt_unit1.setText(unit);
            pt_email1.setText(email);
            pt_qq1.setText(qq);
        } else {
            Toast.makeText(this, "没有数据!", Toast.LENGTH_SHORT).show();
        }
    }

    void confiormDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除"+name+"?");
        builder.setMessage("确认要删除"+name+"?");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdataActivity.this);
                myDB.deleteOneRow(id);
                Intent intent = new Intent(UpdataActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}