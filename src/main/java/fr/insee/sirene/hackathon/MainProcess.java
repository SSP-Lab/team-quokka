package fr.insee.sirene.hackathon;

import java.util.List;

public class MainProcess extends ProcessComponent {

	private List<ProcessStep> steps = null;

	public MainProcess(String inData, String outData, String inParam, String outParam) {
		super(inData, outData, inParam, outParam);
	}

	@Override
	public void execute() {
		for (ProcessStep step : steps) {
			step.execute();
		}
	}

}
