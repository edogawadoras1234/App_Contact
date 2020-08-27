package com.example.testdanhba;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> implements Filterable {
    danhsach context;
    List<Contact> contactList;
    List<Contact> contactListfull;
    DanhBaCLickInterfact danhBaCLickInterfact;
    Database database;

    public  ContactAdapter(danhsach context, List<Contact> contactList, DanhBaCLickInterfact danhBaCLickInterfact) {
        this.context = context;
        this.contactList = contactList;
        this.danhBaCLickInterfact = danhBaCLickInterfact;
        contactListfull = new ArrayList<>(contactList);
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
                database = new Database(context);
                Toast.makeText(context, "Delete Success: " + contactList.get(position).getId(), Toast.LENGTH_SHORT).show();
                database.DeleteData(contactList.get(position).getId());
                remove(position);
            }
        });
    }

    public void remove(int pos){
        contactList.remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Contact> listFilter = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                listFilter.addAll(contactListfull);
            }else{
                String filterPatern = charSequence.toString().toLowerCase().trim();
                for (Contact item : contactListfull){
                    if(item.getName().toLowerCase().contains(filterPatern)){
                        listFilter.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = listFilter;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            contactList.clear();
            contactList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            database = new Database(context);
            int position = getAdapterPosition();
            danhBaCLickInterfact.onItemClick(position);
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_edit_phone);
            final EditText edtname, edtphone, edtavatar;
            Button btnaccept, btncancle;
            edtname = dialog.findViewById(R.id.editName_dialog);
            edtphone = dialog.findViewById(R.id.editPhone_dialog);
            edtavatar = dialog.findViewById(R.id.editAvatar_dialog);
            final String id = contactList.get(position).getId();
            String name  = contactList.get(position).getName();
            String phone = contactList.get(position).getPhone();
            String avatar = contactList.get(position).getPhoto();
            edtname.setText(name);
            edtphone.setText(phone);
            edtavatar.setText(avatar);

            btnaccept = dialog.findViewById(R.id.btnchange_dgl_change);
            btncancle = dialog.findViewById(R.id.btncl_dgl_change);

            btncancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            btnaccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (edtphone.length() == 0) {
                        Toast.makeText(context, "Không được bỏ trống số điện thoại", Toast.LENGTH_SHORT).show();
                    } else {
                        database.UpdateData(edtname.getText().toString(), edtphone.getText().toString(), edtavatar.getText().toString(), id);
                        Toast.makeText(context, "Đã thay đổi thành công", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            dialog.show();
        }
    }
}

