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
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Invegilater_Activity extends AppCompatActivity {

TextView mobilenoinvigelter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invegilater_);

        getSupportActionBar().hide();

        // method to get the location

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String fullName = extras.getString("Name");
            String CENTER_CODE = extras.getString("CenterCode");
            String DATEFROM = extras.getString("From");
            String UPTO = extras.getString("upto");
            String CENTER_NAME = extras.getString("center_name");
            String MOBILE_NO = extras.getString("mobileno");
            // Extract any other user data you need

            // Display user data in TextViews
            mobilenoinvigelter = findViewById(R.id.mobilenoinvigelter);
            TextView fullNameTextView = findViewById(R.id.Inname);
            TextView centercode = findViewById(R.id.centercode);
            TextView datefrom = findViewById(R.id.datefrom);
            TextView upto = findViewById(R.id.upto);
            TextView center_NAME = findViewById(R.id.centername);
            fullNameTextView.setText(fullName);
            centercode.setText(CENTER_CODE);
            datefrom.setText(DATEFROM);
            upto.setText(UPTO);
            center_NAME.setText(CENTER_NAME);
            mobilenoinvigelter.setText(MOBILE_NO);

            // Display any other user data in corresponding TextViews
        }
    }

    public void QickResult(View view) {
        String value = mobilenoinvigelter.getText().toString().trim();
        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("quickcheck", value);
        editor.apply();


        Intent i = new Intent(Invegilater_Activity.this, quickOnlyPhotoCode.class);
        startActivity(i);

    }

    public void DetailResult(View view) {

        String value = mobilenoinvigelter.getText().toString().trim();
        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("value", value);
        editor.apply();

        Intent i = new Intent(Invegilater_Activity.this, Code.class);
        startActivity(i);


    }

    public void munallycheck(View view) {

        String value = mobilenoinvigelter.getText().toString().trim();
        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("munallycheck", value);
        editor.apply();


        Intent i = new Intent(Invegilater_Activity.this, MunallyCheck.class);
        startActivity(i);
    }

    public void Cloaseapp(View view) {////// finishAffinity();/// System.exit(0);
        AlertDialog.Builder builder = new AlertDialog.Builder(Invegilater_Activity.this);

        // Set the message show for the Alert time
        builder.setMessage("Do you want to exit ?");

        // Set Alert Title
        builder.setTitle("Alert !");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            // When the user click yes button then app will close
            finishAffinity();
            System.exit(0);
        });

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();


    }

}