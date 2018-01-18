package fr.insee.sirene.hackathon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.insee.sirene.hackathon.CLIModule.Language;

public class ProcessExecution {

	public static Logger logger = LogManager.getLogger(ProcessExecution.class);

	public static void main(String[] args) {

		MainProcess process = new MainProcess();
		process.setName("Processus principal");
		process.setPath("process");

		ProcessStep lecture = new ProcessStep();
		lecture.setName("Lecture");
		lecture.setPath("lecture");
		ProcessStep enrichissement = new ProcessStep();
		ProcessStep requetage = new ProcessStep();
		ProcessStep scoring = new ProcessStep();
		ProcessStep evaluation = new ProcessStep();
		evaluation.setName("Évaluation");
		evaluation.setPath("evaluation");


		CLIModule lectureRecensement = new CLIModule(Language.R);
		lectureRecensement.setName("Lecture du fichier RP 2017");
		lectureRecensement.setPath("lecture/lecture-rp");
		lectureRecensement.setInData(ProcessComponent.SOURCE_RP_2017);
		String processPath = ProcessComponent.PROCESS_ROOT_FOLDER + "/" + lectureRecensement.getPath();
		lectureRecensement.setCommandLine("\"" + ProcessComponent.R_SCRIPT + "\" lecture-rp.R \"" + processPath + "\"");
		lectureRecensement.setActive(false);

		CLIModule soumissionAPI = new CLIModule(Language.PYTHON);
		soumissionAPI.setName("Soumission à l'API d'évaluation");
		soumissionAPI.setPath("evaluation/soumission-api");
		soumissionAPI.setCommandLine("\"" + ProcessComponent.PYTHON_EXE + "\" eval_api.py");

		lecture.addModule(lectureRecensement);
		evaluation.addModule(soumissionAPI);

		process.addStep(lecture);
		process.addStep(evaluation);

		try {
			process.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
