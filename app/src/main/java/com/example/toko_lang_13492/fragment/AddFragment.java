package com.example.toko_lang_13492.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.toko_lang_13492.MainActivity;
import com.example.toko_lang_13492.Produk;
import com.example.toko_lang_13492.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    EditText nama, harga, des, gambar;
    View view;
    Button btnTambah;
    Spinner kategori;
    RelativeLayout lyLoading;
    String stNama, stHarga, stDes, stKategori, stGambar;
    Boolean isChecked;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Produk produk;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_add, container, false);
        init();
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value();
                if(check(stNama, stHarga, stDes,stGambar)){
                    addDatatoFirebase(stNama,stHarga,stDes,stKategori,stGambar);
                }else{
                    Toast.makeText(getActivity(), "Please add some data.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    void init(){
        lyLoading = view.findViewById(R.id.ly_loading);
        nama = view.findViewById(R.id.nama);
        harga = view.findViewById(R.id.harga);
        des = view.findViewById(R.id.des);
        kategori = view.findViewById(R.id.sp_kat);
        gambar = view.findViewById(R.id.gambar);
        btnTambah = view.findViewById(R.id.btn_tambah);
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
                Toast.makeText(getActivity(), "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                lyLoading.setVisibility(View.GONE);
                // we are displaying a failure toast message.
                Toast.makeText(getActivity(), "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}