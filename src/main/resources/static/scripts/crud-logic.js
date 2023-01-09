// changes the form mode to the given form mode
function set_form_mode(mode, action_button_id, key_field_id) {
    form_mode_lock(mode);
    form_mode_hide(mode);
    let actionButton = document.getElementById(action_button_id);
    clear();
    switch (mode) {
        case 'a': // add
            actionButton.value = 'Hinzufügen';
            actionButton.innerHTML = '<span class="material-symbols-outlined">add</span>';
            actionButton.setAttribute('onclick','add(); ');
            get_newest_key();
            break;
        default:
        case 's': // search
            actionButton.value = 'Öffnen';
            actionButton.setAttribute('onclick'
                ,'search()');
            document.getElementById(key_field_id).value = '';
            break;
        case 'u': // update
            read();
            actionButton.value = 'Aktualisieren';
            actionButton.setAttribute('onclick','update();');
            break;
    }
}


// set all 'lockable' objects (classname) to locked if object has class 'lock-mode-[param]'; [param] === 'mode' parameter
function form_mode_lock(mode) {
    let inputs = document.getElementsByClassName('lockable');
    for (let i = 0; i < inputs.length; i++) {
        let input = inputs.item(i);
        input.disabled = false;
        if (input.classList.contains('lock-mode-' + mode)) input.disabled = true;
    }
}

function form_mode_hide(mode){
    let inputs = document.getElementsByClassName('hideable');
    for (let i = 0; i < inputs.length; i++) {
        let input = inputs.item(i);
        input.classList.remove('hidden');
        if (input.classList.contains('hide-mode-' + mode)) input.classList.add('hidden');
    }
}
// call this at the end of the file. action_id = button to activate action. Returns id which has been given in the url
function load(action_id, key_field_id) {
    let url = new URL(window.location.href)
    let mode = url.searchParams.get('v');
    let id = url.searchParams.get('id');
    let key_field = document.getElementById(key_field_id);
    switch (mode) {
        case 'a':
            let key = get_newest_key();
            key_field.value = key;
            set_form_mode(mode, action_id, key_field_id);
            break;
        case 'u':
            if (id === undefined || id == null || id.trim() === '') {
                key_field.value = '';
                set_form_mode('s', action_id, key_field_id);
            } else {
                key_field.value = id;
                set_form_mode('u', action_id, key_field_id);
            }
            break;
        default:
            key_field.value = '';
            set_form_mode('s', action_id, key_field_id);
            break;
    }

    fill_selections(); // set selection options with data
    custom_params(url.searchParams);

}