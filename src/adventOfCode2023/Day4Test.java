package adventOfCode2023;

import static org.junit.Assert.*;

import org.junit.Test;

public class Day4Test {

	@Test
	public void testPart1() {
		Integer correctValue = 15268;
		Integer calculated = Day4.part1Solution("inputFiles/day4Input.txt");
		
		assertTrue(correctValue == calculated);
	}


	@Test
	public void testCalculateCardPoints() {
		String card = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53";
		Integer expected = 8;
		Integer calculated = Day4.calculateCardPoints(card);
		
		
		assertTrue(expected == calculated);
	}
	
	@Test
	public void testCalculateCardPointsOne() {
		String card = "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83";
		Integer expected = 1;
		Integer calculated = Day4.calculateCardPoints(card);
		
		assertTrue(expected == calculated);
	}
	
	@Test
	public void testCalculateCardPointsZero() {
		
		String card = "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11";
		Integer expected = 0;
		Integer calculated = Day4.calculateCardPoints(card);
		
		
		assertTrue(expected == calculated);
	}

}
