package org.ituns.android.permission;

/**
 * description: 权限申请回调
 * author: wangxiulong
 * date: 2019/5/22 17:09
 * update:
 * version: 1.0.0
 */
public interface PermissionCallback {

    /**
     * 权限允许
     */
    public void onPermissionGranted(String[] granted);

    /**
     * 处理需要询问用户的权限
     *
     * @param service
     * @param request
     */
    public void onPermissionNeedAsk(PermissionService service, PermissionRequest request);

    /**
     * 处理不再询问的权限
     *
     * @param service
     * @param request
     */
    public void onPermissionNeverAsk(PermissionService service, PermissionRequest request);

    /**
     * 权限拒绝
     *
     * @param granted 用户允许的权限数组
     * @param denied 用户拒绝的权限数组
     */
    public void onPermissionDenied(String[] granted, String[] denied);
}
