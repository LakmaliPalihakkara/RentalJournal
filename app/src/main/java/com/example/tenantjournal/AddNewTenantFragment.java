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
    RadioGroup rgGender;
    RadioButton rbMale, rbFemale;
    RadioButton selectedRadioButton;
    CheckBox cbSigned;
    boolean isEdit;
    int position;
    String name;

    View rootView;

    private ArrayList<Tenant> tenantArrayList = new ArrayList<Tenant>();

    String moveIn, moveOut;
    String gender;
    boolean isSigned;

    Tenant tenant;

//
//    TenantInformationAdapter tenantInformationAdapter;
//    RecyclerView recyclerView;


    public AddNewTenantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     rootView = inflater.inflate(R.layout.fragment_add_new_tenant, container, false);

        btSave = (Button) rootView.findViewById(R.id.bt_save);
        btClose = (Button) rootView.findViewById(R.id.bt_close);

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

        int radioId = rgGender.getCheckedRadioButtonId();
     //   rbMale = (RadioButton) rootView.findViewById(radioId);
        //   rbFemale = (RadioButton) rootView.findViewById(radioId);
        cbSigned = (CheckBox) rootView.findViewById(R.id.cb_agreement_signed);

        //   recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_rent);




        final Bundle bundle = getArguments();
        if (bundle != null) {
            tenantArrayList = bundle.getParcelableArrayList("tenant");
            isEdit = bundle.getBoolean("edit");
            position = bundle.getInt("position");
            name = bundle.getString("name");

            if(isEdit)
            {
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



                        isEdit = false;
                    }
                }

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

               // checkButton(rootView);

                tenantArrayList.remove(position);

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
//                else if (tenantArrayList == null) {
//                  //  tenantArrayList = new ArrayList<>();
//                    tenantArrayList.add(tenant);
//                    saveData();
//                }
                else {

//                    rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                            switch (i)
//                            {
//                                case R.id.rb_male:
//                                    gender = "Male";
//                                    break;
//
//                                case R.id.rb_female:
//                                    gender = "Female";
//                                    break;
//                            }
//                        }
//                    });

              //      checkButton();

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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb_male:
                if (checked)
                    // Pirates are the best
                    gender = "Male";
                    break;
            case R.id.rb_female:
                if (checked)
                    // Ninjas rule
                    gender = "Female";
                    break;
        }
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

    public void checkButton() {

        int selectedRadioButtonId = rgGender.getCheckedRadioButtonId();
//        selectedRadioButton = rgGender.findViewById(selectedRadioButtonId);
//        gender = selectedRadioButton.getText().toString();

        if (selectedRadioButtonId != -1) {
            selectedRadioButton = rgGender.findViewById(selectedRadioButtonId);
            String selectedRbText = selectedRadioButton.getText().toString();
          //  textView.setText(selectedRbText + " is Selected");
            gender = selectedRbText;
//        } else {
//          //  textView.setText("Nothing selected from the radio group");
//            gender = selectedRbText;
        }


        //  rbGender = v.findViewById(radioId);

//        if (rbMale.isChecked()) {
//            gender = rbMale.getText().toString();
//        } else {
//            gender = rbFemale.getText().toString();
//        }

//        if(cbSigned.isChecked())
//        {
//            isSigned = true;
//        }
//        else
//        {
//            isSigned = false;
//        }


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
            editor.putString("newTenant", json);
            editor.apply();
            Toast.makeText(getContext(), "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
        }

        callHomeFragment();
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
