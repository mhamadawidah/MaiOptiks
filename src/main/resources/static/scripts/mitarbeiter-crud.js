// Implement these function in your logic
function get_newest_key() {
    // if there is an api endpoint for this use that instead!
    doGetRequest('/api/mitarbeiters', '', (data) => {
        let len = data.length;
        document.getElementById('mitarbeiternr')
            .value = data[len - 1]['mitarbeiterNr'] + 1;
    });
}

function clear() {
    let clearable = document.getElementsByClassName('data-clear');
    for (let i = 0; i < clearable.length; i++) {
        clearable.item(i).value = '';
        clearable.item(i).removeAttribute('value');
    }
}

function read() {
    let key = document.getElementById('mitarbeiternr').value;
    doGetRequest('/api/mitarbeiters/', key, (data) => {
        let objects = document.getElementsByClassName('data');
        for (let i = 0; i < objects.length; i++) {
            let obj = objects.item(i);
            obj.value = data[obj.id];
        }
        checkplz();
    });
}

function add() {
    let data = build_json('data-a');
    doPostRequest('/api/mitarbeiters/', data, (data) => {
        document.getElementById('mitarbeiternr').value = data;
        set_form_mode('u', 'crud1', 'mitarbeiternr');
    });
    // add to db with data
}

function update() {
    let key = document.getElementById('mitarbeiternr').value;
    let data = build_json('data-u');
    doPutRequest('/api/mitarbeiters/', key, data, (data) => {
        // do nothing?!
    });
    // update in db with data
}

function search() {
    // gets called if user tries to search.
    // Get all data from none locked files (<input disabled>)
    // Perform search
    // Set data id to id field
    let key = document.getElementById('mitarbeiternr').value;

    doGetRequest('/api/mitarbeiters/', key, (data) => {
        document.getElementById('mitarbeiternr').value = data['mitarbeiterNr'];
        set_form_mode('u', 'crud1', 'mitarbeiternr');
    });

}

function fill_selections() {
    // Fill all selections here (with options from the api f.i.)
}

function build_json(group_class) {
    let data = '{';
    let update = document.getElementsByClassName(group_class);
    for (let i = 0; i < update.length; i++) {
        let obj = update.item(i);
        data += JSON.stringify(obj.id) + ':' + JSON.stringify(obj.value) + ',';
    }
    data = data.substring(0, data.length - 1);
    data += '}';
    return data;
}