/*
 * @name: Day 1: No Time for a Taxicab
 * @author: Benjamin Komen
 * @url: http://adventofcode.com/2016/day/1
 */

package com.benjamin;

import com.benjamin.repositories.InputRepository;

import java.util.ArrayList;
import java.util.List;

public class Day1 {
    public static String[] input_array;
    public static List<Integer> first_twice = new ArrayList<>();
    private InputRepository inputRepository = new InputRepository();

    public static void main(String[] args) throws Exception {
        Day1 m = new Day1();
        String input = m.inputRepository.getInput(1);
        input_array = input.split(", ");
        int distance = m.calculateDistance(input_array);

        System.out.println("Part 1: The Easter Bunny HQ is " + distance + " blocks away.");
        System.out.println("Part 2: The first location you visit twice is " + first_twice.get(0) + " blocks away.");
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
            String new_direction = array_element.substring(0, 1);
            int blocks = Integer.parseInt(array_element.substring(1));

            // move direction 90 degrees clockwise if R or 90 degrees counter-clockwise if L
            if (new_direction.equals("R")) {
                current_direction += 90;
            } else if (new_direction.equals("L")) {
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
            switch (current_direction) {
                case 0:
                    current_y += blocks;
                    break; // moving north
                case 90:
                    current_x += blocks;
                    break; // moving east
                case 180:
                    current_y -= blocks;
                    break; // moving south
                case 270:
                    current_x -= blocks;
                    break; // moving west
                default:
                    throw new Exception();
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
            System.out.println(i + " Parsing instruction '" + array_element + "' and moving to x:" + current_x + ",y:" + current_y);
            i++;
        }

        int distance = Math.abs(current_x - start_x) + Math.abs(current_y - start_y);
        return distance;
    }
}
