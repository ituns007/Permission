package org.ituns.android.permission;

/**
 * description: 权限申请回调接口包装
 * author: wangxiulong
 * date: 2019/5/23 14:31
 * update:
 * version: 1.0.0
 */
public abstract class PermissionCallbackWrapper implements PermissionCallback {

    @Override
    public void onPermissionGranted(String[] granted) {}

    @Override
    public void onPermissionNeedAsk(final PermissionService service, final PermissionRequest request) {}

    @Override
    public void onPermissionNeverAsk(PermissionService service, final PermissionRequest request) {}

    @Override
    public void onPermissionDenied(String[] granted, String[] denied) {}
}
