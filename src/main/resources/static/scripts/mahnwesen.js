let input_auftragsnr = document.getElementById("auftragsnr");
let p_kundennummer = document.getElementById("kundennummer");
let p_kundenname = document.getElementById("kundenname");
let p_auftragsnummer = document.getElementById("auftragsnummer");

function getKundeByAuftragsNr() {
    doGetRequest(
        '/api/auftrags/',
        input_auftragsnr.value,
        (data) => {   // func
            console.log("data auftrag", data)

            doGetRequest(
                '/api/kundes/',
                data.kundenNr,
                (data) => {   // func
                    console.log("data kunde", data)

                    p_kundenname.innerHTML = data.vorname + " " + data.name;
                    p_kundennummer.innerHTML = data.kundenNr;
            });
    });

}


/*const erstemahnung = `Rechnung Nr. ... vom ...
                     Sehr geehrte/r ..,
                     leider haben Sie auf unsere Zahlungserinnerung vom ... nicht reagiert. Wir bitten Sie daher den überfälligen Betrag in Höhe von ... bis zum ... auf unser Konto zu überweisen. Sofern Sie den vorgenannten Termin nicht einhalten, werden wir Ihnen Verzugszinsen und Mahnkosten berechnen müssen.
                     Sollten Sie zwischenzeitlich bereits Zahlung geleistet haben, betrachten Sie dieses Schreiben bitte als gegenstandslos.
                     Mit freundlichen Grüßen`*/