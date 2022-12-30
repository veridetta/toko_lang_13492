package com.example.toko_lang_13492.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.toko_lang_13492.Cart;
import com.example.toko_lang_13492.HomeActivity;
import com.example.toko_lang_13492.Produk;
import com.example.toko_lang_13492.R;

import com.example.toko_lang_13492.adapter.CartAdapter;
import com.example.toko_lang_13492.db.DBHelper;
import com.example.toko_lang_13492.db.DBManager;
import com.example.toko_lang_13492.helper.Rupiah;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements CartAdapter.Callbacks {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DBManager dbManager;
    private ArrayList<Cart> cartArrayList;
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    EditText etTotal;
    Button btnPesan;
    String atas, bawah, total, isi;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        dbManager = new DBManager(getActivity());
        cartArrayList = new ArrayList<>();
        dbManager.open();
        cartArrayList = dbManager.readCart();
        dbManager.close();
        adapter = new CartAdapter(getActivity(),cartArrayList,this);
        adapter.notifyDataSetChanged();
        recyclerView = view.findViewById(R.id.rc_produk);
        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        recyclerView.setAdapter(adapter);
        etTotal = view.findViewById(R.id.et_total);
        etTotal.setEnabled(false);
        btnPesan = view.findViewById(R.id.btn_cart);
        shared();
        klik();
        return view;
    }
    void shared(){
        SharedPreferences preferencesGet = PreferenceManager.getDefaultSharedPreferences(getActivity());
         atas = preferencesGet.getString("atas", "");
         isi = preferencesGet.getString("isi", "");
         bawah = preferencesGet.getString("bawah", "");
         total = preferencesGet.getString("total", "0");
        String rupiah = new Rupiah().getRupiah(Double.parseDouble(total));
        etTotal.setText(rupiah);
    }
    void klik(){
        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shared();
                String number = "6285329727224";
                String rupiah = new Rupiah().getRupiah(Double.parseDouble(total));
                String message = atas+isi+bawah+"*Total "+rupiah+"*";
                String url = null;
                try {
                    url = "https://api.whatsapp.com/send?phone="+number+"&text=" + URLEncoder.encode(message, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
    @Override
    public void handleUserData(Double total) {
        String rupiah = new Rupiah().getRupiah(total);
        etTotal.setText(rupiah);
    }
}