package me.ez0ne.ouring.utils;

import android.os.Build;

/**
 * Created by Ezreal.Wan on 2016/4/26.
 * SDK版本不同 API 不尽相同
 */
public class VersionUtils {
    public static final boolean IS_MORE_THAN_LOLLIPOP = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
}
