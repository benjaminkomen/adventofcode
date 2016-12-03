/* Name:			calculations
 * Date: 			July 3, 2016
 * Last modified:	July 3, 2016
 * Description:		Calculate amount of wrapping paper
 */
$(document).ready(function() {
	var input_file = 'input.txt';
	
	//Load the input file
	$.get(input_file, function(data) {
		// Split the lines
		var lines = data.split('\n');
		console.log(lines);
		
		//loop through data
		$.each(lines, function(lineNo, line) {
			
		});
	});
		//$('#results_container').text(counter);
		//$('#results_container2').text(position[0]);
});