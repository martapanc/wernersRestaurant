var today = new Date();
$(".date").html("Date: &ensp;<strong>" + moment().format('ddd DD MMM YYYY, hh:mm') + "</strong>");
var cart = JSON.parse(localStorage.getItem("cart"));
var totPrice = JSON.parse(localStorage.getItem("totPrice"));

$(function() { // Display items in cart

	cart.forEach(function(entry) {
		$(".cart").append(
				"<tr><td>" + entry.id + "</td><td>" + entry.name + "</td><td>"
						+ entry.qnt + "</td><td>€ " + entry.totPrice.toFixed(2)
						+ "</td></tr>");
	});
	$(".cart").append(
			"<tr><td></td><td></td><th>Total: </th><td>€ "
					+ totPrice.toFixed(2) + "</td></tr>");
	// localStorage.clear();
});

$('#reservation-form')
		.bootstrapValidator(
				{
					trigger : 'blur',
					fields : {
						firstname : {
							validators : {
								notEmpty : {
									message : 'Your first name is required'
								},
								regexp : {
									regexp : /^[a-zA-Z ]+$/,
									message : 'Your first name cannot have numbers or symbols'
								}
							}
						},
						lastname : {
							validators : {
								notEmpty : {
									message : 'Your last name is required'
								},
								regexp : {
									regexp : /^[a-zA-Z ]+$/,
									message : 'Your last name cannot have numbers or symbols'
								}
							}
						},
						email : {
							validators : {
								regexp : {
									regexp : /^(([^<>()\[\]\\.,;:\s@]+(\.[^<>()\[\]\\.,;:\s@]+)*)|(.+))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
									message : 'The input is not a valid email address'
								}
							}
						},
						telephone : {
							validators : {
								notEmpty : {
									message : 'The phone number is required'
								},
								regexp : {
									regexp : /^[0-9]{10}$/,
									message : 'The input is not a valid phone number (ex: 0123456789)'
								}
							}
						},
						address : {
							validators : {
								notEmpty : {
									message : 'The delivery address is required'
								},
								regexp : {
									regexp : /^[a-zA-Z0-9- ,.]{5,100}$/,
									message : 'The input is not a valid address'
								}
							}
						}
					}
				}).on('error.field.bv', '[name="phone"]', function(e, data) {
			// change the data-bv-trigger value to keydown
			$(e.target).attr('data-bv-trigger', 'keydown');
			// destroy the plugin
			// console.info(data.bv.getOptions());
			data.bv.destroy();
			// console.info(data.bv.getOptions());
			// initialize the plugin
			$('#reservation-form').bootstrapValidator(data.bv.getOptions());
		});

$("#submit-btn")
		.click(
				function() {
					var title = document.getElementById("title").value;
					var firstname = document.getElementById("firstname").value;
					var lastname = document.getElementById("lastname").value;
					var telephone = document.getElementById("telephone").value;
					var email = document.getElementById("email").value;
					var session = document.getElementById("session").value;
					var address = document.getElementById("address").value;
					var comment = document.getElementById("comments").value;

					$(".reservation-area").addClass("col-md-12").html(
							"</br><strong>Reservation name:</strong>").append(
							"&emsp;" + title + " " + firstname + " " + lastname
									+ "</br>").append(
							"<strong>Telephone number: </strong>&emsp;"
									+ telephone + "</br>").append(
							"<strong>Delivery address: </strong>&emsp;"
									+ address + "</br>");
					if (email != "")
						$(".reservation-area").append(
								"<strong>Email address: </strong>&emsp;"
										+ email);
					$(".reservation-area").append("</br>&emsp;");

					$.post({
						type : "POST",
						url : "/restaurantProject/takeaway",
						data : {
							"cart" : localStorage.getItem("cart"),
							"tot" : totPrice,
							"title" : title,
							"firstname" : firstname,
							"lastname" : lastname,
							"email" : email,
							"telephone" : telephone,
							"session" : session,
							"address" : address,
							"comment" : comment
						},
						complete : function(response) {
							console.log(response);
							alert("We are currently working on your reservation! You will receive a phone call as soon as we are ready to deliver it.");
						},
						error : function(error) {
							console.log("error" + error);
						}
					});
					return false;
				});