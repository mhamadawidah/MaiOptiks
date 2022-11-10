

function getInputData() {
    console.log("Hole Daten...")

    if (document.getElementById('anrede1').checked) {
      rate_value = document.getElementById('anrede1').value;
    }
    const anrede = document.querySelector('input[name="anrede"]:checked').value
    const name = document.getElementById("name").value
    const vorname = document.getElementById("vorname").value
    const strasse = document.getElementById("strasse").value
    const hausNr = document.getElementById("hausnummer").value
    const geburtsdatum = document.getElementById("geburtsdatum").value
    const telefonNr = document.getElementById("telefon").value
    const handy = document.getElementById("mobil").value
    const email = document.getElementById("mail").value
    const versicherungsNr = document.getElementById("versicherungs").value
    const gueltigkeit = document.getElementById("gueltig").value
    const plz = document.getElementById("plz").value
    const krankenkassenNr = document.getElementById("krankenkassen").value

    console.log("kaka", typeof geburtsdatum)

    //Json zusammenbauen
    let jsonObject = {
        "anrede": anrede,
        "name": name,
        "vorname": vorname,
        "strasse": strasse,
        "hausNr": hausNr,
        "geburtsdatum": geburtsdatum,
        "telefonNr": telefonNr,
        "handy": handy,
        "email": email,
        "versicherungsNr": versicherungsNr,
        "gueltigkeit": gueltigkeit,
        "plz": plz,
        "krankenkassenNr": krankenkassenNr
    }

    console.log("Jason", jsonObject)

    doPostRequest('/api/kundes',  jsonObject, (response) => {
        console.log("Hier bin ich", response)
    })
    return jsonObject
}