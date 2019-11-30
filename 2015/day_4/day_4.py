# Name:      day_4
# Created:   October 23, 2016
# Blurb:     Find lowest number which creates particular MD5 hash, see http://adventofcode.com/2015/day/4

# import needed libraries
import hashlib

# input
input = 'iwrupvqb'
number = 0  # iterator number
found = False

# loop and hash until number is found which produces hash starting with 5 zeroes
while found == False:
    hash_input = input + str(number)
    obj = hashlib.md5()  # create a hash object
    obj.update(hash_input.encode('utf-8'))  # feed it the hash_input encoded as UTF-8
    hash = obj.hexdigest()  # digest the hash_input and put results into var hash
    if hash[0:5] == '00000':  # first five numbers of hash are zero
        result1 = number  # assign number to result
        found = True  # the lowest number has been found and the while loop can end
    number += 1  # increase value of number by one

print('Part 1: The lowest number is', result1)

# Part Two ------------
number = 0  # iterator number
found = False

# loop and hash until number is found which produces hash starting with 6 zeroes
while found == False:
    hash_input = input + str(number)
    obj = hashlib.md5()  # create a hash object
    obj.update(hash_input.encode('utf-8'))  # feed it the hash_input encoded as UTF-8
    hash = obj.hexdigest()  # digest the hash_input and put results into var hash
    if hash[0:6] == '000000':  # first six numbers of hash are zero
        result2 = number  # assign number to result
        found = True  # the lowest number has been found and the while loop can end
    number += 1  # increase value of number by one

print('Part 2: The lowest number now is', result2)
