package factor.app.luna;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by hassanhussain on 7/18/2016.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(getClass().getName(), "on receive - for alarm receiver.");
        Intent i = new Intent(context, LunaService.class);
        int nRequest = 0;
        if(intent != null){
            nRequest = intent.getIntExtra("REQUEST", 0);
        }
        i.putExtra("REQUEST", LunaConstants.REQUEST_SHOW_NOTIFY);
        i.putExtra("NOTIFY_REQUEST", nRequest);
        startWakefulService(context, i);
    }
}
