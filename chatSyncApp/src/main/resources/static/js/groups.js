const groupInfoSelector = "#group-info-area";
const addGroupSelector = "#add-group-section";

$(document).ready(function() {
    console.log('group.js loaded...')
    $(addGroupSelector).hide();
})

function createGroupBtnHandler(){
    $(groupInfoSelector).hide();
    $(addGroupSelector).show();
}

function cancelGroupCreationHandler(){
    $(groupInfoSelector).show();
    $(addGroupSelector).hide();
}