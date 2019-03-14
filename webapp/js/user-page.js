/**
 * Created by mihail on 12/9/18.
 */
$(document).ready(function () {

    var economy = $("#economy");
    var nextLevelTime = $("#next");
    var secondForOneCent = economy.text();

    $("#smoke").click(function () {
        $.get("/smoke", function (data, status) {

            $("#trow").text(data);

            window.location.replace("/user-page.jsp");

        });
    });
    $("#logout").click(function () {
        $.ajax({
            url: '/logout',
            type: 'POST'
        })
    });

    var time = $("#time");
    var lastSmoke = new Date(time.text());
    var nextLevel = new Date(nextLevelTime.text());

    setInterval(function () {
        var between = new Date() - lastSmoke;
        var timeForLevel = nextLevel - new Date();

        if (secondForOneCent == 0) {
            economy.text("We are count you cigarettes")
        } else {

            economy.text("Economy: " + ((between / 100000) / secondForOneCent).toFixed(2))
        }
        if (between < 0) {
            between = 0 - between;
        }
        if (timeForLevel < 0) {
            timeForLevel = 0 - timeForLevel;
        }

        time.text("Last time for smoke: " + outTime(between));
        nextLevelTime.text("Next level from: " + outTime(timeForLevel))

    }, 1000);

});

function outTime(between) {
    var days = Math.floor(between / (1000 * 60 * 60 * 24));
    var hours = Math.floor((between % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    var minutes = Math.floor((between % (1000 * 60 * 60)) / (1000 * 60));
    var seconds = Math.floor((between % (1000 * 60)) / 1000);
    var out;

    if (days != 0) {
        out = days + " d " + hours + " h";
    } else if (hours != 0) {
        out = hours + " h " + minutes + " m"
    } else {
        out = minutes + " m " + seconds + " s"
    }

    return out;
}
