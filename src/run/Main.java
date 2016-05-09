package run;

import java.io.IOException;

import tokenize.Tokenizer;

public class Main {
	static Tokenizer tokenizer = new Tokenizer();
	public static void main(String... args) throws IOException {
		tokenizer.tokenize();
	}
}
