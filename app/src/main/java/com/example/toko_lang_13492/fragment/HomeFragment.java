package com.example.toko_lang_13492.fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.toko_lang_13492.Produk;
import com.example.toko_lang_13492.R;
import com.example.toko_lang_13492.adapter.ProdukAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    ProdukAdapter produkAdapter, priaAdapter;
    DatabaseReference mbase;
    RelativeLayout lyLoading;
    String path = "";
    Boolean start=false;
    CardView btnPria, btnWanita, btnAnak, btnSort;
    View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        init_fb();
        klik();
        // Inflate the layout for this fragment
        return view;
    }
    void init(){
        lyLoading = view.findViewById(R.id.ly_loading);
        btnAnak = view.findViewById(R.id.btn_anak);
        btnPria = view.findViewById(R.id.btn_pria);
        btnWanita = view.findViewById(R.id.btn_wanita);
        btnSort = view.findViewById(R.id.btn_sort);
        lyLoading.setVisibility(View.VISIBLE);
    }
    void klik(){
        btnPria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter(btnPria);
                path="Pria";
                lyLoading.setVisibility(View.VISIBLE);
                init_fb();
            }
        });
        btnWanita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                produkAdapter.stopListening();
                filter(btnWanita);
                path="Wanita";
                lyLoading.setVisibility(View.VISIBLE);
                init_fb();
            }
        });
        btnAnak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                produkAdapter.stopListening();
                filter(btnAnak);
                path="Anak-anak";
                lyLoading.setVisibility(View.VISIBLE);
                init_fb();
            }
        });
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter(btnSort);
                path="";
                lyLoading.setVisibility(View.VISIBLE);
                init_fb();
            }
        });
    }
    void init_fb(){
        FirebaseRecyclerOptions<Produk> op, priaOptions;
        Query query;
        mbase = FirebaseDatabase.getInstance().getReference("produk");
        if (!path.equals("")){
            query = mbase.orderByChild("kategori").equalTo(path);
            op
                    = new FirebaseRecyclerOptions.Builder<Produk>()
                    .setQuery( query, Produk.class)
                    .build();

        }else{
            query = mbase;
            op
                    = new FirebaseRecyclerOptions.Builder<Produk>()
                    .setQuery(query, Produk.class)
                    .build();
        }
        if (start){
            produkAdapter.stopListening();
            produkAdapter.updateOptions(op);
        }
        produkAdapter = new ProdukAdapter(op, getActivity());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                }
                if(dataSnapshot != null){
                    for(DataSnapshot friend: dataSnapshot.getChildren()){
                        String firebase_id = (String) friend.getKey();
                        Log.d("ContactSync","handle number "+firebase_id+" "+"Anak-anak"+" "+friend);
                    }
                    Log.d("ContactSync","handle number outer "+dataSnapshot);
                    //user exist
                }
                else {
                    //user_does_not_exist
                }
                lyLoading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ContactSync","handle number oncancel "+databaseError.getMessage());
            }
        });
        produkAdapter.startListening();
        recyclerView = view.findViewById(R.id.rc_produk);
        recyclerView.setItemAnimator(null);
        // To display the Recycler view linearly
        recyclerView.setLayoutManager(
                new GridLayoutManager(getActivity(),2));
        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data

        // Connecting object of required Adapter class to
        // the Adapter class itself
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(produkAdapter);
        start=true;
    }
    @Override
    public void onStart()
    {
        super.onStart();
        produkAdapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override
    public void onStop()
    {
        super.onStop();
        produkAdapter.stopListening();
    }
    void filter(CardView cardView){
        btnSort.setCardBackgroundColor(getResources().getColor(R.color.grey));
        btnAnak.setCardBackgroundColor(getResources().getColor(R.color.grey));
        btnPria.setCardBackgroundColor(getResources().getColor(R.color.grey));
        btnWanita.setCardBackgroundColor(getResources().getColor(R.color.grey));
        cardView.setCardBackgroundColor(getResources().getColor(R.color.purple_700));
    }
}