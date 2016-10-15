# Name:      day_3
# Created:   October 14, 2016
# Blurb:     Calculate how many houses receive gifts, see http://adventofcode.com/2015/day/3

#input
myfile = open ("input.txt", "r")
input = myfile.read()
characters = list(input)    # split input by every character
current_coords = [0, 0]     # current coordinates, starts at beginning point
visited_coords = []         # a list of lists with visisted coordinates
visited_coords.append([0, 0])  # first coordinates visisted is the starting point 

# define a function to get unique values from a list
def uniqify(seq): 
   # order preserving
   checked = []
   for e in seq:
       if e not in checked:
           checked.append(e)
   return checked

# loop through the data
for character in characters:
    if character == '^':
        current_coords[1] += 1   # north: y-coordinate increases by one
    elif character == 'v':
        current_coords[1] -= 1   # south: y-coordinate decreases by one
    elif character == '>':
        current_coords[0] += 1   # east: x-coordinate increases by one
    elif character == '<':
        current_coords[0] -= 1   # west: x-coordinate decreases by one
    else:
        print('Error, unexpected input value')
    visited_coords.append(current_coords[:])    # create a copy of current_coords and add it to visisted_coords

unique_coords = uniqify(visited_coords)     # reduce list of visisted_coords to unique_coords
unique_houses = len(unique_coords)          # number of unique houses
total_visists = len(visited_coords)         # number of visisted houses, should be larger than unique amount

print('Part 1: There were', total_visists , 'houses visisted, but the amount of houses receiving at least one present is: ', unique_houses)

# Part Two ------------

instr_santa = characters[0::2]   # santa takes the odd instructions
current_coords_santa = [0, 0]     # current coordinates, starts at beginning point
visited_coords_santa = []         # a list of lists with visisted coordinates
visited_coords_santa.append([0, 0])  # first coordinates visisted is the starting point 

# let santa walk
for santa in instr_santa:
    if santa == '^':
        current_coords_santa[1] += 1   # north: y-coordinate increases by one
    elif santa == 'v':
        current_coords_santa[1] -= 1   # south: y-coordinate decreases by one
    elif santa == '>':
        current_coords_santa[0] += 1   # east: x-coordinate increases by one
    elif santa == '<':
        current_coords_santa[0] -= 1   # west: x-coordinate decreases by one
    else:
        print('Error, unexpected input value')
    visited_coords_santa.append(current_coords_santa[:])    # create a copy of current_coords and add it to visisted_coords

instr_robo_santa = characters[1::2] # robo-santa takes the even instructions
current_coords_robo_santa = [0, 0]     # current coordinates, starts at beginning point
visited_coords_robo_santa = []         # a list of lists with visisted coordinates
visited_coords_robo_santa.append([0, 0])  # first coordinates visisted is the starting point 

# let robo-santa walk
for robo_santa in instr_robo_santa:
    if robo_santa == '^':
        current_coords_robo_santa[1] += 1   # north: y-coordinate increases by one
    elif robo_santa == 'v':
        current_coords_robo_santa[1] -= 1   # south: y-coordinate decreases by one
    elif robo_santa == '>':
        current_coords_robo_santa[0] += 1   # east: x-coordinate increases by one
    elif robo_santa == '<':
        current_coords_robo_santa[0] -= 1   # west: x-coordinate decreases by one
    else:
        print('Error, unexpected input value')
    visited_coords_robo_santa.append(current_coords_robo_santa[:])    # create a copy of current_coords and add it to visisted_coords

combined_visited_coords = visited_coords_santa + visited_coords_robo_santa
combined_total_visists = len(combined_visited_coords)         # number of visisted houses, should be larger than unique amount

unique_coords2 = uniqify(combined_visited_coords)     # reduce list of visisted_coords to unique_coords
unique_houses2 = len(unique_coords2)          # number of unique houses

print('Part 2: There were', combined_total_visists , 'houses visisted, but the amount of houses receiving at least one present is: ', unique_houses2)
