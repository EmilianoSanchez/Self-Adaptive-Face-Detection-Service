package com.emiliano.safdService;

import com.emiliano.fmframework.optimization.objectiveFunctions.AdditionObjective;
import com.emiliano.fmframework.optimization.objectiveFunctions.LinearWeightedObjective;
import com.emiliano.fmframework.optimization.objectiveFunctions.MaximumObjective;
import com.emiliano.fmframework.optimization.objectiveFunctions.MinimumObjective;
import com.emiliano.fmframework.optimization.objectiveFunctions.ObjectiveFunction;

public class QualityPreference {

	public enum QualityProperty {
		RTIME,ACC_POS,ERROR_LAND,ACC_SMILE,ACC_GENDER,ERROR_AGE
	}
	
	public QualityPreference() {
		this(0.5, 0.5);
	}

	public QualityPreference(double weightRtime, double weightAccPos) {
		this(weightRtime, weightAccPos, 0.0, 0.0, 0.0, 0.0);
	}

	public QualityPreference(double weightRtime, double weightAccPos, double weightErrorLand, double weightAccSmile,
			double weightAccGender, double weightErrorAge) {
		this.weightRtime = weightRtime;
		this.weightAccPos = weightAccPos;
		this.weightErrorLand = weightErrorLand;
		this.weightAccSmile = weightAccSmile;
		this.weightAccGender = weightAccGender;
		this.weightErrorAge = weightErrorAge;
	}

	public double weightRtime;
	public double weightAccPos;
	public double weightErrorLand;
	public double weightAccSmile;
	public double weightAccGender;
	public double weightErrorAge;

	public ObjectiveFunction getObjectiveFunction() {
		LinearWeightedObjective objectiveFunction = new LinearWeightedObjective();
		objectiveFunction.addTerm(new AdditionObjective(QualityProperty.RTIME.name()), weightRtime);
		objectiveFunction.addTerm(new MaximumObjective(QualityProperty.ACC_POS.name()), weightAccPos);
		objectiveFunction.addTerm(new MinimumObjective(QualityProperty.ERROR_LAND.name()), weightErrorLand);
		objectiveFunction.addTerm(new MaximumObjective(QualityProperty.ACC_SMILE.name()), weightAccSmile);
		objectiveFunction.addTerm(new MaximumObjective(QualityProperty.ACC_GENDER.name()), weightAccGender);
		objectiveFunction.addTerm(new MinimumObjective(QualityProperty.ERROR_AGE.name()), weightErrorAge);
		return objectiveFunction;
	}
}
