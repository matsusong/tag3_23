package me.ez0ne.ouring;

import org.junit.Test;

import me.ez0ne.ouring.utils.StringUtils;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
//    @Test
//    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
//    }
    @Test
    public void test(){
        String str = "您尾号9230卡3月30日11:35POS支出(转账)50元，余额203.03元。【工商银行】";
        String str1 = "您尾号1754的储蓄卡账户3月30日17时2分53秒余额宝提现收入人民币200.00元，活期余额403.03元。[建设银行]";
        System.out.println("工商银行------------------------");
        System.out.println(StringUtils.tryToGetBank(str));
        System.out.println(StringUtils.tryToGetCard(str));
        System.out.println(StringUtils.tryToGetType(str));
        System.out.println(StringUtils.tryToGetMoney(str) + "::" + StringUtils.moneyToFloat(StringUtils.tryToGetMoney(str)));
        System.out.println("余额：" + StringUtils.tryToGetLetf(str));
        System.out.println("建设银行------------------------");
        System.out.println(StringUtils.tryToGetBank(str1));
        System.out.println(StringUtils.tryToGetCard(str1));
        System.out.println(StringUtils.tryToGetType(str1));
        System.out.println(StringUtils.tryToGetMoney(str1) + "::" + StringUtils.moneyToFloat(StringUtils.tryToGetMoney(str1)));
        System.out.println("余额：" + StringUtils.tryToGetLetf(str1));

    }

}