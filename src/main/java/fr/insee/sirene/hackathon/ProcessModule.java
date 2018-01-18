package fr.insee.sirene.hackathon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProcessModule extends ProcessComponent {

	public static Logger logger = LogManager.getLogger(ProcessComponent.class);

	public ProcessModule() {}

	public ProcessModule(String inData, String outData, String inParam, String outParam) {
		super(inData, outData, inParam, outParam);
	}

}
