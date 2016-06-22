package ca.uwaterloo.crysp.itus.prefabs;

import ca.uwaterloo.crysp.itus.Itus;
import ca.uwaterloo.crysp.itus.Parameters;
import ca.uwaterloo.crysp.itus.machinelearning.Classifier;
import ca.uwaterloo.crysp.itus.machinelearning.EuclideanClassifier;
import ca.uwaterloo.crysp.itus.measurements.KeyEvent;

public class KeystrokeClassifier extends Itus{
	/**
	 * Number of features employed
	 */
	static final int numFeatures = KeyEvent.NUM_FEATURES;
	/**
	 * Default run configuration 
	 */
	private static RunConfiguration recommendedConfiguration;
	
	/**
	 * Default constructor for Keystroke. 
	 * @param {@code keyEvent} is an object of a subclass 
	 * of {@code KeyEvent} 
	 */
	public KeystrokeClassifier(KeyEvent keyEvent) {
		super();
		keyEvent.setFeatureList(KeyEvent.featureList);
		Object model = Classifier.retreiveModelFromStorage();
		Classifier classifier = null;
		double [] threshold = {800000, 800000, 1.5};
		if (model == null)
			classifier = new EuclideanClassifier(numFeatures, 0, threshold);
		else
			classifier = new EuclideanClassifier(model);
		useMeasurement(keyEvent);
		useClassifier(classifier);
		Parameters.setOnlineMode();
	}
	public static RunConfiguration getRecommendedConfiguration(
			KeyEvent keyEvent) {
		//XXX: ToDo
		return null;
	}
}
