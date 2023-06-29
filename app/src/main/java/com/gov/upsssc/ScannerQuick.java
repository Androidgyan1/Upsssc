package com.gov.upsssc;

import android.Manifest;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerQuick extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    ZXingScannerView ScannerQuick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerQuick=new ZXingScannerView(this);
        setContentView(ScannerQuick);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        ScannerQuick.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void handleResult(Result rawResult) {
        QuickCheck.result_edittext.setText(rawResult.getText());
        // Resume camera preview
        ScannerQuick.resumeCameraPreview(this);
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ScannerQuick.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScannerQuick.setResultHandler(this);
        ScannerQuick.startCamera();
    }
}