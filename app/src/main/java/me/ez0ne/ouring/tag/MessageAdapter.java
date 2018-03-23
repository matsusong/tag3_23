package me.ez0ne.ouring.tag;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.ez0ne.ouring.R;

/**
 * Created by Cerian on 2018/2/11.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>  {

    private List<Message> messages;
    private String tag;
    private ShowMessageActivity activity;
    private int po;



    class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView mtimeanddate;
        private TextView mmessage;


        public ViewHolder(View itemView) {
            super(itemView);
            Log.d("Debug","ViewHolder+"+"ViewHolder");
            mmessage = (TextView)itemView.findViewById(R.id.tv_message);
            mtimeanddate=(TextView)itemView.findViewById(R.id.tv_timeanddate);

        }
    }

    public MessageAdapter(List<Message> m, String t, ShowMessageActivity s) {
        Log.d("Debug","MessageAdapter+"+"MessageAdapter");
        Log.d("mydebug", "MessageAdapter: "+String.valueOf(m.size())+String.valueOf(tag==null));
        messages=new ArrayList<>();
        for(int i=m.size()-1;i>=0;i--){
            messages.add(m.get(i));
        }
        tag = t;
        activity = s;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Debug","MessageAdapter+"+"MessageAdapter");
        Log.d("yan","onCreateViewHolder--type="+messages.get(po).getStatus());
        View view;
        if(messages.get(po).getStatus()==1){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_message_receive,parent,false);
        }
        else{
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_message_send,parent,false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Log.d("Debug","MessageAdapter+"+"onBindViewHolder");
        holder.mtimeanddate.setText(messages.get(position).getDate());
        holder.mmessage.setText(messages.get(position).getContent());
        /*if(tag!=null)
            return;*/
        holder.mmessage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,AddTagActivity.class);
                AddTagActivity.message = messages.get(position);
             //  Log.d("yan","测试");
                activity.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        Log.d("Debug","MessageAdapter+"+"getItemCount");
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("Debug","MessageAdapter+"+"getItemViewType");
        Log.d("yan","getItemViewType--position="+position);
        po=position;
        return super.getItemViewType(position);
    }
}
