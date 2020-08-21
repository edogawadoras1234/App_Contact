package com.example.testdanhba;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    Context context;
    List<Contact> contactList;

    public  ContactAdapter(Context context, List<Contact> contactList){
        this.context = context;
        this.contactList = contactList;
    }
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, final int position) {
        holder.edtten.setText(String.valueOf(contactList.get(position).getName()));
        holder.edtsdt.setText(String.valueOf(contactList.get(position).getPhone()));
        final String anh = contactList.get(position).getPhoto();
        Glide.with(context.getApplicationContext()).load(anh).override(50, 50)
                .placeholder(R.drawable.icon_person).into(holder.photo);
        final String sdt = String.valueOf(contactList.get(position).getPhone());
        holder.btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel: " + sdt));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView edtten, edtsdt;
        ImageView photo;
        Button btncall, btndel;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            edtten = itemView.findViewById(R.id.txtten);
            edtsdt = itemView.findViewById(R.id.txtsdt);
            photo = itemView.findViewById(R.id.imageview);
            btncall = itemView.findViewById(R.id.btngoidien);
            btndel = itemView.findViewById(R.id.btnxoa);

        }
    }
}

