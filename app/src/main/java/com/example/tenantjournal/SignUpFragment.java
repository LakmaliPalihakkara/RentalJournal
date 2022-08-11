package com.example.tenantjournal;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tenantjournal.Model.Landlord;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    EditText etUsername, etEmail, etPassword, etConfirmPassword;
    Button btSignUp;
    TextView tvLogin;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        etUsername = view.findViewById(R.id.et_username);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        etConfirmPassword = view.findViewById(R.id.et_confirm_password);
        btSignUp = view.findViewById(R.id.bt_sign_up);
        tvLogin = view.findViewById(R.id.tv_login);

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               validation();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fr = new LoginFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fr);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void validation()
    {

        if(etUsername.getText().toString().equals("")){

            etUsername.setHint("Please enter your username");
            etUsername.setHintTextColor(getResources().getColor(R.color.colorRed));

        }
        else if (etEmail.getText().toString().equals(""))
        {
            etEmail.setHint("Please enter your email");
            etEmail.setHintTextColor(getResources().getColor(R.color.colorRed));
        }

        else if(!isValidEmail(etEmail.getText().toString()))
        {
            etEmail.setText("");
            etEmail.setHint("Please enter valid email");
            etEmail.setHintTextColor(getResources().getColor(R.color.colorRed));
        }

        else if (etPassword.getText().toString().equals(""))
        {
            etPassword.setHint("Please enter your password");
            etPassword.setHintTextColor(getResources().getColor(R.color.colorRed));
        }

        else if (etConfirmPassword.getText().toString().equals(""))
        {
            etConfirmPassword.setHint("Please enter your confirm password");
            etConfirmPassword.setHintTextColor(getResources().getColor(R.color.colorRed));
        }
        else if(!etPassword.getText().toString().equals(etConfirmPassword.getText().toString()))
        {

            etPassword.setText("");
            etConfirmPassword.setText("");

            etPassword.setHint("Password not matched");
            etPassword.setHintTextColor(getResources().getColor(R.color.colorRed));

            etConfirmPassword.setHint("Password not matched");
            etConfirmPassword.setHintTextColor(getResources().getColor(R.color.colorRed));


        }
        else
        {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                SharedPreferences mobilePreference = getContext().getSharedPreferences("shared preferences", getContext().MODE_PRIVATE);
                mobilePreference.edit().remove("newPayment").commit();
                mobilePreference.edit().remove("newTenant1").commit();
                mobilePreference.edit().remove("newTenant").commit();
            }

            saveData();

            Bundle bundle = new Bundle();
            bundle.putString("name",etUsername.getText().toString());

            Fragment fr = new HomeFragment();
            FragmentManager fm = getFragmentManager();
            fr.setArguments(bundle);
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.container, fr);
            fragmentTransaction.commit();
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void saveData() {


          //  newTenantArrayList.add(new Landlord(etUsername.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString()));

        Landlord landlord = new Landlord(etUsername.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();

            String json = gson.toJson(landlord);
            editor.putString("newTenant1", json);
            editor.commit();
          //  Toast.makeText(getContext(), "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
        }

    }
}
