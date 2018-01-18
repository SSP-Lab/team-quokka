package fr.insee.sirene.hackathon;

import java.io.File;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

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

		super.execute(); // Rapatrie les fichiers in-data et in-param vers leurs emplacements par défault

		if (!active) return;

		String workingDirectory = SOURCE_ROOT_FOLDER + "/" + language.getPathElement() + "/" + this.path;
		logger.debug("Lancement de la ligne de commande " + commandLine + " dans le répertoire de travail " + workingDirectory);

		DefaultExecutor executor = new DefaultExecutor();
		executor.setWorkingDirectory(new File(workingDirectory));
		executor.execute(CommandLine.parse(commandLine)); // Peut lever une org.apache.commons.exec.ExecuteException

		// Tout s'est bien passé, on renseigne à leur valeur par défaut le nom des fichiers de sortie
		setOutputsToDefault();
	}

	public String getCommandLine() {
		return commandLine;
	}

	public void setCommandLine(String commandLine) {
		this.commandLine = commandLine;
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
