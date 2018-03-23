package me.ez0ne.ouring.tag;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import me.ez0ne.ouring.R;

/**
 * Created by Cerian on 2018/2/11.
 */

public class TagViewActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TagViewActivity";
    private String activityTitle;//这个活动的标题
    private TextView title;
    private Button back, createNew, submit;
    private ImageView imageView;
    private EditText input;
    private List<String> data;
    private CustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        Log.d("Debug", "TagViewActivity+" + "onCreate");
        initScreen();
        bind();
        //判断数据库是否已经创建
        List<Message> messages = DataSupport.findAll(Message.class);
        //若第一次打开应用
        if (messages.size() == 0) {
            //动态申请权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 1);
            } else {
                initDatabase();//初始化数据库
            }
        }

        // TODO: 2018/3/7  这里要改

        Intent intent = getIntent();
        final String from = intent.getStringExtra("from");
        //判断这个活动是否由其他活动启动
        if (from == null) {
            activityTitle = "标签";
            title.setText(activityTitle);
            // submit.setText("搜索");
        }


        customView.setOnClickListener(new CustomView.OnTagClickListener() {
            @Override
            public void onClick(String tag) {
                input.setText(tag);
                input.setSelection(input.getText().toString().length());
                if (from == null) {
                    submit.callOnClick();
                }
            }
        });
        //将所有的tag按照出现的次数保存在list中
        List<String> data = getTagList();
        customView.addView(data);
        //让软键盘不弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // TODO: 2018/3/23   标签弹出动画，波浪特效


        ObjectAnimator moveIn = ObjectAnimator.ofFloat(customView, "translationY", 1500f, 0f);
        //ObjectAnimator scalex = ObjectAnimator.ofFloat(customView, "scaleX",0.5f,1f);
       // ObjectAnimator scaley = ObjectAnimator.ofFloat(customView, "scaleY",1.5f,1f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(customView, "alpha", 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
       // animSet.play(rotate).with(fadeInOut).after(moveIn);
        animSet.playTogether(moveIn,fadeInOut);
        animSet.setDuration(3000);
        animSet.start();

        //executeAllAnimations(customView);

        WaveView waveView = findViewById(R.id.wave);
        waveView.setRunning(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Debug", "TagViewActivity+" + "onResume");
        List<String> data = getTagList();
        customView.addView(data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("Debug", "TagViewActivity+" + "onRequestPermissionsResult");
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initDatabase();
                } else {
                    Toast.makeText(this, "无权限读取短信，请开放权限", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    // TODO: 2018/3/7   这里要改

    @Override
    public void onClick(View v) {
        Log.d("Debug", "TagViewActivity+" + "onClick");
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.create_new:
                Intent intent = new Intent(this, SelectContactsActivity.class);
                Set<String> set = new TreeSet<>();
                List<Message> messages = DataSupport.findAll(Message.class);
                for (Message i : messages) {
                    set.add(i.getPhoneNumber());
                }
                List<String> args = new ArrayList<>();
                for (String i : set) {
                    args.add(i);
                }
                intent.putExtra("data", (Serializable) args);
                startActivityForResult(intent, 1);
                break;
            case R.id.submit:
                if (input.getText().length() == 0) {
                    Toast.makeText(this, "标签名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<String> arg = new ArrayList<>();
                List<Message> t = DataSupport.where("tag = ?", input.getText().toString()).find(Message.class);
                Set<String> s = new TreeSet<>();
                for (Message i : t) {
                    s.add(i.getPhoneNumber());
                }
                for (String i : s) {
                    arg.add(i);
                }
                intent = new Intent(this, ShowContentOfTagActivity.class);
                intent.putExtra("data", (Serializable) arg);
                intent.putExtra("tag", input.getText().toString());
                startActivity(intent);
                input.setText("");
                break;
        }
    }

    private void bind() {
        Log.d("Debug", "TagViewActivity+" + "bind");
        back = (Button) findViewById(R.id.back);
        imageView = (ImageView) findViewById(R.id.iv_state);
        createNew = (Button) findViewById(R.id.create_new);
        submit = (Button) findViewById(R.id.submit);
        input = (EditText) findViewById(R.id.input);
        title = (TextView) findViewById(R.id.title);
        customView = (CustomView) findViewById(R.id.custom_view);
        back.setOnClickListener(this);
        createNew.setOnClickListener(this);
        submit.setOnClickListener(this);

    }

    //读取系统短信数据库并保存到本应用数据库中
    // TODO: 2018/2/2 这里最好修改一下只讲接受到的信息存入系统数据库？还是在应用的数据库中再添加一个字段表示是发送的短信还是接受的短信
    private void initDatabase() {
        Log.d("Debug", "TagViewActivity+" + "initDatabase");
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(Uri.parse("content://sms/"), null, null, null, null);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd E HH:mm");
        while (cur.moveToNext()) {
            String address = cur.getString(cur.getColumnIndex("address"));
            String body = cur.getString(cur.getColumnIndex("body"));
            long date = cur.getLong(cur.getColumnIndex("date"));
            int status = cur.getInt(cur.getColumnIndex("type"));
            Log.d("yan", "status=" + status);
            Message message = new Message();
            message.setPhoneNumber(address);
            message.setContent(body);
            message.setStatus(status);
            message.setDate(sdf.format(date));
            message.save();
        }
        cur.close();
    }


    //布局全屏化并把状态栏设为透明
    private void initScreen() {
        Log.d("Debug", "TagViewActivity+" + "initScreen");
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            imageView.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Debug", "TagViewActivity+" + "onActivityResult");
        if (resultCode == 2) {
            customView.addView(getTagList());
        }
    }

    private List<String> getTagList() {
        Log.d("Debug", "TagViewActivity+" + "getTagList");
        List<Message> messages = DataSupport.where("tag != ?", "").find(Message.class);
        Map<String, Integer> map = new TreeMap<>();
        for (Message m : messages) {
            if (!map.containsKey(m.getTag())) {
                map.put(m.getTag(), 1);
            } else {
                int t = map.get(m.getTag());
                map.put(m.getTag(), t + 1);
            }
        }
        List<Map.Entry<String, Integer>> t = new ArrayList<>();
        t.addAll(map.entrySet());
        Collections.sort(t, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        data = new ArrayList<>();
        for (Map.Entry<String, Integer> i : t) {
            data.add(i.getKey());
        }
        return data;
    }


    public void executeAllAnimations(View view) {
    /*
     *  创建一个AnimationSet，它能够同时执行多个动画效果
     *  构造方法的入参如果是“true”，则代表使用默认的interpolator，如果是“false”则代表使用自定义interpolator
     */
        AnimationSet animationSet = new AnimationSet(true);

    /*
     *  创建一个半透明效果的动画对象，效果从完全透明到完全不透明
     */
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);

    /*
     *  设置动画的持续时间
     */
        alphaAnimation.setDuration(3000);


    /*
     *  创建一个缩放效果的动画
     *  入参列表含义如下：
     *  fromX：x轴的初始值
     *  toX：x轴缩放后的值
     *  fromY：y轴的初始值
     *  toY：y轴缩放后的值
     *  pivotXType：x轴坐标的类型，有ABSOLUT绝对坐标、RELATIVE_TO_SELF相对于自身坐标、RELATIVE_TO_PARENT相对于父控件的坐标
     *  pivotXValue：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
     *  pivotYType：y轴坐标的类型
     *  pivotYValue：轴的值，0.5f表明是以自身这个控件的一半长度为y轴
     */
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1f, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    /*
     *  设置动画的持续时间
     */
        scaleAnimation.setDuration(3000);


    /*
     *  创建一个移动动画效果
     *  入参的含义如下：
     *  fromXType：移动前的x轴坐标的类型
     *  fromXValue：移动前的x轴的坐标
     *  toXType：移动后的x轴的坐标的类型
     *  toXValue：移动后的x轴的坐标
     *  fromYType：移动前的y轴的坐标的类型
     *  fromYValue：移动前的y轴的坐标
     *  toYType：移动后的y轴的坐标的类型
     *  toYValue：移动后的y轴的坐标
     */
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 300, Animation.RELATIVE_TO_SELF, 0);

    /*
     *  设置动画的持续时间
     */
        translateAnimation.setDuration(3000);

    /*
     *  将四种动画效果放入同一个AnimationSet中
     */
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);

    /*
     *  同时执行多个动画效果
     */
        view.startAnimation(animationSet);
    }
}
