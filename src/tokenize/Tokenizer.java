package tokenize;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tokenizer {

	StopWords stopwords = new StopWords();
	PorterStemmer stemmer = new PorterStemmer();

	public void tokenize() throws IOException {
		File wikiDir = new File("resources\\wiki");
		File[] files = wikiDir.listFiles();
		for (int j = 0; j < files.length; j++) {
			// for (File file : wikiDir.listFiles()) {
			Scanner sc = new Scanner(files[j]);
			StringBuilder sb = new StringBuilder();
			while (sc.hasNext()) {
				String word = sc.next();
				ArrayList<String> tokens = tokenizeWord(word);
				if (tokens != null) {

					for (int i = 0; i < tokens.size(); i++) {
						if (!stopwords.contains(tokens.get(i))) {

//							String stem = stemmer.stem(tokens.get(i).trim());
							sb.append(tokens.get(i));
							if (i < tokens.size() - 1 || sc.hasNext())
								sb.append(" ");
						}
					}
				}
			}
			File tokenizedFile = new File("resources\\Tokenized\\doc_"
					+ (j + 1) + "_tokenized.txt");
			if (!tokenizedFile.exists())
				tokenizedFile.createNewFile();
			else {
				tokenizedFile.delete();
				tokenizedFile.createNewFile();
			}
			FileWriter fw = new FileWriter(tokenizedFile.getAbsolutePath());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sb.toString());
			bw.close();
			sc.close();
		}
	}

	public ArrayList<String> tokenizeWord(String word)
			throws FileNotFoundException {
		ArrayList<String> tokenized = new ArrayList<>();
		word = word.toLowerCase();
		String[] justWord = word.split("[^A-Za-z0-9'.]");
		if (justWord.length > 1) {
			String firstHalf = justWord[0];
			String secondHalf = justWord[1];
			String concat = firstHalf + secondHalf;

			if (checkSpelling(concat)) {
				tokenized.add(concat);
			} else {
				if (checkSpelling(firstHalf)) {
					tokenized.add(firstHalf);
				}
				if (checkSpelling(secondHalf)) {
					tokenized.add(secondHalf);
				}
			}
			return tokenized;
		}
		if (justWord.length > 0) {
			tokenized.add(justWord[0]);
			return tokenized;
		}
		return null;
	}

	public boolean checkSpelling(String word) throws FileNotFoundException {

		Scanner sc = new Scanner(new File("resources\\dictionary.txt"));
		while (sc.hasNext()) {
			String dictionaryEntry = sc.next();
			if (word.equals(dictionaryEntry))
				return true;
		}

		return false;
	}
}
