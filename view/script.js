const url = "http://localhost:8080/users";

function show(users) {

    let tab = `<thead>
            <th scope="col">ID</th>
            <th scope="col">NOME</th>
            <th scope="col">EMAIL</th>
            <th></th>
        </thead>`;

    for (let user of users) {
        tab += `
            <tr>
                <td scope="row">${user.id}</td>
                <td>${user.nome}</td>
                <td>${user.email}</td>
                <td><button type="button" class="btn btn-outline-primary">Editar</button> <button type="button" class="btn btn-outline-danger">Excluir</button></td>
            </tr>
        `;
    }

    document.getElementById("users").innerHTML = tab;
}

/*async function getAPI(url) {
    const response = await fetch(url, { method: "GET" });

    var data = await response.json();
    console.log(data);
    show(data);
}

getAPI(url);*/

async function getUsers() {
    let key = "Authorization";
    const response = await fetch(url, {
        method: "GET",
        headers: new Headers({
            Authorization: localStorage.getItem(key),
        }),
    });

    var data = await response.json();
    console.log(data);
    show(data);
}

getUsers();