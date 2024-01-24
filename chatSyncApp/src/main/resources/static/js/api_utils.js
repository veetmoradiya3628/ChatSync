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

async function getUserInfo(userId) {
    try {
        await $.get('/ChatSync/api/v1/userInfo/' + userId, function (res){
            console.log(JSON.stringify(res));
            selectedGlobalUser = res.data;
        })
    }catch (error){
        console.log(error)
    }
}

async function addContactAPI(reqObject, csrfToken) {
    try {
        $.ajax({
            url: '/ChatSync/api/v1/add-contact',
            type: 'POST',
            headers: {
                'X-CSRF-TOKEN': csrfToken,
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(reqObject),
            success: function(response) {
                // Handle success
                console.log('POST request for addContact successful:', response);
                addContacts();
            },
            error: function(error) {
                // Handle error
                console.error('POST request for addContact failed:', error);
            }
        })
    }catch (error){
        console.log(error)
    }
}