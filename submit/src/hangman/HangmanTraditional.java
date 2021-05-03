package hangman;
// import hangman.dictionary; // TA removed
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HangmanTraditional extends Hangman{
	//Constructor for Traditional hangman type
	public HangmanTraditional(ArrayList<String> wordList) {
		this.wordList = wordList;
		this.type = "Traditional";
		this.word = "";
		this.guessCount = 0;
	}
	
	@Override
	public String selectWord() {
		//random for selecting word in wordList
		Random random = new Random();
		int rand = random.nextInt(this.wordList.size());
		//select word and confirm lowercase
		this.word = this.wordList.get(rand).toLowerCase();
		
		//get word length
		int wordLength = this.word.length();
		
		//set correctGuesses size to word length
		this.correctGuesses = new ArrayList<String>(wordLength);
		
		//fill correctGuesses with underscores to start
		for (int i = 0; i < wordLength; i++) {
			this.correctGuesses.add("_");
		}
		//set wrongGuesses
		this.wrongGuesses = new ArrayList<String>();
		return this.word;
	}
	
	@Override
	public boolean findGuess (String guess) {
		boolean inWord = false;
		//format guessed letter to lowercase and remove spaces
		guess = guess.toLowerCase().strip();
		//guess must be a single alpha letter, we know it's a String from the method's argument type
		if (guess.matches("[a-z]{1}") ) {
			//iterate over all chars in word, change to guessed letter if in word
			for (int i = 0; i < this.word.length(); i++) {
				//guessed letter in word, change from underscore to guessed letter and set inWord to true
				if (guess.charAt(0) == this.word.charAt(i)) {
					this.correctGuesses.set(i, guess);
					inWord = true;
				}
			}
			//if guess not in word
			if (inWord == false) {
				//confirm bad guess not in previous wrongGuesses
				if (this.wrongGuesses.contains(guess) == false) {
					this.wrongGuesses.add(guess);
				}
			}
			
			return true;
		} else { //input not a single letter, invalid guess
			return false;
		}
	}
	
	//Checks if player previously guessed the letter before going through findGuess
	@Override
	public boolean previousGuess(String guess) {
		if (this.wrongGuesses.contains(guess) || this.correctGuesses.contains(guess)) {
			return true;
		} else {
			return false;
		}
	}
}
