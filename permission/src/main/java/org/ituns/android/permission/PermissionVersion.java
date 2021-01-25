package org.ituns.android.permission;

import android.os.Build;

import androidx.collection.SimpleArrayMap;

/**
 * description: 权限版本管理
 * author: wangxiulong
 * date: 2019/5/22 14:45
 * update:
 * version: 1.0.0
 */
class PermissionVersion {
    private static final SimpleArrayMap<String, Integer> PERMISSION_MIN_SDK;

    static {
        PERMISSION_MIN_SDK = new SimpleArrayMap<>(8);

        PERMISSION_MIN_SDK.put("android.permission.ACCEPT_HANDOVER", 28);
        PERMISSION_MIN_SDK.put("android.permission.ACCESS_BACKGROUND_LOCATION", 29);
        PERMISSION_MIN_SDK.put("android.permission.ACCESS_COARSE_LOCATION", 1);
        PERMISSION_MIN_SDK.put("android.permission.ACCESS_FINE_LOCATION", 1);
        PERMISSION_MIN_SDK.put("android.permission.ACCESS_MEDIA_LOCATION", 29);
        PERMISSION_MIN_SDK.put("android.permission.ACTIVITY_RECOGNITION", 29);
        PERMISSION_MIN_SDK.put("android.permission.ADD_VOICEMAIL", 14);
        PERMISSION_MIN_SDK.put("android.permission.ANSWER_PHONE_CALLS", 26);
        PERMISSION_MIN_SDK.put("android.permission.BODY_SENSORS", 20);
        PERMISSION_MIN_SDK.put("android.permission.CALL_PHONE", 1);
        PERMISSION_MIN_SDK.put("android.permission.CAMERA", 1);
        PERMISSION_MIN_SDK.put("android.permission.GET_ACCOUNTS", 1);
        PERMISSION_MIN_SDK.put("android.permission.PROCESS_OUTGOING_CALLS", 1);
        PERMISSION_MIN_SDK.put("android.permission.READ_CALENDAR", 1);
        PERMISSION_MIN_SDK.put("android.permission.READ_CALL_LOG", 16);
        PERMISSION_MIN_SDK.put("android.permission.READ_CONTACTS", 1);
        PERMISSION_MIN_SDK.put("android.permission.READ_EXTERNAL_STORAGE", 16);
        PERMISSION_MIN_SDK.put("android.permission.READ_PHONE_NUMBERS", 26);
        PERMISSION_MIN_SDK.put("android.permission.READ_PHONE_STATE", 1);
        PERMISSION_MIN_SDK.put("android.permission.READ_SMS", 1);
        PERMISSION_MIN_SDK.put("android.permission.RECEIVE_MMS", 1);
        PERMISSION_MIN_SDK.put("android.permission.RECEIVE_SMS", 1);
        PERMISSION_MIN_SDK.put("android.permission.RECEIVE_WAP_PUSH", 1);
        PERMISSION_MIN_SDK.put("android.permission.RECORD_AUDIO", 1);
        PERMISSION_MIN_SDK.put("android.permission.SEND_SMS", 1);
        PERMISSION_MIN_SDK.put("android.permission.USE_SIP", 9);
        PERMISSION_MIN_SDK.put("android.permission.WRITE_CALENDAR", 1);
        PERMISSION_MIN_SDK.put("android.permission.WRITE_CALL_LOG", 16);
        PERMISSION_MIN_SDK.put("android.permission.WRITE_CONTACTS", 1);
        PERMISSION_MIN_SDK.put("android.permission.WRITE_EXTERNAL_STORAGE", 4);
    }

    /**
     * 某个权限是否存在于当前系统版本中
     *
     * @param permission
     * @return
     */
    public static boolean exists(String permission) {
        Integer minVersion = PERMISSION_MIN_SDK.get(permission);
        return minVersion == null || Build.VERSION.SDK_INT >= minVersion;
    }
}
