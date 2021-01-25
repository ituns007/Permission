package org.ituns.android.permission;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by wangxiulong on 2017/10/11.
 */
public abstract class PermissionCompatActivity extends AppCompatActivity {
    PermissionRequest permissionRequest;
    PermissionService permissionService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionRequest = permissionRequest();
        permissionService = new PermissionService(permissionCallback());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        PermissionService service = permissionService;
        if(service != null) {
            permissionService.requestPermission(permissionRequest);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        PermissionService service = permissionService;
        if(service != null) {
            permissionService.requestPermission(permissionRequest);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionService service = permissionService;
        if(service != null) {
            permissionService.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PermissionRequest request = permissionRequest;
        if(request != null) {
            request.release();
            permissionRequest = null;
        }
        PermissionService service = permissionService;
        if(service != null) {
            service.release();
            permissionService = null;
        }
    }

    public abstract PermissionRequest permissionRequest();

    public abstract PermissionCallback permissionCallback();
}
