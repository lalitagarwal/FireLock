package com.example.lagarwal.lock_library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    StringBuilder strbDataEncrypted = new StringBuilder(),
            strbDataDecrypted = new StringBuilder();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            EncryptPassword objEP= new EncryptPassword(getApplicationContext());
            String encryptData = "1234+-ABCD";
            objEP.encrypt(encryptData, strbDataEncrypted);
            objEP.decrypt(strbDataEncrypted.toString(), strbDataDecrypted);
            Log.e("Encrypt", strbDataEncrypted.toString());
            Log.e("Decrypt", strbDataDecrypted.toString());
            Log.e("Matches", objEP.isPasswordEqual(strbDataEncrypted.toString()).toString());
        } catch (Exception e) {Log.e("Error", e.getMessage().toString());}
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }

}
