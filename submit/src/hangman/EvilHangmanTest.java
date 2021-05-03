package hangman;
// import hangman.dictionary; // TA removed
import dictionary.dictionary; // TA added
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EvilHangmanTest {
    String fileName = "words_clean.txt";
    EvilHangman hangerEvil1;
    EvilHangman hangerEvil2;
    ArrayList<String> dict1;
    ArrayList<String> dict2;
	@BeforeEach
	//setUp method 
	void setUp()throws Exception{
		//12 letters starting with ab for ease of testing
		
		dict1 = dictionary.createCleanDictionary(fileName);
		hangerEvil1 = new EvilHangman(dict1);
		
		dict2 = new ArrayList<String>();
		dict2.add("abalienating");
		dict2.add("abalienation");
		dict2.add("abandonments");
		dict2.add("abbotnullius");
		dict2.add("abbreviately");
		dict2.add("abbreviating");
		dict2.add("abbreviation");
		dict2.add("abbreviatory");
		dict2.add("abbreviators");
		dict2.add("abbreviature");
		hangerEvil2 = new EvilHangman(dict2);
	}

	@Test
	final void testSelectWord() {
        //selectWord doesn't remove word from dict
		String wordFromDict1 = hangerEvil1.selectWord();
        hangerEvil2.selectWord();
        assertTrue(dict1.contains(wordFromDict1));
        
        //selectWord sets proper wordLength
        assertTrue(hangerEvil1.correctGuesses.size() == hangerEvil1.wordLength);
        assertEquals(hangerEvil2.correctGuesses.size(), 12);
        assertEquals(hangerEvil2.wordLength, 12);
        
        //selectWord outputs correctGuesses made up of underscores
        assertTrue(hangerEvil1.correctGuesses.contains("_"));
        assertTrue(hangerEvil2.correctGuesses.contains("_"));
        assertFalse(hangerEvil1.correctGuesses.contains("a"));
        assertFalse(hangerEvil1.correctGuesses.contains("a"));                   
	}

	@Test
	final void testFindGuess() {
        hangerEvil2.selectWord();
		//findGuess keeps correctGuesses the same when there's the guess isn't in any of the wordGroup potentials
        ArrayList<String> holding = (ArrayList) hangerEvil2.correctGuesses.clone();
        hangerEvil2.findGuess("z");
        assertEquals(holding, hangerEvil2.correctGuesses);
        //wordList remains the same
        assertTrue(hangerEvil2.wordList.size() == 10);
        
        //guessing "s" updates correctGuesses and cuts wordList size
        hangerEvil2.findGuess("s");
        assertEquals(holding, hangerEvil2.correctGuesses);
        assertEquals(hangerEvil2.wordList.size(), 7);
        
        //guessing "b" changes correctGuesses to _bb_________
        hangerEvil2.findGuess("b");
        assertEquals("b", hangerEvil2.correctGuesses.get(1));
        assertEquals("b", hangerEvil2.correctGuesses.get(2));
        //and cuts Wordlist size
        assertEquals(hangerEvil2.wordList.size(), 5);
	}

	@Test
	final void testPreviousGuess() {
        hangerEvil2.selectWord();
		//previous guess returns false before any guesses
        assertEquals(hangerEvil1.wrongGuesses.size(), 0);
        assertEquals(hangerEvil2.wrongGuesses.size(), 0);
        assertFalse(hangerEvil2.previousGuess("a"));
        
        //previousGuess returns false after wrong Guess gets incorrect guess off 0
        hangerEvil2.findGuess("z");
        assertFalse(hangerEvil2.previousGuess("b"));        
        
        //previousGuess returns false after correct Guess
        hangerEvil2.findGuess("a");
        assertFalse(hangerEvil2.previousGuess("b"));
        //previousGuess returns true for both correct and wrong previous guesses
        assertTrue(hangerEvil2.previousGuess("z"));
        System.out.println(hangerEvil2.correctGuesses);
        assertTrue(hangerEvil2.previousGuess("a"));
        hangerEvil2.selectWord();
    }

	@Test
	final void testEvilHangman() {
		assertEquals(hangerEvil2.type, "Evil");
        
	}
    
    @Test
	final void testLargestWordGroup() {
		//create a correctGuesses for testing purpose
		for(int o=0; o<12; o++) {
			hangerEvil2.correctGuesses.add("_");		
		}
		//when the input does not match with any letter in the entire word list, the largest word group 
		//will have the same number of words as the original word group
		hangerEvil2.largestWordGroup("z");
		assertEquals(hangerEvil2.wordList.size(), 10);
		//reset correctGuesses array
		for(int d=0; d<12; d++) {
			hangerEvil2.correctGuesses.set(d, "_");		
		}
		//when the input is "a" for hangerEvil2, the largest word group will have this pattern:"a______a____"
		//there should be 6 words that fit into that pattern
		hangerEvil2.largestWordGroup("a");
		assertEquals(hangerEvil2.wordList.size(), 6);
		/**
		 * Iterate three times (test a case where we have input three times)
		 */
		//Let's pretend that our first random word is "cycling", which has length of 7
		//create a correctGuesses for testing purpose
		for(int o=0; o<7; o++) {
			hangerEvil1.correctGuesses.add("_");		
		}
		//Manually filter out all the words that don't have length of 7 for our testing purposes
		hangerEvil1.wordList=hangerEvil1.filterWrongSizeWords(7);
		//when the input is "c" for hangerEvil1, the largest word group will have this pattern:"_______"
		//there should be 6 words that fit into that pattern
		hangerEvil1.largestWordGroup("c");
		assertEquals(hangerEvil1.wordList.size(), 6);
		//when the second input is "e" for hangerEvil1, the largest word group will have still this pattern:"____i__"
		//there should be 3 words that fit into that pattern
		hangerEvil1.largestWordGroup("i");
		assertEquals(hangerEvil1.wordList.size(), 3);
		//Lastly, when the third input is "a" for hangerEvil1, the largest word group will have still this pattern:"____i__"
		//there should be 2 words that fit into that pattern
		hangerEvil1.largestWordGroup("a");
		assertEquals(hangerEvil1.wordList.size(), 2);
	}
    
    @Test
	final void testFilterWrongSizeWords() {
		//Test on the word_clean dictionary
		assertEquals(hangerEvil1.filterWrongSizeWords(5).size(), 15);
		assertEquals(hangerEvil1.filterWrongSizeWords(7).size(), 7);
		assertEquals(hangerEvil1.filterWrongSizeWords(9).size(), 2);
		//Test on the fixed length dictionary2
		assertEquals(hangerEvil2.filterWrongSizeWords(10).size(), 0);
		assertEquals(hangerEvil2.filterWrongSizeWords(12).size(), 10);
		
	}
}
