updateUserTable();
addCreateFormRoles();

function updateUserTable() {

    const userList = document.querySelector('#tableBody');
    let output = '';

    fetch('/rest/users')
        .then(res => res.json())
        .then(data => {
            data.forEach(user => {
                output +=
                    "<tr>" +
                    "<td>" + user.id + "</td>" +
                    "<td>" + user.name + "</td>" +
                    "<td>" + user.age + "</td>" +
                    "<td>" + user.email + "</td>" +
                    "<td>" + user.username + "</td>" +
                    "<td>" + parseUserRoles(user.rolesNames) + "</td>" +
                    "<td><button onclick='openEditForm(" + user.id + ")'" +
                    " type=\"button\" class=\"btn btn-success\" data-toggle=\"modal\"" +
                    " data-target=\"#modal-edit\">Edit</button></td>" +

                    "<td><button onclick='openDeleteForm(" + user.id + ")' " +
                    "type=\"button\" class=\"btn btn-danger\" data-toggle=\"modal\" + " +
                    " data-target =\"#modal-delete\">Delete</button></td>" +
                    "</tr>"
            });
            userList.innerHTML = output;
        });
}

function parseUserRoles(roles) {
    let userRoles = "";
    for (let i in roles) {
        userRoles += roles[i] + " ";
    }
    return userRoles;
}

function openEditForm(id) {
    fetch('/rest/users/' + id)
        .then(res => res.json())
        .then(async user => {
            $('#idEdit').val(user.id);
            $('#nameEdit').val(user.name);
            $('#ageEdit').val(user.age);
            $('#emailEdit').val(user.email);
            $('#usernameEdit').val(user.username);
            $('#passwordEdit').val(user.password);
            cleanEditRoles()
            let roles = await getAllRoles();
            for (let i in roles) {
                let hasRole = "";
                for (let r in user.rolesNames) {
                    if (roles[i].name === user.rolesNames[r]) {
                        hasRole = "selected";
                    }
                }
                $("#rolesNamesEdit")
                    .append('<option ' + hasRole + ' value="' + roles[i].name + '" id="' + roles[i].name + '">' + roles[i].name + '</option>');
            }

            $("#editFooter").empty().append(
                "<button data-dismiss='modal'" +
                " type=\"button\" class=\"btn btn-secondary\">Close</button>" +

                "<button onclick='sendEditForm("+user.id+")'" +
            " type=\"button\" class=\"btn btn-primary\" data-toggle=\"modal\" data-target =\"#modal-edit\">Edit</button>"
            )
        })
}

function openDeleteForm(id) {
    fetch('/rest/users/' + id)
        .then(res => res.json())
        .then(user => {
            $('#idDelete').val(user.id);
            $('#nameDelete').val(user.name);
            $('#ageDelete').val(user.age);
            $('#emailDelete').val(user.email);
            $('#usernameDelete').val(user.username);
            cleanDeleteRoles()
            for (let i in user.rolesNames) {
                $("#rolesNamesDelete").append('<option>'+user.rolesNames[i] +'</option>');
            }
            $("#deleteFooter").empty().append(
                "<button data-dismiss='modal'" +
                " type=\"button\" class=\"btn btn-secondary\">Close</button>" +

                "<button onclick='deleteUser("+user.id+")'" +
                " type=\"button\" class=\"btn btn-danger\">Delete</button>"
            )
        })
}

function cleanDeleteRoles() {
    $("#rolesNamesDelete").empty();
}

function cleanEditRoles() {
    $("#rolesNamesEdit").empty();
}

function cleanCreateRoles() {
    $("#rolesNamesAdd").empty();
}

function closeEditModal() {
    $("#modal-edit").modal('toggle');
    $("#tableBody").empty();
    updateUserTable();
}

function closeDeleteModal() {
    $("#modal-delete").modal('toggle');
    $("#tableBody").empty();
    updateUserTable();
}

function deleteUser(id) {
    fetch('/rest/users/' + id, {
        method: 'DELETE',
    }).then(this.closeDeleteModal);
}

async function getAllRoles() {
    const roles = fetch('/rest/roles')
        .then(res => res.json())
    return Array.from(await roles);
}

async function addCreateFormRoles() {
    cleanCreateRoles()
    let roles = await getAllRoles();
    for (let i in roles) {
        $("#rolesNamesAdd")
            .append('<option value="' + roles[i].name + '" id="add' + roles[i].name + '">' + roles[i].name + '</option>');
    }
}

function sendEditForm(id) {

    let user = {
        name: $("#nameEdit").val(),
        age: $("#ageEdit").val(),
        email: $("#emailEdit").val(),
        username: $("#usernameEdit").val(),
        password: $("#passwordEdit").val(),
        rolesNames: $("#rolesNamesEdit").val()
    }

    fetch('/rest/users/' + id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    }).then(this.closeEditModal);
}

function addUser() {

    let user = {
        name: $("#addName").val(),
        age: $("#addAge").val(),
        email: $("#addEmail").val(),
        username: $("#addUsername").val(),
        password: $("#addPassword").val(),
        rolesNames: $("#rolesNamesAdd").val()
    }

    fetch('/rest/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    }).then(this.showTable);
}

function showTable() {
    $("#tableBody").empty();
    $("#usersTableTab").tab('show');
    clearAddForm();
    updateUserTable();
}

function clearAddForm() {
    $("#addName").val('');
    $("#addAge").val('');
    $("#addEmail").val('');
    $("#addUsername").val('');
    $("#addPassword").val('');
    $("#addUser").prop("selected", false);
    $("#addAdmin").prop("selected", false);

}