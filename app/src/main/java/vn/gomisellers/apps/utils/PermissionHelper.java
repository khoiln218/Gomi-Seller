package vn.gomisellers.apps.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionHelper {

    public static final String[] photo_permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private String[] permissions;

    private Activity activity;
    private PermissionCallback permissionCallback;

    private boolean showRational;

    //=========Constructors - START=========
    public PermissionHelper(Activity activity, String[] permissions) {
        this.activity = activity;
        this.permissions = permissions;

        checkIfPermissionPresentInAndroidManifest();
    }

    public void checkIfPermissionPresentInAndroidManifest() {
        for (String permission : permissions) {
            if (hasPermissionInManifest(permission) == false) {
                throw new RuntimeException("Permission (" + permission + ") Not Declared in manifest");
            }
        }
    }

    private boolean hasPermissionInManifest(String permission) {
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(activity.getPackageName(), PackageManager.GET_PERMISSIONS);
            if (info.requestedPermissions != null) {
                for (String p : info.requestedPermissions) {
                    if (p.equals(permission)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //=========Constructors- END=========

    public void request(PermissionCallback permissionCallback) {

        this.permissionCallback = permissionCallback;

        if (checkSelfPermission() == false) {
            showRational = shouldShowRational();
            ActivityCompat.requestPermissions(activity, filterNotGrantedPermission(), GomiConstants.REQUEST_PERMISSION_SETTING);
        } else {
            if (permissionCallback != null)
                permissionCallback.onPermissionGranted();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == GomiConstants.REQUEST_PERMISSION_SETTING) {
            boolean denied = false;
            int i = 0;
            ArrayList<String> grantedPermissions = new ArrayList<>();
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    denied = true;
                } else {
                    grantedPermissions.add(permissions[i]);
                }
                i++;
            }

            if (denied) {
                if (showRational && shouldShowRational()) {
                    if (!grantedPermissions.isEmpty()) {
                        if (permissionCallback != null)
                            permissionCallback.onIndividualPermissionGranted(grantedPermissions.toArray(new String[grantedPermissions.size()]));
                    }
                    if (permissionCallback != null)
                        permissionCallback.onPermissionDenied();

                } else {
                    if (permissionCallback != null)
                        permissionCallback.onPermissionDeniedBySystem();
                }

            } else {
                if (permissionCallback != null)
                    permissionCallback.onPermissionGranted();
            }
        }
    }

    public void launchAppDetailsSettings() {
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        activity.startActivity(intent);
    }

    //===================
    private boolean checkSelfPermission() {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private boolean shouldShowRational() {
        boolean currentShowRational = false;
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) == true) {
                currentShowRational = true;
                break;
            }
        }
        return currentShowRational;
    }

    private String[] filterNotGrantedPermission() {
        List<String> notGrantedPermission = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                notGrantedPermission.add(permission);
            }
        }
        return notGrantedPermission.toArray(new String[notGrantedPermission.size()]);
    }
    //===================

    /**
     * Permission Callback
     */
    public interface PermissionCallback {
        void onPermissionGranted();

        void onIndividualPermissionGranted(String grantedPermission[]);

        void onPermissionDenied();

        void onPermissionDeniedBySystem();
    }
}
