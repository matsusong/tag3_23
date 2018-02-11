package me.ez0ne.ouring.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import org.litepal.tablemanager.Connector;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.ez0ne.ouring.model.BankCard;
import me.ez0ne.ouring.model.Ticket;

/**
 * Created by Ezreal.Wan on 2016/4/19.
 * 收件箱遍历 并写入数据库 所以只有首次运行时调用
 */
public class SmsUtils {


    //只检查收件箱的验证码信息
    public static final Uri MMSSMS_ALL_MESSAGE_URI = Uri.parse("content://sms/inbox");
    public static final Uri ALL_MESSAGE_URI = MMSSMS_ALL_MESSAGE_URI.buildUpon().
            appendQueryParameter("simple", "true").build();
    private static final String[] ALL_THREADS_PROJECTION = {
            "_id", "address", "person", "body",
            "date", "type", "thread_id"};
    Context mContext;

    public SmsUtils(final Context context) {
        mContext = context;
    }

    public boolean smsToDB() {
        SQLiteDatabase db = Connector.getDatabase();
        // 数据库初始化

        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(ALL_MESSAGE_URI, ALL_THREADS_PROJECTION,
                null, null, "date desc");

        if (cursor == null) {
            return false;
        } else {
            while ((cursor.moveToNext())) {
                int indexBody = cursor.getColumnIndex("body");// 短信内容
                int indexAddress = cursor.getColumnIndex("address");// 发件人
                //int indexThreadId = cursor.getColumnIndex("thread_id"); // 不同的对话
                String strbody = cursor.getString(indexBody); // 获取短信内容

                Log.i("message",strbody);
                String strAddress = cursor.getString(indexAddress); // 获取发件人


                if(StringUtils.isTicketMessage(strAddress))
                {
                    int date=cursor.getColumnIndex("date");
                    SimpleDateFormat dateFormat=new SimpleDateFormat("MM-dd hh:mm");

                    Date formatDate=new Date(Long.parseLong(cursor.getString(date)));
                    TimeUtils timeUtils = new TimeUtils();
                    String dateMms = dateFormat.format(formatDate);

                    Ticket ticket=new Ticket();
                    String ticket_time=StringUtils.getTime(strbody);
                    ticket.setTime(ticket_time);
                    Log.i("ticket_time", ticket_time);
                    String start_station=StringUtils.getStart_Station(strbody);
                    String final_station=StringUtils.getFinal_Station(strbody);
                    String num_bus=StringUtils.getNum_Bus(strbody);
                    String num_ticket=StringUtils.getNum_Ticket(strbody);
                    String pwd_ticket=StringUtils.getPwd_Ticket(strbody);
                    String num_seat=StringUtils.getNum_Seat(strbody);
                    String num_check=StringUtils.getNum_check(strbody);
                        ticket.setStart_station(start_station);
                        ticket.setFinal_station(final_station);
                        ticket.setNum_bus(num_bus);


                        ticket.setNum_ticket(num_ticket);
                        ticket.setPassword_ticket(pwd_ticket);
                        ticket.setNum_seat(num_seat);
                        ticket.setnum_check(num_check);


                    int columnIndex=cursor.getColumnIndex("_id");
                    String TicketId=cursor.getString(columnIndex);
                    ticket.setIsTicket(true);
                    ticket.setTicketId(TicketId);
                    ticket.setTicketBody(strbody);
                    ticket.setTicketAddress(strAddress);
                    ticket.saveThrows();
                }

                //根据发件人号码 判断是否是银行短信
                if (StringUtils.isBankMessage(strAddress)) {
                    int date = cursor.getColumnIndex("date");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd hh:mm");// 格式化短信日期提示

                    Date formatDate = new Date(Long.parseLong(cursor.getString(date)));//10000000000 时间戳
                    TimeUtils timeUtils = new TimeUtils();

                    //long threadId = cursor.getLong(indexThreadId); // 获取对话
                    //获得短信的各项内容
                    String dateMms = dateFormat.format(formatDate); // 格式化时间

                    BankCard bankCard = new BankCard();// BankCard

                    // 屏蔽验证码等银行短信
                    String bank = StringUtils.tryToGetBank(strbody);
                    Log.i("bank", bank);
                    String type = StringUtils.tryToGetType(strbody);
                    if (bank != null && type != null) {
                        bankCard.setBank(bank);
                        bankCard.setType(type);
                        String detail = StringUtils.typeToDetail(type);
                        bankCard.setDetail(detail);
                        //Type 支出 or 收入
                    } // Bank 建设银行
                    String card = StringUtils.tryToGetCard(strbody);
                    if (!card.equals("")) {
                        bankCard.setCard(card);
                    } //Card 尾号xxxx
                    String money = StringUtils.tryToGetMoney(strbody);
                    if (!money.equals("")) {
                        bankCard.setMoney(money);
                        float fmoney = StringUtils.moneyToFloat(money);
                        if (!type.equals("") && type.equals("支出")) {
                            fmoney = 0 - fmoney;
                        } else {
                            fmoney = 0 + fmoney;
                        }
                        bankCard.setFmoney(fmoney);
                    } //Money 金额 人民币100.00元
                    String left = StringUtils.tryToGetLetf(strbody);
                    if (!left.equals("")) {
                        bankCard.setLeft(left);
                    } //Left 余额

                    int columnIndex = cursor.getColumnIndex("_id"); // _id
                    String smsId = cursor.getString(columnIndex);
                    bankCard.setIsMessage(true); // Message
                    bankCard.setDate(formatDate); // 时间
                    bankCard.setAddress(strAddress); // 发件人
                    bankCard.setBody(strbody); // 短信内容
                    bankCard.setSmsId(smsId); // Sms_id
                    bankCard.setReceiveDate(dateMms); // 时间 04-20 11:50


                    //数据溢出
                    bankCard.setYear(timeUtils.getYear(formatDate));
                    bankCard.setMonth(timeUtils.getMonth(formatDate));
                    bankCard.setDay(timeUtils.getDay(formatDate));
                    bankCard.save();// 写入数据库
                }





            }
            cursor.close();// 关闭游标

            return true;// 写入数据库成功
        }
    }
}
