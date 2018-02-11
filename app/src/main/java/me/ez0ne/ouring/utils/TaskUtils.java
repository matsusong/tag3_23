package me.ez0ne.ouring.utils;

import android.os.AsyncTask;
import android.os.Build;

/**
 * Created by Ezreal.Wan on 2016/4/26.
 */
public class TaskUtils {
    @SafeVarargs
    public static <Params, Progress, Result> void execute(AsyncTask<Params, Progress, Result> task,
                                                          Params... params) {
        if (Build.VERSION.SDK_INT >= 11) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }
}
