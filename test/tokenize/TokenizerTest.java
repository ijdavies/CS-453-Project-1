package tokenize;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

public class TokenizerTest {

	Tokenizer tokenizer = new Tokenizer();
	
	@Test
	public void tokenizeValidWordWithCommaTest() throws FileNotFoundException{
		assertEquals(Arrays.asList("metalloid"), tokenizer.tokenizeWord("\tMetalloid,"));
	}
	
	@Test
	public void tokenizeValidWordWithApostrophe_STest() throws FileNotFoundException{
		assertEquals(Arrays.asList("children's"), tokenizer.tokenizeWord("\nchildren's,"));
	}
	
	@Test
	public void testTokenizeHyphenatedWordConcatenatedFound() throws FileNotFoundException{
		assertEquals(Arrays.asList("metalloid"), tokenizer.tokenizeWord("Metal-loid"));
	}
	
	@Test
	public void testCheckSpellingValidWord() throws FileNotFoundException{
		assertTrue(tokenizer.checkSpelling("metalloid"));
	}
	
	@Test
	public void testCheckSpellingValidWithApostrophe_S() throws FileNotFoundException{
		assertTrue(tokenizer.checkSpelling("children's"));
	}
	
	@Test
	public void testCheckSpellingInvalidWord() throws FileNotFoundException{
		assertFalse(tokenizer.checkSpelling("mettaloid"));
	}
	
	@Test
	public void testTokenize() throws IOException{
		tokenizer.tokenize();
	}
	
}
