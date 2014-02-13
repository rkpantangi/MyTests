package com.ram.test;

import java.util.BitSet;

/**
 * Given a 9x9 matrix representing a sudoku puzzle, This class checks if it is
 * valid or not. Assumptions:
 * <p>
 * 1.It is a 9x9 matrix with each value >=1 and <=9.
 * 
 * @author Ram
 * 
 */
public class SudokuValidator {

	public static void main(String[] args) {
		// valid puzzle
		int[][] puzzle1 = {
				{ 5, 3, 4, 6, 7, 8, 9, 1, 2 },
				{ 6, 7, 2, 1, 9, 5, 3, 4, 8 },
				{ 1, 9, 8, 3, 4, 2, 5, 6, 7 },
				{ 8, 5, 9, 7, 6, 1, 4, 2, 3 },
				{ 4, 2, 6, 8, 5, 3, 7, 9, 1 },
				{ 7, 1, 3, 9, 2, 4, 8, 5, 6 },
				{ 9, 6, 1, 5, 3, 7, 2, 8, 4 },
				{ 2, 8, 7, 4, 1, 9, 6, 3, 5 },
				{ 3, 4, 5, 2, 8, 6, 1, 7, 9 } };
		System.out.println("Sudoku 1: " + isSudokuValid(puzzle1));

		// Row validation failure
		// Invalid Puzzle due to an element at (1,2) and (1, 8) are same
		int[][] puzzle2 = {
				{ 5, 3, 4, 6, 7, 8, 9, 3, 2 },
				{ 6, 7, 2, 1, 9, 5, 3, 4, 8 },
				{ 1, 9, 8, 3, 4, 2, 5, 6, 7 },
				{ 8, 5, 9, 7, 6, 1, 4, 2, 3 },
				{ 4, 2, 6, 8, 5, 3, 7, 9, 1 },
				{ 7, 1, 3, 9, 2, 4, 8, 5, 6 },
				{ 9, 6, 1, 5, 3, 7, 2, 8, 4 },
				{ 2, 8, 7, 4, 1, 9, 6, 3, 5 },
				{ 3, 4, 5, 2, 8, 6, 1, 7, 9 } };
		System.out.println("Sudoku 2: " + isSudokuValid(puzzle2));

		// Column Validation failure
		// Invalid Puzzle due to an element at (4,2) and (8, 2) are same
		int[][] puzzle3 = {
				{ 5, 3, 4, 6, 7, 8, 9, 1, 2 },
				{ 6, 7, 2, 1, 9, 5, 3, 4, 8 },
				{ 1, 9, 8, 3, 4, 2, 5, 6, 7 },
				{ 8, 5, 9, 7, 6, 1, 4, 2, 3 },
				{ 4, 2, 6, 8, 5, 3, 7, 9, 1 },
				{ 7, 1, 3, 9, 2, 4, 8, 5, 6 },
				{ 9, 6, 1, 5, 3, 7, 2, 8, 4 },
				{ 2, 5, 7, 4, 1, 9, 6, 3, 8 },
				{ 3, 4, 5, 2, 8, 6, 1, 7, 9 } };
		System.out.println("Sudoku 3: " + isSudokuValid(puzzle3));

		// 3x3 sub matrix validation failure
		// element 9 repeated in 3x3 matrix starting (0, 3)
		int[][] puzzle4 = {
				{ 8, 4, 7, 5, 2, 6, 1, 9, 3 },
				{ 2, 5, 1, 3, 6, 4, 9, 8, 7 },
				{ 6, 3, 9, 7, 4, 5, 8, 2, 1 },
				{ 4, 7, 8, 1, 5, 3, 2, 6, 9 },
				{ 5, 1, 2, 9, 3, 7, 6, 4, 8 },
				{ 3, 9, 6, 8, 7, 1, 4, 5, 2 },
				{ 7, 8, 4, 2, 1, 9, 5, 3, 6 },
				{ 1, 2, 5, 6, 9, 8, 3, 7, 4 },
				{ 9, 6, 3, 4, 8, 2, 7, 1, 5 } };
		System.out.println("Sudoku 4: " + isSudokuValid(puzzle4));

		// Puzzle with invalid values
		int[][] puzzle5 = {
				{ 8, 4, 0, 1, 9, 3, 5, 2, 6 },
				{ 2, 5, 1, 9, 8, 7, 3, 6, 4 },
				{ 6, 3, 9, 8, 2, 1, 7, 4, 5 },
				{ 4, 7, 8, 2, 6, 9, 1, 5, 3 },
				{ 5, 1, 2, 6, 4, 8, 9, 3, 7 },
				{ 3, 9, 6, 4, 5, 2, 8, 7, 1 },
				{ 7, 8, 4, 5, 3, 6, 2, 1, 9 },
				{ 1, 2, 5, 3, 7, 4, 6, 9, 8 },
				{ 9, 6, 3, 7, 1, 5, 4, 8, 2 } };
		System.out.println("Sudoku 5: " + isSudokuValid(puzzle5));

		// Puzzle with less than 9 columns
		int[][] puzzle6 = {
				{ 8, 4, 0, 1, 9, 3, 5, 2 },
				{ 2, 5, 1, 9, 8, 7, 3, 6, 4 },
				{ 6, 3, 9, 8, 2, 1, 7, 4, 5 },
				{ 4, 7, 8, 2, 6, 9, 1, 5, 3 },
				{ 5, 1, 2, 6, 4, 8, 9, 3, 7 },
				{ 3, 9, 6, 4, 5, 2, 8, 7, 1 },
				{ 7, 8, 4, 5, 3, 6, 2, 1, 9 },
				{ 1, 2, 5, 3, 7, 4, 6, 9, 8 },
				{ 9, 6, 3, 7, 1, 5, 4, 8, 2 } };
		System.out.println("Sudoku 6: " + isSudokuValid(puzzle6));

		// Puzzle with less than 9 rows
		int[][] puzzle7 = {
				{ 8, 4, 0, 1, 9, 3, 5, 2, 6 },
				{ 2, 5, 1, 9, 8, 7, 3, 6, 4 },
				{ 6, 3, 9, 8, 2, 1, 7, 4, 5 },
				{ 4, 7, 8, 2, 6, 9, 1, 5, 3 },
				{ 5, 1, 2, 6, 4, 8, 9, 3, 7 },
				{ 3, 9, 6, 4, 5, 2, 8, 7, 1 },
				{ 7, 8, 4, 5, 3, 6, 2, 1, 9 },
				{ 9, 6, 3, 7, 1, 5, 4, 8, 2 } };
		System.out.println("Sudoku 7: " + isSudokuValid(puzzle7));

	}

	/**
	 * Takes a 9x9 sudoku matrix and validates it, in case if it is valid, it
	 * returns a result with a reason.
	 * 
	 * @param puzzle
	 *            9x9 sudoku matrix
	 * @return result of validation
	 */
	public static ValidationResult isSudokuValid(int[][] puzzle) {
		// basic boundary conditions
		if (puzzle == null)
			return ValidationResult.invalidResult("Null Puzzle");
		if (puzzle.length != 9)
			return ValidationResult.invalidResult("Puzzle has only "
					+ puzzle.length + " rows");

		// using BitSet to reduce the usage of memory
		// we will have one Set of BitSet of size 9 for each column. Since I
		// parse the matrix row wise (better cache locality!), we wouldn't know
		// if any element is repeated in a column until we start reading the
		// last row, so we need separate BitSet for each column.
		BitSet[] colSets = new BitSet[9];

		// Since I parse the matrix row-wise, we can re-use the BitSet and
		// clearing it is only one operation
		// data structure.
		BitSet rowSet = new BitSet(9);
		BitSet[][] setsFor3x3 = new BitSet[3][3];
		for (int i = 0; i < 9; i++) {
			if (puzzle[i].length != 9)
				return ValidationResult.invalidResult("Puzzle has only "
						+ puzzle[i].length + " columns on row: " + (i + 1));
			rowSet.clear();
			for (int j = 0; j < 9; j++) {
				if (colSets[j] == null) {
					colSets[j] = new BitSet(9);
				}
				// check row validity
				int elem = puzzle[i][j];
				if (elem < 1 || elem > 9)
					return ValidationResult.invalidResult("Element " + elem
							+ " at (" + (i + 1) + ", " + (j + 1)
							+ ") is invalid.");
				// check if the element was already seen before
				if (rowSet.get(elem) == true) {
					return ValidationResult.invalidResult("Element " + elem
							+ " is repeated on row: " + (i + 1));
				}
				// If not, track it
				rowSet.set(elem);

				// check column validity
				// check if the element was seen before on this column
				if (colSets[j].get(elem) == true) {
					return ValidationResult.invalidResult("Element " + elem
							+ " is repeated on Column: " + (j + 1));
				}
				colSets[j].set(elem);

				// check 3x3 validity
				int i3 = i / 3;
				int j3 = j / 3;
				if (setsFor3x3[i3][j3] == null) {
					setsFor3x3[i3][j3] = new BitSet(9);
				}
				if (setsFor3x3[i3][j3].get(elem) == true) {
					return ValidationResult
							.invalidResult("Element "
									+ elem
									+ " is repeated in the 3x3 matrix starting at ("
									+ ((i / 3) * 3 + 1) + ","
									+ ((j / 3) * 3 + 1) + ")");
				}
				setsFor3x3[i3][j3].set(elem);
			}
		}
		return ValidationResult.validResult();
	}

	/**
	 * Enriched ValidationResult to pass the reason, if it is invalid
	 * 
	 * @author Ram
	 * 
	 */
	private static class ValidationResult {
		private boolean valid = true;
		private String reason;

		private ValidationResult() {
		}

		public static ValidationResult validResult() {
			return new ValidationResult();
		}

		public static ValidationResult invalidResult(String reason) {
			ValidationResult result = new ValidationResult();
			result.valid = false;
			result.reason = reason;
			return result;
		}

		@Override
		public String toString() {
			if (valid)
				return "Valid";
			return "Invalid, Reason=" + reason;
		}

	}

}
