package fr.insee.sirene.hackathon;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProcessStep extends ProcessComponent {

	public static Logger logger = LogManager.getLogger(ProcessComponent.class);

	private List<ProcessModule> modules = null;

	public ProcessStep(String inData, String outData, String inParam, String outParam) {
		super(inData, outData, inParam, outParam);
	}

	public ProcessStep() {}

	@Override
	public void execute() throws Exception {
		if (active) for (ProcessModule module : modules) {
			module.execute();
		}
	}

	public ProcessStep getPredecessor() {
		if (this.parent == null) return null;
		MainProcess parentProcess = (MainProcess)this.parent;
		List<ProcessStep> siblings = parentProcess.getSteps();
		int myIndex = siblings.indexOf(this);
		if (myIndex > 0) return siblings.get(myIndex - 1);
		return null;
	}

	public List<ProcessModule> getModules() {
		return modules;
	}

	public void setModules(List<ProcessModule> modules) {
		this.modules = modules;
		for (ProcessModule module : modules) module.setParent(this);
	}

	public void addModule(ProcessModule module) {
		if (this.modules == null) this.modules = new ArrayList<ProcessModule>();
		logger.debug("Ajout du module " + module.getName() + " à l'étape " + this.name);
		this.modules.add(module);
		module.setParent(this);
	}
}
