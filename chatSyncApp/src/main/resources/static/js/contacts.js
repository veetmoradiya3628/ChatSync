const contactListSelector = "#contacts-list";
const addContactListSelector = "#add-contact-list";
const btnAddContactSelector = "#addContactButton";
const btnCancelContactAdditionSelector = "#cancelContactAdditionButton";
const globalUsersListContainerRef = "#global-users-container";
const userContactsListContainerRef = "#user-contacts-container";

const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');

let globalUser = [];
let userContacts = [];

/* function will call when document will be loaded completely */
$(document).ready(function() {
    console.log("contact.js loaded ...")
    $(addContactListSelector).hide()

    $(btnAddContactSelector).click(addContacts);
    $(btnCancelContactAdditionSelector).click(showContactsList);
})

async function addContacts() {
    console.log("addContactButton clicked...")
    console.log(csrfToken);
    $(contactListSelector).hide();

    await fetchGlobalUsersDataForLoggedInUser();
    renderGlobalUsersOnUI();

    $(addContactListSelector).show();
}

function renderGlobalUsersOnUI() {
    let globalUserListContainer = document.querySelector(globalUsersListContainerRef);
    if (globalUser){
        $(globalUsersListContainerRef).empty();
        globalUser.forEach(function (userdata){
            globalUserListContainer.appendChild(globalContactTileGenerator(userdata));
        })
    }else{
        $(globalUsersListContainerRef).append($('<p>No Users Data to Show</p>'))
    }
}
async function fetchGlobalUsersDataForLoggedInUser(){
    try {
        let userId = getCookie('loggedInUserId');
        await $.get('/ChatSync/api/v1/get-global-contacts/' + userId, function (data){
            console.log(JSON.stringify(data));
            globalUser = data.data;
            console.log(globalUser);
        })
    }catch (error){
        console.log(error)
    }
}
async function showContactsList(){
    console.log("cancelContactAdditionButton clicked...")
    $(addContactListSelector).hide()

    await fetchContactUsersDataForLoggedInUser();
    renderUserContactsOnUI();

    $(contactListSelector).show()
}

async function fetchContactUsersDataForLoggedInUser(){
    try {
        let userId = getCookie('loggedInUserId');
        await $.get('/ChatSync/api/v1/get-contacts/' + userId, function (data){
            console.log(JSON.stringify(data));
            userContacts = data.data.contacts;
            console.log(userContacts);
        })
    }catch (error){
        console.log(error)
    }
}

function renderUserContactsOnUI() {
    let userContactsListContainer = document.querySelector(userContactsListContainerRef);
    if (userContacts){
        $(userContactsListContainerRef).empty();
        $(userContactsListContainerRef).append($('<div></div>'))
        userContacts.forEach(function (userdata){
            userContactsListContainer.appendChild(userContactsTileGenerator(userdata));
        })
    }else{
        $(userContactsListContainerRef).append($('<p>No Users Data to Show</p>'))
    }
}

function filterContacts(presenceStatus) {
    console.log(`filterContacts called with ${presenceStatus}`);
}

function globalUserInfoClickHandler(buttonId)
{
    console.log(`userInfo button called for ${buttonId}`)
}

function addUserClickHandler(buttonId){
    console.log(`userInfo button called for ${buttonId}`)
}