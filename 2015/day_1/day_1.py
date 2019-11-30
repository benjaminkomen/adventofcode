# Name:      day_1
# Created:   October 7, 2016
# Blurb:     Calculate floor number from up/down input, see http://adventofcode.com/day/1

# input
myfile = open("input.txt", "r")
input = myfile.read()
characters = list(input)  # split input by every character
counter = 0  # we start on floor 0
position = 1  # first character position of the list is 1
base_pos = []  # list with character positions when basement is reached

# loop through the data
for character in characters:
    if character == '(':  # go up one floor
        counter = counter + 1
    else:  # go down one floor
        counter = counter - 1
    if counter == -1:  # if basement is reached, add character position to list
        base_pos.append(position)
    position = position + 1

print('The answer is: ', counter, ' which is the floor to which Santa is taken.')

print('The position of the character that causes Santa to reach the basement is', base_pos[0])
