package fr.insee.sirene.hackathon;

import java.util.List;

public class MainProcess extends ProcessComponent {

	private List<ProcessStep> steps = null;

	public MainProcess(String inData, String outData, String inParam, String outParam) {
		super(inData, outData, inParam, outParam);
	}

	public MainProcess() {
		super();
	}

	@Override
	public void execute() throws Exception {
		for (ProcessStep step : steps) {
			step.execute();
		}
	}

	public List<ProcessStep> getSteps() {
		return steps;
	}

	public void addStep(ProcessStep step) {
		this.steps.add(step);
	}

	public void setSteps(List<ProcessStep> steps) {
		this.steps = steps;
	}


}
