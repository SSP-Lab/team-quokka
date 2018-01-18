package fr.insee.sirene.hackathon;

import java.util.List;

public class ProcessStep extends ProcessComponent {

	private List<ProcessModule> modules = null;

	public ProcessStep(String inData, String outData, String inParam, String outParam) {
		super(inData, outData, inParam, outParam);
	}

	@Override
	public void execute() {
		for (ProcessModule module : modules) {
			module.execute();
		}
	}
}
