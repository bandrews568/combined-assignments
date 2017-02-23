package com.cooksys.ftd.assignments.control;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * The Fibonacci sequence is simply and recursively defined: the first two elements are `1`, and
 * every other element is equal to the sum of its two preceding elements. For example:
 * <p>
 * [1, 1] =>
 * [1, 1, 1 + 1]  => [1, 1, 2] =>
 * [1, 1, 2, 1 + 2] => [1, 1, 2, 3] =>
 * [1, 1, 2, 3, 2 + 3] => [1, 1, 2, 3, 5] =>
 * ...etc
 */
public class Fibonacci {

	
    /**
     * Calculates the value in the Fibonacci sequence at a given index. For example,
     * `atIndex(0)` and `atIndex(1)` should return `1`, because the first two elements of the
     * sequence are both `1`.
     *
     * @param i the index of the element to calculate
     * @return the calculated element
     * @throws IllegalArgumentException if the given index is less than zero
     */
    public static int atIndex(int i) throws IllegalArgumentException {
    	
    	if (i == -1) {
    		throw new IllegalArgumentException();
    	}
    	
    	int[] array = fibonacci(100000); 
    	return array[i];
    	
    	
    	
    	
    }
    

    /**
     * Calculates a slice of the fibonacci sequence, starting from a given start index (inclusive) and
     * ending at a given end index (exclusive).
     *
     * @param start the starting index of the slice (inclusive)
     * @param end   the ending index of the slice(exclusive)
     * @return the calculated slice as an array of int elements
     * @throws IllegalArgumentException if either the given start or end is negative, or if the
     *                                  given end is less than the given start
     */
    public static int[] slice(int start, int end) throws IllegalArgumentException {
    	
    	if (start == -1 || end == -1) {
    		throw new IllegalArgumentException();
    	}
    	
    	if (end < start) {
    		throw new IllegalArgumentException();
    	}
    	
        int[] array = fibonacci(100000);
        int[] sliceOfArray = new int[end - start];
        
        int index = 0;
        
        for (int i = start; i < end; i++) {
        	sliceOfArray[index] = array[i];
        	index++;
        }
        return sliceOfArray;
        
    }

    /**
     * Calculates the beginning of the fibonacci sequence, up to a given count.
     *
     * @param count the number of elements to calculate
     * @return the beginning of the fibonacci sequence, up to the given count, as an array of int elements
     * @throws IllegalArgumentException if the given count is negative
     */
    public static int[] fibonacci(int count) throws IllegalArgumentException {
    	
    	if (count < 0) {
    		throw new IllegalArgumentException();
    	}
    	
    	int[] numbers;    	
    	if (count == 0) {
    		numbers = new int[]{};
    		return numbers;
    	}
    	
    	numbers = new int[count];
    	System.out.println(count);
    	numbers[0] = 1;
    	
    	if (count != 1) {
    		numbers[1] = 1;
    	}
    	
    	
    	
    	   	
    	for (int i = 2; i < count; i++) {
            numbers[i] = numbers[i - 1] + numbers[i - 2];
        }
    	
    	return numbers;        
    }
}
