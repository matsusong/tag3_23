package me.ez0ne.ouring.adapter;

import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import me.ez0ne.ouring.R;
import me.ez0ne.ouring.model.Ticket;

/**
 * Created by Cerian on 2017/11/20.
 */

public class TicketRecycleViewAapter extends RecyclerView.Adapter<TicketRecycleViewAapter.ViewHolder> {

    private List<Ticket> mlist;
    private OnItemClickListener listener;

    public TicketRecycleViewAapter(List<Ticket> list){
        this.mlist=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder=null;

        if(viewType==ITEM_TYPE.ITEM_TYPE_DATE.ordinal())
        {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_separation,parent,false);
            viewHolder=new ViewHolder(v);
        }
        if (viewType ==ITEM_TYPE.ITEM_TYPE_MESSAGE.ordinal())
        {
            View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_item,parent,false);
            viewHolder=new ViewHolder(v);
            viewHolder.tv_time= (TextView) v.findViewById(R.id.time);
            viewHolder.tv_startStation= (TextView) v.findViewById(R.id.start_station);
            viewHolder.tv_finalStation= (TextView) v.findViewById(R.id.final_station);
            viewHolder.tv_noTicket= (TextView) v.findViewById(R.id.tv_noTicket);
            viewHolder.tv_pwdTicket= (TextView) v.findViewById(R.id.tv_pwdTicket);
            viewHolder.tv_noSeat= (TextView) v.findViewById(R.id.tv_noSeat);
            viewHolder.tv_noCheckport= (TextView) v.findViewById(R.id.tv_noCheckport);
            viewHolder.item= (FrameLayout) v.findViewById(R.id.item_ticket);
            Log.e("TAG", "ticketadapter");
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TicketRecycleViewAapter.ViewHolder holder, final int position) {

        if(holder.getItemViewType()==ITEM_TYPE.ITEM_TYPE_MESSAGE.ordinal())
        {
            holder.tv_time.setText(mlist.get(position).getTime());
            holder.tv_startStation.setText(mlist.get(position).getStart_station());
            Log.i("tttttt", mlist.get(position).getTime());
            holder.tv_finalStation.setText(mlist.get(position).getFinal_station());
            holder.tv_noTicket.setText(mlist.get(position).getNum_ticket());
            holder.tv_pwdTicket.setText(mlist.get(position).getPassword_ticket());
            holder.tv_noSeat.setText(mlist.get(position).getNum_seat());
            holder.tv_noCheckport.setText(mlist.get(position).getNum_check());

            if(listener!=null)
            {
                holder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(v,position);
                    }
                });
            }


        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mlist.size() == 0) return ITEM_TYPE.ITEM_TYPE_DATE.ordinal(); // 解决快速滚动下拉带来的越界异常
        return mlist.get(position).getIsTicket() ? ITEM_TYPE.ITEM_TYPE_MESSAGE.ordinal() : ITEM_TYPE.ITEM_TYPE_DATE.ordinal();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public enum ITEM_TYPE{
        ITEM_TYPE_DATE,
        ITEM_TYPE_MESSAGE
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_time;
        TextView tv_startStation; //初始站
        TextView tv_finalStation; //终点站
        TextView tv_noTicket;       //取票号
        TextView tv_pwdTicket;     //取票密码
        TextView tv_noSeat;         //座位号
        TextView tv_noCheckport;    //检票口
        FrameLayout item;           //每行


        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
