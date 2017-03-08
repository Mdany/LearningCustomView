package com.chenyu.monster.customviewtest.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by chenyu on 16/9/27.
 */

public class PermissionUtils {
    public static void checkPermission(Activity activity){
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {// 没有权限。
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_CONTACTS)) {
                // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
            } else {
                // 申请授权。
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, 100);
            }
        }
    }

    public static void check(Activity activity){
        if (activity.checkPermission(Manifest.permission.READ_CONTACTS,
                Process.myPid(),
                Process.myUid()) != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)){
                    // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
                }else{
                    // 申请授权。
                    activity.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 101);
                }
            }
        }
    }
}
