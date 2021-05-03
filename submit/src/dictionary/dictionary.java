package dictionary;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class dictionary {
	public static ArrayList<String> createCleanDictionary(String fileName) {
		
		//create arraylist to store sum of numbers for each line of given file
		ArrayList<String> dictarray = new ArrayList<String>();
		
		//create file object
		File file = new File(fileName);
		
		//define file reader
		FileReader fileReader = null;
		
		//define buffered reader
		BufferedReader bufferedReader = null;
		
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			
			String line;


			while ((line = bufferedReader.readLine()) != null) {
				//split line into tokens (values) based on whitespace using regular expression \\s+
				//to indicate one or more instances of whitespace
//				String[] numStringArray = line.trim().split("\\s+");
				
				String s = line.trim();
				boolean goodWord = true;
				for (int i = 0; i < s.length(); i++){
				    char check = s.charAt(i);     
				    if (Character.isLowerCase(check)){
				    	continue;
				    	//System.out.println("not okay");
				    }else {
				    	goodWord = false;
					    break;
				    }    
				}
				if (goodWord) {
					dictarray.add(line);
				}else {
					continue;
				}
			}
			
		} catch (FileNotFoundException e) {
			//gets and prints filename
			System.out.println("Sorry, " + file.getName() + " not found.");
		} catch (IOException e) {
			//prints the error message and info about which line
			System.out.println("IOException");
			e.printStackTrace();
		} finally {
			
			//regardless, close file objects
			try {
				fileReader.close();
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		System.out.println(dictarray);
		
		return dictarray;
		
	}
}
