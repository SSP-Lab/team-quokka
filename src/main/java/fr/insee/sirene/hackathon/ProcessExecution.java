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


		/********************* Module de lecture du fichier RP **************************/
		CLIModule lectureRecensement = new CLIModule(Language.R);
		lectureRecensement.setName("Lecture du fichier RP 2017");
		lectureRecensement.setPath("lecture/lecture-rp");
		lectureRecensement.setInData(Configuration.SOURCE_RP_2017);
		String processPath = Configuration.PROCESS_ROOT_FOLDER + "/" + lectureRecensement.getPath();
		lectureRecensement.setCommandLine("\"" + Configuration.R_SCRIPT + "\" lecture-rp.R \"" + processPath + "\"");
		lectureRecensement.setActive(false);

		/** Module d'enrichissement par indicatrice d'égalité des communes de domicile et de travail **/
		CLIModule egaliteCommunesDomicileTravail = new CLIModule(Language.R);
		egaliteCommunesDomicileTravail.setName("Enrichissement par indicatrice d'égalité des communes de domicile et de travail");
		egaliteCommunesDomicileTravail.setPath("egalite-communes-domicile-travail");
		processPath = Configuration.PROCESS_ROOT_FOLDER + "/" + egaliteCommunesDomicileTravail.getPath();
		egaliteCommunesDomicileTravail.setCommandLine("\"" + Configuration.R_SCRIPT + "\" Enrichissement code commune.R \"" + processPath + "\"");

		CLIModule soumissionAPI = new CLIModule(Language.PYTHON);
		soumissionAPI.setName("Soumission à l'API d'évaluation");
		soumissionAPI.setPath("evaluation/soumission-api");
		soumissionAPI.setCommandLine("\"" + Configuration.PYTHON_EXE + "\" eval_api.py");

		lecture.addModule(lectureRecensement);
		enrichissement.addModule(egaliteCommunesDomicileTravail);
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
