package adventOfCode2023;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class Day3 {

	public static void main(String[] args) {
		part1Solution();
		part2Solution();
	
	}
		

	/**
	 * Convert a row into a map from "digitIndices" and "symbolIndices" that hold ArrayLists of the indices
	 * for the numbers (start and end) and indices of symbols.
	 * @param row The row consisting of '.', numbers, and symbols.
	 * @return The map to get the digit indices and symbol indices for.
	 */
	private static HashMap<String, ArrayList<Integer>> mapRowToIndicies(String row) {
		HashMap<String, ArrayList<Integer>> symbolMap = new HashMap<String, ArrayList<Integer>>();
		ArrayList<Integer> digitIndices = new ArrayList<Integer>();
		ArrayList<Integer> symbolIndices = new ArrayList<Integer>();
		
		for (int i = 0; i < row.length(); i++) {
			char c = row.charAt(i);
			// Add digit indices in pairs of 2; start to end.
			if (Character.isDigit(c)) {
				digitIndices.add(i);
				while (Character.isDigit(c) && i < row.length()) {
					if (i + 1 != row.length()) {
						c = row.charAt(i + 1);
					}
					i += 1;
				}
				// Undo overshooting by 1
				i -= 1;
				digitIndices.add(i);
			} else if (c != '.') {
				symbolIndices.add(i);
			}
		}
		
		symbolMap.put("digitIndices", digitIndices);
		symbolMap.put("symbolIndices", symbolIndices);
		
		return symbolMap;
	}

	/**
	 * Return a number from testRow if it is adjacent to a symbol (non '.').
	 * @param testRow The row from which to potentially extract the number.
	 * @param numberStartIndex The starting index for the number in testRow.
	 * @param numberEndIndex The ending index for the number in testRow.
	 * @param symbolIndices The indices of the symbols in the row being tested against.
	 * @return The number if it is considered a "part number" and -1 if not found.
	 */
	private static Integer extractPartNumber(String testRow, int numberStartIndex, int numberEndIndex, ArrayList<Integer> symbolIndices) {
		for (int symbolIndex : symbolIndices) {
			if (symbolIndex >= numberStartIndex - 1 && symbolIndex <= numberEndIndex + 1) {
				return Integer.parseInt(testRow.substring(numberStartIndex, numberEndIndex + 1));
			}
		}
		return -1;
	}
	
	
	/**
	 * Provides the solution to the first part of Day 3 of Advent of Code 2023.
	 */
	private static void part1Solution() {
		try {
			ArrayList<Integer> partNumbers = new ArrayList<Integer>();
			
			File input = new File("inputFiles/adventOfCodeDay3Pt1.txt");
			Scanner fileReader = new Scanner(input);
			
			String testRow = fileReader.nextLine();
			String nextRow = fileReader.nextLine();
			
			HashMap<String, ArrayList<Integer>> testRowMap = mapRowToIndicies(testRow);
			HashMap<String, ArrayList<Integer>> nextRowMap = mapRowToIndicies(nextRow);
			
			
			ArrayList<Integer> testRowDigitIndices = testRowMap.get("digitIndices");
			
			// First line only checks the second line.
			for (int i = 0; i < testRowDigitIndices.size(); i += 2) {
				int testRowNum = extractPartNumber(testRow, testRowDigitIndices.get(i), testRowDigitIndices.get(i + 1), testRowMap.get("symbolIndices"));
				int nextRowNum = extractPartNumber(testRow, testRowDigitIndices.get(i), testRowDigitIndices.get(i + 1), nextRowMap.get("symbolIndices"));
				
				if (testRowNum != -1) {
					partNumbers.add(testRowNum);
				}
				else if (nextRowNum != -1)  {
					partNumbers.add(nextRowNum);
				}
			}
		
			HashMap<String, ArrayList<Integer>> prevRowMap = testRowMap;

			while (fileReader.hasNextLine()) {
				testRow = nextRow;
				testRowMap = nextRowMap;
				nextRow = fileReader.nextLine();
				nextRowMap = mapRowToIndicies(nextRow);
				
				testRowDigitIndices = testRowMap.get("digitIndices");
				
				for (int i = 0; i < testRowDigitIndices.size(); i += 2) {

					int prevRowNum = extractPartNumber(testRow, testRowDigitIndices.get(i), testRowDigitIndices.get(i + 1), prevRowMap.get("symbolIndices"));
					int testRowNum = extractPartNumber(testRow, testRowDigitIndices.get(i), testRowDigitIndices.get(i + 1), testRowMap.get("symbolIndices"));
					int nextRowNum = extractPartNumber(testRow, testRowDigitIndices.get(i), testRowDigitIndices.get(i + 1), nextRowMap.get("symbolIndices"));
					
					if (testRowNum != -1) {
						partNumbers.add(testRowNum);
					}
					else if (nextRowNum != -1)  {
						partNumbers.add(nextRowNum);
					}
					else if (prevRowNum != -1) {
						partNumbers.add(prevRowNum);
					}
				}
			
				prevRowMap = testRowMap;
			}
			
			// Last line only uses the penultimate line.
			testRow = nextRow;
			testRowMap = nextRowMap;
			testRowDigitIndices = testRowMap.get("digitIndices");
			for (int i = 0; i < testRowDigitIndices.size(); i += 2) {

					int prevRowNum = extractPartNumber(testRow, testRowDigitIndices.get(i), testRowDigitIndices.get(i + 1), prevRowMap.get("symbolIndices"));
					int testRowNum = extractPartNumber(testRow, testRowDigitIndices.get(i), testRowDigitIndices.get(i + 1), testRowMap.get("symbolIndices"));
					
					if (testRowNum != -1) {
						partNumbers.add(testRowNum);
					}
					else if (prevRowNum != -1) {
						partNumbers.add(prevRowNum);
					}
			}
			
			Integer finalSum = 0;
			for (Integer i : partNumbers) {
				finalSum += i;
			}
			
			System.out.printf("The sum of all the part numbers is %d.\n", finalSum);
			
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * Convert a row into a map from "digitIndices" and "symbolIndices" that hold ArrayLists of the indices
	 * for the numbers (start and end) and indices of '*'.
	 * @param row The row consisting of '.', numbers, and symbols.
	 * @return The map to get the digit indices and * symbol indices for.
	 */
	private static HashMap<String, ArrayList<Integer>> mapRowToIndiciesStar(String row) {
		HashMap<String, ArrayList<Integer>> symbolMap = new HashMap<String, ArrayList<Integer>>();
		ArrayList<Integer> digitIndices = new ArrayList<Integer>();
		ArrayList<Integer> symbolIndices = new ArrayList<Integer>();
		
		for (int i = 0; i < row.length(); i++) {
			char c = row.charAt(i);
			// Add digit indices in pairs of 2; start to end.
			if (Character.isDigit(c)) {
				digitIndices.add(i);
				while (Character.isDigit(c) && i < row.length()) {
					if (i + 1 != row.length()) {
						c = row.charAt(i + 1);
					}
					i += 1;
				}
				// Undo overshooting by 1
				i -= 1;
				digitIndices.add(i);
			} else if (c == '*') {
				symbolIndices.add(i);
			}
		}
		
		symbolMap.put("digitIndices", digitIndices);
		symbolMap.put("symbolIndices", symbolIndices);
		
		return symbolMap;
	}
	
	
	/**
	 * Extract the gear ratio from a line if there are two numbers next to a '*'.
	 * @param testRow The row from which to potentially extract the number.
	 * @param numberStartIndex The starting index for the number in testRow.
	 * @param numberEndIndex The ending index for the number in testRow.
	 * @param symbolIndices The indices of the symbols in the row being tested against.
	 * @return The number if it is considered a "part number" and -1 if not found.
	 */
	private static Integer extractGearRatio(String testRow, int numberStartIndex, int numberEndIndex, ArrayList<Integer> symbolIndices) {
		for (int symbolIndex : symbolIndices) {
			if (symbolIndex >= numberStartIndex - 1 && symbolIndex <= numberEndIndex + 1) {
				return Integer.parseInt(testRow.substring(numberStartIndex, numberEndIndex + 1));
			}
		}
		return -1;
	}
	
	/**
	 * Provides the solution to the first part of Day 3 of Advent of Code 2023.
	 */
	private static void part2Solution() {
		try {
			ArrayList<Integer> partNumbers = new ArrayList<Integer>();
			
			File input = new File("inputFiles/adventOfCodeDay3test.txt");
			Scanner fileReader = new Scanner(input);
			
			String testRow = fileReader.nextLine();
			String nextRow = fileReader.nextLine();
			
			HashMap<String, ArrayList<Integer>> testRowMap = mapRowToIndiciesStar(testRow);
			HashMap<String, ArrayList<Integer>> nextRowMap = mapRowToIndiciesStar(nextRow);
			
			
			ArrayList<Integer> testRowDigitIndices = testRowMap.get("digitIndices");
			ArrayList<Integer> nextRowDigitIndices = nextRowMap.get("digitIndices");
			
			// First line only checks the second line.
			for (int i = 0; i < testRowDigitIndices.size(); i += 2) {
				int testRowNum = extractPartNumber(testRow, testRowDigitIndices.get(i), testRowDigitIndices.get(i + 1), testRowMap.get("symbolIndices"));
				int nextRowNum = extractPartNumber(nextRow, nextRowDigitIndices.get(i), nextRowDigitIndices.get(i + 1), nextRowMap.get("symbolIndices"));
				
				if (testRowNum != -1 && nextRowNum != -1) {
					partNumbers.add(testRowNum * nextRowNum);
				}
			}
		
			HashMap<String, ArrayList<Integer>> prevRowMap = testRowMap;
			String prevRow = testRow;
			
			ArrayList<Integer> prevRowDigitIndices = prevRowMap.get("digitIndices");

			while (fileReader.hasNextLine()) {
				testRow = nextRow;
				testRowMap = nextRowMap;
				nextRow = fileReader.nextLine();
				nextRowMap = mapRowToIndiciesStar(nextRow);
				
				testRowDigitIndices = testRowMap.get("digitIndices");
				nextRowDigitIndices = nextRowMap.get("digitIndices");
				
				for (int i = 0; i < testRowDigitIndices.size(); i += 2) {

					int prevRowNum = extractPartNumber(prevRow, prevRowDigitIndices.get(i), prevRowDigitIndices.get(i + 1), prevRowMap.get("symbolIndices"));
					int testRowNum = extractPartNumber(testRow, testRowDigitIndices.get(i), testRowDigitIndices.get(i + 1), testRowMap.get("symbolIndices"));
					int nextRowNum = extractPartNumber(nextRow, nextRowDigitIndices.get(i), nextRowDigitIndices.get(i + 1), nextRowMap.get("symbolIndices"));
					
					if (testRowNum != -1 && nextRowNum != -1) {
						partNumbers.add(testRowNum * nextRowNum);
					}
					else if (nextRowNum != -1 && prevRowNum != -1)  {
						partNumbers.add(nextRowNum * prevRowNum);
					}
					else if (prevRowNum != -1 && testRowNum != -1) {
						partNumbers.add(prevRowNum * testRowNum);
					}
				}
			
				prevRowMap = testRowMap;
				prevRow = testRow;
				prevRowDigitIndices = prevRowMap.get("digitIndices");
			}
			
			// Last line only uses the penultimate line.
			testRow = nextRow;
			testRowMap = nextRowMap;
			testRowDigitIndices = testRowMap.get("digitIndices");
			for (int i = 0; i < testRowDigitIndices.size(); i += 2) {

					int prevRowNum = extractPartNumber(prevRow, prevRowDigitIndices.get(i), prevRowDigitIndices.get(i + 1), prevRowMap.get("symbolIndices"));
					int testRowNum = extractPartNumber(testRow, testRowDigitIndices.get(i), testRowDigitIndices.get(i + 1), testRowMap.get("symbolIndices"));
					
					if (prevRowNum != -1 && testRowNum != -1) {
						partNumbers.add(prevRowNum * testRowNum);
					}
			}
			
			Integer finalSum = 0;
			for (Integer i : partNumbers) {
				finalSum += i;
			}
			
			System.out.printf("The sum of all the part numbers is %d.", finalSum);
			
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		}
		
	}
}
