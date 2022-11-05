function getInputData() {
    console.log("Hole Daten...")

    const form = document.getElementById("form");

    let anrede = ""
    if (document.getElementById('anrede1').checked) {
      anrede = document.getElementById('anrede1')
    } else {
        anrede = document.getElementById('anrede2')
    }
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

    let error = false

    if (!checkRequired([name, vorname, strasse, hausNr, geburtsdatum, telefonNr, handy, email, versicherungsNr, plz, gueltigkeit, krankenkassenNr])) {return}
    if (!checkEmail(email)) {error = true;}
    if (!checkInput(name, /^[a-zA-Z]+$/) ) {error = true;}
    if (!checkInput(vorname, /^[a-zA-Z]+$/)) {error = true;}
    if (!checkInput(strasse, /^[a-zA-Z]+$/)) {error = true;}
    if (!checkInput(hausNr, /^[0-9]+$/)) {error = true;}
    if (!checkInput(telefonNr, /^[0-9]+$/)) {error = true;}
    if (!checkInput(handy, /^[0-9]+$/)) {error = true;}
    if (!checkInput(plz, /^\b\d{5}\b/)) {error = true;}
    if (!checkInput(versicherungsNr, /[a-zA-Z0-9]+$/)) {error = true;}
    if (!checkInput(krankenkassenNr, /[a-zA-Z0-9]+$/)) {error = true;}
    
    if (error) {
        console.log("Irgendwo Error")
        return; // Weitere Ausführung stoppen, falls irgendwo error     //Json zusammenbauen
    }

    let jsonObject = {
        "kundenNr": 1, // Placeholder
        "anrede": anrede.value,
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
        "plz": plz.value,
        "krankenkassenNr": krankenkassenNr.value
    }

    console.log("JSONObject", jsonObject)

    doPostRequest('/api/kundes',  jsonObject, (response) => {
        console.log("Hier bin ich", response)
    })

    name.value = ""
    vorname.reset()
    strasse.reset()
    hausNr.reset()
    geburtsdatum.reset()
    telefonNr.reset()
    handy.reset()
    email.reset()
    versicherungsNr.reset()
    gueltigkeit.reset()
    plz.reset()
    krankenkassenNr.reset()
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
    } else {
        showSuccess(input);
    }

    return true;
}


//.toLowerCase()

function checkEmail(input) {

    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (re.test(input.value.trim())) {
        showSuccess(input);
    } else {
        showError(input, "E-mail is not Valid");
        return false;
    }

    return true;
}

function checkInput(input, expression) {
    console.log("aaa", expression, "a", input.value, "qqq" ,expression.test(input.value.trim()))
    if (expression.test(input.value.trim())) {
        showSuccess(input);
    } else {
        showError(input, `${getFieldName(input)} is not Valid`);
        return false
    }

    return true;
}