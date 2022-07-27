package com.example.tenantjournal;


import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tenantjournal.Adapter.TenantInformationAdapter;
import com.example.tenantjournal.Model.Tenant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;


public class AddNewTenantFragment extends Fragment {

    Button btnCheckIn, btnCheckOut, btSave;
    DatePickerDialog datePicker;
    EditText etPassport, etFullName,etCheckIn,etCheckOut,etProfession, etPhoneNumber, etAddress, etDepositPaid;
    RadioGroup rgGender;
    RadioButton rbGender;

    private ArrayList<Tenant> tenantArrayList = new ArrayList<>();
//
//    TenantInformationAdapter tenantInformationAdapter;
//    RecyclerView recyclerView;


    public AddNewTenantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_new_tenant, container, false);

        btSave = (Button) rootView.findViewById(R.id.bt_save);
        btnCheckIn = (Button) rootView.findViewById(R.id.bt_check_in_calender);
        btnCheckOut = (Button) rootView.findViewById(R.id.bt_check_out_calender);

        etPassport = (EditText) rootView.findViewById(R.id.et_passport_no);
        etFullName = (EditText) rootView.findViewById(R.id.et_full_name);
        etCheckIn = (EditText) rootView.findViewById(R.id.et_move_in);
        etCheckOut = (EditText) rootView.findViewById(R.id.et_move_out);
        etProfession = (EditText) rootView.findViewById(R.id.et_profession);
        etPhoneNumber = (EditText) rootView.findViewById(R.id.et_phone_number);
        etDepositPaid = (EditText) rootView.findViewById(R.id.et_deposit_paid);

        rgGender = (RadioGroup) rootView.findViewById(R.id.rg_gender);


     //   recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_rent);




        etCheckIn.setInputType(InputType.TYPE_NULL);
        etCheckOut.setInputType(InputType.TYPE_NULL);


        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(etCheckIn);
            }
        });

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(etCheckOut);
            }
        });

        checkButton(rootView);

      //  LoggedUser.getInstance().loadData(tenantArrayList);
       // loadData();

//        Log.e("array", String.valueOf(tenantArrayList));
//        buildRecyclerView();

//        btLoad.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tenantArrayList.add(new Tenant("111","Mark","eee"));
//                tenantInformationAdapter.notifyItemInserted(tenantArrayList.size());
//            }
//        });


        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


          //   LoggedUser.getInstance().saveData(tenantArrayList);


          //   saveData();


                tenantArrayList.add(new Tenant(etPassport.getText().toString(),
                        etFullName.getText().toString(),
                        etCheckIn.getText().toString(),
                        etCheckOut.getText().toString(),
                        "female",
                        etProfession.getText().toString(),
                        etPhoneNumber.getText().toString(),
                        etAddress.getText().toString(),
                        etDepositPaid.getText().toString(),
                        true
                        ));
                //tenantInformationAdapter.notifyItemInserted(tenantArrayList.size());

                saveData();
            }
        });



        return rootView;
    }


    public void setDate(final EditText editText)
    {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            datePicker = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            editText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        }
                    }, year, month, day);
        }
        datePicker.show();
    }

    public void checkButton(View v){
        int radioId = rgGender.getCheckedRadioButtonId();

        rbGender = v.findViewById(radioId);



    }


//    private void buildRecyclerView() {
//        // initializing our adapter class.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            tenantInformationAdapter = new TenantInformationAdapter(getContext(),tenantArrayList );
//
//            // adding layout manager to our recycler view.
//            LinearLayoutManager manager = new LinearLayoutManager(getContext());
//            recyclerView.setHasFixedSize(true);
//
//            // setting layout manager to our recycler view.
//            recyclerView.setLayoutManager(manager);
//
//            // setting adapter to our recycler view.
//            recyclerView.setAdapter(tenantInformationAdapter);
//        }
//    }



    private void saveData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(tenantArrayList);
        editor.putString("courses", json);
        editor.apply();
        Toast.makeText(getContext(), "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
        }



        Fragment fr = new HomeFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container, fr);
        fragmentTransaction.commit();
    }
//
//
//    private void loadData() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
//            Gson gson = new Gson();
//            String json = sharedPreferences.getString("courses", null);
//            Type type = new TypeToken<ArrayList<Tenant>>() {
//            }.getType();
//            tenantArrayList = gson.fromJson(json, type);
//            if (tenantArrayList == null) {
//                tenantArrayList = new ArrayList<>();
//            }
//        }
//    }



}
