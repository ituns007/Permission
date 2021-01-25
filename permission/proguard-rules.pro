# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontoptimize
-keeppackagenames org.ituns.android.permission

-keep class org.ituns.android.permission.PermissionActivity { *; }
-keep class org.ituns.android.permission.PermissionCallback { *; }
-keep class org.ituns.android.permission.PermissionCallbackSample { *; }
-keep class org.ituns.android.permission.PermissionCallbackWrapper { *; }
-keep class org.ituns.android.permission.PermissionCompatActivity { *; }
-keep class org.ituns.android.permission.PermissionFragmentActivity { *; }
-keep class org.ituns.android.permission.PermissionRequest { *; }
-keep class org.ituns.android.permission.PermissionRequest$Level { *; }
-keep class org.ituns.android.permission.PermissionRequest$Builder { *; }
-keep class org.ituns.android.permission.PermissionService { *; }
-keep class org.ituns.android.permission.PermissionUtils { *; }
-keep class org.ituns.android.permission.PermissionVersion { *; }

-keep class org.ituns.android.permission.R{ *; }
-keep class org.ituns.android.permission.R$* { public static <fields>; }