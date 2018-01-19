package fr.insee.sirene.hackathon;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Configuration {

	// TODO Move in a Configuration class
	public static String ROOT_FOLDER = "D:/Programmes/Java/Hackathon Sirene";
	public static String SOURCE_RP_2017 = "src/main/resources/data/rp_final_2017.csv";
	public static String R_SCRIPT = "C:\\Program Files\\R\\R-3.3.2\\bin\\x64\\Rscript.exe";
	public static String PYTHON_EXE = "C:\\Users\\Franck\\AppData\\Local\\Programs\\Python\\Python36\\python.exe";

	private static final String PROPERTIES_FILE = "src/main/resources/config.properties";

	static {
	  Properties config = new Properties();

	  try (InputStream inputStream = Files.newInputStream(Paths.get(PROPERTIES_FILE))) {
		  config.load(inputStream);
		  ROOT_FOLDER = config.getProperty("root.folder", ROOT_FOLDER);
		  R_SCRIPT = config.getProperty("r.script", R_SCRIPT);
		  PYTHON_EXE = config.getProperty("python.exe", PYTHON_EXE);
	  } catch (IOException ex) {
		  ex.printStackTrace();
	  }
	}
	public static String PROCESS_ROOT_FOLDER = ROOT_FOLDER + "/process"; // TODO Ugly
	public static String SOURCE_ROOT_FOLDER =  ROOT_FOLDER + "/src/main"; // TODO Ugly
}
