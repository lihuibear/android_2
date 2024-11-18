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
    private Activity activity;
    private Context context;
    private ArrayList<String> phone_id, phone_name, phone_phone, phone_address,
            phone_unit, phone_email, phone_qq;

    public CustomAdapter(Activity activity,
                         Context context,
                         ArrayList<String> phone_id,
                         ArrayList<String> phone_name,
                         ArrayList<String> phone_phone,
                         ArrayList<String> phone_address,
                         ArrayList<String> phone_unit,
                         ArrayList<String> phone_email,
                         ArrayList<String> phone_qq) {
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
        holder.phone_id_txt.setText(phone_id.get(position));
        holder.phone_name_txt.setText(phone_name.get(position));
        holder.phone_phone_txt.setText(phone_phone.get(position));
        holder.phone_address_txt.setText(phone_address.get(position));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdataActivity.class);
                putExtras(intent, position);
                activity.startActivityForResult(intent, 1);
            }
        });

        holder.mainLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                myPopupMenu(view, position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return phone_id.size();
    }

    private void myPopupMenu(View v, final int position) {
        final PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.getMenuInflater().inflate(R.menu.my_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getTitle().toString()) {
                    case "拨打电话":
                        dialPhoneNumber(position);
                        break;
                    case "修改":
                        updateContact(position);
                        break;
                    case "删除":
                        showDeleteConfirmationDialog(position);
                        break;
                    case "发送短信":
                        sendSms(position);
                        break;
                    case "查看联系人":
                        viewContact(position);
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void putExtras(Intent intent, int position) {
        intent.putExtra("id", phone_id.get(position));
        intent.putExtra("name", phone_name.get(position));
        intent.putExtra("phone", phone_phone.get(position));
        intent.putExtra("address", phone_address.get(position));
        intent.putExtra("unit", phone_unit.get(position));
        intent.putExtra("email", phone_email.get(position));
        intent.putExtra("qq", phone_qq.get(position));
    }

    private void dialPhoneNumber(int position) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone_phone.get(position)));
        context.startActivity(intent);
    }

    private void updateContact(int position) {
        Intent intent = new Intent(context, UpdataActivity.class);
        putExtras(intent, position);
        activity.startActivityForResult(intent, 1);
    }

    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("删除" + phone_name.get(position) + "信息?");
        builder.setMessage("确认要删除所有信息?");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(context);
                myDB.deleteOneRow(phone_id.get(position));
                // 更新列表
                notifyItemRemoved(position);
                Toast.makeText(context, "联系人已删除", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("否", null);
        builder.create().show();
    }

    private void sendSms(int position) {
        Uri uri = Uri.parse("smsto:" + phone_phone.get(position));
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
        smsIntent.putExtra("sms_body", "hello");
        context.startActivity(smsIntent);
    }

    private void viewContact(int position) {
        Intent intent = new Intent(context, SearchActivity.class);
        putExtras(intent, position);
        activity.startActivityForResult(intent, 1);
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
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
