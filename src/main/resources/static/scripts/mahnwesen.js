let input_auftragsnr = document.getElementById("auftragsnr");
let p_kundennummer = document.getElementById("kundennummer");
let p_kundenname = document.getElementById("kundenname");
let p_auftragsnummer = document.getElementById("auftragsnummer");
let textarea = document.getElementById("textarea");

let rechnungsnummer = ""
let datum = ""

let currentText = 0

function getKundeByAuftragsNr() {
    if (input_auftragsnr.value === null || input_auftragsnr.value === "") {
        console.log("expr Error")
            alert("Bitte geben Sie eine Auftragsnummer ein!")
            return;
        }

    const expression = /^[0-9]+$/
    if (!expression.test(input_auftragsnr.value.trim())) {
    console.log("expr Error")
        alert("Die Auftragsnummer darf nur aus Zahlen bestehen!")
        return;
    }

    doGetRequest(
        '/api/auftrags/',
        input_auftragsnr.value,
        (data) => {
            doGetRequest(
                '/api/kundes/',
                data.kundenNr,
                (data) => {
                    p_kundenname.innerHTML = data.vorname + " " + data.name;
                    p_kundennummer.innerHTML = data.kundenNr;
                    p_auftragsnummer.innerHTML = input_auftragsnr.value;
                    textarea.innerHTML = `Zahlungserinnerung
                                           Rechnung Nr.... vom ...
                                           Sehr geehrte/r ...,
                                           auf unsere o.a. Rechnung haben wir noch keinen Zahlungseingang feststellen können.
                                           Falls Ihrer Aufmerksamkeit unsere o. a. Rechnung entgangen ist, haben wir Ihnen eine Kopie unserer Rechnung beigefügt. Wir bitten Sie, die Regulierung nachzuholen und sehen dem Eingang Ihrer Zahlung entgegen.
                                           Sollten Sie zwischenzeitlich bereits Zahlung geleistet haben, betrachten Sie dieses Schreiben bitte als gegenstandslos.
                                           Mit freundlichen Grüßen`
            });
    });
}

function changeMahnung(direction) {
    if (p_auftragsnummer.innerHTML === "" || p_auftragsnummer.innerHTML === null) {
        alert("Bitte geben Sie eine Autragsnummer ein!")
        return;
    }

    input_auftragsnr.style.BorderColor = "black";

    currentText = currentText + direction

    if (currentText === 3) {currentText = 0}
    if (currentText === -1) {currentText = 2}

    switch (currentText) {
        case 0:
            textarea.innerHTML = `Zahlungserinnerung
                                  Rechnung Nr.... vom ...
                                  Sehr geehrte/r ...,
                                  auf unsere o.a. Rechnung haben wir noch keinen Zahlungseingang feststellen können.
                                  Falls Ihrer Aufmerksamkeit unsere o. a. Rechnung entgangen ist, haben wir Ihnen eine Kopie unserer Rechnung beigefügt. Wir bitten Sie, die Regulierung nachzuholen und sehen dem Eingang Ihrer Zahlung entgegen.
                                  Sollten Sie zwischenzeitlich bereits Zahlung geleistet haben, betrachten Sie dieses Schreiben bitte als gegenstandslos.
                                  Mit freundlichen Grüßen`;
            break;
        case 1:
            textarea.innerHTML = `Mahnung
                                  Rechnung Nr. ... vom ...
                                  Sehr geehrte/r ..,
                                  leider haben Sie auf unsere Zahlungserinnerung vom ... nicht reagiert. Wir bitten Sie daher den überfälligen Betrag in Höhe von ... bis zum ... auf unser Konto zu überweisen. Sofern Sie den vorgenannten Termin nicht einhalten, werden wir Ihnen Verzugszinsen und Mahnkosten berechnen müssen.
                                  Sollten Sie zwischenzeitlich bereits Zahlung geleistet haben, betrachten Sie dieses Schreiben bitte als gegenstandslos.
                                  Mit freundlichen Grüßen`;
            break;
        case 2:
            textarea.innerHTML = `Letzte Mahnung
                                  Rechnung Nr. ... vom ...
                                  Sehr geehrte/r ...,
                                  trotz unserer schriftlichen Erinnerungen vom ... und vom ... konnten wir bis zum heutigen Tag keinen Zahlungseingang feststellen.
                                  Zur Zahlung offen sind folgende Beträge:
                                  Rechnungsbetrag: ... Euro
                                  Verzugszinsen (... %) ... Euro
                                  Mahnkosten: ... Euro
                                  Summe: ... Euro
                                  Wir bitten Sie daher letztmalig, den fälligen Betrag bis zum ... auf unser Konto einzuzahlen.
                                  Sollte auch dieser Termin ohne Geldeingang auf unserem Konto verstreichen, sehen wir uns gezwungen, ohne erneute Aufforderung gerichtliche Schritte einzuleiten. Beachten Sie bitte, dass dadurch für Sie erhöhte Kosten entstehen würden.
                                  Hat sich diese Mahnung mit Ihrer Zahlung überschnitten, bitten wir Sie, dieses Schreiben als gegenstandslos zu betrachten.
                                  Mit freundlichen Grüßen`;
            break;
        default:
            textarea.innerHTML = "Erste Mahnung";
    }
}

function sendMahnung() {
    if (p_auftragsnummer.innerHTML === "" || p_auftragsnummer.innerHTML === null) {
        alert("Bitte geben Sie eine Autragsnummer ein!")
        return;
    }

    alert("Mahnung versendet!")
}