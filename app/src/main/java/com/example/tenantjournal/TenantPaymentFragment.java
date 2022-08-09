package com.example.tenantjournal;


import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tenantjournal.Model.NewPayment;
import com.example.tenantjournal.Model.Tenant;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class TenantPaymentFragment extends Fragment {

    EditText etTenantName, etPropertyName,etPaymentDate,etProfession, etDepositPaid,etRentalFeePaid, etDamagePayment;
    Button btPaymentSave, btClose;
    DatePickerDialog datePicker;

    private ArrayList<NewPayment> tenantArrayList = new ArrayList<NewPayment>();
    NewPayment newPaymentObj;

    public TenantPaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tenant_payment, container, false);

        etTenantName = (EditText) rootView.findViewById(R.id.et_tenant_name);
        etPropertyName = (EditText) rootView.findViewById(R.id.et_property_name);
        etPaymentDate = (EditText) rootView.findViewById(R.id.et_payment_date);
        etDepositPaid = (EditText) rootView.findViewById(R.id.et_deposit_paid);
        etProfession = (EditText) rootView.findViewById(R.id.et_profession);
        etRentalFeePaid = (EditText) rootView.findViewById(R.id.et_rental_fee_paid);
        etDamagePayment = (EditText) rootView.findViewById(R.id.et_damage_payment);
        btPaymentSave = (Button) rootView.findViewById(R.id.bt_payment_save);
        btClose = (Button) rootView.findViewById(R.id.bt_payment_summary_close);

        etPaymentDate.setInputType(InputType.TYPE_NULL);

        btPaymentSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

        etPaymentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(etPaymentDate);
            }
        });

        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               callHomeFragment();
            }
        });

        final Bundle bundle = getArguments();
        if (bundle != null) {
            tenantArrayList = bundle.getParcelableArrayList("arr");
        }

        return rootView;
    }

    private void validation()
    {

        newPaymentObj = new NewPayment(etTenantName.getText().toString(),
                etPropertyName.getText().toString(),
                etPaymentDate.getText().toString(),
                etProfession.getText().toString(),
                etRentalFeePaid.getText().toString(),
                etDamagePayment.getText().toString());


        if(etTenantName.getText().toString().equals("")){

            etTenantName.setHint("Please enter tenant's name");
            etTenantName.setHintTextColor(getResources().getColor(R.color.colorRed));

        }
        else if (etPropertyName.getText().toString().equals(""))
        {
            etPropertyName.setHint("Please enter property name");
            etPropertyName.setHintTextColor(getResources().getColor(R.color.colorRed));
        }

        else if (etPaymentDate.getText().toString().equals(""))
        {
            etPaymentDate.setHint("Please enter payment date");
            etPaymentDate.setHintTextColor(getResources().getColor(R.color.colorRed));
        }

        else if (etProfession.getText().toString().equals(""))
        {
            etProfession.setHint("Please enter profession");
            etProfession.setHintTextColor(getResources().getColor(R.color.colorRed));
        }
        else if (etRentalFeePaid.getText().toString().equals(""))
        {
            etRentalFeePaid.setHint("Please enter rental fee paid amount");
            etRentalFeePaid.setHintTextColor(getResources().getColor(R.color.colorRed));
        }
        else if (etDamagePayment.getText().toString().equals(""))
        {
            etRentalFeePaid.setHint("Please enter damage payment");
            etRentalFeePaid.setHintTextColor(getResources().getColor(R.color.colorRed));
        }

        else if (tenantArrayList == null) {
            tenantArrayList = new ArrayList<>();
            tenantArrayList.add(newPaymentObj);
            saveData();
            callHomeFragment();

        }
        else
        {
            tenantArrayList.add(newPaymentObj);
            saveData();
            callHomeFragment();

        }



    }

    private void saveData() {



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(tenantArrayList);
            editor.putString("newPayment", json);
            editor.apply();
          //  Toast.makeText(getContext(), "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();

        }
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

            datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        }
        datePicker.show();
    }

    private void callHomeFragment()
    {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("arr", tenantArrayList);
        android.app.Fragment fr = new HomeFragment();
        FragmentManager fm = getFragmentManager();
        fr.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container, fr);
        fragmentTransaction.commit();
    }
}
