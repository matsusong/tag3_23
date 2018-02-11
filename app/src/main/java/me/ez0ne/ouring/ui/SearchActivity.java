package me.ez0ne.ouring.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.ez0ne.ouring.R;

public class SearchActivity extends com.github.orangegangsters.lollipin.lib.PinActivity {

    private Button mButton;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // 测试使用
        mButton = (Button) findViewById(R.id.test_btn);
        mTextView = (TextView) findViewById(R.id.test_tv);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, GuideActivity.class);
                //intent.putExtra(AppLock.EXTRA_TYPE, AppLock.ENABLE_PINLOCK);
                //startActivityForResult(intent, 11);
                startActivity(intent);
            }
        });
    }
}
