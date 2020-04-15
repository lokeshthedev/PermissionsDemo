package com.example.permissiondemo;

import android.content.Context;
import android.content.SharedPreferences;

class PermissionPreferences {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    PermissionPreferences(MainActivity context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.permission_preference),Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //Updating preferences for permission
    void updatePermissionPreferences(String permission){
        if(permission.equals("camera")){
            editor.putBoolean(context.getString(R.string.permission_camera),true);
            editor.commit();
        }
        else{
            editor.putBoolean(context.getString(R.string.permission_contacts),true);
            editor.commit();
        }
    }

    // Checking to see if the permission was requested before
    boolean checkPermissionPreference(String permission){
        boolean isDisplayed;
        if(permission.equals("camera")){
            isDisplayed = sharedPreferences.getBoolean(context.getString(R.string.permission_camera),false);
        }
        else{
            isDisplayed = sharedPreferences.getBoolean(context.getString(R.string.permission_contacts),false);
        }
        return !isDisplayed;
    }
}
