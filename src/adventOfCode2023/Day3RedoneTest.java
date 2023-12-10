package adventOfCode2023;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class Day3RedoneTest {

	@Test
	public void testPart1Solution() {
		Integer calculatedSolution = Day3Redone.part1Solution("inputFiles/adventOfCodeDay3test.txt");
		Integer expected = 4361;
		
		assertTrue(expected.equals(calculatedSolution));
	}
	
	@Test
	public void testPart2Solution() {
		Integer calculatedSolution = Day3Redone.part2Solution("inputFiles/adventOfCodeDay3test.txt");
		Integer expected = 467835;
		
		assertTrue(expected.equals(calculatedSolution));
	}

	@Test
	public void testPart2Solution2() {
		Integer calculatedSolution = Day3Redone.part2Solution("inputFiles/day3Test1.txt");
		Integer expected = 7;
		
		assertTrue(expected.equals(calculatedSolution));
	}

	@Test
	public void testReadFile() {
		ArrayList<String> result = Day3Redone.readFile("inputFiles/adventOfCodeDay3test.txt");
		String[] expected =  ("467..114..\n"
				+ "...*......\n"
				+ "..35..633.\n"
				+ "......#...\n"
				+ "617*......\n"
				+ ".....+.58.\n"
				+ "..592.....\n"
				+ "......755.\n"
				+ "...$.*....\n"
				+ ".664.598..").split("\n");
		
		assertTrue(result.size() == expected.length);
		
		for (int i = 0; i < result.size(); i++) {
			assertTrue(result.get(i).equals(expected[i]));
		}
		
	}

	@Test
	public void testExtractNumberFromRow() {
		String sample = "141....432..22";

		assertTrue(141 == Day3Redone.extractNumberFromRow(sample, 0));
		assertTrue(432 == Day3Redone.extractNumberFromRow(sample, 7));
		assertTrue(22 == Day3Redone.extractNumberFromRow(sample, 12));
	}
	
	@Test
	public void testExtractNumnerFromRowMiddle() {
		String sample = "141....432..22";

		assertTrue(141 == Day3Redone.extractNumberFromRow(sample, 1));
		assertTrue(432 == Day3Redone.extractNumberFromRow(sample, 8));
	}
	
	@Test
	public void testExtractNumberFromRowEnd() {
		String sample = "141....432..22";

		assertTrue(141 == Day3Redone.extractNumberFromRow(sample, 2));
		assertTrue(22 == Day3Redone.extractNumberFromRow(sample, 13));
		assertTrue(432 == Day3Redone.extractNumberFromRow(sample, 9));
	}

}
