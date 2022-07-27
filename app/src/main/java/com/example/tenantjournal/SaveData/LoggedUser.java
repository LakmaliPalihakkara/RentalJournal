package com.example.tenantjournal.SaveData;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import com.example.tenantjournal.Model.Tenant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class LoggedUser {
    Context currentContext;
    private static LoggedUser mInstance = null;

    ArrayList<Tenant> tenantArrayList = new ArrayList<>();

    public static LoggedUser getInstance() {
        if (mInstance == null) {
            mInstance = new LoggedUser();
        }
        return mInstance;
    }

    public void setContext(Context currentContext) {
        this.currentContext = currentContext;
    }


    public void saveData(ArrayList<Tenant> tenantArrayList) {
        this.tenantArrayList = tenantArrayList;


            SharedPreferences sharedPreferences = currentContext.getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(tenantArrayList);
            editor.putString("courses", json);
            editor.apply();
            Toast.makeText(currentContext, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();

    }


    public void loadData(ArrayList<Tenant> tenantArrayList) {

        this.tenantArrayList = tenantArrayList;
            SharedPreferences sharedPreferences = currentContext.getSharedPreferences("shared preferences", MODE_PRIVATE);
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
