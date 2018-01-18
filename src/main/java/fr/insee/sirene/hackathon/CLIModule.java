package fr.insee.sirene.hackathon;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class CLIModule extends ProcessModule {

	public String commandLine = null;

	public CLIModule(String inData, String outData, String inParam, String outParam) {
		super(inData, outData, inParam, outParam);
	}

	public CLIModule() {}

	public void execute() throws Exception {

		super.execute();

		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

		ProcessBuilder builder = new ProcessBuilder();
		if (isWindows) {
		    builder.command("cmd.exe", " ", commandLine);
		} else {
		    builder.command("sh", commandLine);
		}
		builder.directory(new File(SOURCE_ROOT_FOLDER + "/R/" + this.path));
		builder.redirectErrorStream(true);
		Process process = builder.start();
		// D'après http://www.baeldung.com/run-shell-command-in-java
		StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
		Executors.newSingleThreadExecutor().submit(streamGobbler);
		logger.debug("Lancement de la ligne de commande " + commandLine + " dans le répertoire de travail " + builder.directory().getAbsolutePath());
		int exitCode = process.waitFor();
		assert exitCode == 0;
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
}
