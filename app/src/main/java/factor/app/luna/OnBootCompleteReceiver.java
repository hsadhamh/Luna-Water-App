package factor.app.luna;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by hassanhussain on 7/16/2016.
 */
public class OnBootCompleteReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(getClass().getName(), "On Receive for Boot complete.");
        Intent i = new Intent();
        i.putExtra("REQUEST", LunaConstants.REQUEST_BOOT_COMPLETE);
        i.setClass(context, LunaService.class);
        startWakefulService(context, i);
    }
}
