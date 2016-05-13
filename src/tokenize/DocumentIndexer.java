package tokenize;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class DocumentIndexer {

	HashMap<String, HashMap<Integer, Integer>> index = new HashMap<>();
	HashMap<Integer, Integer> wordCounts = new HashMap<>();


	public void indexAllFiles() throws FileNotFoundException{
		File tokenDir = new File("resources\\Tokenized");
		File[] files = tokenDir.listFiles();
		for (int i = 0; i < files.length; i++){
			int fileNumber = Integer.valueOf(files[i].getPath().replaceAll("[^\\d]", ""));
			indexFile(files[i], fileNumber);
		}
		System.out.println("Done Indexing");
	}
	
	public void indexFile(File file, int docNumber)
			throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		int wordCount = 0;
		while (sc.hasNext()) {
			String word = sc.next();
			addWordToIndex(word, docNumber);
			wordCount++;
		}
		wordCounts.put(docNumber, wordCount);
		sc.close();
		System.out.println("Indexed file " + docNumber);
	}

	private void addWordToIndex(String word, int docNumber) {
		if (index.containsKey(word)) {
			if (index.get(word).containsKey(docNumber)) {
				int currentValue = index.get(word).get(docNumber);
				index.get(word).put(docNumber, currentValue + 1);
			}
			else
				index.get(word).put(docNumber, 1);
		}
		else{
			HashMap<Integer, Integer> newValue = new HashMap<>();
			newValue.put(docNumber, 1);
			index.put(word, newValue);
			
		}
	}
	
	public HashMap<String, HashMap<Integer, Integer>> getIndex() {
		return index;
	}
	
	public void setIndex(HashMap<String, HashMap<Integer, Integer>> index) {
		this.index = index;
	}
	
	public HashMap<Integer, Integer> getWordCounts() {
		return wordCounts;
	}
	
	public void setWordCounts(HashMap<Integer, Integer> wordCounts) {
		this.wordCounts = wordCounts;
	}

}
