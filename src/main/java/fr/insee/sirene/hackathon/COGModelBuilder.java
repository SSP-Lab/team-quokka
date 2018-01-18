package fr.insee.sirene.hackathon;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class COGModelBuilder {

	public static Logger logger = LogManager.getLogger(COGModelBuilder.class);

	public static Property neighbourProperty = ResourceFactory.createProperty("http://rdf.insee.fr/def/geo#voisin");

	public static void main(String[] args) {

		String cog2017TTL = "src/main/resources/COGcomplet2017.ttl";
		String neighbourhoods2017CSV = "src/main/resources/communes_adjacentes_2017.csv";
		try {
			Model cogModel = buildCOGModel(cog2017TTL, neighbourhoods2017CSV);
			RDFDataMgr.write(new FileOutputStream("src/main/resources/cog2017-neighbours.ttl"), cogModel, Lang.TURTLE);
		} catch (IOException e) {
			logger.error("Exception raised - " + e.getMessage());
			System.exit(1);
		}
		
	}
	public static Model buildCOGModel(String cog2017, String neighbourhoods) throws IOException {

		logger.debug("Building COG model, base COG file is " + cog2017 + ", neighbourhoods file is " + neighbourhoods);

		Model cogModel = ModelFactory.createDefaultModel();
		cogModel.read(cog2017);

		Reader reader = new FileReader(neighbourhoods);
		Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(reader);
		for (CSVRecord record : records) {

			String code = record.get("insee");
			Resource communeResource = cogModel.createResource(getCommuneURI(code));
			List<String> neighbours = Arrays.asList(record.get("insee_voisins").split("\\|"));
			for (String neighbour : neighbours) {
				Resource neighbourResource = cogModel.createResource(getCommuneURI(neighbour));
				communeResource.addProperty(neighbourProperty, neighbourResource);
				neighbourResource.addProperty(neighbourProperty, communeResource); // This is probably superfluous
			}
		}

		return cogModel;
	}

	private static String getCommuneURI(String code) {
		return "http://id.insee.fr/geo/commune/" + code;
	}
}
