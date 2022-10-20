function getInputData() {
    console.log("Hole Daten...")

    const anrede = document.getElementById("anrede").value
    const name = document.getElementById("name").value
    const vorname = document.getElementById("vorname").value
    const strasse = document.getElementById("strasse").value
    const hausNr = document.getElementById("hausnummer").value
    const geburtsdatum = document.getElementById("geburtsdatum").value
    const telefonNr = document.getElementById("telefon").value
    const handy = document.getElementById("mobil").value
    const email = document.getElementById("mail").value
    const versicherungsNr = document.getElementById("versicherungs").value
    const gueltigkeit = document.getElementById("gueltigkeit").value
    const bemerkung = document.getElementById("").value
    const plz = document.getElementById("plz").value
    const krankenkassenNr = document.getElementById("krankenkassen").value

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

    return jsonObject
}