package me.ez0ne.ouring.tag;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

import me.ez0ne.ouring.R;

public class ShowContentOfTagActivity extends AppCompatActivity {
    TextView title;
    Button back;
    ImageView imageView;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Debug","ShowContentOfTagActivity+"+"onCreate");
        setContentView(R.layout.activity_show_content_of_tag);
        initScreen();
        bindView();
        String tag;
        tag = getIntent().getStringExtra("tag");
        List<Message> messages;
            messages = DataSupport.where("tag = ? ",tag).find(Message.class);
            title.setText(tag);
        ShowContentAdapter adapter = new ShowContentAdapter(messages,tag,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void bindView(){
        Log.d("Debug","ShowContentOfTagActivity+"+"bindView");
        title=(TextView)findViewById(R.id.tv_showcontent_tag);
        back=(Button) findViewById(R.id.bt_showcontent_back);
        imageView=(ImageView)findViewById(R.id.iv_state);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView_showcontent);

    }

    private void initScreen(){
        Log.d("Debug","ShowContentOfTagActivity+"+"initScreen");
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
