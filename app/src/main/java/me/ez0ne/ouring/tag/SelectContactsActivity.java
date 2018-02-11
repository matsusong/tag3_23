package me.ez0ne.ouring.tag;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.ez0ne.ouring.R;

/**
 * Created by Cerian on 2018/2/11.
 */

public class SelectContactsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SelectContactsActivity";
    private RecyclerView recyclerView;
    private Button back;
    private List<String> rawData;
    private  List<String> data;
    private  List<String> rdata;
    private String tag;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==2){
            setResult(2);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contacts);
        initScreen();
        bind();
        tag= getIntent().getStringExtra("tag");
        //从前一个活动传来的原始信息,号码保证无重复
        rawData = (List<String>)getIntent().getSerializableExtra("data");
        data = new ArrayList<>();
        rdata = new ArrayList<>();


        //动态申请权限
        if(ContextCompat.checkSelfPermission(SelectContactsActivity.this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SelectContactsActivity.this,new String[]{Manifest.permission.READ_CONTACTS},2);
        }else{
            //两个循环是为了让在通讯录中的号码优先显示
            for(String i:rawData){
                String t= getDisplayNameByNumber(i);

                if(!"".equals(t)){
                    data.add(t);
                    rdata.add(i);
                }
            }
            for(String i:rawData){
                String t= getDisplayNameByNumber(i);
                if("".equals(t)){
                    data.add(i);
                    rdata.add(i);
                }
            }
        }


        // TODO: 2018/2/2  试验下动态权限申请是否是异步的
        recyclerView.setAdapter(new ContectAdapter(data,rdata,this,tag));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void bind()
    {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(this);
    }
    //http://blog.csdn.net/guolin_blog/article/details/51763825
    //学到了，我的手机版本太低，我说怎么看不出来。。
    //btw：为啥MainActivity里面没有这个函数，第一个不需要沉浸式嘛。。
    //这个重载好像没有用了，额，忘删了，用了initScreen这个函数


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 2:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    // TODO: 2018/2/2  不是要两个foreach吗
                    for(String i:rawData){
                        String t= getDisplayNameByNumber(i);

                        if(!"".equals(t)){
                            data.add(t);
                            rdata.add(i);
                        }
                    }
                    for(String i:rawData){
                        String t= getDisplayNameByNumber(i);
                        if("".equals(t)){
                            data.add(i);
                            rdata.add(i);
                        }
                    }
                    // TODO: 2018/2/2  允许读取联系人后加载界面
                    recyclerView.setAdapter(new ContectAdapter(data,rdata,this,tag));
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                }
                else{
                    Toast.makeText(this,"无权限读取通讯录，请开放权限",Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    // TODO: 2018/1/31 报告说有些号码在通讯录中有保存的短信还是显示不了，是否是这个函数的问题
    public String getDisplayNameByNumber(String phoneNum) {
        String contactName = "";
        ContentResolver cr = getContentResolver();
        Cursor pCur = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.NUMBER + " = ?",
                new String[] { phoneNum }, null);
        if (pCur!=null&&pCur.moveToFirst()) {
            contactName = pCur .getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            pCur.close();
        }
        if(!"".equals(contactName))
            return contactName;
        pCur = cr.query(Uri.parse("content://icc/adn"),null,"number = ?",new String[]{phoneNum},null);
        if(pCur!=null&&pCur.moveToFirst()){
            contactName = pCur .getString(pCur.getColumnIndex("tag"));
            pCur.close();
        }
        if(!"".equals(contactName))
            return contactName;
        pCur = cr.query(Uri.parse("content://sim/adn"),null,"number = ?",new String[]{phoneNum},null);
        if(pCur!=null&&pCur.moveToFirst()){
            contactName = pCur .getString(pCur.getColumnIndex("tag"));
            pCur.close();
        }
        return contactName;
    }

    private void initScreen(){
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
