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

function globalContactTileGenerator(contactData){
    const row = document.createElement('div');
    row.className = 'row mt-1 p-1';

    // Create columns and elements within the row

    let avatarUrl = contactData.profileImage ? contactData.profileImage : 'https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/default-profile-picture-grey-male-icon.png'
    const avatarElement = document.createElement('div');
    avatarElement.className = 'col-2';
    avatarElement.innerHTML = '<input class="form-check-input" id="flexCheckIndeterminate" type="checkbox" value="">' +
        '<img src="' + avatarUrl + '" alt="Avatar" class="avatar">' +
        '</div>';

    const metaInfoElement = document.createElement('div');
    metaInfoElement.className = 'col-6';
    metaInfoElement.innerHTML = '<div class="pl-10">' +
        '<strong><i class="fas fa-circle text-success"></i> ' + contactData.firstName + ' ' + contactData.lastName + '</strong>' +
        '<p>Last message goes here</p>' +
        '</div>';

    const actionElement = document.createElement('div');
    actionElement.className = 'col-4 text-right';
    actionElement.innerHTML = '<button class="btn btn-info"><i class="bi bi-info-circle-fill mr-5"></i>Info</button>';

    // Append columns to the row
    row.appendChild(avatarElement);
    row.appendChild(metaInfoElement);
    row.appendChild(actionElement);

    return row;
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

function userContactsTileGenerator(userData){
    const row = document.createElement('div');
    row.className = 'row mt-1 p-1';

    // Column 1: Avatar
    const colAvatar = document.createElement('div');
    colAvatar.className = 'col-1';
    const avatarImg = document.createElement('img');
    avatarImg.src = userData.profileImage ? userData.profileImage : 'https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/default-profile-picture-grey-male-icon.png';
    avatarImg.alt = 'Avatar';
    avatarImg.className = 'avatar';
    colAvatar.appendChild(avatarImg);

    // Column 2: User information
    const userInfo = document.createElement('div');
    userInfo.className = 'col-5';
    const userInfoDiv = document.createElement('div');
    userInfoDiv.className = 'pl-10';
    const statusIcon = document.createElement('i');
    statusIcon.className = 'fas fa-circle text-success';
    const strongTag = document.createElement('strong');
    strongTag.appendChild(statusIcon);
    strongTag.innerHTML += ' ' + userData.firstName + ' ' + userData.lastName;
    const messageParagraph = document.createElement('p');
    messageParagraph.textContent = 'Last message goes here';
    userInfoDiv.appendChild(strongTag);
    userInfoDiv.appendChild(messageParagraph);
    userInfo.appendChild(userInfoDiv);

    // Column 3: Buttons (Chat, Info, Delete)
    const userAction = document.createElement('div');
    userAction.className = 'col-6 text-right';
    const chatButton = createButton('btn btn-light', 'bi bi-chat-left-fill mr-5', 'Chat');
    const infoButton = createButton('btn btn-info', 'bi bi-info-circle-fill mr-5', 'Info');
    const deleteButton = createButton('btn btn-danger', 'bi bi-trash-fill mr-5', 'Delete');
    userAction.appendChild(chatButton);
    userAction.appendChild(infoButton);
    userAction.appendChild(deleteButton);

    // Append columns to the main row container
    row.appendChild(colAvatar);
    row.appendChild(userInfo);
    row.appendChild(userAction);

    return row;
}

function createButton(className, iconClass, text) {
    const button = document.createElement('button');
    button.className = className;
    const icon = document.createElement('i');
    icon.className = iconClass;
    button.appendChild(icon);
    button.innerHTML += text;
    return button;
}

function filterContacts(presenceStatus) {
    console.log(`filterContacts called with ${presenceStatus}`);
}