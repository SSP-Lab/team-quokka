package fr.insee.sirene.hackathon;

import java.util.List;

public class ProcessStep extends ProcessComponent {

	private List<ProcessModule> modules = null;

	public ProcessStep(String inData, String outData, String inParam, String outParam) {
		super(inData, outData, inParam, outParam);
	}

	public ProcessStep() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws Exception {
		for (ProcessModule module : modules) {
			module.execute();
		}
	}
}
