var today = new Date();
$(".date").html("Date: &ensp;<strong>" + moment().format('ddd DD MMM YYYY, hh:mm') + "</strong>");
var cart = JSON.parse(localStorage.getItem("cart"));
var totPrice = JSON.parse(localStorage.getItem("totPrice"));

$(function () { // Display items in cart
    cart.forEach(function (entry) {
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

$("#submit-btn").click(function () {
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
        type: "POST",
        url: "/create-takeaway",
        data: {
            "cart": localStorage.getItem("cart"),
            "tot": totPrice,
            "title": title,
            "firstname": firstname,
            "lastname": lastname,
            "email": email,
            "telephone": telephone,
            "session": session,
            "address": address,
            "comment": comment
        },
        complete: function (response) {
            console.log(response);
            alert("We are currently working on your reservation! You will receive a phone call as soon as we are ready to deliver it.");
        },
        error: function (error) {
            console.log("error" + error);
        }
    });
    return false;
});
