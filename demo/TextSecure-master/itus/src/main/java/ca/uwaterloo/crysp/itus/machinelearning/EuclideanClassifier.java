package ca.uwaterloo.crysp.itus.machinelearning;

import java.util.List;

import ca.uwaterloo.crysp.itus.FeatureVector;

public class EuclideanClassifier extends Classifier {
	int numFeatures;
	/**
	 * Uses the triggerFeatureIdx to match and 
	 * operate (i.e., select on FeatureVectors)
	 * set triggerFeatureIdx to -1 to avoid trigger*/
	int triggerFeatureIndex;
	double [] threshold;
	EuclideanModel model = null;
	@SuppressWarnings("serial")
	public class EuclideanModel implements java.io.Serializable {
		int numFeatures;
		int triggerFeatureIndex;
		double [] threshold;
		public List<FeatureVector> data;
		public EuclideanModel( int numFeatures, int triggerFeatureIdx, 
				double [] threshold,List<FeatureVector> data) {
			this.numFeatures = numFeatures;
			this.triggerFeatureIndex = triggerFeatureIdx;
			this.threshold = threshold;
			this.data = data;
		}
		public EuclideanModel(Object _model) {
			this.numFeatures = model.numFeatures;
			this.triggerFeatureIndex = model.triggerFeatureIndex;
			this.threshold = model.threshold;
			this.data = model.data;
		}
	}
	
	public EuclideanClassifier(int numFeatures, int triggerFeatureIdx,
			double [] threshold) {
		this.numFeatures = numFeatures;
		this.triggerFeatureIndex = triggerFeatureIdx;
		this.threshold = threshold;
		setState(ClassifierState.NOT_TRAINED);
	}
	
	public EuclideanClassifier(Object _model) {
		model = new EuclideanModel(_model); 
		this.numFeatures = model.numFeatures;
		this.triggerFeatureIndex = model.triggerFeatureIndex;
		this.threshold = model.threshold;
		setState(ClassifierState.TRAINED);
	}
	@Override
	public boolean train(List<FeatureVector> data) throws IllegalArgumentException {
		model = new EuclideanModel(numFeatures, triggerFeatureIndex, threshold,
				data);
		setState(ClassifierState.TRAINED);
		return true;
	}

	@Override
	public int classify(FeatureVector featureVector) throws IllegalStateException {
		double [] dist = new double[numFeatures];
		int ctr = 0;
		for (FeatureVector fv : model.data) {
			int tIndex = model.triggerFeatureIndex; 
			if (tIndex != -1) 
				if (fv.get(tIndex) != featureVector.get(tIndex))
					continue;
			for (int idx = 0; idx < numFeatures; idx++) {
				if (tIndex == idx)
					continue;
				dist[idx] += Math.abs(fv.get(idx) - featureVector.get(idx));
			}
			ctr++;
		}
		if (ctr > 0)
			for (int i = 0; i < numFeatures; i++)
				dist[i] /= ctr;
		
		//System.out.println(""+dist[1]+ " "+dist[2]+" "+dist[3]);
		for (int idx = 0; idx < numFeatures; idx++) {
			if (idx == model.triggerFeatureIndex)
				continue;
			if (dist[idx] > threshold[idx])
				return -1;
		}
		return 1;
	}

	@Override
	public Object getModel() {
		return this.model;
	}
	public void setThreshold(double [] threshold) {
		this.threshold = threshold;
	}
	public double [] getThreshold() {
		return this.threshold;
	}
}
