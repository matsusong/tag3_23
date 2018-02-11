package me.ez0ne.ouring;

import com.github.orangegangsters.lollipin.lib.managers.LockManager;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.xutils.x;

import me.ez0ne.ouring.ui.PinActivity;

/**
 * Created by ez0ne on 2016/3/1 0001.
 */
public class App extends LitePalApplication {
    public  static String appip="http://apis.juhe.cn/mobile/get?phone=";
    @SuppressWarnings("unchecked")
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        LockManager<PinActivity> lockManager = LockManager.getInstance();
        lockManager.enableAppLock(this, PinActivity.class);
        lockManager.getAppLock().setLogoId(R.drawable.ic_lock);

    }
}
