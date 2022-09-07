$('document').ready(function() {
    $('.table #deleteButton').on('click', function(event) {

        event.preventDefault();

        let href= $(this).attr('href');

        $.get(href, function (user) {
            $('#idDelete').val(user.id);
            $('#nameDelete').val(user.name);
            $('#ageDelete').val(user.age);
            $('#emailDelete').val(user.email);
            $('#usernameDelete').val(user.username);
            cleanDelRoles();
            for (let i in user.roles) {
                $("#rolesDelete").append('<option>' +  user.roles[i].name +'</option>');
            }
        });

        $('#modal-delete').modal();
    });
});

function cleanDelRoles() {
    $("#rolesDelete").empty();
}