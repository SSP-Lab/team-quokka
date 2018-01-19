package fr.insee.sirene.hackathon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

public class Sampler {

	public static void main(String[] args) throws IOException {

		Random random = new Random();

		double rate = 0.001;

		String sampleFileName = StringUtils.replace(Configuration.SOURCE_RP_2017, ".csv", "-extrait.csv");
		Files.write(Paths.get(sampleFileName), (Iterable<String>)Files.lines(Paths.get(Configuration.SOURCE_RP_2017)).filter(line -> random.nextFloat() < rate)::iterator);
	}
}
