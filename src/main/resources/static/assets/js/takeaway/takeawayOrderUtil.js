$(document).ready(function () {

    $.ajax({
        type: 'POST',
        cache: false,
        url: "/foodClass",
        data: {
            "action": "list"
        },
        success: function (response) {
            $("#food-class-list").html("");
            response.forEach(function (entry) {
                $("#food-class-list").append("<a href='#' id='" + entry.name.toLowerCase() + "-btn' class='btn btn-lg btn-primary'>"
                    + "<img width='30' src='../../" + entry.image + "'> &emsp;" + entry.name);
                $("#pizza-btn").addClass("active");
            });

            response.forEach(function (entry) {
                $("#" + entry.name.toLowerCase() + "-btn").click(function () {
                    removeActive();
                    $(this).addClass("active");
                    $('.thmenu-header').html("<h3>" + entry.name.toUpperCase() + "</h3>");
                    $("#item-table").bootstrapTable('filterBy', {
                        "foodClass_id": entry.id
                    });
                });
            });

            function removeActive() {
                response.forEach(function (entry) {
                    $("#" + entry.name.toLowerCase() + "-btn").removeClass("active");
                });
            }

        },
        error: function (error) {
            console.log(error);
        }
    });

    $("#item-table").bootstrapTable('filterBy', {
        "foodClass_id": 41
    });
});

// Table functions
function availableSorter(a, b) {
    if (a === true)
        return 1;
    if (a === false)
        return -1;
    return 0;
};

function actionFormatter(value, row, index) {
    return ['<a class="add" href="javascript:void(0)" title="Add to Cart">',
        '<span class="glyphicon glyphicon-plus"></span>', '</a>'].join('');
}

var price = 0;
var cart = [];
window.actionEvent = {
    'click .add': function (e, value, row, index) {
        $("#checkout-btn").prop("disabled", false); // enable Checkout button when at least one item is in the cart

        // Check if the cart already includes the selected item
        var dupCheck = -1;
        cart.forEach(function (entry, i) {
            if (row.name === entry[0])
                dupCheck = i; // Store the index of the duplicate item
        });
        if (dupCheck === -1) { // If the cart has no duplicates (=the loop found no match and the index did not change) push a new  item
            cart.push([row.name, 1, row.price, row.price, row.id]);
            price += row.price;
        } else {
            if (cart[dupCheck][1] < 20) {
                cart[dupCheck][1] += 1; // Else update quantity and price of the item already in cart
                cart[dupCheck][2] += cart[dupCheck][3];
                price += row.price;
            }
        }

        $(".order-list").html(""); // Display the cart
        cart.forEach(function (entry) {
            $(".order-list")
                .append(
                    "<tr><td>" + entry[0] + "&emsp;</td>"
                    + "<td><i class='fa fa-plus-square fa-lg'></i>&ensp;" + entry[1] + "&ensp;<i class='fa fa-minus-square fa-lg'></i></td>"
                    + "<td>&ensp;€ " + entry[2].toFixed(2) + "</td></tr>"
                );
        });


        $("#total-price-box").html('<h4><span class="pull-right total-price">Total price: € ' + price.toFixed(2) + '</span></h4>');
    }
};

// Increase quantity on "plus" click
$(".cart-form").on("click", "i.fa-plus-square", function () {
    var i = $(this).parent().parent().index(); // store index of selected row
    if (cart[i][1] < 20) {
        cart[i][1] += 1; // increase quantity by 1
        cart[i][2] += cart[i][3]; // increase price by the price of a single item
        price = 0;
        cart.forEach(function (entry) { // update cart
            price += entry[2];
        });
        $(".order-list").html("");
        cart.forEach(function (entry) { // display changes
            $(".order-list")
                .append(
                    "<tr><td>" + entry[0] + "&emsp;</td>"
                    + "<td><i class='fa fa-plus-square fa-lg'></i>&ensp;" + entry[1] + "&ensp;<i class='fa fa-minus-square fa-lg'></i></td>"
                    + "<td>&ensp;€ " + entry[2].toFixed(2) + "</td></tr>"
                );
        });
        $("#total-price-box").html('<h4><span class="pull-right total-price">Total price: € ' + price.toFixed(2) + '</span></h4>');
    }
});

// Decrease quantity on "minus" click. Remove when qnt reaches 0
$(".cart-form").on("click", "i.fa-minus-square", function () {
    var i = $(this).parent().parent().index();
    cart[i][1] -= 1;
    if (cart[i][1] != 0) {
        cart[i][2] -= cart[i][3]; // decrease price by the
        // price of a single item
    } else {
        cart.splice(i, 1); // remove item from array when
        // quantity reaches 0
        if (cart[0] == null)
            $("#checkout-btn").prop("disabled", true);
    }
    price = 0;
    cart.forEach(function (entry) { // update cart
        price += entry[2];
    });
    $(".order-list").html("");
    cart.forEach(function (entry) {
        $(".order-list")
            .append(
                "<tr><td>" + entry[0] + "&emsp;</td>"
                + "<td><i class='fa fa-plus-square fa-lg'></i>&ensp;" + entry[1] + "&ensp;<i class='fa fa-minus-square fa-lg'></i></td>"
                + "<td>&ensp;€ " + entry[2].toFixed(2) + "</td></tr>"
            );
    });
    $("#total-price-box").html(
        '<h4><span class="pull-right total-price">Total price: € ' + price.toFixed(2) + '</span></h4>');
});

// Checkout button functions
$("#checkout-btn").on("click", function () {
    var jsoncart = {
        jcart: []
    };
    cart.forEach(function (entry) {
        jsoncart.jcart.push({
            "name": entry[0],
            "qnt": entry[1],
            "totPrice": entry[2],
            "uPrice": entry[3],
            "id": entry[4]
        });
    });
    var cartToSend = JSON.stringify(jsoncart.jcart);
    if (typeof (Storage) != "undefined") {
        localStorage.setItem("cart", cartToSend);
        localStorage.setItem("totPrice", price)
        console.log("Cart saved: " + cartToSend + "\nPrice: " + price);
    } else {
        console.log("Local storage non supported.")
    }
    var session = document.getElementById("session").value;
    console.log("session " + session);
    if (session != "")
        window.location.href = "takeawayCheckout.jsp";
    else
        window.location.href = "/restaurantProject/pages/home/takeaway-checkout.jsp";
});

// Reset button functions
$("#reset-btn").on("click", function () {
    cart = new Array();
    price = 0;
    $(".order-list").html("");
    $("#total-price-box").html(
        '<h4><span class="pull-right total-price">Total price: € ' + price.toFixed(2) + '</span></h4>');
    $("#checkout-btn").prop("disabled", true);
});
