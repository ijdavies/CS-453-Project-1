package run;

import java.io.File;
import java.io.IOException;

import score.DocumentScorer;
import tokenize.Tokenizer;

public class Main {
	static Tokenizer tokenizer = new Tokenizer();
	static DocumentScorer scorer;// = new DocumentScorer();
	static Statistician stat = new Statistician();
	
	public static void main(String... args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {

		//		tokenizer.tokenize();
		
//		scorer = new DocumentScorer();
//		scorer.scoreDocumentsFromQuery("actor appeared in movie premiere");
		stat.countWords(new File("resources\\wiki"));
		
	}
}
