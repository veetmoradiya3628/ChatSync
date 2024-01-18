const btnAddContactSelector = "#addContactButton";
const contactListSelector = "#contacts-list";
const addContactListSelector = "#add-contact-list";
const btnCancelContactAdditionSelector = "#cancelContactAdditionButton";
$(document).ready(function() {
    console.log("contact.js loaded ...")
    $(addContactListSelector).hide()

    $(btnAddContactSelector).click(function() {
        console.log("addContactButton clicked...")

        $(contactListSelector).hide()
        $(addContactListSelector).show()
    })

    $(btnCancelContactAdditionSelector).click(function() {
        console.log("cancelContactAdditionButton clicked...")

        $(addContactListSelector).hide()
        $(contactListSelector).show()
    })


})