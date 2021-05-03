import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

// import hangman.dictionary; // TA removed
import dictionary.dictionary; // TA added
import hangman.EvilHangman;
import hangman.Hangman;
import hangman.HangmanTraditional;
//test test

public class HangmanGame {

	public static void main(String[] args) {
		
		//Debug mode toggle true or false
		boolean debugMode = true;
		Hangman hanger;
		//Scanner for user inputs
		Scanner scanner = new Scanner(System.in);
		//set up cleanDictionary
		String fileName = "words.txt";
		ArrayList<String> ar = dictionary.createCleanDictionary(fileName);
		
		//Randomly pick Evil or Traditional hangman
		Random random = new Random();
		 boolean randBool = random.nextBoolean();
		 if (randBool) {
			 hanger = new HangmanTraditional(ar);
		 } else {
			hanger = new EvilHangman(ar);
		 }
				
		//Select word/wordgroup
		hanger.word = hanger.selectWord();
		//Print greeting
		String greeting = "Welcome to Hangman! ";
		if (debugMode) {
			greeting += "[DEBUG MODE: " + hanger.type.toUpperCase() + " HANGMAN]";
		}
		System.out.println(greeting);
		
		//Game loop, exit when done guessing (no more underscores in correctGuesses)
		while (hanger.correctGuesses.contains("_")) {
			
			//Print game board
			System.out.println();
			System.out.println("Guess a letter");
			//correctGuesses stored as ArrayList, need to cycle through strings to print
			for (String letter: hanger.correctGuesses) {
				System.out.print(letter);
			}
			System.out.println();
			
			//Incorrect Guesses line
			if (hanger.wrongGuesses.size() != 0) {
				String incorGuessString = "Incorrect guesses: [";
				for (String letter: hanger.wrongGuesses) {
					incorGuessString += letter + ", ";
				}
				//pull off the trailing comma and close bracket
				incorGuessString = incorGuessString.substring(0, incorGuessString.length()-2) + "]";
				//print incorrect guesses line
				System.out.println(incorGuessString);
			}
			
			//Total guesses line
			System.out.println("Total guesses: " + hanger.guessCount);
			
			//print word or wordList for debugging
			if (debugMode) {
				String currentString = "Current word";
				if (hanger.type == "Traditional") {
					currentString += ": "+ hanger.word;
				} else {
					currentString += " list sample: [";
					for (int i = 0; i < hanger.wordList.size(); i ++) {
						currentString += hanger.wordList.get(i);
						currentString += ", ";
						if (i > 8) {
							break;
						}
					}
					currentString = currentString.substring(0, currentString.length()-2) + " ...]";
				}
				System.out.println(currentString);
			}
			//Get user input
			String guess = scanner.nextLine();
			
			//Check if user previously guessed that letter
			if (hanger.previousGuess(guess)) {
				System.out.println("You already guessed that letter!");
			} else {
				//Find Guess in word, checks valid input
				if (hanger.findGuess(guess)) {
					hanger.guessCount ++;
				} else {
				System.out.println("Invalid guess, make sure you're inputting a single letter.");			
				}
			}
		}
		//Final Print game board
		System.out.println();
		System.out.println("Game Over");
		//correctGuesses stored as ArrayList, need to cycle through strings to print
		for (String letter: hanger.correctGuesses) {
			System.out.print(letter);
		}
		System.out.println("You were playing "+ hanger.type + "style hangman.");
		System.out.println();
		//close out scanner
		scanner.close();
	}

}
