package fr.insee.sirene.hackathon;

import java.io.File;
import java.io.IOException;

public class CLIModule extends ProcessModule {

	public String commandLine = null;

	public CLIModule(String inData, String outData, String inParam, String outParam) {
		super(inData, outData, inParam, outParam);
	}

	public void execute() throws Exception {

		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

		ProcessBuilder builder = new ProcessBuilder();
		if (isWindows) {
		    builder.command("cmd.exe", commandLine);
		} else {
		    builder.command("sh", commandLine);
		}
		builder.directory(new File(System.getProperty("user.home")));
		Process process = builder.start();
		// Si besoin pour connecter les entrées-sorties : http://www.baeldung.com/run-shell-command-in-java
		int exitCode = process.waitFor();
		assert exitCode == 0;
	}
}
