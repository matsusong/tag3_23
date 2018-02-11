package me.ez0ne.ouring.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import me.ez0ne.ouring.model.BankCard;
import me.ez0ne.ouring.utils.NotificationUtils;
import me.ez0ne.ouring.utils.ToastUtils;

/**
 * Created by Ezreal.Wan on 2016/4/26.
 * Toast + Notification 通知服务
 */
public class ShowInfoService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            BankCard message = (BankCard) bundle.getSerializable("message");

            if (message.getResultContent() != null) {

                ToastUtils.showLong(message.getResultContent());
                NotificationUtils.showSmsNotify(ShowInfoService.this, message);
            }
        }
        return START_STICKY;
    }
}
