const url = 'http://localhost:8080/api/'
const postList = document.getElementById("userList")
const editModal = new bootstrap.Modal(document.getElementById('editModal'))
const formEdit = document.getElementById('editForm')
const editId = document.getElementById('id0')
const editFirstName = document.getElementById('firstname0')
const editLastName = document.getElementById('lastname0')
const editAge = document.getElementById('age0')
const editEmail = document.getElementById('email0')
const editPassword = document.getElementById('password0')
const editRoles = document.getElementById('roles0')

fetch(url).then(
    res=>{
        res.json().then(
            data=>{
                console.log(data)
                    let output = ""
                    data.forEach(el => {
                        
                        output+= `
                        <tr class="text-center">
                            <th>${el.id}</th>
                            <th>${el.firstname}</th>
                            <th>${el.lastname}</th>
                            <th>${el.age}</th>
                            <th>${el.username}</th>
                            <th>${el.roles.map(e => " " + e.name.substring(5))}</th>
                            <th><a class="btnEdit btn btn-sm btn-primary">Edit</a></th>
                            <th><a class="btnDelete btn btn-sm btn-danger">Delete</a></th>
                        `                              
                    /*     output+=`
                        <div class="modal fade" id="${'DELETE'+el.id}"
                            aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog" modal-dialog-centered>
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="editModalLabel">Edit user</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>

                                <form action="@{admin/{id}/update(id=${el.id})}"
                                        object="${el}" method="PUT">
                                    <div class="modal-body col-md text-center">
                                        <br>
                                        <label for="id0"><b>ID</b></label>
                                        <input name="id" type="text" readonly
                                                class="form-control" id="id0"
                                                value="${el.id}"/>
                                        <br>
                                        <label for="firstname0"><b>First name</b></label>
                                        <input name="firstname" type="text" readonly
                                                class="form-control" id="firstname0"
                                                value="${el.firstname}"/>
                                        <br>
                                        <label for="lastname0"><b>Last name</b></label>
                                        <input name="lastname" type="text" readonly
                                                class="form-control" id="lastname0"
                                                value="${el.lastname}"/>
                                        <br>
                                        <label for="age0"><b>Age</b></label>
                                        <input name="age" type="number" readonly
                                                class="form-control" id="age0"
                                                value="${el.age}"/>
                                        <br>
                                        <label for="email0"><b>Email</b></label>
                                        <input name="username" type="text" readonly
                                                class="form-control" id="email0"
                                                value="${el.username}"/>
                                        
                                        <label for="roles0"><b>Role</b></label>
                                        <select multiple class="form-control form-control-sm" id="roles0" name="role" size="2" readonly>
                                            <option value="1">${el.roles.map(e => " " + e.name.substring(5))}</option>
                                            
                                        </select>
                                        <br><br>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary"
                                                data-bs-dismiss="modal">Close
                                        </button>
                                        <button type="submit" class="btn btn-danger">
                                            Delete
                                        </button>
                                    </div>
                                </form> 

                            </div>
                        </div>
                    </div>
                        `                       */
                    output+= "</tr>"
                    })
                    postList.innerHTML = output
                              
            }
        )
    }
 )

 const on = (element, event, selector, hendler) => {
    element.addEventListener(event, e => {
        if(e.target.closest(selector)){
            hendler(e)
        }
    })
 }
 
on(document, 'click', '.btnDelete', e=>{
    const line = e.target.parentNode.parentNode
    const id = line.firstElementChild.innerHTML
    console.log(id)
 })

let idForm = 0
on(document, 'click', '.btnEdit', e=>{
    const line = e.target.parentNode.parentNode
    idForm = line.children[0].innerHTML
    editId.value = line.children[0].innerHTML
    editFirstName.value = line.children[1].innerHTML
    editLastName.value = line.children[2].innerHTML
    editAge.value = line.children[3].innerHTML
    editEmail.value = line.children[4].innerHTML
    editRoles.value = line.children[5].innerHTML
    editModal.show()
})
 
formEdit.addEventListener('submit', (e)=>{
    e.preventDefault()
    console.log(editRoles.value)
    fetch(url, {
        method:'PUT',
        headers:{
            'Content-Type':'application/json'
        },
        body: JSON.stringify({
            id:editId.value,
            firstname:editFirstName.value,
            lastname:editLastName.value,
            age:editAge.value,
            username:editEmail.value,
            password:editPassword.value,
            roles: [{id: 1, name: 'ROLE_USER'}]    
        })
    })  
    .then( response => response.json() )
    .then( response => location.reload() )    
    editModal.hide()
})
function getUserRoles(number){

}
/* let roleList = [
    {id: 1, role: "ROLE_USER"},
    {id: 2, role: "ROLE_ADMIN"}
]
let isUser = true;

$(async function () {
    await getUser();
    await infoUser();
    await tittle();
    await getUsers();
    await getNewUserForm();
    await getDefaultModal();
    await createUser();

})

const userFetch = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    findAllUsers: async () => await fetch('http://localhost:8080/api/'),
    findUserByUsername: async () => await fetch(`api/user`),
    findOneUser: async (id) => await fetch(`api/users/${id}`),
    addNewUser: async (user) => await fetch('api/users', {method: 'POST', headers: userFetch.head, body: JSON.stringify(user)}),
    updateUser: async (user, id) => await fetch(`api/users/${id}`, {method: 'PUT', headers: userFetch.head, body: JSON.stringify(user)}),
    deleteUser: async (id) => await fetch(`api/users/${id}`, {method: 'DELETE', headers: userFetch.head})
}

async function infoUser() {
    let temp = '';
    const info = document.querySelector('#info');
    await userFetch.findUserByUsername()
        .then(res => res.json())
        .then(user => {
            temp += `
             <span style="color: white">
               ${user.username} with roles <span>${user.roles.map(e => " " + e.role.substr(5))}</span>
                </div>
            </span>
                </tr>
            `;
        });
    info.innerHTML = temp;
} */