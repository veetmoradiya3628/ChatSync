function globalContactTileGenerator(contactData){
    const row = document.createElement('div');
    row.className = 'row mt-1 p-1';

    // Create columns and elements within the row

    let avatarUrl = contactData.profileImage ? contactData.profileImage : 'https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/default-profile-picture-grey-male-icon.png'
    const avatarElement = document.createElement('div');
    avatarElement.className = 'col-2';
    avatarElement.innerHTML ='<img src="' + avatarUrl + '" alt="Avatar" class="avatar"></div>';

    const metaInfoElement = document.createElement('div');
    metaInfoElement.className = 'col-6';
    metaInfoElement.innerHTML = '<div class="pl-10">' +
        '<strong><i class="fas fa-circle text-success"></i> ' + contactData.firstName + ' ' + contactData.lastName + '</strong>' +
        '<p>Last message goes here</p>' +
        '</div>';

    const actionElement = document.createElement('div');
    actionElement.className = 'col-4 text-right';
    actionElement.innerHTML = '<button id="btnGlobalUserInfo_'+contactData.id+'" onclick="globalUserInfoClickHandler(id)" class="btn btn-info"><i class="bi bi-info-circle-fill mr-5"></i>Info</button>' +
        '<button id="btnAddUser_'+contactData.id+'" class="btn btn-primary" onclick="addUserClickHandler(id)"><i class="bi bi-person-fill-add mr-5"></i>Add</button>';

    // Append columns to the row
    row.appendChild(avatarElement);
    row.appendChild(metaInfoElement);
    row.appendChild(actionElement);

    return row;
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
    const chatButton = createButton(userData.id, 'btn btn-light', 'bi bi-chat-left-fill mr-5', 'Chat');
    const infoButton = createButton(userData.id, 'btn btn-info', 'bi bi-info-circle-fill mr-5', 'Info');
    const deleteButton = createButton(userData.id, 'btn btn-danger', 'bi bi-trash-fill mr-5', 'Delete');

    userAction.appendChild(chatButton);
    userAction.appendChild(infoButton);
    userAction.appendChild(deleteButton);

    // Append columns to the main row container
    row.appendChild(colAvatar);
    row.appendChild(userInfo);
    row.appendChild(userAction);

    return row;
}

function createButton(id, className, iconClass, text) {
    const button = document.createElement('button');
    button.className = className;
    const icon = document.createElement('i');
    icon.className = iconClass;
    button.appendChild(icon);
    button.innerHTML += text;

    if(text === 'Delete'){
        button.onclick = function (){
            deleteContact(id).then(r => {});
        }
    }else if (text === 'Info'){
        button.onclick = function (){
            showContactInfo(id).then(r => {});
        }
    }else if (text === 'Chat'){

    }

    return button;
}

function generateUserInformation(userData, containerRef) {
    const container = document.querySelector(containerRef);
    $(container).empty();

    const userInformationDiv = document.createElement("div");
    userInformationDiv.className = "col-6";

    const heading = document.createElement("h3");
    heading.textContent = "User Information";
    userInformationDiv.appendChild(heading);

    const avatarImg = document.createElement("img");
    avatarImg.src = userData.profileImage ? userData.profileImage : 'https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/default-profile-picture-grey-male-icon.png';
    avatarImg.alt = "Avatar";
    avatarImg.className = "contact-user-info-profile-image mt-2";
    userInformationDiv.appendChild(avatarImg);

    const statusBadge = document.createElement("span");
    statusBadge.className = 1 === 1 ? "online-status-badge" : "offline-status-badge";
    statusBadge.textContent = 1 === 1 ? "Online" : "Offline";
    userInformationDiv.appendChild(document.createTextNode(`${userData.firstName} ${userData.lastName} is `));
    userInformationDiv.appendChild(statusBadge);

    const emailDiv = createInfoDiv("E-mail", userData.email);
    userInformationDiv.appendChild(emailDiv);

    const firstNameDiv = createInfoDiv("First Name", userData.firstName);
    userInformationDiv.appendChild(firstNameDiv);

    const lastNameDiv = createInfoDiv("Last Name", userData.lastName);
    userInformationDiv.appendChild(lastNameDiv);

    container.appendChild(userInformationDiv);
}

// Helper function to create information divs
function createInfoDiv(label, value) {
    const infoDiv = document.createElement("div");
    infoDiv.className = "mt-1";
    const labelElement = document.createElement("b");
    labelElement.textContent = `${label} : `;
    infoDiv.appendChild(labelElement);
    infoDiv.appendChild(document.createTextNode(value));
    return infoDiv;
}
