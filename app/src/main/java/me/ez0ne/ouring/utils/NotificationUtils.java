package me.ez0ne.ouring.utils;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import me.ez0ne.ouring.R;
import me.ez0ne.ouring.model.BankCard;
import me.ez0ne.ouring.model.Express;

/**
 * Created by Ezreal.Wan on 2016/4/19.
 */
public class NotificationUtils {

    public static void showExpNotify(Context context, Express express) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_notify_exp_small);
        builder.setAutoCancel(false);

        RemoteViews remoteViews;
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_msg);

        if (express.getExpname() != null) {
            remoteViews.setTextViewText(R.id.tv_expname, formatTv(express.getExpname())); // xx快递
        } else remoteViews.setTextViewText(R.id.tv_expname, "新快递");

        if (express.getExdate() != null) {
            remoteViews.setTextViewText(R.id.tv_content, express.getExdate());
        }
        if (express.getNum() != null) {
            remoteViews.setTextViewText(
                    R.id.tv_title,
                    express.getNum()

            );
        } else {
            remoteViews.setTextViewText(R.id.tv_title, "您有新快递,进入短信查看~");
        }
        builder.setContent(remoteViews);
    }

    public static void showSmsNotify(Context context, BankCard bankCard) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_notify_bank_small);
        builder.setAutoCancel(false);

        RemoteViews remoteViews;
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_msg);

        if (bankCard.getBank() != null) {
            remoteViews.setTextViewText(R.id.tv_expname, bankCard.getBank());
        } else remoteViews.setTextViewText(R.id.tv_expname, "银行");

        remoteViews.setTextViewText(R.id.tv_title, "银行新动态~");
        if (bankCard.getResultContent() != null) {
            remoteViews.setTextViewText(
                    R.id.tv_content,
                    bankCard.getResultContent()

            );
        } else {
            remoteViews.setTextViewText(R.id.tv_content, bankCard.getBody());
        }
        builder.setContent(remoteViews);
    }

    public static String formatTv(String string) {
        // 中文四个字的名字特别换行
        String formatTv = string;
        if (formatTv.length() == 4) {
            String fourCharsName = "";
            for (int u = 0; u < formatTv.length(); u++) {
                if (u == 2) {
                    fourCharsName += "\n";
                }
                fourCharsName += formatTv.charAt(u);
            }
            formatTv = fourCharsName;
        }
        return formatTv;
    }
}
