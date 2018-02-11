package me.ez0ne.ouring.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class QrScanActivity extends Activity implements ZBarScannerView.ResultHandler {

    private ZBarScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZBarScannerView(this);
        setContentView(mScannerView);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Toast.makeText(this, "Contents = " + result.getContents() +
                ", Format = " + result.getBarcodeFormat().getName(), Toast.LENGTH_SHORT).show();
        //mScannerView.resumeCameraPreview(this);

    }
}
