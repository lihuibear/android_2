package com.example.stage_2;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    TextView pt_name2, pt_phone2, pt_address2, pt_unit2, pt_email2, pt_qq2;
    String id, name, phone, address, unit, email, qq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        pt_name2 = findViewById(R.id.pt_name2);
        pt_phone2 = findViewById(R.id.pt_phone2);
        pt_address2 = findViewById(R.id.pt_address2);
        pt_unit2 = findViewById(R.id.pt_unit2);
        pt_email2 = findViewById(R.id.pt_email2);
        pt_qq2 = findViewById(R.id.pt_qq2);
        getAndSetIntentData();
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

            pt_name2.setText(name);
            pt_phone2.setText(phone);
            pt_address2.setText(address);
            pt_unit2.setText(unit);
            pt_email2.setText(email);
            pt_qq2.setText(qq);

        } else {
            Toast.makeText(this, "没有数据!", Toast.LENGTH_SHORT).show();
        }
    }
}