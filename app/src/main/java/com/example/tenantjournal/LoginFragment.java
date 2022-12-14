package com.example.tenantjournal;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;


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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    EditText etUsername, etPassword;
    Button btLogin;
    TextView tvSignUp;
    Landlord landlord;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etUsername = view.findViewById(R.id.et_username);
        etPassword = view.findViewById(R.id.et_password);
        btLogin = view.findViewById(R.id.bt_login);
        tvSignUp = view.findViewById(R.id.tv_signup);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fr = new SignUpFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fr);
                fragmentTransaction.commit();
            }
        });

        return view;
    }


    private void loadData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("newTenant1", "");
            landlord = gson.fromJson(json, Landlord.class);

            if (landlord != null) {

                if (etUsername.getText().toString().equals("")) {

                    etUsername.setHint("Please enter your username");
                    etUsername.setHintTextColor(getResources().getColor(R.color.colorRed));

                } else if (etPassword.getText().toString().equals("")) {
                    etPassword.setHint("Please enter your password");
                    etPassword.setHintTextColor(getResources().getColor(R.color.colorRed));
                } else if ((!etUsername.getText().toString().equals(landlord.getUsername())) || (!etUsername.getText().toString().equals(landlord.getUsername()))) {
                    etUsername.setText("");

                    etUsername.setHint("Incorrect username or email");
                    etUsername.setHintTextColor(getResources().getColor(R.color.colorRed));

                } else if (!etPassword.getText().toString().equals(landlord.getPassword())) {
                    etPassword.setText("");

                    etPassword.setHint("Incorrect password");
                    etPassword.setHintTextColor(getResources().getColor(R.color.colorRed));
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", landlord.getUsername());
                    Fragment fr = new HomeFragment();
                    FragmentManager fm = getFragmentManager();
                    fr.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.container, fr);
                    fragmentTransaction.commit();
                }

                System.out.println(landlord.getUsername());

            } else {
                Toast.makeText(getContext(), "User not available", Toast.LENGTH_SHORT).show();

            }
        }
    }


}
