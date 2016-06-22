package layout.authentication;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Timer;
import java.util.TimerTask;

import ca.uwaterloo.crysp.R;
import ca.uwaterloo.crysp.iaeval.Configuration;
import ca.uwaterloo.crysp.storage.Storage;
import haibison.android.lockpattern.LockPatternActivity;

public class PatternLock extends LockPatternActivity {

	private String category;
	private View bgview, lockview;
	boolean scrollable=true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        Storage.Log("Lock", "Pattern Activity Called");
		super.onCreate(savedInstanceState);
		super.initCall();
		Bundle extras= getIntent().getExtras();
        category= extras.getString("category");
        bgview=findViewById(R.id.bgview);
        lockview=findViewById(R.id.lockview);
        section_called(category);
	}
	
	 @Override
	 public boolean dispatchTouchEvent(MotionEvent ev) {
         if(scrollable){
             if(Configuration.currentTask=="EMAIL")
                 Configuration.currentEmail.dispatchTouchEvent(ev);
             else if(Configuration.currentTask=="TE")
                 Configuration.currentTextEntry.dispatchTouchEvent(ev);
         }
		 return super.dispatchTouchEvent(ev);
	 }

    @Override
    public boolean dispatchKeyEvent(KeyEvent ke) {
        Log.e("PinLock", "DispatchKey");
        if(scrollable){
            if(Configuration.currentTask=="EMAIL")
                Configuration.currentEmail.dispatchKeyEvent(ke);
            else if(Configuration.currentTask=="TE"){
                Log.e("Scrollable", "DispatchKey");
                Configuration.currentTextEntry.dispatchKeyEvent(ke);
            }
        }else{
            hide_keyboard();
            //InputMethodManager mgr= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            //mgr.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), 0);
        }
        return super.dispatchKeyEvent(ke);
    }

    public void updateUI(){
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hide_keyboard();

                            }
                        });
                    }
                },
                100
        );
    }
    public void hide_keyboard(){
        //Log.e("Current Task", Configuration.currentTask);
        //Log.e("Scrollable Value", String.valueOf(scrollable));
        if(Configuration.currentTask=="EMAIL"){
            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), 0);
        }else{
            if(!scrollable){
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                //mgr.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void section_called(String category){
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
                    0x80000000,
                    0xA9000000, 0xC9000000, 0xE9000000, 0xF9000000);
            backgroundColorAnimator1.setDuration(8000);
            backgroundColorAnimator1.start();
        }else if (category.equals("del_dark_pin_end")) {
        	 lockview.setVisibility(View.INVISIBLE);
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
                                 	lockview.setVisibility(View.VISIBLE);
                                 }
                             });
                         }
                     },
                     8000
             );
         }else if (category.equals("del_dark_pin_middle")) {
            updateUI();
        	 lockview.setVisibility(View.INVISIBLE);
        	 bgview.setBackgroundColor(Color.parseColor("#F9000000"));
        	 bgview.setVisibility(View.VISIBLE);           
             final ObjectAnimator backgroundColorAnimator1 = ObjectAnimator.ofObject(bgview, "backgroundColor", new ArgbEvaluator(),
                     0x80000000,
                     0xA9000000, 0xC9000000, 0xE9000000, 0xF9000000);
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
                                     hide_keyboard();
                                 	lockview.setVisibility(View.VISIBLE);
                                 }
                             });
                         }
                     },
                     4000
             );
         }
    }
}
