package factor.app.luna;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by hassanhussain on 7/18/2016.
 */
public class LunaService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LunaService(String name) {
        super(name);
    }

    public LunaService() {
        super("LunaService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(getClass().getName(), "On Handle intent - for service.");
        WakefulBroadcastReceiver.completeWakefulIntent(intent);

        int nRequest = 0;
        if(intent != null){
            nRequest = intent.getIntExtra("REQUEST", 0);
        }
        if(nRequest == LunaConstants.REQUEST_BOOT_COMPLETE || nRequest == LunaConstants.REQUEST_SCHEDULE_ALARMS) {
            Log.d(getClass().getName(), "Scheduling alarms");
            LunaConstants.ScheduleAlarms(getApplicationContext());
        }
        else if(nRequest == LunaConstants.REQUEST_SHOW_NOTIFY){
            int nNotifyRequest = intent.getIntExtra("NOTIFY_REQUEST", 0);
            String Content, title, bigText;

            switch(nNotifyRequest){
                case LunaConstants.REQUEST_GOOD_MORN:
                    Content = "One of the awesome days, lets make it memorable and endurable. Come & lets step out.";
                    title = "Hello! Good Morning.";
                    bigText = "The world is beautiful outside when there is stability inside.";
                    CreateNotification(title, Content, bigText, LunaConstants.REQUEST_GOOD_MORN,
                            R.drawable.partly_cloudy_day);
                    break;

                case LunaConstants.REQUEST_GOOD_NIGHT:
                    Content = "You did awesome today!";
                    title = "Have a good night sleep.";
                    bigText = "If it is bad day, you need to remember is 'IT SHALL PASS'. " +
                                "Now close your eyes and take deep breath. " +
                                "You were AWESOME for the day!";
                    CreateNotification(title, Content, bigText, LunaConstants.REQUEST_GOOD_NIGHT,
                            R.drawable.night_portrait);
                    break;

                case LunaConstants.REQUEST_HAVE_DRINK:
                    Content = "Reminder for drink";
                    title = "Get some drink for you.";
                    bigText = "You are adorable and to continue that adore, please have a drink.";
                    CreateNotification(title, Content, bigText, LunaConstants.REQUEST_HAVE_DRINK, R.drawable.espresso_cup);
                    break;

                case LunaConstants.REQUEST_LUNCH:
                    Content = "Its lunch time.";
                    title = "Lunch time";
                    bigText = "Have a delicious & healthy lunch.";
                    CreateNotification(title, Content, bigText, LunaConstants.REQUEST_LUNCH, R.drawable.dining_room);
                    break;

                case LunaConstants.REQUEST_BRUNCH:
                    Content = "Brunch time now";
                    title = "Brunch time now";
                    bigText = "Get some cookies to fill your tummy.";
                    CreateNotification(title, Content, bigText, LunaConstants.REQUEST_BRUNCH, R.drawable.cookies);
                    break;

                case LunaConstants.REQUEST_SNACK:
                    Content = "Have some snack and drink.";
                    title = "Snacks time";
                    bigText = "You should be hungry, so eat snack and drink.";
                    CreateNotification(title, Content, bigText, LunaConstants.REQUEST_SNACK, R.drawable.espresso_cup);
                    break;

                case LunaConstants.REQUEST_BREAKFAST:
                    Content = "You should eat healthy breakfast.";
                    title = "Breakfast time";
                    bigText = "Have breakfast with pomegranate juice. Keep smiling.";
                    CreateNotification(title, Content, bigText, LunaConstants.REQUEST_BREAKFAST, R.drawable.pancake);
                    break;

                case LunaConstants.REQUEST_DINNER:
                    Content = "Have a light dinner to keep you healthy.";
                    title = "Dinner time";
                    bigText = "Have a good, light & healthy dinner.";
                    CreateNotification(title, Content, bigText, LunaConstants.REQUEST_DINNER, R.drawable.dining_room);
                    break;

                case LunaConstants.REQUEST_HAVE_NICE_DAY:
                    Content = "One of the awesome days, lets make it memorable and endurable.";
                    title = "Have a nice day!";
                    bigText = "The world is beautiful outside when there is stability inside.";
                    CreateNotification(title, Content, bigText, LunaConstants.REQUEST_HAVE_NICE_DAY, R.drawable.bus);
                    break;

                case LunaConstants.REQUEST_HAVE_JUICE:
                    Content = "Have a drink";
                    title = "Drink some juice.";
                    bigText = "'Anar' is your favorite, how about 'ICE CREAMS'?";
                    CreateNotification(title, Content, bigText, LunaConstants.REQUEST_HAVE_JUICE, R.drawable.coffee_to_go);
                    break;
            }
            Calendar now = Calendar.getInstance();
            int hh = now.get(Calendar.HOUR);
            int times = (hh == 10 ? 0 : hh == 14 ? 1 : hh == 17 ? 2 : 3);
            LunaConstants.setNextDayAlarm(getApplicationContext(), nNotifyRequest, times, true);
        }
        this.stopSelf();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void CreateNotification(String title, String content, String bigContent, int request, int resID){
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("CONTENT", content);
        intent.putExtra("BIG_CONTENT", bigContent);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, request);

        Notification notification = new Notification();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        // Build notification
        // Actions are just fake
        Notification noti = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pIntent)
                .setSmallIcon(resID)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setLights(Color.CYAN, 3000, 3000)
                .setDefaults(notification.defaults)
                .setStyle(new
                        NotificationCompat.BigTextStyle()
                        .bigText(bigContent))
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(request, noti);
    }

}
