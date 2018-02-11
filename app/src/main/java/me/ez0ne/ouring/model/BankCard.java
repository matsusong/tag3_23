package me.ez0ne.ouring.model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ezreal.Wan on 2016/4/18.
 * LitePal 数据库类
 */
public class BankCard extends DataSupport implements Serializable {


    /**
     * address : 95533
     * body : 您尾号6297的储蓄卡账户4月17日9时39分消费支出人民币10.00元,活期余额100.00元。[建设银行]
     * bank : 建设银行
     * card : 尾号6297
     * type : 支出
     * detail : 消费支出
     * money : 人民币100.00元
     * fmoney : 100.00
     * left : 1000.00元
     * readStatus
     * fromSmsDB
     * date
     * threadId
     */

    public Boolean isMessage; // 是否是银行短信,没必要写入数据库

    private String address; // 95535
    private String body; // 短信内容
    private String bank; // 建设银行
    private String card; //尾号6666
    private String type; // 支出 or 收入
    private String money; // 人民币 100.00元
    private float fmoney; // 100.00
    private String left; // 余额 1000.00元
    private String detail; // 交易支出 or 转账收入
    private Date date; // 100000000000000000000
    private int year;
    private int month;
    private int day;
    private String receiveDate; // 04-10 9:10
    private String smsId;
    private String resultContent;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public float getFmoney() {
        return fmoney;
    }

    public void setFmoney(float fmoney) {
        this.fmoney = fmoney;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public Boolean getIsMessage() {
        return isMessage;
    }

    public void setIsMessage(Boolean isMessage) {
        this.isMessage = isMessage;
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getResultContent() {
        return resultContent;
    }

    public void setResultContent(String resultContent) {
        this.resultContent = resultContent;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
