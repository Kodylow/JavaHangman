package hangman;
// import hangman.dictionary; // TA removed
import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
//test
public class EvilHangman extends Hangman {
	//Constructor for Evil hangman type
	public EvilHangman(ArrayList<String> wordList) {
		this.wordList = wordList;
		this.type = "Evil";
		this.word = "";
		this.wordLength = 0;
	}
		
	//Overload of selectWord to use word length
	@Override
	public String selectWord() {
		//random wordlength
		Random random = new Random();
		int temp = random.nextInt(this.wordList.size());
		this.word = this.wordList.get(temp);
		this.wordLength = this.word.length();
		//set correctLetters size to wordLength
		this.correctGuesses = new ArrayList<String>(this.wordLength);
		
		//fill with underscores to start
		for (int i = 0; i < this.wordLength; i++) {
			this.correctGuesses.add("_");
		}
		//filter out wrong size words from wordList
		this.wordList = this.filterWrongSizeWords(this.wordLength);
		return this.word;
	}
	
	//find guess in words of wordList, group them based on occurrences, then update wordList
	@Override
	public boolean findGuess(String guess) {
		boolean inWord = false;
		//format guessed letter to lowercase and remove spaces
		guess = guess.toLowerCase().strip();
		//guess must be a single alpha letter, we know it's a String from the method's argument type
		if (guess.matches("[a-z]{1}") ) {
			//create largest wordGroup, update word and correctGuesses
			this.largestWordGroup(guess);

			if (!this.correctGuesses.contains(guess)) {
				this.wrongGuesses.add(guess);
				inWord = true;
			}
		} else { //input not a single letter, invalid guess
			return inWord; 
		}
		return true;
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
	//removes all bigger words than selectWord's length from wordList
	public ArrayList<String> filterWrongSizeWords(int correctLength) {
		ArrayList<String> newWordList = new ArrayList<String>();
		//iterate through wordList, only use words with correctLength
		for (String word : this.wordList) {
			if (word.length() == correctLength) {
				newWordList.add(word);
			} else {
				continue;
			}
		}
		//return new word list
		return newWordList;
	}
	
	//Find largest word group based on input guess
	public void largestWordGroup(String guess) {
		Map<String, ArrayList<String>> wordGroups = new HashMap<String, ArrayList<String>>();
		//start wordGroups with current correct guesses
		ArrayList<String> hold = new ArrayList<String>();
		String correctGuessesString = "";
		for (String letter : this.correctGuesses) {
			correctGuessesString += letter;
		}
		wordGroups.put(correctGuessesString, hold);
		
		//generate all possible group keys based on guess, already have all words at correct length
		//from selectWord method
		for (String word: this.wordList) {
			ArrayList<String> potentialKey = (ArrayList) this.correctGuesses.clone();
			for (int i = 0; i < word.length(); i++) {
				if (guess.charAt(0) == word.charAt(i)) {
					potentialKey.set(i, guess);
				}
			}
			//convert potentialKey from ArrayList to String
			String potentialKeyString = "";
			for (String letter: potentialKey) {
				potentialKeyString += letter;
			}
			//add word and key to wordGroups
			
			hold = wordGroups.get(potentialKeyString);
			//if hold not null then potential key already has mapped word
			//add word to hold then put it back in HashMap
			if (hold == null) {
				hold = new ArrayList<String>();
			}
			hold.add(word);
			wordGroups.put(potentialKeyString, hold);
			//if hold was null this makes a new mapping for a new key
			
		}
		
		//Find largest word group based on mapping
		String biggestKey = "";
		ArrayList<String> biggestWordGroup = new ArrayList<String>();
		ArrayList<String> tempWordGroup = new ArrayList<String>();
		for (Map.Entry<String, ArrayList<String>> pairEntry: wordGroups.entrySet()) {            
			tempWordGroup = (ArrayList<String>) pairEntry.getValue();
			if (tempWordGroup.size() > biggestWordGroup.size()) {
				biggestWordGroup = tempWordGroup;
				biggestKey = (String) pairEntry.getKey();
			} else if (tempWordGroup.size() == biggestWordGroup.size()) {
				//If same size as biggest so far, randomly picks which to treat as biggest
				Random random = new Random();
				boolean rBool = random.nextBoolean();
				if (rBool) {
					biggestWordGroup = tempWordGroup;
					biggestKey = (String) pairEntry.getKey();
				}
			}
		}
		
		//Found biggest word group, update correctGuesses for new string
		for (int i = 0; i < biggestKey.length(); i++) {
			String temp = "";
			temp += biggestKey.charAt(i);
			this.correctGuesses.set(i, temp);
		}
		
		//return new wordList
        
		this.wordList = biggestWordGroup;	
	}
}
