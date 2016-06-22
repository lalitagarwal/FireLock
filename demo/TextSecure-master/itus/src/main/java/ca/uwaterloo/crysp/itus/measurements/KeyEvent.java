package ca.uwaterloo.crysp.itus.measurements;

import java.util.ArrayList;
import java.util.List;

import ca.uwaterloo.crysp.itus.FeatureVector;

public class KeyEvent extends Measurement {
	/**
	 * List of all the features supported
	 */
	public static final String featureList [] = {
			"BigramKey",
			"Key1HoldTime",
			"Key2HoldTime",
			"InterstrokeTime",
	};
	
	/**
	 * Number of features currently supported by this measurement
	 */
	public static final int NUM_FEATURES = featureList.length;
	
	
	/**operates on most frequent bigrams only
	 * see:
	 * https://en.wikipedia.org/wiki/Bigram#Bigram_frequency_in_the_English_language
	 * */
	private static String[] topBigrams = {
			"th", "he", "in", "er", "an", "re", "nd", 
			"at", "on", "nt", "ha", "es", "st", "en",
			"ed", "to", "it", "ou", "ea", "hi", "is",
			"or", "ti", "as", "te", "et", "ng", "of", 
			"al", "de", "se", "le", "sa", "si", "ar",
			"ve", "ra", "ld", "ur",};

	public String[] getSupportedBigrams() {
		return topBigrams;
	}

	public static boolean isBigramSupported(String bigram) {
		for (String bi : topBigrams)
			if (bi.equals(bigram))
				return true;
		return false;
	}
	public static int getBigramKey(String bigram) {
		for (int i = 0; i < topBigrams.length; i++) 
			if (topBigrams[i].equals(bigram))
				return i;
		return -1;
	}
	@Override
	public void staticInitializer(Dispatcher eventDispatcher) {
		eventDispatcher.registerCallback(EventType.KEY_INPUT, this);

	}


	@Override
	public Measurement newInstance() {
		return new KeyEvent();
	}

	@Override
	public boolean procEvent(Object ev, EventType eventType) {
		// TODO Auto-generated method stub
		return false;
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
		return ca.uwaterloo.crysp.itus.machinelearning.EuclideanClassifier.class;
	}

	@Override
	public ArrayList<Class<?>> getSupportedClassifier() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
