$('document').ready(function() {
    $('.table #editButton').on('click', function(event) {

        event.preventDefault();

        let href= $(this).attr('href');

        $.get(href, function (user) {
            $('#idEdit').val(user.id);
            $('#nameEdit').val(user.name);
            $('#ageEdit').val(user.age);
            $('#emailEdit').val(user.email);
            $('#userNameEdit').val(user.username);
            $('#passwordEdit').val(user.password);
            cleanEditRoles()
            $.ajax({
                type: "get",
                url: "/admin/roles-get",
                success: function (roles) {
                    for (let i in roles) {
                        let hasRole = "";
                        for (let r in user.roles) {
                            if (roles[i].name === user.roles[r].name) {
                                hasRole = "selected";
                            }
                        }
                        $("#rolesEdit")
                            .append('<option ' + hasRole + ' value="'+roles[i].name+'">'+roles[i].name+'</option>');
                    }
                }
            })
        });

        $('#modal-edit').modal();
    });
});

function cleanEditRoles() {
    $("#rolesEdit").empty();
}