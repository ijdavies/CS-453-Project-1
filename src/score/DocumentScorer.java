package score;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.tartarus.snowball.SnowballStemmer;

import tokenize.DocumentIndexer;
import tokenize.StopWords;

public class DocumentScorer {

	private HashMap<Integer, Double> documentScores = new HashMap<>();
	private DocumentIndexer indexer;

	public DocumentScorer() throws FileNotFoundException {
		this.indexer = new DocumentIndexer();
		this.indexer.indexAllFiles();
	}

	public void scoreDocumentsFromQuery(String query)
			throws FileNotFoundException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {

		File tokenDir = new File("resources\\Tokenized");
		File[] files = tokenDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			int fileNumber = Integer.valueOf(files[i].getPath().replaceAll(
					"[^\\d]", ""));
			scoreDocument(fileNumber, query);
		}
		for (int i = 0; i < 10; i++) {
			Map.Entry<Integer, Double> max = null;

			for (Entry<Integer, Double> entry : documentScores.entrySet()) {
				if (max == null
						|| entry.getValue().compareTo(max.getValue()) < 0)
					max = entry;
			}
			System.out.println(String.format("Best result #%d: Document %d -- Score: %f" , (i + 1) , max.getKey(), max.getValue()));
			documentScores.remove(max.getKey());
		}
	}

	private void scoreDocument(int docNumber, String query)
			throws FileNotFoundException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		String[] keyWords = query.split("[\\s]");
		int documentTotalWordCount = this.indexer.getWordCounts()
				.get(docNumber);
		Double document_queryScore = 0D;

		Class<?> stemClass = Class
				.forName("org.tartarus.snowball.ext.englishStemmer");
		SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
		StopWords stopwords = new StopWords();

		for (String word : keyWords) {
			stemmer.setCurrent(word.trim());
			stemmer.stem();
			String stemmed = stemmer.getCurrent();

			if (!stopwords.contains(word) && this.indexer.getIndex().containsKey(stemmed) && this.indexer.getIndex().get(stemmed).containsKey(docNumber)) {

				int keywordFrequency = this.indexer.getIndex().get(stemmed)
						.get(docNumber);
				document_queryScore += (termFrequency(keywordFrequency,
						documentTotalWordCount) * inverseDocumentFrequency(
						keywordFrequency, documentTotalWordCount));
			}
		}
		documentScores.put(docNumber, document_queryScore);
	}

	private Double termFrequency(int keywordFrequency, int wordCount) {
		return ((double) keywordFrequency / (double) wordCount);
	}

	private Double inverseDocumentFrequency(int keywordFrequency,
			int documentTotalWordCount) {
		return (Math
				.log(((double) keywordFrequency / (double) documentTotalWordCount)) / Math
				.log(2.0));
	}

	// private int getWordCount(File file) throws FileNotFoundException {
	// Scanner sc = new Scanner(file);
	// int count = 0;
	// while (sc.hasNext()) {
	// sc.next();
	// count++;
	// }
	// sc.close();
	// return count;
	// }

}
