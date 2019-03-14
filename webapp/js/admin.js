/**
 * Created by mihail on 11/20/18.
 */
function userDelete(id, userName) {

    $(document).ready(function () {

        $.ajax({
            url: "/user/" + id,
            type: 'DELETE',
            success: function () {
                $("#result").text("User " + userName + " deleted");
                $("#user-" + id).hide(3000)
            }
        });
    });
}

$(document).ready(function () {
    $("#confirm_edit").click(function () {

        var paramethers = 'id=' + $("#user_id").val() +
            '&name=' + $("#user_name").val() +
            '&role=' + $("#role").val() +
            '&cigarettePrice=' + $("#price").val();
        $.ajax({
            name: 'edit',
            url: "/user?" + paramethers,
            type: 'PUT',
            success: function (data) {
                if (data == 'admin') {
                    window.location.replace("/admin-page.jsp");
                } else if (data == 'user') {
                    window.location.replace("/user-page.jsp");
                } else {
                    $("#about-edit").text("Can't change user " + $("#user_name").val())
                }
            }
        })
    });
});