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
    let key = document.getElementById('Auftragsnummer').value;
    doGetRequest('/api/auftrags/', key, (data) => {
        let auftrag = data.item(0);
        let dataObjects = document.getElementsByClassName('data-auftrag');
        for (let i = 0; i < dataObjects.length; i++) {
            let dataObject = dataObjects.item(i);
            switch (dataObject.id) {
                case "rezepturvorhanden":
                    dataObject.value = auftrag.rezepturvorhanden;
                    break;
                case   "womit":
                    dataObject.value = auftrag.womit;
                    break;
                case   "wann":
                    dataObject.value = auftrag.wann;
                    break;
                case   "fertig":
                    dataObject.value = auftrag.fertig;
                    break;
                case   "abgeholt":
                    dataObject.value = auftrag.abgeholt;
                    break;
                case   "bezahlt":
                    dataObject.value = auftrag.bezahlt;
                    break;
                case   "auftragsbestaetigung":
                    dataObject.value = auftrag.auftragsbestaetigung;
                    break;
                case   "rechnung":
                    dataObject.value = auftrag.rechnung;
                    break;
                case  "ersteMahnung":
                    dataObject.value = auftrag.ersteMahnung;
                    break;
                case  "zweiteMahnung":
                    dataObject.value = auftrag.zweiteMahnung;
                    break;
                case  "dritteMahnung":
                    dataObject.value = auftrag.dritteMahnung;
                    break;
                case  "datum":
                    dataObject.value = auftrag.datum;
                    break;
                case "kundenNr":
                    // query more data
                    break;
                case  "werkstatt":
                    // query more data
                    break;
                case "refraktion":
                    // query more data
                    break;
                case "abrechnungs":
                    // query more data
                    break;
            }
        }
        alert(auftrag);
    });
}