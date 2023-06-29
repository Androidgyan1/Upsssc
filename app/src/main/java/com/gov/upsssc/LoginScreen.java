package com.gov.upsssc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;


public class LoginScreen extends AppCompatActivity {
    String deviceId;
    public static final String SECURE_SETTINGS_BLUETOOTH_ADDRESS = "bluetooth_address";
    String imei;
    //   EditText ip_address;
    public static final int PERMISSION_REQUEST_READ_PHONE_STATE = 100;
    public static final int RequestPermissionCode = 1;
    ProgressBar login_progress;
    Button login;
    EditText mobileNumber, mac_address;
    TextView username, MAC_address, ip_address, secureid;
    String fullName, CenterCode, Datefrom, upto, center_name,formattedDateTime,formattedDateTimeupto;

    @SuppressLint("HardwareIds")
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        ip_address = findViewById(R.id.ip_address);

       // displaySerialNumber();
        //getSerialNumber();

        // Get the IP address and display it in the TextView
        try {
            String ipAddress = getIPAddress();
            ip_address.setText(ipAddress);
        } catch (SocketException e) {
            e.printStackTrace();
        }


        getSupportActionBar().hide();
        login = findViewById(R.id.login);
        //mac_address = findViewById(R.id.mac_address);
        mobileNumber = findViewById(R.id.mobile_no);
        username = findViewById(R.id.username);
        login_progress = findViewById(R.id.login_progress);
        //  ip_address = findViewById(R.id.ip_address);
        MAC_address = findViewById(R.id.MAC_address);
        // secureid = findViewById(R.id.secureid);

        //@SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

// in the below line, we are initializing our variables.


        //  secureid.setText(getDeviceID(telephonyManager));
//      TelephonyManager  telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//
//        // in the below line, we are setting our imei to our text view.
//        imei = telephonyManager.;
//        MAC_address.setText(imei);

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
//        }
        // getSerialNumber();

       // getMacAddress();

        //  getDeviceId();


MAC_address.setText(Build.ID);

// The user has not granted permission.
// You can show a dialog to ask the user to grant permission.
//        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
//        mac_address.setText("" + wifiInfo.getMacAddress());

        //  EnableRuntimePermission();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /////for mac address

                String value = MAC_address.getText().toString().trim();
                SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("MACAddress", value);
                editor.apply();


                ////for ip address

                String ipaddress = ip_address.getText().toString().trim();
                SharedPreferences ipshared = getSharedPreferences("myKey", MODE_PRIVATE);
                SharedPreferences.Editor ipeditor = ipshared.edit();
                ipeditor.putString("ipAddress", ipaddress);
                ipeditor.apply();


                login_progress.setVisibility(View.VISIBLE);

                //first getting the values
                RequestQueue queue = Volley.newRequestQueue(LoginScreen.this);

                final String MOBILENUMBER = mobileNumber.getText().toString();
                final String MAC_ADDRESS = MAC_address.getText().toString();////set in parameter of mac address
                final JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put("MobileNo", MOBILENUMBER);///scantext.toString()
                    jsonObject.put("MacAddress", MAC_ADDRESS);

                    Log.d("parameter", MAC_ADDRESS);
                    Log.d("mobile no", MOBILENUMBER);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://164.100.181.233/upssscai/api/Home/DeviceAut", jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // inside the on response method.
                        // we are hiding our progress bar.


                        // in below line we are making our card
                        // view visible after we get all the data.
                        Log.d("MSg", response.toString());
                        // now we get our response from API in json object format.
                        // in below line we are extracting a string with its key
                        // value from our json object.
                        // similarly we are extracting all the strings from our json object.

                        if (!response.isNull("IsActive")) {
                            // Move to the next screen
                            try {

                                fullName = response.getString("Name");
                                CenterCode = response.getString("Centre_Code");
                                Datefrom = response.getString("DateTimeFrom");
                                // Format the date and time string according to your custom format
                                formattedDateTime = formatDateAndTime(Datefrom);
                                upto = response.getString("DateTimeTo");
                                formattedDateTimeupto = formatDateAndTimeupto(upto);
                                center_name = response.getString("Center_Name");


                                Intent intent = new Intent(LoginScreen.this, Invegilater_Activity.class);
                                login_progress.setVisibility(View.INVISIBLE);
                                intent.putExtra("Name", fullName);
                                intent.putExtra("CenterCode", CenterCode);
                                intent.putExtra("From", formattedDateTime);
                                intent.putExtra("upto", formattedDateTimeupto);
                                intent.putExtra("center_name", center_name);
                                intent.putExtra("mobileno", MOBILENUMBER);
                                startActivity(intent);
                                finish();


                            } catch (JSONException e) {
                                e.printStackTrace();
                                // Show an error message
                                Toast.makeText(LoginScreen.this, "User not found" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                login_progress.setVisibility(View.INVISIBLE);
                            }

                        } else {
                            // Show an error message
                            Toast.makeText(LoginScreen.this,""+ response, Toast.LENGTH_LONG).show();
                            login_progress.setVisibility(View.INVISIBLE);
                        }
                        //                    String courseTracks = response.getString("courseTracks");
//                    String courseMode = response.getString("courseMode");
//                    String courseImageURL = response.getString("courseimg");

                        // after extracting all the data we are
                        // setting that data to all our views.


                        // we are using picasso to load the image from url.
                    }
                }, new Response.ErrorListener() {
                    // this is the error listener method which
                    // we will call if we get any error from API.
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // below line is use to display a toast message along with our error.
                        Toast.makeText(LoginScreen.this, "Fail to get data.." + error.getMessage(), Toast.LENGTH_SHORT).show();
                        login_progress.setVisibility(View.INVISIBLE);
                    }
                })
                {
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


                    @Override
                    public String getBodyContentType() {
                        // as we are passing data in the form of url encoded
                        // so we are passing the content type below
                        return "application/json; charset=UTF-8";
                    }

                };
                // at last we are adding our json
                // object request to our request
                // queue to fetch all the json data.
                queue.add(jsonObjectRequest);

            }
        });
    }

    private String formatDateAndTimeupto(String upto) {
        // Parse the server date and time string into a Date object
        SimpleDateFormat serverDateTimeFormatupto = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa", Locale.getDefault());
        Date dateTimeupto;
        try {
            dateTimeupto = serverDateTimeFormatupto.parse(upto);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid date and time";
        }

        // Define your custom date and time format
        SimpleDateFormat customFormatupto = new SimpleDateFormat("dd/MMM/yyyy, hh:mm aa", Locale.getDefault());

        // Format the date and time using your custom format
        return customFormatupto.format(dateTimeupto);

    }

    private String formatDateAndTime(String datefrom) {
        // Parse the server date and time string into a Date object
        SimpleDateFormat serverDateTimeFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa", Locale.getDefault());
        Date dateTime;
        try {
            dateTime = serverDateTimeFormat.parse(datefrom);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid date and time";
        }

        // Define your custom date and time format
        SimpleDateFormat customFormat = new SimpleDateFormat("dd/MMM/yyyy, hh:mm aa", Locale.getDefault());

        // Format the date and time using your custom format
        return customFormat.format(dateTime);
    }


//    private void getSerialNumber() {
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (telephonyManager != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                String serialNumber = Build.getSerial();
//                // Use the serial number as needed
//                MAC_address.setText(serialNumber);
//                Log.d("Serial,",serialNumber);
//            } else {
//                // For older devices
//                String serialNumber = Build.SERIAL;
//                // Use the serial number as needed
//                MAC_address.setText(serialNumber);
//            }
//        }
//    }

    private String getIPAddress() throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress address = addresses.nextElement();
                if (!address.isLinkLocalAddress() && !address.isLoopbackAddress() && address instanceof Inet4Address) {
                    return address.getHostAddress();
                }
            }
        }
        return null;
    }


    @SuppressLint("HardwareIds")
    private void getMacAddress() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String macAddress;
        if (wifiManager != null) {
            macAddress = wifiManager.getConnectionInfo().getMacAddress();
            if (macAddress == null || macAddress.equals("02:00:00:00:00:00")) {
                try {
                    List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                    for (NetworkInterface intf : interfaces) {

                        byte[] mac = intf.getHardwareAddress();
                        if (mac != null) {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (byte b : mac) {
                                stringBuilder.append(String.format("%02x:", b));
                            }
                            if (stringBuilder.length() > 0) {
                                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                            }
                            macAddress = stringBuilder.toString();
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    macAddress = "Error retrieving MAC address";
                }
            }
        } else {
            macAddress = "Error retrieving MAC address";
        }
        MAC_address.setText(macAddress);
    }

    public void EnableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(LoginScreen.this,
                Manifest.permission.CAMERA)) {
            Toast.makeText(LoginScreen.this, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(LoginScreen.this, new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);
        }
    }

    //    public void Login(View view) {
//
//    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] result) {
        super.onRequestPermissionsResult(requestCode, permissions, result);
        switch (requestCode) {
            case RequestPermissionCode:
                if (result.length > 0 && result[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(LoginScreen.this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();
                } else {
                    // Toast.makeText(LoginScreen.this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();
                }

                break;
                }

        }

   }