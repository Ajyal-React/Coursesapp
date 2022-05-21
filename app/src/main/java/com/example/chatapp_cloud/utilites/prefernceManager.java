package com.example.chatapp_cloud.utilites;

import android.content.Context;
import android.content.SharedPreferences;

public class prefernceManager {
    private final SharedPreferences sharedPreferences;

    public prefernceManager(Context context){
        sharedPreferences = context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME,Context.MODE_PRIVATE);

    }

    public prefernceManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void putBoolean(String Key, Boolean value){
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putBoolean(Key,value);
        editor.apply();
    }
    public Boolean getBoolean(String Key){
        return sharedPreferences.getBoolean(Key,false);
    }

    public void putString(String Key,String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Key,value);
        editor.apply();
//        Getcurrentuser.getuid;
    }
    public String getString(String key){
        return sharedPreferences.getString(key,null);
    }
    public void clear(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
