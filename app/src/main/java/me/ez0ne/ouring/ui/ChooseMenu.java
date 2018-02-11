package me.ez0ne.ouring.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import me.ez0ne.ouring.R;

import static android.R.attr.onClick;

/**
 * Created by Cerian on 2017/12/4.
 */

public class ChooseMenu extends Activity implements View.OnClickListener {

    ImageView iv_ticket;
    ImageView iv_bankcard;
    ImageView iv_express;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_menu);

        iv_bankcard = (ImageView) findViewById(R.id.iv_bankcard);
        iv_express = (ImageView) findViewById(R.id.iv_express);
        iv_ticket = (ImageView) findViewById(R.id.iv_ticket);
        iv_bankcard.setOnClickListener(this);
        iv_ticket.setOnClickListener(this);
        iv_express.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_bankcard:
                Intent intent=new Intent();
                intent.setClass(ChooseMenu.this,MainActivity.class);
                intent.putExtra("choice","bankcard");
                startActivity(intent);
                break;
            case R.id.iv_ticket:
                Intent intent2=new Intent();
                intent2.setClass(ChooseMenu.this,MainActivity.class);
                intent2.putExtra("choice","ticket");
                startActivity(intent2);
                break;

            case R.id.iv_express:
//                Intent intent3=new Intent();
//                intent3.setClass(ChooseMenu.this,MainActivity.class);
//                intent3.putExtra("choice","express");
                Toast.makeText(ChooseMenu.this,"No Data",Toast.LENGTH_SHORT).show();
                break;

        }
    }


}
