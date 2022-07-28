package com.example.tenantjournal;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tenantjournal.Adapter.TenantInformationAdapter;
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

    private ArrayList<Tenant> tenantArrayList = new ArrayList<>();

    TenantInformationAdapter tenantInformationAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Button btNewTenant, btViewTenant, btTenantPayment;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_rent);
        btNewTenant = (Button) rootView.findViewById(R.id.bt_new_tenant);
        btViewTenant = (Button) rootView.findViewById(R.id.bt_view_tenant);
        btTenantPayment = (Button) rootView.findViewById(R.id.bt_tenant_payment);

        loadData();
        buildRecyclerView();

        btNewTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callFragment(new AddNewTenantFragment());

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
                callFragment(new TenantPaymentFragment());
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

            tenantInformationAdapter = new TenantInformationAdapter(getContext(),tenantArrayList );
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(tenantInformationAdapter);
        }
    }

    private void loadData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("courses", null);
            Type type = new TypeToken<ArrayList<Tenant>>() {
            }.getType();
            tenantArrayList = gson.fromJson(json, type);
            if (tenantArrayList == null) {
                tenantArrayList = new ArrayList<>();
            }
        }
    }


}
