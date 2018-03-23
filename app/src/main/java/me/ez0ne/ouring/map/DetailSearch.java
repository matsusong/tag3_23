package me.ez0ne.ouring.map;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import me.ez0ne.ouring.R;
import me.ez0ne.ouring.bean.Contacts;
import me.ez0ne.ouring.bean.Detail;
import me.ez0ne.ouring.bean.StringSMS;
import me.ez0ne.ouring.utils.mapContactUtils;

import static org.litepal.LitePalApplication.getContext;

/**
 * Created by Cerian on 2018/2/24.
 */

public class DetailSearch extends Activity implements AdapterView.OnItemSelectedListener {

    private Detail detail;
    private Button back;
    private ListView lv_details;
    private Spinner spinner=null;
    private List<String> list_spinner=new ArrayList<>();
    private ArrayAdapter adapter;
    private mapContactUtils contactUtils=new mapContactUtils(DetailSearch.this);

    private List<Detail> list=new ArrayList<Detail>();
    private DetailAdapter detailAdapter;
    private String []provinces={"江苏","天津","上海","重庆","河北", "山西","辽宁",
            "黑龙江", "吉林" , "北京" , "浙江", "安徽" , "福建" ,
            "江西" , "山东", "河南", "湖北", "湖南",
            "广东", "海南", "四川", "贵州", "云南",
            "陕西", "甘肃", "青海", "内蒙古", "广西",
            "西藏", "宁夏", "新疆", "澳门", "香港",
            "台湾"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapdetails);
        initView();
        setData();
        adapter=new ArrayAdapter(DetailSearch.this,android.R.layout.simple_list_item_1, list_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DetailSearch.this,Mapview.class);
                intent.putExtra("from","detail");
                startActivity(intent);
            }
        });

    }

    private void initView() {
        spinner= (Spinner) findViewById(R.id.spinner);
        back= (Button) findViewById(R.id.back);
        lv_details= (ListView) findViewById(R.id.lv_detail);
    }

    public  void setData() {
        for (int i = 0; i < provinces.length; i++) {
            list_spinner.add(provinces[i]);
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        view.invalidate();
        String province= (String) adapter.getItem(i);
        list=contactUtils.getDetails(province);
        for(int j=0;j<list.size();j++){
            List<Contacts>list2=DataSupport.where("phone=?",list.get(j).getPhone()).find(Contacts.class);
            Log.i("aaaaa",list.get(j).getPhone() );
            if(list2.size()>0){
                String phonename=list2.get(0).getName();
                list.get(j).setPhone(phonename);
            }
        }
        if(list.size()!=0){
            detailAdapter=new DetailAdapter(DetailSearch.this,list);
            lv_details.setAdapter(detailAdapter);
            lv_details.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Detail detail= (Detail) detailAdapter.getItem(i);
                    AlertDialog alertDialog = new AlertDialog.Builder(DetailSearch.this)
                            .setTitle(detail.getPhone())
                            .setMessage(detail.getStringsms())
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
            });
        }
        else
            Toast.makeText(DetailSearch.this,"收件箱没有来自该省的短信",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
