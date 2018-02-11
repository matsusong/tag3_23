package me.ez0ne.ouring.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.ez0ne.ouring.model.BankCard;
import me.ez0ne.ouring.model.Express;
import me.ez0ne.ouring.model.Ticket;
import me.ez0ne.ouring.utils.NotificationUtils;
import me.ez0ne.ouring.utils.StringUtils;

/**
 * Created by Ezreal.Wan on 2016/4/19.
 * 收到银行短信时，自动提取信息并存储数据库
 */
public class SMSBroadcastReceiver extends BroadcastReceiver {

    Intent mServiceIntent;//intent,主要用于解决Android应用的各项组件之间的通信

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        for (Object p : pdus) {
            byte[] sms = (byte[]) p;
            SmsMessage message = SmsMessage.createFromPdu(sms);
            //获取短信内容
            final String content = message.getMessageBody();
            //获取发送时间
            final Date date = new Date(message.getTimestampMillis());
            final String sender = message.getOriginatingAddress();

            // 快递短信处理
            if (StringUtils.isExpMsg(content)) {
                this.abortBroadcast();
                Express exp = new Express();
                String expName = StringUtils.tryToGetExpName(content);
                if (expName != null) {
                    exp.setExpname(expName);
                }
                String expDate = StringUtils.tryToGetExpDate(content);
                if (expDate != null) {
                    exp.setExdate(expDate);
                }
                String num = StringUtils.tryToGetNum(content);
                if (num != null) {
                    exp.setNum(num);
                }
                exp.save();

                NotificationUtils.showExpNotify(context, exp);
            }


            //巴士管家短信处理


            if(StringUtils.isTicketMessage(sender))
            {
               boolean isTicketMessage=true;
                if(isTicketMessage){
                    this.abortBroadcast();
                    Ticket ticketMessage=new Ticket();
                    ticketMessage.setIsTicket(true);
                    ticketMessage.setTicketBody(content);


                    String time=StringUtils.getTime(content);
                    ticketMessage.setTime(time);

                    String Start_Station=StringUtils.getStart_Station(content);
                    ticketMessage.setStart_station(Start_Station);

                    String Final_Station=StringUtils.getFinal_Station(content);
                    ticketMessage.setFinal_station(Final_Station);

                    String Num_Bus=StringUtils.getNum_Bus(content);
                    ticketMessage.setNum_bus(Num_Bus);

                    String Num_Ticket=StringUtils.getNum_Ticket(content);
                    ticketMessage.setNum_ticket(Num_Ticket);

                    String Pwd_Ticket=StringUtils.getPwd_Ticket(content);
                    ticketMessage.setPassword_ticket(Pwd_Ticket);

                    String Num_Seat=StringUtils.getNum_Seat(content);
                    ticketMessage.setNum_seat(Num_Seat);

                    String Num_Check=StringUtils.getNum_check(content);
                    ticketMessage.setnum_check(Num_Check);
                    ticketMessage.save();

                    mServiceIntent = new Intent(context, ShowInfoService.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("message", ticketMessage);
                    mServiceIntent.putExtra("bundle", bundle);
                    context.startService(mServiceIntent);
                }
            }


            // 银行短信处理
            if (StringUtils.isBankMessage(sender)) {
                boolean isBankMessage = true;

                if (isBankMessage) {
                    this.abortBroadcast(); // 系统短信通知不弹出
                    BankCard smsMessage = new BankCard();
                    smsMessage.setIsMessage(true);
                    // 是银行短信
                    smsMessage.setBody(content);
                    // 短信内容
                    smsMessage.setAddress(sender);
                    // 短信发信人
                    smsMessage.setDate(date);

                    //格式化短信日期提示
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd hh:mm");
                    //获得短信的各项内容
                    String date_mms = dateFormat.format(date);
                    smsMessage.setReceiveDate(date_mms);

                    String bank = StringUtils.tryToGetBank(content);
                    if (!bank.equals("")) {
                        smsMessage.setBank(bank);
                    } // 提取银行

                    String card = StringUtils.tryToGetCard(content);
                    if (!card.equals("")) {
                        smsMessage.setCard(card);
                    } // 提取卡号

                    String type = StringUtils.tryToGetType(content);
                    if (!type.equals("")) {
                        smsMessage.setType(type);
                    } // 提取 支出 or 收入

                    String money = StringUtils.tryToGetMoney(content);
                    if (!money.equals("")) {
                        smsMessage.setMoney(money);

                        float fmoney = StringUtils.moneyToFloat(money);
                        if (!type.equals("") && type.equals("支出")) {
                            fmoney = 0 - fmoney;
                        } else {
                            fmoney = 0 + fmoney;
                        }
                        smsMessage.setFmoney(fmoney);
                        //float 100.00/-100.00  含负号 方便计算和统计,图标生成
                    } // 提取金额 人民币100.00元

                    String left = StringUtils.tryToGetLetf(content);
                    if (!left.equals("")) {
                        smsMessage.setLeft(left);
                    } // 提取余额


                    String resultContent = StringUtils.getResultText(smsMessage, false);
                    if (resultContent != null) {
                        smsMessage.setResultContent(resultContent);

                        smsMessage.save();// 写入数据库
                    }

                    // 弹出通知
                    mServiceIntent = new Intent(context, ShowInfoService.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("message", smsMessage);
                    mServiceIntent.putExtra("bundle", bundle);
                    context.startService(mServiceIntent);
                }
            }// 银行短信处理
        }


    }
}
