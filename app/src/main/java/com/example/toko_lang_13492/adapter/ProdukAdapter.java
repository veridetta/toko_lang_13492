package com.example.toko_lang_13492.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toko_lang_13492.DetailActivity;
import com.example.toko_lang_13492.Produk;
import com.example.toko_lang_13492.R;
import com.example.toko_lang_13492.helper.Rupiah;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class ProdukAdapter extends FirebaseRecyclerAdapter<
        Produk, ProdukAdapter.ProdukViewholder> {
    Context mContext;
    String stNama, stHarga, stImg, stDes;

    public ProdukAdapter(
            @NonNull FirebaseRecyclerOptions<Produk> options, Context context)
    {
        super(options);
        mContext = context;
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull ProdukViewholder holder,
                     int position, @NonNull Produk model)
    {
        holder.nama.setText(model.getNama());
        String rupiah = new Rupiah().getRupiah(Double.parseDouble(model.getHarga()));
        holder.harga.setText(rupiah);
        Glide.with(mContext).load(model.getGambar()).fitCenter().centerCrop().into(holder.gambar);
        holder.cdProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("nama", model.getNama());
                intent.putExtra("harga", model.getHarga());
                intent.putExtra("des", model.getDeskripsi());
                intent.putExtra("img", model.getGambar());
                mContext.startActivity(intent);
                notifyItemRangeChanged(0,getItemCount());
            }
        });
    }

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public ProdukViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_produk, parent, false);

        return new ProdukAdapter.ProdukViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class ProdukViewholder
            extends RecyclerView.ViewHolder {
        TextView nama, harga;
        ImageView gambar;
        CardView cdProduk;
        public ProdukViewholder(@NonNull View itemView)
        {
            super(itemView);
            nama = itemView.findViewById(R.id.nama_produk);
            harga = itemView.findViewById(R.id.harga_produk);
            gambar = itemView.findViewById(R.id.img_produk);
            cdProduk = itemView.findViewById(R.id.cd_produk);
        }
    }
}
