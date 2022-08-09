package com.example.tenantjournal;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tenantjournal.Adapter.AddNewTenantAdapter;
import com.example.tenantjournal.Adapter.TenantInformationAdapter;
import com.example.tenantjournal.Model.NewPayment;
import com.example.tenantjournal.Model.Tenant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ArrayList<NewPayment> tenantArrayList = new ArrayList<NewPayment>();

    AddNewTenantAdapter addNewTenantAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Button btNewTenant, btViewTenant, btTenantPayment;
    //ListView arrayList;
//    Tenant tenant;
//    ArrayAdapter<Tenant> adapterNames;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_rent);
     //   arrayList = (ListView) rootView.findViewById(R.id.lv_tenant);
        btNewTenant = (Button) rootView.findViewById(R.id.bt_new_tenant);
        btViewTenant = (Button) rootView.findViewById(R.id.bt_view_tenant);
        btTenantPayment = (Button) rootView.findViewById(R.id.bt_tenant_payment);


//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            SharedPreferences mobilePreference = getContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
//            mobilePreference.edit().remove("courses").commit();
//        }


        loadData();
        buildRecyclerView();


        final Bundle bundle = getArguments();
        if (bundle != null) {
         //   tenant = bundle.getParcelable("tenant");
            tenantArrayList = bundle.getParcelableArrayList("arr"); // Key



                 //   tenantArrayList.add(tenant);
                  //  saveData(tenantArrayList);


//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                adapterNames = new ArrayAdapter<Tenant>(getContext(),android.R.layout.simple_list_item_1, android.R.id.text1, tenantArrayList);
//
//
//                tenantArrayList.add(0,tenant);
//                adapterNames.notifyDataSetChanged();
//
//                arrayList.setAdapter(adapterNames);
//            }

         //   loadData();



        }







        btNewTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // callFragment(new AddNewTenantFragment());

                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("arr",tenantArrayList);


                Fragment fr = new AddNewTenantFragment();
                FragmentManager fm = getFragmentManager();
                fr.setArguments(bundle1);
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fr);
                fragmentTransaction.commit();

            }
        });

        btViewTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callFragment(new TenantsInformationFragment());
            }
        });

        btTenantPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("arr",tenantArrayList);


                Fragment fr = new TenantPaymentFragment();
                FragmentManager fm = getFragmentManager();
                fr.setArguments(bundle1);
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fr);
                fragmentTransaction.commit();
            }
        });


        return rootView;
    }

    private void callFragment(Fragment fragment)
    {
        Fragment fr = fragment;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container, fr);
        fragmentTransaction.commit();
    }

    private void buildRecyclerView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            //   adapterNames.notifyDataSetChanged();

            //     loadData();

            if (tenantArrayList != null) {

                addNewTenantAdapter = new AddNewTenantAdapter(getContext(), tenantArrayList);

                addNewTenantAdapter.notifyDataSetChanged();

                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(addNewTenantAdapter);
            }
        }
    }

    private void loadData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("newPayment", null);
            Type type = new TypeToken<ArrayList<NewPayment>>() {
            }.getType();


                tenantArrayList = gson.fromJson(json, type);
                saveData(tenantArrayList);

        }
    }


    private void saveData(ArrayList<NewPayment> tenantArrayList2) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(tenantArrayList2);
            editor.putString("tenant", json);
            editor.apply();
            Toast.makeText(getContext(), "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
        }

    }
}
