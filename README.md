# Permission
Android 检查动态权限封装，可以设置反馈级别。

1.在项目根 build.gradle 文件中添加 Maven 仓库：
```
allprojects {
    repositories {
        maven {
            url "http://maven.ituns.org/repository/maven-public/"
        }
    }
}
```

2.在模块 build.gradle 中添加引用：
```
dependencies {
    implementation "org.ituns.android:permission:1.0.0"
}
```