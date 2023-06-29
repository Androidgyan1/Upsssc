package com.gov.upsssc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static androidx.constraintlayout.motion.widget.Debug.getLocation;


public class MainActivity extends AppCompatActivity implements LocationListener {
    // GPSTracker class
    GPSTracker gps;

    private LocationManager locationManager;

    TextView datafromcodescreen, device_name, mobile_no_main, show_latitude, show_longitude,MAC_Ad,ip_main_address;
    String SCANNER;
    LinearLayout linearLayout;
    private BarcodeView barcodeView;
    Button btnTakePhoto;
    Bitmap bitmap;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int RequestPermissionCode = 1;
    ImageView scanbtn;
    String latitude, longitude;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;


    @SuppressLint("StaticFieldLeak")
    static EditText scantext;
    TextView canditate_Name, canditate_Dob, canditate_Mobile, canditate_Address, canditate_District_Code, canditate_Centre_Code, canditate_Centre_Address, canditate_Exam_Date, canditate_Exam_Time, canditade_native_place, canditade_category_name, canditate_father_name, canditade_gender, canditate_District_name, canditate_Centre_name;
    ImageView canditate_Photo, canditate_Sign;
    String TEXTPHOTO, REQUEST_ID, fullName, DEVICE_NAMEPHONE, MOBILE_NO,LATITUDE,LONGITUDE,MACValue,IP_ADDRESS;
    private static final int REQUEST_LOCATION = 1;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scantext = findViewById(R.id.scantext);
        barcodeView = findViewById(R.id.barcode_scanner);
        btnTakePhoto = findViewById(R.id.button);
        device_name = findViewById(R.id.device_name);
        mobile_no_main = findViewById(R.id.mobile_no_main);
        show_latitude = findViewById(R.id.show_latitude);
        show_longitude = findViewById(R.id.show_longitute);
        MAC_Ad = findViewById(R.id.MAC_Ad);
        ip_main_address = findViewById(R.id.ip_main_address);

        // Get the location manager and TextView
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        /////location

       // mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // method to get the location
       // getLastLocation();

        ////location value latitude

        SharedPreferences locationvalue = getSharedPreferences("myKey", MODE_PRIVATE);
        String value1 = locationvalue.getString("latitute", "");
        show_latitude.setText(value1);


        ////longtitude value


        SharedPreferences longtitude = getSharedPreferences("myKey2", MODE_PRIVATE);
        String value2 = longtitude.getString("longitude", "");
        show_longitude.setText(value2);


////////device name
        device_name.setText(Build.BRAND);

/////// get mobile no
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String value = sharedPreferences.getString("value", "");
        mobile_no_main.setText(value);


        /////mac address

        SharedPreferences MACShared = getSharedPreferences("myKey", MODE_PRIVATE);
        MACValue = MACShared.getString("MACAddress", "");
        MAC_Ad.setText(MACValue);

        /// get ip address

        SharedPreferences ipAddress = getSharedPreferences("myKey", MODE_PRIVATE);
        IP_ADDRESS = ipAddress.getString("ipAddress", "");
        ip_main_address.setText(IP_ADDRESS);



        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fullName = extras.getString("Name");

            // Extract any other user data you need

            // Display user data in TextViews
            datafromcodescreen = findViewById(R.id.datafromcodescreen);
            datafromcodescreen.setText(fullName);

            // Display any other user data in corresponding TextViews
        }

        uploadtoserver();
        /// ip address


        ////iniliziting the varibiles
        linearLayout = findViewById(R.id.linearqrscandetail);
        canditate_Name = findViewById(R.id.canditate_Name);
        canditate_Dob = findViewById(R.id.canditate_Dob);
//        canditate_Mobile = findViewById(R.id.canditate_Mobile);
//        canditate_Address = findViewById(R.id.canditate_Address);
        canditate_District_Code = findViewById(R.id.canditate_District_Code);
        canditate_Centre_Code = findViewById(R.id.canditate_Centre_Code);
        canditate_Centre_Address = findViewById(R.id.canditate_Centre_Address);
        canditate_Exam_Date = findViewById(R.id.canditate_Exam_Date);
        canditate_Exam_Time = findViewById(R.id.canditate_Exam_Time);
        canditade_native_place = findViewById(R.id.canditade_native_place);
        canditade_category_name = findViewById(R.id.canditade_category_name);
        canditate_father_name = findViewById(R.id.canditate_father_name);
        canditade_gender = findViewById(R.id.canditade_gender);
        canditate_Photo = findViewById(R.id.canditate_Photo);
        canditate_Sign = findViewById(R.id.canditate_Sign);
        canditate_District_name = findViewById(R.id.canditate_District_name);
        canditate_Centre_name = findViewById(R.id.canditate_Centre_name);


        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // create Intent to take a picture and return control to the calling application
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // start the image capture Intent
                startActivityForResult(intent1, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);


            }
        });
        // scanbtn = (ImageView) findViewById(R.id.scanbtn);

//        scanbtn.setOnClickListener(view -> {
//
//
//
////            if (linearLayout.getVisibility()==View.INVISIBLE){
////                        linearLayout.setVisibility(View.VISIBLE);
////
////                   }
////
////
//            // creating a new variable for our request queue
//            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
//
//
//            final JSONObject jsonObject = new JSONObject();
//            try {
//                startActivity(new Intent(getApplicationContext(), scannerView.class));
//                final String BAR= scantext.getText().toString().trim();
//                jsonObject.put("Roll_Number",BAR);///scantext.toString()
//                jsonObject.put("MacAddress", "MAC123");
//                jsonObject.put("IP", "10:135:32:20");
//                jsonObject.put("MobileNo", "9852589455");
//                jsonObject.put("Longitude", "0.25.251.25445.455");
//                jsonObject.put("Latitude", "3.2.5225.2522.252");
//                jsonObject.put("DeviceName", "IPhone646");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://10.135.32.81/upssscai/api/Home/GetDetailsByRollNo", jsonObject, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//
//                    // inside the on response method.
//                    // we are hiding our progress bar.
//
//                    if (linearLayout.getVisibility()==View.GONE){
//                        linearLayout.setVisibility(View.VISIBLE);
//                    }
//
//                        // in below line we are making our card
//                        // view visible after we get all the data.
//                        try {
//                            Log.d("MSg", response.toString());
//                            // now we get our response from API in json object format.
//                            // in below line we are extracting a string with its key
//                            // value from our json object.
//                            // similarly we are extracting all the strings from our json object.
//                            String CANDITATE_NAME = response.getString("Candidate_Name");
//                            String CANDITATE_DOB = response.getString("DOB");
//                            String CANDITATE_DISTRICT_CODE = response.getString("District_Code");
//                            String EXAMINATION_DATE = response.getString("Examination_Date");
//                            String EXAMINATION_TIMING = response.getString("Examination_Time");
//                            String CENTER_ADDRESS = response.getString("Centre_Address");
//                            String CENTER_CODE = response.getString("Centre_Code");
//                            String NATIVE_PLACE = response.getString("NativeOfUP");
//                            String FATHER_NAME = response.getString("Fat_hus_name");
//                            String CATEGORY = response.getString("Cat_Name");
//                            String GENDER = response.getString("Gender");
//                            String PHOTO = response.getString("Photodata");
//                            String SIGN = response.getString("Signdata");
//                            String DISTRICT_NAME = response.getString("District_Name");
//                            String CENTER_NAME = response.getString("Center_Name");
//                            TEXTPHOTO = response.getString("Photodata");
//                            REQUEST_ID = response.getString("RequestId");
//
//
////                    String courseTracks = response.getString("courseTracks");
////                    String courseMode = response.getString("courseMode");
////                    String courseImageURL = response.getString("courseimg");
//
//                            // after extracting all the data we are
//                            // setting that data to all our views.
//                            canditate_Name.setText(CANDITATE_NAME);
//                            canditate_Dob.setText(CANDITATE_DOB);
//                            canditate_District_Code.setText(CANDITATE_DISTRICT_CODE);
//                            canditate_Exam_Date.setText(EXAMINATION_DATE);
//                            canditate_Exam_Time.setText(EXAMINATION_TIMING);
//                            canditate_Centre_Address.setText(CENTER_ADDRESS);
//                            canditate_Centre_Code.setText(CENTER_CODE);
//                            canditade_native_place.setText(NATIVE_PLACE);
//                            canditate_father_name.setText(FATHER_NAME);
//                            canditade_category_name.setText(CATEGORY);
//                            canditade_gender.setText(GENDER);
//                            canditate_District_name.setText(DISTRICT_NAME);
//                            canditate_Centre_name.setText(CENTER_NAME);
//                            /////imageview
//                            byte[] bytes = Base64.decode(PHOTO, Base64.DEFAULT);
//                            // Initialize bitmap
//                            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                            canditate_Photo.setImageBitmap(bitmap);
//
//                            /////imageview
//                            byte[] sign = Base64.decode(SIGN, Base64.DEFAULT);
//                            // Initialize bitmap
//                            Bitmap signbitmap = BitmapFactory.decodeByteArray(sign, 0, sign.length);
//                            canditate_Sign.setImageBitmap(signbitmap);
//
//
//                            // we are using picasso to load the image from url.
//                        } catch (JSONException e) {
//                            // if we do not extract data from json object properly.
//                            // below line of code is use to handle json exception
//                            e.printStackTrace();
//                        }
//
//                }
//            }, new Response.ErrorListener() {
//                // this is the error listener method which
//                // we will call if we get any error from API.
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    // below line is use to display a toast message along with our error.
//                    Toast.makeText(MainActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
//                }
//            });
//            // at last we are adding our json
//            // object request to our request
//            // queue to fetch all the json data.
//            queue.add(jsonObjectRequest);
//
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
                            show_latitude.setText(location.getLatitude() + "Latitute");
                            show_longitude.setText(location.getLongitude() + "Longitude");
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
            show_latitude.setText("Latitude: " + mLastLocation.getLatitude() + "");
            show_longitude.setText("Longitude: " + mLastLocation.getLongitude() + "");
        }
    };

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
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
                    Intent i = new Intent(this, CameraActivity.class);
                    i.putExtra("name", imageData );
                    i.putExtra("Name",TEXTPHOTO);
                    i.putExtra("REQ",REQUEST_ID);
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







       private void uploadtoserver() {
         fullName = datafromcodescreen.getText().toString();
         DEVICE_NAMEPHONE = device_name.getText().toString();
         MOBILE_NO = mobile_no_main.getText().toString();
         LATITUDE = show_latitude.getText().toString().trim();
         LONGITUDE = show_longitude.getText().toString().trim();
         MACValue = MAC_Ad.getText().toString();
         IP_ADDRESS = ip_main_address.getText().toString();


        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

         final JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("Roll_Number",fullName);///scantext.toString()
            jsonObject.put("MacAddress",MACValue);
            jsonObject.put("IP",IP_ADDRESS);
            jsonObject.put("MobileNo",MOBILE_NO);
            jsonObject.put("Longitude",LONGITUDE);
            jsonObject.put("Latitude",LATITUDE);
            jsonObject.put("DeviceName",DEVICE_NAMEPHONE);


            Log.d("paremeter",fullName);
            Log.d("mobile",MOBILE_NO);
            Log.d("longitute",LONGITUDE);
            Log.d("latitude",LATITUDE);
            Log.d("device",DEVICE_NAMEPHONE);
            Log.d("MAC",MACValue);
            Log.d("IP",IP_ADDRESS);
                } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://164.100.181.233/upssscai/api/Home/GetDetailsByRollNo", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // inside the on response method.
                // we are hiding our progress bar.


                // in below line we are making our card
                // view visible after we get all the data.
                try {
                    Log.d("MSg",response.toString());
                    // now we get our response from API in json object format.
                    // in below line we are extracting a string with its key
                    // value from our json object.
                    // similarly we are extracting all the strings from our json object.
                    String CANDITATE_NAME = response.getString("Candidate_Name");
                    String CANDITATE_DOB = response.getString("DOB");
                    String CANDITATE_DISTRICT_CODE = response.getString("District_Code");
                    String EXAMINATION_DATE = response.getString("Examination_Date");
                    String EXAMINATION_TIMING = response.getString("Examination_Time");
                    String CENTER_ADDRESS = response.getString("Centre_Address");
                    String CENTER_CODE = response.getString("Centre_Code");
                    String NATIVE_PLACE = response.getString("NativeOfUP");
                    String FATHER_NAME = response.getString("Fat_hus_name");
                    String CATEGORY = response.getString("Cat_Name");
                    String GENDER = response.getString("Gender");
                    String PHOTO = response.getString("Photodata");
                    String SIGN = response.getString("Signdata");
                    String DISTRICT_NAME = response.getString("District_Name");
                    String CENTER_NAME = response.getString("Center_Name");
                    TEXTPHOTO = response.getString("Photodata");
                    REQUEST_ID = response.getString("RequestId");
//                    String courseTracks = response.getString("courseTracks");
//                    String courseMode = response.getString("courseMode");
//                    String courseImageURL = response.getString("courseimg");

                    // after extracting all the data we are
                    // setting that data to all our views.
                    canditate_Name.setText(CANDITATE_NAME);
                    canditate_Dob.setText(CANDITATE_DOB);
                    canditate_District_Code.setText(CANDITATE_DISTRICT_CODE);
                    canditate_Exam_Date.setText(EXAMINATION_DATE);
                    canditate_Exam_Time.setText(EXAMINATION_TIMING);
                    canditate_Centre_Address.setText(CENTER_ADDRESS);
                    canditate_Centre_Code.setText(CENTER_CODE);
                    canditade_native_place.setText(NATIVE_PLACE);
                    canditate_father_name.setText(FATHER_NAME);
                    canditade_category_name.setText(CATEGORY);
                    canditade_gender.setText(GENDER);
                    canditate_District_name.setText(DISTRICT_NAME);
                    canditate_Centre_name.setText(CENTER_NAME);
                 /////imageview
                    byte[] bytes=Base64.decode(PHOTO, Base64.DEFAULT);
                    // Initialize bitmap
                     bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    canditate_Photo.setImageBitmap(bitmap);

                    /////imageview
                    byte[] sign=Base64.decode(SIGN, Base64.DEFAULT);
                    // Initialize bitmap
                    Bitmap signbitmap= BitmapFactory.decodeByteArray(sign,0,sign.length);
                    canditate_Sign.setImageBitmap(signbitmap);


                    // we are using picasso to load the image from url.
                } catch (JSONException e) {
                    // if we do not extract data from json object properly.
                    // below line of code is use to handle json exception
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            // this is the error listener method which
            // we will call if we get any error from API.
            @Override
            public void onErrorResponse(VolleyError error) {
                // below line is use to display a toast message along with our error.
                Toast.makeText(MainActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
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

    @Override
    public void onLocationChanged(Location location) {
        // Update the TextView with the current latitude and longitude
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        show_latitude.setText("Latitude: " + latitude);
        show_longitude.setText("Latitude: " + longitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}