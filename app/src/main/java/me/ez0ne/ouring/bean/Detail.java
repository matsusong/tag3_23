package me.ez0ne.ouring.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Cerian on 2018/2/24.
 */

public class Detail extends DataSupport {
    private String phone;
    private String stringsms;
    private String place;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStringsms() {
        return stringsms;
    }

    public void setStringsms(String stringsms) {
        this.stringsms = stringsms;
    }
}
