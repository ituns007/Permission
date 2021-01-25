package org.ituns.android.permission;

import android.app.Activity;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;

/**
 * description:
 * author: wangxiulong
 * date: 2019/5/22 17:39
 * update:
 * version: 1.0.0
 */
public class PermissionRequest {
    /**
     * 如果用户允许了所有的权限申请，直接回调
     * {@link PermissionCallback#onPermissionGranted(String[])} 方法
     *
     * 如果用户拒绝了一个以上的权限，直接回调
     * {@link PermissionCallback#onPermissionDenied(String[], String[])} 方法
     */
    public static final int LEVEL_STRICT = 1;
    /**
     * 如果用户允许了所有的权限申请，直接回调
     * {@link PermissionCallback#onPermissionGranted(String[])} 方法
     *
     * 如果用户拒绝的权限中，有一个以上的权限可以再次申请(没有勾选不再提示)，直接回调
     * {@link PermissionCallback#onPermissionNeedAsk(PermissionService, PermissionRequest)} 方法
     *
     * 如果用户拒绝的权限中，没有一个权限可以再次申请，直接回调
     * {@link PermissionCallback#onPermissionDenied(String[], String[])}
     *
     * 参照 {@link PermissionCallbackWrapper} 的实现
     */
    public static final int LEVEL_NORMAL = 2;
    /**
     * 如果用户允许了所有的权限申请，直接回调
     * {@link PermissionCallback#onPermissionGranted(String[])} 方法
     *
     * 如果用户拒绝的权限中，有一个以上的权限可以再次申请(没有勾选不再提示)，直接回调
     * {@link PermissionCallback#onPermissionNeedAsk(PermissionService, PermissionRequest)} 方法
     *
     * 如果用户拒绝的权限中，有一个以上的权限不可以再次申请(勾选不再提示)，直接回调
     * {@link PermissionCallback#onPermissionNeverAsk(PermissionService, PermissionRequest)} 方法
     *
     * 理论上，该模式下，不会自动触发
     * {@link PermissionCallback#onPermissionDenied(String[], String[])} 方法
     * 除非在方法
     * {@link PermissionCallback#onPermissionNeedAsk(PermissionService, PermissionRequest)}
     * 和
     * {@link PermissionCallback#onPermissionNeverAsk(PermissionService, PermissionRequest)}
     * 中主动触发，可以参照 {@link PermissionCallbackWrapper} 的实现
     */
    public static final int LEVEL_LOOSE = 4;
    @IntDef({LEVEL_STRICT, LEVEL_NORMAL, LEVEL_LOOSE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Level {}

    private final int mLevel;
    private final WeakReference<Activity> mActivity;
    private final HashMap<String, String> mPermissionMap;

    private PermissionRequest(Builder builder) {
        mLevel = builder.level;
        mActivity = new WeakReference<>(builder.activity);
        mPermissionMap = new HashMap<>(builder.permissionMap);
    }

    public int getLevel() {
        return mLevel;
    }

    public Activity getActivity() {
        return mActivity.get();
    }

    /**
     * 获取权限数组
     * @return
     */
    public String[] getPermissions() {
        return mPermissionMap.keySet().toArray(new String[]{});
    }

    /**
     * 获取所有权限的描述信息
     * @return
     */
    public String buildDescriptions() {
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = mPermissionMap.keySet().iterator();
        while (iterator.hasNext()) {
            if(builder.length() > 0) {
                builder.append(",");
            }

            String description = mPermissionMap.get(iterator.next());
            builder.append(description);
        }
        return builder.toString();
    }

    /**
     * 获取指定权限的描述信息
     * @return
     */
    public String buildDescriptions(String[] permissions) {
        if(permissions == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for(String permission : permissions) {
            if(mPermissionMap.containsKey(permission)) {
                if(builder.length() > 0) {
                    builder.append(",");
                }

                builder.append(mPermissionMap.get(permission));
            }
        }
        return builder.toString();
    }

    /**
     * 基于指定的权限数组，生成新的Request
     *
     * @param permissions
     * @return
     */
    public PermissionRequest newRequest(String[] permissions) {
        Builder builder = new Builder(mActivity.get());
        builder.level(mLevel);
        for(String pemission : permissions) {
            if(mPermissionMap.containsKey(pemission)) {
                String description = mPermissionMap.get(pemission);
                builder.addPermission(pemission, description);
            }
        }
        return builder.build();
    }

    public void release() {
        mActivity.clear();
        mPermissionMap.clear();
    }

    public static class Builder {
        private Activity activity;
        private int level;
        private HashMap<String, String> permissionMap;

        public Builder(Activity activity) {
            this.activity = activity;
            this.level = LEVEL_STRICT;
            this.permissionMap = new HashMap<>();
        }

        public Builder level(@Level int level) {
            this.level = level;
            return this;
        }

        public Builder addPermission(String permission, String description) {
            permissionMap.put(permission, description);
            return this;
        }

        public PermissionRequest build() {
            return new PermissionRequest(this);
        }
    }
}
