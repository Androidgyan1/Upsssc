package com.gov.upsssc;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class QuickCheckValidate extends AppCompatActivity {
    LinearLayout quickstatus;
    ImageView quickvalidate,cameraImage;
    Bitmap bitmap,bitmapquickcheck;
    EditText quickedittext;
    String encodedimage;
    String apiurl = "https://dhwani.up.nic.in/api/auth/CheckforSameFaceBase64";
    TextView messege,messegevalue,requestid;
    ProgressBar quick_progress;
     String MESSEGE;
     String MATCHED_VALUE;
     String REQUEST_ID;
     Button validatequick;
     ImageView photostringquickcheck,matchedquick,notmatchedquick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_check_validate);
        quickstatus = findViewById(R.id.quickstatus);
        quick_progress = findViewById(R.id.quick_progress);
        validatequick = findViewById(R.id.validatequick);
        messege = findViewById(R.id.messege);
        messegevalue = findViewById(R.id.matchedvalue);
        photostringquickcheck = findViewById(R.id.imageView_quick);
        matchedquick = findViewById(R.id.matchedquick);
        notmatchedquick = findViewById(R.id.notmatchedquick);

        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            String fullName = extras.getString("Name");
            byte[] bytes=Base64.decode(fullName, Base64.DEFAULT);
            // Initialize bitmap
            bitmapquickcheck= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            photostringquickcheck.setImageBitmap(bitmapquickcheck);

            String request_id = extras.getString("REQ");
            quickedittext = findViewById(R.id.quickedittext);
            requestid = findViewById(R.id.requestid);
            quickedittext.setText(fullName);
            requestid.setText(request_id);
            Log.d("photo", "onCreate: " +fullName);
        }

        bitmap = getIntent().getExtras().getParcelable("name");
        cameraImage = (ImageView) findViewById(R.id.cameraImage);
        cameraImage.setImageBitmap(bitmap);
        encodebitmap(bitmap);
    }

    public void validate(View view) {

        quick_progress.setVisibility(View.VISIBLE);


        Toast.makeText(QuickCheckValidate.this, "please wait.....", Toast.LENGTH_SHORT).show();

        uploadtoserver();

  new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
          uploadtoSumbit();
          validatequick.setClickable(false);
      }
  },20000);







    }

    private void uploadtoSumbit() {
        validatequick.setClickable(false);
       // quick_progress.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(QuickCheckValidate.this);
          MESSEGE= messege.getText().toString();
          MATCHED_VALUE = messegevalue.getText().toString();
          REQUEST_ID = requestid.getText().toString();

        final JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("RequestId",REQUEST_ID);///scantext.toString()
            jsonObject.put("PhotoStatus",MESSEGE);
            jsonObject.put("MatchedValue",MATCHED_VALUE);
            jsonObject.put("PhotoBase64", encodedimage);
            Log.d("image", "uploadtoSumbit: "+encodedimage);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://164.100.181.233/upssscai/api/Home/SubmitStatus", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // inside the on response method.
                // we are hiding our progress bar.


                // in below line we are making our card
                // view visible after we get all the data.
                Log.d("MSg",response.toString());

              //  Toast.makeText(QuickCheckValidate.this,response.toString(),Toast.LENGTH_LONG).show();
              //  quick_progress.setVisibility(View.INVISIBLE);
                // now we get our response from API in json object format.
                // in below line we are extracting a string with its key
                // value from our json object.
                // similarly we are extracting all the strings from our json object.
//                    String courseTracks = response.getString("courseTracks");
//                    String courseMode = response.getString("courseMode");
//                    String courseImageURL = response.getString("courseimg");

                // after extracting all the data we are


                // we are using picasso to load the image from url.
            }
        }, new Response.ErrorListener() {
            // this is the error listener method which
            // we will call if we get any error from API.
            @Override
            public void onErrorResponse(VolleyError error) {
                // below line is use to display a toast message along with our error.
                Toast.makeText(QuickCheckValidate.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
               // quick_progress.setVisibility(View.INVISIBLE);
            }
        });
        // at last we are adding our json
        // object request to our request
        // queue to fetch all the json data.
        queue.add(jsonObjectRequest);





    }

    private void uploadtoserver() {
        validatequick.setClickable(false);
        quick_progress.setVisibility(View.VISIBLE);
        final String PHOTO_STRING = quickedittext.getText().toString();

        final StringRequest request = new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  Toast.makeText(QuickCheckValidate.this, response, Toast.LENGTH_SHORT).show();

                // on below line passing our response to json object.
                try {
                    JSONObject jsonObject1 = new JSONObject(response);

                    String MESSEGE = jsonObject1.getString("Matched");

                    if (MESSEGE.equals("1")){
                       // String MESSEGE = jsonObject1.getString("Matched Value");
                        String REQUEST_ID = jsonObject1.getString("dept_request_id");
                        messege.setText(MESSEGE);
                        // messegevalue.setText(MATCH_VALUE);
                        quick_progress.setVisibility(View.INVISIBLE);
                        quickstatus.setVisibility(View.GONE);

                        matchedquick.setVisibility(View.VISIBLE);


                    }else {
                        quick_progress.setVisibility(View.INVISIBLE);

                        notmatchedquick.setVisibility(View.VISIBLE);


                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.d("msg", "onResponse: "+response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                quick_progress.setVisibility(View.INVISIBLE);
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("imagefile1",PHOTO_STRING);
                map.put("imagefile2",encodedimage);
               // map.put("api_code", "3");
                map.put("api_key", "c295e536-6e7b-4b9a-aaa5-0a68ab2c6bb3");
               // map.put("project_code", "3");
                map.put("dept_request_id","AI_Ram");
                return map;

            }
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
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
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        Log.d("upload", "uploadtoserver:");
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

    private void encodebitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream1);
        byte[] byteofimages1 = byteArrayOutputStream1.toByteArray();
        encodedimage = Base64.encodeToString(byteofimages1, Base64.DEFAULT);


    }

    public void sentoserver(View view) {
      finish();
        // uploadtoSumbit();
       // uploadtoSumbit();
    }
}

