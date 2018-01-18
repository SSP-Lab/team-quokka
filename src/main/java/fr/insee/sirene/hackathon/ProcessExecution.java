package fr.insee.sirene.hackathon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProcessExecution {

	public static Logger logger = LogManager.getLogger(ProcessExecution.class);

	public static void main(String[] args) {

		MainProcess process = new MainProcess();
		process.setName("Processus principal");
		process.setPath("process/");

		ProcessStep lecture = new ProcessStep();
		lecture.setName("Lecture");
		lecture.setPath("lecture/");
		ProcessStep enrichissement = new ProcessStep();
		ProcessStep requetage = new ProcessStep();
		ProcessStep scoring = new ProcessStep();
		ProcessStep evaluation = new ProcessStep();

		CLIModule lectureRecensement = new CLIModule();
		lectureRecensement.setName("Lecture du fichier RP 2017");
		lectureRecensement.setPath("lecture/lecture-rp/");
		String path = ProcessComponent.ROOT_FOLDER + "/process/" + lectureRecensement.getPath();
		lectureRecensement.setInData(ProcessComponent.SOURCE_RP_2017);
		lectureRecensement.setCommandLine(ProcessComponent.rScript + " lecture-rp.R " + path);

		lecture.addModule(lectureRecensement);
		process.addStep(lecture);

		try {
			process.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
