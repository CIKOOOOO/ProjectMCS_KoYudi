package com.example.abedc.projectnotemcs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by abedc on 30/12/2017.
 */

public class Message extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = null;

        Object[] objects = (Object[]) bundle.get("pdus");
        messages = new SmsMessage[objects.length];

        String str ="Message : ";
        for (int i = 0; i < objects.length; i++) {
            messages[i] = SmsMessage.createFromPdu((byte[]) objects[i],bundle.getString("format"));
            str += messages[i].getMessageBody().toString();
        }
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

}
