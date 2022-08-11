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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tenantjournal.Model.Tenant;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;


public class AddNewTenantFragment extends Fragment {

    Button btSave, btClose;
    DatePickerDialog datePicker;
    EditText etPassport, etFullName, etCheckIn, etCheckOut, etProfession, etPhoneNumber, etDepositPaid;
    TextView tvTitle;
    RadioGroup rgGender;
    RadioButton rbMale, rbFemale;
    CheckBox cbSigned;
    boolean isEdit;
    int position;
    String name;
    String moveIn, moveOut;
    String gender;

    View rootView;

    private ArrayList<Tenant> tenantArrayList = new ArrayList<Tenant>();


    boolean isSigned;

    Tenant tenant;


    public AddNewTenantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     rootView = inflater.inflate(R.layout.fragment_add_new_tenant, container, false);

        btSave = (Button) rootView.findViewById(R.id.bt_save);
        btClose = (Button) rootView.findViewById(R.id.bt_close);

        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        etPassport = (EditText) rootView.findViewById(R.id.et_passport_no);
        etFullName = (EditText) rootView.findViewById(R.id.et_full_name);
        etCheckIn = (EditText) rootView.findViewById(R.id.et_move_in);
        etCheckOut = (EditText) rootView.findViewById(R.id.et_move_out);
        etProfession = (EditText) rootView.findViewById(R.id.et_profession);
        etPhoneNumber = (EditText) rootView.findViewById(R.id.et_phone_number);
        etDepositPaid = (EditText) rootView.findViewById(R.id.et_deposit_paid);

        rgGender = (RadioGroup) rootView.findViewById(R.id.rg_gender);
        rbMale = (RadioButton) rootView.findViewById(R.id.rb_male);
        rbFemale = (RadioButton) rootView.findViewById(R.id.rb_female);

        cbSigned = (CheckBox) rootView.findViewById(R.id.cb_agreement_signed);

        final Bundle bundle = getArguments();
        if (bundle != null) {
            tenantArrayList = bundle.getParcelableArrayList("tenant");
            isEdit = bundle.getBoolean("edit");
            position = bundle.getInt("position");
            name = bundle.getString("name");



            if(isEdit)
            {
                tvTitle.setText("Update Tenant");
                for(int i=0; i<tenantArrayList.size(); i++) {
                    if(position == i) {
                        etPassport.setText(tenantArrayList.get(i).getPassport());
                        etFullName.setText(tenantArrayList.get(i).getFullName());
                        etCheckIn.setText(tenantArrayList.get(i).getCheckInDate());
                        etCheckOut.setText(tenantArrayList.get(i).getCheckOutDate());
                        etProfession.setText(tenantArrayList.get(i).getProfession());
                        etPhoneNumber.setText(tenantArrayList.get(i).getPhoneNumber());
                        etDepositPaid.setText(tenantArrayList.get(i).getDepositPaid());
                        gender = tenantArrayList.get(i).getGender();
                        isSigned = tenantArrayList.get(i).getSigned();

                        System.out.println("genderMale"+  gender );

                        if(gender == "Male") {
                            rbMale.setChecked(true);
                        } else
                        {
                            rbFemale.setChecked(true);
                        }

                        if(isSigned)
                        {
                            cbSigned.setChecked(true);
                        }
                        else
                        {
                            cbSigned.setChecked(false);
                        }

                        tenantArrayList.remove(position);

                        isEdit = false;
                    }
                }

            } else
            {
                tvTitle.setText("Add New Tenant");
            }
        }


        etCheckIn.setInputType(InputType.TYPE_NULL);
        etCheckOut.setInputType(InputType.TYPE_NULL);


        etCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(etCheckIn);
                moveIn = etCheckIn.getText().toString();
            }
        });

        etCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(etCheckOut);
                moveOut = etCheckOut.getText().toString();
            }
        });

        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callHomeFragment();
            }
        });



        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (etPassport.getText().toString().equals("")) {
                    etPassport.setHint("Please enter tenant ID number");
                    etPassport.setHintTextColor(getResources().getColor(R.color.colorRed));
                } else if (etFullName.getText().toString().equals("")) {
                    etFullName.setHint("Please enter tenant full name");
                    etFullName.setHintTextColor(getResources().getColor(R.color.colorRed));
                } else if (etCheckIn.getText().toString().equals("")) {
                    etCheckIn.setHint("Move In");
                    etCheckIn.setHintTextColor(getResources().getColor(R.color.colorRed));
                } else if (etCheckOut.getText().toString().equals("")) {
                    etCheckOut.setHint("Move Out");
                    etCheckOut.setHintTextColor(getResources().getColor(R.color.colorRed));
                } else if (etProfession.getText().toString().equals("")) {
                    etProfession.setHint("Please enter tenant occupation");
                    etProfession.setHintTextColor(getResources().getColor(R.color.colorRed));
                } else if (etPhoneNumber.getText().toString().equals("")) {
                    etPhoneNumber.setHint("Please enter tenant phone number");
                    etPhoneNumber.setHintTextColor(getResources().getColor(R.color.colorRed));

                } else if (etPhoneNumber.getText().toString().length() != 10) {
                    etPhoneNumber.setText("");
                    etPhoneNumber.setHint("Incorrect phone number");
                    etPhoneNumber.setHintTextColor(getResources().getColor(R.color.colorRed));

                } else if (etDepositPaid.getText().toString().equals("")) {
                    etDepositPaid.setHint("Please enter deposit paid");
                    etDepositPaid.setHintTextColor(getResources().getColor(R.color.colorRed));
                }
                else {

                    if (rbMale.isChecked()) {
                        gender = "Male";
                    } else {
                        gender = "Female";
                    }



                    if (cbSigned.isChecked()) {
                        isSigned = true;
                    } else if (!cbSigned.isChecked()) {
                        isSigned = false;
                    }

                    System.out.println("genderclick"+gender);

                    tenant = new Tenant(etPassport.getText().toString(),
                            etFullName.getText().toString(),
                            etCheckIn.getText().toString(),
                            etCheckOut.getText().toString(),
                            gender,
                            etProfession.getText().toString(),
                            etPhoneNumber.getText().toString(),
                            etDepositPaid.getText().toString(),
                            isSigned
                    );

                    System.out.println("genderMaleNew"+  gender );

                    if(tenantArrayList == null)
                    {
                        tenantArrayList = new ArrayList<>();
                        tenantArrayList.add(tenant);
                        saveData();
                    }
                    else {
                        tenantArrayList.add(tenant);
                        saveData();
                    }
                }


            }
        });


        return rootView;
    }


    public void setDate(final EditText editText) {
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


    private void saveData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(tenantArrayList);
            editor.putString("newTenant", json);
            editor.apply();
          //  Toast.makeText(getContext(), "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
        }

        callHomeFragment();
    }

    private void callHomeFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("tenant", tenantArrayList);
        bundle.putString("name", name);

        android.app.Fragment fr = new HomeFragment();
        FragmentManager fm = getFragmentManager();
        fr.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container, fr);
        fragmentTransaction.commit();
    }


}
