package me.ez0ne.ouring.tag;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by Cerian on 2018/2/11.
 */

public class Message extends DataSupport {
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String content;
    private String tag;
    private String place;
    private String date;
    private int status;//判断接收方还是发送方

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
