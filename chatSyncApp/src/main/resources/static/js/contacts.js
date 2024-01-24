const contactListSelector = "#contacts-list";
const addContactListSelector = "#add-contact-list";
const btnAddContactSelector = "#addContactButton";
const btnCancelContactAdditionSelector = "#cancelContactAdditionButton";
const globalUsersListContainerRef = "#global-users-container";
const userContactsListContainerRef = "#user-contacts-container";

// globalUserInfo
const globalUserInfoSectionRef = "#globalUserInfoSection";
const globalUserInfoProfileImageRef = "#globalUserInfoProfileImage";
const globalUserInfoStatusRef = "#globalUserInfoStatus";
const globalUserInfoEmailRef = "#globalUserInfoEmail";
const globalUserInfoFirstNameRef = "#globalUserInfoFirstName";
const globalUserInfoLastNameRef = "#globalUserInfoLastName";

const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');

let globalUser = [];
let userContacts = [];
let selectedGlobalUser = null;

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
        await getUserInfo(userId);
    }
    renderGlobalUserInfoOnUI()
}
function renderGlobalUserInfoOnUI(){
    console.log(selectedGlobalUser);
    if(selectedGlobalUser){
        let imgRef = $(globalUserInfoProfileImageRef)
        if(selectedGlobalUser.profileImage){
            imgRef.attr('src', selectedGlobalUser.profileImage);
        }else{
            imgRef.attr('src', 'https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/default-profile-picture-grey-male-icon.png')
        }

        // for status default online as of now
        $(globalUserInfoStatusRef).html(`${selectedGlobalUser.firstName} ${selectedGlobalUser.lastName} <span class="online-status-badge">Online</span>`)
        $(globalUserInfoEmailRef).html(`<b>E-mail : </b> ${selectedGlobalUser.email}`)
        $(globalUserInfoFirstNameRef).html(`<b>First Name : </b> ${selectedGlobalUser.firstName}`)
        $(globalUserInfoLastNameRef).html(`<b>Last Name : </b> ${selectedGlobalUser.lastName}`)
        
        $(globalUserInfoSectionRef).show();
    }else{
        // get userInfo failed
    }
}

async function addUserClickHandler(buttonId){
    console.log(`add User button called for ${buttonId}`)
    let userId = buttonId.split("_")[1]
    console.log(`function called with userId : ${userId}`)

    let reqObject = {
        userId: getCookie("loggedInUserId"),
        contactId: userId
    }

    console.log(JSON.stringify(reqObject))
    await addContactAPI(reqObject, csrfToken)
}
