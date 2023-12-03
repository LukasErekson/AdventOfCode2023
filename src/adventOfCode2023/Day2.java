package adventOfCode2023;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2 {

	public static void main(String[] args) {
		int maxRed = 12;
		int maxGreen = 13;
		int maxBlue = 14;
	
		// Part 1
		int sumPossibleGameIDs = sumPossibleGameNums("AdventOfCodeDay2Pt1.txt", maxRed, maxGreen, maxBlue);
		System.out.printf("The sum of possible game IDs is %d.\n", sumPossibleGameIDs);
	
		// Part 2
		int powerSum = sumPowerCubeSets("AdventOfCodeDay2Pt1.txt");
		System.out.printf("The sum of the power of the cube sets is %d.\n", powerSum);
	}
	
	/**
	 * Determine whether or not a given game result would have been possible according to
	 * the maximum possible criteria provided.
	 * @param gameResults A map from "red", "green", and "blue" to the maximum number seen from
	 * the games.
	 * @param maxRed The maximum number of red cubes in the bag.
	 * @param maxGreen The maximum number of green cubes in the bag.
	 * @param maxBlue The maximum number of blue cubes in the bag.
	 * @return Whether or not the game would be possible given the constraints.
	 */
	private static boolean isPossible(HashMap<String, Integer> gameResults, int maxRed, int maxGreen, int maxBlue ) {
		return gameResults.get("red") <= maxRed && gameResults.get("blue") <= maxBlue && gameResults.get("green") <= maxGreen;
	}

	/**
	 * Take a single game and parse the round to show the maximum number of
	 * Red, Green, and Blue cubes shown at any given time.
	 * @param game A single game of drawing cubes. It should be formatted like:
	 * 	"Game %d: [%d ((red|green|blue),]+;)+". e.g. - "Game 2: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
	 * @return	A HashMap mapping 'red', 'green', and 'blue' to the maximum
	 * values that we saw cubes of for that game.
	 */
	private static HashMap<String, Integer> parseGame(String game) {
		HashMap<String, Integer> maxValues = new HashMap<String, Integer>(3);
		maxValues.put("red", 0);
		maxValues.put("green", 0);
		maxValues.put("blue", 0);
		
		// Remove the "Game x:" from the front
		String gameRounds = game.split(":")[1];
		
		for (String round : gameRounds.split(";")) {
			String[] draws = round.split(",");
			
			for (String draw : draws) {
				String[] splitDraw = draw.trim().split(" ");
				Integer number = Integer.parseInt(splitDraw[0].replaceAll("[^\\d+]", ""));
				String color = splitDraw[1].replaceAll("[^\\w+]", "");
				if (number > maxValues.get(color)) {
					maxValues.put(color, number);
				}
				
			}
			
		}

		return maxValues;
	}
	
	/**
	 * Return the sum of all the possible game IDs.
	 * @param fileName The name and path of the input file.
	 * @return The sum of all possible game IDs.
	 */
	private static int sumPossibleGameNums(String fileName, int maxRed, int maxGreen, int maxBlue) {
		int possibleGameIDSum = 0;
		
		try {
			File inputFile = new File(fileName);
			Scanner fileReader = new Scanner(inputFile);
			
			while (fileReader.hasNextLine()) {
				String game = fileReader.nextLine();
				if (isPossible(parseGame(game), maxRed, maxGreen, maxBlue)) {
					int gameID = Integer.parseInt(game.split(" ")[1].replace(":", ""));
					possibleGameIDSum += gameID;
				}
				
			}
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			System.out.printf("File with name %s not found", fileName);
		}
		
		return possibleGameIDSum;
	}
	
	/**
	 * Find the sum of the power sets, which is defined as the product of the minimum number of each
	 * color cube for the game to be possible, for a given input file.
	 * @param fileName The name of the file containing the inputs with game information.
	 * @return The sum of all the power sets for the given file.
	 */
	private static int sumPowerCubeSets(String fileName) {
		int powerSum = 0;
		try {
			File inputFile = new File(fileName);
			Scanner fileReader = new Scanner(inputFile);
			
			while (fileReader.hasNextLine()) {
				String game = fileReader.nextLine();
				HashMap<String, Integer> parsedGame = parseGame(game);
				int gamePower = 1;
				for (Integer numberOfCubes : parsedGame.values()) {
					gamePower *= numberOfCubes;
				}
				powerSum += gamePower;
			}
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			System.out.printf("File with name %s not found", fileName);
		}
		return powerSum;
	}
	
}
