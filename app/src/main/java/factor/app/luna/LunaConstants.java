package factor.app.luna;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by hassanhussain on 7/18/2016.
 */
public class LunaConstants {
    public static final int REQUEST_GOOD_MORN = 5000;
    public static final int REQUEST_BOOT_COMPLETE = 5001;
    public static final int REQUEST_GOOD_NIGHT = 5002;
    public static final int REQUEST_HAVE_DRINK = 5003;
    public static final int REQUEST_LUNCH = 5004;
    public static final int REQUEST_BRUNCH = 5005;
    public static final int REQUEST_SNACK = 5006;
    public static final int REQUEST_BREAKFAST = 5007;
    public static final int REQUEST_SCHEDULE_ALARMS = 5008;
    public static final int REQUEST_DINNER = 5009;
    public static final int REQUEST_HAVE_NICE_DAY = 5010;
    public static final int REQUEST_HAVE_JUICE = 5011;
    public static final int REQUEST_SHOW_NOTIFY = 5012;
    /*
    * 0630 - GM
    * 0700 - HAVE A NICE DAY
    * 0800 - HAVE BREAKFAST
    * 0930 - HAVE A JUICE
    * 1030 - HAVE A DRINK
    * 1130 - GET SOME DISTRACTION
    * 1300 - LUNCH TIME
    * 1430 - HAVE A DRINK
    * 1600 - HAVE A DRINK & SNACK
    * 1730 - DRINK JUICE
    * 1900 - DRINK WATER
    * 2030 - DINNER TIME
    * 2200 - GOOD NIGHT
    * */

    public static void ScheduleAlarms(Context context){
        setNextDayAlarm(context, REQUEST_GOOD_MORN, 0, false);
        setNextDayAlarm(context, REQUEST_HAVE_NICE_DAY, 0, false);
        setNextDayAlarm(context, REQUEST_BREAKFAST, 0, false);
        setNextDayAlarm(context, REQUEST_HAVE_JUICE, 0, false);
        setNextDayAlarm(context, REQUEST_HAVE_DRINK, 0, false);
        setNextDayAlarm(context, REQUEST_BRUNCH, 0, false);
        setNextDayAlarm(context, REQUEST_LUNCH, 0, false);
        setNextDayAlarm(context, REQUEST_HAVE_DRINK, 1, false);
        setNextDayAlarm(context, REQUEST_SNACK, 0, false);
        setNextDayAlarm(context, REQUEST_HAVE_DRINK, 2, false);
        setNextDayAlarm(context, REQUEST_HAVE_DRINK, 3, false);
        setNextDayAlarm(context, REQUEST_DINNER, 0, false);
        setNextDayAlarm(context, REQUEST_GOOD_NIGHT, 0, false);
    }

    public static void setAlarm(AlarmManager alarmManager, int hh, int mm, int ss, PendingIntent pendingIntent, boolean nextDay){
        Log.d("set alarm", "setting  alarms" + hh + mm + ss );
        Calendar calendar = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hh);
        calendar.set(Calendar.MINUTE, mm);
        calendar.set(Calendar.SECOND, ss);
        if(calendar.before(now) || nextDay){
            calendar.add(Calendar.DATE, 1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        else{
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    public static void setNextDayAlarm(Context context, int nNotifyRequest, int times, boolean nextDay){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra("REQUEST", nNotifyRequest);
        PendingIntent intentPending = PendingIntent.getBroadcast(context, nNotifyRequest,
                alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int hr = 0, mm = 0, ss = 0;
        switch(nNotifyRequest){
            case LunaConstants.REQUEST_GOOD_MORN:
                hr = 6;
                mm = 30;
                ss = 0;
                break;
            case LunaConstants.REQUEST_GOOD_NIGHT:
                hr = 22;
                mm = 0;
                ss = 0;
                break;
            case LunaConstants.REQUEST_HAVE_DRINK:
                hr = (times == 0 ? 10 : times == 1 ? 14 : times == 2 ? 17 : 19);
                mm = (times == 2 ? 30 : 0);
                ss = 0;
                break;
            case LunaConstants.REQUEST_LUNCH:
                hr = 13;
                mm = 0;
                ss = 0;
                break;
            case LunaConstants.REQUEST_BRUNCH:
                hr = 11;
                mm = 30;
                ss = 0;
                break;
            case LunaConstants.REQUEST_SNACK:
                hr = 16;
                mm = 0;
                ss = 0;
                break;
            case LunaConstants.REQUEST_BREAKFAST:
                hr = 8;
                mm = 0;
                ss = 0;
                break;
            case LunaConstants.REQUEST_DINNER:
                hr = 20;
                mm = 30;
                ss = 0;
                break;
            case LunaConstants.REQUEST_HAVE_NICE_DAY:
                hr = 7;
                mm = 0;
                ss = 0;
                break;
            case LunaConstants.REQUEST_HAVE_JUICE:
                hr = 9;
                mm = 30;
                ss = 0;
               break;
        }
        setAlarm(alarmManager, hr, mm, ss, intentPending, nextDay);
    }

}
