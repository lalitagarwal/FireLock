package com.example.lagarwal.lock_library.authentication.pinLock;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lagarwal.lock_library.R;

import java.util.Timer;
import java.util.TimerTask;

public class PinLock extends Activity {
    private View bgview, lockview, textview;
    EditText userinput;
    Vibrator vibe;
    boolean scrollable=true;
    private String lock_category;
    private int fadeInDelay, lockDelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras= getIntent().getExtras();
        lock_category= extras.getString("category");
        fadeInDelay= extras.getInt("fadeInDelay", 8);
        lockDelay= extras.getInt("lockDelay", 4);
        Log.e("Fade in", (Integer.toString(lockDelay)));
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_lock);
        bgview= findViewById(R.id.pin_lock);
        lockview=findViewById(R.id.numpad_layout);
        textview=findViewById(R.id.edittext_layout);
        bgview.setBackgroundColor(Color.parseColor("#50000000"));
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        userinput = (EditText) bgview.findViewById(R.id.userInput);
        userinput.setInputType(InputType.TYPE_CLASS_NUMBER);
        updateLockUI(lock_category);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        hide_keyboard();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        hide_keyboard();
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

    public void updateUI(){
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //hide_keyboard();

                            }
                        });
                    }
                },
                100
        );
    }

    public void updateLockUI(String category){
        if(category.equals("imm_trans")){
            scrollable=false;
            updateUI();
            bgview.setBackgroundColor(Color.parseColor("#50000000"));
        } else if(category.equals("imm_dark")){
            scrollable=false;
            updateUI();
            bgview.setBackgroundColor(Color.parseColor("#F9000000"));
        }else if (category.equals("del_dark_pin_start")) {
            scrollable=false;
            updateUI();
            final ObjectAnimator backgroundColorAnimator1 = ObjectAnimator.ofObject(bgview, "backgroundColor", new ArgbEvaluator(),
                    0x40000000, 0x50000000, 0x60000000, 0x80000000, 0x89000000, 0x99000000,
                    0xA9000000, 0xB9000000,0xC9000000, 0xD9000000,0xE9000000, 0xF9000000);
            backgroundColorAnimator1.setDuration((fadeInDelay) * 1000);
            backgroundColorAnimator1.start();
        }else if (category.equals("del_dark_pin_middle")) {
            updateUI();
            lockview.setVisibility(View.INVISIBLE);
            textview.setVisibility(View.INVISIBLE);
            bgview.setBackgroundColor(Color.parseColor("#F9000000"));
            bgview.setVisibility(View.VISIBLE);
            final ObjectAnimator backgroundColorAnimator1 = ObjectAnimator.ofObject(bgview, "backgroundColor", new ArgbEvaluator(), 0x30000000, 0x50000000,
                    0x40000000, 0x50000000, 0x60000000, 0x80000000, 0x89000000, 0x99000000,
                    0xA9000000, 0xB9000000,0xC9000000, 0xD9000000,0xE9000000, 0xF9000000);
            backgroundColorAnimator1.setDuration((fadeInDelay)*1000);
            Log.e("fade", Integer.toString(lockDelay));
            backgroundColorAnimator1.start();
            new Timer().schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    scrollable=false;
                                    textview.setVisibility(View.VISIBLE);
                                    lockview.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    },
                    (lockDelay)*1000
            );
        }
    }

    public void btnClickHandler(View v) {
        switch (v.getId()) {
            case R.id.numlock_b0:
                editNumpadText("0");
                break;
            case R.id.numlock_b1:
                editNumpadText("1");
                break;
            case R.id.numlock_b2:
                editNumpadText("2");
                break;
            case R.id.numlock_b3:
                editNumpadText("3");
                break;
            case R.id.numlock_b4:
                editNumpadText("4");
                break;
            case R.id.numlock_b5:
                editNumpadText("5");
                break;
            case R.id.numlock_b6:
                editNumpadText("6");
                break;
            case R.id.numlock_b7:
                editNumpadText("7");
                break;
            case R.id.numlock_b8:
                editNumpadText("8");
                break;
            case R.id.numlock_b9:
                editNumpadText("9");
                break;
            case R.id.numlock_bAccept:
                checkPin();
                break;
            case R.id.numlock_bDelete:
                vibe.vibrate(28);
                userinput = (EditText) bgview.findViewById(R.id.userInput);
                int slength = userinput.length();
                if (slength > 0) {
                    //get the last character of the input
                    String selection = userinput.getText().toString().substring(slength - 1, slength);
                    String result = userinput.getText().toString().replace(selection, "");
                    userinput.setText(result);
                    userinput.setSelection(userinput.getText().length());
                }
        }
    }

    public void editNumpadText(String n) {
        vibe.vibrate(28);
        userinput = (EditText) bgview.findViewById(R.id.userInput);
        int slength = userinput.length();
        if (slength < 4) {
            userinput.append(n);
        }
    }

    public void checkPin(){
        vibe.vibrate(28);
        EditText pin_input= (EditText) bgview.findViewById(R.id.userInput);
        String pin= pin_input.getText().toString();
        System.out.println("String is " + pin);
        if(pin.equals("1234")){
            bgview.setClickable(false);
            bgview.setVisibility(View.GONE);
            pin_input.setText("");
            finish();
        } else {
            Context context = bgview.getContext();
            CharSequence text = "Incorrect PIN!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            pin_input.setText("");
        }
    }

    @Override
    public void onBackPressed() {
    }
}
