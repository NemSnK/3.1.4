const url = 'http://localhost:8080/api/'
//Таблицы и модальные окна
const postList = document.getElementById("userList")
const editModal = new bootstrap.Modal(document.getElementById('editModal'))
const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'))
var tabUserTable = new bootstrap.Tab(document.querySelector('#myTab li:first-child a'))  
//Формы отправки
const formEdit = document.getElementById('editForm')
const formNew = document.getElementById('newForm')
const formDelete = document.getElementById('deleteForm')
//Данные для заполнения и отправки
    //форма Edit
const editId = document.getElementById('id0')
const editFirstName = document.getElementById('firstname0')
const editLastName = document.getElementById('lastname0')
const editAge = document.getElementById('age0')
const editEmail = document.getElementById('email0')
const editPassword = document.getElementById('password0')
const editRoles = document.getElementById('roles0')
    //форма New
const newFirstName = document.getElementById('firstname')
const newLastName = document.getElementById('lastname')
const newAge = document.getElementById('age')
const newEmail = document.getElementById('email')
const newPassword = document.getElementById('password')
const newRoles = document.getElementById('roles')
    //форма Delete
const deleteId = document.getElementById('id1')
const deleteFirstName = document.getElementById('firstname1')
const deleteLastName = document.getElementById('lastname1')
const deleteAge = document.getElementById('age1')
const deleteEmail = document.getElementById('email1')
const deletePassword = document.getElementById('password1')
const deleteRoles = document.getElementById('option1')                
//Заполнение таблицы пользователей
function showUser() {
    fetch(url).then(
        response => {
            response.json().then(
                data => {
                    let output = ""
                    data.forEach(el => {

                        output += `
                        <tr class="text-center">
                            <th>${el.id}</th>
                            <th>${el.firstname}</th>
                            <th>${el.lastname}</th>
                            <th>${el.age}</th>
                            <th>${el.username}</th>
                            <th>${el.roles.map(e => " " + e.name.substring(5))}</th>
                            <th><a class="btnEdit btn btn-sm btn-primary">Edit</a></th>
                            <th><a class="btnDelete btn btn-sm btn-danger">Delete</a></th>
                        </tr>`
                    })
                    postList.innerHTML = output

                }
            )
        }
    )
}
showUser()
//Обработчик событий
 const on = (element, event, selector, hendler) => {
    element.addEventListener(event, e => {
        if(e.target.closest(selector)){
            hendler(e)
        }
    })
 }
//Кнопка DELETE 
let idForm = 0
on(document, 'click', '.btnDelete', e=>{
    const line = e.target.parentNode.parentNode
    idForm = line.children[0].innerHTML        
    deleteId.value = line.children[0].innerHTML
    deleteFirstName.value = line.children[1].innerHTML
    deleteLastName.value = line.children[2].innerHTML
    deleteAge.value = line.children[3].innerHTML
    deleteEmail.value = line.children[4].innerHTML
    deleteRoles.value = line.children[5].innerHTML
    arr = deleteRoles.value.split(',')
    document.getElementById('roles1').options[0].innerHTML = "";
    document.getElementById('roles1').options[1].innerHTML = "";
    var index;
    for (index = 0; index < arr.length; ++index) {
        console.log(arr[index]);
        document.getElementById('roles1').options[index].innerHTML = arr[index];
    }
    deleteModal.show() 
})
//Кнопка EDIT
on(document, 'click', '.btnEdit', e=>{
    const line = e.target.parentNode.parentNode
    editId.value = line.children[0].innerHTML
    editFirstName.value = line.children[1].innerHTML
    editLastName.value = line.children[2].innerHTML
    editAge.value = line.children[3].innerHTML
    editEmail.value = line.children[4].innerHTML
    editRoles.value = line.children[5].innerHTML
    editPassword.value = ""
    editModal.show()
})

//Отправка DELETE
formDelete.addEventListener('submit', (e)=>{
    e.preventDefault()
    fetch(url+idForm, {
        method:'DELETE'
    })
    .then( response =>{response.json().then(
        data=>{
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
                    </tr>`                                              
                })
                postList.innerHTML = output
                        
        }
    )}) 
    deleteModal.hide()    
})
//Оправка PUT 
formEdit.addEventListener('submit', (e)=>{
    e.preventDefault()
    const role = getUserRoles(editRoles.value)
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
            roles: getUserRoles(editRoles.value)    
        })
    })  
    .then( response =>{response.json().then(
        data=>{
            if ('info' in data) {
                alert(data.info)
            }else {
                let output = ""
                data.forEach(el => {
                    console.log(data)
                    output += `
                    <tr class="text-center">
                        <th>${el.id}</th>
                        <th>${el.firstname}</th>
                        <th>${el.lastname}</th>
                        <th>${el.age}</th>
                        <th>${el.username}</th>
                        <th>${el.roles.map(e => " " + e.name.substring(5))}</th>
                        <th><a class="btnEdit btn btn-sm btn-primary">Edit</a></th>
                        <th><a class="btnDelete btn btn-sm btn-danger">Delete</a></th>
                    </tr>`
                })
                postList.innerHTML = output
                editModal.hide()
            }
        }
    )})
})
//Отправка POST
formNew.addEventListener('submit', (e)=>{
   e.preventDefault()
   const role = getUserRoles(newRoles.value)
   fetch(url, {
        method:'POST',
        headers:{
            'Content-Type':'application/json'
        },
        body: JSON.stringify({
            firstname:newFirstName.value,
            lastname:newLastName.value,
            age:newAge.value,
            username:newEmail.value,
            password:newPassword.value,
            roles: getUserRoles(newRoles.value)    
        })
    })  
    .then( response =>{response.json().then(
        data=>{
                if ('info' in data) {
                    alert(data.info)
                }else {
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
                        </tr>`
                    })
                    postList.innerHTML = output
                    newFirstName.value=""
                    newLastName.value=""
                    newAge.value=""
                    newEmail.value=""
                    newPassword.value=""
                    newRoles.value =""
                    tabUserTable.show()
                }
        }
    )})
})
//Вспомогательная функция для заполнения ролей в JSON
function getUserRoles(number){
    if (number == 2){
        return [{id: 2, name: 'ROLE_ADMIN'}]
    }else{
        return [{id: 1, name: 'ROLE_USER'}]    
    }
}
