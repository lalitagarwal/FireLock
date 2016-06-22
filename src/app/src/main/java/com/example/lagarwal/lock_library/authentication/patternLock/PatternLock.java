package com.example.lagarwal.lock_library.authentication.patternLock;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.lagarwal.lock_library.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import haibison.android.lockpattern.LockPatternActivity;
import haibison.android.lockpattern.util.AlpSettings;
import haibison.android.lockpattern.widget.LockPatternUtils;
import haibison.android.lockpattern.widget.LockPatternView;

public class PatternLock extends LockPatternActivity {

	private String category;
	private View bgview, lockview;
	boolean scrollable=true;
    private int fadeInDelay, lockDelay;

    List<LockPatternView.Cell> lock_pat = new ArrayList<LockPatternView.Cell>();
    static char[] savedPattern;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		super.initCall();
		Bundle extras= getIntent().getExtras();
        category= extras.getString("category");
        fadeInDelay= extras.getInt("fadeInDelay", 8);
        lockDelay= extras.getInt("lockDelay", 4);

        bgview=findViewById(R.id.bgview);
        lockview=findViewById(R.id.lockview);
        section_called(category);
        AlpSettings.Display.setMaxRetries(this, 100);
	}

    public void setPat(){
        lock_pat.add(LockPatternView.Cell.of(3));
        lock_pat.add(LockPatternView.Cell.of(0));
        lock_pat.add(LockPatternView.Cell.of(1));
        lock_pat.add(LockPatternView.Cell.of(2));
        lock_pat.add(LockPatternView.Cell.of(5));
        savedPattern = LockPatternUtils.patternToSha1(lock_pat).toCharArray();
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
                    0x40000000, 0x50000000, 0x60000000, 0x80000000, 0x89000000, 0x99000000,
                    0xA9000000, 0xB9000000,0xC9000000, 0xD9000000,0xE9000000, 0xF9000000);
            backgroundColorAnimator1.setDuration((fadeInDelay) * 1000);
            backgroundColorAnimator1.start();
        }else if (category.equals("del_dark_pin_middle")) {
            updateUI();
        	 lockview.setVisibility(View.INVISIBLE);
        	 bgview.setBackgroundColor(Color.parseColor("#F9000000"));
        	 bgview.setVisibility(View.VISIBLE);           
             final ObjectAnimator backgroundColorAnimator1 = ObjectAnimator.ofObject(bgview, "backgroundColor", new ArgbEvaluator(),
                     0x40000000, 0x50000000, 0x60000000, 0x80000000, 0x89000000, 0x99000000,
                     0xA9000000, 0xB9000000,0xC9000000, 0xD9000000,0xE9000000, 0xF9000000);
             backgroundColorAnimator1.setDuration((fadeInDelay) * 1000);
             backgroundColorAnimator1.start();
             new Timer().schedule(
                     new TimerTask() {
                         @Override
                         public void run() {
                        	 runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() { 
                                	scrollable=false;
                                     //hide_keyboard();
                                 	lockview.setVisibility(View.VISIBLE);
                                 }
                             });
                         }
                     },
                     (lockDelay) * 1000
             );
         }
    }
}
