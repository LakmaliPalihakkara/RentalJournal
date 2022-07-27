//package com.example.tenantjournal;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.SharedPreferences;
//
//public class LoggedUser {
//
//    Context currentContext;
//    private static LoggedUser mInstance = null;
//
//    public static LoggedUser getInstance() {
//        if (mInstance == null) {
//            mInstance = new LoggedUser();
//        }
//        return mInstance;
//    }
//
//    public void setContext(Context currentContext) {
//        this.currentContext = currentContext;
//    }
//
//
//    void saveSharedPreference(String key, String value) {
//        SharedPreferences sharedPref = currentContext.getSharedPreferences("FortressPreference", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString(key, value);
//        editor.commit();
//    }
//
//    void saveSharedPreferenceInt(String key, int value) {
//        SharedPreferences sharedPref = currentContext.getSharedPreferences("FortressPreference", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putInt(key, value);
//        editor.commit();
//    }
//
//    void saveSharedPreferenceBoolean(String key, boolean value) {
//        SharedPreferences sharedPref = currentContext.getSharedPreferences("FortressPreference", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putBoolean(key, value);
//        editor.commit();
//    }
//
//
//}
