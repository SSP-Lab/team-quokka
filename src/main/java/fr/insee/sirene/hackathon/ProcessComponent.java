package fr.insee.sirene.hackathon;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ProcessComponent {

	public static String DEFAULT_IN_DATA = "in-data.csv";
	public static String DEFAULT_IN_PARAM = "in-param.csv";
	public static String DEFAULT_OUT_DATA = "out-data.csv";
	public static String DEFAULT_OUT_PARAM = "out-param.csv";

	public static Logger logger = LogManager.getLogger(ProcessComponent.class);

	String name = null;
	String path = null;

	String inData = null;
	String outData = null;
	String inParam = null;
	String outParam = null;

	boolean active = true;

	ProcessComponent parent = null;

	public ProcessComponent() {}

	public ProcessComponent(String inData, String outData, String inParam, String outParam) {
		super();
		this.inData = inData;
		this.outData = outData;
		this.inParam = inParam;
		this.outParam = outParam;
	}

	/**
	 * Opérations préalables à l'exécution effective qui est réalisée dans les classes filles : rapatriement des fichiers en entrée si nécessaire.
	 * 
	 * @throws Exception
	 */
	public void execute() throws Exception {
		if (!this.active) {
			logger.info("Ce composant est inactivé, exécution interrompue");
			return;
		}
		String originFile = this.inData;
		if (originFile == null) { // Le fichier d'entrée n'est pas précisé : on prend par défaut le fichier de sortie du module précédent
			ProcessComponent predecessor = this.getPredecessor();
			if (predecessor != null) originFile = predecessor.getOutData();
		}
		if (originFile != null) {
			// Move file to local directory under standard name
			String destinationDataFile = Configuration.PROCESS_ROOT_FOLDER + "/" + path + "/" + DEFAULT_IN_DATA;
			logger.info("Copie du fichier " + originFile + " vers " + destinationDataFile);
			FileUtils.copyFile(new File(originFile), new File(destinationDataFile));
			// On laisse inData à null : la prochaine exécution refera une copie.
		} else {
			// Rien à faire, on espère que le fichier d'entrée est bien là où on l'attend.
		}
		// Même jeu sur le fichier de paramètres
		originFile = this.inParam;
		/*if (originFile == null) {
			ProcessComponent predecessor = this.getPredecessor();
			if (predecessor != null) originFile = predecessor.getOutParam();
		}
		if (originFile != null) {
			// Move file to local directory under standard name
			String destinationParamFile = Configuration.PROCESS_ROOT_FOLDER + "/" + path + "/" + DEFAULT_IN_PARAM;
			logger.info("Copie du fichier " + originFile + " vers " + destinationParamFile);
			FileUtils.copyFile(new File(originFile), new File(destinationParamFile));
		}*/
	}

	/**
	 * Met à leurs valeurs par défaut les noms des fichiers de sortie.
	 */
	public void setOutputsToDefault() {
		this.outData = Configuration.PROCESS_ROOT_FOLDER + "/" + path + "/" + DEFAULT_OUT_DATA;
		this.outParam = Configuration.PROCESS_ROOT_FOLDER + "/" + path + "/" + DEFAULT_OUT_PARAM;
	}

	public ProcessComponent getPredecessor() {
		return null;
	}
	
	public String getInData() {
		return inData;
	}

	public void setInData(String inData) {
		this.inData = inData;
	}

	public String getOutData() {
		return outData;
	}

	public void setOutData(String outData) {
		this.outData = outData;
	}

	public String getInParam() {
		return inParam;
	}

	public void setInParam(String inParam) {
		this.inParam = inParam;
	}

	public String getOutParam() {
		return outParam;
	}

	public void setOutParam(String outParam) {
		this.outParam = outParam;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public ProcessComponent getParent() {
		return parent;
	}

	public void setParent(ProcessComponent parent) {
		this.parent = parent;
	}
}
