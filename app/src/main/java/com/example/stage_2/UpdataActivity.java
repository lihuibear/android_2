package com.example.stage_2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stage_2.util.MyDatabaseHelper;

public class UpdataActivity extends AppCompatActivity {
    EditText pt_name1, pt_phone1, pt_address1, pt_unit1, pt_email1, pt_qq1;
    Button update_button, delete_button;
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
                updateContact();
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    public void getAndSetIntentData() {
        if (getIntent().hasExtra("name") && getIntent().hasExtra("phone") && getIntent().hasExtra("address") && getIntent().hasExtra("unit") && getIntent().hasExtra("email") && getIntent().hasExtra("qq")) {
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            phone = getIntent().getStringExtra("phone");
            address = getIntent().getStringExtra("address");
            unit = getIntent().getStringExtra("unit");
            email = getIntent().getStringExtra("email");
            qq = getIntent().getStringExtra("qq");
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

    private void updateContact() {
        MyDatabaseHelper myDB = new MyDatabaseHelper(UpdataActivity.this);
        name = pt_name1.getText().toString().trim();
        phone = pt_phone1.getText().toString().trim();
        address = pt_address1.getText().toString().trim();
        unit = pt_unit1.getText().toString().trim();
        email = pt_email1.getText().toString().trim();
        qq = pt_qq1.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "姓名和电话不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = myDB.updateData(id, name, phone, address, unit, email, qq);
        if (result == -1) {
            Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
            finish(); // 返回到前一个活动
        }
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除 " + name + "?");
        builder.setMessage("确认要删除 " + name + "?");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdataActivity.this);
                myDB.deleteOneRow(id);
                Toast.makeText(UpdataActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("否", null);
        builder.create().show();
    }
}
