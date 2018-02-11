package me.ez0ne.ouring.model;

import org.litepal.crud.DataSupport;

/**
 * 快递
 * Created by Ezreal.Wan on 2016/5/6.
 */
public class Express extends DataSupport {
    private String expname; // 快递公司名
    private String num; // 去货号
    private Boolean isExpMsg; // 是否是菜鸟驿站短信
    private String exdate; // 快递日期

    public String getExdate() {
        return exdate;
    }

    public void setExdate(String exdate) {
        this.exdate = exdate;
    }

    public String getExpname() {
        return expname;
    }

    public void setExpname(String expname) {
        this.expname = expname;
    }

    public Boolean getExpMsg() {
        return isExpMsg;
    }

    public void setExpMsg(Boolean expMsg) {
        isExpMsg = expMsg;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
