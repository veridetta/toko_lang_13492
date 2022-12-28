package com.example.toko_lang_13492;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText nama, harga, des, gambar;
    Button btnTambah;
    Spinner kategori;
    RelativeLayout lyLoading;
    String stNama, stHarga, stDes, stKategori, stGambar;
    Boolean isChecked;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Produk produk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        init();
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value();
                if(check(stNama, stHarga, stDes,stGambar)){
                    addDatatoFirebase(stNama,stHarga,stDes,stKategori,stGambar);
                }else{
                    Toast.makeText(MainActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void init(){
        lyLoading = findViewById(R.id.ly_loading);
        nama = findViewById(R.id.nama);
        harga = findViewById(R.id.harga);
        des = findViewById(R.id.des);
        kategori = findViewById(R.id.sp_kat);
        gambar = findViewById(R.id.gambar);
        btnTambah = findViewById(R.id.btn_tambah);
        firebaseDatabase = FirebaseDatabase.getInstance("https://toko-lang-13492-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference("produk");
        produk = new Produk();

    }
    void value(){
        stNama = nama.getText().toString();
        stHarga = harga.getText().toString();
        stDes = des.getText().toString();
        stGambar = gambar.getText().toString();
        stKategori = kategori.getSelectedItem().toString();
    }
    Boolean check(String cNama, String cHarga, String cDes, String cGambar){
        if(!cNama.equals("") && !cHarga.equals("") && !cDes.equals("") && !cGambar.equals("")){
           return true;
        }else{
            return false;
        }
    }
    private void addDatatoFirebase(String fNama, String fHarga, String fDes, String fKat, String fGam) {
        produk.setNama(fNama);
        produk.setHarga(fHarga);
        produk.setDeskripsi(fDes);
        produk.setKategori(fKat);
        produk.setGambar(fGam);
        lyLoading.setVisibility(View.VISIBLE);
        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(produk);
                lyLoading.setVisibility(View.GONE);
                // after adding this data we are showing toast message.
                Toast.makeText(MainActivity.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                lyLoading.setVisibility(View.GONE);
                // we are displaying a failure toast message.
                Toast.makeText(MainActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}