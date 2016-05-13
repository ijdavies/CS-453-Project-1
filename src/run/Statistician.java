package run;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import tokenize.Tokenizer;


public class Statistician {

	TreeMap<String, Integer> wordFrequencies = new TreeMap<>();
	Tokenizer tokenizer = new Tokenizer();
	
	public void countWords(File dirFile) throws IOException{
		for (File textFile : dirFile.listFiles()){
			Scanner sc = new Scanner(textFile);
			while (sc.hasNext()){
				String word = sc.next();
				ArrayList<String> tokens = tokenizer.tokenizeWord(word);
				if (tokens != null) {

					for (int i = 0; i < tokens.size(); i++) {
						if (!wordFrequencies.containsKey(tokens.get(i)))
							wordFrequencies.put(tokens.get(i), 1);
						else{
							int currentFrequency = wordFrequencies.get(tokens.get(i));
							currentFrequency++;
							wordFrequencies.put(tokens.get(i), currentFrequency);
						}
					}
				}
			}
			sc.close();
		}
		FileWriter writer = new FileWriter("resources\\stats.csv");
		writer.append("WORD, FREQUENCY\n");
		for (Entry<String, Integer> entry : wordFrequencies.entrySet()){
			writer.append(entry.getKey());
			writer.append(",");
			writer.append(entry.getValue().toString());
			writer.append("\n");
		}
		writer.flush();
		writer.close();
	}
}
