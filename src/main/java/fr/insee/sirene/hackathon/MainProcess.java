package fr.insee.sirene.hackathon;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainProcess extends ProcessComponent {

	public static Logger logger = LogManager.getLogger(MainProcess.class);

	private List<ProcessStep> steps = null;

	public MainProcess(String inData, String outData, String inParam, String outParam) {
		super(inData, outData, inParam, outParam);
	}

	public MainProcess() {
		super();
	}

	@Override
	public void execute() throws Exception {
		if (active) for (ProcessStep step : steps) {
			step.execute();
		}
	}

	public List<ProcessStep> getSteps() {
		return steps;
	}

	public void setSteps(List<ProcessStep> steps) {
		this.steps = steps;
	}

	public void addStep(ProcessStep step) {
		if (this.steps == null) this.steps = new ArrayList<ProcessStep>();
		logger.debug("Ajout de l'étape " + step.getName() + " au processus principal");
		this.steps.add(step);
	}
}
