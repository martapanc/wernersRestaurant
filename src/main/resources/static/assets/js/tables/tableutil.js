/**
 * This js file collects a number of useful functions for
 * the bootstrap table plugin, like column formatters and
 * sorters.
 *
 */

	/**
	 * Function formating a boolean column.
	 * 
	 * @param {Object}
	 *            value The row value.
	 * @param {Object}
	 *			  row The row object.
	 */
	function boolFormatter(value, row) {
    	var icon = value === true ? 'fa-check' : 'fa-times';
    	return '<i class="fa ' + icon + '"></i> ';
	}

	/**
	 * Function for sorting rows based on boolean column.
	 * 
	 * @param {boolean}
	 *            a The first value.
	 * @param {boolean}
	 *			  b The value to compared with.
	 */
	function boolSorter(a, b) {
		return (a === b)? 0 : a? -1 : 1;
	}
	
	/**
	 * Formats the action column of the table by adding a pencil icon.
	 * 
	 * @param {number}
	 *            value The row number.
	 */
	function actionFormatter(value) {
		return [
				'<a class="edit" href="javascript:" title="Edit Item"><i class="fa fa-pencil"></i></a>', ]
				.join('');
	}
	
	/**
	 * Formats the table category column of the table by adding star icons.
	 * 
	 * @param {number}
	 *            value The row number.
	 */
	function categoryFormatter(value) {
		var retvalue = '';
		switch(value){
		
		case 'lowcost':
			retvalue = '<i class="fa fa-star"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i>';
			break;
			
		case 'medium':
			retvalue = '<i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star-o"></i>';
			break;
		
		case 'highend':
			retvalue = '<i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i>';
			break;
		}
		
		return retvalue;
	}
	 
	/**
	* Formats the price column of the table by adding a Euro sign.
	* 
	* @param {number}
	*            value The row number.
	*/
	function priceFormatter(value) {
		var num = parseFloat(value);
		return '€ ' + num.toFixed(2);
	}

	/**
	 * Returns the number of selected rows (its id's).
	 * 
	 * @return {number}
	 *            value The selected row numbers.
	 */
	function getIdSelections() {
		return $.map($table.bootstrapTable('getSelections'), function(row) {
			return row.id
		});
	}