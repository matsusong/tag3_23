package me.ez0ne.ouring.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Cerian on 2018/2/8.
 */

public class StringSMS extends DataSupport {
    private String phone;
    private String details;
    private String place;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
