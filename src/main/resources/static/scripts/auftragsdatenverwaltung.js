//
// SITE MODE LOGIC
//

function set_tab(tab_id) {
    let tabs = document.getElementsByClassName('tab');

    for (let i = 0; i < tabs.length; i++) {
        let tab = tabs.item(i);
        // remove hidden tag
        let classes = tab.getAttribute('class');

        if (classes != 'tab')
            classes = classes.replace(' hidden', '');

        // add hidden tag if
        if (tab.id != 'tab-' + tab_id)
            classes += ' hidden';

        tab.setAttribute('class', classes);
    }
}

//
// LOADING DATA LOGIC
//

function get_newest_key() {
    return 1; //return the key you have got from somewhere (api)
}

function fill_selections() {

}

function clear(){

}

function search() {
    return 2;
}

// add all data to database
function add() {

}

// update all data in database
function update() {
    set_form_mode('s');
}

// read data from database
function read() {
    let key = document.getElementById('auftragsnr').value;
    doGetRequest('/api/auftrags/', key, (data) => {


        alert(data);
    });
}

function get_data_from_input(){
    let data = document.getElementsByClassName('data');
    for (let i = 0; i < data.length; i++){
        let table = data.item(i).getAttribute('table');

    }
}