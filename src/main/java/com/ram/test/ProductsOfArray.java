package com.ram.test;

import java.util.Arrays;

/**
 * Assumptions:
 * <p>
 * 1. The values in the input integer array are not big enough to cause integer
 * overflow when all of them are multiplied together.
 * <p>
 * 2.
 * 
 * @author Ram
 * 
 */
public class ProductsOfArray {

	public static void main(String[] args) {
		int[] input = new int[] { 4, 3, 2, 8 };
		runTestFor(input);

		input = new int[] { -4, 3, -2, 8 };
		runTestFor(input);

		input = new int[] { 0, 3, 2, 8 };
		runTestFor(input);

		input = new int[] { 0, 3, 2, 8, 0 };
		runTestFor(input);

		input = new int[] { 0, 0, 0, 0 };
		runTestFor(input);
	}

	public static void runTestFor(int[] input) {
		String message = "input array: " + Arrays.toString(input);
		int[] output = ProductsOfArray.getProductOfArrayUsingDivision(input);
		message += "\toutput Products array: " + Arrays.toString(output);
		System.out.println(message);
	}

	/**
	 * This approach uses the approach of multiplying all the integers in the
	 * input array and then dividing it by the number at a give index, to get
	 * the product array output at that index.
	 * <p>
	 * Space complexity = O(1), basically the results are stored back into the
	 * input array. In some functional programming styles, where immutability is
	 * practiced, it may not be liked. In that case, we need to return another
	 * array but it will increase the space complexity to O(n).
	 * <p>
	 * Time Complexity = O(n), we need scan the array once to get the product of
	 * all elements, and then another scan where we set the product ouput for
	 * each index. So overall time complexity = O(n) + O(n) which is still O(n)
	 * 
	 * @param input
	 *            an array of integers
	 * @return an array of integers, where each element is the product all the
	 *         integers in the input array except the one at that index.
	 */
	public static int[] getProductOfArrayUsingDivision(int[] input) {
		int totalproduct = 1;
		// take the product of all the numbers, time complexity = O(n)
		int oneZeroIndex = -1;
		boolean twoZeros = false;
		for (int i = 0; i < input.length; i++) {
			if (input[i] == 0) {
				if (oneZeroIndex >= 0) {
					twoZeros = true;
					totalproduct = 0;
					break;
				} else {
					oneZeroIndex = i;
					continue;
				}
			}
			totalproduct *= input[i];
		}

		// are there two zeros in the input array?
		if (twoZeros) {
			// no point product array would be all zeros
			for (int i = 0; i < input.length; i++) {
				input[i] = 0;
			}
			return input;
		}

		// Is there one and only one zero in the input array
		if (oneZeroIndex >= 0) {
			// one element in the output array will be non-zero, and the rest
			// are all zeros
			for (int i = 0; i < input.length; i++) {
				if (oneZeroIndex == i) {
					input[i] = totalproduct;
				} else {
					input[i] = 0;
				}
			}
			return input;
		}
		// again divide each product with the number at the given index, so Time
		// complexity = O(n)
		for (int i = 0; i < input.length; i++) {
			input[i] = totalproduct / input[i];
		}

		return input;
	}

}
