package com.example.toko_lang_13492.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toko_lang_13492.Cart;
import com.example.toko_lang_13492.Produk;
import com.example.toko_lang_13492.R;
import com.example.toko_lang_13492.db.DBManager;
import com.example.toko_lang_13492.helper.Rupiah;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Cart> notesList;
    DBManager dbManager;
    Callbacks callbacks;

    String atas= "*Daftar Pesanan Belanja :*\n", isi="", bawah = "--------------------- \n", total="0";
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName,tvHarga,tvQty;
        ImageView img, btnDel;
        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.nama_produk);
            tvHarga = view.findViewById(R.id.harga_produk);
            tvQty = view.findViewById(R.id.qty);
            img = view.findViewById(R.id.img_produk);
            btnDel = view.findViewById(R.id.btn_delete);
        }
    }


    public CartAdapter(Context context, ArrayList<Cart> notesList, CartAdapter.Callbacks callbacks) {
        this.context = context;
        this.notesList = notesList;
        this.callbacks = callbacks;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_cart, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,  int position) {
        final Cart student = notesList.get(position);
        String rupiah = new Rupiah().getRupiah(Double.parseDouble(student.getHarga()));
        holder.tvName.setText(student.getNama());
        holder.tvHarga.setText(rupiah);
        holder.tvQty.setText("Qty "+student.getQt());
        Glide.with(context).load(student.getGambar()).centerCrop().into(holder.img);
        dbManager = new DBManager(context);
        dbManager.open();
        SharedPreferences preferencesGet = PreferenceManager.getDefaultSharedPreferences(context);
        Integer nomor = position+1;
        String isinya = isi+nomor+". "+student.getNama()+" ("+student.getQt()+")    = "+rupiah+"\n";
        isi = isinya;
        Double totalx = Double.parseDouble(total)+(Double.parseDouble(student.getQt())*Double.parseDouble(student.getHarga()));
        total = String.valueOf(totalx);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("isi",isi);
        editor.putString("atas",atas);
        editor.putString("bawah",bawah);
        editor.putString("total", String.valueOf(totalx));
        editor.apply();
        callbacks.handleUserData(Double.valueOf(total));
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.delete(Long.parseLong(student.getId()));
                dbManager.close();
                notesList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyDataSetChanged();
                Double totalxz = Double.parseDouble(total)-(Double.parseDouble(student.getQt())*Double.parseDouble(student.getHarga()));
                total = String.valueOf(totalxz);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("total", String.valueOf(total));
                editor.apply();
                callbacks.handleUserData(Double.valueOf(total));
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
    public interface Callbacks {
        void handleUserData(Double total);
    }
}
