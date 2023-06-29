package com.gov.upsssc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class SplashScreen extends AppCompatActivity {
    private static final int REQUEST_CODE_ENABLE_ADMIN = 1;
    // GPSTracker class
    GPSTracker gps;
    TextView latitudeshow,Longitudeshow,serialno;
    private LocationManager locationManager;
    String SERIAL;
    TelephonyManager manager;
    @SuppressLint({"HardwareIds", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ///////permission
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BIND_DEVICE_ADMIN,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        enableDeviceAdmin();
// check if all permissions are allowed or granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(SplashScreen.this, "Permission Granted", Toast.LENGTH_LONG).show();

// do you work now
                        }

// check for permanent decline of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {

// permission denied permanently, navigate user to app settings for granting permissions
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken permissoonToken) {
                        permissoonToken.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();





        getSupportActionBar().hide();
        latitudeshow = findViewById(R.id.latitude);
        Longitudeshow = findViewById(R.id.longitude);
        serialno = findViewById(R.id.serialno);
        // create class object
        gps = new GPSTracker(SplashScreen.this);


        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            latitudeshow.setText(""+latitude);
            Longitudeshow.setText(""+longitude);
            // \n is for new line
//            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
//                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

    }

    private void enableDeviceAdmin() {
        ComponentName componentName = new ComponentName(this, MyDeviceAdminReceiver.class);
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Explanation why this permission is required");
        startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);
    }


    //  manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //  manager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
       // serial_no.setText(""+manager.getDeviceId());

//        SERIAL = manager.getDeviceId();
//        serial_no.setText(SERIAL);

    public void splashNext(View view) {

        String value = latitudeshow.getText().toString().trim();
        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("latitute", value);
        editor.apply();


        String value2 = Longitudeshow.getText().toString().trim();
        SharedPreferences sharedPref2 = getSharedPreferences("myKey2", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPref2.edit();
        editor2.putString("longitude", value2);
        editor2.apply();




        Intent i = new Intent(SplashScreen.this, LoginScreen.class);
        startActivity(i);
        finish();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ENABLE_ADMIN) {
            if (resultCode == RESULT_OK) {
                // Device admin permission granted
                Toast.makeText(this, "Device admin enabled", Toast.LENGTH_SHORT).show();
            } else {
                // Device admin permission denied
                Toast.makeText(this, "Device admin permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
