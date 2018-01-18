package fr.insee.sirene.hackathon;

import java.io.File;

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
		Process process = builder.start();
		// Si besoin pour connecter les entrées-sorties : http://www.baeldung.com/run-shell-command-in-java
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
}
