package fr.insee.sirene.hackathon;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.vocabulary.DCTerms;

public class CLIModule extends ProcessModule {

	public String commandLine = null;

	public CLIModule(String inData, String outData, String inParam, String outParam) {
		super(inData, outData, inParam, outParam);
	}

	private Language language;

	public CLIModule(Language language) {
		this.language = language;
	}

	public void execute() throws Exception {

		super.execute();

//		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
//
//		ProcessBuilder builder = new ProcessBuilder();
//		if (isWindows) {
//		    builder.command("cmd.exe", " ", commandLine);
//		} else {
//		    builder.command("sh", commandLine);
//		}
//		builder.directory(new File(SOURCE_ROOT_FOLDER + "/R/" + this.path));
//		builder.redirectErrorStream(true);
//		Process process = builder.start();
//		// D'après http://www.baeldung.com/run-shell-command-in-java
//		StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
//		Executors.newSingleThreadExecutor().submit(streamGobbler);
//		logger.debug("Lancement de la ligne de commande " + commandLine + " dans le répertoire de travail " + builder.directory().getAbsolutePath());
//		int exitCode = process.waitFor();
//		assert exitCode == 0;

		String workingDirectory = SOURCE_ROOT_FOLDER + "/" + language.getPathElement() + "/" + this.path;
		logger.debug("Lancement de la ligne de commande " + commandLine + " dans le répertoire de travail " + workingDirectory);

		DefaultExecutor executor = new DefaultExecutor();
		executor.setWorkingDirectory(new File(workingDirectory));
		executor.execute(CommandLine.parse(commandLine));
	}

	public String getCommandLine() {
		return commandLine;
	}

	public void setCommandLine(String commandLine) {
		this.commandLine = commandLine;
	}

	private static class StreamGobbler implements Runnable {
	    private InputStream inputStream;
	    private Consumer<String> consumer;
	 
	    public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
	        this.inputStream = inputStream;
	        this.consumer = consumer;
	    }
	 
	    @Override
	    public void run() {
	        new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
	    }
	}

	public enum Language {
		R,
		PYTHON;

		@Override
		public String toString() {
			switch(this) {
				case R: return "R";
				case PYTHON: return "PYTHON";
				default: return "unknown";
			}
		}

		/** Returns the path element associated to the language */
		public String getPathElement() {
			switch(this) {
			case R: return "R";
			case PYTHON: return "Python";
			default: return null;
			}
		}
	}

}
