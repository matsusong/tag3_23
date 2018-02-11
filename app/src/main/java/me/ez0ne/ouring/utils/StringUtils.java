package me.ez0ne.ouring.utils;


import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.ez0ne.ouring.model.BankCard;

/**
 * 正则匹配等处理
 * Created by Ezreal.Wan on 2016/4/19.
 */
public class StringUtils {
    private StringUtils() {
    }

    //根据号码判断是否是巴士管家短信
    public static boolean isTicketMessage(String address)
    {
        Boolean isTicketMessage=false;
        if(address.equals("10690329120581"))
        isTicketMessage=true;
        return isTicketMessage;
    }
    //日期
    public static String getTime(String str)
    {
        Pattern gettime=Pattern.compile("(\\d{4}-\\d{2}-\\d{2}\\s\\S{5})");
        Matcher m=gettime.matcher(str);
        String theTime="";
        while(m.find())
        {
            theTime=m.group();
        }
        return theTime;
    }
    //始发站
    public static String getStart_Station(String str)
    {
        Pattern getStart_Station=Pattern.compile("[\\u4e00-\\u9fa5]+[\\/][\\u4e00-\\u9fa5]+(?=\\-)");
        Matcher m=getStart_Station.matcher(str);
        String theStart_Station="";
        while(m.find())
        {
            theStart_Station=m.group();
        }
        return theStart_Station;
    }
    //终点站
    public static String getFinal_Station(String str)
    {
        Pattern getStart_Station=Pattern.compile("[\\u4e00-\\u9fa5]+[\\/][\\u4e00-\\u9fa5]+(?=的)");
        Matcher m=getStart_Station.matcher(str);
        String theFinal_Station="";
        while(m.find())
        {
            theFinal_Station=m.group();
        }
        return theFinal_Station;
    }
    //车次
    public static String getNum_Bus(String str)
    {
        Pattern getStart_Station=Pattern.compile("您购买的\\d{4}-\\d{2}-\\d{2}\\s\\S{5}\\S*\\-\\S*的(\\S*)次");
        Matcher m=getStart_Station.matcher(str);
        String s="";
        while(m.find())
        {
            s=m.group();
        }
        return s;
    }
    //取票号
    public static String getNum_Ticket(String str)
    {
        Pattern getStart_Station=Pattern.compile("取票号：\\d{7}");
        Matcher m=getStart_Station.matcher(str);
        String s="";
        while(m.find())
        {
            s=m.group();
        }
        return s;
    }
    //取票密码
    public static String getPwd_Ticket(String str)
    {
        Pattern getStart_Station=Pattern.compile("取票密码：\\d{6}");
        Matcher m=getStart_Station.matcher(str);
        String s="";
        while(m.find())
        {
            s=m.group();
        }
        return s;
    }
    //座位号
    public static String getNum_Seat(String str)
    {
        Pattern getStart_Station=Pattern.compile("座位号：\\d");
        Matcher m=getStart_Station.matcher(str);
        String s="";
        while(m.find())
        {
            s=m.group();
        }
        return s;
    }
    //检票口
    public static String getNum_check(String str)
    {
        Pattern getStart_Station=Pattern.compile("检票口：\\d");
        Matcher m=getStart_Station.matcher(str);
        String s="";
        while(m.find())
        {
            s=m.group();
        }
        return s;
    }



/*    public static String getInfo_ticket(String str)
    {
        Pattern getStart_Station=Pattern.compile("取票上车，(\\\\S*)，祝");
        Matcher m=getStart_Station.matcher(str);
        String s="";
        while(m.find())
        {
            s=m.group();
        }
        return s;
    }*/

    public static boolean isBankMessage(String address) {
        Boolean isBankMessage = false;
//        if (address.equals(BankAddress[i])) {
//            isBankMessage = true;
//        }
        //多银行 号码数组
        if (address.equals("95533") || address.equals("95588") || address.equals("95599")) {
            isBankMessage = true;
        }
        //仅匹配建设银行、工商银行、农业银行
        return isBankMessage;
    } //根据号码判断是否是银行短信





    public static String tryToGetBank(String str) {
        Pattern getBankPattern = Pattern.compile("\\[(.*?)\\]|\\【(.*?)\\】");//建设银行、工商银行
        Matcher m = getBankPattern.matcher(str);
        String theBank = "";
        while (m.find()) {
            theBank = m.group();
        }
        return theBank;
    } // 通过正则获取银行名称

    public static String tryToGetCard(String str) {
        Pattern getCardPattern = Pattern.compile("尾号\\d+");
        Matcher m = getCardPattern.matcher(str);
        String theCard = "";
        while (m.find()) {
            theCard = m.group();
        }
        return theCard;
    }
    // 卡号 6666

    public static String tryToGetMoney(String str) {
        Pattern getMoneyPattern = Pattern.compile("人民币\\d+.\\d+元|\\)\\d+.\\d+元|\\)\\d+元");
        Matcher m = getMoneyPattern.matcher(str);
        String theMoney = "";
        while (m.find()) {
            theMoney = m.group();
        }

        return theMoney;
    }
    // 人民币100.00元 正则

    public static float moneyToFloat(String money) {
        Pattern getMoneyPattern = Pattern.compile("\\d+.\\d+|\\d+");
        Matcher m = getMoneyPattern.matcher(money);
        float theFloatMoney = 0;
        while (m.find()) {
            theFloatMoney = Float.valueOf(m.group());
        }
        return theFloatMoney;
    }
    // 100.00 float 类型

    public static String tryToGetType(String str) {
        Pattern getTypePattern = Pattern.compile("支出|现支");
        Matcher m = getTypePattern.matcher(str);
        String theType = "收入";
        while (m.find()) {
          //theType = m.group();
            theType = "支出";
            //Type = 支出
        }
        return theType;
    }
    // 收入 or 支出 正则

    public static String typeToDetail(String type) {
        if (type == "支出") {
            return "消费支出";
        } else {
            return "转账收入";
        }
    }
    // 消费支出 或 转账收入

    public static String tryToGetLetf(String str) {
        Pattern getLeftPattern = Pattern.compile("余额\\d+.\\d+元");
        Matcher m = getLeftPattern.matcher(str);
        String theLeft = "";
        while (m.find()) {
            theLeft = m.group();
        }

//        Log.e("DEBUG", "theCard" + theLeft);
        return theLeft;
    }
    // 余额 正则


    /**
     * 根据短信获取描述文字
     * 您 建设银行 资金支出 100.00元
     * 资金 变动 100.00元
     *
     * @return
     */
    public static String getResultText(BankCard message, Boolean isNotificationText) {
        String resultStr = "";
        if (message.getBank() != null && !isNotificationText) {
            resultStr += "您" + message.getBank() + "资金";
        } else {
            resultStr += "资金";
        }
        if (message.getType() != null) {
            resultStr += message.getType();
        } else {
            resultStr += "变动：";
        }
        if (message.getMoney() != null) {
            resultStr += message.getMoney();
        } else {
            resultStr += "点击查看详情.";
        }
        return resultStr;
    }


    // 是否是菜鸟驿站的短信
    public static boolean isExpMsg(String body) {
        return body.contains("菜鸟驿站");
    }

    // 快递去货号匹配
    public static String tryToGetNum(String body) {
        Pattern getNumPattern = Pattern.compile("【(\\d+)】");
        Matcher m = getNumPattern.matcher(body);
        String theNum = "";
        while (m.find()) {
            theNum = m.group();
        }
        return theNum;
    }

    // 5月2日
    public static String tryToGetExpDate(String body) {
        Pattern getExpDatePattern = Pattern.compile("\\d+月\\d+日");
        Matcher m = getExpDatePattern.matcher(body);
        String theExpDate = "";
        while (m.find()) {
            theExpDate = m.group();
        }
        return theExpDate;
    }

    // 圆通 顺丰 百世汇通 等
    public static String tryToGetExpName(String body) {
        Pattern getExpName = Pattern.compile("[\\u4e00-\\u9fa5]+(?=包裹)");// 零宽断言正则
        Matcher m = getExpName.matcher(body);
        String theExpName = "";
        while (m.find()) {
            theExpName = m.group();
        }
        return theExpName;
    }



}
