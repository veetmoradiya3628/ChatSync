const contactListSelector = "#contacts-list";
const addContactListSelector = "#add-contact-list";
const btnAddContactSelector = "#addContactButton";
const btnCancelContactAdditionSelector = "#cancelContactAdditionButton";
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');

/* function will call when document will be loaded completely */
$(document).ready(function() {
    console.log("contact.js loaded ...")
    $(addContactListSelector).hide()

    $(btnAddContactSelector).click(addContacts);
    $(btnCancelContactAdditionSelector).click(showContactsList);
})

function addContacts(){
    console.log("addContactButton clicked...")
    console.log(csrfToken);
    $(contactListSelector).hide();
    $(addContactListSelector).show();
}

function showContactsList(){
    console.log("cancelContactAdditionButton clicked...")

    $(addContactListSelector).hide()
    $(contactListSelector).show()
}

function filterContacts(presenceStatus) {
    console.log(`filterContacts called with ${presenceStatus}`);
}