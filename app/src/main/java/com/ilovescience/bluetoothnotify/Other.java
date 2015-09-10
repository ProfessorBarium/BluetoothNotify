package com.ilovescience.bluetoothnotify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;


public class Other extends AppCompatActivity  {
    private static Other inst;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Set<String> myContactNumbers;
    Button addContact;
    Button addKeywordtext;
    Button addKeywordEmail;
    Button resetSharedPrefs;
    EditText contactInput;
    //int INDEX_CONTACT_SHARED_PREFS;

    public static Other instance() {
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
        setContentView(R.layout.activity_other);
        final Context context = this;
        sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();


        addContact = (Button)findViewById(R.id.button_add_contact);
        addKeywordtext = (Button)findViewById(R.id.button_add_keyword_text);
        addKeywordEmail = (Button)findViewById(R.id.button_add_keyword_email_subj);
        resetSharedPrefs = (Button)findViewById(R.id.button_reset);
        contactInput = (EditText)findViewById(R.id.editText_phone_contact);

        myContactNumbers = sharedPref.getStringSet(getString(R.string.my_set_saved_Callers),new HashSet<String>());//Retrieve the saved list of phone Numbers
        //INDEX_CONTACT_SHARED_PREFS = getContactIndex(); //look this number up, ALSO in the preference file

        addContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String newContactString = contactInput.getText().toString();
                String testString;
                String[] testStringArray;
                Set<String> testSet;
                setAddContact(newContactString);

                contactInput.setText("");//clear the input

                testSet = sharedPref.getStringSet(getString(R.string.my_set_saved_Callers), myContactNumbers);
                //int testInt = testSet.size();rs);
            }


        });

        resetSharedPrefs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                myContactNumbers = sharedPref.getStringSet(getString(R.string.my_set_saved_Callers),new HashSet<String>());

                //use this to only reset phone numbers
               // myContactNumbers=null;
               // editor.putStringSet(getString(R.string.my_set_saved_Callers), myContactNumbers);
            }
        });

    }
    public void checkContactList(Context context, SmsMessage mySMS)
    {
        String address = mySMS.getOriginatingAddress();

        if(address.equals("+16048162747")||address.equals("+16045625376"))
        {
            Toast.makeText(context, "BUZZZZ", Toast.LENGTH_SHORT).show();
            //openApp(context,"com.tumaku.msmble");
            //Intent myNewIntent = new Intent(this,Other.class);
            //startActivity(myNewIntent);
        }
        else
        {
            Toast.makeText(context, "Poop", Toast.LENGTH_SHORT).show();
        }
    }

    public void setAddContact(String contactNumber)
    {
        //better way to do this is probably to find the last item in the array, then append... consider an Object (struct?) type with all the info
        //phone number, contact name, buzz, light, light colour, sharedPreferences name
        //int nextAvailableIndex=i; //which digit is next
        //Resources res = getResources();
        //String[] phoneNumbers = res.getStringArray(R.array.caller_array);
        //editor.putString(phoneNumbers[nextAvailableIndex], contactNumber);
        //Set<String> myPhoneNumbers = sharedPref.getStringSet(getString(R.string.set_saved_Callers),new HashSet<String>());//Retrieve the saved list of phone Numbers
        myContactNumbers.add(contactNumber);
        editor.putStringSet(getString(R.string.my_set_saved_Callers),myContactNumbers); //duplicates not allowed, so sharedPrefs StringSet may be shorter than myContactNumbers
        editor.commit();
    }
    public void clearAllSharedPreferences()
    {
        editor.clear();
        editor.commit();

    }
    public int getContactIndex()
    {
        int contactIndex;
        //Set<String> nullSet = new HashSet<String>(){{}};
        Set<String> myPhoneNumbers = sharedPref.getStringSet(getString(R.string.my_set_saved_Callers),new HashSet<String>());//Retrieve the saved list of phone Numbers
        contactIndex = myPhoneNumbers.size();//number of elements stored
        return contactIndex;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_other, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_addContacts)
        {
            //editor.putString(getString(R.string.saved_caller1), "+16045625376");
            //editor.commit();
            // String testString = sharedPref.getString(getString(R.string.saved_caller1),"default");
            //Toast.makeText(this, testString, Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
