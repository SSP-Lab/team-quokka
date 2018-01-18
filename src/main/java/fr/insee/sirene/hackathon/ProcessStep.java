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

	public List<ProcessModule> getModules() {
		return modules;
	}

	public void setModules(List<ProcessModule> modules) {
		this.modules = modules;
	}

	public void addModule(ProcessModule module) {
		if (this.modules == null) this.modules = new ArrayList<ProcessModule>();
		logger.debug("Ajout du module " + module.getName() + " à l'étape " + this.name);
		this.modules.add(module);
	}
}
