package com.example.tenantjournal;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tenantjournal.Adapter.AddNewTenantAdapter;
import com.example.tenantjournal.Model.NewPayment;
import com.example.tenantjournal.Model.Tenant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ArrayList<NewPayment> paymentArrayList = new ArrayList<NewPayment>();
    private ArrayList<Tenant> tenantArrayList = new ArrayList<Tenant>();

    AddNewTenantAdapter addNewTenantAdapter;
    RecyclerView recyclerView;
    Button btNewTenant, btViewTenant, btTenantPayment, btLogOut;
    TextView tvHi, tvCurrentMonth;
    String name;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        tvHi = (TextView) rootView.findViewById(R.id.tv_hi);
        tvCurrentMonth = (TextView) rootView.findViewById(R.id.tv_current_month);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_rent);
        btNewTenant = (Button) rootView.findViewById(R.id.bt_new_tenant);
        btViewTenant = (Button) rootView.findViewById(R.id.bt_view_tenant);
        btTenantPayment = (Button) rootView.findViewById(R.id.bt_tenant_payment);
        btLogOut =(Button) rootView.findViewById(R.id.bt_log_out);


//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            SharedPreferences mobilePreference = getContext().getSharedPreferences("shared preferences", getContext().MODE_PRIVATE);
//            mobilePreference.edit().remove("newPayment").commit();
//            mobilePreference.edit().remove("newTenant1").commit();
//            mobilePreference.edit().remove("newTenant").commit();
//        }


        loadData();
        buildRecyclerView();


        final Bundle bundle = getArguments();
        if (bundle != null) {
         //   tenant = bundle.getParcelable("tenant");
            paymentArrayList = bundle.getParcelableArrayList("payment");
        //    tenantArrayList = bundle.getParcelableArrayList("tenant");// Key
            name = bundle.getString("name");

            tvHi.setText("Hello "+ name);



        }

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM", Locale.US);
        tvCurrentMonth.setText("Monthly Payment for "+month_date.format(cal.getTime()));






        btNewTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("tenant", tenantArrayList);
                bundle1.putString("name", name);


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
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("tenant", tenantArrayList);
                bundle1.putString("name", name);

                Fragment fr = new TenantsInformationFragment();
                FragmentManager fm = getFragmentManager();
                fr.setArguments(bundle1);
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fr);
                fragmentTransaction.commit();
            }
        });

        btTenantPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("payment", paymentArrayList);
                bundle1.putString("name", name);

                Fragment fr = new TenantPaymentFragment();
                FragmentManager fm = getFragmentManager();
                fr.setArguments(bundle1);
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fr);
                fragmentTransaction.commit();
            }
        });

        btLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fr = new LoginFragment();
                FragmentManager fm = getFragmentManager();
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

            if (paymentArrayList != null) {

                addNewTenantAdapter = new AddNewTenantAdapter(getContext(), paymentArrayList);

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


                paymentArrayList = gson.fromJson(json, type);
                saveData(paymentArrayList);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("newTenant", null);
            Type type = new TypeToken<ArrayList<Tenant>>() {
            }.getType();


            tenantArrayList = gson.fromJson(json, type);

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
          //  Toast.makeText(getContext(), "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
        }

    }
}
