package com.gov.upsssc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

public class quickOnlyPhotoCode extends AppCompatActivity {
    private  CodeScanner codeScanner;
    private TextView codeData;
    private CodeScannerView scannerview;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_only_photo_code);
        scannerview = findViewById(R.id.scanner_view);
        codeData = findViewById(R.id.quickchecj);
        getSupportActionBar().hide();

        runCodeScan();
        scannerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();
            }
        });
    }

    private void runCodeScan() {
        codeScanner= new CodeScanner(this,scannerview);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFormats(CodeScanner.ALL_FORMATS);
        codeScanner.setScanMode(ScanMode.CONTINUOUS);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                data = result.getText();
                codeData.setText(data);
                Intent i = new Intent(quickOnlyPhotoCode.this, QuickCheck.class);
                i.putExtra("NamePhoto", data);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }
    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }
}