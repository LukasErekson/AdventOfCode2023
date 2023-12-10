package adventOfCode2023;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class Day4 {

	public static void main(String[] args) {
		System.out.printf("For part 1, the total points is %d.", part1Solution("inputFiles/day4Input.txt"));
	}

	
	/**
	 * Read a file from a given input path and return its contents as an
	 * ArrayList of strings, where each entry is one line.
	 * @param inputPath Where to read the file from.
	 * @return The contents of the file at inputPath with each line as an entry.
	 */
	public static ArrayList<String> readFile(String inputPath) {
		ArrayList<String> fileLines = new ArrayList<String>();
		
		try {
			File input = new File(inputPath);
			Scanner fileReader = new Scanner(input);
			
			while (fileReader.hasNextLine()) {
				fileLines.add(fileReader.nextLine());
			}
			
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			System.out.printf("The file %s could not be found.", inputPath);
			e.printStackTrace();
		}
		
		return fileLines;
	}
	
	
	/**
	 * Take in a line of a card input (e.g. - Card 1: 2 3 23 | 3 2 1) and process
	 * how many points the card is worth. Points are determined by 2^{numMatches - 1} or 0
	 * if there are no matches from the left of the '|' to the right of it.
	 * @param card Input line consisting of "Card X: <Winning numbers> | <Your Numbers>
	 * @return The number of points a given card is worth.
	 */
	public static Integer calculateCardPoints(String card) {
		String withoutCardNumber = card.split(":")[1];
		
		String[] justNumbers = withoutCardNumber.split("\\|");
		
		HashSet<Integer> winningNumbers = new HashSet<Integer>();

		for (String num: justNumbers[0].split(" ")) {
			if (num.length() == 0) {
				continue;
			}
			
			Integer winningNumber = Integer.parseInt(num);
			winningNumbers.add(winningNumber);
		}
		
		HashSet<Integer> cardNumbers = new HashSet<Integer>();

		for (String num: justNumbers[1].split(" ")) {
			if (num.length() == 0) {
				continue;
			}
			
			Integer cardNumber = Integer.parseInt(num);
			cardNumbers.add(cardNumber);
		}
		
		cardNumbers.retainAll(winningNumbers);
		
		Integer numMatches = cardNumbers.size();
		
		if (numMatches == 1) {
			return 1;
		}
		else if (numMatches > 1) {
			return (int) Math.pow(2, numMatches - 1);
		}
		
		return 0;
	}
	
	public static Integer part1Solution(String inputPath) {
	
		ArrayList<String> cards = readFile(inputPath);
		Integer totalPoints = 0;
	
		for (String card : cards) {
			totalPoints += calculateCardPoints(card);
		}
		
		return totalPoints;
	}
	
}
