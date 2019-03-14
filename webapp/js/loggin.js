/**
 * Created by mihail on 11/15/18.
 */
$(document).ready(function () {
    $("#enter").click(function () {
        var username = $("#user").val();
        var password = $("#password").val();
      //  password = CryptoJS.MD5(password);

        var param = '?name=' + username +
            '&password=' + password;

        var somewait = 35;
        var outBuf = "";
        setInterval(function () {
            if (somewait > 0) {
                if (outBuf.length == 0) {
                    outBuf = "===========";
                }
                outBuf = outBuf.substr(0, outBuf.length - 2);
                $("#view").text("~~" + outBuf + "|===|");
                somewait--;
            }else if (somewait == 0) {
                $("#view").text("Need start server side");
            }
        }, 500);
        $.ajax({
            name: 'login',
            url: '/login' + param,
            type: 'POST',
            success: function (data, status) {
                if (data == 'ok') {
                    window.location.replace("/user-page.jsp");
                } else {
                    somewait = -1;
                    $("#view").text(data)
                }
            }
        });
    })
});
