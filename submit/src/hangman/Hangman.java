package hangman;
// import hangman.dictionary; // TA removed
import java.util.ArrayList;

public abstract class Hangman {
	//Sets hangman type, traditional or evil
	public String type;
	
	///Word list
	public ArrayList<String> wordList = new ArrayList<String>();
	
	//Current Word (Will change for evil)
	public String word;
	
	//Word length
	public int wordLength;
	
	//Correct guesses
	public ArrayList<String> correctGuesses = new ArrayList<String>();
	
	//Guess counter
	public int guessCount;
	
	//Wrong guesses
	public ArrayList<String> wrongGuesses = new ArrayList<String>();
	
	//Select Word to start the game
	abstract public String selectWord();
	
	//Find player's guess
	abstract public boolean findGuess(String guess);
	
	//check if player already guessed the input
	abstract public boolean previousGuess(String guess);
}
