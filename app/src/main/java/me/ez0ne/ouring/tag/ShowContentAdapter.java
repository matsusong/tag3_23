package me.ez0ne.ouring.tag;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.ez0ne.ouring.R;

/**
 * Created by MSI on 2018/3/7.
 */

public class ShowContentAdapter extends RecyclerView.Adapter<ShowContentAdapter.ViewHolder> {

    private List<Message> messages;
    private String tag;
    private ShowContentOfTagActivity activity;
     public TextView mname;
   public   TextView mnumber;

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mtimeanddate;
        private TextView mmessage;

        public ViewHolder(View view){
            super(view);
            Log.d("Debug","ViewHolder+"+"ViewHolder");
            mmessage = (TextView)itemView.findViewById(R.id.tv_message);
            mtimeanddate=(TextView)itemView.findViewById(R.id.tv_timeanddate);
        }
    }
    public ShowContentAdapter(List<Message> m, String t, ShowContentOfTagActivity s){
        Log.d("Debug","ShowContentAdapter+"+"ShowContentAdapter");
        messages=new ArrayList<>();
        for(int i=m.size()-1;i>=0;i--){
            messages.add(m.get(i));
        }
        tag = t;
        activity = s;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Debug","ShowContentAdapter+"+"onCreateViewHolder");
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_message_receive,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d("Debug","ShowContentAdapter+"+"onBindViewHolder");
        holder.mtimeanddate.setText(messages.get(position).getDate());
        holder.mmessage.setText(messages.get(position).getContent());

        holder.mmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastMessage(getDisplayNameByNumber(messages.get(position).getPhoneNumber()),messages.get(position).getPhoneNumber(),holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("Debug","ShowContentAdapter+"+"getItemCount");
        return messages.size();
    }

    private void ToastMessage(String name, String numeber,ViewHolder holder) {
        Log.d("Debug","ShowContentAdapter+"+"ToastMessage");
                //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
                LayoutInflater inflater = activity.getLayoutInflater();//调用Activity的getLayoutInflater()
                View view = inflater.inflate(R.layout.toast_imformation, null); //加載layout下的布局
                mname=view.findViewById(R.id.tv_toast_name);
                mnumber=view.findViewById(R.id.tv_toast_number);
                mname.setText(name);
                mnumber.setText(numeber);
                 Toast toast = new Toast(activity.getApplicationContext());
               toast.setGravity(Gravity.FILL_HORIZONTAL,0,0);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
                 toast.setDuration(Toast.LENGTH_LONG);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
                 toast.setView(view); //添加视图文件

                 toast.show();
             }

    public String getDisplayNameByNumber(String phoneNum) {
        Log.d("Debug","ShowContentAdapter+"+"getDisplayNameByNumber");
        String contactName = "";
        ContentResolver cr = activity.getContentResolver();
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

       /* pCur = cr.query(Uri.parse("content://icc/adn"),null,"number = ?",new String[]{phoneNum},null);
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
        }*/
        return contactName;
    }
}
