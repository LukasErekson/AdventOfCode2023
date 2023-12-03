package adventOfCode2023;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Day1 {
	private static HashMap<String, Character> writtenNumbers = new HashMap<String, Character>();
	
	public static void main(String[] args) {
		writtenNumbers.put("zero", '0');
		writtenNumbers.put("one", '1');
		writtenNumbers.put("two", '2');
		writtenNumbers.put("three", '3');
		writtenNumbers.put("four", '4');
		writtenNumbers.put("five", '5');
		writtenNumbers.put("six", '6');
		writtenNumbers.put("seven", '7');
		writtenNumbers.put("eight", '8');
		writtenNumbers.put("nine", '9');	

		ArrayList<Integer> parsedValues = parseCalibrationFile("adventOfCodeDay1.txt");
		
		
		Integer calibrationSum = 0;
		for (Integer calibrationNum : parsedValues) {
			calibrationSum += calibrationNum;
		}
		
		System.out.println(calibrationSum);
		
		
	}

	/**
	 * Processes one line of the "Calibration document" to extract the first and last
	 * digit to then combine them into a single integer for the calibration value.
	 * @param line One line of the "Calibration Document" containing at least 1 digit
	 * and several other characters.
	 * @return The calibration number parse from the line.
	 */
	private static int parseCalibrationValueFromLine(String line) {
		char firstDigit = '\000';
		int firstDigitIndex = -1;
		char lastDigit = '\000';
		int lastDigitIndex = -1;
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (Character.isDigit(c)) {
				// First time we see a digit
				if (firstDigit == '\000') {
					firstDigit = c;
					firstDigitIndex = i;
					lastDigit = c;
					lastDigitIndex = i;
				}
				else {
					lastDigit = c;
					lastDigitIndex = i;
				}
			}
		}
		
		for (String writtenNum : writtenNumbers.keySet()) {
			int writtenNumIndex = line.indexOf(writtenNum);
			if (writtenNumIndex > -1) {
				if (writtenNumIndex < firstDigitIndex || firstDigitIndex == -1) {
					firstDigitIndex = writtenNumIndex;
					firstDigit = writtenNumbers.get(writtenNum);
				}
			}
			
			writtenNumIndex = line.lastIndexOf(writtenNum);
			if (writtenNumIndex > lastDigitIndex) {
				lastDigitIndex = writtenNumIndex;
				lastDigit = writtenNumbers.get(writtenNum);
			}
		}
		
		int calibrationValue = Integer.parseInt(String.format("%c%c", firstDigit, lastDigit));
		
		return calibrationValue;
	}
	
	/**
	 * Create an array of parsed number values from a given file name.
	 * @param fileName The name of the file from which to get the calibration
	 * numbers.
	 * @return ArrayList of parsed integer values from the given file.
	 */
	private static ArrayList<Integer> parseCalibrationFile(String fileName) {
		ArrayList<Integer> calibrationNumbers = new ArrayList<Integer>();
		
		try {

			File inputFile = new File(fileName);
			Scanner fileReader = new Scanner(inputFile);
			
			while (fileReader.hasNextLine()) {
				String calibrationLine = fileReader.nextLine();

				calibrationNumbers.add(parseCalibrationValueFromLine(calibrationLine));
			}

			fileReader.close();

		} catch (FileNotFoundException e) {
			System.out.println(String.format("File %s not found.", fileName));
		}
		
		return calibrationNumbers;
	}
	
}
