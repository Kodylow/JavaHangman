package hangman;
// import hangman.dictionary; // TA removed
import dictionary.dictionary; // TA added
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class HangmanTraditionalTest {
	String fileName = "words_clean.txt";
	ArrayList<String> dict1 = dictionary.createCleanDictionary(fileName);
	ArrayList<String> dict2 = new ArrayList<String>();
	ArrayList<String> dict3 = new ArrayList<String>();
	HangmanTraditional hangerTra1 = new HangmanTraditional(dict1);
	HangmanTraditional hangerTra2 = new HangmanTraditional(dict2);
	HangmanTraditional hangerTra3 = new HangmanTraditional(dict3);
	
	

	
	@BeforeEach
	//setUp method 
	void setUp()throws Exception{
		dict2.add("apple");
		dict2.add("orange");
		dict2.add("grape");
		dict3.add("red");
		dict3.add("blue");
		dict3.add("green");
		dict3.add("yellow");
		//We don't know what the randomly selected words will be, so we need to set 
		//those words manually.
		hangerTra1.word="hysterical";
		hangerTra2.word="apple";
		hangerTra3.word="green";
		for (int i=0; i<10; i++) {
			hangerTra1.correctGuesses.add("_");
			if(i<5) {
				hangerTra2.correctGuesses.add("_");
				hangerTra3.correctGuesses.add("_");
			}
		}
	}

	@Test
	void testSelectWord() {
		//It is impossible to test on the random portion of this method because
		//we can't know what the random word will be;
		//Instead, we will test if the word that is selected is actually from the proper
		//word list

		String wordFromDict1= hangerTra1.selectWord();
		String wordFromDict2= hangerTra2.selectWord();
		String wordFromDict3= hangerTra3.selectWord();
		assertTrue(dict1.contains(wordFromDict1));
		assertFalse(dict1.contains(wordFromDict2));
		assertFalse(wordFromDict1.equals("yellow"));
		
		assertTrue(dict2.contains(wordFromDict2));
		assertFalse(dict2.contains(wordFromDict3));
		assertFalse(wordFromDict2.equals("electric"));
		
		assertTrue(dict3.contains(wordFromDict3));
		assertFalse(dict3.contains(wordFromDict2));
		assertFalse(wordFromDict3.equals("apple"));
		
	}

	@Test
	void testFindGuess() {
		//if the guess letter is in the correct word, method will return true
		//and it will be in the correctGuesses array
		assertTrue(hangerTra1.findGuess("y"));
		assertTrue(hangerTra1.correctGuesses.contains("y"));
		assertTrue(hangerTra2.findGuess("p"));
		assertTrue(hangerTra2.correctGuesses.contains("p"));
		assertTrue(hangerTra3.findGuess("n"));
		assertTrue(hangerTra3.correctGuesses.contains("n"));
		
		//if the guess letter is not in the correct word, method will still return true;
		//however, the guess letter will not be in the correctGuesses array
		assertTrue(hangerTra1.findGuess("z"));
		assertFalse(hangerTra1.correctGuesses.contains("z"));
		assertTrue(hangerTra2.findGuess("y"));
		assertFalse(hangerTra2.correctGuesses.contains("y"));
		assertTrue(hangerTra3.findGuess("x"));
		assertFalse(hangerTra3.correctGuesses.contains("x"));
		
		//if the guess input is not a letter, return false
		assertFalse(hangerTra1.findGuess("9"));
		assertFalse(hangerTra2.findGuess("-"));
		assertFalse(hangerTra3.findGuess("!"));
		
	}

	@Test
	void testPreviousGuess() {
		//if the guess letter has been guessed and is already in the correct word
		//method will return true 
		assertTrue(hangerTra1.findGuess("y"));
		assertTrue(hangerTra1.previousGuess("y"));
		assertTrue(hangerTra2.findGuess("p"));
		assertTrue(hangerTra2.previousGuess("p"));
		assertTrue(hangerTra3.findGuess("n"));
		assertTrue(hangerTra3.previousGuess("n"));
		
		//if the guess letter has been guessed and but is the wrong letter
		//method will still return true 
		assertTrue(hangerTra1.findGuess("z"));
		assertTrue(hangerTra1.previousGuess("z"));
		assertTrue(hangerTra2.findGuess("y"));
		assertTrue(hangerTra2.previousGuess("y"));
		assertTrue(hangerTra3.findGuess("x"));
		assertTrue(hangerTra3.previousGuess("x"));
		
		//if the guess input has not been guessed, return false
		assertFalse(hangerTra1.previousGuess("a"));
		assertFalse(hangerTra2.previousGuess("b"));
		assertFalse(hangerTra3.previousGuess("c"));
		
		//if the guess input is not a letter, it will not be stored in either correct
		//or wrong word list; so return false
		assertFalse(hangerTra1.findGuess("9"));
		assertFalse(hangerTra2.findGuess("-"));
		assertFalse(hangerTra3.findGuess("!"));
	}

}
