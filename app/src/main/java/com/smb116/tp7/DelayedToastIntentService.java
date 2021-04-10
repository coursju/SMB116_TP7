package com.smb116.tp7;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DelayedToastIntentService extends IntentService {

    private static final String TAG = DelayedToastIntentService.class.getSimpleName();
    @SuppressWarnings("deprecation")
    final Handler mHandler = new Handler();
    private int period;
    private final int TRENTE_SECONDES = 30;

    public DelayedToastIntentService() {
        super("name");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        period = intent.getIntExtra("period", 10);
        Log.i(TAG, "onHandleIntent, period = " + String.valueOf(period));

        /** Question 1 */
//         toast();

        /** Question 2 */
//            messenger(intent);

        /** Question 3 */
        if (period > TRENTE_SECONDES){
            alert();
        }else {
            messenger(intent);
        }

    }

    void toast() {
        SystemClock.sleep(period * 1000);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "délai écoulé: " + period, Toast.LENGTH_SHORT).show();
            }
        });
    }

    void messenger(Intent intent){
        SystemClock.sleep(period * 1000);
        Bundle extras = intent.getExtras();
        Messenger messager = (Messenger) extras.get("messager");
        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putInt("period", period);
        msg.setData(bundle);
        try {
            messager.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void alert(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), AlertDialogActivity.class);
                intent.putExtra("period", period);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
