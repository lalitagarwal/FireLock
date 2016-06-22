package org.thoughtcrime.securesms.jobs;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.thoughtcrime.securesms.R;

import java.util.Timer;
import java.util.TimerTask;

public class PinLock extends Activity {
    private View bgview, lockview, textview;
    private GridLayout view_numpad;
    private LinearLayout view_edittext;
    Button btn[] = new Button[14];
    ImageButton imgbtn[]= new ImageButton[2];
    EditText userinput;
    Vibrator vibe;
    boolean scrollable=true;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras= getIntent().getExtras();
        category= extras.getString("category");
        setContentView(R.layout.activity_pin_lock);
        bgview= findViewById(R.id.pin_lock);
        lockview=findViewById(R.id.numpad_layout);
        textview=findViewById(R.id.edittext_layout);
        bgview.setBackgroundColor(Color.parseColor("#50000000"));
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        userinput = (EditText) bgview.findViewById(R.id.userInput);
        userinput.setInputType(InputType.TYPE_NULL);
        section_called(category);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pin_lock, menu);
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

    /*@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(scrollable)
            Webview.webActivity.dispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }*/

    public void section_called(String category){
        if(category.equals("imm_trans")){
            scrollable=false;
            bgview.setBackgroundColor(Color.parseColor("#50000000"));
        } else if(category.equals("imm_dark")){
            scrollable=false;
            bgview.setBackgroundColor(Color.parseColor("#F9000000"));
        }else if (category.equals("del_dark_pin_start")) {
            scrollable=false;
            final ObjectAnimator backgroundColorAnimator1 = ObjectAnimator.ofObject(bgview, "backgroundColor", new ArgbEvaluator(), 0x30000000, 0x50000000,
                    0x60000000, 0x80000000, 0x89000000, 0x99000000,
                    0xA9000000, 0xB9000000,0xC9000000, 0xD9000000,0xE9000000, 0xF9000000);
            backgroundColorAnimator1.setDuration(12000);
            backgroundColorAnimator1.start();
        }else if (category.equals("del_dark_pin_end")) {
            lockview.setVisibility(View.INVISIBLE);
            textview.setVisibility(View.INVISIBLE);
            bgview.setBackgroundColor(Color.parseColor("#F9000000"));
            final ObjectAnimator backgroundColorAnimator1 = ObjectAnimator.ofObject(bgview, "backgroundColor", new ArgbEvaluator(), 0x30000000, 0x50000000,
                    0x60000000, 0x80000000, 0x89000000, 0x99000000,
                    0xA9000000, 0xB9000000,0xC9000000, 0xD9000000,0xE9000000, 0xF9000000);
            backgroundColorAnimator1.setDuration(10000);
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
                    8000
            );
        }else if (category.equals("del_dark_pin_middle")) {
            lockview.setVisibility(View.INVISIBLE);
            textview.setVisibility(View.INVISIBLE);
            bgview.setBackgroundColor(Color.parseColor("#F9000000"));
            bgview.setVisibility(View.VISIBLE);
            final ObjectAnimator backgroundColorAnimator1 = ObjectAnimator.ofObject(bgview, "backgroundColor", new ArgbEvaluator(), 0x30000000, 0x50000000,
                    0x60000000, 0x80000000, 0x89000000, 0x99000000,
                    0xA9000000, 0xB9000000,0xC9000000, 0xD9000000,0xE9000000, 0xF9000000);
            backgroundColorAnimator1.setDuration(12000);
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
                    4000
            );
        }
    }

    public void btnClickHandler(View v) {
        switch (v.getId()) {
            case R.id.numlock_b0:
                addtoarray("0");
                break;
            case R.id.numlock_b1:
                addtoarray("1");
                break;
            case R.id.numlock_b2:
                addtoarray("2");
                break;
            case R.id.numlock_b3:
                addtoarray("3");
                break;
            case R.id.numlock_b4:
                addtoarray("4");
                break;
            case R.id.numlock_b5:
                addtoarray("5");
                break;
            case R.id.numlock_b6:
                addtoarray("6");
                break;
            case R.id.numlock_b7:
                addtoarray("7");
                break;
            case R.id.numlock_b8:
                addtoarray("8");
                break;
            case R.id.numlock_b9:
                addtoarray("9");
                break;
            case R.id.numlock_bAccept:
                check_pin();
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

    public void addtoarray(String n) {
        vibe.vibrate(28);
        userinput = (EditText) bgview.findViewById(R.id.userInput);
        int slength = userinput.length();
        if (slength < 4) {
            userinput.append(n);
        }
    }

    public void check_pin(){
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
