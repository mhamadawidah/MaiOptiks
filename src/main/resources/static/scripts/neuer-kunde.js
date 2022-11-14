const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
console.log("Params", urlParams.get('neu'))

const form = document.getElementById("form");

const radios = [document.getElementById("anrede1"), document.getElementById("anrede2"), document.getElementById("anrede3")]
const name = document.getElementById("name")
const vorname = document.getElementById("vorname")
const strasse = document.getElementById("strasse")
const hausNr = document.getElementById("hausnummer")
const geburtsdatum = document.getElementById("geburtsdatum")
const telefonNr = document.getElementById("telefon")
const handy = document.getElementById("mobil")
const email = document.getElementById("mail")
const versicherungsNr = document.getElementById("versicherung")
const gueltigkeit = document.getElementById("gueltigkeit")
const plz = document.getElementById("plz")
const krankenkassenNr = document.getElementById("krankenkasse")


if (urlParams.get('neu') === true || urlParams.get("neu") === null) {
    console.log("Neu anlegen")
    document.getElementById("submit-button").style.visibility = "visible";
    document.getElementById("button-bearbeiten").style.visibility = "hidden";

} else {
    document.getElementById("submit-button").style.visibility = "hidden";
    document.getElementById("button-bearbeiten").style.visibility = "visible";

    radios.forEach((radio) => {radio.disabled = true})
    name.disabled = true
    vorname.disabled = true
    strasse.disabled = true
    hausNr.disabled = true
    geburtsdatum.disabled = true
    telefonNr.disabled = true
    handy.disabled = true
    email.disabled = true
    versicherungsNr.disabled = true
    gueltigkeit.disabled = true
    plz.disabled = true
    krankenkassenNr.disabled = true
    fillTextFields();
}

function setBearbeiten() {
    console.log("qwertzui")
    radios.forEach((radio) => {radio.disabled = false})
    name.disabled = false
    vorname.disabled = false
    strasse.disabled = false
    hausNr.disabled = false
    geburtsdatum.disabled = false
    telefonNr.disabled = false
    handy.disabled = false
    email.disabled = false
    versicherungsNr.disabled = false
    gueltigkeit.disabled = false
    plz.disabled = false
    krankenkassenNr.disabled = false

    document.getElementById("button-bearbeiten").innerText = "Speichern";
    document.getElementById("button-bearbeiten").setAttribute('onclick','speichern()')
}

function speichern() {
    doPutRequest(`/api/kundes/${urlParams.get("kunnr")}`, "",  getInputData(urlParams.get("kunnr")), (response) => {
        console.log("RequestResponse PUT: ", response)
        alert('\nÄnderungen gespeichert.');
    })

    radios.forEach((radio) => {radio.disabled = true})
    name.disabled = true
    vorname.disabled = true
    strasse.disabled = true
    hausNr.disabled = true
    geburtsdatum.disabled = true
    telefonNr.disabled = true
    handy.disabled = true
    email.disabled = true
    versicherungsNr.disabled = true
    gueltigkeit.disabled = true
    plz.disabled = true
    krankenkassenNr.disabled = true

    document.getElementById("button-bearbeiten").innerText = "Bearbeiten";
    document.getElementById("button-bearbeiten").setAttribute('onclick','setBearbeiten()')

    console.log("gespeichert")
}

function fillTextFields() {
    if (urlParams.get("anrede") === "Herr") {
        radios[1].checked = true
    } else if (urlParams.get("anrede") === "Frau") {
        radios[0].checked = true
    } else {
        radios[3].checked = true
    }

    name.value = urlParams.get("name")
    vorname.value = urlParams.get("vorname")
    strasse.value = urlParams.get("strasse")
    hausNr.value = urlParams.get("hausnr")
    geburtsdatum.value = urlParams.get("geburtsdatum")
    telefonNr.value = urlParams.get("tel")
    handy.value = urlParams.get("handy")
    email.value = urlParams.get("mail")
    versicherungsNr.value = urlParams.get("vsnr")
    gueltigkeit.value = urlParams.get("gueltigkeit")
    plz.value = urlParams.get("plz")
    krankenkassenNr.value = urlParams.get("kknr")
}

function createKunde() {
    // 1-> Placeholder für POST
    let json = getInputData(1);
    doPostRequest('/api/kundes', json , (response) => {
        console.log("RequestResponse POST: ", response)
        alert('\nKunde angelegt.');
    })
}

function getInputData(kundennr) {
    console.log("Hole Daten...")

    let error = false
    let checkedRadio = checkRadios(radios)

    if (!checkRequired([name, vorname, strasse, hausNr, geburtsdatum, telefonNr, handy, email, versicherungsNr, plz, gueltigkeit, krankenkassenNr])) {return}
    if (checkedRadio == -1) {console.log("Fehler Anrede"); error = true;}
    if (!checkEmail(email)) {console.log("Fehler Anrede"); error = true;}
    if (!checkInput(name, /^[a-zA-Z]+$/) ) {console.log("Fehler Anrede"); error = true;}
    if (!checkInput(vorname, /^[a-zA-Z]+$/)) {console.log("Fehler Anrede"); error = true;}
    if (!checkInput(strasse, /^[a-zA-Z]+$/)) {console.log("Fehler Anrede"); error = true;}
    if (!checkInput(hausNr, /^[0-9]+$/)) {console.log("Fehler Anrede"); error = true;}
    if (!checkInput(telefonNr, /^[0-9 ]+$/)) {console.log("Fehler Anrede"); error = true;}
    if (!checkInput(handy, /^[0-9 ]+$/)) {console.log("Fehler Anrede"); error = true;}
    if (!checkInput(plz, /^\b\d{5}\b/)) {console.log("Fehler Anrede"); error = true;}
    if (!checkInput(versicherungsNr, /[a-zA-Z0-9]+$/)) {console.log("Fehler Anrede"); error = true;}
    if (!checkInput(krankenkassenNr, /[a-zA-Z0-9]+$/)) {console.log("Fehler Anrede"); error = true;}

    if (error) {
        console.log("Irgendwo Error")
        return; // Weitere Ausführung stoppen, falls irgendwo error
    }

    //Json zusammenbauen
    let jsonObject = {
        "kundenNr": parseInt(kundennr),
        "anrede": radios[checkedRadio].value,
        "name": name.value,
        "vorname": vorname.value,
        "strasse": strasse.value,
        "hausNr": hausNr.value,
        "geburtsdatum": geburtsdatum.value,
        "telefonNr": telefonNr.value,
        "handy": handy.value,
        "email": email.value,
        "versicherungsNr": versicherungsNr.value,
        "gueltigkeit": gueltigkeit.value,
        "bemerkung": "",
        "plz": plz.value,
        "krankenkassenNr": krankenkassenNr.value
    }

    console.log("JSONObject: ", jsonObject)

    return jsonObject
}

// Show Error Msg
function showError(input, message) {
    const formControl = input.parentElement;
    formControl.className = "inputs error";
    const small = formControl.querySelector("small");
    small.innerText = message;
}

// Show Success Msg
function showSuccess(input) {
    const formControl = input.parentElement;
    formControl.classList.remove("error");
    formControl.classList.add("success");
}

// Check required fields
function checkRequired(inputArr) {
    inputArr.forEach(function (input) {
        if (input.value.trim() === "") {
            showError(input, `${getFieldName(input)} is required`)
            return false
        } else {
            showSuccess(input);
        }
    });

    return true;
}

function getFieldName(input) {
    return input.id.charAt(0).toUpperCase() + input.id.slice(1);
}

// Check input length
function checkLength(input, min, max) {
    if (input.value.length < min) {
        showError(input, `${getFieldName(input)} must be at least ${min} characters`);
        return false;
    } else if (input.value.length > max) {
        showError(input, `${getFieldName(input)} must be less than ${max} characters`);
        return false;
    }

    showSuccess(input);
    return true;
}

function checkEmail(input) {

    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (re.test(input.value.trim())) {
        showSuccess(input);
        return true;
    }
    showError(input, "E-mail is not Valid");
    return false;
}

function checkInput(input, expression) {
    if (expression.test(input.value.trim())) {
        showSuccess(input);
        return true;
    }

    showError(input, `${getFieldName(input)} is not Valid`);
    return false;
}

function checkRadios(inputArr) {

    for(let i = 0; i < inputArr.length; i++) {
        if (inputArr[i].checked) {
            showSuccess(inputArr[i]);
            return i;
        }
    }

    showError(inputArr[0], `Anrede is not Valid`);
    return -1;
}