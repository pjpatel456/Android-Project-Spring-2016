package com.example.android.moveyourthings;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

public class CameraActivity extends Activity {

    private Camera mCamera;
    private CameraPreview mPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_view);

        // Create an instance of Camera
        mCamera = CameraPreview.getCameraInstance();

        Log.e("camera", String.valueOf(mCamera));
        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(CameraActivity.this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.mCamera == null){
            this.mCamera = CameraPreview.getCameraInstance();
        }
    }

}
