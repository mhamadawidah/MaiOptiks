// Seiten mit Buttons öffnen
function openLink(link) {
    window.location.href = link;
}

// Datenbank Abfrage
function doRequest(method, url, data, func) {
    debugger;
    let request = new XMLHttpRequest();

    url = 'http://localhost:8080' + url;

    if (data !== undefined && data !== "") {
        url += data;
    } else {
        alert('\nBitte machen Sie eine Eingabe');

        return;
    }

    request.open(method, url, true);

    request.setRequestHeader("Accept", "application/json");
    request.setRequestHeader("Content-Type", "application/json");

    data !== undefined && data !== "" ? request.send(data) : request.send();

    request.onload = () => {
        if (request.status !== 200 || request.status !== 201 || request.status !== 202) {
            // alert('\nError: ' + request.status + '\n\n' + JSON.parse(request.response).exception);
        }

        func(request.response);
    };

    request.onerror = () => {
        alert('\nNetzwerkfehler!\n\nBitte den Systemadministrator kontaktieren.');
    };
}