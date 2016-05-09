package tokenize;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class DocumentIndexer {

	HashMap<String, HashMap<Integer, Integer>> index = new HashMap<>();

	public void indexAllFiles() throws FileNotFoundException{
		File tokenDir = new File("resources\\Tokenized");
		File[] files = tokenDir.listFiles();
		for (int i = 0; i < files.length; i++){
			indexFile(files[i], i+1);
		}
	}
	
	public void indexFile(File file, int docNumber)
			throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		while (sc.hasNext()) {
			String word = sc.next();
			addWordToIndex(word, docNumber);
		}
		sc.close();
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

}
