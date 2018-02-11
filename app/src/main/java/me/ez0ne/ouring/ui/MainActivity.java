package me.ez0ne.ouring.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.orangegangsters.lollipin.lib.PinCompatActivity;
import com.github.orangegangsters.lollipin.lib.managers.AppLock;

import java.util.ArrayList;
import java.util.List;

import me.ez0ne.ouring.R;
import me.ez0ne.ouring.map.Mapview;
import me.ez0ne.ouring.model.BankCard;
import me.ez0ne.ouring.service.ShowInfoService;
import me.ez0ne.ouring.tag.tagview;
import me.ez0ne.ouring.utils.SmsUtils;
import me.ez0ne.ouring.utils.TaskUtils;
import me.ez0ne.ouring.utils.TicketUtils;
import me.ez0ne.ouring.utils.mapContactUtils;

public class MainActivity extends PinCompatActivity {
    private String TAG="";
    private static final int REQUEST_CODE_READ_PERMISSIONS =123 ;
    private DrawerLayout mDrawerLayout;//左侧抽屉

    private mapContactUtils mapContavtUtils=new mapContactUtils(MainActivity.this);
    private FloatingActionMenu menuRed;

    private FloatingActionButton fab_in;
    private FloatingActionButton fab_out;
    //private FloatingActionButton fab_search;


    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_SMS},REQUEST_CODE_READ_PERMISSIONS);
        sharedPreferences = getSharedPreferences(
                "userinfo", Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }



        //menuRed = (FloatingActionMenu) findViewById(R.id.menu_red);
        //fab_in = (FloatingActionButton) findViewById(R.id.fab_in);
        //fab_out = (FloatingActionButton) findViewById(R.id.fab_out);
        //fab_search = (FloatingActionButton) findViewById(R.id.fab_search);


//        fab_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,
//                        SearchActivity.class);
//                startActivity(intent);
//            }
//        });




        firstTimeUseGuide();
    }


   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_qrscan:
                openQrScanActivity();
                break;


            //@@@@ 测试 通知 待删除
            case R.id.action_test_newsms:
                //openTestNotifActivity();
                openSearchActivity();
                break;
            //@@@@ 测试 通知 待删除


            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //@@@@　模拟测试
    private void openTestNotifActivity() {
        Intent mServiceIntent = new Intent(this, ShowInfoService.class);
        Bundle bundle = new Bundle();
        BankCard test = new BankCard();
        test.setResultContent("您 建设银行 资金支出 100.00元");

        bundle.putSerializable("message", test);
        mServiceIntent.putExtra("bundle", bundle);
        this.startService(mServiceIntent);
    }
    //@@@@ 测试 待删除

    //Fab的显示与隐藏
    public void hideFAB() {
        menuRed.hideMenuButton(true);
    }
    public void showFAB() {
        menuRed.showMenuButton(true);
    }

    //ViewPager 初始化
    private void setupViewPager(ViewPager viewPager) {

        if(TAG.equals("BANK")){
            Adapter adapter = new Adapter(getSupportFragmentManager());
            adapter.addFragment(new SmsCardsFragment(), "明细");
            adapter.addFragment(new ChartFragment(), "统计");
            viewPager.setAdapter(adapter);
        }
        if(TAG.equals("TICKET")){
            Adapter adapter = new Adapter(getSupportFragmentManager());
            adapter.addFragment(new ticketFragment(), "");
            viewPager.setAdapter(adapter);
        }

    }

    private void setViewPager(String tag){
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabLayout1 = (TabLayout) findViewById(R.id.tabs);
        tabLayout1.removeAllTabs();
        viewPager.removeAllViews();
        if(tag.equals("ticket")) {
            Adapter adapter = new Adapter(getSupportFragmentManager());
            adapter.addFragment(new ticketFragment(), "车票");
            adapter.notifyDataSetChanged();
            viewPager.setAdapter(adapter);
        }
        if(tag.equals("bank")){
            Adapter adapter = new Adapter(getSupportFragmentManager());
            adapter.addFragment(new SmsCardsFragment(), "明细");
            adapter.addFragment(new ChartFragment(), "统计");
            adapter.notifyDataSetChanged();
            viewPager.setAdapter(adapter);
        }


        tabLayout1.setupWithViewPager(viewPager);
    }

    //Drawer 初始化
    private void setupDrawerContent(NavigationView navigationView) {


        // TODO 动态改变 Drawer 中的 Title 文本
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.nav_checked:
                                openSetPinActivity();
                                break;
                            case R.id.nav_help:
                                openGuideActivity();
                                break;
                            case R.id.nav_bank:
                                setViewPager("bank");
                                break;

                            case R.id.nav_ticket:
                                setViewPager("ticket");

                                break;
                            case R.id.nav_place:
                                //地图跳转
                                Intent intent1=new Intent(MainActivity.this, Mapview.class);
                                startActivity(intent1);
                                break;

                            case R.id.nav_tag:
                                //标签
                                Intent intent=new Intent(MainActivity.this,tagview.class);
                                startActivity(intent);
                                break;
                            default:
                                return true;
                        }

                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void openGuideActivity() {
        Intent intent = new Intent(this, GuideActivity.class);
        startActivity(intent);
    }


    //启动QR扫描 Activity
    private void openQrScanActivity() {
        Intent intent = new Intent(this, QrScanActivity.class);
        startActivity(intent);
    }

    //设置Pin密码
    private void openSetPinActivity() {
        Intent intent = new Intent(this, PinActivity.class);
        intent.putExtra(AppLock.EXTRA_TYPE, AppLock.ENABLE_PINLOCK);
        startActivityForResult(intent, 11);
        // Result 用来弹窗用，不一定需要。
    }

    // 测试使用
    private void openSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }


    // 首次启动引导页 + 数据库初始化
    private void firstTimeUseGuide() {
        boolean isFirstStart = sharedPreferences.getBoolean("is_first_start", true);

        if (isFirstStart) {
            sharedPreferences.edit().putBoolean("is_first_start", false).apply();
           /* OnlyTicketToDb();*/
           mapContavtUtils.initDB();
           mapContavtUtils.getPlaceToDB();
            OnlySmsToDb();// 初始化数据库
            startActivity(new Intent(this, SearchActivity.class));
            // @@@ 需要修改，临时为引导页
        }
    } // 是否首次启动


    private void OnlyTicketToDb(){
        TaskUtils.execute(
                new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {
                        TicketUtils ticketUtils = new TicketUtils(MainActivity.this);
                        if (ticketUtils.TicketToDB()) {
                            sharedPreferences.edit().putBoolean("db_ready", true).apply();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                    }
                }
        );
    }
    // Sms to Database
    private void OnlySmsToDb() {
        // AsyncTask
        TaskUtils.execute(
                new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {
                        SmsUtils smsUtils = new SmsUtils(MainActivity.this);
                        if (smsUtils.smsToDB()) {
                            sharedPreferences.edit().putBoolean("db_ready", true).apply();// 数据库完成标识，一般为false，仅首次启动为true

                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);

                    }
                }
        );

    }


    // Tabs Pager Adapter 偷懒了...
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {

            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        public void clearFragment(){
            mFragments.clear();
            mFragmentTitles.clear();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }

        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return POSITION_UNCHANGED;
        }
    }

}
