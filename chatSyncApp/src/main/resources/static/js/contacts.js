const contactListSelector = "#contacts-list";
const addContactListSelector = "#add-contact-list";
const btnAddContactSelector = "#addContactButton";
const btnCancelContactAdditionSelector = "#cancelContactAdditionButton";
const globalUsersListContainerRef = "#global-users-container";
const userContactsListContainerRef = "#user-contacts-container";

// globalUserInfo
const globalUserInfoSectionRef = "#globalUserInfoSection";
const userInfoSectionRef = "#userDetailInfo";

const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');

let globalUser = [];
let userContacts = [];
let selectedGlobalUser = null;
let selectedUser = null;

const searchUserRef = "#searchContact";

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
    $(globalUserInfoSectionRef).hide();

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

async function showContactsList(){
    console.log("cancelContactAdditionButton clicked...")
    $(addContactListSelector).hide()
    await fetchContactUsersDataForLoggedInUser();
    renderUserContactsOnUI();
    $(contactListSelector).show()
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

async function globalUserInfoClickHandler(buttonId)
{
    console.log(`userInfo button called for ${buttonId}`)
    let userId = buttonId.split("_")[1]
    console.log(`function called with userId : ${userId}`)
    if(selectedGlobalUser === null || (selectedGlobalUser && selectedGlobalUser.id !== userId)){
        const res = await getUserInfo(userId);
        console.log(res);
        selectedGlobalUser = res.data;
    }
    renderGlobalUserInfoOnUI();
}

async function showContactInfo(userId){
    console.log(`showUserInfo called with userId : ${userId}`);
    if(selectedUser === null || (selectedUser && selectedUser.id !== userId)){
        const res = await getUserInfo(userId);
        console.log(res);
        selectedUser = res.data;
    }
    renderSelectedUserInfoOnUI();
}

function renderGlobalUserInfoOnUI(){
    console.log(selectedGlobalUser);
    if(selectedGlobalUser){
        generateUserInformation(selectedGlobalUser, globalUserInfoSectionRef);
        $(globalUserInfoSectionRef).show();
    }else{
        // get userInfo failed
    }
}

function renderSelectedUserInfoOnUI(){
    console.log(selectedUser)
    if (selectedUser){
        generateUserInformation(selectedUser, userInfoSectionRef);
        $(userInfoSectionRef).show();
    }else{
        // no user to show
    }
}

async function addUserClickHandler(buttonId){
    console.log(`add User button called for ${buttonId}`)
    let userId = buttonId.split("_")[1]
    console.log(`function called with userId : ${userId}`)

    let userResp = window.confirm('Are you sure want to add contact to you contact list ?');
    if (userResp){
        let reqObject = {
            userId: getCookie("loggedInUserId"),
            contactId: userId
        }

        console.log(JSON.stringify(reqObject))
        await addContactAPI(reqObject, csrfToken)
    }else{
        console.log('user contact addition cancelled by user!!');
    }
}

async function deleteContact(contactId) {
    console.log('delete contact function called with contactID : ' + contactId);

    let userResp = window.confirm('Are you sure want to delete contact from you contact list ?');
    if (userResp) {
        console.log('user selected true for user contact remove operation, processing for user contact delete');
        let userId = getCookie("loggedInUserId");
        await deleteUserContact(userId, contactId);
    } else {
        console.log('cancelled user contact remove operation!!')
    }
}

/*
 function to handle user search for contacts
 */
function searchContact(){
    const value = $(searchUserRef).val();
    if (value.length > 2){
        console.log(value);

        let filteredContacts = filterContact(value.toLowerCase());
        console.log(filteredContacts);
    }
}

function filterContact(value) {
    let filteredUser = []
    userContacts.forEach(user => {
        if (user.firstName.toLowerCase().includes(value) || user.lastName.toLowerCase().includes(value)){
            filteredUser.push(user)
        }
    })
    return filteredUser;
}