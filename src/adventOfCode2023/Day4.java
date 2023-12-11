package adventOfCode2023;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class Day4 {

	static ArrayList<Integer> cardCount = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		System.out.printf("For part 1, the total points is %d.\n", part1Solution("inputFiles/day4Input.txt"));
		System.out.printf("For part 2, the total number of cards is %d.", part2Solution("inputFiles/day4Input.txt"));
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
	 * Parse a card from a line giving both the card number and the rest of the
	 * string without the "Card XX:" prefix.
	 * @param card A card of the form "Card XX: <numbers> | <numbers>"
	 * @return [cardNumber, "<numbers> | <numbers>"]
	 */
	public static String[] parseCard(String card) {
		String[] result = new String[2];
		
		String[] colonSplit = card.split(":");
		
		result[1] = colonSplit[1];
		
		String[] spaceSplit = colonSplit[0].split(" ");
		
		String cardNumber = spaceSplit[spaceSplit.length - 1];
		result[0]  = cardNumber;
		
		return result;
	}
	

	/**
	 * Populate the cardCount class variable with the number of cards that are won.
	 * @param card A card of the form "Card XX: <numbers> | <numbers>"
	 * @return
	 */
	public static void calculateCardsIncrease(String card) {

		String[] parsedCard = parseCard(card);
		
		Integer cardNumber = Integer.parseInt(parsedCard[0]);
		
		String withoutCardNumber = parsedCard[1];
		
		Integer currentIndex = cardNumber - 1;

		if (cardCount.size() < cardNumber) {
			cardCount.add(0);
		}
		
		if (cardCount.get(currentIndex) == 0) {
			cardCount.set(currentIndex, 1);
		} else {
			cardCount.set(currentIndex, cardCount.get(currentIndex) + 1);
		}
		
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
			
			Integer playerNumber= Integer.parseInt(num);
			cardNumbers.add(playerNumber);
		}
		
		cardNumbers.retainAll(winningNumbers);
		
		Integer numMatches = cardNumbers.size();
		
		while (cardCount.size() <= currentIndex + numMatches) {
			cardCount.add(0);
		}
		
		for (int i = 1; i <= numMatches; i++) {
			cardCount.set(currentIndex + i, cardCount.get(currentIndex + i) + cardCount.get(currentIndex));
		}
	
		
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

	/**
	 * Solve Part 1 and find the total number of points the cards at inputPath
	 * provide.
	 * @param inputPath File path to the cards separated by a newline character.
	 * @return The total points each card gets.
	 */
	public static Integer part1Solution(String inputPath) {
	
		ArrayList<String> cards = readFile(inputPath);
		Integer totalPoints = 0;
	
		for (String card : cards) {
			totalPoints += calculateCardPoints(card);
		}
		
		return totalPoints;
	}

	/**
	 * Find the solution to AoC 2023, Day 4 part 2.
	 * @param inputPath The path to the input file.
	 * @return The total cards won from all the scratch cards.
	 */
	public static Integer part2Solution(String inputPath) {
	
		ArrayList<String> cards = readFile(inputPath);
		Integer totalCards = 0;
	
		for (String card : cards) {
			calculateCardsIncrease(card);
		}
		
		for (Integer count : cardCount) {
			totalCards += count;
		}
		
		return totalCards;
	}
}
