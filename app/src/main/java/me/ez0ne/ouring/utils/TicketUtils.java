package me.ez0ne.ouring.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import org.litepal.tablemanager.Connector;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.ez0ne.ouring.model.Ticket;

/**
 * Created by Cerian on 2017/11/15.
 */

public class TicketUtils {

    public static final Uri MESSAGR_ALL_URL= Uri.parse("content://sms/inbox");
    public static final Uri ALL_MESSAGE_URL=MESSAGR_ALL_URL.buildUpon().
            appendQueryParameter("simple","true").build();
    public static final String[] ALL_THREADS_PROJECTION ={
            "_id","address"};

    Context mContext;

    public TicketUtils(final Context context){mContext=context;}

    public Boolean TicketToDB()
    {
        SQLiteDatabase db= Connector.getDatabase();

        ContentResolver contentResolver=mContext.getContentResolver();
        Cursor cursor=contentResolver.query(ALL_MESSAGE_URL,ALL_THREADS_PROJECTION,
                null,null,"date desc");

        if(cursor==null)
        {
            return false;
        }
        else
        {
            while((cursor.moveToNext()))
            {
                int indexBody=cursor.getColumnIndex("body");//短信内容
                int indexAddress=cursor.getColumnIndex("address");//收件人
                String strbody=cursor.getString(indexBody);
                Log.i("ticketmess", strbody);
                String strAddress=cursor.getString(indexAddress);

                //根据发件人号码，判断是否是巴士管家短信
                if(StringUtils.isTicketMessage(strAddress))
                {
                    int date=cursor.getColumnIndex("date");
                    SimpleDateFormat dateFormat=new SimpleDateFormat("MM-dd hh:mm");

                    Date formatDate=new Date(Long.parseLong(cursor.getString(date)));
                    TimeUtils timeUtils = new TimeUtils();
                    String dateMms = dateFormat.format(formatDate);

                    Ticket ticket=new Ticket();
                    String ticket_time=StringUtils.getTime(strbody);
                    if(!ticket_time.equals(""))
                    {
                        ticket.setTime(ticket_time);
                    }
                    Log.i("ticket_time", ticket_time);
                    String start_station=StringUtils.getStart_Station(strbody);
                    String final_station=StringUtils.getFinal_Station(strbody);
                    String num_bus=StringUtils.getNum_Bus(strbody);
                    String num_ticket=StringUtils.getNum_Ticket(strbody);
                    String pwd_ticket=StringUtils.getPwd_Ticket(strbody);
                    String num_seat=StringUtils.getNum_Seat(strbody);
                    String num_check=StringUtils.getNum_check(strbody);
                    if(!start_station.equals("")&&!final_station.equals("")&&!num_bus.equals(""))
                    {
                        ticket.setStart_station(start_station);
                        ticket.setFinal_station(final_station);
                        ticket.setNum_bus(num_bus);
                    }
                    if(!num_ticket.equals("")&&!pwd_ticket.equals("")&&!num_seat.equals("")&&
                            !num_check.equals(""))
                    {
                        ticket.setNum_ticket(num_ticket);
                        ticket.setPassword_ticket(pwd_ticket);
                        ticket.setNum_seat(num_seat);
                        ticket.setnum_check(num_check);
                    }

                    int columnIndex=cursor.getColumnIndex("_id");
                    String TicketId=cursor.getString(columnIndex);
                    ticket.setIsTicket(true);
                    ticket.setTicketId(TicketId);
                    ticket.setTicketBody(strbody);
                    ticket.setTicketAddress(strAddress);
                    ticket.save();
                }
            }
            if(cursor.isClosed())
                return true;
            else
                cursor.close();
            return true;
        }
    }
}
