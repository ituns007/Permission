package org.ituns.android.permission;

import androidx.annotation.NonNull;
import androidx.collection.SimpleArrayMap;
import androidx.core.app.ActivityCompat;

/**
 * description: 权限申请服务
 * author: wangxiulong
 * date: 2019/5/22 17:02
 * update:
 * version: 1.0.0
 */
public class PermissionService {
    private Integer permissionRequestCode;
    private PermissionCallback permissionCallback;
    private final SimpleArrayMap<Integer, PermissionRequest> permissionRequestMap;

    public PermissionService(@NonNull PermissionCallback callback) {
        permissionRequestCode = 10506;
        permissionCallback = callback;
        permissionRequestMap = new SimpleArrayMap<>(1);
    }

    /**
     * 申请权限
     *
     * @param request
     */
    public void requestPermission(@NonNull PermissionRequest request) {
        String[] deniedPermissions = PermissionUtils.findAllDeniedPermissions(request);
        if(deniedPermissions.length == 0) {
            String[] grantedPermissions = PermissionUtils.findAllGrantedPermissions(request);
            PermissionCallback callback = permissionCallback;
            if(callback != null) {
                callback.onPermissionGranted(grantedPermissions);
            }
        } else {
            permissionRequestCode++;
            permissionRequestMap.put(permissionRequestCode, request);
            ActivityCompat.requestPermissions(request.getActivity(), deniedPermissions, permissionRequestCode);
        }
    }

    /**
     * 接收Activity中的回调结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(permissionRequestMap.containsKey(requestCode)) {
            PermissionCallback callback = permissionCallback;
            if(callback == null) {
                return;
            }

            PermissionRequest request = permissionRequestMap.remove(requestCode);
            if(request == null) {
                return;
            }

            if(PermissionUtils.isPermissionsGranted(request)) {
                String[] grantedPermissions = PermissionUtils.findAllGrantedPermissions(request);
                callback.onPermissionGranted(grantedPermissions);
                return;
            }

            String[] needAskPermissions = PermissionUtils.findNeedAskPermissions(request);
            if(needAskPermissions.length > 0 && request.getLevel() >= PermissionRequest.LEVEL_NORMAL) {
                callback.onPermissionNeedAsk(this, request);
                return;
            }

            String[] neverAskPermissions = PermissionUtils.findNeverAskPermissions(request);
            if(neverAskPermissions.length > 0 && request.getLevel() == PermissionRequest.LEVEL_LOOSE) {
                callback.onPermissionNeverAsk(this, request);
                return;
            }

            String[] grantedPermissions = PermissionUtils.findAllGrantedPermissions(request);
            String[] deniedPermissions = PermissionUtils.findAllDeniedPermissions(request);
            callback.onPermissionDenied(grantedPermissions, deniedPermissions);
        }
    }

    public void release() {
        permissionCallback = null;
        permissionRequestMap.clear();
    }
}
