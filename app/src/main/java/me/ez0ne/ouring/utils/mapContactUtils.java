package me.ez0ne.ouring.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.litepal.util.Const;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import me.ez0ne.ouring.App;
import me.ez0ne.ouring.bean.Contacts;
import me.ez0ne.ouring.bean.StringSMS;

/**
 * Created by Cerian on 2018/2/11.
 */

public class mapContactUtils {
    private static final int REQUECT_CODE_SDCARD =2 ;
    private List<Contacts> list;


    private String place;
    private int count;
    private String result="Return";
    private Context mcontext;
    public mapContactUtils(Context context){
        this.mcontext=context;
    }

    private String [] provinces={"北京","天津","上海","重庆","河北","山西","辽宁","黑龙江","吉林","江苏"
            ,"浙江","安徽","福建","江西","山东","河南","湖北","湖南","广东","海南","四川","贵州","云南","陕西","甘肃"
            ,"青海","内蒙古","广西","西藏","宁夏","新疆","澳门","香港","台湾"};
    public static final Uri MMSSMS_ALL_MESSAGE_URI = Uri.parse("content://sms/inbox");
    public static final Uri ALL_MESSAGE_URI = MMSSMS_ALL_MESSAGE_URI.buildUpon().
            appendQueryParameter("simple", "true").build();
    private static final String[] ALL_THREADS_PROJECTION = {
            "_id", "address", "person", "body",
            "date", "type", "thread_id"};
    //数据库初始化
    public void initDB(){
        SQLiteDatabase db = LitePal.getDatabase();
        for(int i=0;i<provinces.length;i++) {
            StringSMS sms = new StringSMS();
            sms.setCount(0);
            sms.setPlace(provinces[i]);
            sms.saveThrows();
            Log.d("provinceslength", provinces.length+"");
        }
    }
    //获取指定省份短信数量
    public StringSMS getInfo(String place2){
        StringSMS sms=new StringSMS();
        final List<StringSMS> list_sms2= DataSupport.where("place=?",place2).find(StringSMS.class);
        Log.i("result place",list_sms2.get(0).getPlace() );
        place=list_sms2.get(0).getPlace();
        count=list_sms2.get(0).getCount();
        sms.setPlace(place);
        sms.setCount(count);
        return sms;
    }
    //遍历收件箱获取号码归属地存入数据库
    public void getPlaceToDB(){
        SQLiteDatabase db = LitePal.getDatabase();
        Cursor cursor2 = mcontext.getContentResolver().query(ALL_MESSAGE_URI, ALL_THREADS_PROJECTION,
                null, null, "date desc");
        while ((cursor2.moveToNext())) {
            int indexBody = cursor2.getColumnIndex("body");// 短信内容
            int indexAddress = cursor2.getColumnIndex("address");// 发件人
            final String strbody = cursor2.getString(indexBody); // 获取短信内容
            String strAddress = cursor2.getString(indexAddress); // 获取发件人
            Log.i("address", strAddress + strAddress.length() + "");
            if (strAddress.length() > 0 && strAddress.length() == 14) {
                strAddress = strAddress.substring(3, 14);
                Log.d("222", strAddress);
            }
            if (strAddress.length() == 11) {
                RequestParams params = new RequestParams(App.appip + strAddress + "&key=22a6ba14995ce26dd0002216be51dabb");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject json = new JSONObject(result);
                            String error_code = json.getString("error_code");
                            Log.i("reason", error_code);
                            if (error_code.equals(0 + "")) {
                                JSONObject re = json.getJSONObject("result");
                                String province = re.getString("province");
                                Log.i("place", province);
                                StringSMS stringsms = new StringSMS();
                                final List<StringSMS> list_sms = DataSupport.where("place='江苏'").find(StringSMS.class);
                                count = list_sms.get(list_sms.size() - 1).getCount();
                                stringsms.setCount(count + 1);
                                stringsms.updateAll("place=?", province);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean b) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        }
        cursor2.close();
    }

    public StringSMS getIndexInfo(int i){
        StringSMS sms=new StringSMS();
        final List<StringSMS> list_sms2=DataSupport.order("count desc").find(StringSMS.class);
        if(list_sms2.isEmpty())
            Toast.makeText(mcontext,"收件箱没有符合要求的联系人短信",Toast.LENGTH_SHORT).show();
        else {
            count = list_sms2.get(i).getCount();
            place = list_sms2.get(i).getPlace();
            sms.setCount(count);
            sms.setPlace(place);
        }
        return sms;
    }

    public StringSMS getMinInfo(){
        StringSMS sms=new StringSMS();
        final List<StringSMS> list_sms2=DataSupport.order("count asc").find(StringSMS.class);
        Log.i("result place",list_sms2.get(0).getPlace() );
        place=list_sms2.get(0).getPlace();
        count=list_sms2.get(0).getCount();
        sms.setPlace(place);
        sms.setCount(count);
        return sms;
    }

    public int getCount(String place){
        StringSMS sms=new StringSMS();
        final List<StringSMS> list_sms2=DataSupport.where("place=?",place).find(StringSMS.class);
        count=list_sms2.get(0).getCount();
        return count;
    }
}
