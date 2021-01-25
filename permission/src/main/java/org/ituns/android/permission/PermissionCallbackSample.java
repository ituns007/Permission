package org.ituns.android.permission;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

/**
 * description: 权限申请回调的简单实现
 * author: wangxiulong
 * date: 2019/5/27 09:36
 * update:
 * version: 1.0.0
 */
public abstract class PermissionCallbackSample implements PermissionCallback {

    /**
     * 处理需要询问用户的权限
     *
     * 范例仅适用系统对话框，和一般的处理逻辑
     * 如果需要自定义，请实现接口:
     * @see PermissionCallback
     *
     * @param service
     * @param request
     */
    @Override
    public void onPermissionNeedAsk(final PermissionService service, final PermissionRequest request) {
        String[] needAskPermissions = PermissionUtils.findNeedAskPermissions(request);
        String description = request.buildDescriptions(needAskPermissions);

        Activity activity = request.getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setMessage("程序运行需要以下权限:" + description);
        builder.setNegativeButton("申请", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                service.requestPermission(request);
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String[] grantedPermissions = PermissionUtils.findAllGrantedPermissions(request);
                String[] deniedPermissions = PermissionUtils.findAllDeniedPermissions(request);
                onPermissionDenied(grantedPermissions, deniedPermissions);
            }
        });
        builder.show();
    }

    /**
     * 处理不再询问的权限
     *
     * 范例仅适用系统对话框，和一般的处理逻辑
     * 如果需要自定义，请实现接口:
     * @see PermissionCallback
     *
     * @param service
     * @param request
     */
    @Override
    public void onPermissionNeverAsk(PermissionService service, final PermissionRequest request) {
        String[] neverAskPermissions = PermissionUtils.findNeverAskPermissions(request);
        String description = request.buildDescriptions(neverAskPermissions);

        final Activity activity = request.getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setMessage("程序运行需要手动设置以下权限:" + description);
        builder.setNegativeButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                activity.startActivity(intent);
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String[] grantedPermissions = PermissionUtils.findAllGrantedPermissions(request);
                String[] deniedPermissions = PermissionUtils.findAllDeniedPermissions(request);
                onPermissionDenied(grantedPermissions, deniedPermissions);
            }
        });
        builder.show();
    }
}
