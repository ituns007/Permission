package org.ituns.permission;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;

import org.ituns.android.permission.PermissionCallback;
import org.ituns.android.permission.PermissionCallbackSample;
import org.ituns.android.permission.PermissionCompatActivity;
import org.ituns.android.permission.PermissionRequest;

public class MainActivity extends PermissionCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public PermissionRequest permissionRequest() {
        return new PermissionRequest.Builder(this)
                .level(PermissionRequest.LEVEL_LOOSE)
                .addPermission(Manifest.permission.CAMERA, "相机")
                .build();
    }

    @Override
    public PermissionCallback permissionCallback() {
        return new PermissionCallbackSample() {
            @Override
            public void onPermissionGranted(String[] strings) {

            }

            @Override
            public void onPermissionDenied(String[] strings, String[] strings1) {
                finish();
            }
        };
    }
}
