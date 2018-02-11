package me.ez0ne.ouring.ui;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.List;

import me.ez0ne.ouring.R;
import me.ez0ne.ouring.adapter.OnItemClickListener;
import me.ez0ne.ouring.adapter.SmsCardRecyclerViewAdapter;
import me.ez0ne.ouring.adapter.TicketRecycleViewAapter;
import me.ez0ne.ouring.model.BankCard;
import me.ez0ne.ouring.model.Ticket;

/**
 * Created by ez0ne on 2016/3/1 0001.
 */
public class ticketFragment extends Fragment {


    int i = 1;
    private XRecyclerView mRecyclerView;
    private SmsCardRecyclerViewAdapter mViewAdapter;
    private List<Ticket> showTicketcard;
    private TicketRecycleViewAapter ticketRecycleViewAapter;
    private List<Ticket> pageTicketcard;
    private List<BankCard> showBankcard;
    private List<BankCard> pageBankcard;

    private SharedPreferences sharedPreferences;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sms_cards, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        boolean isDbReady = sharedPreferences.getBoolean("db_ready", false);

        mRecyclerView = (XRecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //mRecyclerView.addItemDecoration(new SeparatorItemDecoration(this.getContext()));
        //时间线 删掉吧

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        //mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);//下拉小箭头


        if (isDbReady) {
            showTicketcard=DataSupport.limit(20).find(Ticket.class);
            Log.i("showticket", showTicketcard.size()+"" );
            ticketRecycleViewAapter = new TicketRecycleViewAapter(showTicketcard);
            mRecyclerView.setAdapter(ticketRecycleViewAapter);


            ticketRecycleViewAapter.setOnItemClickListener(
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            if (showTicketcard.get(position).getIsTicket()) {
                                showDetailSMS(showTicketcard.get(position));
                            }
                        }
                    }
            );


            mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {

                            //刷新操作 基本无需操作
                            ticketRecycleViewAapter.notifyDataSetChanged();
                            mRecyclerView.refreshComplete();
                        }

                    }, 300);
                }

                @Override
                public void onLoadMore() {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            mRecyclerView.loadMoreComplete();

                            loadMore();
                            //下拉刷新数据
                            ticketRecycleViewAapter.notifyDataSetChanged();
                            mRecyclerView.refreshComplete();
                        }
                    }, 500);


                }
            });

        }
        //否则 过渡动画

    }


    private void loadMore() {
        pageBankcard = DataSupport.limit(20).offset(20 * i).find(BankCard.class);
        showBankcard.addAll(pageBankcard);
        i++;

    }


    private void showDetailSMS(Ticket ticket) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setTitle("巴士管家")
                .setMessage(ticket.getTicketBody())
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


}
