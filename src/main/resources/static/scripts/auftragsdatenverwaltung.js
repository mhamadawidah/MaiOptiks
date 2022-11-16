//
// SITE MODE LOGIC
//

function set_tab(tab_id) {
    let tabs = document.getElementsByClassName('tab');

    for (let i = 0; i < tabs.length; i++) {
        let tab = tabs.item(i);

        tab.classList.remove('hidden');

        if (tab.id != 'tab-' + tab_id)
            tab.classList.add('hidden')
    }
}

//
// LOADING DATA LOGIC
//

function get_newest_key() {
    doGetRequest('/api/auftrags/getNextId', '', (data) => {
        document.getElementById('auftragsnr').value = data;
    });
}

function fill_selections() {
    let kundeSelection = document.getElementById('selection-kunde');

    doGetRequest('/api/kundes?name=%2A&vorname=%2A', '', (data) => {
        for (let i = 0; i < data.length; i++) {
            var opt = document.createElement('option');
            opt.value = data[i]['kundenNr'];
            opt.innerHTML = data[i]['vorname'] + ' ' + data[i]['name']
            kundeSelection.appendChild(opt);
        }
    });

    let auftragsartenSelection = document.getElementById('selection-abrechnungsart');

    doGetRequest('/api/abrechnungsarts', '', (data) => {
        for (let i = 0; i < data.length; i++) {
            var opt = document.createElement('option');
            opt.value = data[i]['id'];
            opt.innerHTML = data[i]['art'];
            auftragsartenSelection.appendChild(opt);
        }
    });

    let farbeSelection = document.getElementById('select-farbe');
    doGetRequest('/api/farbes', '', (data) => {
        for (let i = 0; i < data.length; i++) {
            var opt = document.createElement('option');
            opt.value = data[i]['farbeid'];
            opt.innerHTML = data[i]['bezeichnung'];
            opt.style = 'background: ' + data[i]['info'];
            farbeSelection.appendChild(opt);
        }
    });

    let werkstattSelection = document.getElementById('werkstatt');
    let beraterSelection = document.getElementById('berater');

    doGetRequest('/api/mitarbeiters', '', (data) => {
        for (let i = 0; i < data.length; i++) {
            var opt = document.createElement('option');
            opt.value = data[i]['mitarbeiterNr'];
            opt.innerHTML = data[i]['name'] + ' ' + data[i]['vorname'];
            werkstattSelection.appendChild(opt);
        }
    });
    doGetRequest('/api/mitarbeiters', '', (data) => {
        for (let i = 0; i < data.length; i++) {
            var opt = document.createElement('option');
            opt.value = data[i]['mitarbeiterNr'];
            opt.innerHTML = data[i]['name'] + ' ' + data[i]['vorname'];
            beraterSelection.appendChild(opt);
        }
    });

    let materialSelection = document.getElementById('select-material');
    doGetRequest('/api/materials', '', (data) => {
        for (let i = 0; i < data.length; i++) {
            var opt = document.createElement('option');
            opt.value = data[i]['materialId'];
            opt.innerHTML = data[i]['bezeichung'];
            materialSelection.appendChild(opt);
        }
    });

    let lieferantSelection = document.getElementById('select-lieferant');
    doGetRequest('/api/lieferants', '', (data) => {
        for (let i = 0; i < data.length; i++) {
            var opt = document.createElement('option');
            opt.value = data[i]['lieferantId'];
            opt.innerHTML = data[i]['name'];
            lieferantSelection.appendChild(opt);
        }
    });
}

function clear() {
    let datas = document.getElementsByClassName('data');
    for (let i = 0; i < datas.length; i++) {
        let data = datas.item(i);
        data.value = '';
        data.checked = '';
        data.removeAttribute('value');
    }
}

function search() {
    let key = document.getElementById('auftragsnr').value;

    doGetRequest('/api/auftrags/', key, (data) => {
        document.getElementById('auftragsnr').value = data['auftragsnummer'];
        set_form_mode('u', 'exit-logic-1', 'auftragsnr');
    });
}

// add all data to database
function add() {
    let auftragsdaten = build_json('data-auftrag');
    doPostRequest('/api/auftrags', auftragsdaten, (data) => {
        document.getElementById('auftragsnr').value = data;
        set_form_mode('u', 'exit-logic-1', 'auftragsnr');
    });
}

// update all data in database
function update() {
    let key = document.getElementById('auftragsnr').value;
    let auftragsdaten = build_json('data-auftrag');
    doPutRequest('/api/auftrags/', key, auftragsdaten, (data) => {
        // do nothing
    })

}

// read data from database
function read() {
    let key = document.getElementById('auftragsnr').value;
    doGetRequest('/api/auftrags/', key, (data) => {
        let auftragData = document.getElementsByClassName('data-auftrag');
        for (let i = 0; i < auftragData.length; i++) {
            let dataA = auftragData.item(i);
            if (dataA.type === 'checkbox') {
                dataA.checked = data[dataA.id];
            } else
                dataA.value = data[dataA.id];
        }
    });
}

function get_data_from_input() {
    let data = document.getElementsByClassName('data');
    for (let i = 0; i < data.length; i++) {
        let table = data.item(i).getAttribute('table');

    }
}

function set_tabs_visibility(selection) {
    clear();
    let class_id = '';
    if (selection.selectedIndex == 0) {
        class_id = 'br';
    } else {
        class_id = 'cl';

    }
    unhide_tabs('type-' + class_id);
    let extra = document.getElementsByClassName('extra-logic-cl');
    for (let i = 0; i < extra.length; i++) {
        let button = extra.item(i);
        button.classList.remove('hidden');
        if (!button.classList.contains('extra-logic-' + class_id)) {
            button.classList.add('hidden');
        }
    }
}

function unhide_tabs(class_id) {
    let buttons = document.getElementById('tab-buttons').children;
    for (let i = 0; i < buttons.length; i++) {
        let button = buttons.item(i);
        button.classList.remove('hidden');
        if (!button.classList.contains(class_id)) {
            button.classList.add('hidden');
        }
    }
    set_tab(1);
}

function build_json(group_class) {
    let data = '{';
    let update = document.getElementsByClassName(group_class);
    for (let i = 0; i < update.length; i++) {
        let obj = update.item(i);
        if (obj.type === 'checkbox') {
            data += JSON.stringify(obj.id) + ':' + JSON.stringify(obj.value === 'on') + ',';
        } else
            data += JSON.stringify(obj.id) + ':' + JSON.stringify(obj.value) + ',';
    }
    data = data.substring(0, data.length - 1);
    data += '}';
    return data;
}

