package com.example.permissiondemo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_CONTACTS = 200;

    private static final int INT_CAMERA = 1;
    private static final int INT_CONTACTS = 2;

    private PermissionPreferences ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ref = new PermissionPreferences(this);
    }

    // check if the permission is already granted
    private int checkPermission(int permission){
        int status;

        if(permission == INT_CAMERA){
            status = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        }
        else{
            status = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS);
        }
        return status;
    }

    // function to request permission
    private void requestPermission(int permission){

        if(permission == INT_CAMERA){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA);
        }
        else{
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS},REQUEST_CONTACTS);
        }
    }


    private void displayExplanation(final int permission){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(permission == INT_CAMERA) {
            builder.setTitle("Important msg about the camera permission");
            builder.setMessage("We require this to access your camera without this the app cannot function properly");
        }
        else{
            builder.setTitle("Important msg about the contacts permission");
            builder.setMessage("We require this to access your contacts without this the app cannot function properly");
        }
        builder.setPositiveButton("Allow", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                if(permission == INT_CAMERA){
                    requestPermission(INT_CAMERA);
                }
                else{
                    requestPermission(INT_CONTACTS);
                }
            }
        });

        builder.setNegativeButton("Decline",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface,int i){
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void onCameraOpen(View v){

        if(checkPermission(INT_CAMERA) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.CAMERA)){
                displayExplanation(INT_CAMERA);
            }
            else if(ref.checkPermissionPreference("camera")){
                requestPermission(INT_CAMERA);
                ref.updatePermissionPreferences("camera");
            }
            else{
                Toast.makeText(this,"Please update the permission in your settings",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package",this.getPackageName(),null);
                intent.setData(uri);
                this.startActivity(intent);

            }
        }
        else{
            Toast.makeText(this,"You already have the camera permission",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ResultActivity.class);
            startActivity(intent);

        }

    }

    public void onContactsOpen(View v){
        if(checkPermission(INT_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_CONTACTS)){
                displayExplanation(INT_CONTACTS);
            }
            else if(ref.checkPermissionPreference("contacts")){
                requestPermission(INT_CONTACTS);
                ref.updatePermissionPreferences("contacts");
            }
            else{
                Toast.makeText(this,"Please update the permission in your settings",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package",this.getPackageName(),null);
                intent.setData(uri);
                this.startActivity(intent);
            }
        }
        else{
            Toast.makeText(this,"You already have the contacts permission",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ResultActivity.class);
            startActivity(intent);
        }
    }
}
