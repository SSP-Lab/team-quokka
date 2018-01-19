package fr.insee.sirene.hackathon;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProcessModule extends ProcessComponent {

	public static Logger logger = LogManager.getLogger(ProcessComponent.class);

	public ProcessModule() {}

	public ProcessModule(String inData, String outData, String inParam, String outParam) {
		super(inData, outData, inParam, outParam);
	}

	@Override
	public ProcessModule getPredecessor() {
		if (this.parent == null) return null;
		ProcessStep parentStep = (ProcessStep)this.parent;
		List<ProcessModule> siblings = parentStep.getModules();
		int myIndex = siblings.indexOf(this);
		if (myIndex > 0) return siblings.get(myIndex -1);
		// Cas du premier module d'une étape : chercher le dernier module de l'étape précédente
		ProcessStep parentPredecessor = parentStep.getPredecessor();
		if (parentPredecessor == null) return null;
		return parentPredecessor.getModules().get(parentPredecessor.getModules().size() - 1);
	}
}
