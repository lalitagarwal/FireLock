package com.example.lagarwal.lock_library.authentication.patternLock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import com.example.lagarwal.lock_library.R;
import haibison.android.lockpattern.LockPatternActivity;
import haibison.android.lockpattern.util.AlpSettings;

public class GetPattern extends Activity {

    private static final int REQ_CREATE_PATTERN= 1;
    static char[] savedPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_get_pattern);
        Intent intent = new Intent(LockPatternActivity.ACTION_CREATE_PATTERN, null,
                this, LockPatternActivity.class);
        startActivityForResult(intent, REQ_CREATE_PATTERN);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_CREATE_PATTERN: {
                if (resultCode == RESULT_OK) {
                    char[] pattern = data.getCharArrayExtra(LockPatternActivity.EXTRA_PATTERN);
                    //final char[] pattern = getIntent().getCharArrayExtra(EXTRA_PATTERN);
                    //savedPattern= LockPatternUtils.patternToSha1(pattern).toCharArray();
                    AlpSettings.Security.setPattern(this, pattern);
                }

                break;
            }// REQ_CREATE_PATTERN
        }
    }
}
