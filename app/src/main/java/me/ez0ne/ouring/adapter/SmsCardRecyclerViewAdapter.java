package me.ez0ne.ouring.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.ez0ne.ouring.R;
import me.ez0ne.ouring.model.BankCard;

/**
 * Created by Ezreal.Wan on 2016/4/19.
 */

public class SmsCardRecyclerViewAdapter extends RecyclerView.Adapter<SmsCardRecyclerViewAdapter.ViewHolder> {

    private List<BankCard> mList;
    private OnItemClickListener listener;

    public SmsCardRecyclerViewAdapter(List<BankCard> messageList) {
        mList = messageList;
    }

    @Override
    public SmsCardRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;

         if (viewType == ITEM_TYPE.ITEM_TYPE_DATE.ordinal()) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_separation, parent, false);
            viewHolder = new ViewHolder(v);
            viewHolder.date = (TextView) v.findViewById(R.id.date_message_tv);
        }// 时间线布局
        if (viewType == ITEM_TYPE.ITEM_TYPE_MESSAGE.ordinal()) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder(v);
            viewHolder.card = (TextView) v.findViewById(R.id.tv_card);
            viewHolder.detail = (TextView) v.findViewById(R.id.tv_detail);
            viewHolder.avatar = (ImageView) v.findViewById(R.id.avatar);
            viewHolder.date = (TextView) v.findViewById(R.id.tv_time);
            viewHolder.money = (TextView) v.findViewById(R.id.tv_money);
            viewHolder.item = (FrameLayout) v.findViewById(R.id.item_message);
        }// 普通布局
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SmsCardRecyclerViewAdapter.ViewHolder holder, final int position) {
        if (holder.getItemViewType() == ITEM_TYPE.ITEM_TYPE_MESSAGE.ordinal()) {

            holder.card.setText(mList.get(position).getCard());// 尾号6666
            holder.detail.setText(mList.get(position).getType());// 消费支出 or 转账收入
            //@@@记得修改
            if (mList.get(position).getReceiveDate() != null) {
                holder.date.setText(mList.get(position).getReceiveDate());
            }// 时间
            holder.money.setText(signedMoney(mList.get(position).getFmoney())); // +1000.00元
            holder.money.setTextColor(colorOfMoney(mList.get(position).getFmoney())); // 红色还是绿色
            holder.avatar.setImageResource(bankToLogo(mList.get(position).getBank())); // 银行Logo

            if (listener != null) {
                holder.item.setOnClickListener(
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                listener.onItemClick(v, position);
                            }
                        }
                );
            }//

        } else {
            holder.date.setText(mList.get(position).getReceiveDate());
        } // 时间上栏
    }


    //辅助样式匹配
    private int bankToLogo(String bank) {
        if (bank.equals("[建设银行]")) {
            return R.drawable.bank_zgjsyh;
        } else if (bank.equals("中国银行")) {
            return R.drawable.bank_zgyh;
        } else if (bank.equals("【工商银行】")) {
            return R.drawable.bank_zggsyh;
        } else if(bank.equals("【中国农业银行】")){
           return R.drawable.bank_zgnyyh;
        } else
            return R.drawable.bank_visa;
    } // 根据银行名称匹配Logo

    private int colorOfMoney(float fmoney) {
        if (fmoney > 0) {
            return 0xff11CD6E;// 颜色的int值
        } else
            return 0xffEB4F38;// 同上
    } // 颜色

    private String signedMoney(float fmoney) {
        if (fmoney > 0) {
            return "+" + fmoney + "元";
        } else
            return fmoney + "元";
    } // +100.00元 / -100.00元


    @Override
    public int getItemCount() {
        return mList.size(); //
    }


    @Override
    public int getItemViewType(int position) {
        if (mList.size() == 0) return ITEM_TYPE.ITEM_TYPE_DATE.ordinal(); // 解决快速滚动下拉带来的越界异常
        return mList.get(position).getIsMessage() ? ITEM_TYPE.ITEM_TYPE_MESSAGE.ordinal() : ITEM_TYPE.ITEM_TYPE_DATE.ordinal();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
          }

    public enum ITEM_TYPE {
        ITEM_TYPE_DATE, // 时间栏
        ITEM_TYPE_MESSAGE // 信息
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar; // 银行Logo
        TextView card; // 尾号6666
        TextView detail; // 消费支出
        TextView date; // 时间
        TextView money; // 金额
        FrameLayout item; // 每行
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
