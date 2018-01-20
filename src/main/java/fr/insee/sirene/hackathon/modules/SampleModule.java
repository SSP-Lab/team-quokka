package fr.insee.sirene.hackathon.modules;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.insee.sirene.hackathon.Configuration;
import fr.insee.sirene.hackathon.ProcessModule;

/**
 * Module d'échantillonnage : retient une proportion donnée des lignes du fichier d'entrée.
 * 
 * @author Franck
 */
public class SampleModule extends ProcessModule {

	public static Logger logger = LogManager.getLogger(SampleModule.class);

	double DEFAULT_RATE = 0.001;
	double rate = DEFAULT_RATE;

	public void execute() throws Exception {

		Random random = new Random();

		super.execute();
		// L'instruction précédente ramène les fichiers d'entrée à leur emplacement par défaut
		String inputDataFile = Configuration.PROCESS_ROOT_FOLDER + "/" + this.getPath() + "/in-data.csv";
		String inputParamFile = Configuration.PROCESS_ROOT_FOLDER + "/" + this.getPath() + "/in-param.csv";
		String outputDataFile = Configuration.PROCESS_ROOT_FOLDER + "/" + this.getPath() + "/out-data.csv";

		if (Files.exists(Paths.get(inputParamFile))) {
			Map<Object, Object> params = Files.lines(Paths.get(inputParamFile)).collect(Collectors.toMap(line -> line.split(",")[0], line -> line.split(",")[1])); // TODO Check type
			try {
				if (params.containsKey("rate")) rate = Float.parseFloat((String) params.get("rate"));
				else logger.info("Taux d'échantillonnage absent des paramètres, la valeur par défaut est utilisée");
			} catch (Exception e) {
				logger.error("Impossible d'interprêter le taux d'échantillonnage passé en paramètre : " + params.get("rate") + ", traitement abandonné");
				return;
			}
		}
		logger.info("Échantillonnage au taux " + rate + " du fichier " + inputDataFile);
		try {
			Stream<String> stream = Files.lines(Paths.get(inputDataFile)).filter(line -> random.nextFloat() < rate);
			Files.write(Paths.get(outputDataFile), (Iterable<String>)stream::iterator);
			logger.info("Échantillon sauvegardé dans le fichier " + outputDataFile);
		} catch (Exception e) {
			logger.error("Erreur dans le module d'échantillonnage : " + e.getMessage());
		}
	}
}
