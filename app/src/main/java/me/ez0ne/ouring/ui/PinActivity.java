package me.ez0ne.ouring.ui;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.github.orangegangsters.lollipin.lib.managers.AppLockActivity;

/**
 * Created by Ezreal.Wan on 2016/5/5.
 */
public class PinActivity extends AppLockActivity {


    @Override
    public void showForgotDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("忘记密码？")
                .setMessage("忘记密码就进不去了~")
                .setPositiveButton(
                        android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }
                )
                .create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    @Override
    public void onPinFailure(int attempts) {

    }

    @Override
    public void onPinSuccess(int attempts) {

    }

    @Override
    public int getPinLength() {
        return super.getPinLength();//default 4
    }

}
