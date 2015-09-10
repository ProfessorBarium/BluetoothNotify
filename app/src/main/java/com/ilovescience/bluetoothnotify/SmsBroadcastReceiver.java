package com.ilovescience.bluetoothnotify;

/**
 *Code from  http://javapapers.com/android/android-receive-sms-tutorial/
 * Adapted by Professor Barium on 9/2/2015.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            SmsMessage firstMessage = SmsMessage.createFromPdu((byte[]) sms[0]);


            //Check for a multiple message SMS... I think.
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";



            }
            //Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();

            //this will update the UI with message
            ReceiveSMSActivity inst = ReceiveSMSActivity.instance();
            //inst.updateList(smsMessageStr);
            inst.checkSender(context,firstMessage);
            //this causes a crash because of a NULL reference... probably because Other.instance() is calling something that doesn't already exist?
            //try just opening it w/ an intent
            /*Other myOther = Other.instance();
            myOther.checkSender(context, firstMessage);*/
        }
    }
}