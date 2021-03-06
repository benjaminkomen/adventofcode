/* Name:			floor_parser
 * Date: 			July 3, 2016
 * Last modified:	July 3, 2016
 * Description:		Calculate floor number from up/down input
 */
$(document).ready(function() {
	//input
	var input = '';
	var characters = input.split('');
	var counter = 0;
	var position = [];
	
	//loop through data
	$.each(characters, function(charNo, character) {
		if (character == "(") { //go up one floor
			counter++;
		} else { //go down one floor
			counter--;
		}
		if (counter == -1) { //if basement is reached
			position.push(charNo+1); //plus one to correct for javascript starts counting at zero instead of one
		}
	});
	$('#results_container').text(counter);
	$('#results_container2').text(position[0]);
});