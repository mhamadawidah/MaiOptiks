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

function set_form_mode(mode) {
    form_mode_lock(mode);
    let actionButton = document.getElementById('save-cancel-1');
    switch (mode) {
        default: // add
            let key = get_newest_key();
            document.getElementById('Auftragsnummer').value = key;

            actionButton.value = 'Hinzufügen';
            actionButton.onclick = 'add()';
            break;
        case 's': // search
            document.getElementById('Auftragsnummer').value = '';
            actionButton.value = 'Öffnen';
            actionButton.onclick = 'read()';
            break;
        case 'u': // update

            actionButton.value = 'Aktuallisieren';
            actionButton.onclick = 'update()';
            break;
    }
}

function get_newest_key() {
    return 1;
}

function form_mode_lock(mode) {
    let inputs = document.getElementsByClassName('lockable');
    for (let i = 0; i < inputs.length; i++) {
        let input = inputs.item(i);
        input.disabled = false;
        if (input.classList.contains('lock-mode-' + mode))
            input.disabled = true;
    }
}

//
// LOADING SITE LOGIC
//

function fill_selections() {

}

function preload() {
    let url = new URL(window.location.href)
    let mode = url.searchParams.get('v');
    let id = url.searchParams.get('id');
    if (id === undefined || id == null || id === '')
        id = '';
    else mode = 'v';

    set_tab(1);
    fill_selections(); // set selection options with data

    if (!(mode === undefined || mode === '')) {
    } else {
        mode = 's';
    }
    set_form_mode(mode); // sets button to mode equivalent text (e.g. Suchen, Speichern, Hinzufügen)
}

//
// LOADING DATA LOGIC
//

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
    set_form_mode('u');
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