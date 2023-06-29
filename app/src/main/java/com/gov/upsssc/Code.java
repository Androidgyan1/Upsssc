package com.gov.upsssc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;


public class Code extends AppCompatActivity {
    private  CodeScanner CodeScanner;
   private TextView  codeData;
    private CodeScannerView scannerView;
    String data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_scanner);
        getSupportActionBar().hide();

        scannerView = findViewById(R.id.scanner_view);
        codeData = findViewById(R.id.textview);

        runCodeScan();

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CodeScanner.startPreview();
            }
        });

    }

    public void runCodeScan() {
        CodeScanner = new CodeScanner(this,scannerView);
        CodeScanner.setAutoFocusEnabled(true);
        CodeScanner.setFormats(CodeScanner.ALL_FORMATS);
        CodeScanner.setScanMode(ScanMode.CONTINUOUS);

        CodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                 data = result.getText();
                codeData.setText(data);
                Intent i = new Intent(Code.this, MainActivity.class);
                i.putExtra("Name", data);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        CodeScanner.startPreview();
    }
    @Override
    protected void onPause() {
        CodeScanner.releaseResources();
        super.onPause();
    }

}