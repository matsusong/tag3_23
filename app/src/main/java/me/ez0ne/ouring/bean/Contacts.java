package me.ez0ne.ouring.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Cerian on 2018/2/5.
 */

public class Contacts  extends DataSupport {
    private String phone;
    private String name;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
