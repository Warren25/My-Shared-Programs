package com.github.applications;
/**
 * Warren Edward Jones
 * Date Finished: 08/01/16
 * Working Code for Exercise 42 with custom modifications not required by the exercise
 * A text-based user interface for the Hangman game
 * Link: http://mooc.cs.helsinki.fi/programming-part1/material-2013/week-2?noredirect=1
 * Version 0.1
 */

// Scanner class imported for user to input guesses
import java.util.Scanner;

public class Hangman {
	
	// List of class variables
	String status;
	String quit;
	String word;
	String newWord = "";
	// StringBuilder is used because it is mutable, unlike Strings!
	StringBuilder reveal = new StringBuilder(newWord);
	String alphabet = "abcdefghijklmnopqrstuvwxyz";
	String newAlphabet = "abcdefghijklmnopqrstuvwxyz";
	String usedLetters = "";
	String guessedLetter = "";
	String digits = "1234567890";
	int guessCount;
	int incorrect = 0;
	boolean isGameOn = true;
	String hangedMan = "";
	
	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		Hangman hangman = new Hangman();
		
		// status, quit, and word variable are set
		// a new word has to be manually set for each game
		hangman.status = "status";
        hangman.quit = "quit";
        hangman.word = "hangman";
        
        // Set of statements to introduce user to game
		System.out.println("************");
        System.out.println("* Hangman *");
        System.out.println("************");
        System.out.println("");
        printMenu();
        System.out.println("");        
        
        // Encodes the word in question marks
        hangman.newWord = "";
		for (int i = 0; i < hangman.word.length(); i++){
			hangman.newWord += hangman.word.replaceAll(hangman.word, "?");
		}		
		
		hangman.reveal = new StringBuilder(hangman.newWord);
		
		// The main program, starts the loop, loop and game ends when isGameOn has been set to false
        while (hangman.gameOn()) {
        	
        	System.out.println("Type a command: ");
        	
            String command = reader.nextLine();
            
            // Set of statements to apply when user has entered 'status'
            if (command.equals(hangman.status))
            {            	
            	hangman.printStatus();
            	hangman.printWord();
                hangman.printHangedMan();
                
            }
            // Thanks user for playing, and immediately terminates program with break statement
            else if (command.equals(hangman.quit))
            {
            	System.out.println("Thank you for playing!");
            	break;
            }
            // If the user has entered a String of only one length and is a letter in the alphabet, apply logic
            if(command.length() == 1 && hangman.alphabet.contains(command)) {
            	
            	// These 2 statements trim down the original alphabet String into a new String 
            	// with letters that haven't been used yet
            	hangman.guessedLetter = command;
            	hangman.alphabet = hangman.alphabet.replace(hangman.guessedLetter, "");
            	
            	// If guess method returns empty String which it will do if guess is not a match, then user
            	// will be notified, encoded word will be printed, incorrect count will increment,
            	// and printHangedMan method will reflect the increment count when it display
                if (hangman.guess(command).isEmpty()) {
                	System.out.println("The letter " + command + " is not in the word." + "\n");
                	hangman.printWord();
	                hangman.incorrect++;
                    hangman.printHangedMan();
                    // An extra check is made to see if there has been 5 incorrect guesses
                    // Game/program will terminate if this is the case
                    if (hangman.incorrect >= 5){
                    	System.out.println("Sorry!");
                    	System.out.println("You have run out of guesses. Game over.");
                    	hangman.isGameOn = false;
                    }
                } 	// If guess is correct, display to user
                	else {
	                	System.out.println("The letter " + command + " was found in the word!" + "\n");
		                hangman.printWord();
		                hangman.printHangedMan();
	            }
            	
            }
            
            /*Conditional checks for faulty input below*/
            
            // If user enters nothing, print menu
            if (command.isEmpty()){
            	printMenu();
            	continue;
            }
            
            // If users enter a one-character String and is not a letter, notify user of invalid command
            if(command.length() == 1 && !hangman.newAlphabet.contains(command)) {
            	
            	System.out.println("You have entered a symbol. Invalid command." + "\n");
            	printMenu();
            	System.out.println("");
            	
            } 	// Notifies user if they enter a letter that has already been used
            	else if (hangman.usedLetters.contains(command)){
            	
	        		System.out.println("You have already used this letter.");
	            	System.out.println("");
            	
        	} 
            // Adds guessed letters to 'usedLetters' variable
        	hangman.usedLetters += hangman.guessedLetter;
            
        	// Checks for every other kind of string entered
            if (!(command.equals(hangman.status)) && !(command.length() == 1) 
            		&& (!command.isEmpty())){
            	
            	// boolean to be set if String is number, defaulted to false
            	boolean isNumber = false;
            	
            	// Logic to determine if this String is has a digit
            	for (int i = 0; i < command.length(); i++){
            		if ((hangman.digits.contains(command.substring(i, i+1)))){
            			System.out.println("You entered a digit. Invalid command." + "\n");
                    	printMenu();
                    	System.out.println(""); 
                    	isNumber = true;
                    	break;
            		}
            	}
            	
            	// If there is no digit in the String
            	if (!isNumber){
            	System.out.println("You have entered an invalid String. Invalid command." + "\n");
            	printMenu();
            	System.out.println("");
            	}
            }
            
            // If user has correctly guessed the word, user deserves to be commended
            // Note that isGameOn has been set to false to end game and terminate program
            if (hangman.reveal.toString().equals(hangman.word)){
            	System.out.println("Congratulations, you have won the game!" + "\n");
            	hangman.isGameOn = false;
            }
            
        }

	}
	
	// Prints menu
	public static void printMenu() {
        System.out.println(" * menu *");
        System.out.println("quit   - quits the game");
        System.out.println("status  - prints the game status");
        System.out.println("a single letter uses the letter as a guess");
        System.out.println("an empty line prints this menu");
    }
	
	// Method that returns variable to keep the program running
	// Program will terminate when method returns false
	boolean gameOn(){
		return isGameOn;
	}
	
	// Prints updated in-game status of user
	void printStatus(){
		// Prints status when user hasn't made any guesses
		if (guessCount == 0){
			System.out.println("You have not made any guesses yet.");
			System.out.println("Unused letters: abcdefghijklmnopqrstuvwxyz" + "\n");
		} 
		// Once user has made at least one guess, updates the user on what letters have not been guessed
		else if (guessCount > 0){
			System.out.println("Unused letters: " + alphabet + "\n");
		}
	
	}
	
	// Prints a visual of the hangman's platform
	// Only prints when user has made at least one guess, none incorrect
	void printMan() {
		String base1 = "_______" + "\n";
		String base2 = "|" + "\n";
		String base3 = "|" + "\n";
		String base4 = "|" + "\n";
		String base5 = "|" + "\n";
		String base6 = "|_" + "\n";
		String platform = base1 + base2 + base3 + base4 + base5 + base6;
		System.out.println(platform);
	}
	
	// Revised version of printMan() method that incrementally adds a hanging man with each incorrect guess
	void printHangedMan() {
		String base1 = "_______" + "\n";
		String base2 = "|";
		String base3 = "|";
		String base4 = "|";
		String base5 = "|";
		String base6 = "|_";
		String space = "\n";
		
		String head = " (-_-)" + "\n";
		String neck = "   |" + "\n";
		String arms = "  /|\\" + "\n";
		String body = "   |" + "\n";
		String legs = " /|\\" + "\n";
		
		// Each case represents how many wrong guesses
		// 5 wrong guesses displays to the user that they have lost, and program terminates
		switch (this.incorrect){
		case 0: printMan();
				break;
		case 1: hangedMan = base1 + base2 + head + base3 + space + base4 + space + base5 + space + base6;
				break;
		case 2: hangedMan = base1 + base2 + head + base3 + neck + base4 + space + base5 + space + base6;
				break;
		case 3: hangedMan = base1 + base2 + head + base3 + neck + base4 + arms + base5 + space + base6;
				break;
		case 4: hangedMan = base1 + base2 + head + base3 + neck + base4 + arms + base5 + body + base6;
				break;
		case 5: hangedMan = base1 + base2 + head + base3 + neck + base4 + arms + base5 + body + base6 + legs;
				break;				
		}
		// Prints the hanged man, each piece of the body and platform was incrementally added
		System.out.println(hangedMan);
	}
	
	// Prints word encoded in question marks
	// When no guesses have been made, the word is displayed fully as question marks
	// With each correct guess, question marks are replaced by the correct char, slowly revealing the word
	void printWord(){
		// Only want logic to apply with at least one guess
		if(guessCount > 0){
			// If a correct guess is made
			if(this.word.contains(this.guessedLetter)){
				// Replace that question mark with the correct guess of the user
				int index = this.word.indexOf(this.guessedLetter);
				// Logic for when there are multiple of the same letter in a word
				while (index >= 0) {
					reveal.setCharAt(index, this.word.charAt(index));
					index = word.indexOf(this.guessedLetter, index + 1);
				}				
			}
		}
		
		System.out.print("Word to be guessed: " + reveal + "\n");		
	}
	
	// Returns the letter the user guessed as a String, not char
	// Only returns if that letter is present in the 'word' class variable
	// Returns empty String if the guessed letter is not present in the 'word' variable
	String guess(String letter){
		
		// Increments the number of guesses made
		guessCount += 1;
		
		// Passes guessed letter as argument to String named 'found'
		String found = letter;
		// Want method to return empty String if guess is incorrect, assign empty String
		String notFound = "";
		
		if (word.contains(letter))
		{	// Either return guessed letter
			return found;
		} 
		else if (!(word.contains(letter)))
		{	// Or return empty String
			return notFound;
		}
		// Returns empty String if the first two conditions aren't met
		return "";
	}

}
