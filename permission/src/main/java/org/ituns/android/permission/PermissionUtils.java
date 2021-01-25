package org.ituns.android.permission;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import java.util.ArrayList;

/**
 * description: 权限工具类
 * author: wangxiulong
 * date: 2019/5/22 14:45
 * update:
 * version: 1.0.0
 */
class PermissionUtils {

    /**
     * 检查单个权限是否被允许
     *
     * @param activity
     * @param permission
     * @return
     */
    private static boolean isPermissionGranted(Activity activity, String permission) {
        try {
            int result = PermissionChecker.checkSelfPermission(activity, permission);
            return result == PackageManager.PERMISSION_GRANTED;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 检查权限列表是否被允许
     *
     * @param activity
     * @param permissions
     * @return
     */
    public static boolean isPermissionsGranted(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if(!PermissionVersion.exists(permission)) {
                return false;
            }

            if(!isPermissionGranted(activity, permission)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPermissionsGranted(PermissionRequest request) {
        return isPermissionsGranted(request.getActivity(), request.getPermissions());
    }


    /**
     * 获取所有允许的权限列表
     *
     * @param activity
     * @param permissions
     * @return
     */
    public static String[] findAllGrantedPermissions(Activity activity, String[] permissions) {
        ArrayList<String> permissionList = new ArrayList<>();
        for(String permission : permissions) {
            if(PermissionVersion.exists(permission) && isPermissionGranted(activity, permission)) {
                permissionList.add(permission);
            }
        }
        return permissionList.toArray(new String[]{});
    }

    public static String[] findAllGrantedPermissions(PermissionRequest request) {
        return findAllGrantedPermissions(request.getActivity(), request.getPermissions());
    }

    /**
     * 获取需要询问的权限列表
     *
     * @param activity
     * @param permissions
     * @return
     */
    public static String[] findNeedAskPermissions(Activity activity, String[] permissions) {
        ArrayList<String> permissionList = new ArrayList<>();
        for(String permission : permissions) {
            if(!PermissionVersion.exists(permission)) {
                continue;
            }

            if(isPermissionGranted(activity, permission)) {
                continue;
            }

            if(!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                continue;
            }

            permissionList.add(permission);
        }
        return permissionList.toArray(new String[]{});
    }

    public static String[] findNeedAskPermissions(PermissionRequest request) {
        return findNeedAskPermissions(request.getActivity(), request.getPermissions());
    }

    /**
     * 获取不再询问的权限列表
     *
     * @param activity
     * @param permissions
     * @return
     */
    public static String[] findNeverAskPermissions(Activity activity, String[] permissions) {
        ArrayList<String> permissionList = new ArrayList<>();
        for(String permission : permissions) {
            if(!PermissionVersion.exists(permission)) {
                continue;
            }

            if(isPermissionGranted(activity, permission)) {
                continue;
            }

            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                continue;
            }

            permissionList.add(permission);
        }
        return permissionList.toArray(new String[permissionList.size()]);
    }

    public static String[] findNeverAskPermissions(PermissionRequest request) {
        return findNeverAskPermissions(request.getActivity(), request.getPermissions());
    }

    /**
     * 获取所有拒绝的权限列表
     *
     * @param activity
     * @param permissions
     * @return
     */
    public static String[] findAllDeniedPermissions(Activity activity, String[] permissions) {
        ArrayList<String> permissionList = new ArrayList<>();
        for(String permission : permissions) {
            if(PermissionVersion.exists(permission) && !isPermissionGranted(activity, permission)) {
                permissionList.add(permission);
            }
        }
        return permissionList.toArray(new String[]{});
    }

    public static String[] findAllDeniedPermissions(PermissionRequest request) {
        return findAllDeniedPermissions(request.getActivity(), request.getPermissions());
    }
}
