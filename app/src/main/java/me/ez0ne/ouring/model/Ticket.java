package me.ez0ne.ouring.model;

/**
 * Created by Cerian on 2017/11/13.
 */

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 【巴士管家】您购买的2017-09-15 18:30无锡/无锡客运站-南通/南通的WK0461次出票成功，
 * 凭订单详情中【条形码】
 * 检票可免取票上车，取票号：6364402，取票密码：379005，座位号：27，检票口：3，祝您旅途愉快！
 *
时间：日期和时间  始发站、终点站  车牌号 车次 取票号 取票密码 座位号 检票口
 */
public class Ticket extends DataSupport implements Serializable {


    public Boolean isTicket;

    private String ticketId;
    private String time;
    private String start_station;
    private String final_station;
    private String num_bus;
    private String num_trip;
    private String num_ticket;
    private String password_ticket;
    private String num_seat;
    private String num_check;
    public String ticketBody;
    public String ticketAddress;


    public String getTime() {
        return time;
    }

    public Boolean getIsTicket() {
        return isTicket;
    }

    public String getTicketBody(){return ticketBody;}

    public String getTicketId(){return ticketId;}

    public String getTicketAddress(){return ticketAddress;}
    public void setTicketAddress(String ticketAddress){this.ticketAddress=ticketAddress;}

    public void setIsTicket(Boolean isTicket){this.isTicket=isTicket;}

    public void setNum_check(String num_check){this.num_check=num_check;}

    public boolean getTicket(){return isTicket;}

    public void setIsTicket(boolean isTkicket){this.isTicket=isTkicket;}

    public void setTicketBody(String ticketBody){this.ticketBody=ticketBody;}

    public void setTicketId(String ticketId){this.ticketId=ticketId;}

    public void setTime(String time) {
        this.time = time;
    }

    public String getStart_station() {
        return start_station;
    }

    public void setStart_station(String start_station) {
        this.start_station = start_station;
    }

    public String getFinal_station() {
        return final_station;
    }

    public void setFinal_station(String final_station) {
        this.final_station = final_station;
    }

    public String getNum_bus() {
        return num_bus;
    }

    public void setNum_bus(String num_bus) {
        this.num_bus = num_bus;
    }

    public String getNum_trip() {
        return num_trip;
    }

    public void setNum_trip(String num_trip) {
        this.num_trip = num_trip;
    }

    public String getNum_ticket() {
        return num_ticket;
    }

    public void setNum_ticket(String num_ticket) {
        this.num_ticket = num_ticket;
    }

    public String getPassword_ticket() {
        return password_ticket;
    }

    public void setPassword_ticket(String password_ticket) {
        this.password_ticket = password_ticket;
    }

    public String getNum_seat() {
        return num_seat;
    }

    public void setNum_seat(String num_seat) {
        this.num_seat = num_seat;
    }

    public String getNum_check() {
        return num_check;
    }

    public void setnum_check(String num_ticketcheck) {
        this.num_check = num_ticketcheck;
    }




}
