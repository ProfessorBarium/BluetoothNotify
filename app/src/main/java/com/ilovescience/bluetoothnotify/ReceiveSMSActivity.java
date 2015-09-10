package com.ilovescience.bluetoothnotify;


import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReceiveSMSActivity extends Activity implements AdapterView.OnItemClickListener {

    private static ReceiveSMSActivity inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;

    public static ReceiveSMSActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_sms);
        smsListView = (ListView) findViewById(R.id.SMSList);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);
        smsListView.setOnItemClickListener(this);

        refreshSmsInbox();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_other, menu);
        return true;
    }


    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
        do {
            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) + "\n";
            arrayAdapter.add(str);
        } while (smsInboxCursor.moveToNext());
    }

    public void updateList(final String smsMessage) {
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();
    }

    public static void openApp(Context context, String packageName) { //launch another program... probably don't need this after all.
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {

                throw new PackageManager.NameNotFoundException();
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(i);
            return;
        } catch (PackageManager.NameNotFoundException e) {
            return;
        }
    }


    public void checkSender(Context context, SmsMessage mySMS)
    {
        String address = mySMS.getOriginatingAddress();

        if(address.equals("+16048162747")||address.equals("+16045625376"))
        {
            Toast.makeText(context, "BUZZZZ", Toast.LENGTH_SHORT).show();
            //openApp(context,"com.tumaku.msmble");
            Intent myNewIntent = new Intent(this,Other.class);
            startActivity(myNewIntent);
        }
        else
        {
            Toast.makeText(context, "Poop", Toast.LENGTH_SHORT).show();
        }
    }

    public void tempClickCheck(Context context, String address )
    {
        if(address.equals("+16048162747")||address.equals("+16045625376"))
        {
            Toast.makeText(context, "BUZZZZ", Toast.LENGTH_SHORT).show();
            //openApp(context,"com.tumaku.msmble");
            Intent myNewIntent = new Intent(this,Other.class);
            //add in EXTRA_DATA
            startActivity(myNewIntent);
        }
        else
        {
            Toast.makeText(context, "Poop", Toast.LENGTH_SHORT).show();
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        try {
            String[] smsMessages = smsMessagesList.get(pos).split("\n");
            String address = smsMessages[0];
            String phoneNumberString = address.substring(10);
            //String address = "+16045625376";
            String smsMessage = "";
            String someString = "WTF?";
            for (int i = 1; i < smsMessages.length; ++i) {
                smsMessage += smsMessages[i];
            }

            String smsMessageStr = address + "\n";
            smsMessageStr += smsMessage;
            //Toast.makeText(this, smsMessageStr, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, phoneNumberString, Toast.LENGTH_SHORT).show();
            tempClickCheck(this,phoneNumberString);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}