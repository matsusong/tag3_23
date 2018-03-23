package me.ez0ne.ouring.tag;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import me.ez0ne.ouring.R;

public class AddTagActivity extends AppCompatActivity  {
    CustomView customView;
    EditText input;
    Button submit,back;
    static Message message;
    ImageView imageView;
    private List<String> data;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollecter.remove(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Debug","AddTagActivity+"+"onCreate");
        setContentView(R.layout.activity_addtag);
        ActivityCollecter.AddActivity(this);
        bindView();
        initScreen();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input.getText().length()==0)
                {
                    Toast.makeText(AddTagActivity.this, "标签不能为空", Toast.LENGTH_SHORT).show();
                }
                else{
                    ActivityCollecter.FinishAll();
                    message.setTag(input.getText().toString());
                    message.save();

                }
            }
        });
        customView.setOnClickListener(new CustomView.OnTagClickListener() {
            @Override
            public void onClick(String tag) {
                   input.setText(tag);
                    submit.callOnClick();
            }
        });
        //将所有的tag按照出现的次数保存在list中
        List<String> data = getTagList();
        customView.addView(data);
    }

    public void bindView(){
        Log.d("Debug","AddTagActivity+"+"bindView");
        input=(EditText)findViewById(R.id.et_addtag_input);
        submit=(Button) findViewById(R.id.bt_addtag_positive);
        back=(Button) findViewById(R.id.bt_addtag_back);
        customView=(CustomView)findViewById(R.id.CustomView_addtag);


    }

    private List<String> getTagList(){
        Log.d("Debug","AddTagActivity+"+"getTagList");
        List<Message> messages = DataSupport.where("tag != ?","").find(Message.class);
        Map<String,Integer> map = new TreeMap<>();
        for(Message m:messages){
            if(!map.containsKey(m.getTag())){
                map.put(m.getTag(),1);
            }
            else{
                int t = map.get(m.getTag());
                map.put(m.getTag(),t+1);
            }
        }
        List<Map.Entry<String,Integer>> t = new ArrayList<>();
        t.addAll(map.entrySet());
        Collections.sort(t,new Comparator<Map.Entry<String,Integer>>(){
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        data = new ArrayList<>();
        for(Map.Entry<String,Integer> i:t){
            data.add(i.getKey());
        }
        return data;
    }

    private void initScreen(){
        Log.d("Debug","AddTagActivity+"+"initScreen");
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        else{
            imageView.setVisibility(View.GONE);
        }

    }
}
