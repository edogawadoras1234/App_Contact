package com.example.testdanhba;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DanhBaAdapter extends RecyclerView.Adapter<DanhBaAdapter.DanhBaViewHolder>  {
    danhsach context;
    final ArrayList arrid, arrten, arrphone, arravatar;
    Database database;
    DanhBaCLickInterfact danhBaCLickInterfact;
    List<Contact> contactList;


    public  DanhBaAdapter(danhsach context, ArrayList arrid, ArrayList arrten, ArrayList arrphone, ArrayList arravatar, DanhBaCLickInterfact danhBaCLickInterfact){
        this.context = context;
        this.arrid = arrid;
        this.arrten = arrten;
        this.arrphone = arrphone;
        this.arravatar = arravatar;
        this.danhBaCLickInterfact = danhBaCLickInterfact;
    }

    @NonNull
    @Override
    public DanhBaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_database, parent, false);
        return new DanhBaViewHolder(view);
    }
    public void remove(int pos){
        arrten.remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhBaViewHolder holder, final int position) {
        holder.edtten.setText(String.valueOf(arrten.get(position)));
        holder.edtsdt.setText(String.valueOf(arrphone.get(position)));
        final String anh = String.valueOf(arravatar.get(position));
        Glide.with(context.getApplicationContext()).load(anh)
                .placeholder(R.drawable.icon_person).into(holder.photo);
        final String sdt = String.valueOf(arrphone.get(position));
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
        final String id = String.valueOf(arrid.get(position));
        holder.btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = new Database(context);
                Toast.makeText(context, "Delete Success: " + String.valueOf(arrten.get(position)), Toast.LENGTH_SHORT).show();
                database.DeleteData(id);
                remove(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrten.size();
    }

    public class DanhBaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView edtten, edtsdt;
        ImageView photo;
        Button btncall, btndel;

        public DanhBaViewHolder(@NonNull View itemView) {
            super(itemView);
            edtten = itemView.findViewById(R.id.txttendb);
            edtsdt = itemView.findViewById(R.id.txtsdtdb);
            photo = itemView.findViewById(R.id.imgviewdb);
            btncall = itemView.findViewById(R.id.btngoidiendb);
            btndel = itemView.findViewById(R.id.btnxoadb);

           itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            danhBaCLickInterfact.onItemClick(position);

        }
    }
}

