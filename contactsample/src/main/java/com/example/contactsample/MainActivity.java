package com.example.contactsample;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                chooseContacts();
            }
        });

        //권한체크
        AutoPermissions.Companion.loadAllPermissions(this,101);
    }//end of oncreate
    public void chooseContacts(){
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 101)  {
            try {
                Uri contactsUri = data.getData();
                String id = contactsUri.getLastPathSegment();
                getContacts(id);
            }catch  (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getContacts(String id){
        Cursor cursor = null;
        String name = "";
        try{
            cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                    null,
                    ContactsContract.Data.CONTACT_ID + "=?",
                    new String[]{id},
                    null);
            if(cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                textView.setText(name);

                String columns[] = cursor.getColumnNames();
                for(String column : columns) {
                    int index = cursor.getColumnIndex(column);
                    String columnOutput = ("#"+index + "->["+ column +"]" + cursor.getString(index));
                    Log.d("Sample contact", columnOutput);
                }
                cursor.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDenied(int i, String[] strings) {

    }

    @Override
    public void onGranted(int i, String[] strings) {

    }
}
