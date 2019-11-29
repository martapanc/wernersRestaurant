$(document).ready(function () {

    $('#timepicker').datetimepicker({
        format: 'HH:mm',
        stepping: 15,
        defaultDate: moment(),
        enabledHours: [11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24]
    });

    $('#datepicker').datetimepicker({
        format: 'DD MMM YYYY',
        defaultDate: moment(),
        minDate: moment()
    });

});