package me.ez0ne.ouring.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import me.ez0ne.ouring.R;
import me.ez0ne.ouring.bean.Detail;

/**
 * Created by Cerian on 2018/2/24.
 */

public class DetailAdapter extends BaseAdapter {
    /**
     * 上下文对象
     * 容器
     * 打气筒
     * 数据源
     */
    private Context context;
    private LayoutInflater inflater;
    private List<Detail> list=new ArrayList<>();
    private Detail bean_detail;

    //构造函数
    public DetailAdapter(Context context,List<Detail>list){
        this.context=context;
        this.list=list;
        inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder=null;
        if(view==null){
            holder=new viewHolder();
            view=inflater.inflate(R.layout.bean_listdetail,null);
            holder.tv_content= (TextView) view.findViewById(R.id.content);
            holder.tv_phone= (TextView) view.findViewById(R.id.phone);
            view.setTag(holder);
        }
        else {
            holder= (viewHolder) view.getTag();
        }
        bean_detail= (Detail) getItem(i);
        holder.tv_phone.setText(bean_detail.getPhone());
        holder.tv_content.setText(bean_detail.getStringsms());
        return view;
    }

    /**
     * 优化
     */
    public class viewHolder{
        private TextView tv_phone;
        private TextView tv_content;
    }
}
