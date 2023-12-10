package adventOfCode2023;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day3Redone {

	public static void main(String args[]) {
		// Pseudo code
		/*
		 * Read the entire file
		 * Iterate through the rows.
		 * Pt 1. When a number is encountered, check to see if any of its "neighbors"
		 * 	are symbols. If so, add it to the sum.
		 * Pt 2. When a '*' is encountered, check to see if any of its "neighbors" are digits.
		 * 	If there are two, extract them and multiply them together for the gear ratio.
		 */
		
		Integer p1Soln = part1Solution("inputFiles/adventOfCodeDay3Pt1.txt");
		System.out.printf("The sum of all the part numbers is %d.", p1Soln);
		
		
		System.out.println(part2Solution("inputFiles/adventOfCodeDay3Pt1.txt"));
		
	}

	/**
	 * Provide the solution to Part 1 of Advent of Code Day 3 (2023) give a fileName
	 * to gather the input.
	 * @param fileName The file to use as input.
	 * @return The sum of all the valid part numbers (numbers adjacent to a symbol).
	 */
	public static Integer part1Solution(String fileName) {
		Integer partNumberSum = 0;
		
		ArrayList<String> rows = readFile(fileName);
		
		for (int i = 0; i < rows.size(); i++) {
			String row = rows.get(i);
			
			for (int j = 0; j < row.length(); j++) {
				// Check before, after, above, and below (with diagonals)
				if (Character.isDigit(row.charAt(j))) {
					Boolean isPartNumber = false;
					Integer extractedNum = extractNumberFromRow(row, j);
					Integer digitLength = extractedNum.toString().length();
					
					// Before
					Boolean symbolBefore = j - 1 >= 0 && row.charAt(j - 1) != '.' && !Character.isDigit(row.charAt(j - 1));
					
					// After
					Boolean symbolAfter = j + digitLength < row.length() && row.charAt(j + digitLength) != '.' && !Character.isDigit(row.charAt(j + digitLength));
					
					// Below 
					Boolean symbolBelow = false;
					if (i + 1 != rows.size()) {
						String rowBelow = rows.get(i + 1);
						for (int k = j - 1; k < Math.min(j + digitLength + 1, row.length()); k++) {
							if (k < 0) {
								continue;
							}
							if (rowBelow.charAt(k) != '.' && !Character.isDigit(rowBelow.charAt(k))) {
								symbolBelow = true;
								break;
							}
						}
					}
					
					// Above 
					Boolean symbolAbove = false;
					if (i > 0) {
						String rowAbove = rows.get(i - 1);
						for (int k = j - 1; k < Math.min(j + digitLength + 1, row.length()); k++) {
							if (k < 0) {
								continue;
							}
							if (rowAbove.charAt(k) != '.' && !Character.isDigit(rowAbove.charAt(k))) {
								symbolAbove = true;
								break;
							}
						}
					}
					
					isPartNumber = symbolBefore || symbolAfter || symbolBelow || symbolAbove;
					
					if (isPartNumber) {
						partNumberSum += extractedNum;
					}
				
					j += digitLength;
				}
				
			}
		}
		
		return partNumberSum;
	}
	/**
	 * Provide the solution to Part 2 of Advent of Code Day 3 (2023) give a fileName
	 * to gather the input.
	 * @param fileName The file to use as input.
	 * @return The sum of all the valid part numbers (numbers adjacent to a symbol).
	 */
	public static Integer part2Solution(String fileName) {
		Integer gearRatioSum = 0;
		
		ArrayList<String> rows = readFile(fileName);
		
		for (int i = 0; i < rows.size(); i++) {
			String row = rows.get(i);
			
			for (int j = 0; j < row.length(); j++) {
				// Check before, after, above, and below (with diagonals)
				if (row.charAt(j) == '*') {
					ArrayList<Integer> adjacentNumbers = new ArrayList<Integer>();
					
					// Before
					Boolean numberBefore = j - 1 >= 0 && Character.isDigit(row.charAt(j - 1));
					if (numberBefore) {
						adjacentNumbers.add(extractNumberFromRow(row, j - 1));
					}
					// After
					Boolean numberAfter = j + 1 < row.length() && Character.isDigit(row.charAt(j + 1));
					if (numberAfter) {
						adjacentNumbers.add(extractNumberFromRow(row, j + 1));
					}
					
					// Below 
					if (i + 1 < rows.size()) {
						String rowBelow = rows.get(i + 1);
						for (int k = j - 1; k < Math.min(j + 2, row.length()); k++) {
							if (k < 0) {
								continue;
							}
							if (Character.isDigit(rowBelow.charAt(k))) {
								Integer number = extractNumberFromRow(rowBelow, k);
								adjacentNumbers.add(number);
								do {
									k += 1;
								} while (k < row.length() && Character.isDigit(rowBelow.charAt(k))) ;
							}
						}
					}
					
					// Above 
					if (i > 0) {
						String rowAbove = rows.get(i - 1);
						for (int k = j - 1; k < Math.min(j + 2, row.length()); k++) {
							if (k < 0) {
								continue;
							}
							
							if (Character.isDigit(rowAbove.charAt(k))) {
								Integer number = extractNumberFromRow(rowAbove, k);

								adjacentNumbers.add(number);
								do {
									k += 1;
								} while (k < row.length() && Character.isDigit(rowAbove.charAt(k))) ;
							}

						}
					}
					
					if (adjacentNumbers.size() == 2) {
//						System.out.println(adjacentNumbers);
						System.out.println(adjacentNumbers.get(0) * adjacentNumbers.get(1));
						gearRatioSum += adjacentNumbers.get(0) * adjacentNumbers.get(1);
					}
				}
				
			}
		}
		
		return gearRatioSum;
	}
	
	/**
	 * Read in the entire given file.
	 * @param fileName The file to use as input.
	 * @return The file as an array of string rows.
	 */
	public static ArrayList<String> readFile(String fileName) {
		ArrayList<String> rows = new ArrayList<String>();  
		try {
			File input = new File(fileName);
			Scanner fileReader = new Scanner(input);
		
			while (fileReader.hasNextLine()) {
				rows.add(fileReader.nextLine());
			}
			
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			System.out.printf("File %s not found", fileName);
			e.printStackTrace();
		}
		return rows;
	}
	
	/**
	 * Extract a number from a line of text that has a digit at the given index. This
	 * doesn't have to be the starting index for the number for the function to work.
	 * @param row The row from which to extract the number.
	 * @param knownIndex A known index for one of the digits of the number.
	 * @return The integer from row that has a digit at index knownIndex.
	 */
	public static Integer extractNumberFromRow(String row, Integer knownIndex) {
		Integer startingIndex = knownIndex;
		Integer endingIndex = knownIndex;
		
		Integer indexSearcher = knownIndex - 1;
		while (indexSearcher >= 0 && Character.isDigit(row.charAt(indexSearcher))) {
			startingIndex = indexSearcher;
			indexSearcher--;
		}
		
		indexSearcher = knownIndex + 1;
		while (indexSearcher < row.length() && Character.isDigit(row.charAt(indexSearcher))) {
			endingIndex = indexSearcher;
			indexSearcher++;
		}
		
		return Integer.parseInt(row.substring(startingIndex, endingIndex + 1));
	}
	
	
	
}
