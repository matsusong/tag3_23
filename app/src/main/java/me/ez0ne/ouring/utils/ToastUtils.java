package me.ez0ne.ouring.utils;

import android.widget.Toast;

import me.ez0ne.ouring.App;

/**
 * Created by Ezreal.Wan on 2016/4/19.
 * Toast 通知
 */
public class ToastUtils {
    private ToastUtils() {
    }

    public static void showShort(int resId) {
        Toast.makeText(App.getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(String message) {
        Toast.makeText(App.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(int resId) {
        Toast.makeText(App.getContext(), resId, Toast.LENGTH_LONG).show();
    }

    public static void showLong(String message) {
        Toast.makeText(App.getContext(), message, Toast.LENGTH_LONG).show();
    }
}
