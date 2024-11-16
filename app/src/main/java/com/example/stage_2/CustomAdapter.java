package com.example.stage_2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stage_2.util.MyDatabaseHelper;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Activity activity;
    EditText pt_name1, pt_phone1, pt_address1, pt_unit1, pt_email1, pt_qq1;
    private Context context;
    private ArrayList phone_id, phone_name, phone_phone, phone_address,
            phone_unit, phone_email, phone_qq;

    CustomAdapter(Activity activity,
                  Context context,
                  ArrayList phone_id,
                  ArrayList phone_name,
                  ArrayList phone_phone,
                  ArrayList phone_address,
                  ArrayList phone_unit,
                  ArrayList phone_email,
                  ArrayList phone_qq) {
        this.activity = activity;
        this.context = context;
        this.phone_id = phone_id;
        this.phone_name = phone_name;
        this.phone_phone = phone_phone;
        this.phone_address = phone_address;
        this.phone_unit = phone_unit;
        this.phone_email = phone_email;
        this.phone_qq = phone_qq;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.phone_id_txt.setText(String.valueOf(phone_id.get(position)));
        holder.phone_name_txt.setText(String.valueOf(phone_name.get(position)));
        holder.phone_phone_txt.setText(String.valueOf(phone_phone.get(position)));
        holder.phone_address_txt.setText(String.valueOf(phone_address.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdataActivity.class);
                intent.putExtra("id", String.valueOf(phone_id.get(position)));
                intent.putExtra("name", String.valueOf(phone_name.get(position)));
                intent.putExtra("phone", String.valueOf(phone_phone.get(position)));
                intent.putExtra("address", String.valueOf(phone_address.get(position)));
                intent.putExtra("unit", String.valueOf(phone_unit.get(position)));
                intent.putExtra("email", String.valueOf(phone_email.get(position)));
                intent.putExtra("qq", String.valueOf(phone_qq.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
        //长按事件
        holder.mainLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                myPopupMenu(view, position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return phone_id.size();
    }

    private void myPopupMenu(View v, final int position) {
        //创建一个对象
        final PopupMenu popupMenu = new PopupMenu(context, v);
        //设置布局
        popupMenu.getMenuInflater().inflate(R.menu.my_menu1, popupMenu.getMenu());
        //设置点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(context, "你点击了:" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
//                当你点击拨打电话，跳转拨打电话
                if (menuItem.getTitle().equals("拨打电话")) {
                    Toast.makeText(context, "你点击了拨打电话:" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + String.valueOf(phone_phone.get(position))));
                    context.startActivity(intent);
                } else if (menuItem.getTitle().equals("修改")) {
                    Toast.makeText(context, "你点击了修改:" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, UpdataActivity.class);
                    //从这里获取数据，并设置意图在数据下方
                    intent.putExtra("id", String.valueOf(phone_id.get(position)));
                    intent.putExtra("name", String.valueOf(phone_name.get(position)));
                    intent.putExtra("phone", String.valueOf(phone_phone.get(position)));
                    intent.putExtra("address", String.valueOf(phone_address.get(position)));
                    intent.putExtra("unit", String.valueOf(phone_unit.get(position)));
                    intent.putExtra("email", String.valueOf(phone_email.get(position)));
                    intent.putExtra("qq", String.valueOf(phone_qq.get(position)));
                    activity.startActivityForResult(intent, 1);
                } else if (menuItem.getTitle().equals("删除")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("删除" + String.valueOf(phone_name.get(position)) + "信息?");
                    builder.setMessage("确认要" + String.valueOf(phone_name.get(position)) + "所有信息?");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MyDatabaseHelper myDB = new MyDatabaseHelper(context);
                            myDB.deleteOneRow(String.valueOf(phone_id.get(position)));
                            Intent intent1 = new Intent(context, MainActivity.class);
                            context.startActivity(intent1);
                            activity.finish();
                        }
                    });
                    builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.create().show();
                } else if (menuItem.getTitle().equals("发送短信")) {
                    Uri uri1 = Uri.parse("smsto:" + String.valueOf(phone_phone.get(position)));
                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri1);
                    smsIntent.putExtra("sms_body", "hello");
                    context.startActivity(smsIntent);
                } else if (menuItem.getTitle().equals("查看联系人")) {
                    Intent intent2 = new Intent(context, SearchActivity.class);
                    //从这里获取数据，并设置意图在数据下方
                    intent2.putExtra("id", String.valueOf(phone_id.get(position)));
                    intent2.putExtra("name", String.valueOf(phone_name.get(position)));
                    intent2.putExtra("phone", String.valueOf(phone_phone.get(position)));
                    intent2.putExtra("address", String.valueOf(phone_address.get(position)));
                    intent2.putExtra("unit", String.valueOf(phone_unit.get(position)));
                    intent2.putExtra("email", String.valueOf(phone_email.get(position)));
                    intent2.putExtra("qq", String.valueOf(phone_qq.get(position)));
                    activity.startActivityForResult(intent2, 1);
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView phone_id_txt, phone_name_txt, phone_phone_txt, phone_address_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            phone_id_txt = itemView.findViewById(R.id.phone_id_txt);
            phone_name_txt = itemView.findViewById(R.id.phone_name_txt);
            phone_phone_txt = itemView.findViewById(R.id.phone_phone_txt);
            phone_address_txt = itemView.findViewById(R.id.phone_address_txt);
            pt_name1 = itemView.findViewById(R.id.pt_name1);
            pt_phone1 = itemView.findViewById(R.id.pt_phone1);
            pt_address1 = itemView.findViewById(R.id.pt_address1);
            pt_unit1 = itemView.findViewById(R.id.pt_unit1);
            pt_email1 = itemView.findViewById(R.id.pt_email1);
            pt_qq1 = itemView.findViewById(R.id.pt_qq1);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}

