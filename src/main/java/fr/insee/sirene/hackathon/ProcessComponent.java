package fr.insee.sirene.hackathon;

import java.io.File;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ProcessComponent {

	public static String ROOT_FOLDER = "D:/Programmes/Java/Hackathon Sirene"; // TODO Ugly
	public static String rScript = "C:\\Program Files\\R\\R-3.3.2\\bin\\x64\\Rscript.exe";
	public static String SOURCE_RP_2017 = "src/main/resources/data/rp_final_2017.csv";

	public static Logger logger = LogManager.getLogger(ProcessComponent.class);

	String name = null;
	String path = null;

	String inData = null;
	String outData = null;
	String inParam = null;
	String outParam = null;

	public ProcessComponent() {}

	public ProcessComponent(String inData, String outData, String inParam, String outParam) {
		super();
		this.inData = inData;
		this.outData = outData;
		this.inParam = inParam;
		this.outParam = outParam;
	}

	public void execute() throws Exception {
		if (this.inData != null) {
			// Move file to local directory under standard name
			String destinationFolder = ROOT_FOLDER + "/" + path + "in-data.csv";
			logger.info("Copie du fichier " + inData + " vers " + destinationFolder);
			FileUtils.copyFile(new File(inData), new File(destinationFolder));
		}
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
}
