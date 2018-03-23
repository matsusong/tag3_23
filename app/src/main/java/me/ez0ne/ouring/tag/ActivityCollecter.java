package me.ez0ne.ouring.tag;

import android.app.Activity;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MSI on 2018/3/8.
 */

public class ActivityCollecter
{
    public static List<Activity> activityArray=new ArrayList<>();

   public static void AddActivity(Activity activity){
       Log.d("Debug","ActivityCollecter+"+"AddActivity");
       activityArray.add(activity);
   }

   public static void FinishAll(){
       Log.d("Debug","ActivityCollecter+"+"FinishAll");
       for(Activity a:activityArray){

           if(!a.isFinishing())
               a.finish();
       }
       activityArray.clear();
   }

   public static void remove(Activity activity){
       for(int i=0;i<activityArray.size();i++){
           if(activityArray.get(i)==activity){
               activityArray.remove(i);
           }
       }
   }
}
