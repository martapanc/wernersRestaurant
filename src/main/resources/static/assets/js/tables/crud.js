/**
 * This js file handles the CRUD actions of the bootstrap table by adding event
 * handlers to the buttons, controling modal visualization, notifications for
 * feedback and handling error situations.
 * 
 */

var $table = $('#table');

var $cButton = $('#create');
var $cModal = $('#create-modal');
var $cBody = $('#create-modal-body');

var $eButton = $('.edit');
var $eModal = $('#edit-modal');
var $eBody = $('#edit-modal-body');

var $delete = $('#delete');
var $modals = $('.modal');

var $notifier = $('#notify');

/**
 * Initializes the webpage for CRUD operations. Add eventhandlers to create,
 * edit and delete button of bootstrap table
 * 
 */
function initCRUD(API_URL) {
	
	// create button 
	$cButton.on('click', function(){
	    $cBody.load(API_URL + "-find" + ' #create-form', {action: 'find', id: 0}, function (responseText, textStatus, req) {
	    	if(textStatus == 'error') {
	    		$notifier.notify({
				    message: { text: 'Server error while sending a find request!' },
				    type: 'danger',
				    closable: false,
				  }).show(); 
	    	} else {
	    		console.log('Successfully loaded create-form');
	    		$('#create-form').validator().on('submit', function(e) {
	    			if (e.isDefaultPrevented()) {
	    			    console.log('Create form validation has failed');
	    			  } else {
	    				e.preventDefault();
						sendCRUDRequest(API_URL + "-action", $(this).serialize());
	    			  }
	    		});
			    	$cModal.modal();
	    	}
		});
	});
	
	// edit button (window.action events is needed because of async table creation)
	window.actionEvents = {
		'click .edit' : function(e, value, row) {
		    $eBody.load(API_URL + "-find" + ' #edit-form', {action: 'find', id: row.id}, function (responseText, textStatus, req) {
		    	if(textStatus == 'error') {
		    		$notifier.notify({
					    message: { text: 'Server error while sending a find request!' },
					    type: 'danger',
					    closable: false,
					  }).show();
		    	} else {
		    		console.log('Successfully loaded edit-form');
		    		$('#edit-form').validator().on('submit', function(e) {
		    			if (e.isDefaultPrevented()) {
		    			    console.log('Edit form validation has failed');
		    			  } else {
		    				e.preventDefault();
							sendCRUDRequest(API_URL + "-action", $(this).serialize());
		    			  }
		    		});
			    	$eModal.modal();
	            }
			});
		}
	};
			
	// checkboxes in table (to enable delete button)
	$table.on('check.bs.table uncheck.bs.table '
			+ 'check-all.bs.table uncheck-all.bs.table', function() {
					$delete.prop('disabled', !$table.bootstrapTable('getSelections').length);
		// save your data, here just save the current page
		// selections = getIdSelections();
		// push or splice the selections if you want to save all data selections
	});

	
	// delete button
	$delete.on('click', function() {
		var idArray = getIdSelections();
		var formData = 'action=delete&id=' + idArray.join('&id=');
		$('#delete-modal-body').html('<p>Are you sure to delete ' + idArray.length + ' entries?<p>');
		$('#delete-modal').modal();
		$('#delete-button').on('click', function() {
				sendCRUDRequest(API_URL + "-action", formData);
			  });
	});
};

/**
 * Sends an AJAX call based on the action parameter (which can be 
 * either create, edit or delete), in order to interact with the
 * database. After the call (no matter if succesful) all open modals 
 * (in theory only one is open) are closed.
 * 
 * @param {String} 
 * 				action String that defines the CRUD action
 */
function sendCRUDRequest(URL, formdata) {
	console.log(URL, formdata);
	var regex_id = /id=([a-z]+)/g;
	var regex_action = /action=([a-z]+)/g;
	var action = regex_action.exec(formdata);
	var id = regex_id.exec(formdata);
	$.ajax({
		url : URL,
		type : 'post',
		data: formdata,
		success : function() {

			$('#notify').notify({
			    message: { text: action[1].toUpperCase() + ' executed!' },
			    type: 'success',
			    closable: false,
			  }).show();
			$table.bootstrapTable('refresh');
			location.reload();
		},
		error : function() {
			console.log(URL, formdata);
			$notifier.notify({
			    message: { text: 'Server error while executing ' + action[1] + ' request!' },
			    type: 'danger',
			    closable: false,
			  }).show();
		}
	});
	$modals.modal('hide');
};


