function activeTab(currentPath) {
    currentPath = currentPath.split("/").pop();

    var tabs = ["dashboard", "scheduler", "tableReservation", "takeawayOrder", "documentation"];
    tabs.splice( tabs.indexOf(currentPath), 1 );

    $('#' + currentPath + '-menu').addClass('active');

    tabs.forEach(function (tab) {
        $('#' + tab + '-menu').removeClass('active');
    });
}