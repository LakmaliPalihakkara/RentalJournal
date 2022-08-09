package com.example.tenantjournal;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.tenantjournal.Adapter.TenantInformationAdapter;
import com.example.tenantjournal.Model.Tenant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class TenantsInformationFragment extends Fragment {

    private ListView lvSearch;
    private EditText etSearch;
    private ArrayAdapter<String> searchAdapter;
    Button btClose;

    TenantInformationAdapter tenantInformationAdapter;
    RecyclerView recyclerView;

    private ArrayList<Tenant> tenantArrayList = new ArrayList<Tenant>();
    SearchView svSearch;

    public TenantsInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_tenants_information, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_rent);
        lvSearch = (ListView) rootView.findViewById(R.id.lv_search);
        etSearch = (EditText) rootView.findViewById(R.id.et_search);
        btClose = (Button) rootView.findViewById(R.id.bt_close);
        svSearch = (SearchView) rootView.findViewById(R.id.sv_search);

        loadData();

        buildRecyclerView();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            searchAdapter = new ArrayAdapter<Tenant>(getContext(), R.layout.search_list_item, R.id.textview,tenantArrayList);
//
//            lvSearch.setAdapter(searchAdapter);
//        }

//        for (int i = 0; i < tenantArrayList.size(); i++) {
//            tenantArrayListName.add(tenantArrayList.get(i).getFullName());
//        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            searchAdapter = new ArrayAdapter<String>(getContext(), R.layout.search_list_item, R.id.textview, tenantArrayListName);
//
//            viewOnClick(rootView);
//
//            lvSearch.setAdapter(searchAdapter);
//        }

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

              //  searchAdapter.getFilter().filter(s);
                tenantInformationAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fr = new HomeFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fr);
                fragmentTransaction.commit();
            }
        });


        return rootView;
    }

    private void buildRecyclerView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            //   adapterNames.notifyDataSetChanged();

            //     loadData();

            if (tenantArrayList != null) {

                tenantInformationAdapter = new TenantInformationAdapter(getContext(), tenantArrayList, new TenantInformationAdapter.ViewClickListener() {
                    @Override
                    public void onClickView(View view, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("position",position);
                        bundle.putParcelableArrayList("arr",tenantArrayList);

                        Fragment fr = new TenantDetailsFragment();
                        FragmentManager fm = getFragmentManager();
                        fr.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.container, fr);
                        fragmentTransaction.commit();
                    }

                    @Override
                    public void onClickDelete(View view, int position, ArrayList<Tenant> arrayList) {

                     //   tenantArrayList.clear();
                     //   tenantArrayList = arrayList;
                 //  tenantArrayList.remove(position);
//                  searchAdapter.notifyItemRemoved(position);
//                   notifyItemRangeChanged(position,mData.size());
                    }
                });

                tenantInformationAdapter.notifyDataSetChanged();

                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(tenantInformationAdapter);
            }
        }
    }

    public void viewOnClick(View v) {
        final int position = lvSearch.getPositionForView(v);
//        String text = lvSearch.getItemAtPosition(position).toString();

        System.out.println("view"+position);
       // Toast.makeText(getApplicationContext, text, Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("tenant", null);
            Type type = new TypeToken<ArrayList<Tenant>>() {
            }.getType();
            tenantArrayList = gson.fromJson(json, type);

            }


//            if (tenantArrayList == null) {
//                tenantArrayList = new ArrayList<>();
//            }
        }
    }

