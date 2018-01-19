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
		enrichissement.setName("Enrichissement");
		enrichissement.setPath("enrichissement");
		ProcessStep requetage = new ProcessStep();
		requetage.setName("Requetage");
		requetage.setPath("Requetage");
		ProcessStep scoring = new ProcessStep();
		scoring.setName("Score");
		scoring.setPath("Score");
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
		lectureRecensement.setActive(true);

		/** Module d'enrichissement par indicatrice d'égalité des communes de domicile et de travail **/
		CLIModule egaliteCommunesDomicileTravail = new CLIModule(Language.R);
		egaliteCommunesDomicileTravail.setName("Enrichissement par indicatrice d'égalité des communes de domicile et de travail");
		egaliteCommunesDomicileTravail.setPath("enrichissement/egalite-communes-domicile-travail");
		processPath = Configuration.PROCESS_ROOT_FOLDER + "/" + egaliteCommunesDomicileTravail.getPath();
		egaliteCommunesDomicileTravail.setCommandLine("\"" + Configuration.R_SCRIPT + "\" egalite-communes-domicile-travail.R \"" + processPath + "\"");

		/** Module d'enrichissement par ajout des communes adjacentes **/
		CLIModule ajoutCommunesAdjacentes = new CLIModule(Language.R);
		ajoutCommunesAdjacentes.setName("Enrichissement par ajout des communes adjacentes");
		ajoutCommunesAdjacentes.setPath("enrichissement/ajout-communes-adjacentes");
		processPath = Configuration.PROCESS_ROOT_FOLDER + "/" + ajoutCommunesAdjacentes.getPath();
		ajoutCommunesAdjacentes.setCommandLine("\"" + Configuration.R_SCRIPT + "\" ajout-communes-adjacentes.R \"" + processPath + "\"");

		/** Module de requêtage par appel de l'API Sirene **/
		/*CLIModule apiSireneNaif = new CLIModule(Language.PYTHON);
		apiSireneNaif.setName("Requêtage naïf de l'API Sirene");
		apiSireneNaif.setPath("requetage/api-sirene-naif");
		apiSireneNaif.setCommandLine("\"" + Configuration.PYTHON_EXE + "\" api-sirene-naif.py");
		*/
		/** Module de requêtage par appel de l'API Sirene **/
		CLIModule apiSireneNaif = new CLIModule(Language.PYTHON);
		apiSireneNaif.setName("Requêtage phonétique de l'API Sirene");
		apiSireneNaif.setPath("requetage/api-sirene-phonetique");
		apiSireneNaif.setCommandLine("\"" + Configuration.PYTHON_EXE + "\" api-sirene-phonetique.py");

		/** Module de requêtage par appel de l'API de géocodage de la BAN **/
		CLIModule apiGeocodageBAN = new CLIModule(Language.PYTHON);
		apiGeocodageBAN.setName("Requêtage par appel de l'API de géocodage de la BAN");
		apiGeocodageBAN.setPath("requetage/geocodage-ban");
		apiGeocodageBAN.setCommandLine("\"" + Configuration.PYTHON_EXE + "\" geocodage-ban.py");

		/** Module de scoring par distance géographique **/
		CLIModule distanceGeo = new CLIModule(Language.R);
		distanceGeo.setName("Scoring par distance géographique");
		distanceGeo.setPath("scoring/distance-geo");
		processPath = Configuration.PROCESS_ROOT_FOLDER + "/" + distanceGeo.getPath();
		distanceGeo.setCommandLine("\"" + Configuration.R_SCRIPT + "\" distance-geo.R \"" + processPath + "\"");

		/** Module de scoring par distance textuelle **/
		CLIModule distanceTextuelle = new CLIModule(Language.R);
		distanceTextuelle.setName("Scoring par distance textuelle");
		distanceTextuelle.setPath("scoring/distance-texte");
		processPath = Configuration.PROCESS_ROOT_FOLDER + "/" + distanceTextuelle.getPath();
		distanceTextuelle.setCommandLine("\"" + Configuration.R_SCRIPT + "\" distance-texte.R \"" + processPath + "\"");

		/** Module de scoring final **/
		CLIModule scoreFinal = new CLIModule(Language.R);
		scoreFinal.setName("Scoring final");
		scoreFinal.setPath("scoring/score-final");
		processPath = Configuration.PROCESS_ROOT_FOLDER + "/" + scoreFinal.getPath();
		scoreFinal.setCommandLine("\"" + Configuration.R_SCRIPT + "\" score-final.R \"" + processPath + "\"");

		/** Module d'évaluation par soumission à l'API d'évaluation **/
		CLIModule soumissionAPI = new CLIModule(Language.PYTHON);
		soumissionAPI.setName("Soumission à l'API d'évaluation");
		soumissionAPI.setPath("evaluation/soumission-api");
		soumissionAPI.setCommandLine("\"" + Configuration.PYTHON_EXE + "\" soumission-api.py");

		/** Ajout des modules aux étapes **/
		lecture.addModule(lectureRecensement);
		enrichissement.addModule(egaliteCommunesDomicileTravail);
		enrichissement.addModule(ajoutCommunesAdjacentes);
		requetage.addModule(apiSireneNaif);
		requetage.addModule(apiGeocodageBAN);
		scoring.addModule(distanceGeo);
		scoring.addModule(distanceTextuelle);
		scoring.addModule(scoreFinal);
		evaluation.addModule(soumissionAPI);

		process.addStep(lecture);
		process.addStep(enrichissement);
		process.addStep(requetage);
		process.addStep(scoring);
		process.addStep(evaluation);

		try {
			process.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
