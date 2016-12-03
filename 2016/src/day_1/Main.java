/*
 * @name: Day 1: No Time for a Taxicab
 * @author: Benjamin Komen
 * @url: http://adventofcode.com/2016/day/1
 */

package day_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static String[] input_array;
	public static ArrayList<Integer> first_twice = new ArrayList<>();

	public static void main(String[] args) throws Exception {
		Main m = new Main();
		input_array = m.readFile();
		int distance = m.calculateDistance(input_array);
		
		System.out.println("Part 1: The Easter Bunny HQ is " + distance + " blocks away.");
		System.out.println("Part 2: The first location you visit twice is " + first_twice.get(0) + " blocks away.");
	}
	
	// read the input file and return the input as an array
	public String[] readFile() {
		String[] return_array = null;
		try {
			BufferedReader input = new BufferedReader(new FileReader("day_1_input.txt"));
			String line;
			while ((line = input.readLine()) != null) {
				return_array = line.split(", ");
			}
		} catch(IOException e) {
			System.out.println("An error occured while loading the input file");
		}
		return return_array;
	}
	
	// calculate the distance from the instructions of the input_array
	public int calculateDistance(String[] input_array) throws Exception {
		
		// declare some local variables
		int start_x = 0;
		int start_y = 0;
		int current_x = 0;
		int current_y = 0;
		int current_direction = 0; // 0 = north, 1 = east, 2 = south, 3 = west
		int[][] coordinates = new int[200][2];
		int i = 1;
		
		// loop over input_array and update current position
		for (String array_element : input_array) {
			String new_direction = array_element.substring(0,1);
			int blocks = Integer.parseInt(array_element.substring(1));
			
			// move direction 90 degrees clockwise if R or 90 degrees counter-clockwise if L
			if(new_direction.equals("R")) {
				current_direction += 90;
			} else if(new_direction.equals("L")) {
				current_direction -= 90;
			} else {
				throw new Exception();
			}
			
			// make sure current_direction is between 0 and 360 degrees and not a multiple
			if (current_direction < 0) {
				current_direction += 360;
			} else if (current_direction >= 360) {
				current_direction -= 360;
			}
			
			// walk in the specified direction
			switch(current_direction) {
			case 0: current_y += blocks; break; // moving north
			case 90: current_x += blocks; break; // moving east
			case 180: current_y -= blocks; break; // moving south
			case 270: current_x -= blocks; break; // moving west
			default: throw new Exception();
			}
			
			// have we been here before?
			for (int j = 1; j < coordinates.length; j++) {
				// check if current elements of coordinates array matches current_x and current_y 
				if ((coordinates[j][0] == current_x) & (coordinates[j][1] == current_y)) {
					System.out.println("We have been here before at step " + j + " Current position is x:" + current_x + ",y:" + current_y);
					Integer distance = Math.abs(current_x - start_x) + Math.abs(current_y - start_y); 
					first_twice.add(distance);
				}
			}
			
			// add current coordinates to array
			coordinates[i][0] = current_x;
			coordinates[i][1] = current_y;
			
			// debug
			System.out.println(i + " Parsing instruction '" + array_element + "' and moving to x:" + current_x + ",y:" + current_y );
			i++;
		}
		
		int distance = Math.abs(current_x - start_x) + Math.abs(current_y - start_y);
		return distance;
	}
}
