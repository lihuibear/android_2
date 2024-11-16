package com.example.stage_2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.stage_2.util.MyDatabaseHelper;


public class AddContacts extends AppCompatActivity {
    private EditText et_name,et_phone,et_address,et_unit,et_email,et_qq;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        init();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddContacts.this);
                myDB.addPhone(et_name.getText().toString().trim(),
                        et_phone.getText().toString().trim(),
                        et_address.getText().toString().trim(),
                        et_unit.getText().toString().trim(),
                        et_email.getText().toString().trim(),
                        et_qq.getText().toString().trim());

            }

        });

    }

    private void init(){
        et_name = findViewById(R.id.pt_name);
        et_phone = findViewById(R.id.pt_phone);
        et_address = findViewById(R.id.pt_address);
        et_unit = findViewById(R.id.pt_unit);
        et_email = findViewById(R.id.pt_email);
        et_qq = findViewById(R.id.pt_qq);
        button = findViewById(R.id.button);
    }
}