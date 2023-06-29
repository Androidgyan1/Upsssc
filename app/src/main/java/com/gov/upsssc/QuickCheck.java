package com.gov.upsssc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class QuickCheck extends AppCompatActivity {
    public static final int RequestPermissionCode = 1;
    FusedLocationProviderClient mFusedLocationClient;
    TextView quickrollno,quickrollphoto,device_name_quick_check,mobile_no_quick_check,show_longitute_quick,show_latitude_quick,quick_mac_address,ip_address_quick;
   // TextView rollno;
    ImageView scanbtn,canditate_camra_photo;
    String barcode;
    ImageView canditate_Photo;
    Bitmap bitmap;
   static EditText result_edittext;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    String PHOTO,REQ,fullName,DEVICE_NAMEQUICKCHECK,MOBILE_NO_QUICK,QUICK_LATITUDE,QUICK_LONGITUDE,MACValuequick,IP_ADDRESS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_check);
        device_name_quick_check = findViewById(R.id.device_name_quick_check);
        mobile_no_quick_check = findViewById(R.id.mobile_no_quick_check);
        show_latitude_quick = findViewById(R.id.show_latitude_quick);
        show_longitute_quick = findViewById(R.id.show_longitute_quick);
        quick_mac_address = findViewById(R.id.quick_mac_address);
        ip_address_quick = findViewById(R.id.ip_address_quick);

        /////mac address

        SharedPreferences MACShared = getSharedPreferences("myKey", MODE_PRIVATE);
        MACValuequick = MACShared.getString("MACAddress", "");
        quick_mac_address.setText(MACValuequick);


        /// ip address

        SharedPreferences ipAddress = getSharedPreferences("myKey", MODE_PRIVATE);
        IP_ADDRESS = ipAddress.getString("ipAddress", "");
        ip_address_quick.setText(IP_ADDRESS);


        ///////get location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // method to get the location
      //  getLastLocation();




        /////getmobileno
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String value = sharedPreferences.getString("quickcheck", "");
        mobile_no_quick_check.setText(value);




        ///getdevicename
        device_name_quick_check.setText(Build.BRAND);


        ////location value latitude

        SharedPreferences locationvalue = getSharedPreferences("myKey", MODE_PRIVATE);
        String value1 = locationvalue.getString("latitute", "");
        show_latitude_quick.setText(value1);

        ////longtitude value


        SharedPreferences longtitude = getSharedPreferences("myKey2", MODE_PRIVATE);
        String value2 = longtitude.getString("longitude", "");
        show_longitute_quick.setText(value2);





        getSupportActionBar().hide();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fullName = extras.getString("NamePhoto");

            // Extract any other user data you need

            // Display user data in TextViews
            quickrollphoto = findViewById(R.id.quickrollphoto);
            quickrollphoto.setText(fullName);

            // Display any other user data in corresponding TextViews
        }
        uploadtoserver();
        //scanbtn = findViewById(R.id.scanbtn);
        //rollno = findViewById(R.id.scantext);
        canditate_Photo = findViewById(R.id.canditate_Photo);
        result_edittext = findViewById(R.id.result_edittext);
        canditate_camra_photo = findViewById(R.id.canditate_camra_photo);


//        scanbtn.setOnClickListener(v -> {
//
//            startActivity(new Intent(getApplicationContext(), ScannerQuick.class));
//            // creating a new variable for our request queue
//            RequestQueue queue = Volley.newRequestQueue(QuickCheck.this);
//
//            barcode= result_edittext.getText().toString();
//
//            final JSONObject jsonObject = new JSONObject();
//            try {
//
//                jsonObject.put("Roll_Number",barcode);///scantext.toString()
//                jsonObject.put("MacAddress", "MAC123");
//                jsonObject.put("IP", "10:135:32:20");
//                jsonObject.put("MobileNo", "9852589455");
//                jsonObject.put("Longitude", "0.25.251.25445.455");
//                jsonObject.put("Latitude", "3.2.5225.2522.252");
//                jsonObject.put("DeviceName", "IPhone646");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://10.135.32.81/upssscai/api/Home/GetDetailsByRollNo", jsonObject, response -> {
//                // inside the on response method.
//                // we are hiding our progress bar.
//
//
//                // in below line we are making our card
//                // view visible after we get all the data.
//                try {
//                    Log.d("MSg",response.toString());
//                    // now we get our response from API in json object format.
//                    // in below line we are extracting a string with its key
//                    // value from our json object.
//                     PHOTO = response.getString("Photodata");
//                      REQ = response.getString("RequestId");
////                    String courseTracks = response.getString("courseTracks");
////                    String courseMode = response.getString("courseMode");
////                    String courseImageURL = response.getString("courseimg");
//
//                    // after extracting all the data we are
//                    // setting that data to all our views.
//                    /////imageview
//                    byte[] bytes=Base64.decode(PHOTO, Base64.DEFAULT);
//                    // Initialize bitmap
//                    bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                    canditate_Photo.setImageBitmap(bitmap);
//
//                    // we are using picasso to load the image from url.
//                } catch (JSONException e) {
//                    // if we do not extract data from json object properly.
//                    // below line of code is use to handle json exception
//                    e.printStackTrace();
//                }
//            }, new Response.ErrorListener() {
//                // this is the error listener method which
//                // we will call if we get any error from API.
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    // below line is use to display a toast message along with our error.
//                    Toast.makeText(QuickCheck.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
//                }
//            });
//            // at last we are adding our json
//            // object request to our request
//            // queue to fetch all the json data.
//            queue.add(jsonObjectRequest);
//        });
    }

    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            show_latitude_quick.setText(location.getLatitude() + "Latitute");
                            show_longitute_quick.setText(location.getLongitude() + "Longitude");
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            /// requestPermissions();
        }
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestNewLocationData() {
        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            show_latitude_quick.setText("Latitude: " + mLastLocation.getLatitude() + "");
            show_longitute_quick.setText("Longitude: " + mLastLocation.getLongitude() + "");
        }
    };
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }



    private void uploadtoserver() {

        fullName = quickrollphoto.getText().toString();
        DEVICE_NAMEQUICKCHECK = device_name_quick_check.getText().toString();
        MOBILE_NO_QUICK = mobile_no_quick_check.getText().toString();
        QUICK_LATITUDE = show_latitude_quick.getText().toString();
        QUICK_LONGITUDE = show_longitute_quick.getText().toString();
        MACValuequick = quick_mac_address.getText().toString();
        IP_ADDRESS = ip_address_quick.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(QuickCheck.this);

        final JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("Roll_Number", fullName);///scantext.toString()
            jsonObject.put("MacAddress", MACValuequick);
            jsonObject.put("IP", IP_ADDRESS);
            jsonObject.put("MobileNo", MOBILE_NO_QUICK);
            jsonObject.put("Longitude", QUICK_LONGITUDE);
            jsonObject.put("Latitude", QUICK_LATITUDE);
            jsonObject.put("DeviceName", DEVICE_NAMEQUICKCHECK);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://164.100.181.233/upssscai/api/Home/GetDetailsByRollNo", jsonObject, response -> {
            // inside the on response method.
            // we are hiding our progress bar.


            // in below line we are making our card
            // view visible after we get all the data.
            try {
                Log.d("MSg", response.toString());
                // now we get our response from API in json object format.
                // in below line we are extracting a string with its key
                // value from our json object.
                PHOTO = response.getString("Photodata");
                REQ = response.getString("RequestId");
//                    String courseTracks = response.getString("courseTracks");
//                    String courseMode = response.getString("courseMode");
//                    String courseImageURL = response.getString("courseimg");

                // after extracting all the data we are
                // setting that data to all our views.
                /////imageview
                byte[] bytes = Base64.decode(PHOTO, Base64.DEFAULT);
                // Initialize bitmap
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                canditate_Photo.setImageBitmap(bitmap);

                // we are using picasso to load the image from url.
            } catch (JSONException e) {
                // if we do not extract data from json object properly.
                // below line of code is use to handle json exception
                e.printStackTrace();
            }
        }, new Response.ErrorListener() {
            // this is the error listener method which
            // we will call if we get any error from API.
            @Override
            public void onErrorResponse(VolleyError error) {
                // below line is use to display a toast message along with our error.
                Toast.makeText(QuickCheck.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        }){
        protected Response<JSONObject> parseNetworkResponse (NetworkResponse response){
            // Get the response time and date
            // long responseTime = response.networkTimeMs;
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String fullDateTime = sdf.format(date);

            // Log or use the full date and time as needed
            Log.d("API Response", "Response Time: " + fullDateTime);

            // Return the response
            return super.parseNetworkResponse(response);
        }
    };
        // at last we are adding our json
        // object request to our request
        // queue to fetch all the json data.
        queue.add(jsonObjectRequest);


    }

    public void EnableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(QuickCheck.this,
                Manifest.permission.CAMERA)) {
            Toast.makeText(QuickCheck.this, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(QuickCheck.this, new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] result) {
        super.onRequestPermissionsResult(requestCode, permissions, result);
        switch (requestCode) {
            case RequestPermissionCode:
                if (result.length > 0 && result[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(QuickCheck.this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(QuickCheck.this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            Bitmap imageData = null;
            if (resultCode == RESULT_OK)
            {
                imageData = (Bitmap) data.getExtras().get("data");

               // canditate_camra_photo.setImageBitmap(imageData);

                Intent i = new Intent(this, QuickCheckValidate.class);
                i.putExtra("name", imageData );
                i.putExtra("Name",PHOTO);
                i.putExtra("REQ",REQ);
                startActivity(i);
                finish();

            }
            else if (resultCode == RESULT_CANCELED)
            {
                // User cancelled the image capture
            }
            else
            {
                // Image capture failed, advise user
            }
        }

    }

    public void validate(View view) {


        // create Intent to take a picture and return control to the calling application
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // start the image capture Intent
        startActivityForResult(intentCamera, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

    }
}