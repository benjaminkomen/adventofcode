# Name:      day_2
# Created:   October 7, 2016
# Blurb:     Do something, see http://adventofcode.com/day/2

# input
myfile = open("input.txt", "r")
input = myfile.read()
lines = input.split('\n')
total_wrapping = 0
total_ribbon = 0

# loop through the data
for line in lines:
    dimensions = line.split('x')  # split every line in 3 different numbers
    length = int(dimensions[0])
    width = int(dimensions[1])
    height = int(dimensions[2])
    side_1 = length * width
    side_2 = width * height
    side_3 = height * length
    wrapping = 2 * side_1 + 2 * side_2 + 2 * side_3 + min(side_1, side_2, side_3)
    total_wrapping = total_wrapping + wrapping  # add wrapping to counter with total amount of wrapping
    sides = []
    sides.append(length)
    sides.append(width)
    sides.append(height)
    sides.sort()  # put the dimension of the 3 sides in an array and sort ascending
    ribbon = sides[0] + sides[0] + sides[1] + sides[1] + sides[0] * sides[1] * sides[2]
    total_ribbon = total_ribbon + ribbon  # add ribbon to counter with total amount of ribbon

print('The total amount of wrapping required is', total_wrapping)
print('The total amount of ribbon required is', total_ribbon)
