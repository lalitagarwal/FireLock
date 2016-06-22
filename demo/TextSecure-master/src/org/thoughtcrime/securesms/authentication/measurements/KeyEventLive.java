package org.thoughtcrime.securesms.authentication.measurements;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import org.thoughtcrime.securesms.authentication.SecureActivity;

import java.util.ArrayList;
import java.util.List;

import ca.uwaterloo.crysp.itus.FeatureVector;
import ca.uwaterloo.crysp.itus.Itus;
import ca.uwaterloo.crysp.itus.machinelearning.ClassLabel;
import ca.uwaterloo.crysp.itus.measurements.Dispatcher;
import ca.uwaterloo.crysp.itus.measurements.EventType;
import ca.uwaterloo.crysp.itus.measurements.KeyEvent;
import ca.uwaterloo.crysp.itus.measurements.Measurement;

public class KeyEventLive extends KeyEvent {
	TextView textView = null;
	String lastStr = "";
	char lastChar = '.';
	long lastTimestamp = 0;
	long lastHoldTime = 0;
	
	private final int NUM_FEATURES = KeyEvent.NUM_FEATURES;
	
	/**
	 * Default constructor for Key
	 */
	public KeyEventLive(TextView textView){
		fv = new FeatureVector(NUM_FEATURES);
		super.setFeatureList(featureList);
		this.textView = textView;
		this.textView.addTextChangedListener(textChangedListener);
	}
	
	private TextWatcher textChangedListener = new TextWatcher() {
		long pushTime = 0;
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			pushTime = System.nanoTime();
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			;
		}

		@Override
		public void afterTextChanged(Editable s) {
			long releaseTime = System.nanoTime();
			Itus.getItus().getDispatcher().procEvent(EventType.KEY_INPUT,
					(Object)(s.toString() + ';' + pushTime + ';' + releaseTime));
			/*procEvent((Object)(s.toString() + ';' + pushTime + ';' + releaseTime),
					EventType.KEY_INPUT);*/
		}
		
	};
	
	@Override
	public void staticInitializer(Dispatcher eventDispatcher) {
		eventDispatcher.registerCallback(EventType.KEY_INPUT, this);

	}

	@Override
	public boolean procEvent(Object ev, EventType eventType) {
		String eventObj = new String((String)ev);
		String [] toks = eventObj.split(";");
		String currentInput = toks[0];
		long pushTime = Long.parseLong(toks[1]);
		long releaseTime = Long.parseLong(toks[2]);
		long holdTime = releaseTime - pushTime;
		char currentChar = '.';
		long currentTimestamp = 0;
		int key = -1;
		boolean rv = false;
		
		/*find the key that was pressed by the user*/
		/*if this was a suggestion by android return*/
		if (currentInput.length() == this.lastStr.length())
			return false;
		/*if this is the first char*/
		else if (currentInput.length() == 1) {
			currentChar = currentInput.charAt(currentInput.length() -1 );
    		currentTimestamp = releaseTime;
    		rv = false;
		}
		/*if a character was deleted, backspace was pressed*/
		else if (currentInput.length() == this.lastStr.length() - 1)
    		; /*do nothing, we are only interested in chars 'a-z'*/
    	/*case of a simple append*/
    	else if(currentInput.length() == this.lastStr.length() + 1 && 
    			currentInput.startsWith(this.lastStr)) { 
    		currentChar = currentInput.charAt(currentInput.length() -1 );
    		currentTimestamp = releaseTime;
    		String bigram = Character.toString(lastChar) + 
    				Character.toString(currentChar);
    		
    		key = getBigramKey(bigram); 
    		/*Log.e("","" + key + ";"+ lastHoldTime +";"+ holdTime + ";" + 
    				(currentTimestamp- lastTimestamp));*/
    		if (key != -1) {
    			fv.clear();
    			fv.set(0, key);
    			fv.set(1, lastHoldTime);
    			fv.set(2, holdTime);
    			fv.set(3, currentTimestamp- lastTimestamp);
    			fv.setClassLabel(ClassLabel.UNKNOWN);
    			rv = true;
    		}
    	}
    	else /*user made a change within string by navigating within string*/
    		; /*not interested as interstroke time is skewed*/
    	
		this.lastChar = currentChar;
		this.lastTimestamp = currentTimestamp;
		this.lastHoldTime = holdTime;
		this.lastStr = new String(currentInput);
    	return rv;
	}

	@Override
	public Measurement newInstance() {
		if (SecureActivity.textView == null)
			return null;
		return new KeyEventLive(SecureActivity.textView);
	}

	@Override
	public List<FeatureVector> defaultPositiveInstances() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FeatureVector> defaultNegativeInstances() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getRecommendedClassifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Class<?>> getSupportedClassifier() {
		// TODO Auto-generated method stub
		return null;
	}
}
